package com.miempresa.fitsmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private LinearLayout layoutHistory;
    private WorkoutLogRepository logRepository;
    private ExerciseRepository exerciseRepository;
    private EditText etExerciseName;
    private Button btnSearch;
    private TextView tvStats;
    private LineChart chartProgress;

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
        chartProgress = findViewById(R.id.chartProgress);

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

        String currentDate = "";

        for (WorkoutLog log : logs) {

            Exercise exercise = exerciseRepository.getExerciseById(log.getExerciseId());

            if (exercise == null)
                continue;

            if (!log.getDate().equals(currentDate)) {

                currentDate = log.getDate();

                TextView tvDate = new TextView(this);
                tvDate.setText(currentDate);
                tvDate.setTextSize(20);
                tvDate.setPadding(0, 30, 0, 20);
                tvDate.setTypeface(null, android.graphics.Typeface.BOLD);

                layoutHistory.addView(tvDate);
            }

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(40, 20, 40, 20);

            TextView tvExercise = new TextView(this);

            String text = exercise.getName() + "\n" + log.getWeight() + " kg" + " - " + log.getSets() + "x" + log.getReps();

            tvExercise.setText(text);
            card.addView(tvExercise);

            layoutHistory.addView(card);
        }
    }

    private void showExerciseStats(int userId, String exerciseName) {

        List<WorkoutLog> logs = logRepository.getLogsByExerciseName(userId, exerciseName);

        if (logs.isEmpty()) {
            tvStats.setText("No hay registros para ese ejercicio.");
            chartProgress.clear();
            return;
        }

        int times = logs.size();

        double maxWeight = 0;
        double totalWeight = 0;

        for (WorkoutLog log : logs) {

            if (log.getWeight() > maxWeight) {
                maxWeight = log.getWeight();
            }

            totalWeight += log.getWeight();
        }

        double averageWeight = totalWeight / times;

        WorkoutLog lastLog = logs.get(0);

        double lastWeight = lastLog.getWeight();
        String lastDate = lastLog.getDate();

        String text = "Ejercicio: " + exerciseName + "\n\nVeces realizado: " + times + "\nPeso máximo: " + maxWeight + " kg" + "\nPeso medio: " + String.format("%.1f", averageWeight) + " kg" + "\nÚltimo peso: " + lastWeight + " kg" + "\nÚltimo entrenamiento: " + lastDate;

        tvStats.setText(text);

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < logs.size(); i++) {

            entries.add(new Entry(i, (float) logs.get(i).getWeight()));
            dates.add(logs.get(i).getDate());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Peso (kg)");

        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);

        chartProgress.setData(lineData);

        XAxis xAxis = chartProgress.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);

        chartProgress.getDescription().setText("Progresión del peso");
        chartProgress.invalidate();
    }
}