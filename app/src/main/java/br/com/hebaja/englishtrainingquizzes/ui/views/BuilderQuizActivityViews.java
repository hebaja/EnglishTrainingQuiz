package br.com.hebaja.englishtrainingquizzes.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Question;
import br.com.hebaja.englishtrainingquizzes.ui.activity.FinalScoreActivity;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.ConfirmActionDialog;

import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.FINAL_SCORE;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.NEXT_QUESTION;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_A;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_B;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_C;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.RIGHT_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.Constants.SCORE_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.WRONG_ANSWER;

public class BuilderQuizActivityViews {

    private final List<Question> questions;

    private final Context context;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonBackToMainMenu;
    private Button buttonQuit;
    private CardView buttonNext;
    private TextView buttonNextTextView;
    private View chosenOptionWarning;
    private ImageView chosenOptionWarningView;
    private TextView chosenOptionWarningTextView;

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private String rightAnswer;

    private int score = 0;

    private int position;

    private int chosenOption;
    private int chosenLevel;

    public BuilderQuizActivityViews(List<Question> questions, Context context, int chosenOptionMenuActivity, int chosenLevelMenuActivity) {
        this.questions = questions;
        this.context = context;
        this.chosenOption = chosenOptionMenuActivity;
        this.chosenLevel = chosenLevelMenuActivity;
    }

    public void initializeViews() {
        textViewQuestion = ((Activity) context).findViewById(R.id.id_question);
        scoreView = ((Activity) context).findViewById(R.id.score_counter);
        buttonOptionA = ((Activity) context).findViewById(R.id.button_option_a);
        buttonOptionB = ((Activity) context).findViewById(R.id.button_option_b);
        buttonOptionC = ((Activity) context).findViewById(R.id.button_option_c);
        buttonQuit = ((Activity) context).findViewById(R.id.button_quit_quiz_activity);
        buttonNext = ((Activity) context).findViewById(R.id.next_clickable_cardview);
        chosenOptionWarning = ((Activity) context).findViewById(R.id.chosen_option_warning);
        chosenOptionWarningView = ((Activity) context).findViewById(R.id.chosen_option_warning_view);
        chosenOptionWarningTextView = ((Activity) context).findViewById(R.id.chosen_option_warning_textview);
        buttonNextTextView = ((Activity) context).findViewById(R.id.next_clickable_cardview_textview);
        buttonBackToMainMenu = ((Activity) context).findViewById(R.id.button_back_main_menu_quiz_activity);
    }

    public void setOptionsButtons(FragmentManager supportFragmentManager) {

        buttonOptionA.setOnClickListener(view -> configureButtonOption(optionPositionA));

        buttonOptionB.setOnClickListener(view -> configureButtonOption(optionPositionB));

        buttonOptionC.setOnClickListener(view -> configureButtonOption(optionPositionC));

        buttonBackToMainMenu.setOnClickListener(view -> {
            new ConfirmActionDialog(
                    GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT,
                    GO_TO_MAIN_MENU_ANSWER_CONSTANT,
                    CANCEL_ANSWER_CONSTANT,
                    (Activity) context)
                    .show(supportFragmentManager, "go_to_main_menu");
        });

        buttonQuit.setOnClickListener(view -> {
                    new ConfirmActionDialog(QUIT_DIALOG_QUESTION_CONSTANT,
                    QUIT_ANSWER_CONSTANT,
                    CANCEL_ANSWER_CONSTANT,
                    (Activity) context)
                    .show(supportFragmentManager, "quit_app");
        });

        buttonNext.setOnClickListener(view -> {
            updateMainActivityOrGoToFinalActivity();
            enableAllButtons();
            buttonNext.setVisibility(View.INVISIBLE);
            chosenOptionWarning.setVisibility(View.INVISIBLE);
        });
    }

    private void configureButtonOption(String chosenOptionButton) {
        if (chosenOptionButton.equals(rightAnswer)) {
            doWhenChosenOptionIsCorrect();
        } else {
            doWhenChosenOptionIsWrong();
        }
        configureNextButton(buttonNext, buttonNextTextView);
    }

    private void configureNextButton(CardView buttonNext, TextView buttonNextTextView) {
        buttonNext.setTag(R.drawable.next_circle_outline);
        if (position < 10) {
            buttonNextTextView.setText(NEXT_QUESTION);
        } else {
            buttonNextTextView.setText(FINAL_SCORE);
        }
    }

    private void doWhenChosenOptionIsCorrect() {
        score++;
        position++;
        updateScore(score);
        buttonNext.setVisibility(View.VISIBLE);
        disableAllButtonsAndSetNextButtonTag();
        chosenOptionWarningView.setImageResource(R.drawable.right_answer_circle_outline);
        chosenOptionWarningView.setTag(R.drawable.right_answer_circle_outline);
        chosenOptionWarningTextView.setText(RIGHT_ANSWER);
        setColorOfTextView(R.color.colorGreenRightAnswer);
        chosenOptionWarning.setVisibility(View.VISIBLE);
    }

    private void doWhenChosenOptionIsWrong() {
        position++;
        buttonNext.setVisibility(View.VISIBLE);
        disableAllButtonsAndSetNextButtonTag();
        chosenOptionWarningView.setImageResource(R.drawable.wrong_answer_circle_outline);
        chosenOptionWarningView.setTag(R.drawable.wrong_answer_circle_outline);
        chosenOptionWarningTextView.setText(WRONG_ANSWER);
        setColorOfTextView(R.color.colorRedWrongAnswer);
        chosenOptionWarning.setVisibility(View.VISIBLE);
    }

    private void setColorOfTextView(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chosenOptionWarningTextView.setTextColor(context.getColor(color));
        }
    }

    public void updateScore(int score) {
        scoreView.setText(String.valueOf(score));
    }

    private void updateMainActivityOrGoToFinalActivity() {
        if (questions.size() > position) {
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

    public void updatePosition() {
        rightAnswer = questions.get(position).getRightOption();
    }

    private void goToFinalScoreActivity() {
        disableAllButtonsAndSetNextButtonTag();
        startFinalScoreActivity();
        ((Activity) context).finish();
    }

    private void disableAllButtonsAndSetNextButtonTag() {
        buttonOptionA.setEnabled(false);
        buttonOptionB.setEnabled(false);
        buttonOptionC.setEnabled(false);
    }

    private void enableAllButtons() {
        buttonOptionA.setEnabled(true);
        buttonOptionB.setEnabled(true);
        buttonOptionC.setEnabled(true);
    }

    private void startFinalScoreActivity() {
        Intent intent = new Intent(context, FinalScoreActivity.class);
        intent.putExtra(SCORE_KEY, score);
        intent.putExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, chosenOption);
        intent.putExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, chosenLevel);
        context.startActivity(intent);
    }
}

