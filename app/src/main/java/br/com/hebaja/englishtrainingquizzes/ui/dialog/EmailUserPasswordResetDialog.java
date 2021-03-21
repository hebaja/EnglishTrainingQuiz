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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.EmailUserPasswordResetService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EmailUserPasswordResetDialog extends DialogFragment {

    public static final String EMAIL_USER_PASSWORD_RESET_DIALOG_EDIT_TEXT = "email_user_password_reset_dialog_edit_text";
    public static final int EMAIL_USER_PASSWORD_RESET_DIALOG_KEY_CODE = 321;
    public static final String CHECK_YOUR_EMAIL_INBOX_MESSAGE = "Check your email inbox to complete password reset.";
    public static final String PASSWORD_RESET_REQUEST_ERROR = "It was not possible to send password reset request.";
    public static final String SERVER_CONNECT_ERROR = "Could not connect to server.";
    public static final String PROVIDE_EMAIL_MESSAGE = "You must provide an e-mail.";
    public static final String VALID_EMAIL_MESSAGE = "Insert a valid e-mail.";
    private TextInputLayout emailInputLayout;
    private final ProgressBar progressBar;
    private final View view;
    private String email;

    public EmailUserPasswordResetDialog(ProgressBar progressBar, View view) {
        this.view = view;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getLayoutInflater().inflate(R.layout.email_user_password_reset_dialog, null))
                .setPositiveButton("Confirm", (dialog, which) -> {})
                .setNegativeButton("Cancel", (dialog, which) -> {});
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null) {
            Button button = dialog.getButton(Dialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                emailInputLayout = dialog.findViewById(R.id.dialog_email_user_password_reset_input_layout);
                if(inputsAreValid()) {
                    dialog.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    EmailUserPasswordResetService service = new BaseRetrofit().getEmailUserPasswordResetService();
                    Call<Boolean> call = service.resetPassword(email);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        @EverythingIsNonNull
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful()) {
                                if(response.body() != null) {
                                    if(response.body()) {
                                        Snackbar.make(view, CHECK_YOUR_EMAIL_INBOX_MESSAGE, Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Snackbar.make(view, PASSWORD_RESET_REQUEST_ERROR, Snackbar.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        @EverythingIsNonNull
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Snackbar.make(view, SERVER_CONNECT_ERROR, Snackbar.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
    }

    private boolean inputsAreValid() {
        clearErrorLayouts();
        getInputs();
        return validateInputs();
    }

    private boolean validateInputs() {
        if(email.isEmpty()) {
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError(PROVIDE_EMAIL_MESSAGE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError(VALID_EMAIL_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearErrorLayouts() {
        emailInputLayout.setErrorEnabled(false);
    }

    private void getInputs() {
        if(emailInputLayout != null) {
            email = Objects.requireNonNull(emailInputLayout.getEditText()).getText().toString();
        }
    }
}