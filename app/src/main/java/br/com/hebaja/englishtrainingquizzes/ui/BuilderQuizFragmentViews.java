package br.com.hebaja.englishtrainingquizzes.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.enums.LevelType;
import br.com.hebaja.englishtrainingquizzes.model.ButtonNext;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.model.WarningView;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.SaveExerciseService;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonNextViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.EmailRegisterViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.PositionViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.UserViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.WarningViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

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

    public static final String EXERCISE_SAVED_MESSAGE = "Your exercise was successfully saved.";
    public static final String EXERCISE_SAVE_ERROR_MESSAGE = "There was a problem. Your exercise couldn't be saved.";
    private final View view;
    private final List<Task> tasks;
    private final FragmentActivity mainActivity;
    private final LifecycleOwner viewLifecycleOwner;
    private User storedUser;

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
    private final LevelType level;
    private final String subject;

    public BuilderQuizFragmentViews(View view, List<Task> tasks,
                                    FragmentActivity activity,
                                    LifecycleOwner viewLifecycleOwner,
                                    LevelType level,
                                    String subject) {
        this.view = view;
        this.tasks = tasks;
        this.mainActivity = activity;
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.level = level;
        this.subject = subject;
    }

    public void initializeViews() {
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
        configureButtonNextViewModel();
        configureWarningViewModel();
        if(buttonNext.getCardView().isShown() && chosenOptionWarning.getCardView().isShown()) {
            disableAllButtons();
        }
    }

    private void configureWarningViewModel() {
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

        User user = fetchCurrentUser();
        Exercise exercise = new Exercise(user, subject, level, score);
        saveOnApi(exercise);
        resetViewStates();
    }

    @NotNull
    private User fetchCurrentUser() {
        GoogleSignInAccount currentGoogleAccount = GoogleSignIn.getLastSignedInAccount(mainActivity);
        Profile currentFacebookProfile = Profile.getCurrentProfile();
        UserViewModel userViewModel = new ViewModelProvider(mainActivity).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(viewLifecycleOwner, user -> {
            if(user != null) {
                this.storedUser = user;
            }
        });
        User user = new User();
        setUser(currentGoogleAccount, currentFacebookProfile, user);
        return user;
    }

    private void setUser(GoogleSignInAccount currentGoogleAccount, Profile currentFacebookProfile, User user) {
        if(currentGoogleAccount != null) {
            user.setUsername(currentGoogleAccount.getGivenName());
            user.setEmail(currentGoogleAccount.getEmail());
        } else if (currentFacebookProfile != null) {
            user.setUsername(currentFacebookProfile.getFirstName());
            user.setUid(currentFacebookProfile.getId());
        } else if (storedUser != null) {
            user.setUsername(storedUser.getUsername());
            user.setEmail(storedUser.getEmail());
        }
    }

    private void saveOnApi(Exercise exercise) {
        SaveExerciseService service = new BaseRetrofit().getSaveExerciseService();
        Call<Exercise> call = service.register(exercise);
        call.enqueue(new Callback<Exercise>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if(response.isSuccessful()) {
                    Exercise exerciseSaved = response.body();
                    if(exerciseSaved != null) {
                        Snackbar.make(view, EXERCISE_SAVED_MESSAGE, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(view, EXERCISE_SAVE_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Exercise> call, Throwable t) {
                Snackbar.make(view, EXERCISE_SAVE_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        });
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