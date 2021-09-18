package org.meicode.rentacarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private ProgressBar progressBar;
    private Button registerUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner= (TextView) findViewById(R.id.bannerReg);
        banner.setOnClickListener(this);
        registerUser = (Button) findViewById(R.id.bt_RegisterReg);
        registerUser.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_NameAndSurnameReg);
        etEmail = (EditText) findViewById(R.id.et_EmailReg);
        etPassword = (EditText) findViewById(R.id.et_PasswordReg);
        etConfirmPassword = (EditText) findViewById(R.id.et_ConfirmPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bannerReg:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.bt_RegisterReg:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (name.isEmpty()){
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide valid email");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if (password.length()<8){
            etPassword.setError("Password min length is 8");
            etPassword.requestFocus();
            return;
        }
        if (!password.toString().equals(confirmPassword.toString())){
            etConfirmPassword.setError("Passwords must match");
            etConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name, email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Successful registration", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}