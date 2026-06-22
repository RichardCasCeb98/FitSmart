package com.miempresa.fitsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        ExerciseRepository exerciseRepo = new ExerciseRepository(this);

        RoutineEntity routine = routineRepo.getRoutineByUser(userId);

        if (routine != null) {

            tvRoutineName.setText("Rutina: " + routine.getName());
            tvDays.setText("Días de entrenamiento: " + routine.getDaysPerWeek());

            List<RoutineExercise> routineExercises = routineRepo.getRoutineExercises(routine.getId());
            StringBuilder builder = new StringBuilder();
            String currentDay = "";

            for (RoutineExercise re : routineExercises) {

                if (!re.getDay().equals(currentDay)) {
                    currentDay = re.getDay();
                    builder.append("\n").append(currentDay).append("\n");
                }

                Exercise exercise = exerciseRepo.getExerciseById(re.getExerciseId());

                if (exercise != null) {
                    builder.append("• ").append(exercise.getName()).append(" - ").append(re.getSets()).append("x").append(re.getReps()).append("\n");
                }
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
            startActivity(new Intent(this, ProfileActivity.class));
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