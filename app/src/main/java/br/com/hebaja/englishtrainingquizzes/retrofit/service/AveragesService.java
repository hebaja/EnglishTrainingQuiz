package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Average;
import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AveragesService {

    @GET("api/averages/email/{email}")
    Call<List<Average>> getAveragesByEmail(@Path("email") String email);

    @GET("api/averages/uid/{uid}")
    Call<List<Average>> getAveragesByUid(@Path("uid") String uid);
}
