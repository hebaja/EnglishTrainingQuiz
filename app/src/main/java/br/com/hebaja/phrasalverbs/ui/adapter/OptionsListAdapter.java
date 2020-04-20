package br.com.hebaja.phrasalverbs.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.model.Option;
import br.com.hebaja.phrasalverbs.ui.activity.MenuActivity;

public class OptionsListAdapter extends BaseAdapter {

    private final List<Option> options;
    private final Context context;

    public OptionsListAdapter(List<Option> options, Context context) {
        this.options = options;
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
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.button_menu_item, parent, false);
        TextView prompt = viewCriada.findViewById(R.id.item_prompt_button);
        prompt.setText(options.get(position).getPrompt());
        return viewCriada;
    }
}
