package br.com.hebaja.englishtrainingquizzes.ui.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.ButtonNext;

import static br.com.hebaja.englishtrainingquizzes.Constants.FINAL_SCORE;
import static br.com.hebaja.englishtrainingquizzes.Constants.NEXT_QUESTION;

public class ButtonNextViewModel extends ViewModel {

    private MutableLiveData<ButtonNext> buttonNextLiveData;
    private ButtonNext buttonNext;

    public LiveData<ButtonNext> setVisibilityState(ButtonNext buttonNext) {
        checkIfDataIsNull();
        this.buttonNext = buttonNext;
        buttonNext.getCardView().setVisibility(View.INVISIBLE);
        buttonNextLiveData.setValue(buttonNext);
        return buttonNextLiveData;
    }

    public LiveData<ButtonNext> setState(ButtonNext buttonNext, int position) {
        checkIfDataIsNull();
        this.buttonNext = buttonNext;
        buttonNext.getCardView().setTag(R.drawable.next_circle_outline);
        if (position < 9) {
            buttonNext.getTextView().setText(NEXT_QUESTION);
        } else {
            buttonNext.getTextView().setText(FINAL_SCORE);
        }
        buttonNextLiveData.setValue(buttonNext);
        return buttonNextLiveData;
    }

    public LiveData<ButtonNext> getState() {
        checkIfDataIsNull();
        buttonNextLiveData.setValue(buttonNext);
        return buttonNextLiveData;
    }

    public LiveData<ButtonNext> reset() {
        buttonNext.getCardView().setVisibility(View.INVISIBLE);
        buttonNextLiveData.setValue(buttonNext);
        return buttonNextLiveData;
    }

    private void checkIfDataIsNull() {
        if (buttonNextLiveData == null) {
            buttonNextLiveData = new MutableLiveData<>();
        }
    }
}