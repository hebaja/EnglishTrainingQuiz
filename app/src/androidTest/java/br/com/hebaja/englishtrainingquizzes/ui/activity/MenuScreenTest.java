package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.TestBase;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static br.com.hebaja.englishtrainingquizzes.Constants.CHOSEN_OPTION_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MenuScreenTest extends TestBase{

    @Rule
    public ActivityTestRule<MenuActivity> menuActivity = new ActivityTestRule(MenuActivity.class, true, false);

    @Before
    public void setUp() {
        menuActivity.launchActivity(new Intent());
        setIdButtonQuit(R.id.button_quit_menu_activity);
    }

    @Test
    public void should_DisplayPromptAndListOfOptions_WhenMenuActivityIsStarted() {
        onView(withText("Menu")).check(matches(isDisplayed()));
        onView(withId(R.id.id_prompt_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.button_level_a1_a2)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_quit_menu_activity), withText("Quit"), isClickable())).check(matches(isDisplayed()));
    }

    @Test
    public void should_DisplayQuitDialog_WhenQuitButtonIsPressed() {
        tryToDisplayQuitDialog();

    }

    @Test
    public void should_QuitApp_WhenQuitOptionIsTappedInQuitDialog() {
        tryToDisplayQuitDialog();
        getQuitDialogOption().perform(click());
        assertTrue(menuActivity.getActivity().isFinishing());
    }

    @Test
    public void shouldNot_QuitApp_WhenCancelOptionIsTappedInQuitDialog() {
        tryToDisplayQuitDialog();
        getCancelDialogOption().perform(click());
        onView(allOf(withText("Menu"), isDisplayed()));
    }

    @Test
    public void should_GoToQuizzActivity_WhenAnOptionIsPressedInListView() {
        init();
        Matcher<Intent> intentMatcher = tryToConfigureIntentOfActivity(QuizActivity.class.getName());

        onView(allOf(withId(R.id.button_level_a1_a2), isClickable())).perform(click());
        onData(anything()).inAdapterView(withId(R.id.list_button_options_listview)).atPosition(0).perform(click());
        intended(allOf(intentMatcher, hasExtra(CHOSEN_OPTION_KEY, 0)));

        release();
    }
}
