package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;

public class SaveExerciseTask extends AsyncTask<Void, Void, Void> {

    private final Exercise exercise;
    private final ExerciseDao dao;

    public SaveExerciseTask(Exercise exercise, ExerciseDao dao) {
        this.exercise = exercise;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.save(exercise);
        return null;
    }
}
