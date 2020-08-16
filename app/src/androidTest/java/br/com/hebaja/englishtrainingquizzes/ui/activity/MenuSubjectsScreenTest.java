package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.os.Bundle;

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
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragment;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.MenuSubjectsFragmentDirections;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MenuSubjectsScreenTest {

    private NavController mockNavController;
    private FragmentScenario<MenuSubjectsFragment> fragmentScenario;

    @Before
    public void setUp() {
        mockNavController = Mockito.mock(NavController.class);
        Bundle bundle = new Bundle();
        bundle.putInt("key", 0);
        fragmentScenario = FragmentScenario.launchInContainer(MenuSubjectsFragment.class, bundle, R.style.AppTheme, new FragmentFactory());
    }

    @Test
    public void shouldDisplayViewsInMenuSubjects_whenMenuSubjectsFragmentIsLaunched() {
        onView(withId(R.id.menu_subjects_parent)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.menu_subjects_prompt), withText("Choose a subject"))).check(matches(isDisplayed()));
        onView(withId(R.id.list_buttons_subjects_list_view)).check(matches(isDisplayed()));

        fragmentScenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), mockNavController));
        onData(anything()).inAdapterView(withId(R.id.list_buttons_subjects_list_view)).atPosition(0).perform(click());
        verify(mockNavController).navigate(MenuSubjectsFragmentDirections.actionMenuSubjectsToQuiz(0, 0));
    }
}
