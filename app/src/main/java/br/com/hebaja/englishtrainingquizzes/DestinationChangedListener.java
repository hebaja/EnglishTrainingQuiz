package br.com.hebaja.englishtrainingquizzes;

import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import br.com.hebaja.englishtrainingquizzes.ui.activity.MainActivity;

import static android.view.View.INVISIBLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class DestinationChangedListener {

    private final NavController navController;
    private final OnBackPressedCallback backPressedCallback;
    private final MainActivity mainActivity;
    private final Button buttonQuit;

    public DestinationChangedListener(NavController navController, OnBackPressedCallback backPressedCallback, Button buttonQuit, MainActivity mainActivity) {
        this.navController = navController;
        this.backPressedCallback = backPressedCallback;
        this.mainActivity = mainActivity;
        this.buttonQuit = buttonQuit;
    }

    public void configureDestinationChangedListener() {

        final Animation toBottom = AnimationUtils.loadAnimation(mainActivity, R.anim.to_bottom);
        final Animation fromBottom = AnimationUtils.loadAnimation(mainActivity, R.anim.from_bottom);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if(id == R.id.menuSubjects) {
                configureTitleAndBackCallback(destination, false);
                configureNavigationVisibilityFinalScore(fromBottom);
            }
            if(id == R.id.menuLevels || id == R.id.quiz) {
                configureTitleAndBackCallback(destination, true);
                configureNavigationVisibilityFinalScore(fromBottom);
            }
            if(id == R.id.finalScore) {
                configureNavigationVisibilityFinalScore(fromBottom);
                configureTitleAndBackCallback(destination, true);
            }
            if(id == R.id.login) {
                buttonQuit.setVisibility(INVISIBLE);
            }
            if(id == R.id.feedback || id == R.id.aboutPage || id == R.id.averages) {
                configureTitleAndBackCallback(destination, false);
                buttonQuit.setVisibility(INVISIBLE);
                buttonQuit.startAnimation(toBottom);
            }
        });
    }

    private void configureTitleAndBackCallback(NavDestination destination, boolean b) {
        mainActivity.setTitle(destination.getLabel());
        backPressedCallback.setEnabled(b);
    }

    private void configureNavigationVisibilityFinalScore(Animation fromBottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = mainActivity.getWindow().getDecorView();
            int uiOptions = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            decorView.setSystemUiVisibility(uiOptions);
            if(buttonQuit.getVisibility() == INVISIBLE) {
                buttonQuit.setVisibility(View.VISIBLE);
                buttonQuit.startAnimation(fromBottom);
            }
        }
    }
}