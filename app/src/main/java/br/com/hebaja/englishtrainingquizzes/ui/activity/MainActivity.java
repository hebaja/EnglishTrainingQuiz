package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import br.com.hebaja.englishtrainingquizzes.DestinationChangedListener;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;

import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class MainActivity extends AppCompatActivity {

    private OnBackPressedCallback backPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.main_activity);

        NavController navController = Navigation.findNavController(this, R.id.main_activity_nav_host);

        configureBackPressedCallback();

        Button buttonQuit = findViewById(R.id.main_activity_button_quit);
        buttonQuit.setOnClickListener(view -> configureQuitDialog());

        DestinationChangedListener destinationChangedListener = new DestinationChangedListener(
                navController,
                backPressedCallback,
                buttonQuit,
                this);
        destinationChangedListener.configureDestinationChangedListener();
    }

    private void configureBackPressedCallback() {
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                configureQuitDialog();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    private void configureQuitDialog() {
        View view = findViewById(R.id.main_activity_parent).getRootView();
        new AppDialog(this, view, QUIT_DIALOG_QUESTION_CONSTANT, QUIT_ANSWER_CONSTANT, CANCEL_ANSWER_CONSTANT).buildDialog().show();
    }
}