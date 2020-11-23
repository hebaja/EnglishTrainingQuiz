package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.retrofit.SearchExercise;
import br.com.hebaja.englishtrainingquizzes.ui.adapter.AveragesListAdapter;

public class AveragesFragment extends Fragment {

    private List<Exercise> exercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.averages_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView averagesList = view.findViewById(R.id.averages_listview);

        exercises = getFromApi();

        if(exercises != null) {
            averagesList.setAdapter(new AveragesListAdapter(exercises, getContext()));
        } else {
            Toast.makeText(getContext(), "Could not load averages. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Exercise> getFromApi() {
        Future<List<Exercise>> futureExercises = new SearchExercise().getExercises();

        try {
            return futureExercises.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}