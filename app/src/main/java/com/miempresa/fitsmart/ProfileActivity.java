package com.miempresa.fitsmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {

    EditText etAge, etWeight, etHeight, etLevel, etGoal;
    Button btnSave;
    UserRepository repo;
    SessionManager session;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SessionManager(this);

        repo = new UserRepository(this);

        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etLevel = findViewById(R.id.etLevel);
        etGoal = findViewById(R.id.etGoal);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {

            int userId = session.getUserId();
            int age = Integer.parseInt(etAge.getText().toString());
            double weight = Double.parseDouble(etWeight.getText().toString());
            double height = Double.parseDouble(etHeight.getText().toString());
            String level = etLevel.getText().toString();
            String goal = etGoal.getText().toString();

            boolean result = repo.saveProfile(userId, age, weight, height, level, goal);

            if(result) {
                Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}