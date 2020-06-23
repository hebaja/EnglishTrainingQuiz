package br.com.hebaja.englishtrainingquizzes.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.activity.QuizActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public abstract class TestBase {

    private ViewInteraction quitDialogOption;
    private ViewInteraction cancelDialogOption;
    private int idButtonQuit;

    protected void setIdButtonQuit(int id) {
        idButtonQuit = id;
    }

    protected void tryToDisplayQuitDialog() {
        onView(withId(idButtonQuit)).perform(click());
        onView(allOf(withText("Do you really want to quit?"), withId(android.R.id.message))).check(matches(isDisplayed()));
        quitDialogOption = onView(allOf(withText("Quit"), withId(android.R.id.button1))).check(matches(isDisplayed()));
        cancelDialogOption = onView(allOf(withText("Cancel"), withId(android.R.id.button2))).check(matches(isDisplayed()));
    }

    protected ViewInteraction getQuitDialogOption() {
        return quitDialogOption;
    }

    protected ViewInteraction getCancelDialogOption() {
        return cancelDialogOption;
    }

    protected Matcher<Intent> tryToConfigureIntentOfActivity(String activityName) {
        Matcher<Intent> intentMatcher = hasComponent(activityName);
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(intentMatcher).respondWith(activityResult);
        return intentMatcher;
    }
}
