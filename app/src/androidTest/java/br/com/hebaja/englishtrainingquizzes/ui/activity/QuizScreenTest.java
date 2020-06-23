package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;
import android.util.Log;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.TestBase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.SCORE_KEY;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class QuizScreenTest extends TestBase {

    @Rule
    public ActivityTestRule<QuizActivity> quizActivity = new ActivityTestRule(QuizActivity.class, true, false);

    @Before
    public void setup() {
        Intent intent = new Intent().putExtra(CHOSEN_OPTION_KEY, 0);
        quizActivity.launchActivity(intent);
        setIdButtonQuit(R.id.button_quit_quiz_activity);
    }

    @Test
    public void should_DisplayAllViewsInQuizActivity_WhenQuizActivityIsStarted() {
        onView(withText("English Training Quizzes")).check(matches(isDisplayed()));
        onView(withId(R.id.id_question)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_option_a), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_option_b), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_option_c), isClickable())).check(matches(isDisplayed()));
        onView(withId(R.id.score)).check(matches(isDisplayed()));
        onView(withId(R.id.score_counter)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_quit_quiz_activity), isClickable())).check(matches(isDisplayed()));
        onView(withId(R.id.next_clickable_cardview)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void should_DisplayQuitDialog_WhenQuitButtonIsPressed() {
        tryToDisplayQuitDialog();
    }

    @Test
    public void should_QuitApp_WhenQuitOptionIsTappedInQuitDialog() {
        tryToDisplayQuitDialog();
        getQuitDialogOption().perform(click());
        assertTrue(quizActivity.getActivity().isFinishing());
    }

    @Test
    public void shouldNot_QuitApp_WhenCancelOptionIsTappedInQuitDialog() {
        tryToDisplayQuitDialog();
        getCancelDialogOption().perform(click());
        onView(allOf(withText("English Training Quizzes"), isDisplayed()));
    }

    @Test
    public void should_DisplayWriteOrWrongOptionWarning_whenAButtonIsTapped() {
        onView(allOf(withId(R.id.button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.chosen_option_warning)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.chosen_option_warning_textview), anyOf(withText("Right answer"), withText("Wrong answer")))).check(matches(isDisplayed()));

        onView(
                allOf(
                        withId(R.id.chosen_option_warning_view),
                        withContentDescription("Chosen option warning icon"),
                        anyOf(
                                withTagValue(equalTo(R.drawable.right_answer_circle_outline)),
                                withTagValue(equalTo(R.drawable.wrong_answer_circle_outline)))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_DisplayNextQuestionClicableCardview_WhenAButtonIsTapped() {
        onView(allOf(withId(R.id.button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.next_clickable_cardview)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.next_clickable_cardview_textview), withText("Next question"))).check(matches(isDisplayed()));
        onView(withId(R.id.next_clickable_cardview_view)).check(matches(isDisplayed()));
        onView(withTagValue(equalTo(R.drawable.next_circle_outline))).check(matches(isDisplayed()));
    }

    @Test
    public void should_GoToFinalScoreActivity() {
        init();
        Matcher<Intent> intentMatcher = tryToConfigureIntentOfActivity(FinalScoreActivity.class.getName());

        int counter = 0;
        while(counter < 10) {
            onView(allOf(withId(R.id.button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
            onView(allOf(withId(R.id.next_clickable_cardview_textview), anyOf(withText("Next question"), withText("Final score")))).check(matches(isDisplayed()));
            onView(withId(R.id.next_clickable_cardview)).check(matches(isDisplayed())).perform(click());
            counter++;
        }

//        onView(allOf(withId(R.id.next_clickable_cardview_textview), withText("Final Socre"))).check(matches(isDisplayed()));

        intended(allOf(intentMatcher, hasExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, 0), hasExtraWithKey(SCORE_KEY)));
        release();
    }
}
