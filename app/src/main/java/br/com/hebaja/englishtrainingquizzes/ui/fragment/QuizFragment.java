package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.TaskObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.ui.views.BuilderQuizFragmentViews;

public class QuizFragment extends Fragment {

    List<Task> tasks = new ArrayList<>();
    private int chosenLevel;
    private int chosenSubject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        chosenLevel = QuizFragmentArgs.fromBundle(getArguments()).getChosenLevel();
        chosenSubject = QuizFragmentArgs.fromBundle(getArguments()).getChosenOption();
        tasks = new TaskObjectsGenerator(chosenLevel, chosenSubject, getContext()).generateTaskObjectsFromJsonFile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BuilderQuizFragmentViews builderQuizFragmentViews = new BuilderQuizFragmentViews(tasks, requireActivity());
        builderQuizFragmentViews.initializeViews(view);
        builderQuizFragmentViews.setButtons(view, chosenLevel);
        builderQuizFragmentViews.updatePosition();
        builderQuizFragmentViews.updateTaskViews();
    }


}