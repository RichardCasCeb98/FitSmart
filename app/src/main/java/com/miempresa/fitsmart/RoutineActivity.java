package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        UserRepository userRepo = new UserRepository(this);

        int userId = session.getUserId();

        Profile profile = userRepo.getProfile(userId);

        if(profile != null) {

            RoutineGenerator generator = new RoutineGenerator(this);

            Routine routine = generator.generateRoutine(profile.getAge(), profile.getLevel(), profile.getGoal());

            tvRoutineName.setText("Rutina: " + routine.getName());

            tvDays.setText("Días de entrenamiento: " + routine.getDays());

            StringBuilder builder = new StringBuilder();

            for(Exercise exercise : routine.getExercises()) {

                builder.append("• ").append(exercise.getName()).append(" - ").append(exercise.getSets()).append("x").append(exercise.getReps()).append("\n");
            }

            tvExercises.setText(builder.toString());
        }
    }
}