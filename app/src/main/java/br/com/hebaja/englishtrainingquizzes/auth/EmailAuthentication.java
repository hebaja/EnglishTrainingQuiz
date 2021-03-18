package br.com.hebaja.englishtrainingquizzes.auth;

import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.UserEmailSignInService;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.LoginFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.EMAIL_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.UID_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.USERNAME_RECEIVED;

public class EmailAuthentication {

    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password.";
    public static final String EMAIL_NOT_REGISTERED_MESSAGE = "This e-mail is not registered.";
    public static final String SERVER_CONNECT_ERROR_MESSAGE = "It wasn't possible to connect to server.";
    public static final String EMAIL_NECESSARY_MESSAGE = "E-mail is necessary.";
    public static final String INSERT_VALID_EMAIL_MESSAGE = "Insert a valid e-mail.";
    public static final String INSERT_PASSWORD_MESSAGE = "You must insert a password.";
    private String email;
    private String password;

    public void configureSignInButton(Button emailSignInButton,
                                      TextInputLayout emailTextInputLayout,
                                      TextInputLayout passwordTextInputLayout,
                                      View view,
                                      LoginViewModel loginViewModel,
                                      ProgressBar progressBar,
                                      SharedPreferences preferences) {
        emailSignInButton.setOnClickListener(v -> {

            emailTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);

            if(validateInputs(emailTextInputLayout, passwordTextInputLayout)) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);

                attemptToAuthenticateUser(
                        emailTextInputLayout,
                        passwordTextInputLayout,
                        view,
                        loginViewModel,
                        progressBar,
                        user,
                        preferences);
            }
        });
    }

    private void attemptToAuthenticateUser(TextInputLayout emailTextInputLayout,
                                           TextInputLayout passwordTextInputLayout,
                                           View view,
                                           LoginViewModel loginViewModel,
                                           ProgressBar progressBar,
                                           User user,
                                           SharedPreferences preferences) {
        UserEmailSignInService service = new BaseRetrofit().getUserEmailSignInService();
        Call<User> call = service.sendUser(user, user.getEmail());
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<User>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User userReturned = response.body();
                    if(userReturned != null) {
                        BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), userReturned.getPassword());
                        if(result.verified) {
                            doWhenPasswordIsCorrect(userReturned, view, loginViewModel, preferences);
                        } else {
                            passwordTextInputLayout.setError(WRONG_PASSWORD_MESSAGE);
                        }
                    }
                } else {
                    emailTextInputLayout.setError(EMAIL_NOT_REGISTERED_MESSAGE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(view, SERVER_CONNECT_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void doWhenPasswordIsCorrect(User userReturned, View view, LoginViewModel loginViewModel, SharedPreferences preferences) {
        navigateToMenuLevels(view);
        loginViewModel.login();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_RECEIVED, userReturned.getEmail());
        editor.putString(USERNAME_RECEIVED, userReturned.getUsername());
        editor.putString(UID_RECEIVED, userReturned.getUid());
        editor.apply();
    }

    private boolean validateInputs(TextInputLayout emailTextInputLayout, TextInputLayout passwordTextInputLayout) {
        if(emailTextInputLayout != null) {
            email = Objects.requireNonNull(emailTextInputLayout.getEditText()).getText().toString();
        }
        if(emailTextInputLayout != null) {
            password = Objects.requireNonNull(passwordTextInputLayout.getEditText()).getText().toString();
        }
        if(email.isEmpty()) {
            assert emailTextInputLayout != null;
            emailTextInputLayout.setErrorEnabled(true);
            emailTextInputLayout.setError(EMAIL_NECESSARY_MESSAGE);
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            assert emailTextInputLayout != null;
            emailTextInputLayout.setErrorEnabled(true);
            emailTextInputLayout.setError(INSERT_VALID_EMAIL_MESSAGE);
            return false;
        }
        if(password.isEmpty()) {
            passwordTextInputLayout.setErrorEnabled(true);
            passwordTextInputLayout.setError(INSERT_PASSWORD_MESSAGE);
            return false;
        }
        return true;
    }

    private void navigateToMenuLevels(View view) {
        NavController controller = Navigation.findNavController(view);
        NavDirections directions = LoginFragmentDirections.actionLoginToMenuLevels();
        controller.navigate(directions);
    }
}