package br.com.hebaja.englishtrainingquizzes;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import br.com.hebaja.englishtrainingquizzes.ui.fragment.AveragesFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FeedbackFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections;

public class OptionsMenuConfigure {

    private final NavController controller;
    private final MenuItem item;

    public OptionsMenuConfigure(@NonNull MenuItem item, NavController controller) {
        this.controller = controller;
        this.item = item;
    }

    public void configureOptionsMenuItems() {
        NavDirections directions;

        if (item.getItemId() == R.id.menu_final_score_feedback) {
            directions = FeedbackFragmentDirections.actionGlobalFeedback();
            controller.navigate(directions);
        }
        if (item.getItemId() == R.id.menu_final_score_about) {
            directions = FinalScoreFragmentDirections.actionGlobalAboutPage();
            controller.navigate(directions);
        }
        if (item.getItemId() == R.id.menu_averages) {
            directions = AveragesFragmentDirections.actionGlobalAverages();
            controller.navigate(directions);
        }
    }
}
