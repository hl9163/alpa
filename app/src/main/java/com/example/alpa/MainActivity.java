package com.example.alpa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.PatternItem;
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
    boolean wantToLogIn = false;

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
        wantToLogIn = false;
        str_email = email.getText().toString().trim();
        str_password = password.getText().toString().trim();
        if (str_email.isEmpty() || str_password.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            Toast.makeText(MainActivity.this, "something is missing or not valid!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "user already exist", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        mAuth.createUserWithEmailAndPassword(str_email,str_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "good", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void logIn(View view) {
        wantToLogIn = true;
        str_email = email.getText().toString().trim();
        str_password = password.getText().toString().trim();
        if (str_email.isEmpty() || str_password.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            Toast.makeText(MainActivity.this, "something is missing or not valid!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (wantToLogIn){
                        Toast.makeText(MainActivity.this, "welcome!", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

    }

    public void isExist(){


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