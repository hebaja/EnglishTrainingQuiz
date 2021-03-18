package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;

public class RemoveExercisesTask extends AsyncTask<Void, Void, Void> {

    private final ExerciseDao dao;
    private final String userUid;

    public RemoveExercisesTask(ExerciseDao dao, String userUid) {
        this.dao = dao;
        this.userUid = userUid;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.removeExercises(userUid);
        return null;
    }
}
