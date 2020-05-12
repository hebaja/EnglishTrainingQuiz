package br.com.hebaja.englishtrainingquizzes.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.daos.OptionDAO;
import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.ui.adapter.OptionsListAdapter;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.QuitAppDialog;

import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_KEY;

public class MenuActivity extends AppCompatActivity {

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        activity = this;

        ListView optionsList = findViewById(R.id.list_button_options_listview);
        final List<Option> options = new OptionDAO().list();

        optionsList.setAdapter(new OptionsListAdapter(options, this));

        Button buttonQuit = findViewById(R.id.button_quit_menu_activity);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuitAppDialog quitAppDialog = new QuitAppDialog(MenuActivity.this);
                quitAppDialog.show(getSupportFragmentManager(), "quit_app");
            }
        });
    }

}
