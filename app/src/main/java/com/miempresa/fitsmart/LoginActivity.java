package com.miempresa.fitsmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnGoRegister;
    UserRepository repo;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(this);

        if(session.isLogged()) {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }

        repo = new UserRepository(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            int userId = repo.login(email, password);

            if(userId != -1) {

                session.saveUser(userId, email);

                Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, ProfileActivity.class));
                finish();

            } else {
                Toast.makeText(this, "Error en login", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}