package br.com.hebaja.phrasalverbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.phrasalverbs.R;

import static br.com.hebaja.phrasalverbs.ui.activity.Constants.APPBAR_TITLE;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.SCORE_KEY;

public class FinalScoreActivity extends AppCompatActivity {

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        setTitle(APPBAR_TITLE);

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra(SCORE_KEY, this.score);

        TextView scoreView = findViewById(R.id.final_activity_score_counter);
        scoreView.setText(String.valueOf(finalScore));

        Button buttonQuit = findViewById(R.id.button_final_activity_quit);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}