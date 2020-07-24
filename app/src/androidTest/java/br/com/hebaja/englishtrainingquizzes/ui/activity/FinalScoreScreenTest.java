package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.TestBase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_LEVEL_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.SCORE_KEY;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

public class FinalScoreScreenTest extends TestBase {

    @Rule
    public ActivityTestRule<FinalScoreActivity> finalScoreActivity = new ActivityTestRule(FinalScoreActivity.class, true, false);

    @Before
    public void setup() {
        Intent intent = new Intent();
        intent.putExtra(SCORE_KEY, 10);
        intent.putExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, 0);
        intent.putExtra(CHOSEN_LEVEL_TRY_AGAIN_KEY, 0);
        finalScoreActivity.launchActivity(intent);
    }

    @Test
    public void should_DisplayAllViewsInFinalScoreActivity_WhenFinalScoreActivityIsStarted() {
        onView(withText("English Training Quizzes")).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_activity_textview), withText("Your final score is:"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_activity_score_counter), withText("10"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_activity_button_quit), withText("Quit"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_activity_button_try_again), withText("Try Again"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_activity_button_back_main_menu), withText("Back to main menu"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.menu_final_score_activity_feedback), withContentDescription("Access feedback"), isClickable())).check(matches(isDisplayed()));
    }

    @Test
    public void should_ReturnToTheBeginningOfQuizzActivity_WhenTryAgainButtonIsTapped() {
        onView(allOf(withId(R.id.final_score_activity_button_try_again), withText("Try Again"), isClickable())).perform(click());
        onView(withId(R.id.id_question)).check(matches(isDisplayed()));
    }

    @Test
    public void should_ReturnToMenuActivity_WhenBackToMainMenuButtonIsTapped() {
        onView(allOf(withId(R.id.final_score_activity_button_back_main_menu), withText("Back to main menu"), isClickable())).perform(click());
        onView(withId(R.id.id_prompt_menu)).check(matches(isDisplayed()));
    }

    @Test
    public void should_QuitApp_WhenQuitButtonIsPressed() {
        onView(allOf(withId(R.id.final_score_activity_button_quit), withText("Quit"), isClickable())).perform(click());
        finalScoreActivity.getActivity().isFinishing();
    }

    @Test
    public void should_GoToFeedbackActivity_WhenMenuButtonIsTapped() {
        onView(allOf(withId(R.id.menu_final_score_activity_feedback), withContentDescription("Access feedback"), isClickable())).perform(click());
        onView(withId(R.id.banner_layout_feedback_activity)).check(matches(isDisplayed()));
    }
}