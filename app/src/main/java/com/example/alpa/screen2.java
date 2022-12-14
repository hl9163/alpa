package com.example.alpa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class screen2 extends AppCompatActivity {
    Button gallery;
    ImageView imageView;
    Uri image;
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference copyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        imageView = (ImageView) findViewById(R.id.imageView);
        gallery =  (Button) findViewById(R.id.cameraOpen);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Log.i("tag","ok");
            image = data.getData();
            uploadPic();



        }else{
            Log.i("tag","not ok");
        }
    }

    private void uploadPic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading Image...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        Log.i("tag",randomKey);
        StorageReference ref = storageReference.child("images/"+randomKey+".png");
        Log.i("tag",ref.toString());
        copyRef =  FirebaseStorage.getInstance().getReference("images/"+randomKey+".png");
        Log.i("tag",copyRef.toString());
        ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.", Snackbar.LENGTH_LONG).show();
                loadFile();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Failed to upload", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.000 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount() );
                pd.setMessage((int) progressPercent +"%");
            }
        });


    }
    private void loadFile(){
        try {
            File local_file = File.createTempFile("tempFile",".jpg");
            copyRef.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}