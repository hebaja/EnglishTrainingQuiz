package br.com.hebaja.englishtrainingquizzes.ui.views;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.ButtonNext;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.model.WarningView;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonNextViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.PositionViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.WarningViewModel;

import static br.com.hebaja.englishtrainingquizzes.Constants.ADD_NO_POINT;
import static br.com.hebaja.englishtrainingquizzes.Constants.ADD_ONE_POINT;
import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.GET_POSITION;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_A;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_B;
import static br.com.hebaja.englishtrainingquizzes.Constants.OPTION_POSITION_C;
import static br.com.hebaja.englishtrainingquizzes.Constants.RIGHT_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.Constants.UPDATE_POSITION;
import static br.com.hebaja.englishtrainingquizzes.Constants.WRONG_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections.actionQuizToFinalScore;

public class BuilderQuizFragmentViews {

    private final List<Task> tasks;
    private final FragmentActivity mainActivity;
    private final LifecycleOwner viewLifecycleOwner;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonBackToMainMenu;

    private ButtonNext buttonNext = new ButtonNext();

    private WarningView chosenOptionWarning = new WarningView();

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private String rightAnswer;

    private int score = 0;

    private int position;

    public BuilderQuizFragmentViews(List<Task> tasks, FragmentActivity activity, LifecycleOwner viewLifecycleOwner) {
        this.tasks = tasks;
        this.mainActivity = activity;
        this.viewLifecycleOwner = viewLifecycleOwner;
    }

    public void initializeViews(@NonNull View view) {
        textViewQuestion = view.findViewById(R.id.quiz_question_prompt);
        scoreView = view.findViewById(R.id.quiz_score_counter);
        buttonOptionA = view.findViewById(R.id.quiz_button_option_a);
        buttonOptionB = view.findViewById(R.id.quiz_button_option_b);
        buttonOptionC = view.findViewById(R.id.quiz_button_option_c);
        buttonBackToMainMenu = view.findViewById(R.id.quiz_button_back_to_menu);

        buttonNext.setCardView(view.findViewById(R.id.quiz_next_clickable_cardview));
        buttonNext.setTextView(view.findViewById(R.id.quiz_next_clickable_cardview_textview));

        chosenOptionWarning.setCardView(view.findViewById(R.id.quiz_chosen_option_warning));
        chosenOptionWarning.setImageView(view.findViewById(R.id.quiz_chosen_option_warning_view));
        chosenOptionWarning.setTextView(view.findViewById(R.id.quiz_chosen_option_warning_textview));

        fetchScore(ADD_NO_POINT);
        fetchPosition(GET_POSITION);

        ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(mainActivity).get(ButtonNextViewModel.class);
        buttonNextViewModel.getState().observe(viewLifecycleOwner, buttonNextState -> {
            if (!(buttonNextState == null)) {
                buttonNext.getCardView().setVisibility(buttonNextState.getCardView().getVisibility());
                buttonNext.getTextView().setText(buttonNextState.getTextView().getText());
            }
        });

        WarningViewModel warningViewModel = new ViewModelProvider(mainActivity).get(WarningViewModel.class);
        warningViewModel.getState().observe(viewLifecycleOwner, chosenOptionWarningState -> {
            if(!(chosenOptionWarningState == null)) {
                chosenOptionWarning.getImageView().setImageResource(chosenOptionWarningState.getImageRes());
                chosenOptionWarning.getImageView().setTag(chosenOptionWarningState.getImageView().getTag());
                chosenOptionWarning.getTextView().setText(chosenOptionWarningState.getTextView().getText());
                chosenOptionWarning.getCardView().setVisibility(chosenOptionWarningState.getCardView().getVisibility());
                chosenOptionWarning.getTextView().setTextColor(chosenOptionWarningState.getTextView().getCurrentTextColor());
            }
        });

        if(buttonNext.getCardView().isShown() && chosenOptionWarning.getCardView().isShown()) {
            disableAllButtons();
        }
    }

    public void setButtons(View view, int chosenLevel, int chosenSubject) {
        buttonOptionA.setOnClickListener(v -> configureButtonOption(optionPositionA));
        buttonOptionB.setOnClickListener(v -> configureButtonOption(optionPositionB));
        buttonOptionC.setOnClickListener(v -> configureButtonOption(optionPositionC));

        buttonBackToMainMenu.setOnClickListener(v -> {
            AlertDialog alertDialog = new AppDialog(mainActivity, view, GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT, GO_TO_MAIN_MENU_ANSWER_CONSTANT, CANCEL_ANSWER_CONSTANT).buildDialog();
            alertDialog.show();
        });

        buttonNext.getCardView().setOnClickListener(v -> {
            fetchPosition(UPDATE_POSITION);
            updateQuizFragmentOrGoToFinalScoreFragment(view, chosenLevel, chosenSubject);
            enableAllButtons();

            ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(mainActivity).get(ButtonNextViewModel.class);
            buttonNextViewModel.setVisibilityState(buttonNext).observe(viewLifecycleOwner, buttonNextState -> {
                buttonNext = buttonNextState;
            });

            WarningViewModel warningViewModel = new ViewModelProvider(mainActivity).get(WarningViewModel.class);
            warningViewModel.setVisibilityState(chosenOptionWarning).observe(viewLifecycleOwner, chosenOptionWarningState -> {
                chosenOptionWarning = chosenOptionWarningState;
            });
        });
    }

    private void configureButtonOption(String chosenOptionButton) {
        if (chosenOptionButton.equals(rightAnswer)) {
            doWhenChosenOptionIsCorrect();
        } else {
            doWhenChosenOptionIsWrong();
        }
        configureNextButton();
    }

    private void configureNextButton() {

        ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(mainActivity).get(ButtonNextViewModel.class);
        buttonNextViewModel.setState(buttonNext, position).observe(viewLifecycleOwner, buttonNextState -> {
            buttonNext = buttonNextState;
        });
    }

    private void doWhenChosenOptionIsCorrect() {
        fetchScore(ADD_ONE_POINT);
        updateScore(score);
        buttonNext.getCardView().setVisibility(View.VISIBLE);
        disableAllButtons();
        fetchOptionsWarningView(R.drawable.right_answer_circle_outline, R.drawable.right_answer_circle_outline, RIGHT_ANSWER, R.color.colorGreenRightAnswer);
    }

    private void doWhenChosenOptionIsWrong() {
        fetchScore(ADD_NO_POINT);
        buttonNext.getCardView().setVisibility(View.VISIBLE);
        disableAllButtons();
        fetchOptionsWarningView(R.drawable.wrong_answer_circle_outline, R.drawable.wrong_answer_circle_outline, WRONG_ANSWER, R.color.colorRedWrongAnswer);
    }

    private void fetchOptionsWarningView(int imageRes, int tag, String answer, int color) {
        WarningViewModel warningViewModel = new ViewModelProvider(mainActivity).get(WarningViewModel.class);
        warningViewModel.setState(chosenOptionWarning, imageRes, tag, answer, color).observe(viewLifecycleOwner, chosenOptionWarningState -> {
            chosenOptionWarning = chosenOptionWarningState;
        });
    }

    private void fetchScore(int response) {
        ScoreViewModel scoreViewModel = new ViewModelProvider(mainActivity).get(ScoreViewModel.class);
        scoreViewModel.request(response).observe(viewLifecycleOwner, scoreState -> {
            this.score = scoreState;
        });
    }

    private void fetchPosition(int response) {
        PositionViewModel positionViewModel = new ViewModelProvider(mainActivity).get(PositionViewModel.class);
        positionViewModel.getPosition(response).observe(viewLifecycleOwner, positionState -> {
            this.position = positionState;
        });
    }

    private void updateScore(int score) {
        scoreView.setText(String.valueOf(score));
    }

    private void updateQuizFragmentOrGoToFinalScoreFragment(View view, int chosenLevel, int chosenSubject) {
        if (tasks.size() > position) {
            updateTaskViews();
            updatePosition();
        } else {
            goToFinalScoreFragment(view, chosenLevel, chosenSubject);
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
        disableAllButtons();
        NavController controller = Navigation.findNavController(view);
        QuizFragmentDirections.ActionQuizToFinalScore directions = actionQuizToFinalScore(score, chosenLevel, chosenSubject);
        controller.navigate(directions);
        resetViewStates();
    }

    private void resetViewStates() {
        resetScoreState();
        resetPositionState();
    }

    private void resetPositionState() {
        PositionViewModel positionViewModel = new ViewModelProvider(mainActivity).get(PositionViewModel.class);
        positionViewModel.reset().observe(viewLifecycleOwner, positionState -> {
            position = positionState;
        });
    }

    private void resetScoreState() {
        ScoreViewModel scoreViewModel = new ViewModelProvider(mainActivity).get(ScoreViewModel.class);
        scoreViewModel.reset().observe(viewLifecycleOwner, scoreState -> {
            score = scoreState;
        });
    }

    private void disableAllButtons() {
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