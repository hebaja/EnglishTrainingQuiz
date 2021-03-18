package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private MutableLiveData<Integer> scoreLiveData;
    private Integer score = 0;

    public LiveData<Integer> request(int request) {
        checkIfDataIsNull();
        if(request == 0) {
            score++;
        }
        scoreLiveData.setValue(score);
        return scoreLiveData;
    }

    public void reset() {
        checkIfDataIsNull();
        score = 0;
        scoreLiveData.setValue(0);
    }

    private void checkIfDataIsNull() {
        if(scoreLiveData == null) {
            scoreLiveData = new MutableLiveData<>();
        }
    }

}
