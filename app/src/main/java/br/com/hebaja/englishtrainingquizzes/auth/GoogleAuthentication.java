package br.com.hebaja.englishtrainingquizzes.auth;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.GoogleIdTokenService;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.LoginFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static br.com.hebaja.englishtrainingquizzes.Constants.GOOGLE_AUTHENTICATION_PROBLEM;

public class GoogleAuthentication {

    private final FragmentActivity mainActivity;

    public GoogleAuthentication(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public GoogleSignInClient getSignInClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(context, gso);
    }

    public void authenticate(GoogleSignInAccount googleAccount, ProgressBar progressBar, View view) {
        String idToken = googleAccount.getIdToken();
        if(idToken != null) {
            attemptToAuthenticateUser(progressBar, idToken, view);
        }
    }

    private void attemptToAuthenticateUser(ProgressBar progressBar, String idToken, View view) {
        GoogleIdTokenService service = new BaseRetrofit().getGoogleIdTokenService();
        Call<User> call = service.sendGoogleIdToken(idToken);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<User>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        doWhenAuthenticationSucceeds(progressBar, view);
                    }
                } else {
                    Snackbar.make(view, GOOGLE_AUTHENTICATION_PROBLEM, Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(view, GOOGLE_AUTHENTICATION_PROBLEM, Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void doWhenAuthenticationSucceeds(ProgressBar progressBar, View view) {
        LoginViewModel loginViewModel = new ViewModelProvider(mainActivity).get(LoginViewModel.class);
        loginViewModel.login();
        navigateToMenuLevels(view);
        progressBar.setVisibility(View.GONE);
    }

    private void navigateToMenuLevels(View view) {
        NavController controller = Navigation.findNavController(view);
        NavDirections directions = LoginFragmentDirections.actionLoginToMenuLevels();
        controller.navigate(directions);
    }
}
