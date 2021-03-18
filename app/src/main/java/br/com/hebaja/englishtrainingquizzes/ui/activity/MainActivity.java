package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;

import br.com.hebaja.englishtrainingquizzes.utils.DestinationChangedListener;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.AppDialog;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class MainActivity extends AppCompatActivity {

    private OnBackPressedCallback backPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        NavController navController = Navigation.findNavController(this, R.id.main_activity_nav_host);
        configureBackPressedCallback();

        DestinationChangedListener destinationChangedListener = new DestinationChangedListener(
                navController,
                backPressedCallback,
                this);
        destinationChangedListener.configureDestinationChangedListener();

        FirebaseMessaging.getInstance().subscribeToTopic("file_checksum")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if(!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribed_failed);
                    }
                    Log.d("Main Activity", msg);
        });

        MobileAds.initialize(this, initializationStatus -> {});
    }

    private void configureBackPressedCallback() {
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                configureQuitDialog();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    private void configureQuitDialog() {
        new AppDialog(QUIT_DIALOG_QUESTION_CONSTANT, QUIT_ANSWER_CONSTANT, CANCEL_ANSWER_CONSTANT, this::finish).buildDialog(this).show();
    }
}