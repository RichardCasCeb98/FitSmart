package com.miempresa.fitsmart;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.List;

public class TrainingDayActivity extends AppCompatActivity {

    private LinearLayout layoutExercises;
    private RoutineRepository routineRepo;
    private ExerciseRepository exerciseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutExercises = findViewById(R.id.layoutExercises);

        routineRepo = new RoutineRepository(this);
        exerciseRepo = new ExerciseRepository(this);

        int routineId = getIntent().getIntExtra("routine_id", -1);
        String day = getIntent().getStringExtra("day");

        setTitle(day);

        loadExercises(routineId, day);
    }

    private void loadExercises(int routineId, String day) {

        List<RoutineExercise> exercises =
                routineRepo.getExercisesByDay(routineId, day);

        for (RoutineExercise re : exercises) {

            Exercise exercise =
                    exerciseRepo.getExerciseById(re.getExerciseId());

            if (exercise == null)
                continue;

            addExerciseCard(exercise, re);
        }
    }

    private void addExerciseCard(
            Exercise exercise,
            RoutineExercise routineExercise) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20,20,20,20);

        TextView tvName = new TextView(this);
        tvName.setText(exercise.getName());
        tvName.setTextSize(20);

        TextView tvObjective = new TextView(this);
        tvObjective.setText("Objetivo: " + routineExercise.getSets() + " x " + routineExercise.getReps());

        EditText etWeight = new EditText(this);
        etWeight.setHint("Peso (kg)");
        etWeight.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        EditText etReps = new EditText(this);
        etReps.setHint("Repeticiones realizadas");
        etReps.setInputType(InputType.TYPE_CLASS_NUMBER);

        card.addView(tvName);
        card.addView(tvObjective);
        card.addView(etWeight);
        card.addView(etReps);

        layoutExercises.addView(card);
    }
}