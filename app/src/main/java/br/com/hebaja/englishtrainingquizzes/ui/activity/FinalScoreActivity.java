package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.SCORE_KEY;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        setTitle(APPBAR_TITLE);

        int score = -1;
        int chosenOption = -1;

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra(SCORE_KEY, score);
        int mainActivityChosenOption = intent.getIntExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, chosenOption);

        TextView scoreView = findViewById(R.id.final_score_activity_score_counter);
        scoreView.setText(String.valueOf(finalScore));

        Button buttonQuit = findViewById(R.id.final_score_activity_button_quit);
        Button buttonTryAgain = findViewById(R.id.final_score_activity_button_try_again);
        Button buttonBackToMainMenu = findViewById(R.id.final_score_activity_button_back_main_menu);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalScoreActivity.this, QuizActivity.class);
                intent.putExtra("chosen_option_try_again", mainActivityChosenOption);
                startActivity(intent);
                finish();
            }
        });

        buttonBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalScoreActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
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