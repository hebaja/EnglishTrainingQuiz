package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.MEDIUM_MODE;
import static br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuLevelsFragmentDirections.*;

public class MenuLevelsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_levels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button1 = view.findViewById(R.id.button_level_a1_a2);
        Button button2 = view.findViewById(R.id.button_level_b1_b2);
        Button button3 = view.findViewById(R.id.button_level_c1_c2);

        button1.setOnClickListener(v -> goToMenuSubjectsFragment(view, EASY_MODE));
        button2.setOnClickListener(v -> goToMenuSubjectsFragment(view, MEDIUM_MODE));
        button3.setOnClickListener(v -> goToMenuSubjectsFragment(view, HARD_MODE));
    }

    private void goToMenuSubjectsFragment(View view, int levelKey) {
        NavController controller = Navigation.findNavController(view);
        ActionMenuLevelsToMenuSubjects directions = actionMenuLevelsToMenuSubjects(levelKey);
        controller.navigate(directions);
    }
}
