package br.com.hebaja.englishtrainingquizzes.ui.activity;

//import androidx.fragment.app.testing.FragmentScenario;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuLevelsFragment;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuLevelsFragmentDirections;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MenuLevelsScreenTest {

    private NavController mockNavController;
    private FragmentScenario<MenuLevelsFragment> fragmentScenario;

    @Before
    public void setUp() {
        mockNavController = Mockito.mock(NavController.class);
        fragmentScenario = FragmentScenario.launchInContainer(MenuLevelsFragment.class, null, R.style.AppTheme, new FragmentFactory());
    }

    @Test
    public void shouldDisplayViews_whenMenuLevelsFragmentIsLaunched() {
        onView(withId(R.id.menu_levels_parent)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.menu_levels_prompt), withText("Choose a level"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_level_a1_a2), withText("Easy mode (A1/A2)"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_level_b1_b2), withText("Medium mode (B1/B2)"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.button_level_c1_c2), withText("Hard mode (C1/C2)"), isClickable())).check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToMenuSubjectsFragment_whenLevelsButtonIsTapped() {
        fragmentScenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), mockNavController));
        onView(allOf(withId(R.id.button_level_a1_a2), withText("Easy mode (A1/A2)"), isClickable())).check(matches(isDisplayed())).perform(click());
        verify(mockNavController).navigate(MenuLevelsFragmentDirections.actionMenuLevelsToMenuSubjects(0));
    }
}
