package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.TextView;
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
}