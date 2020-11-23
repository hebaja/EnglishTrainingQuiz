package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.savedstate.SavedStateRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import br.com.hebaja.englishtrainingquizzes.OptionsMenuConfigure;
import br.com.hebaja.englishtrainingquizzes.TaskObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.TaskListViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.views.BuilderQuizFragmentViews;

public class QuizFragment extends Fragment {

    private NavController controller;

    List<Task> tasks = new ArrayList<>();
    private int chosenLevel;
    private int chosenSubject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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

        TaskListViewModel model = new ViewModelProvider(this).get(TaskListViewModel.class);
        model.getTasks(chosenLevel, chosenSubject).observe(getViewLifecycleOwner(), taskList -> {
            tasks = taskList;

            BuilderQuizFragmentViews builderQuizFragmentViews = new BuilderQuizFragmentViews(taskList, requireActivity(), getViewLifecycleOwner());
            builderQuizFragmentViews.initializeViews(view);
            builderQuizFragmentViews.setButtons(view, chosenLevel, chosenSubject);
            builderQuizFragmentViews.updatePosition();
            builderQuizFragmentViews.updateTaskViews();

        });

        controller = Navigation.findNavController(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        OptionsMenuConfigure optionsMenuConfigure = new OptionsMenuConfigure(item, controller);
        optionsMenuConfigure.configureOptionsMenuItems();

        return super.onOptionsItemSelected(item);
    }
}