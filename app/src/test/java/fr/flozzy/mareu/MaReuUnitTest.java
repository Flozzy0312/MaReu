package fr.flozzy.mareu;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.flozzy.mareu.DI.DI;
import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.SERVICE.MaReuApiService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MaReuUnitTest {

    private Date datePicker;
    private MaReuApiService maReuApiService;
    private Calendar calendar;

    @Before
    public void setup() {
        maReuApiService = DI.getNewInstanceApiService();
    }
    /**
     * Test thats the generate listMmeeting is empty at the lauching of the application
     */

    @Test
    public void generateList()  {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int listSize = lMeetings.size();
        assertEquals( 9, listSize );
    }


    /**
     * Test thats the add meeting function creates a new meeting to the list with a empty list
     */
    @Test
    public void addListWithSuccess()  {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int debSize = lMeetings.size();

        Calendar mCalendarDeb = Calendar.getInstance();
        Calendar mCalendarFin = Calendar.getInstance();
        mCalendarDeb.set( 2021, 02, 12, 10, 00 );
        mCalendarDeb.set( 2021, 02, 12, 11, 00 );
        Date dateDebMeeting = new Date( mCalendarDeb.getTimeInMillis() );
        Date dateFinMeeting = new Date( mCalendarFin.getTimeInMillis() );
        Meeting aMeeting = new Meeting( System.currentTimeMillis(),
                1,
                "Sujet de la reunion",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 3, 4, 6 ) );

        maReuApiService.addMeeting(aMeeting );
        assertTrue( lMeetings.contains( aMeeting ) );

        int finSize = lMeetings.size();
        int diffSize = finSize - debSize;
        assertEquals( 1, diffSize );

    }
    /**
     * Test thats the add meeting function creates a new meeting to the list with a empty list
     */
    @Test
    public void removeListWithSuccess() {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int debSize = lMeetings.size();

        Meeting meetingToDelete = lMeetings.get(0);
        maReuApiService.deleteMeeting(meetingToDelete);

        assertFalse( lMeetings.contains( meetingToDelete ) );
        int finSize = lMeetings.size();
        int diffSize = debSize - finSize;
        assertEquals( 1, diffSize );

    }

    /**
     * Test  the FilterMeeting by Room  with a initlist with 7 items
     */
    @Test
    public void FilterbyRoom() {
        List<Meeting> lMeetings = maReuApiService.getMeetings();
        List<Integer> lRoomSelectedId = Arrays.asList( 1, 9 );
        List<Meeting> lMeetingFiltered = new ArrayList<>();

        lMeetingFiltered = maReuApiService.lMeetingsFilteredId( lRoomSelectedId );

        int nbMeetingFiltered = lMeetingFiltered.size();
        assertEquals( 2, nbMeetingFiltered );
    }

    /**
     * Test  the FilterMeeting by Date with a initlist with 7 items
     */
    @Test
    public void FilterByDate() {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();

        Calendar mCalendarPicker = Calendar.getInstance();
        mCalendarPicker.set( 2022, 06, 20, 10, 00 );
        List<Meeting> lMeetingsFiltered = maReuApiService.filterMeetingsByDate( 2022, 06, 20 );
        int nbMeetingSelected = lMeetingsFiltered.size();
        assertEquals( 0, nbMeetingSelected );
    }

    /**
     * Test  the availability of a room
     */
    @Test
    public void checkRoomAvailability() {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int idRoom = 1;
        Calendar mCalendarDeb = Calendar.getInstance();
        Calendar mCalendarFin = Calendar.getInstance();
        mCalendarDeb.set( 2022, 06, 12, 10, 30 );
        mCalendarFin.set( 2022, 06, 12, 11, 30 );
        Date mStartDate = new Date( mCalendarDeb.getTimeInMillis() );
        Date mEndDate = new Date( mCalendarFin.getTimeInMillis() );
        boolean available = maReuApiService.checkRoomAvailability( idRoom, mStartDate, mEndDate );
        assertEquals( true, available );

        mCalendarDeb = Calendar.getInstance();
        mCalendarFin = Calendar.getInstance();
        mCalendarDeb.set( 2022, 06, 01, 07, 30 );
        mCalendarFin.set( 2022, 06, 01, 10, 30 );
        mStartDate = new Date( mCalendarDeb.getTimeInMillis() );
        mEndDate = new Date( mCalendarFin.getTimeInMillis() );
        available = maReuApiService.checkRoomAvailability( idRoom, mStartDate, mEndDate );
        assertEquals( true, available );
    }


}