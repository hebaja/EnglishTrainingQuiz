package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FeedbackFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class FeedbackScreenTest {

    private Context applicationContext;
    private FragmentScenario<FeedbackFragment> fragmentScenario;
    private FragmentActivity fragmentActitivy;

    @Rule
    public IntentsTestRule<MainActivity> mainActivity = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        fragmentScenario = FragmentScenario.launchInContainer(FeedbackFragment.class, null, R.style.AppTheme, new FragmentFactory());
        applicationContext = ApplicationProvider.getApplicationContext();
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void shouldDisplayViews_whenFeedbackFragmentIsLaunched() {
        onView(withId(R.id.feedback_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.feedback_banner_image_view)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.feedback_banner_message), withText("Leave your feedback! ;)"))).check(matches(isDisplayed()));
        onView(withId(R.id.feedback_message_input_layout)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.feedback_message_edit_text), withHint("Write your feedback here"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.feedback_instructions_layout), withText("Send your experience and doubts about English Training Quizzes. " +
                "We will use the information to enhance the user experience. You must have an e-mail client (such as Gmail) configured on your device.")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_DisplayWarningToast_OnTryingToSendEmptyMessage() {
        ActionMenuItem actionMenuItem = new ActionMenuItem(applicationContext, 0, R.id.feedback_send, 0, 0, null);
        fragmentScenario.onFragment(fragment -> {
            fragment.onOptionsItemSelected(actionMenuItem);
            fragmentActitivy = fragment.requireActivity();
        });
        onView(withText("You can't send empty feedback")).inRoot(withDecorView(not(is(getActivity(fragmentActitivy).getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void should_SendFeedbackMessage_WhenMessageIsTypedAndMenuButtonIsTapped() {
        ActionMenuItem actionMenuItem = new ActionMenuItem(applicationContext, 0, R.id.feedback_send, 0, 0, null);
        onView(allOf(withId(R.id.feedback_message_edit_text), isDisplayed())).perform(typeText("message"), closeSoftKeyboard());
        fragmentScenario.onFragment(fragment ->
                fragment.onOptionsItemSelected(actionMenuItem));
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(hasAction(Intent.ACTION_CHOOSER)).respondWith(activityResult);
        intended(allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(Intent.EXTRA_TITLE, "Choose an email client"), hasExtraWithKey(Intent.EXTRA_INTENT)));
    }
}