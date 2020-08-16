package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragment;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.FinalScoreFragmentDirections;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class FinalScreenTest {
    private NavController mockNavController;
    private FragmentScenario<FinalScoreFragment> fragmentScenario;
    private Context applicationContext;

    @Before
    public void setUp() {
        mockNavController = Mockito.mock(NavController.class);

        Bundle bundle = new Bundle();
        bundle.putInt("score", 0);
        bundle.putInt("chosenLevel", 0);
        bundle.putInt("chosenOption", 0);

        fragmentScenario = FragmentScenario.launchInContainer(FinalScoreFragment.class, bundle, R.style.AppTheme, new FragmentFactory() {
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                FinalScoreFragment finalScoreFragment = new FinalScoreFragment();
                finalScoreFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        Navigation.setViewNavController(finalScoreFragment.requireView(), mockNavController);
                    }
                });

                return finalScoreFragment;
            }
        });
        applicationContext = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void shouldDisplayViewsInFinalScoreFragment_whenFinalScoreFragmentIsLaunched() {
        onView(withId(R.id.final_score_parent)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_textview), withText("Your final score is:"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_counter), withText("0"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_button_try_again), withText("Try Again"), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.final_score_button_back_menu), withText("Back to main menu"), isClickable())).check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToQuizFragment_whenButtonTryAgainIsTapped() {
        onView(withId(R.id.final_score_button_try_again)).perform(click());
        verify(mockNavController).navigate(FinalScoreFragmentDirections.actionFinalScoreToQuiz(0, 0));
    }

    @Test
    public void shouldGoToMenuLevelsFragment_whenGoBackToMainMenuIsTapped() {
        onView(withId(R.id.final_score_button_back_menu)).perform(click());
        verify(mockNavController).navigate(FinalScoreFragmentDirections.actionGlobalMenuLevels());
    }

    @Test
    public void shouldGoToFeedbackFragment_whenFeedbackMenuItemIsTapped() {
        ActionMenuItem actionMenuItem = new ActionMenuItem(applicationContext, 0, R.id.menu_final_score_feedback, 0, 0, null);
        fragmentScenario.onFragment(fragment ->
                fragment.onOptionsItemSelected(actionMenuItem));
        verify(mockNavController).navigate(FinalScoreFragmentDirections.actionFinalScoreToFeedback());
    }

    @Test
    public void shouldGoToAboutScreenFragment_whenAboutMenuItemIsTapped() {
        ActionMenuItem actionMenuItem = new ActionMenuItem(applicationContext, 0, R.id.menu_final_score_about, 0, 0, null);
        fragmentScenario.onFragment(fragment ->
                fragment.onOptionsItemSelected(actionMenuItem));
        verify(mockNavController).navigate(FinalScoreFragmentDirections.actionFinalScoreToAboutPage());
    }
}