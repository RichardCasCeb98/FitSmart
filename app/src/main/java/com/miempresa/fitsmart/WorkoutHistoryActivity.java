package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private LinearLayout layoutHistory;
    private WorkoutLogRepository logRepository;
    private ExerciseRepository exerciseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Historial");

        layoutHistory = findViewById(R.id.layoutHistory);

        logRepository = new WorkoutLogRepository(this);
        exerciseRepository = new ExerciseRepository(this);

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        loadHistory(userId);
    }

    private void loadHistory(int userId) {

        List<WorkoutLog> logs = logRepository.getLogsByUser(userId);

        if (logs.isEmpty()) {

            TextView tv = new TextView(this);
            tv.setText("Todavía no has registrado entrenamientos.");
            layoutHistory.addView(tv);

            return;
        }

        for (WorkoutLog log : logs) {

            Exercise exercise = exerciseRepository.getExerciseById(log.getExerciseId());

            if (exercise == null)
                continue;

            TextView tv = new TextView(this);
            String text = log.getDate() + "\n" + exercise.getName() + "\nPeso: " + log.getWeight() + " kg" + "\nSeries: " + log.getSets() + "\nRepeticiones: " + log.getReps();

            tv.setText(text);
            tv.setPadding(20,20,20,20);

            layoutHistory.addView(tv);
        }
    }
}