package fr.flozzy.mareu;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
    public void addMeetingWithSuccess()  {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int debSize = lMeetings.size();

        Calendar mCalendarDeb = Calendar.getInstance();
        Calendar mCalendarFin = Calendar.getInstance();
        mCalendarDeb.set( 2021, 02, 12, 10, 00 );
        mCalendarFin.set( 2021, 02, 12, 11, 00 );
        Date dateDebMeeting = new Date( mCalendarDeb.getTimeInMillis() );
        Date dateFinMeeting = new Date( mCalendarFin.getTimeInMillis() );
        Meeting aMeeting = new Meeting( System.currentTimeMillis(),
                1,
                "Sujet de la reunion",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 3, 4, 6 ) );

        maReuApiService.addMeeting(aMeeting );
        lMeetings = maReuApiService.getMeetings();

        assertTrue( lMeetings.contains( aMeeting ) );
        assertEquals( debSize +1, lMeetings.size() );

    }
    /**
     * Test thats the add meeting function creates a new meeting to the list with a empty list
     */
    @Test
    public void removeMeetingWithSuccess() {
        List<Meeting> lMeetings =  maReuApiService.getMeetings();
        int debSize = lMeetings.size();

        Meeting meetingToDelete = lMeetings.get(0);
        maReuApiService.deleteMeeting(meetingToDelete);
        lMeetings = maReuApiService.getMeetings();

        assertFalse( lMeetings.contains( meetingToDelete ) );
        assertEquals( debSize-1, lMeetings.size() );

    }

    /**
     * Test  the FilterMeeting by Room  with a initlist with 7 items
     */
    @Test
    public void filterByRoom() {
        List<Integer> lRoomSelectedId = Arrays.asList( 1, 9 );
        List<Meeting> lMeetingFiltered;

        lMeetingFiltered = maReuApiService.meetingsByRoomIds( lRoomSelectedId );

        int nbMeetingFiltered = lMeetingFiltered.size();
        assertEquals( 2, nbMeetingFiltered );
    }

    /**
     * Test  the FilterMeeting by Date with a initlist with 7 items
     */
    @Test
    public void filterByDate() {
        List<Meeting> lMeetingsFiltered = maReuApiService.filterMeetingsByDate( 2022, 05, 13 );
        assertEquals( 1, lMeetingsFiltered.size() );
    }

    /**
     * Test  the availability of a room
     */
    @Test
    public void checkRoomAvailability() {
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
        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        mStartDate = new Date( mCalendarDeb.getTimeInMillis() );
        mEndDate = new Date( mCalendarFin.getTimeInMillis() );
        available = maReuApiService.checkRoomAvailability( idRoom, mStartDate, mEndDate );
        assertEquals( false, available );
    }


}