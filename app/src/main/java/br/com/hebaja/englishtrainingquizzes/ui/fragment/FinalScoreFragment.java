package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.utils.OptionsMenuConfigure;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.async.SaveExerciseTask;
import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.database.EtqDatabase;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.SaveExerciseService;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ExerciseViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.TasksViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections.*;

public class FinalScoreFragment extends Fragment {

    public static final String EXERCISE_SAVED_MESSAGE = "Your exercise was successfully saved.";
    public static final String EXERCISE_SAVE_ERROR_MESSAGE = "There was a problem. Your exercise couldn't be saved. Check your internet connection";
    public static final String RETRY_ACTION_LABEL = "Retry";
    private NavController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.final_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView finalScore = view.findViewById(R.id.final_score_counter);
        Button buttonTryAgain = view.findViewById(R.id.final_score_button_try_again);
        Button buttonBackToMenu = view.findViewById(R.id.final_score_button_back_menu);
        final ProgressBar progressBar = view.findViewById(R.id.final_score_progress_bar);

        assert getArguments() != null;
        finalScore.setText(String.valueOf(FinalScoreFragmentArgs.fromBundle(getArguments()).getScore()));

        int chosenLevel = FinalScoreFragmentArgs.fromBundle(getArguments()).getChosenLevel();
        int chosenSubject = FinalScoreFragmentArgs.fromBundle(getArguments()).getChosenOption();
        String subjectPrompt = FinalScoreFragmentArgs.fromBundle(getArguments()).getSubjectPrompt();
        String fileName = FinalScoreFragmentArgs.fromBundle(getArguments()).getFileName();

        final ExerciseViewModel exerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        exerciseViewModel.getExercise().observe(getViewLifecycleOwner(), exercise -> {
            if(exercise != null) {
                progressBar.setVisibility(View.VISIBLE);
                final List<Exercise> exercises = new ArrayList<>(Collections.singleton(exercise));
                saveOnApi(exercises, view, requireActivity(), progressBar);
            }
        });

        controller = Navigation.findNavController(view);

        buttonBackToMenu.setOnClickListener(v -> {
            NavDirections directions = actionGlobalMenuLevels();
            final TasksViewModel tasksViewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
            tasksViewModel.reset();
            controller.navigate(directions);
        });

        buttonTryAgain.setOnClickListener(v -> {
            final TasksViewModel tasksViewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
            tasksViewModel.reset();
            ActionFinalScoreToQuiz directions = actionFinalScoreToQuiz(chosenSubject, chosenLevel, fileName, subjectPrompt);
            controller.navigate(directions);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        OptionsMenuConfigure optionsMenuConfigure = new OptionsMenuConfigure(item, controller, getContext());
        optionsMenuConfigure.configureOptionsMenuItems(getActivity());
        return super.onOptionsItemSelected(item);
    }

    private void saveOnApi(List<Exercise> exercises, View view, FragmentActivity activity, ProgressBar progressBar) {
        SaveExerciseService service = new BaseRetrofit().getSaveExerciseService();
        Call<Void> call = service.register(exercises);
        call.enqueue(new Callback<Void>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Void> call, Response<Void> response) {
                doOnResponse(call, response, view, activity, progressBar, exercises);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                configureErrorSnackbar(view, call, activity, progressBar, exercises);
                final ExerciseDao dao = EtqDatabase.getInstance(activity).getExercisesDao();
                new SaveExerciseTask(exercises.get(0), dao).execute();
            }
        });
    }

    private static void doOnResponse(Call<Void> call, Response<Void> response, View view, FragmentActivity activity, ProgressBar progressBar, List<Exercise> exercises) {
        if (response.isSuccessful()) {
            if(response.code() == 200) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view.findViewById(R.id.final_score_fragment_coordinator_layout), EXERCISE_SAVED_MESSAGE, Snackbar.LENGTH_SHORT).show();
                final ExerciseViewModel exerciseViewModel = new ViewModelProvider(activity).get(ExerciseViewModel.class);
                exerciseViewModel.reset();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            configureErrorSnackbar(view, call, activity, progressBar, exercises);
            final ExerciseDao dao = EtqDatabase.getInstance(activity).getExercisesDao();
            new SaveExerciseTask(exercises.get(0), dao).execute();
        }
    }

    private static void configureErrorSnackbar(View view, Call<Void> call, FragmentActivity activity, ProgressBar progressBar, List<Exercise> exercises) {
        final BaseTransientBottomBar.Behavior behavior = new BaseTransientBottomBar.Behavior();
        behavior.setSwipeDirection(BaseTransientBottomBar.Behavior.SWIPE_DIRECTION_ANY);
        Snackbar snackbar = Snackbar.make(
                view.findViewById(R.id.final_score_fragment_coordinator_layout),
                EXERCISE_SAVE_ERROR_MESSAGE,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setBehavior(behavior);
        snackbar.setAction(RETRY_ACTION_LABEL,
                new RetryListener(call, activity, view, progressBar, exercises));
        snackbar.setActionTextColor(ContextCompat.getColor(activity,
                R.color.colorPrimary));
        snackbar.show();
    }

    static class RetryListener implements View.OnClickListener {

        private final Call<Void> call;
        private final FragmentActivity activity;
        private final View view;
        private final ProgressBar progressBar;
        private final List<Exercise> exercises;

        public RetryListener(Call<Void> call, FragmentActivity activity, View view, ProgressBar progressBar, List<Exercise> exercises) {
            this.call = call;
            this.activity = activity;
            this.view = view;
            this.progressBar = progressBar;
            this.exercises = exercises;
        }

        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            call.clone().enqueue(new Callback<Void>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Void> call, Response<Void> response) {
                    doOnResponse(call, response, view, activity, progressBar, exercises);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<Void> call, Throwable t) {
                    configureErrorSnackbar(view, call, activity, progressBar, exercises);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}