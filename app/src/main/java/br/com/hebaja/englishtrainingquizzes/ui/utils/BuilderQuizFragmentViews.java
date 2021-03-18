package br.com.hebaja.englishtrainingquizzes.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.enums.LevelType;
import br.com.hebaja.englishtrainingquizzes.model.ButtonNext;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.model.WarningView;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter.ButtonOptionListAdapter;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonNextViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonsDisableViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ExerciseViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.PositionViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.WarningViewModel;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.ADD_NO_POINT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.ADD_ONE_POINT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.EMAIL_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.GET_POSITION;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.GO_TO_MAIN_MENU_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.MEDIUM_MODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.RIGHT_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.SHARED_PREFERENCES_KEY;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.UID_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.UPDATE_POSITION;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.USERNAME_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.WRONG_ANSWER;
import static br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections.actionQuizToFinalScore;

public class BuilderQuizFragmentViews {

    private final View view;
    private List<Task> tasks;
    private final FragmentActivity mainActivity;
    private final LifecycleOwner viewLifecycleOwner;
    private ButtonsDisableViewModel buttonsDisableViewModel;
    private TextView textViewQuestion;
    private TextView scoreView;
    private TextView scoreTextView;
    private Button buttonBackToMainMenu;
    private ButtonNext buttonNext = new ButtonNext();
    private WarningView chosenOptionWarning = new WarningView();
    private int score = 0;
    private int position;
    private final String subjectPrompt;
    private final String fileName;
    private LevelType levelType;
    private final int chosenLevel;

    public BuilderQuizFragmentViews(View view,
                                    FragmentActivity activity,
                                    LifecycleOwner viewLifecycleOwner,
                                    String subjectPrompt,
                                    String fileName,
                                    int chosenLevel) {
        this.view = view;
        this.mainActivity = activity;
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.subjectPrompt = subjectPrompt;
        this.fileName = fileName;
        this.chosenLevel = chosenLevel;
    }

    public void configureTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void initializeViews() {
        buttonsDisableViewModel = new ViewModelProvider(mainActivity).get(ButtonsDisableViewModel.class);
        textViewQuestion = view.findViewById(R.id.quiz_question_prompt);
        scoreView = view.findViewById(R.id.quiz_score_counter);
        scoreTextView = view.findViewById(R.id.quiz_score);
        buttonBackToMainMenu = view.findViewById(R.id.quiz_button_back_to_menu);
        buttonNext.setCardView(view.findViewById(R.id.quiz_next_clickable_cardview));
        buttonNext.setTextView(view.findViewById(R.id.quiz_next_clickable_cardview_textview));
        chosenOptionWarning.setCardView(view.findViewById(R.id.quiz_chosen_option_warning));
        chosenOptionWarning.setImageView(view.findViewById(R.id.quiz_chosen_option_warning_view));
        chosenOptionWarning.setTextView(view.findViewById(R.id.quiz_chosen_option_warning_textview));
        fetchScore(ADD_NO_POINT);
        fetchPosition(GET_POSITION);
        configureButtonNextViewModel();
        configureWarningViewModel();
    }

    private void configureWarningViewModel() {
        WarningViewModel warningViewModel = new ViewModelProvider(mainActivity).get(WarningViewModel.class);
        warningViewModel.getState().observe(viewLifecycleOwner, chosenOptionWarningState -> {
            if (!(chosenOptionWarningState == null)) {
                chosenOptionWarning.getImageView().setImageResource(chosenOptionWarningState.getImageRes());
                chosenOptionWarning.getImageView().setTag(chosenOptionWarningState.getImageView().getTag());
                chosenOptionWarning.getTextView().setText(chosenOptionWarningState.getTextView().getText());
                chosenOptionWarning.getCardView().setVisibility(chosenOptionWarningState.getCardView().getVisibility());
                chosenOptionWarning.getTextView().setTextColor(chosenOptionWarningState.getTextView().getCurrentTextColor());
            }
        });
    }

    private void configureButtonNextViewModel() {
        ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(mainActivity).get(ButtonNextViewModel.class);
        buttonNextViewModel.getState().observe(viewLifecycleOwner, buttonNextState -> {
            if (!(buttonNextState == null)) {
                buttonNext.getCardView().setVisibility(buttonNextState.getCardView().getVisibility());
                buttonNext.getTextView().setText(buttonNextState.getTextView().getText());
            }
        });
    }

    public void setButtons(int chosenLevel, int chosenSubject) {
        buttonBackToMainMenu.setOnClickListener(v -> {
            AlertDialog alertDialog = new AppDialog(
                    GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT,
                    GO_TO_MAIN_MENU_ANSWER_CONSTANT,
                    CANCEL_ANSWER_CONSTANT, () -> {
                NavController controller = Navigation.findNavController(view);
                NavDirections directions = QuizFragmentDirections.actionGlobalMenuLevels();
                controller.navigate(directions);

                PositionViewModel positionViewModel = new ViewModelProvider(mainActivity).get(PositionViewModel.class);
                positionViewModel.reset();

                ScoreViewModel scoreViewModel = new ViewModelProvider(mainActivity).get(ScoreViewModel.class);
                scoreViewModel.reset();

                WarningViewModel warningViewModel = new ViewModelProvider(mainActivity).get(WarningViewModel.class);
                warningViewModel.reset();

                ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(mainActivity).get(ButtonNextViewModel.class);
                buttonNextViewModel.reset();

                buttonsDisableViewModel.reset();

            }).buildDialog(mainActivity);
            alertDialog.show();
        });

        buttonNext.getCardView().setOnClickListener(v -> {
            buttonsDisableViewModel.reset();
            fetchPosition(UPDATE_POSITION);
            updateQuizFragmentOrGoToFinalScoreFragment(view, chosenLevel, chosenSubject);

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

    private void configureButtonOption(int chosenOption, int rightOption) {
        if (chosenOption == rightOption) {
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
        fetchOptionsWarningView(R.drawable.right_answer_circle_outline, R.drawable.right_answer_circle_outline, RIGHT_ANSWER, R.color.colorGreenRightAnswer);
    }

    private void doWhenChosenOptionIsWrong() {
        fetchScore(ADD_NO_POINT);
        buttonNext.getCardView().setVisibility(View.VISIBLE);
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
        scoreViewModel.request(response).observe(viewLifecycleOwner, scoreState -> this.score = scoreState);
    }

    private void fetchPosition(int response) {
        PositionViewModel positionViewModel = new ViewModelProvider(mainActivity).get(PositionViewModel.class);
        positionViewModel.getPosition(response).observe(viewLifecycleOwner, positionState -> this.position = positionState);
    }

    private void updateScore(int score) {
        scoreView.setText(String.valueOf(score));
    }

    private void updateQuizFragmentOrGoToFinalScoreFragment(View view, int chosenLevel, int chosenSubject) {
        if (tasks.size() > position) {
            updateTaskViews();
        } else {
            goToFinalScoreFragment(view, chosenLevel, chosenSubject);
        }
    }

    public void updateTaskViews() {
        textViewQuestion.setText(tasks.get(position).getPrompt());
        final int rightOption = tasks.get(position).getRightOption();
        final List<Task.Option> options = tasks.get(position).getOptions();
        final RecyclerView buttonOptionsListRecycleView = view.findViewById(R.id.quiz_button_option_recyclerview);
        buttonOptionsListRecycleView.setAdapter(new ButtonOptionListAdapter(
                mainActivity,
                options,
                buttonsDisableViewModel,
                viewLifecycleOwner,
                (chosenOption) -> configureButtonOption(chosenOption, rightOption)));
        updateScore(score);
    }

    private void goToFinalScoreFragment(View view, int chosenLevel, int chosenSubject) {
        NavController controller = Navigation.findNavController(view);
        QuizFragmentDirections.ActionQuizToFinalScore directions = actionQuizToFinalScore(score, chosenLevel, chosenSubject, subjectPrompt, fileName);
        controller.navigate(directions);
        User user = fetchCurrentUser();
        defineLevel();
        final long id = fetchExerciseId();
        final Exercise exercise = new Exercise(id, subjectPrompt, levelType, score, user.getUid());
        final ExerciseViewModel exerciseViewModel = new ViewModelProvider(mainActivity).get(ExerciseViewModel.class);
        exerciseViewModel.registerExercise(exercise);
        resetViewStates();
    }

    private long fetchExerciseId() {
        long leftLimit = 1L;
        long rightLimit = 100000000L;
        return new RandomDataGenerator().nextLong(leftLimit, rightLimit);
    }

    @NotNull
    private User fetchCurrentUser() {
        GoogleSignInAccount currentGoogleAccount = GoogleSignIn.getLastSignedInAccount(mainActivity);
        Profile currentFacebookProfile = Profile.getCurrentProfile();
        return setUser(currentGoogleAccount, currentFacebookProfile);
    }

    private User setUser(GoogleSignInAccount currentGoogleAccount, Profile currentFacebookProfile) {
        User user = new User();
        if (currentGoogleAccount != null) {
            user.setUid(Objects.requireNonNull(currentGoogleAccount.getId()));
            user.setUsername(currentGoogleAccount.getGivenName());
            user.setEmail(currentGoogleAccount.getEmail());
        } else if (currentFacebookProfile != null) {
            user.setUid(currentFacebookProfile.getId());
            user.setUsername(currentFacebookProfile.getFirstName());
        } else {
            final SharedPreferences preferences = mainActivity.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            final String email = preferences.getString(EMAIL_RECEIVED, null);
            final String username = preferences.getString(USERNAME_RECEIVED, null);
            final String uid = preferences.getString(UID_RECEIVED, null);
            user.setEmail(email);
            user.setUsername(username);
            user.setUid(uid);
        }
        return user;
    }

    private void resetViewStates() {
        resetScoreState();
        resetPositionState();
    }

    private void resetPositionState() {
        PositionViewModel positionViewModel = new ViewModelProvider(mainActivity).get(PositionViewModel.class);
        positionViewModel.reset();
        position = 0;
    }

    private void resetScoreState() {
        ScoreViewModel scoreViewModel = new ViewModelProvider(mainActivity).get(ScoreViewModel.class);
        scoreViewModel.reset();
        score = 0;
    }

    public void hideViews() {
        textViewQuestion.setVisibility(View.INVISIBLE);
        scoreView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
    }

    public void showViews() {
        textViewQuestion.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
    }

    public void setOnlyBackToMainMenuButton() {
        buttonBackToMainMenu.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(view);
            NavDirections directions = QuizFragmentDirections.actionGlobalMenuLevels();
            controller.navigate(directions);
        });
    }

    private void defineLevel() {
        switch (chosenLevel) {
            case EASY_MODE:
                levelType = LevelType.EASY;
                break;
            case MEDIUM_MODE:
                levelType = LevelType.MEDIUM;
                break;
            case HARD_MODE:
                levelType = LevelType.HARD;
                break;
        }
    }
}