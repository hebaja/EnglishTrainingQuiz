package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.AboutPageFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AboutScreenTest {

    @Before
    public void setUp() {
        Bundle bundle = new Bundle();
        FragmentFactory fragmentFactory = new FragmentFactory();
        FragmentScenario.launchInContainer(AboutPageFragment.class, bundle, R.style.AppTheme, fragmentFactory);
    }

    @Test
    public void shouldLoadAboutPageContainer_whenAboutPageFragmentIsLaunched() {
        onView(withId(R.id.about_page_parent)).check(matches(isDisplayed()));
    }
}
