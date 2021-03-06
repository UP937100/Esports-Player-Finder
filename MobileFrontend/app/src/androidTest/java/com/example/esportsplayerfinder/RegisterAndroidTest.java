package com.example.esportsplayerfinder;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterAndroidTest {

    @Rule
    public ActivityScenarioRule<Register> activityRule =
            new ActivityScenarioRule<>(Register.class);
    // Assigns Register activity to be subject of the tests.

    @Test
    public void test_isActivityInView() {
        //Launches activity to test within.
        onView(withId(R.id.register)) //Get object on current view with ID = register
                .check(matches(isDisplayed())); //check if displayed
    }

    @Test
    public void test_isDashbarInView() {
        onView(withId(R.id.Dashbar))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_isRegisterButtonTextDisplayed() {
        onView(withId(R.id.btnCreateTeam))
                .check(matches(withText("Register")));
    }

    @Test
    public void test_switchToLogin() {
        onView(withId(R.id.linkToRegister))
                .perform(click());
        onView(withId(R.id.login))
                .check(matches(isDisplayed()));

    }
}