package br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Average;

public class AveragesListAdapter extends RecyclerView.Adapter<AveragesListAdapter.AverageViewHolder> {

    private final List<Average> averages;
    private final Context context;

    public AveragesListAdapter(List<Average> averages, Context context) {
        this.averages = averages;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AveragesListAdapter.AverageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(context).inflate(R.layout.subject_average_cardview_item, parent, false);
        return new AverageViewHolder(createdView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AveragesListAdapter.AverageViewHolder holder, int position) {
        Average average = averages.get(position);
        holder.bind(average);
    }

    @Override
    public int getItemCount() {
        return averages.size();
    }

    static class AverageViewHolder extends RecyclerView.ViewHolder {

        private final TextView subject;
        private final TextView level;
        private final TextView score;
        private final ProgressBar averageBar;

        public AverageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.average_cardview_subject);
            level = itemView.findViewById(R.id.average_cardview_level);
            score = itemView.findViewById(R.id.average_cardview_score);
            averageBar = itemView.findViewById(R.id.average_cardview_bar);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(Average average) {
            setColorBar(average);
            String scoreToString = Double.toString(average.getAverage());
            averageBar.setMax(10);
            averageBar.setProgress((int) Math.round(average.getAverage()));
            subject.setText(average.getSubject());
            level.setText(average.getLevel().toString());
            score.setText(scoreToString);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setColorBar(Average average) {
            if(average.getAverage() < 4) {
                averageBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            } else if(average.getAverage() < 7) {
                averageBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(230, 230, 0)));
            } else {
                averageBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 153, 51)));
            }
        }
    }
}
