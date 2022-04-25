package fr.flozzy.mareu.SERVICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.MODEL.Participant;
import fr.flozzy.mareu.MODEL.Room;
import fr.flozzy.mareu.R;

public abstract class MaReuDummyApiGenerator {

    private static final List<Participant> DUMMY_PARTICIPANT = Arrays.asList(
            new Participant(1, "Joel", "http://clipart-library.com/data_images/163977.jpg", "Joel@lamzone.com.fr"),
            new Participant(2, "Julien", "http://clipart-library.com/newhp/kAibeA8c4.png", "Julien@lamzone.com"),
            new Participant(3, "Gaetan", "http://clipart-library.com/data_images/163960.png", "Gaetan@lamzone.com"),
            new Participant(4, "Jean-Pierre", "http://clipart-library.com/data_images/163956.jpg", "JeanPierre@lamzone.com"),
            new Participant(5, "Jordane", "http://clipart-library.com/data_images/163966.jpg", "Jordane@lamzone.com"),
            new Participant(6, "Pierre", "http://clipart-library.com/data_images/163932.jpg", "Pierre@lamzone.com"),
            new Participant(7, "Bruno", "http://clipart-library.com/data_images/163926.jpg", "Bruno@lamzone.com"),
            new Participant(8, "Sindy", "http://clipart-library.com/data_images/163935.jpg", "Sindy@lamzone.com"),
            new Participant(9, "Isabelle", "http://clipart-library.com/data_images/163948.png", "Isabelle@lamzone.com"),
            new Participant(10, "Nicole", "http://clipart-library.com/data_images/163932.jpg", "Nicole@lamzone..com"),
            new Participant(11, "Patricia", "http://clipart-library.com/data_images/79663.png", "Patricia@lamzone.com"),
            new Participant(12, "Barbara", "http://clipart-library.com/data_images/163914.jpg", "Barbara@lamzone.com"),
            new Participant(13, "Salomé", "http://clipart-library.com/data_images/19601.png", "Salome@lamzone.com"),
            new Participant(14, "Suzanne", "http://clipart-library.com/data_images/163892.jpg", "Suzanne@lamzone.com"),
            new Participant(15, "Mathilde", "http://clipart-library.com/data_images/163914.jpg", "Mathilde@lamzone.com"),
            new Participant(16, "Dominique", "http://clipart-library.com/images/ziXp7E7iB.png", "Dominique@lamzone.com"),
            new Participant(17, "Audrey", "http://clipart-library.com/new_gallery/1-15826_free-gold-christmas-ornaments-png-png-download.png", "Audrey@lamzone.com"),
            new Participant(18, "Joaquim", "https://thumbs.dreamstime.com/b/funny-christmas-reindeer-cartoon-holding-wooden-pa-22253100.jpg", "Joaquim@lamzone.com"),
            new Participant(19, "Gwenael", "https://thumbs.dreamstime.com/b/dachshund-christmas-santa-cute-cartoon-character-vector-illustration-happy-dog-wearing-claus-beanie-ready-celebrate-156337318.jpg", "Gwenael@lamzone.com")
    );

    public static List<Participant> generateParticipant() {
        return new ArrayList<>(DUMMY_PARTICIPANT);
    }
    public static List<Meeting> generateMeeting() {
        List<Meeting> lMeetingDeb = new ArrayList<Meeting>();
        lMeetingDeb = initList();
        return lMeetingDeb;
    }

    private static List<Meeting> initList() {
        List<Meeting> lMeetings = new ArrayList<Meeting>();

        Calendar mCalendarDeb = Calendar.getInstance();
        Calendar mCalendarFin = Calendar.getInstance();
        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        java.util.Date dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        java.util.Date dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        Meeting aMeeting = new Meeting( System.currentTimeMillis(),
                1,
                "Objet Reunion 1",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 3,4,6 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 20, 10, 00 );
        mCalendarDeb.set( 2022, 05, 20, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                8,
                "Objet Reunion 2",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 3,4,6 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarDeb.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                2,
                "Objet Reunion 3",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 9, 5 ) );
        lMeetings.add( aMeeting );
        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                3,
                "Objet Reunion 4",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 6,7,8 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                4,
                "Objet Reunion 5",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 3,4,5, 6 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                5,
                "Objet Reunion 6",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 1, 2 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                6,
                "Objet Reunion 7",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 5,6,7,8,10) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 12, 10, 00 );
        mCalendarFin.set( 2022, 05, 12, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new java.util.Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                7,
                "Objet Reunion 8",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 1,2,3,4,9, 5 ) );
        lMeetings.add( aMeeting );

        mCalendarDeb.set( 2022, 05, 13, 10, 00 );
        mCalendarFin.set( 2022, 05, 13, 11, 00 );
        dateDebMeeting = new java.util.Date( mCalendarDeb.getTimeInMillis() );
        dateFinMeeting = new Date( mCalendarFin.getTimeInMillis() );
        aMeeting = new Meeting( System.currentTimeMillis(),
                10,
                "Objet Reunion 9",
                dateDebMeeting,
                dateFinMeeting,
                Arrays.asList( 1,2,3,4,9, 5 ) );
        lMeetings.add( aMeeting );
        return lMeetings;
    }
    public static final List<Room> DUMMY_ROOMS = Arrays.asList(
            new Room( 1, "Salle 1", R.drawable.salleun),
            new Room( 2, "Salle 2",  R.drawable.salledeux ),
            new Room( 3, "Salle 3",  R.drawable.salletrois),
            new Room( 4, "Salle 4", R.drawable.sallequatre),
            new Room( 5, "Salle 5", R.drawable.sallecinq),
            new Room( 6, "Salle 6" , R.drawable.sallesix),
            new Room( 7, "Salle 7",  R.drawable.sallesept),
            new Room( 8, "Salle 8", R.drawable.sallehuit),
            new Room( 9, "Salle 9",R.drawable.salleneuf),
            new Room( 10, "Salle 10",R.drawable.salledix),
            new Room( 11, "Salle 11", R.drawable.salleonze)
    );

    public static List<Room> generateRooms() {
        return DUMMY_ROOMS;
    }

}
