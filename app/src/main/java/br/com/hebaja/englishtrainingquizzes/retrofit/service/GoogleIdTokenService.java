package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GoogleIdTokenService {

    @POST("user/google/{idToken}")
    Call<User> sendGoogleIdToken(@Path("idToken") String idToken);

}
