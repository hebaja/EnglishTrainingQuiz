package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.TaskObjectsGenerator;
import br.com.hebaja.englishtrainingquizzes.model.Task;

public class TaskListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Task>> tasksLiveData;

    public TaskListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Task>> getTasks(int chosenLevel, int chosenSubject) {
        if(tasksLiveData == null) {
            tasksLiveData = new MutableLiveData<>();
            List<Task> tasks = new TaskObjectsGenerator(chosenLevel, chosenSubject, getApplication()).generateTaskObjectsFromJsonFile();
            tasksLiveData.setValue(tasks);
        }
        return tasksLiveData;
    }
}
