package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private static final String LOGIN_KEY = "logged";
    public static final String SHARED_PREFERENCES_KEY = "shared_preferences";

    private MutableLiveData<Boolean> loggedLiveData;
    private final SharedPreferences preferences;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        preferences = getApplication().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void login() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOGIN_KEY, true);
        editor.apply();
    }

    public void logoff() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOGIN_KEY, false);
        editor.apply();
    }

    public LiveData<Boolean> isLoggedIn() {
        if (loggedLiveData == null) {
            loggedLiveData = new MutableLiveData<>();
        }
        boolean logged = preferences.getBoolean(LOGIN_KEY, false);
        loggedLiveData.setValue(logged);
        return loggedLiveData;
    }
}