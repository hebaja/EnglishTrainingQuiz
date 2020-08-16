package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragment;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class QuizScreenTest {
    private NavController mockNavController;

    @Before
    public void setUp() {
        Bundle bundle = new Bundle();
        bundle.putInt("chosen_level", 0);
        bundle.putInt("chosen_option", 0);
        mockNavController = Mockito.mock(NavController.class);
        FragmentScenario<QuizFragment> fragmentScenario = FragmentScenario.launchInContainer(QuizFragment.class, bundle, R.style.AppTheme, new FragmentFactory());
        fragmentScenario.onFragment(fragment -> Navigation.setViewNavController(fragment.requireView(), mockNavController));
    }

    @Test
    public void shouldDisplayViewInQuizFragment_whenQuizFragmnetIsLaunched() {
        onView(withId(R.id.quiz_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.quiz_question_prompt)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_button_option_a), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_button_option_b), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_button_option_c), isClickable())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_score), withText("Score:"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_score_counter), withText("0"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_button_back_to_menu), withText("Back to main menu"), isClickable())).check(matches(isDisplayed()));
        onView(withId(R.id.quiz_next_clickable_cardview)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void should_DisplayWriteOrWrongOptionWarning_whenAButtonIsTapped() {
        onView(allOf(withId(R.id.quiz_button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.quiz_chosen_option_warning)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_chosen_option_warning_textview), anyOf(withText("Right answer"), withText("Wrong answer")))).check(matches(isDisplayed()));
        onView(
                allOf(
                        withId(R.id.quiz_chosen_option_warning_view),
                        withContentDescription("Chosen option warning icon"),
                        anyOf(
                                withTagValue(equalTo(R.drawable.right_answer_circle_outline)),
                                withTagValue(equalTo(R.drawable.wrong_answer_circle_outline)))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_DisplayNextQuestionClicableCardview_WhenAButtonIsTapped() {
        onView(allOf(withId(R.id.quiz_button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.quiz_next_clickable_cardview)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.quiz_next_clickable_cardview_textview), withText("Next question"))).check(matches(isDisplayed()));
        onView(withId(R.id.quiz_next_clickable_cardview_view)).check(matches(isDisplayed()));
        onView(withTagValue(equalTo(R.drawable.next_circle_outline))).check(matches(isDisplayed()));
    }

    @Test
    public void should_NavigateToFinalScoreFragment_whenButtonGoBackToMenuIsTapped() {
        onView(allOf(withId(R.id.quiz_button_back_to_menu), withText("Back to main menu"), isClickable())).perform(click());
        onView(allOf(withText("Do you really want to go back to main menu? Your current score will be lost."), withId(android.R.id.message))).check(matches(isDisplayed()));
        onView(allOf(withText("Ok"), withId(android.R.id.button1))).check(matches(isDisplayed()));
        onView(allOf(withText("Cancel"), withId(android.R.id.button2))).check(matches(isDisplayed()));
        onView(allOf(withText("Ok"), withId(android.R.id.button1))).check(matches(isDisplayed())).perform(click());
        verify(mockNavController).navigate(QuizFragmentDirections.actionGlobalMenuLevels());
    }

    @Test
    public void should_NavigateToFinalScoreFragment_whenLastQuestionIsDone() {
        int counter = 0;
        int score = 0;

        while(counter < 10) {
            onView(allOf(withId(R.id.quiz_button_option_a), isClickable())).check(matches(isDisplayed())).perform(click());
            try {
                onView(allOf(withId(R.id.quiz_chosen_option_warning_textview), withText("Right answer"))).check(matches(isDisplayed()));
                score++;
            } catch (NoMatchingViewException e){
                e.getMessage();
            }
            onView(allOf(withId(R.id.quiz_next_clickable_cardview_textview), anyOf(withText("Next question"), withText("Final score")))).check(matches(isDisplayed()));
            onView(withId(R.id.quiz_next_clickable_cardview)).check(matches(isDisplayed())).perform(click());
            counter++;
        }
        verify(mockNavController).navigate(QuizFragmentDirections.actionQuizToFinalScore(score, 0, 0));
    }
}