package br.com.hebaja.englishtrainingquizzes.ui.views;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;

import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.FINAL_SCORE;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.NEXT_QUESTION;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_A;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_B;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_C;
import static br.com.hebaja.englishtrainingquizzes.Constants.RIGHT_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.Constants.WRONG_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections.actionQuizToFinalScore;

public class BuilderQuizFragmentViews {

    private final List<Task> tasks;

    private final FragmentActivity mainActivity;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonBackToMainMenu;
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

    public BuilderQuizFragmentViews(List<Task> tasks, FragmentActivity activity) {
        this.tasks = tasks;
        this.mainActivity = activity;
    }

    public void initializeViews(@NonNull View view) {
        textViewQuestion = view.findViewById(R.id.quiz_question_prompt);
        scoreView = view.findViewById(R.id.quiz_score_counter);
        buttonOptionA = view.findViewById(R.id.quiz_button_option_a);
        buttonOptionB = view.findViewById(R.id.quiz_button_option_b);
        buttonOptionC = view.findViewById(R.id.quiz_button_option_c);
        buttonNext = view.findViewById(R.id.quiz_next_clickable_cardview);
        chosenOptionWarning = view.findViewById(R.id.quiz_chosen_option_warning);
        chosenOptionWarningView = view.findViewById(R.id.quiz_chosen_option_warning_view);
        chosenOptionWarningTextView = view.findViewById(R.id.quiz_chosen_option_warning_textview);
        buttonNextTextView = view.findViewById(R.id.quiz_next_clickable_cardview_textview);
        buttonBackToMainMenu = view.findViewById(R.id.quiz_button_back_to_menu);
    }

    public void setButtons(View view, int chosenLevel) {
        buttonOptionA.setOnClickListener(v -> configureButtonOption(optionPositionA));
        buttonOptionB.setOnClickListener(v -> configureButtonOption(optionPositionB));
        buttonOptionC.setOnClickListener(v -> configureButtonOption(optionPositionC));

        buttonBackToMainMenu.setOnClickListener(v -> {
            AlertDialog alertDialog = new AppDialog(mainActivity, view, GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT, GO_TO_MAIN_MENU_ANSWER_CONSTANT, CANCEL_ANSWER_CONSTANT).buildDialog();
            alertDialog.show();
        });

        buttonNext.setOnClickListener(v -> {
            updateQuizFragmentOrGoToFinalActivity(view, chosenLevel, chosenLevel);
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
            chosenOptionWarningTextView.setTextColor(mainActivity.getColor(color));
        }
    }

    private void updateScore(int score) {
        scoreView.setText(String.valueOf(score));
    }

    private void updateQuizFragmentOrGoToFinalActivity(View view, int chosenLevel, int chosenOption) {
        if (tasks.size() > position) {
            updateTaskViews();
            updatePosition();
        }
        else {
            goToFinalScoreFragment(view, chosenLevel, chosenOption);
        }
    }

    public void updateTaskViews() {
        textViewQuestion.setText(tasks.get(position).getPrompt());
        buttonOptionA.setText(tasks.get(position).getOptionA());
        buttonOptionB.setText(tasks.get(position).getOptionB());
        buttonOptionC.setText(tasks.get(position).getOptionC());

        optionPositionA = OPTION_POSITION_A;
        optionPositionB = OPTION_POSITION_B;
        optionPositionC = OPTION_POSITION_C;

        updateScore(score);
    }

    public void updatePosition() {
        rightAnswer = tasks.get(position).getRightOption();
    }

    private void goToFinalScoreFragment(View view, int chosenLevel, int chosenSubject) {
        disableAllButtonsAndSetNextButtonTag();
        NavController controller = Navigation.findNavController(view);
        QuizFragmentDirections.ActionQuizToFinalScore directions = actionQuizToFinalScore(score, chosenLevel, chosenSubject);
        controller.navigate(directions);
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
}