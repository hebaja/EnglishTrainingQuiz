package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.view.Menu;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import br.com.hebaja.englishtrainingquizzes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class SplashScreenTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashScreenActivity = new ActivityTestRule(SplashScreenActivity.class, true, false);

    @Test
    public void should_showSplashScreen() {
        splashScreenActivity.launchActivity(new Intent());
        onView(withId(R.id.splash_screen_activity_imageview)).check(matches(isDisplayed()));
    }

}
