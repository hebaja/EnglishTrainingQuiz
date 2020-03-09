package br.com.hebaja.phrasalverbs.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hebaja.phrasalverbs.R;

public class FinalScoreActivity extends AppCompatActivity {

    public static final String APPBAR_TITLE = "Phrasal Verbs Quiz";
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        setTitle(APPBAR_TITLE);

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra("score", this.score);

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