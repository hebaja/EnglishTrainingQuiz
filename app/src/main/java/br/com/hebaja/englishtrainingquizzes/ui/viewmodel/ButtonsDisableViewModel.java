package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Task;

public class ButtonsDisableViewModel extends ViewModel {

    private MutableLiveData<Boolean> buttonsDisableLiveData;

    public void set() {
        checkIfDataIsNotNull();
        buttonsDisableLiveData.setValue(true);
    }

    private void checkIfDataIsNotNull() {
        if(buttonsDisableLiveData == null) {
            buttonsDisableLiveData = new MutableLiveData<>();
            buttonsDisableLiveData.setValue(false);
        }
    }

    public LiveData<Boolean> get() {
        checkIfDataIsNotNull();
        return buttonsDisableLiveData;
    }

    public void reset() {
        checkIfDataIsNotNull();
        buttonsDisableLiveData.setValue(false);
    }
}
