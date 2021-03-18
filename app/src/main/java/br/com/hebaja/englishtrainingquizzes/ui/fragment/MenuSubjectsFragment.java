package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.daos.SubjectDAO;
import br.com.hebaja.englishtrainingquizzes.model.Subject;
import br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter.SubjectsListAdapter;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.TasksViewModel;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.MEDIUM_MODE;

public class MenuSubjectsFragment extends BaseFragment {

    private List<Subject> subjects;
    private Integer levelKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineLevel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_subjects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView subjectsList = view.findViewById(R.id.list_buttons_subjects_recyclerview);
        subjectsList.setAdapter(new SubjectsListAdapter(subjects, getContext(), levelKey));
        final TasksViewModel tasksViewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        tasksViewModel.reset();
    }

    private void defineLevel() {
        assert getArguments() != null;
        levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();

        if(levelKey.equals(EASY_MODE)) {
            subjects = new SubjectDAO().easyList();
            Collections.sort(subjects);
            levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }
        if(levelKey.equals(MEDIUM_MODE)) {
            subjects = new SubjectDAO().mediumList();
            Collections.sort(subjects);
            levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }
        if(levelKey.equals(HARD_MODE)) {
            subjects = new SubjectDAO().hardList();
            Collections.sort(subjects);
            levelKey = MenuSubjectsFragmentArgs.fromBundle(getArguments()).getKey();
        }
    }
}