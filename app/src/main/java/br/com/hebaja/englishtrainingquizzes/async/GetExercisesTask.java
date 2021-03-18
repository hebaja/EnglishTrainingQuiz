package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;

public class GetExercisesTask extends AsyncTask<Void, Void, List<Exercise>> {

    private final ExerciseDao dao;
    private final String userUid;
    private final WhenExercisesReadyListener listener;

    public GetExercisesTask(ExerciseDao dao, String userUid, WhenExercisesReadyListener listener) {
        this.dao = dao;
        this.userUid = userUid;
        this.listener = listener;
    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        return dao.getExercisesFromUser(userUid);
    }

    @Override
    protected void onPostExecute(List<Exercise> exercises) {
        super.onPostExecute(exercises);
        listener.whenReady(exercises);
    }

    public interface WhenExercisesReadyListener {
        void whenReady(List<Exercise> exercises);
    }

}
