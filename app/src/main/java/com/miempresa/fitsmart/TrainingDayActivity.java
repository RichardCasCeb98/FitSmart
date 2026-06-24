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
        card.setPadding(20, 20, 20, 20);

        TextView tvName = new TextView(this);
        tvName.setText(exercise.getName());
        tvName.setTextSize(20);

        TextView tvObjective = new TextView(this);
        tvObjective.setText("Objetivo: " + routineExercise.getSets() + " x " + routineExercise.getReps());

        EditText etWeight = new EditText(this);
        etWeight.setHint("Peso (kg)");
        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        EditText etReps = new EditText(this);
        etReps.setHint("Repeticiones realizadas");
        etReps.setInputType(InputType.TYPE_CLASS_NUMBER);

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
}