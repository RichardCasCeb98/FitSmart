package com.miempresa.fitsmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etAge, etWeight, etHeight;
    Spinner spLevel, spGoal;
    private Button btnSave, btnLogout;

    private UserRepository repo;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SessionManager(this);
        repo = new UserRepository(this);

        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        spLevel = findViewById(R.id.spLevel);
        spGoal = findViewById(R.id.spGoal);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);

        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLevel.setAdapter(levelAdapter);

        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(this, R.array.goals, android.R.layout.simple_spinner_item);

        goalAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spGoal.setAdapter(goalAdapter);

        // Guardar perfil
        btnSave.setOnClickListener(v -> saveProfile());

        // Cerrar sesión
        btnLogout.setOnClickListener(v -> {

            session.logout();

            Intent intent = new Intent(
                    ProfileActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();
        });
    }

    private void saveProfile() {

        String ageText = etAge.getText().toString().trim();
        String weightText = etWeight.getText().toString().trim();
        String heightText = etHeight.getText().toString().trim();
        String level = spLevel.getSelectedItem().toString();
        String goal = spGoal.getSelectedItem().toString();

        // Validar campos vacíos
        if (ageText.isEmpty() || weightText.isEmpty() || heightText.isEmpty()) {

            Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        try {

            int age = Integer.parseInt(ageText);
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            int userId = session.getUserId();

            boolean result = repo.saveProfile(
                    userId,
                    age,
                    weight,
                    height,
                    level,
                    goal
            );

            if (result) {

                Toast.makeText(
                        this,
                        "Perfil guardado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Error al guardar el perfil",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Edad, peso y altura deben ser numéricos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}