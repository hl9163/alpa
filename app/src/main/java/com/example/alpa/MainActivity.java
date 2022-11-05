package com.example.alpa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.alpa.FBref.refUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    String str_email, str_password;
    Intent si;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();

    }

    public void go(View view) {
        str_email = email.getText().toString().trim();
        str_password = password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(str_email,str_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            user man = new user(str_email,str_password);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(man).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "good", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "not good", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ac1){
            si = new Intent(this,MainActivity.class);
            startActivity(si);
        }else if(id == R.id.ac2){
            si = new Intent(this,screen2.class);
            startActivity(si);
        }else if(id == R.id.ac3){
            si = new Intent(this,screen3.class);
            startActivity(si);
        }else if(id == R.id.ac4){
            si = new Intent(this,screen4.class);
            startActivity(si);
        }else if(id == R.id.ac5){
            si = new Intent(this,screen5.class);
            startActivity(si);
        }else if(id == R.id.ac6){
            si = new Intent(this,screen6.class);
            startActivity(si);
        }else if(id == R.id.ac7){
            si = new Intent(this,screen7.class);
            startActivity(si);
        }
        return true;
    }
}