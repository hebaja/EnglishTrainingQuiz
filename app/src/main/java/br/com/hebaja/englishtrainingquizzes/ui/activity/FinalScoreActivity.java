package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        TextView scoreView = findViewById(R.id.final_activity_score_counter);
        scoreView.setText(String.valueOf(finalScore));

        Button buttonQuit = findViewById(R.id.button_final_activity_quit);
        Button buttonTryAgain = findViewById(R.id.button_final_activity_try_again);

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

    }
}