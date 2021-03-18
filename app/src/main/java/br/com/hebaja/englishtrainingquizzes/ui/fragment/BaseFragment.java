package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.hebaja.englishtrainingquizzes.utils.OptionsMenuConfigure;
import br.com.hebaja.englishtrainingquizzes.R;

public abstract class BaseFragment extends Fragment {

    private NavController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        OptionsMenuConfigure optionsMenuConfigure = new OptionsMenuConfigure(item, controller, getContext());
        optionsMenuConfigure.configureOptionsMenuItems(requireActivity());
        return super.onOptionsItemSelected(item);
    }
}
