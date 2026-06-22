package com.miempresa.fitsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    private TextView tvRoutineName;
    private TextView tvDays;
    private TextView tvExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvRoutineName = findViewById(R.id.tvRoutineName);
        tvDays = findViewById(R.id.tvDays);
        tvExercises = findViewById(R.id.tvExercises);

        SessionManager session = new SessionManager(this);

        int userId = session.getUserId();

        RoutineRepository routineRepo = new RoutineRepository(this);
        RoutineEntity routine = routineRepo.getRoutineByUser(userId);

        if (routine != null) {

            tvRoutineName.setText("Rutina: " + routine.getName());
            tvDays.setText("Días de entrenamiento: " + routine.getDaysPerWeek());
            List<Exercise> exercises = routineRepo.getExercisesByRoutine(routine.getId());
            StringBuilder builder = new StringBuilder();

            for (Exercise exercise : exercises) {
                builder.append("• ").append(exercise.getName()).append(" - ").append(exercise.getSets()).append("x").append(exercise.getReps()).append("\n");
            }

            tvExercises.setText(builder.toString());

        } else {
            tvRoutineName.setText("No existe ninguna rutina");
            tvDays.setText("");
            tvExercises.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_routine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menu_logout) {

            SessionManager session = new SessionManager(this);
            session.logout();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
            return true;
        }

        if (item.getItemId() == R.id.menu_exit) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}