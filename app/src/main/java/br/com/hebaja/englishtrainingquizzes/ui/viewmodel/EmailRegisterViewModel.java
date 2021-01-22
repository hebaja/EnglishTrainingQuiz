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

public class EmailRegisterViewModel extends AndroidViewModel {

    public static final String EMAIL_RECEIVED = "email_received";
    public static final String USERNAME_RECEIVED = "username_received";
    private MutableLiveData<String[]> emailLiveData;
    private SharedPreferences preferences;

    public EmailRegisterViewModel(@NonNull Application application) {
        super(application);
        preferences = getApplication().getSharedPreferences("email_shared_preferences", Context.MODE_PRIVATE);
    }

    public void setEmail(String emailReceived) {
        String[] emailSplit = emailReceived.split("@");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_RECEIVED, emailReceived);
        editor.putString(USERNAME_RECEIVED, emailSplit[0]);
        editor.apply();
    }

    public LiveData<String[]> getEmail() {
        if(emailLiveData == null) {
            emailLiveData = new MutableLiveData<>();
        }
        String email = preferences.getString(EMAIL_RECEIVED, null);
        String username = preferences.getString(USERNAME_RECEIVED, null);
        emailLiveData.setValue(new String[]{email, username});
        return emailLiveData;
    }
}
