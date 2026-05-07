package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    UserRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repo = new UserRepository(this);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {

            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(repo.register(username, email, password)) {

                Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show();

                finish();

            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}