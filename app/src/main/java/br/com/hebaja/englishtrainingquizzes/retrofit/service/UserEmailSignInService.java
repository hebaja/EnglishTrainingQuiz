package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserEmailSignInService {

    @POST("api/user/email/{email}")
    Call<User> sendUser(@Body User user, @Path("email") String email);
}
