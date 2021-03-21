package br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

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
        private final LinearProgressIndicator linearProgressIndicator;

        public AverageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.average_cardview_subject);
            level = itemView.findViewById(R.id.average_cardview_level);
            score = itemView.findViewById(R.id.average_cardview_score);
            linearProgressIndicator = itemView.findViewById(R.id.average_cardview_bar_linear_progress_bar);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(Average average) {
            setColorBar(average);
            String scoreToString = Double.toString(average.getAverage());
            linearProgressIndicator.setMax(10);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                linearProgressIndicator.setProgress((int) Math.round(average.getAverage()), true);
            }
            subject.setText(average.getSubject());
            level.setText(average.getLevel().toString());
            score.setText(scoreToString);
            linearProgressIndicator.setProgressTintList(ColorStateList.valueOf(Color.BLACK));
            linearProgressIndicator.setScaleY(2f);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setColorBar(Average average) {
            if(average.getAverage() < 4) {
                linearProgressIndicator.setIndicatorColor(Color.RED);
            } else if(average.getAverage() < 7) {
                linearProgressIndicator.setIndicatorColor(Color.parseColor("#ffd600"));
            } else {
                linearProgressIndicator.setIndicatorColor(Color.GREEN);
            }
        }
    }
}
