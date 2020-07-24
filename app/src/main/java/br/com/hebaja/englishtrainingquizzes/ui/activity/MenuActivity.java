package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.QuitAppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.OptionLevelFragment;

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
            QuitAppDialog quitAppDialog = new QuitAppDialog(MenuActivity.this);
            quitAppDialog.show(getSupportFragmentManager(), "quit_app");
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_activity_container, new OptionLevelFragment())
                .commit();
    }
}