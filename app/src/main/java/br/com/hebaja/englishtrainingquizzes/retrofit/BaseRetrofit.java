package br.com.hebaja.englishtrainingquizzes.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {

    private final ExerciseService exerciseService;
    private final UserService userService;


    public BaseRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.100:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        exerciseService = retrofit.create(ExerciseService.class);
        userService = retrofit.create(UserService.class);
    }

    public ExerciseService getExerciseService() {
        return exerciseService;
    }
    public UserService getUserService() { return userService; }
}
