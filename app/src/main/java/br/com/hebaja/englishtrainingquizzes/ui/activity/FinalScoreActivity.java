package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.ConfirmActionDialog;

import static br.com.hebaja.englishtrainingquizzes.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.INVALID_NUMBER;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.SCORE_KEY;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        setTitle(APPBAR_TITLE);

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra(SCORE_KEY, INVALID_NUMBER);
        int mainActivityChosenOption = intent.getIntExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, INVALID_NUMBER);
        int mainActivityChosenLevel = intent.getIntExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, INVALID_NUMBER);

        TextView scoreView = findViewById(R.id.final_score_activity_score_counter);
        scoreView.setText(String.valueOf(finalScore));

        Button buttonQuit = findViewById(R.id.final_score_activity_button_quit);
        Button buttonTryAgain = findViewById(R.id.final_score_activity_button_try_again);
        Button buttonBackToMainMenu = findViewById(R.id.final_score_activity_button_back_main_menu);

        buttonQuit.setOnClickListener(view -> {
                new ConfirmActionDialog(QUIT_DIALOG_QUESTION_CONSTANT,
                        QUIT_ANSWER_CONSTANT,
                        CANCEL_ANSWER_CONSTANT,
                        FinalScoreActivity.this)
                        .show(getSupportFragmentManager(), "quit_app");
        });

        buttonTryAgain.setOnClickListener(view -> {
            Intent tryAgainIntent = new Intent(FinalScoreActivity.this, QuizActivity.class);
            tryAgainIntent.putExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, mainActivityChosenOption);
            tryAgainIntent.putExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, mainActivityChosenLevel);
            startActivity(tryAgainIntent);
            finish();
        });

        buttonBackToMainMenu.setOnClickListener(view -> {
            startActivity(new Intent(FinalScoreActivity.this, MenuActivity.class));
            finish();
        });

        configureBackPressedCallback();
    }

    private void configureBackPressedCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new ConfirmActionDialog(
                        QUIT_DIALOG_QUESTION_CONSTANT,
                        QUIT_ANSWER_CONSTANT,
                        CANCEL_ANSWER_CONSTANT,
                        FinalScoreActivity.this)
                        .show(getSupportFragmentManager(), "quit_app");
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_final_score_access_feedback_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent iniciaFeedbackActivity = new Intent(FinalScoreActivity.this, FeedbackActivity.class);
        startActivity(iniciaFeedbackActivity);
        return super.onOptionsItemSelected(item);
    }
}