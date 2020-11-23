package br.com.hebaja.englishtrainingquizzes.model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class WarningView {

    private int color;
    private int imageRes;
    private CardView cardView;
    private ImageView imageView;
    private TextView textView;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
