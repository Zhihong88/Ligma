package com.example.ligma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAcc extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextHomeCountry;
    private ProgressBar progressBar;
    private ImageView backspace;
    private Button registerUser;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        mAuth = FirebaseAuth.getInstance();

        //left arrow icon for backing to login page
        ImageView backspace = findViewById(R.id.backspace);
        backspace.setOnClickListener(this);

        //register user button
        Button registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        //Input text box
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextHomeCountry = (EditText) findViewById(R.id.homeCountry);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backspace:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }
    private void  registerUser(){
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String homeCountry = editTextHomeCountry.getText().toString().trim();


        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Minimum Length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(homeCountry.isEmpty()){
            editTextHomeCountry.setError("Home Country is required");
            editTextHomeCountry.requestFocus();
            return;
        }

        progressBar.setVisibility(View.GONE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user  = new User(email,homeCountry);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(CreateAcc.this,"user has been registered successfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        //direct to login layout
                                        startActivity(new Intent(CreateAcc.this, HomeScreen.class));


                                    }
                                    else{
                                        Toast.makeText(CreateAcc.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(CreateAcc.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });








    }


}