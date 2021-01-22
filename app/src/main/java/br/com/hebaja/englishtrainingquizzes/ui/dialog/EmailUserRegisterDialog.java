package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EmailUserRegisterDialog extends DialogFragment {

    public static final String EMAIL_USER_REGISTER_DIALOG_EDIT_TEXT = "email_user_register_dialog_edit_text";
    public static final int EMAIL_USER_REGISTER_DIALOG_KEY_CODE = 123;
    public static final String USER_REGISTER_REQUEST_PROBLEM = "There was a problem";
    public static final String POSITIVE_BUTTON_LABEL = "Register";
    public static final String NEGATIVE_BUTTON_LABEL = "Cancel";
    public static final String EMAIL_HAS_BEEN_USED_MESSAGE = "E-mail has already been used. Please use another.";
    public static final String USER_REGISTER_REQUEST_ERROR_MESSAGE = "There was a problem during user register request.";
    public static final String CHECK_EMAIL_REGISTRATION_MESSAGE = "Check your email inbox to complete registration.";
    public static final String PROVIDE_USERNAME_MESSAGE = "You must provide a username.";
    public static final String USERNAME_MIN_CHARS_MESSAGE = "Username must contain more than 2 characters.";
    public static final String USERNAME_MAX_CHARS_MESSAGE = "Username must contain less than 20 characters.";
    public static final String PROVIDE_EMAIL_MESSAGE = "You must provide an e-mail.";
    public static final String VALID_EMAIL_MESSAGE = "Insert a valid e-mail.";
    public static final String CONFIRM_EMAIL_MESSAGE = "You must confirm e-mail.";
    public static final String EMAILS_DONT_MATCH_MESSAGE = "E-mails can't be different.";
    public static final String PROVIDE_PASSWORD_MESSAGE = "You must provide a password.";
    public static final String PASSWORD_MIN_CHARS_MESSAGE = "Password must contain more than 3 characters.";
    public static final String PASSWORD_MAX_CHARS_MESSAGE = "Password must contain less than 20 characters.";
    public static final String CONFIRM_PASSWORD_MESSAGE = "You must confirm password.";
    public static final String PASSWORDS_DONT_MATCH_MESSAGE = "Passwords can't be different.";
    private String username;
    private String email;
    private String emailConfirm;
    private String password;
    private String passwordConfirm;
    private TextInputLayout usernameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout emailConfirmInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout passwordConfirmInputLayout;
    private final View view;
    private final ProgressBar progressBar;

    public EmailUserRegisterDialog(ProgressBar progressBar, View view) {
        this.view = view;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getLayoutInflater().inflate(R.layout.email_user_register_dialog, null))
                .setPositiveButton(POSITIVE_BUTTON_LABEL, (dialog, which) -> {

                })
                .setNegativeButton(NEGATIVE_BUTTON_LABEL, (dialog, which) -> {

                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null) {
            Button button = dialog.getButton(Dialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {

                configureViews(dialog);
                User user = configureUser();

                if(inputsAreValid()) {
                    UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
                    attemptToSendUserRegisterRequest(dialog, user, userViewModel);
                } else {
                    dialog.cancel();
                    Snackbar.make(view, USER_REGISTER_REQUEST_PROBLEM, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    @NotNull
    private User configureUser() {
        User user = new User();
        user.setUsername(usernameInputLayout.getEditText().getText().toString());
        user.setEmail(emailInputLayout.getEditText().getText().toString());
        user.setPassword(passwordInputLayout.getEditText().getText().toString());
        return user;
    }

    private void configureViews(AlertDialog dialog) {
        usernameInputLayout = dialog.findViewById(R.id.dialog_email_register_username_input_layout);
        emailInputLayout = dialog.findViewById(R.id.dialog_email_register_input_layout);
        emailConfirmInputLayout = dialog.findViewById(R.id.dialog_confirm_email_register_input_layout);
        passwordInputLayout = dialog.findViewById(R.id.dialog_email_register_password_input_layout);
        passwordConfirmInputLayout = dialog.findViewById(R.id.dialog_email_register_confirm_password_input_layout);
    }

    private void attemptToSendUserRegisterRequest(AlertDialog dialog, User user, UserViewModel userViewModel) {
        Call<Boolean> call = new BaseRetrofit().getEmailExistsService().emailExists(email);
        call.enqueue(new Callback<Boolean>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    if(response.body()) {
                        emailInputLayout.setErrorEnabled(true);
                        emailInputLayout.setError(EMAIL_HAS_BEEN_USED_MESSAGE);
                    } else {
                        dialog.cancel();
                        sendUserRegisterRequest(user, userViewModel);
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Boolean> call, Throwable t) {
                dialog.cancel();
                Snackbar.make(view, USER_REGISTER_REQUEST_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void sendUserRegisterRequest(User user, UserViewModel userViewModel) {
        progressBar.setVisibility(View.VISIBLE);
        Call<Boolean> userRegisterCall = new BaseRetrofit().getEmailUserRegisterService().register(user);
        userRegisterCall.enqueue(new Callback<Boolean>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    if(response.body()) {
                        userViewModel.setUserLiveData(user);
                        Snackbar.make(view, CHECK_EMAIL_REGISTRATION_MESSAGE, Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, USER_REGISTER_REQUEST_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(view, USER_REGISTER_REQUEST_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean inputsAreValid() {
        clearErrorLayouts();
        getInputs();
        return validateInputs();
    }

    private boolean validateInputs() {
        if(username.isEmpty()) {
            usernameInputLayout.setErrorEnabled(true);
            usernameInputLayout.setError(PROVIDE_USERNAME_MESSAGE);
            return false;
        } else if (username.length() < 3) {
            usernameInputLayout.setErrorEnabled(true);
            usernameInputLayout.setError(USERNAME_MIN_CHARS_MESSAGE);
            return false;
        } else if (username.length() > 20) {
            usernameInputLayout.setErrorEnabled(true);
            usernameInputLayout.setError(USERNAME_MAX_CHARS_MESSAGE);
            return false;
        }
        if(email.isEmpty()) {
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError(PROVIDE_EMAIL_MESSAGE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError(VALID_EMAIL_MESSAGE);
            return false;
        }
        if(emailConfirm.isEmpty()) {
            emailConfirmInputLayout.setErrorEnabled(true);
            emailConfirmInputLayout.setError(CONFIRM_EMAIL_MESSAGE);
            return false;
        } else if(!emailConfirm.equals(email)) {
            emailConfirmInputLayout.setErrorEnabled(true);
            emailConfirmInputLayout.setError(EMAILS_DONT_MATCH_MESSAGE);
            return false;
        }
        if(password.isEmpty()) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError(PROVIDE_PASSWORD_MESSAGE);
            return false;
        } else if (password.length() < 4) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError(PASSWORD_MIN_CHARS_MESSAGE);
            return false;
        } else if (password.length() > 20) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError(PASSWORD_MAX_CHARS_MESSAGE);
            return false;
        }
        if(passwordConfirm.isEmpty()) {
            passwordConfirmInputLayout.setErrorEnabled(true);
            passwordConfirmInputLayout.setError(CONFIRM_PASSWORD_MESSAGE);
            return false;
        } else if(!passwordConfirm.equals(password)) {
            passwordConfirmInputLayout.setErrorEnabled(true);
            passwordConfirmInputLayout.setError(PASSWORDS_DONT_MATCH_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearErrorLayouts() {
        usernameInputLayout.setErrorEnabled(false);
        emailInputLayout.setErrorEnabled(false);
        emailConfirmInputLayout.setErrorEnabled(false);
        passwordInputLayout.setErrorEnabled(false);
        passwordConfirmInputLayout.setErrorEnabled(false);
    }

    private void getInputs() {
        if(usernameInputLayout != null) {
            username = Objects.requireNonNull(usernameInputLayout.getEditText()).getText().toString();
        }
        if(emailInputLayout != null) {
            email = Objects.requireNonNull(emailInputLayout.getEditText()).getText().toString();
        }
        if(emailConfirmInputLayout != null) {
            emailConfirm = Objects.requireNonNull(emailConfirmInputLayout.getEditText()).getText().toString();
        }
        if(passwordInputLayout != null) {
            password = Objects.requireNonNull(passwordInputLayout.getEditText()).getText().toString();
        }
        if(passwordConfirmInputLayout != null) {
            passwordConfirm = Objects.requireNonNull(passwordConfirmInputLayout.getEditText()).getText().toString();
        }
    }
}