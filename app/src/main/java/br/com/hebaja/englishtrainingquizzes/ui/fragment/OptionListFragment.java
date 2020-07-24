package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.daos.OptionDAO;
import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.ui.adapter.OptionsListAdapter;

import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_EASY_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_HARD_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_MEDIUM_KEY;

public class OptionListFragment extends Fragment {

    private List<Option> options;
    private Integer levelKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;

        if(getArguments().containsKey(LEVEL_EASY_KEY)) {
            options = new OptionDAO().easyList();
            levelKey = getArguments().getInt(LEVEL_EASY_KEY);
        }

        if(getArguments().containsKey(LEVEL_MEDIUM_KEY)) {
            options = new OptionDAO().mediumList();
            levelKey = getArguments().getInt(LEVEL_MEDIUM_KEY);
        }

        if(getArguments().containsKey(LEVEL_HARD_KEY)) {
            options = new OptionDAO().hardList();
            levelKey = getArguments().getInt(LEVEL_HARD_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.options_list_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView optionsList = view.findViewById(R.id.list_button_options_listview);
        optionsList.setAdapter(new OptionsListAdapter(options, levelKey, getContext()));
    }
}
