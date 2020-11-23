package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private MutableLiveData<Integer> scoreLiveData;
    private Integer score = 0;

    public LiveData<Integer> request(int request) {
        scoreLiveData = new MutableLiveData<>();
        if(request == 0) {
            score++;
        }
        scoreLiveData.setValue(score);
        return scoreLiveData;
    }

    public LiveData<Integer> reset() {
        score = 0;
        scoreLiveData.setValue(0);
        return scoreLiveData;
    }

}
