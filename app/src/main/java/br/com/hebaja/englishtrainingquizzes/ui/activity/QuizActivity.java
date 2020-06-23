package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.QuestionObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.model.Question;
import br.com.hebaja.englishtrainingquizzes.ui.views.BuilderQuizActivityViews;

import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.INVALID_NUMBER;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.STATE_POSITION;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.STATE_SCORE;

public class QuizActivity extends AppCompatActivity {

    List<Question> questions = new ArrayList<>();

    private int score = 0;

    private int position;
    private BuilderQuizActivityViews builderQuizActivityViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);

        Intent intent = getIntent();
        int chosenOptionMenuActivity;

        if(intent.hasExtra(CHOSEN_OPTION_KEY)) {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_KEY, INVALID_NUMBER);
        } else {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, INVALID_NUMBER);
        }

        questions = new QuestionObjectsGenerator(chosenOptionMenuActivity, this).generateQuestionObjectsFromJsonFile();

        builderQuizActivityViews = new BuilderQuizActivityViews(questions, this, chosenOptionMenuActivity);
        builderQuizActivityViews.initializeViews();
        builderQuizActivityViews.setOptionsButtons(getSupportFragmentManager());
        builderQuizActivityViews.updatePosition();
        builderQuizActivityViews.updateViewsQuestions();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SCORE, score);
        outState.putInt(STATE_POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        score = savedInstanceState.getInt(STATE_SCORE);
        builderQuizActivityViews.updateScore(score);

        position = savedInstanceState.getInt(STATE_POSITION);
        builderQuizActivityViews.updateViewsQuestions();
    }
}