package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Task;

public class TasksViewModel extends ViewModel {

    private MutableLiveData<List<Task>> quizBuiltLiveData;

    public void set(List<Task> tasks) {
        checkIfDataIsNotNull();
        quizBuiltLiveData.setValue(tasks);
    }

    private void checkIfDataIsNotNull() {
        if(quizBuiltLiveData == null) {
            quizBuiltLiveData = new MutableLiveData<>();
            quizBuiltLiveData.setValue(Collections.emptyList());
        }
    }

    public LiveData<List<Task>> get() {
        checkIfDataIsNotNull();
        return quizBuiltLiveData;
    }

    public void reset() {
        checkIfDataIsNotNull();
        quizBuiltLiveData.setValue(Collections.emptyList());
    }
}
