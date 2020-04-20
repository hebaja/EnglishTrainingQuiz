package br.com.hebaja.phrasalverbs.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.hebaja.phrasalverbs.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMenuActivity();
            }
        },1500);
    }

    private void showMenuActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
