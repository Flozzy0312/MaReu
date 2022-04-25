package fr.flozzy.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static fr.flozzy.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.flozzy.mareu.SERVICE.MaReuDummyApiGenerator;
import fr.flozzy.mareu.UI.MainActivity;
import fr.flozzy.mareu.utils.DeleteViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    private static final int INITIAL_LIST_SIZE = MaReuDummyApiGenerator.generateMeeting().size();

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void MaReuList_shouldNotBeEmpty() {
        // Faites d'abord défiler jusqu'à la position voulu puis cliquer dessus.
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void addMeetingWithSuccess() {
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE));
        onView(withId(R.id.button_add_meeting))
                .perform(click());
        onView(withId(R.id.edit_text_add_meeting_subject))
                .perform(click());
        onView(withId(R.id.edit_text_add_meeting_subject))
                .perform(typeText("Nouvelle réunion"));
        onView(withId(R.id.text_add_meeting_datepicker))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 6, 13));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.text_add_meeting_timepicker))
                .perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(15, 0));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.numberpicker_add_meeting_duration_hours_))
                .perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_CENTER, Press.FINGER, 1, 0));
        onView(withId(R.id.numberpicker_add_meeting_duration_minutes_))
                .perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_CENTER, Press.FINGER, 1, 0));

        onView(withId(R.id.spinner_add_meeting_room))
                .perform(click());
        onData(anything()).atPosition(1).perform(click());

        onView(withId(R.id.autocomplete_text_add_meeting_participant))
                .perform(typeText("f"));
        onData(anything()).atPosition(1).perform(click());

        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(click());
        onView(withText(R.string.menu_creation_meeting))
                .perform(click());
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE + 1));
    }
    @Test
    public void addMeetingWithMissingSubjectThrowsToast() {
        onView(withId(R.id.button_add_meeting))
                .perform(click());

        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(doubleClick());

        onView(withText(R.string.toast_subject_empty)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE));
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE - 1));
    }

    @Test
    public void filterMeetingByDate() {
        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(click());
        onView(withText(R.string.menu_filter_date))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 03, 13));
        onView(withText(R.string.filter_ok_text)).perform(click());
        onView(withId(R.id.list_recycler_view)).check(withItemCount(1));
        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(click());
        onView(withText(R.string.menu_filter_date))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 5, 6));
        onView(withText(R.string.filter_ok_text)).perform(click());
        onView(withId(R.id.list_recycler_view)).check(withItemCount(0));
    }

    @Test
    public void filterMeetingByRoom() {
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE));
        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(click());
        onView(withText(R.string.menu_filter_room))
                .perform(click());
        String room = "Salle 1";
        onView(withText(room)).perform(click());
        onView(withText(R.string.filter_ok_text)).perform(click());
        onView(withId(R.id.list_recycler_view)).check(withItemCount(1));
        onView(withId(R.id.menu_overflow_button_create_meeting))
                .perform(click());
        onView(withText(R.string.menu_filter_room))
                .perform(click());
        onView(withText(R.string.filter_reset_text)).perform(click());
        onView(withId(R.id.list_recycler_view)).check(withItemCount(INITIAL_LIST_SIZE));
    }

}