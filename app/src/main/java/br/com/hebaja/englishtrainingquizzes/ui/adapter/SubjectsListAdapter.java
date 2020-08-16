package br.com.hebaja.englishtrainingquizzes.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Subject;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragmentDirections;

import static br.com.hebaja.englishtrainingquizzes.R.layout.button_menu_list_item;
import static br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragmentDirections.*;

public class SubjectsListAdapter extends BaseAdapter {

    private final List<Subject> subjects;
    private final Context context;
    private final Integer chosenLevelKey;

    public SubjectsListAdapter(List<Subject> subjects, Integer chosenLevelKey, Context context) {
        this.subjects = subjects;
        this.chosenLevelKey = chosenLevelKey;
        this.context = context;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int chosenSubjectKey, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") Button buttonCreated = (Button) LayoutInflater.from(context).inflate(button_menu_list_item, parent, false);
        buttonCreated.setText(subjects.get(chosenSubjectKey).getPrompt());

        buttonCreated.setOnClickListener(view -> {
            NavController controller = Navigation.findNavController(view);

            ActionMenuSubjectsToQuiz directions = actionMenuSubjectsToQuiz(chosenSubjectKey, chosenLevelKey);
            controller.navigate(directions);
        });
        return buttonCreated;
    }
}
