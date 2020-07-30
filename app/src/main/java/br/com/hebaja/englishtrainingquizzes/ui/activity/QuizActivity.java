package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.QuestionObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Question;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.ConfirmActionDialog;
import br.com.hebaja.englishtrainingquizzes.ui.views.BuilderQuizActivityViews;

import static br.com.hebaja.englishtrainingquizzes.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.INVALID_NUMBER;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.STATE_POSITION;
import static br.com.hebaja.englishtrainingquizzes.Constants.STATE_SCORE;

public class QuizActivity extends AppCompatActivity {

    List<Question> questions = new ArrayList<>();

    private int score = Integer.MIN_VALUE;

    private int position;
    private BuilderQuizActivityViews builderQuizActivityViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);

        Intent intent = getIntent();
        int chosenOptionMenuActivity;
        int chosenLevelMenuActivity;

        if(intent.hasExtra(CHOSEN_OPTION_KEY)) {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_KEY, INVALID_NUMBER);
        } else {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, INVALID_NUMBER);
        }

        if(intent.hasExtra(CHOSEN_LEVEL_KEY)) {
            chosenLevelMenuActivity = intent.getIntExtra(CHOSEN_LEVEL_KEY, INVALID_NUMBER);
        } else {
            chosenLevelMenuActivity = intent.getIntExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, INVALID_NUMBER);
        }

        questions = new QuestionObjectsGenerator(chosenLevelMenuActivity, chosenOptionMenuActivity, this).generateQuestionObjectsFromJsonFile();

        builderQuizActivityViews = new BuilderQuizActivityViews(questions, this, chosenOptionMenuActivity, chosenLevelMenuActivity);
        builderQuizActivityViews.initializeViews();
        builderQuizActivityViews.setOptionsButtons(getSupportFragmentManager());
        builderQuizActivityViews.updatePosition();
        builderQuizActivityViews.updateViewsQuestions();

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
                        QuizActivity.this)
                        .show(getSupportFragmentManager(), "quit_app");
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
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