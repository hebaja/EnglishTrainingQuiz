package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import br.com.hebaja.englishtrainingquizzes.OptionsMenuConfigure;
import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections.*;

public class FinalScoreFragment extends Fragment {

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

        assert getArguments() != null;
        finalScore.setText(String.valueOf(FinalScoreFragmentArgs.fromBundle(getArguments()).getScore()));

        int chosenLevel = FinalScoreFragmentArgs.fromBundle(getArguments()).getChosenLevel();
        int chosenSubject = FinalScoreFragmentArgs.fromBundle(getArguments()).getChosenOption();

        controller = Navigation.findNavController(view);

        buttonBackToMenu.setOnClickListener(v -> {
            NavDirections directions = actionGlobalMenuLevels();
            controller.navigate(directions);
        });

        buttonTryAgain.setOnClickListener(v -> {
            ActionFinalScoreToQuiz directions = actionFinalScoreToQuiz(chosenSubject, chosenLevel);
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

        OptionsMenuConfigure optionsMenuConfigure = new OptionsMenuConfigure(item, controller);
        optionsMenuConfigure.configureOptionsMenuItems();

        return super.onOptionsItemSelected(item);
    }

//    private void configureOptionsMenuItems(@NonNull MenuItem item) {
//        NavDirections directions;
//
//        if(item.getItemId() == R.id.menu_final_score_feedback) {
//            directions = actionFinalScoreToFeedback();
//            controller.navigate(directions);
//        }
//        if(item.getItemId() == R.id.menu_final_score_about) {
//            directions = FinalScoreFragmentDirections.actionFinalScoreToAboutPage();
//            controller.navigate(directions);
//        }
//        if(item.getItemId() == R.id.menu_averages) {
//            directions = actionFinalScoreToAverages();
//            controller.navigate(directions);
//
//        }
//    }
}