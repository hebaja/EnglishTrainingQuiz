package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SaveExerciseService {

    @POST("api/exercise")
    Call<Exercise> register(@Body Exercise exercise);
}
