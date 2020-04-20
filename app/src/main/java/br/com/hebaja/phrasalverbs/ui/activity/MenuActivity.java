package br.com.hebaja.phrasalverbs.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.daos.OptionDAO;
import br.com.hebaja.phrasalverbs.model.Option;
import br.com.hebaja.phrasalverbs.ui.adapter.OptionsListAdapter;

import static br.com.hebaja.phrasalverbs.ui.activity.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.PHRASAL_VERBS_OPTION;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        ListView optionsList = findViewById(R.id.list_button_options_listview);
        final List<Option> options = new OptionDAO().list();

        optionsList.setAdapter(new OptionsListAdapter(options, this));
        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MenuActivity.this, QuizActivity.class);
                intent.putExtra(CHOSEN_OPTION_KEY, position);
                startActivity(intent);
                finish();
            }
        });

        Button buttonQuit = findViewById(R.id.button_quit_menu_activity);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
