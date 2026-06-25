package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private LinearLayout layoutHistory;
    private WorkoutLogRepository logRepository;
    private ExerciseRepository exerciseRepository;
    private EditText etExerciseName;
    private Button btnSearch;
    private TextView tvStats;
    private LinearLayout layoutChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Historial");

        layoutHistory = findViewById(R.id.layoutHistory);
        etExerciseName = findViewById(R.id.etExerciseName);
        btnSearch = findViewById(R.id.btnSearch);
        tvStats = findViewById(R.id.tvExerciseStats);
        layoutChart = findViewById(R.id.layoutChart);

        logRepository = new WorkoutLogRepository(this);
        exerciseRepository = new ExerciseRepository(this);

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        loadHistory(userId);

        btnSearch.setOnClickListener(v -> {

            String exerciseName =
                    etExerciseName.getText().toString().trim();

            if (exerciseName.isEmpty()) {
                tvStats.setText("Introduce un ejercicio.");
                layoutChart.removeAllViews();
                return;
            }

            showExerciseStats(userId, exerciseName);
        });
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

    private void showExerciseStats(int userId, String exerciseName) {

        List<WorkoutLog> logs = logRepository.getLogsByExerciseName(userId, exerciseName);

        layoutChart.removeAllViews();

        if (logs.isEmpty()) {

            tvStats.setText("No hay registros para ese ejercicio.");
            return;
        }

        int times = logs.size();
        double maxWeight = 0;

        for (WorkoutLog log : logs) {

            if (log.getWeight() > maxWeight) {
                maxWeight = log.getWeight();
            }
        }

        String text = "Ejercicio: " + exerciseName + "\nVeces realizado: " + times + "\nPeso máximo: " + maxWeight + " kg";
        tvStats.setText(text);

        TextView tvChart = new TextView(this);
        tvChart.setText("\nAquí mostraremos la gráfica en el siguiente paso.");

        layoutChart.addView(tvChart);
    }
}