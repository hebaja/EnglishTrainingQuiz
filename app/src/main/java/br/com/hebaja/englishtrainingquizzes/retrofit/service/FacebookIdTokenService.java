package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FacebookIdTokenService {

    @POST("user/facebook/{idToken}")
    Call<User> sendFacebookIdToken(@Path("idToken") String idToken);

}
