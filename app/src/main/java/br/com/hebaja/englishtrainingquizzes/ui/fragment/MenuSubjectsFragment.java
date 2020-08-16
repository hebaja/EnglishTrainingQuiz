package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.daos.SubjectDAO;
import br.com.hebaja.englishtrainingquizzes.model.Subject;
import br.com.hebaja.englishtrainingquizzes.ui.adapter.SubjectsListAdapter;

import static br.com.hebaja.englishtrainingquizzes.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.MEDIUM_MODE;

public class MenuSubjectsFragment extends Fragment {

    private List<Subject> subjects;
    private Integer levelKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        this.levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();

        if(levelKey.equals(EASY_MODE)) {
            subjects = new SubjectDAO().easyList();
            this.levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }

        if(levelKey.equals(MEDIUM_MODE)) {
            subjects = new SubjectDAO().mediumList();
            this.levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }

        if(levelKey.equals(HARD_MODE)) {
            subjects = new SubjectDAO().hardList();
            this.levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_subjects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView subjectsList = view.findViewById(R.id.list_buttons_subjects_list_view);
        subjectsList.setAdapter(new SubjectsListAdapter(subjects, levelKey, getContext()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getContext(), "clicado", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
