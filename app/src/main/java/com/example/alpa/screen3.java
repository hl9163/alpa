package com.example.alpa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class screen3 extends AppCompatActivity {
    Button camera;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap bitmap;
    boolean canSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        imageView = (ImageView) findViewById(R.id.imageView);
        camera = (Button) findViewById(R.id.cameraOpen);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            canSave = true;
        }
    }
    public void save(View view) {
        if (canSave){
            uploadPic(bitmap);
            canSave = false;
        }

    }
    private void uploadPic(Bitmap bb) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bb.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        byte b[] = bytes.toByteArray();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading Image...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/"+randomKey);
        ref.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.", Snackbar.LENGTH_LONG).show();

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


}