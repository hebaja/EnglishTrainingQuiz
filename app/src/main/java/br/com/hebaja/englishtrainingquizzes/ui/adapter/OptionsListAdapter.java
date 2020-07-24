package br.com.hebaja.englishtrainingquizzes.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.ui.activity.MenuActivity;
import br.com.hebaja.englishtrainingquizzes.ui.activity.QuizActivity;

import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.englishtrainingquizzes.R.layout.button_menu_list_item;

public class OptionsListAdapter extends BaseAdapter {

    private Activity menuActivity = MenuActivity.activity;
    private final List<Option> options;
    private final Context context;
    private final Integer levelKey;

    public OptionsListAdapter(List<Option> options, Integer levelKey, Context context) {
        this.options = options;
        this.levelKey = levelKey;
        this.context = context;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button buttonCreated = (Button) LayoutInflater.from(context).inflate(button_menu_list_item, parent, false);
        buttonCreated.setText(options.get(position).getPrompt());

        buttonCreated.setOnClickListener(view -> {
            Intent intent = new Intent(context, QuizActivity.class);
            intent.putExtra(CHOSEN_OPTION_KEY, position);
            intent.putExtra(CHOSEN_LEVEL_KEY, levelKey);
            context.startActivity(intent);
            menuActivity.finish();
        });
        return buttonCreated;
    }
}
