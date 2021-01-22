package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.hebaja.englishtrainingquizzes.model.User;

public class UserViewModel extends AndroidViewModel {

    public static final String USERNAME_RECEIVED = "username_received";
    public static final String EMAIL_RECEIVED = "email_received";
    public static final String SHARED_PREFERENCES_KEY = "user_shared_preferences";
    private MutableLiveData<User> userLiveData;
    private final SharedPreferences preferences;

    public UserViewModel(@NonNull Application application) {
        super(application);
        preferences = getApplication().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void setUserLiveData(User user) {
        checkIfDataIsNull();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_RECEIVED, user.getUsername());
        editor.putString(EMAIL_RECEIVED, user.getEmail());
        editor.apply();
    }

    public LiveData<User> getUserLiveData() {
        checkIfDataIsNull();
        String username = preferences.getString(USERNAME_RECEIVED, null);
        String email = preferences.getString(EMAIL_RECEIVED, null);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        userLiveData.setValue(user);
        return userLiveData;
    }

    private void checkIfDataIsNull() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
        }
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}