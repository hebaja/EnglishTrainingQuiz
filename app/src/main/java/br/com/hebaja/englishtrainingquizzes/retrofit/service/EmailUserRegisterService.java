package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailUserRegisterService {

    @POST("api/user/register")
    Call<Boolean> register(@Body User user);
}
