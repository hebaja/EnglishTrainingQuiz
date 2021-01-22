package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.TaskObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.enums.LevelType;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.EmailRegisterViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.TaskListViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.BuilderQuizFragmentViews;

public class QuizFragment extends BaseFragment {

    List<Task> tasks = new ArrayList<>();
    private int chosenLevel;
    private int chosenSubject;
    private LevelType level;
    private String subject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        chosenLevel = QuizFragmentArgs.fromBundle(getArguments()).getChosenLevel();
        chosenSubject = QuizFragmentArgs.fromBundle(getArguments()).getChosenOption();
        TaskObjectsGenerator generator = new TaskObjectsGenerator(chosenLevel, chosenSubject, getContext());
        tasks = generator.generateTaskObjectsFromJsonFile();
        level = generator.getLevel();
        subject = generator.getSubject();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TaskListViewModel taskListViewModel = new ViewModelProvider(this).get(TaskListViewModel.class);
        taskListViewModel.getTasks(chosenLevel, chosenSubject).observe(getViewLifecycleOwner(), taskList -> {
            tasks = taskList;

            BuilderQuizFragmentViews builderQuizFragmentViews = new BuilderQuizFragmentViews(
                    view,
                    taskList,
                    requireActivity(),
                    getViewLifecycleOwner(),
                    level,
                    subject);
            builderQuizFragmentViews.initializeViews();
            builderQuizFragmentViews.setButtons(chosenLevel, chosenSubject);
            builderQuizFragmentViews.updatePosition();
            builderQuizFragmentViews.updateTaskViews();
        });
    }
}