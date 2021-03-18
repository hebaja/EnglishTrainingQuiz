package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonArrayExerciseService {

    @GET("files/json/{file}")
    Call<JsonArray> getJsonArrayExercise(@Path("file") String file);
}
