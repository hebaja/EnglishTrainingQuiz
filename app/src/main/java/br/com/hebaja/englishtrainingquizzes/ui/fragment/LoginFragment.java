package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.auth.EmailAuthentication;
import br.com.hebaja.englishtrainingquizzes.auth.FacebookAuthentication;
import br.com.hebaja.englishtrainingquizzes.auth.GoogleAuthentication;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.EmailUserPasswordResetDialog;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.EmailUserRegisterDialog;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.GOOGLE_REQUEST_CODE;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.SHARED_PREFERENCES_KEY;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.USERNAME_RECEIVED;

public class LoginFragment extends Fragment {

    public static final String QUERY_PARAMETER_KEY = "id";
    public static final String QUERY_PARAMETER_USER = "user-register";
    public static final String QUERY_PARAMETER_USER_REMOVED = "user-removed";
    public static final String QUERY_PARAMETER_PASSWORD = "password-reset";
    private GoogleSignInClient googleSignInClient;
    private LoginViewModel loginViewModel;
    private NavController controller;
    private GoogleAuthentication googleAuthentication;
    private FacebookAuthentication facebookAuthentication;
    private EmailAuthentication emailAuthentication;
    private ProgressBar progressBar;
    private LinearLayout googleSignInButton;
    private LinearLayout facebookSignInButton;
    private Button emailSignInButton;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button emailUserRegisterButton;
    private Button emailUserResetPasswordButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeAuthServices();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        final SharedPreferences preferences = requireContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        configureViewModels();
        getIntentData(view, preferences);
        configureViews(view);
        checkIfUserIsSignedIn();

//        configureGoogleSignInButton(googleSignInButton);
        facebookAuthentication.configureSignInButton(facebookSignInButton, this, view, progressBar);

        emailAuthentication.configureSignInButton(
                emailSignInButton,
                emailTextInputLayout,
                passwordTextInputLayout,
                getView(),
                loginViewModel,
                progressBar,
                preferences);

        emailUserRegisterButton.setOnClickListener(v -> configureUserRegisterDialog(view, preferences));
        emailUserResetPasswordButton.setOnClickListener(v -> configurePasswordResetDialog(view));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_REQUEST_CODE) {
            setGoogleSignInAuthentication(data);
        }

        facebookAuthentication.getActivityResult(requestCode, resultCode, data);
    }

    private void initializeAuthServices() {
        googleAuthentication = new GoogleAuthentication(getActivity());
        googleSignInClient = googleAuthentication.getSignInClient(requireContext());
        facebookAuthentication = new FacebookAuthentication();
        facebookAuthentication.getCallbackManager();
        emailAuthentication = new EmailAuthentication();
    }

    private void getIntentData(@NotNull View view, SharedPreferences preferences) {
        Uri data = requireActivity().getIntent().getData();
        if (data != null) {
            if(data.getQueryParameter(QUERY_PARAMETER_KEY).equals(QUERY_PARAMETER_USER)) {
                final String username = preferences.getString(USERNAME_RECEIVED, null);
//                Snackbar.make(view, "User " + username + " was successfully registered. You can sign in now.", Snackbar.LENGTH_LONG).show();
                Toast.makeText(requireContext(), "User " + username + " was successfully registered. You can sign in now.", Toast.LENGTH_LONG).show();
            } else if(data.getQueryParameter(QUERY_PARAMETER_KEY).equals(QUERY_PARAMETER_PASSWORD)) {
//                Snackbar.make(view, "You can sign in with new password.", Snackbar.LENGTH_LONG).show();
                Toast.makeText(requireContext(), "You can sign in with new password.", Toast.LENGTH_LONG).show();
            } else if(data.getQueryParameter(QUERY_PARAMETER_KEY).equals(QUERY_PARAMETER_USER_REMOVED)) {
                Toast.makeText(requireContext(), "User was successfully removed.", Toast.LENGTH_LONG).show();
            }
            clearIntentData();
        }
    }

    private void clearIntentData() {
        Intent intent = requireActivity().getIntent();
        intent.replaceExtras(new Bundle());
        intent.setAction("");
        intent.setData(null);
        intent.setFlags(0);
    }

    private void configureViewModels() {
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    private void configureViews(@NotNull View view) {
//        googleSignInButton = view.findViewById(R.id.sign_in_google_button);
        facebookSignInButton = view.findViewById(R.id.facebook_sign_in_button);
        emailSignInButton = view.findViewById(R.id.email_sign_in_button);
        emailTextInputLayout = view.findViewById(R.id.email_sign_in_text_input_layout);
        passwordTextInputLayout = view.findViewById(R.id.password_sign_in_text_input_layout);
        emailUserRegisterButton = view.findViewById(R.id.email_user_register_button);
        emailUserResetPasswordButton = view.findViewById(R.id.email_user_password_reset_button);
        progressBar = view.findViewById(R.id.sign_in_progress_bar);
    }

//    private void configureGoogleSignInButton(LinearLayout googleSignInButton) {
//        googleSignInButton.setOnClickListener(v -> {
//            Intent signInIntent = googleSignInClient.getSignInIntent();
//            startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE);
//        });
//    }

    private void checkIfUserIsSignedIn() {
        loginViewModel.isLoggedIn().observe(getViewLifecycleOwner(), logged -> {
            if(logged) {
                navigateToMenuLevels();
            }
        });
    }

    private void navigateToMenuLevels() {
        NavDirections directions = LoginFragmentDirections.actionLoginToMenuLevels();
        controller.navigate(directions);
    }

    private void configurePasswordResetDialog(@NotNull View view) {
        EmailUserPasswordResetDialog emailUserPasswordResetDialog = new EmailUserPasswordResetDialog(progressBar, view);
        emailUserPasswordResetDialog.setTargetFragment(this, EmailUserPasswordResetDialog.EMAIL_USER_PASSWORD_RESET_DIALOG_KEY_CODE);
        emailUserPasswordResetDialog.show(getParentFragmentManager(), EmailUserPasswordResetDialog.EMAIL_USER_PASSWORD_RESET_DIALOG_EDIT_TEXT);
    }

    private void configureUserRegisterDialog(@NotNull View view, SharedPreferences preferences) {
        EmailUserRegisterDialog emailUserRegisterDialog = new EmailUserRegisterDialog(progressBar, view, preferences);
        emailUserRegisterDialog.setTargetFragment(this, EmailUserRegisterDialog.EMAIL_USER_REGISTER_DIALOG_KEY_CODE);
        emailUserRegisterDialog.show(getParentFragmentManager(), EmailUserRegisterDialog.EMAIL_USER_REGISTER_DIALOG_EDIT_TEXT);
    }

    private void setGoogleSignInAuthentication(Intent data) {

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount googleAccount = task.getResult(ApiException.class);

            if(googleAccount != null) {
                googleAuthentication.authenticate(googleAccount, progressBar, getView());

            }
        } catch (ApiException e) {
           Snackbar.make(requireView(), "There was a problem during authentication " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}