package com.miempresa.fitsmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    private TextView tvRoutineName;
    private TextView tvDays;
    private LinearLayout layoutDays;
    private Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvRoutineName = findViewById(R.id.tvRoutineName);
        tvDays = findViewById(R.id.tvDays);
        layoutDays = findViewById(R.id.layoutDays);
        btnHistory = findViewById(R.id.btnHistory);

        tvRoutineName.setTextColor(Color.WHITE);
        tvDays.setTextColor(Color.WHITE);

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        btnHistory.setOnClickListener(v -> {Intent intent = new Intent(RoutineActivity.this, WorkoutHistoryActivity.class);
            startActivity(intent);
        });

        RoutineRepository routineRepo = new RoutineRepository(this);
        ExerciseRepository exerciseRepo = new ExerciseRepository(this);

        RoutineEntity routine = routineRepo.getRoutineByUser(userId);

        if (routine != null) {

            tvRoutineName.setText("Rutina: " + routine.getName());
            tvDays.setText("Días de entrenamiento: " + routine.getDaysPerWeek());

            List<RoutineExercise> routineExercises = routineRepo.getRoutineExercises(routine.getId());
            String currentDay = "";

            for (RoutineExercise re : routineExercises) {

                if (!re.getDay().equals(currentDay)) {

                    currentDay = re.getDay();

                    TextView tvDay = new TextView(this);
                    tvDay.setText(currentDay);
                    tvDay.setTextSize(20);
                    tvDay.setTypeface(null, Typeface.BOLD);
                    tvDay.setPadding(0, 32, 0, 16);

                    tvDay.setTextColor(getColor(R.color.blue));

                    String finalCurrentDay = currentDay;

                    tvDay.setOnClickListener(v -> {

                        Intent intent = new Intent(RoutineActivity.this, TrainingDayActivity.class);
                        intent.putExtra("routine_id", routine.getId());
                        intent.putExtra("day", finalCurrentDay);

                        startActivity(intent);
                    });

                    layoutDays.addView(tvDay);
                }

                Exercise exercise = exerciseRepo.getExerciseById(re.getExerciseId());

                if (exercise != null) {

                    TextView tvExercise = new TextView(this);
                    tvExercise.setText("• " + exercise.getName() + " - " + re.getSets() + "x" + re.getReps());
                    tvExercise.setPadding(50, 0, 0, 12);

                    tvExercise.setTextColor(Color.parseColor("#DDDDDD"));

                    layoutDays.addView(tvExercise);
                }
            }

        } else {

            tvRoutineName.setText("No existe ninguna rutina");
            tvDays.setText("");
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