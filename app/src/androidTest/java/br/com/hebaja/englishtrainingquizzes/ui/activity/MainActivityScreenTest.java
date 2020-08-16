package br.com.hebaja.englishtrainingquizzes.ui.activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.hebaja.englishtrainingquizzes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule(MainActivity.class, true, true);


    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void shouldDisplayMainActivityViews_whenMainActivityIsLaunched() {
        onView(withId(R.id.main_activity_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.main_activity_nav_host)).check(matches(isDisplayed()));
        onView(withId(R.id.linear_layout_button_quit)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.main_activity_button_quit), withText("Quit"))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayQuitDialog_whenButtonQuitIsTapped() {
        onView(allOf(withId(R.id.main_activity_button_quit), withText("Quit"))).perform(click());
        onView(allOf(withText("Do you really want to quit?"), withId(android.R.id.message))).check(matches(isDisplayed()));
        onView(allOf(withText("Quit"), withId(android.R.id.button1))).check(matches(isDisplayed()));
        onView(allOf(withText("Cancel"), withId(android.R.id.button2))).check(matches(isDisplayed()));
    }

    @Test
    public void should_QuitApp_WhenQuitOptionIsTappedInQuitDialog() {
        onView(allOf(withId(R.id.main_activity_button_quit), withText("Quit"))).perform(click());
        onView(allOf(withText("Quit"), withId(android.R.id.button1))).check(matches(isDisplayed())).perform(click());

        assertTrue(mainActivityRule.getActivity().isFinishing());
    }

    @Test
    public void shouldNot_QuitApp_WhenCancelOptionIsTappedInQuitDialog() {
        onView(allOf(withId(R.id.main_activity_button_quit), withText("Quit"))).perform(click());
        onView(allOf(withText("Cancel"), withId(android.R.id.button2))).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.main_activity_parent)).check(matches(isDisplayed()));
    }
}
