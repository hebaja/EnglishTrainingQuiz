package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PositionViewModel extends ViewModel {

    private MutableLiveData<Integer> scoreLiveData;
    private Integer position = 0;

    public LiveData<Integer> getPosition(int response) {
        scoreLiveData = new MutableLiveData<>();
        if(response == 0) {
            position++;
        }
        scoreLiveData.setValue(position);
        return scoreLiveData;
    }

    public LiveData<Integer> reset() {
        position = 0;
        scoreLiveData.setValue(position);
        return scoreLiveData;
    }

}
