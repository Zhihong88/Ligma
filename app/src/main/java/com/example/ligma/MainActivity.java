package com.example.ligma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_1);

        //On Click listener for buttons
        Button createAcc = findViewById(R.id.createAccount);
        Button signIn = findViewById(R.id.signIn);

        createAcc.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createAccount:
                openCreateAcc();
                break;
            case R.id.signIn:
                openSignin();
                break;
        }

    }

    //Methods for opening activity
    public void openCreateAcc(){
        Intent intent= new Intent(this, CreateAcc.class);
        startActivity(intent);
    }

    public void openSignin(){
        Intent intent= new Intent(this, Signin.class);
        startActivity(intent);
    }

}