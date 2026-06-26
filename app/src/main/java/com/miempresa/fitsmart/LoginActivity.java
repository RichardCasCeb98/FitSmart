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
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);
        repo = new UserRepository(this);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(v -> {
            finishAffinity();
        });

        if(session.isLogged()) {

            int userId = session.getUserId();

            if(repo.hasProfile(userId)) {
                startActivity(new Intent(this, RoutineActivity.class));

            } else {
                startActivity(new Intent(this, ProfileActivity.class));
            }

            finish();
            return;
        }


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {

                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = repo.login(email, password);

            if(userId != -1) {

                session.saveUser(userId, email);
                Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();

                if(repo.hasProfile(userId)) {
                    startActivity(new Intent(this, RoutineActivity.class));

                } else {
                    startActivity(new Intent(this, ProfileActivity.class));
                }

                finish();

            } else {
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}