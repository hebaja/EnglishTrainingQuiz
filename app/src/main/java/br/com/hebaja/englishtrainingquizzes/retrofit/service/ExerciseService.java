package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseService {

    @GET("api/json/{jsonFile}")
    Call<JsonArray> getExercise(@Path("jsonFile") String jsonFile);
}
