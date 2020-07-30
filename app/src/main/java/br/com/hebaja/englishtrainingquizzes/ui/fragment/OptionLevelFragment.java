package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_EASY_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_HARD_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.LEVEL_MEDIUM_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.MEDIUM_MODE;

public class OptionLevelFragment extends Fragment {

    private FragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.options_list_levels_menu, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button1 = view.findViewById(R.id.button_level_a1_a2);
        Button button2 = view.findViewById(R.id.button_level_b1_b2);
        Button button3 = view.findViewById(R.id.button_level_c1_c2);

        button1.setOnClickListener(v -> {
            createOptionListFragment(LEVEL_EASY_KEY, EASY_MODE);
        });

        button2.setOnClickListener(v -> {
            createOptionListFragment(LEVEL_MEDIUM_KEY, MEDIUM_MODE);
        });

        button3.setOnClickListener(v -> {
            createOptionListFragment(LEVEL_HARD_KEY, HARD_MODE);
        });
    }

    private void createOptionListFragment(String stringKey, int intKey) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        OptionListFragment fragment = new OptionListFragment();
        Bundle dados = new Bundle();
        dados.putInt(stringKey, intKey);
        fragment.setArguments(dados);
        transaction.addToBackStack(null);
        transaction.replace(R.id.menu_activity_container, fragment).commit();
    }
}
