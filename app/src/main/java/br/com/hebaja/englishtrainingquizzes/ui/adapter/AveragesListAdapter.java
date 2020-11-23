package br.com.hebaja.englishtrainingquizzes.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;

public class AveragesListAdapter extends BaseAdapter {

    private final List<Exercise> exercises;
    private final Context context;

    public AveragesListAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View createdView = LayoutInflater.from(context).inflate(R.layout.subject_average_cardview_item, parent, false);

        Exercise exercise = exercises.get(position);

        TextView subject = createdView.findViewById(R.id.average_cardview_subject);
        TextView level = createdView.findViewById(R.id.average_cardview_level);
        TextView score = createdView.findViewById(R.id.average_cardview_score);
        ProgressBar averageBar = createdView.findViewById(R.id.average_cardview_bar);

        String scoreToString = Double.toString(exercise.getScore());

//        double doubleScore = exercise.getScore();
//        doubleScore * 10;
//
//        averageBar.setMax(10);
//        averageBar.setProgress();


        subject.setText(exercise.getSubject());
        Log.i("subject", "getView: " + exercise.getSubject());
        level.setText(exercise.getLevel().toString());
        score.setText(scoreToString);

        return createdView;
    }
}
