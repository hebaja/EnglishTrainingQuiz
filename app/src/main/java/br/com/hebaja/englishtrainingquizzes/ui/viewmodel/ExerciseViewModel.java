package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;

public class ExerciseViewModel extends ViewModel {

    private MutableLiveData<Exercise> exerciseLiveData;

    public void registerExercise(Exercise exercise) {
        checkIfLiveDataIsNull();
        exerciseLiveData.setValue(exercise);
    }

    private void checkIfLiveDataIsNull() {
        if (exerciseLiveData == null) {
            exerciseLiveData = new MutableLiveData<>();
        }
    }

    public LiveData<Exercise> getExercise() {
        checkIfLiveDataIsNull();
        return exerciseLiveData;
    }

    public void reset() {
        checkIfLiveDataIsNull();
        exerciseLiveData.setValue(null);
    }
}
