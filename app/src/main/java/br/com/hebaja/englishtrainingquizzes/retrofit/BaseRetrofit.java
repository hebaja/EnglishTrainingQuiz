package br.com.hebaja.englishtrainingquizzes.retrofit;

import br.com.hebaja.englishtrainingquizzes.retrofit.service.AveragesService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.EmailExistsService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.EmailUserPasswordResetService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.FacebookIdTokenService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.GoogleIdTokenService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.EmailUserRegisterService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.SaveExerciseService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.UserEmailSignInService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {

    private final EmailUserRegisterService emailUserRegisterService;
    private final EmailExistsService emailExistsService;
    private final SaveExerciseService saveExerciseService;
    private final AveragesService averagesService;
    private final GoogleIdTokenService googleIdTokenService;
    private final FacebookIdTokenService facebookIdTokenService;
    private final UserEmailSignInService userEmailSignInService;
    private final EmailUserPasswordResetService emailUserPasswordResetService;

    public BaseRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://englishtrainingquizzes.herokuapp.com/")
                .baseUrl("http://192.168.1.100:8080")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        emailUserRegisterService = retrofit.create(EmailUserRegisterService.class);
        emailExistsService = retrofit.create(EmailExistsService.class);
        saveExerciseService = retrofit.create(SaveExerciseService.class);
        averagesService = retrofit.create(AveragesService.class);
        googleIdTokenService = retrofit.create(GoogleIdTokenService.class);
        facebookIdTokenService = retrofit.create(FacebookIdTokenService.class);
        userEmailSignInService = retrofit.create(UserEmailSignInService.class);
        emailUserPasswordResetService = retrofit.create(EmailUserPasswordResetService.class);
    }

    public EmailUserRegisterService getEmailUserRegisterService() { return emailUserRegisterService; }
    public EmailExistsService getEmailExistsService() { return emailExistsService; }
    public SaveExerciseService getSaveExerciseService() { return saveExerciseService; }
    public AveragesService getAveragesService() { return averagesService; }
    public GoogleIdTokenService getGoogleIdTokenService() { return googleIdTokenService; }
    public FacebookIdTokenService getFacebookIdTokenService() { return facebookIdTokenService; }
    public UserEmailSignInService getUserEmailSignInService() { return userEmailSignInService; }
    public EmailUserPasswordResetService getEmailUserPasswordResetService() { return emailUserPasswordResetService; }
}
