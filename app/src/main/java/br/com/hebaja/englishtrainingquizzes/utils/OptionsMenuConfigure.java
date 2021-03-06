package br.com.hebaja.englishtrainingquizzes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.AveragesFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FeedbackFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.LoginFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonNextViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.WarningViewModel;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.LOGOFF_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.LOGOFF_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.QUIT_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.SHARED_PREFERENCES_KEY;

public class OptionsMenuConfigure {

    private NavDirections directions;
    private final NavController controller;
    private final MenuItem item;
    private final Context context;

    public OptionsMenuConfigure(@NonNull MenuItem item, NavController controller, Context context) {
        this.controller = controller;
        this.item = item;
        this.context = context;
    }

    public void configureOptionsMenuItems(FragmentActivity activity) {

        if (item.getItemId() == R.id.menu_final_score_feedback) {
            directions = FeedbackFragmentDirections.actionGlobalFeedback();
            controller.navigate(directions);
        }
        if (item.getItemId() == R.id.menu_final_score_about) {
            directions = FinalScoreFragmentDirections.actionGlobalAboutPage();
            controller.navigate(directions);
        }
        if (item.getItemId() == R.id.menu_averages) {
            directions = AveragesFragmentDirections.actionGlobalAverages();
            controller.navigate(directions);
        }
        if (item.getItemId() == R.id.menu_logoff) {
            configureLogOffDialog(activity);
        }
        if (item.getItemId() == R.id.menu_quit_app) {
            configureQuitDialog(activity);
        }
    }

    private void configureLogOffDialog(FragmentActivity activity) {
        new AppDialog(LOGOFF_DIALOG_QUESTION_CONSTANT,
                LOGOFF_ANSWER_CONSTANT,
                CANCEL_ANSWER_CONSTANT,
                () -> executeLogOffActions(activity)).buildDialog(activity).show();
    }

    private void executeLogOffActions(FragmentActivity activity) {
        LoginViewModel loginViewModel = new ViewModelProvider(activity).get(LoginViewModel.class);
        final SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        googleSignOut();
        facebookSignOut();
        loginViewModel.logoff();
        resetViewModels(activity);
        directions = LoginFragmentDirections.actionGlobalLogin();
        controller.navigate(directions);
    }

    private void resetViewModels(FragmentActivity activity) {
        ScoreViewModel scoreViewModel = new ViewModelProvider(activity).get(ScoreViewModel.class);
        scoreViewModel.reset();

        WarningViewModel warningViewModel = new ViewModelProvider(activity).get(WarningViewModel.class);
        warningViewModel.reset();

        ButtonNextViewModel buttonNextViewModel = new ViewModelProvider(activity).get(ButtonNextViewModel.class);
        buttonNextViewModel.reset();
    }

    private void facebookSignOut() {
        LoginManager.getInstance().logOut();
    }

    private void googleSignOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, gso);
        googleSignInClient.signOut();
    }

    private void configureQuitDialog(FragmentActivity activity) {
        new AppDialog(QUIT_DIALOG_QUESTION_CONSTANT,
                QUIT_ANSWER_CONSTANT,
                CANCEL_ANSWER_CONSTANT,
                activity::finish).buildDialog(activity).show();
    }
}
