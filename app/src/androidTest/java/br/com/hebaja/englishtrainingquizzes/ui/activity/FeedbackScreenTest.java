package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.hebaja.englishtrainingquizzes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.FeedbackActivity.START_EMAIL_CLIENT_ACTIVITY_MESSAGE;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class FeedbackScreenTest {

    @Rule
    public ActivityTestRule<FeedbackActivity> feedbackActivity = new ActivityTestRule(FeedbackActivity.class, true, false);

    @Rule
    public IntentsTestRule<FeedbackActivity> intentFeedback = new IntentsTestRule<>(FeedbackActivity.class);

    @Before
    public void setup() {
        Intent intent = new Intent();
        feedbackActivity.launchActivity(intent);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void should_DisplayAllViewsInFeedbackActivity_WhenFeedbackActivityIsStarted() {
        onView(allOf(withId(R.id.menu_feedback_activity_send), withContentDescription("Send feedback"), isClickable())).check(matches(isDisplayed()));
        onView(withId(R.id.banner_layout_feedback_activity)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.banner_message_layout_feedback_activity), withText("Leave your feedback! ;)"))).check(matches(isDisplayed()));
        onView(withId(R.id.feedback_activity_message_input_layout)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.feedback_activity_message_edit_text), withHint("Write your feedback here"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.feedback_activity_instructions_layout), withText("Send your experience and doubts about English Training Quizzes. " +
                "We will use the information to enhance the user experience. You must have an e-mail client (such as Gmail) configured on your device.")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_DisplayWarningToast_OnTryingToSendEmptyMessage() {
        onView(allOf(withId(R.id.menu_feedback_activity_send), withContentDescription("Send feedback"), isClickable())).perform(click());
        onView(withText("You can't send empty feedback")).inRoot(withDecorView(not(is(getActivity(feedbackActivity.getActivity()).getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void should_SendFeedbackMessage_WhenMessageIsTypedAndMenuButtonIsTapped() {
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(hasAction(Intent.ACTION_CHOOSER)).respondWith(activityResult);

        onView(allOf(withId(R.id.feedback_activity_message_edit_text), isDisplayed())).perform(typeText("message"), closeSoftKeyboard());
        onView(withId(R.id.menu_feedback_activity_send)).perform(click());

        intended(allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(Intent.EXTRA_TITLE, "Choose an email client"), hasExtraWithKey(Intent.EXTRA_INTENT)));
    }
}