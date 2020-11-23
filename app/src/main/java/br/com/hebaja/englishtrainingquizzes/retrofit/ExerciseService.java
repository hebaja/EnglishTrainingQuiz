package br.com.hebaja.englishtrainingquizzes.retrofit;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ExerciseService {

    @GET("api/exercises/user/hebaja")
    Call<List<Exercise>> getExercises();


}
