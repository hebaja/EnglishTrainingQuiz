package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.utils.BuilderAverages;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.LoginViewModel;

public class AveragesFragment extends Fragment {

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.averages_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.averages_list_username);
        RecyclerView averagesListRecyclerView = view.findViewById(R.id.averages_recyclerview);
        ProgressBar progressBar = view.findViewById(R.id.average_page_progress_bar);

        final BuilderAverages builderAverages = new BuilderAverages(requireActivity(), requireContext());
        builderAverages.build(usernameTextView, averagesListRecyclerView, progressBar, view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.averages_fragment_delete_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.averages_delete_user) {
            Log.i("AveragesFragment", "onOptionsItemSelected: clicked");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://trainingquizzes.com/user/android/delete"));
            final LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
            loginViewModel.logoff();
            final NavDirections directions = AveragesFragmentDirections.actionGlobalLogin();
            final NavController controller = Navigation.findNavController(requireView());
            controller.navigate(directions);
            startActivity(browserIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}