package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PositionViewModel extends ViewModel {

    private MutableLiveData<Integer> scoreLiveData;
    private Integer position = 0;

    public LiveData<Integer> getPosition(int response) {
        checkIfDataIsNull();
        if(response == 0) {
            position++;
        }
        scoreLiveData.setValue(position);
        return scoreLiveData;
    }

    private void checkIfDataIsNull() {
        if(scoreLiveData == null) {
            scoreLiveData = new MutableLiveData<>();
        }
    }

    public void reset() {
        checkIfDataIsNull();
        position = 0;
        scoreLiveData.setValue(position);
    }
}
