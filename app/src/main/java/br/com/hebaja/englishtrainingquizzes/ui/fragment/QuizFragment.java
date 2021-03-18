package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.utils.BuilderExerciseFromApi;

public class QuizFragment extends BaseFragment {

    private int chosenLevel;
    private int chosenSubject;
    private String fileName;
    private String subjectPrompt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        chosenLevel = QuizFragmentArgs.fromBundle(getArguments()).getChosenLevel();
        chosenSubject = QuizFragmentArgs.fromBundle(getArguments()).getChosenOption();
        fileName = QuizFragmentArgs.fromBundle(getArguments()).getFileName();
        subjectPrompt = QuizFragmentArgs.fromBundle(getArguments()).getSubjectPrompt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AdView adView = view.findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);

        final BuilderExerciseFromApi builderExerciseFromApi = new BuilderExerciseFromApi(
                view,
                fileName,
                requireActivity(),
                chosenLevel,
                chosenSubject,
                subjectPrompt,
                getViewLifecycleOwner());

        builderExerciseFromApi.fetchExerciseFromApi();
    }
}