package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.ConfirmActionDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.OptionLevelFragment;

import static br.com.hebaja.englishtrainingquizzes.Constants.CANCEL_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_ANSWER_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class MenuActivity extends AppCompatActivity {

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        activity = this;

        Button buttonQuit = findViewById(R.id.button_quit_menu_activity);

        buttonQuit.setOnClickListener(view -> {
            new ConfirmActionDialog(QUIT_DIALOG_QUESTION_CONSTANT,
                    QUIT_ANSWER_CONSTANT,
                    CANCEL_ANSWER_CONSTANT,
                    MenuActivity.this)
                    .show(getSupportFragmentManager(), "quit_app");
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_activity_container, new OptionLevelFragment())
                .commit();
    }
}