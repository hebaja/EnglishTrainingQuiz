package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.QuitAppDialog;

import static br.com.hebaja.englishtrainingquizzes.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.INVALID_NUMBER;
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

        buttonQuit.setOnClickListener(view -> finish());

        buttonTryAgain.setOnClickListener(view -> {
            Intent intent1 = new Intent(FinalScoreActivity.this, QuizActivity.class);
            intent1.putExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, mainActivityChosenOption);
            intent1.putExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, mainActivityChosenLevel);
            startActivity(intent1);
            finish();
        });

        buttonBackToMainMenu.setOnClickListener(view -> {
            Intent intent12 = new Intent(FinalScoreActivity.this, MenuActivity.class);
            startActivity(intent12);
            finish();
        });

        configureBackPressedCallback();
    }

    private void configureBackPressedCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                QuitAppDialog quitAppDialog = new QuitAppDialog(FinalScoreActivity.this);
                quitAppDialog.show(getSupportFragmentManager(), "quit_app");
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