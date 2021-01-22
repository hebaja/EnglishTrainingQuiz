package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Average;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.AveragesService;
import br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter.AveragesListAdapter;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AveragesFragment extends Fragment {

    public static final String AVERAGES_LOAD_ERROR_MESSAGE = "Could not load averages. Check your internet connection.";
    private String email;
    private TextView usernameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.averages_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextView = view.findViewById(R.id.averages_list_username);
        RecyclerView averagesList = view.findViewById(R.id.averages_recyclerview);
        ProgressBar progressBar = view.findViewById(R.id.average_page_progress_bar);

        GoogleSignInAccount currentGoogleAccount = GoogleSignIn.getLastSignedInAccount(requireContext());
        Profile currentFacebookAccount = Profile.getCurrentProfile();

        if (currentGoogleAccount != null) {
            email = currentGoogleAccount.getEmail();
            usernameTextView.setText(currentGoogleAccount.getGivenName());
            AveragesService service = new BaseRetrofit().getAveragesService();
            Call<List<Average>> call = service.getAveragesByEmail(email);
            searchAverages(averagesList, call, progressBar);
        } else if (currentFacebookAccount != null) {
            String uid = currentFacebookAccount.getId();
            usernameTextView.setText(currentFacebookAccount.getFirstName());
            AveragesService service = new BaseRetrofit().getAveragesService();
            Call<List<Average>> call = service.getAveragesByUid(uid);
            searchAverages(averagesList, call, progressBar);
        } else {
            UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    email = user.getEmail();
                    usernameTextView.setText(user.getUsername());
                    AveragesService service = new BaseRetrofit().getAveragesService();
                    Call<List<Average>> call = service.getAveragesByEmail(email);
                    searchAverages(averagesList, call, progressBar);
                }
            });
        }
    }

    private void searchAverages(RecyclerView averagesList, Call<List<Average>> call, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Average>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Average>> call, Response<List<Average>> response) {
                progressBar.setVisibility(View.GONE);
                configureRecyclerView(response, averagesList);

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Average>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(requireView(), AVERAGES_LOAD_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void configureRecyclerView(Response<List<Average>> response, RecyclerView averagesList) {
        if (response.isSuccessful()) {
            List<Average> averages = response.body();
            if (averages != null) {
                averagesList.setAdapter(new AveragesListAdapter(averages, getContext()));
            }
        } else {
            Snackbar.make(requireView(), AVERAGES_LOAD_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
        }
    }
}