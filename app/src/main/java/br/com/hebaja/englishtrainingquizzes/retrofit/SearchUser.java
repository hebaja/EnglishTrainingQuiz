package br.com.hebaja.englishtrainingquizzes.retrofit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.User;
import retrofit2.Call;
import retrofit2.Response;

public class SearchUser {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<User> getUser() {

        return executor.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {

                UserService service = new BaseRetrofit().getUserService();
                Call<User> userCall = service.getUser();

                try {
                    Response<User> response = userCall.execute();
                    return response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
