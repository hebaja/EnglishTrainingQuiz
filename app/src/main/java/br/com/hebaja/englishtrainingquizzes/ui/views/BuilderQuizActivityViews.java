package br.com.hebaja.englishtrainingquizzes.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Question;
import br.com.hebaja.englishtrainingquizzes.ui.activity.FinalScoreActivity;
import br.com.hebaja.englishtrainingquizzes.ui.activity.QuizActivity;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.QuitAppDialog;

import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.OPTION_POSITION_A;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.OPTION_POSITION_B;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.OPTION_POSITION_C;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.SCORE_KEY;

public class BuilderQuizActivityViews {

    //    private final ArrayList<Question> questions;
    private final List<Question> questions;

    private final Context context;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonQuit;
    private View buttonNext;
    private View wrongOptionWarning;
    private View rightOptionWarning;

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private String rightAnswer;

    private int score = 0;

    private int position;

    private Activity mainActivity = QuizActivity.mainActivity;

    private int chosenOption;

    public BuilderQuizActivityViews(List<Question> questions, Context context, int chosenOptionMainActivity) {
        this.questions = questions;
        this.context = context;
        this.chosenOption = chosenOptionMainActivity;
    }

    public void initializeViews() {
        textViewQuestion = mainActivity.findViewById(R.id.id_question);
        scoreView = mainActivity.findViewById(R.id.score_counter);
        buttonOptionA = mainActivity.findViewById(R.id.button_option_a);
        buttonOptionB = mainActivity.findViewById(R.id.button_option_b);
        buttonOptionC = mainActivity.findViewById(R.id.button_option_c);
        buttonQuit = mainActivity.findViewById(R.id.button_quit_quiz_activity);
        buttonNext = mainActivity.findViewById(R.id.next_green);
        wrongOptionWarning = mainActivity.findViewById(R.id.wrong_option_red);
        rightOptionWarning = mainActivity.findViewById(R.id.right_option_green);
    }

    public void setOptionsButtons(FragmentManager supportFragmentManager) {

        buttonOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionA.equals(rightAnswer)) {
                    doWhenChosenOptionIsCorrect();
                } else {
                    doWhenChosenOptionIsWrong();
                }
            }
        });

        buttonOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionB.equals(rightAnswer)) {
                    doWhenChosenOptionIsCorrect();
                } else {
                    doWhenChosenOptionIsWrong();
                }
            }
        });

        buttonOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionC.equals(rightAnswer)) {
                    doWhenChosenOptionIsCorrect();
                } else {
                    doWhenChosenOptionIsWrong();
                }
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuitAppDialog quitAppDialog = new QuitAppDialog(mainActivity);
                quitAppDialog.show(supportFragmentManager, "quit_app");
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMainActivityOrGoToFinalActivity();
                enableAllButtons();
                buttonNext.setVisibility(View.INVISIBLE);
                wrongOptionWarning.setVisibility(View.INVISIBLE);
                rightOptionWarning.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void doWhenChosenOptionIsCorrect() {
        score++;
        position++;
        updateScore(score);
        buttonNext.setVisibility(View.VISIBLE);
        disableAllButtons();
        rightOptionWarning.setVisibility(View.VISIBLE);
    }

    private void doWhenChosenOptionIsWrong() {
        position++;
        buttonNext.setVisibility(View.VISIBLE);
        disableAllButtons();
        wrongOptionWarning.setVisibility(View.VISIBLE);
        buttonQuit.setEnabled(true);
    }

    public void updateScore(int score) {
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

        optionPositionA = OPTION_POSITION_A;
        optionPositionB = OPTION_POSITION_B;
        optionPositionC = OPTION_POSITION_C;

        updateScore(score);
    }

    public void updatePosition () {
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
    }

    private void enableAllButtons(){
        buttonOptionA.setEnabled(true);
        buttonOptionB.setEnabled(true);
        buttonOptionC.setEnabled(true);
    }

    private void startFinalScoreActivity() {
        Intent intent = new Intent(context, FinalScoreActivity.class);
        intent.putExtra(SCORE_KEY, score);
        intent.putExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, chosenOption);
        context.startActivity(intent);
    }
}

