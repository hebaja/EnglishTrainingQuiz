package br.com.hebaja.englishtrainingquizzes.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.FacebookIdTokenService;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.LoginFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static br.com.hebaja.englishtrainingquizzes.Constants.FACEBOOK_AUTHENTICATION_PROBLEM;
import static br.com.hebaja.englishtrainingquizzes.Constants.FACEBOOK_READ_PERMISSION_EMAIL;
import static br.com.hebaja.englishtrainingquizzes.Constants.FACEBOOK_READ_PERMISSION_PUBLIC_PROFILE;

public class FacebookAuthentication {

    public static final String PARAMETERS_KEY = "fields";
    public static final String PARAMETERS_VALUE = "first_name,email";
    private CallbackManager callbackManager;

    public void getCallbackManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    public void configureSignInButton(LinearLayout facebookSignInButton, Fragment fragment, View view, ProgressBar progressBar) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {});
                Bundle parameters = new Bundle();
                parameters.putString(PARAMETERS_KEY, PARAMETERS_VALUE);
                request.setParameters(parameters);
                request.executeAsync();
                attemptToAuthenticateUser(accessToken, progressBar, fragment, view);
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(view, FACEBOOK_AUTHENTICATION_PROBLEM, Snackbar.LENGTH_LONG).show();
            }
        });

        facebookSignInButton.setOnClickListener(v ->
                LoginManager
                        .getInstance()
                        .logInWithReadPermissions(fragment, Arrays.asList(FACEBOOK_READ_PERMISSION_EMAIL, FACEBOOK_READ_PERMISSION_PUBLIC_PROFILE)));
    }

    private void attemptToAuthenticateUser(AccessToken accessToken, ProgressBar progressBar, Fragment fragment, View view) {
        FacebookIdTokenService service = new BaseRetrofit().getFacebookIdTokenService();
        Call<User> call = service.sendFacebookIdToken(accessToken.getToken());
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<User>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    if(user != null) {
                        doWhenAuthenticationSucceeds(fragment, view);
                    }
                } else {
                    Snackbar.make(view, FACEBOOK_AUTHENTICATION_PROBLEM, Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(view, FACEBOOK_AUTHENTICATION_PROBLEM, Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void doWhenAuthenticationSucceeds(Fragment fragment, View view) {
        LoginViewModel loginViewModel = new ViewModelProvider(fragment.getActivity()).get(LoginViewModel.class);
        loginViewModel.login();
        navigateToMenuLevels(view);
    }

    public void getActivityResult(int request, int result, @Nullable Intent data) {
        callbackManager.onActivityResult(request, result, data);
    }

    private void navigateToMenuLevels(View view) {
        NavController controller = Navigation.findNavController(view);
        NavDirections directions = LoginFragmentDirections.actionLoginToMenuLevels();
        controller.navigate(directions);
    }
}
