package br.com.hebaja.englishtrainingquizzes.model;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class ButtonNext {

    private CardView cardView;
    private TextView textView;

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public CardView getCardView() {
        return cardView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
