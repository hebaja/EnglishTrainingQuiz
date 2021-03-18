package br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Subject;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragmentDirections;

import static br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragmentDirections.actionMenuSubjectsToQuiz;

public class SubjectsListAdapter extends RecyclerView.Adapter<SubjectsListAdapter.SubjectViewHolder> {

    private final List<Subject> subjects;
    private final Context context;
    private final Integer chosenLevelKey;

    public SubjectsListAdapter(List<Subject> subjects, Context context, Integer chosenLevelKey) {
        this.subjects = subjects;
        this.context = context;
        this.chosenLevelKey = chosenLevelKey;
    }

    @NonNull
    @NotNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Button buttonCreated = (Button) LayoutInflater.from(context).inflate(R.layout.button_subject_list_item, parent, false);
        return new SubjectViewHolder(buttonCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubjectViewHolder holder, int position) {
        holder.binds(subjects, chosenLevelKey, position);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {

        private final Button button;

        public SubjectViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            button = (Button) itemView;
        }

        public void binds(List<Subject> subjects, Integer chosenLevelKey, Integer buttonPosition) {
            button.setText(subjects.get(buttonPosition).getPrompt());
            button.setOnClickListener(view -> {

                String fileName = subjects.get(buttonPosition).getFileName();
                String subject = subjects.get(buttonPosition).getPrompt();

                NavController controller = Navigation.findNavController(view);
                MenuSubjectsFragmentDirections.ActionMenuSubjectsToQuiz directions = actionMenuSubjectsToQuiz(buttonPosition, chosenLevelKey, fileName, subject);
                controller.navigate(directions);
            });
        }
    }

}
