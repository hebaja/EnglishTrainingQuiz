package br.com.hebaja.englishtrainingquizzes.retrofit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import retrofit2.Call;
import retrofit2.Response;

public class SearchExercise {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<List<Exercise>> getExercises() {

        return executor.submit(new Callable<List<Exercise>>() {
            @Override
            public List<Exercise> call() throws Exception {

                ExerciseService service = new BaseRetrofit().getExerciseService();
                Call<List<Exercise>> exerciseListCall = service.getExercises();

                try {
                    Response<List<Exercise>> response = exerciseListCall.execute();
                    return response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
