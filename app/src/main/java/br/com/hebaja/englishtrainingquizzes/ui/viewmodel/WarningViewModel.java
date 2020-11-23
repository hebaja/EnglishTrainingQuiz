package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.hebaja.englishtrainingquizzes.model.WarningView;

public class WarningViewModel extends AndroidViewModel {

    private MutableLiveData<WarningView> warningViewLiveData;
    private WarningView chosenOptionWarning;
//    private int imageRes;

    public WarningViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<WarningView> setVisibilityState(WarningView chosenOptionWarning) {
        checkIfDataIsNull();

        this.chosenOptionWarning = chosenOptionWarning;
        chosenOptionWarning.getCardView().setVisibility(View.INVISIBLE);
        warningViewLiveData.setValue(chosenOptionWarning);
        return warningViewLiveData;
    }

    private void checkIfDataIsNull() {
        if (warningViewLiveData == null) {
            warningViewLiveData = new MutableLiveData<>();
        }
    }

    public LiveData<WarningView> setState(WarningView chosenOptionWarning, int imageRes, int tag, String answer, int color) {
        checkIfDataIsNull();
        this.chosenOptionWarning = chosenOptionWarning;
//        this.imageRes = imageRes;
        chosenOptionWarning.setImageRes(imageRes);
        configureChosenOptionWarning(imageRes, tag, answer, color);
        chosenOptionWarning.getCardView().setVisibility(View.VISIBLE);
        warningViewLiveData.setValue(chosenOptionWarning);
        return warningViewLiveData;
    }

    public LiveData<WarningView> getState() {
        checkIfDataIsNull();
        warningViewLiveData.setValue(chosenOptionWarning);
        return warningViewLiveData;
    }

    public LiveData<WarningView> reset() {
        chosenOptionWarning.getCardView().setVisibility(View.INVISIBLE);
        warningViewLiveData.setValue(chosenOptionWarning);

        return warningViewLiveData;
    }

    private void configureChosenOptionWarning(int imageRes, int tag, String answer, int color) {
        chosenOptionWarning.getImageView().setImageResource(imageRes);
        chosenOptionWarning.getImageView().setTag(tag);
        chosenOptionWarning.getTextView().setText(answer);
        setColorOfTextView(color);
    }

    private void setColorOfTextView(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chosenOptionWarning.getTextView().setTextColor(getApplication().getColor(color));
        }
    }
}
