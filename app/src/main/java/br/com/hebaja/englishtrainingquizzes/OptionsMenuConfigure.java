package br.com.hebaja.englishtrainingquizzes;

import android.content.Context;
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

import br.com.hebaja.englishtrainingquizzes.ui.fragment.AveragesFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FeedbackFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.LoginFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.UserViewModel;

public class OptionsMenuConfigure {

    private final NavController controller;
    private final MenuItem item;
    private final Context context;

    public OptionsMenuConfigure(@NonNull MenuItem item, NavController controller, Context context) {
        this.controller = controller;
        this.item = item;
        this.context = context;
    }

    public void configureOptionsMenuItems(FragmentActivity activity) {
        NavDirections directions;

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
            LoginViewModel loginViewModel = new ViewModelProvider(activity).get(LoginViewModel.class);
            UserViewModel userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);
            googleSignOut();
            facebookSignOut();
            loginViewModel.logoff();
            userViewModel.clear();
            directions = LoginFragmentDirections.actionGlobalLogin();
            controller.navigate(directions);
        }
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
}
