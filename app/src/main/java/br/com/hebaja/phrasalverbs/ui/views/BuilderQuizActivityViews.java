package br.com.hebaja.phrasalverbs.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.model.Question;
import br.com.hebaja.phrasalverbs.ui.activity.FinalScoreActivity;
import br.com.hebaja.phrasalverbs.ui.activity.QuizActivity;


public class BuilderQuizActivityViews {

    private final ArrayList<Question> questions;

    private final Context context;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonQuit;

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private String rightAnswer;

    private int score = 0;

    private int position;
    private int positionRestored = 0;

    private Activity mainActivity = QuizActivity.mainActivity;

    public BuilderQuizActivityViews(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    public void initializeViews() {
        textViewQuestion = mainActivity.findViewById(R.id.id_question);
        scoreView = mainActivity.findViewById(R.id.score_counter);
        buttonOptionA = mainActivity.findViewById(R.id.button_option_a);
        buttonOptionB = mainActivity.findViewById(R.id.button_option_b);
        buttonOptionC = mainActivity.findViewById(R.id.button_option_c);
        buttonQuit = mainActivity.findViewById(R.id.button_quit);
    }

    public void setOptionsButtons() {

        buttonOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionA.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionB.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionC.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.finish();
            }
        });
    }

    public void updateScore(int score) {
        Log.i("score", "updateScore: " + score);
        Log.i("score", "updateScore: " + scoreView);
        scoreView.setText(String.valueOf(score));
    }

    private void updateMainActivityOrGoToFinalActivity() {
        if(questions.size() > position) {
            updateViewsQuestions();
            updatePosition();
        } else {
            goToFinalScoreActivity();
        }
    }

    public void updateViewsQuestions() {
        textViewQuestion.setText(questions.get(position).getPrompt());
        buttonOptionA.setText(questions.get(position).getOptionA());
        buttonOptionB.setText(questions.get(position).getOptionB());
        buttonOptionC.setText(questions.get(position).getOptionC());

        optionPositionA = "a";
        optionPositionB = "b";
        optionPositionC = "c";

        updateScore(score);
    }

    public void updatePosition () {
        position = position + positionRestored;
        rightAnswer = questions.get(position).getRightOption();
    }

    private void goToFinalScoreActivity(){
        disableAllButtons();
        startFinalScoreActivity();
        QuizActivity.mainActivity.finish();
    }

    private void disableAllButtons(){
        buttonOptionA.setEnabled(false);
        buttonOptionB.setEnabled(false);
        buttonOptionC.setEnabled(false);
        buttonQuit.setEnabled(false);
    }

    private void startFinalScoreActivity() {
        Intent intent = new Intent(context, FinalScoreActivity.class);
        intent.putExtra("score", score);
        context.startActivity(intent);
    }
}

