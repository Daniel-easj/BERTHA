package com.dba.berthamandatoryassignment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "UserLogin";

    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        emailText = findViewById(R.id.editTextEmail);
        passwordText = findViewById(R.id.editTextPassword);
        spinner = findViewById(R.id.progressBar1);

        spinner.setVisibility(View.GONE);

        // ??
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged: Signed in " + user.getUid());
                }
                else{
                    // user signed out
                    Log.d(TAG, "signed out");
                }
            }
        };


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        // TODO: Check if user i already signed in, then take appropriate action
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
    if (mAuthListener != null){
        mAuth.removeAuthStateListener(mAuthListener);
    }
    }

    // SIGN UP HANDLER
    public void createAccount(String email, String password){
        spinner.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.e("FCREATE", "Task success");

                            // Sign up success, go to main activity with new user
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            spinner.setVisibility(View.GONE);
                            FirebaseException e = (FirebaseException) task.getException();
                            Log.e("FCREATE", e.getMessage());

                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Could not create account: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        spinner.setVisibility(View.GONE);
                    }
                });

    }

    // SIGN IN HANDLER
    public void signIn(String email, String password){
        spinner.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("FSIGNIN", "sign in success");
                            Toast.makeText(getBaseContext(), "User signed in", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            // If sign in is successful, go to main activity
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("UID", user.getUid());
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);

                        } else {
                            FirebaseException e = (FirebaseException) task.getException();
                            Log.e("FSIGNIN", e.getMessage());

                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Sign in failed: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                        }

                        spinner.setVisibility(View.GONE);
                    }
                });
    }

    public void onClick(View view) {
        int i = view.getId();

        if(i == R.id.signUp){
            createAccount(emailText.getText().toString(), passwordText.getText().toString());
        }
        else if (i == R.id.signIn){
            signIn(emailText.getText().toString(), passwordText.getText().toString());
        }
    }
}
