package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmailExistsService {

    @GET("user/email/{email}")
    Call<Boolean> emailExists(@Path("email") String email);

}
