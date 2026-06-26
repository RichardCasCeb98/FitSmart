package com.miempresa.fitsmart;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrainingDayActivity extends AppCompatActivity {

    private LinearLayout layoutExercises;
    private Button btnSaveTraining;
    private RoutineRepository routineRepo;
    private ExerciseRepository exerciseRepo;
    private List<ExerciseInput> exerciseInputs = new ArrayList<>();

    private static class ExerciseInput {
        Exercise exercise;
        RoutineExercise routineExercise;
        EditText etWeight;
        EditText etReps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutExercises = findViewById(R.id.layoutExercises);
        btnSaveTraining = findViewById(R.id.btnSaveTraining);

        routineRepo = new RoutineRepository(this);
        exerciseRepo = new ExerciseRepository(this);

        int routineId = getIntent().getIntExtra("routine_id", -1);
        String day = getIntent().getStringExtra("day");
        setTitle(day);

        layoutExercises.removeView(btnSaveTraining);
        loadExercises(routineId, day);
        layoutExercises.addView(btnSaveTraining);

        btnSaveTraining.setOnClickListener(v -> saveTraining());
    }

    private void loadExercises(int routineId, String day) {

        List<RoutineExercise> exercises =
                routineRepo.getExercisesByDay(routineId, day);

        for (RoutineExercise re : exercises) {

            Exercise exercise =
                    exerciseRepo.getExerciseById(re.getExerciseId());

            if (exercise == null) {
                continue;
            }

            addExerciseCard(exercise, re);
        }
    }

    private void addExerciseCard(Exercise exercise, RoutineExercise routineExercise) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(40,40,40,40);
        card.setBackgroundColor(android.graphics.Color.parseColor("#2A2A2A"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,30);
        card.setLayoutParams(params);

        TextView tvName = new TextView(this);
        tvName.setText(exercise.getName());
        tvName.setTextSize(22);
        tvName.setTextColor(android.graphics.Color.WHITE);
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvObjective = new TextView(this);
        tvObjective.setText("Objetivo: " + routineExercise.getSets() + " x " + routineExercise.getReps());
        tvObjective.setTextColor(android.graphics.Color.parseColor("#DDDDDD"));

        EditText etWeight = new EditText(this);
        etWeight.setHint("Peso (kg)");
        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etWeight.setTextColor(android.graphics.Color.WHITE);
        etWeight.setHintTextColor(android.graphics.Color.parseColor("#CCFFFFFF"));
        etWeight.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));

        EditText etReps = new EditText(this);
        etReps.setHint("Repeticiones realizadas");
        etReps.setInputType(InputType.TYPE_CLASS_NUMBER);
        etReps.setTextColor(android.graphics.Color.WHITE);
        etReps.setHintTextColor(android.graphics.Color.parseColor("#CCFFFFFF"));
        etReps.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));

        card.addView(tvName);
        card.addView(tvObjective);
        card.addView(etWeight);
        card.addView(etReps);

        layoutExercises.addView(card);

        ExerciseInput input = new ExerciseInput();
        input.exercise = exercise;
        input.routineExercise = routineExercise;
        input.etWeight = etWeight;
        input.etReps = etReps;

        exerciseInputs.add(input);
    }

    private void saveTraining() {

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        WorkoutLogRepository repository = new WorkoutLogRepository(this);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        int savedExercises = 0;

        for (ExerciseInput input : exerciseInputs) {

            String weightText = input.etWeight.getText().toString().trim();
            String repsText = input.etReps.getText().toString().trim();

            if (weightText.isEmpty() || repsText.isEmpty()) {
                continue;
            }

            double weight = Double.parseDouble(weightText);
            int reps = Integer.parseInt(repsText);

            repository.saveLog(userId, input.exercise.getId(), date, weight, reps, input.routineExercise.getSets());

            savedExercises++;
        }

        if (savedExercises == 0) {
            Toast.makeText(this, "Introduce al menos un ejercicio", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Entrenamiento guardado", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_routine) {

            Intent intent = new Intent(this, RoutineActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();

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