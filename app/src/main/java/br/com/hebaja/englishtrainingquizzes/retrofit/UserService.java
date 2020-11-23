package br.com.hebaja.englishtrainingquizzes.retrofit;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("api/users/user/hebaja")
    Call<User> getUser();

}
