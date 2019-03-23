package com.example.blotcanvas.automated_farming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText etEmail;
    private EditText etpassword;
    private TextView tvregister;
    private ProgressDialog progressDialog;
    private ImageView imageViewHead;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null ){
            //strat next activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainPage.class));
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etpassword = (EditText) findViewById(R.id.editTextPassword);
        tvregister = (TextView) findViewById(R.id.textViewNotReg);


        buttonLogin.setOnClickListener(this);
        tvregister.setOnClickListener(this);

    }

    private void registerUser(){
        String email= etEmail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is registered
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            return;
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    private void userLogin(){
        String email= etEmail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start new activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainPage.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Incorrect Credentials !!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();

        }
        if(view == tvregister){
            registerUser();
        }

    }
    @Override
    protected void onStop() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        super.onStop();
    }

}
