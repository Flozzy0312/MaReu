package fr.flozzy.mareu.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import fr.flozzy.mareu.DI.DI;
import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.MODEL.Participant;
import fr.flozzy.mareu.MODEL.Room;
import fr.flozzy.mareu.R;
import fr.flozzy.mareu.SERVICE.MaReuApiService;
import fr.flozzy.mareu.SERVICE.MaReuDummyApiGenerator;

public class AddMeetingActivity extends AppCompatActivity {
    private static final String EMAILS_LIST_SEPARATOR = ", ";
    private static final int DURATION_MAX_HOURS = 5;
    private static final int DURATION_STEP_MINUTES = 15;

    private MaReuApiService maReuApiService;

    private Spinner sRoom;
    private MultiAutoCompleteTextView participantEmail;

    private int idRoom = 0;

    private final Calendar datePickerCalendar = Calendar.getInstance();
    private final Calendar timePickerCalendar = Calendar.getInstance();

    private Date mStartDate;
    private NumberPicker durationMinutes, durationHours;
    private EditText mSubject;
    private TextView startDatePickerText, startTimePickerText;
    private int nbRoom = MaReuDummyApiGenerator.generateRooms().size();
    private int nbParticipant = MaReuDummyApiGenerator.generateRooms().size();
    private List<Room> lRooms = MaReuDummyApiGenerator.generateRooms();
    private List<Participant> lParticipant = MaReuDummyApiGenerator.generateParticipant();

    private boolean topVide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_meeting );
        init();
    }

    // Instanciation des vues et des variables
    private void init() {
        Toolbar toolbar = findViewById( R.id.toolbar_add_meeting );
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        maReuApiService = DI.getMaReuApiService();

        mSubject = findViewById( R.id.edit_text_add_meeting_subject );
        startDatePickerText = findViewById( R.id.text_add_meeting_datepicker );
        startTimePickerText = findViewById( R.id.text_add_meeting_timepicker );

        durationHours = findViewById( R.id.numberpicker_add_meeting_duration_hours_ );
        durationMinutes = findViewById( R.id.numberpicker_add_meeting_duration_minutes_ );
        sRoom = findViewById( R.id.spinner_add_meeting_room );
        participantEmail = findViewById( R.id.autocomplete_text_add_meeting_participant );

        setStartDatePickerDialog();
        setStartTimePickerDialog();
        durationHours.setMaxValue( DURATION_MAX_HOURS );
        durationHours.setMinValue( 0 );
        setDurationsMinutesValues();
        setDurationsMinutesValues();
        setStartDatePickerDialog();
        setStartTimePickerDialog();
        setRoomSpinnerDialog();
        setParticipantArrayAdapter();
    }

    private void setRoomSpinnerDialog() {
        ArrayList<String> mRoomsList = new ArrayList<>();
        mRoomsList.add( 0, getResources().getString( R.string.add_meeting_room ) );

        for (int mId = 1; mId <= nbRoom; mId++) {
            String mRoomName = lRooms.get( mId - 1 ).getRoomName();
            mRoomsList.add( mRoomName );
            String[] mRoomsArray = mRoomsList.toArray( new String[0] );
            ArrayAdapter<String> adapterRooms
                    = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, mRoomsArray );
            sRoom.setAdapter( adapterRooms );
        }
    }

    // Sélecteur de date
    private void setStartDatePickerDialog() {
        final DatePickerDialog.OnDateSetListener startDate = (view, year, monthOfYear, dayOfMonth) -> {
            datePickerCalendar.set( Calendar.YEAR, year );
            datePickerCalendar.set( Calendar.MONTH, monthOfYear );
            datePickerCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );
            updateStartDateLabel();
        };
        startDatePickerText.setOnClickListener( v -> new DatePickerDialog( AddMeetingActivity.this, startDate, datePickerCalendar
                .get( Calendar.YEAR ), datePickerCalendar.get( Calendar.MONTH ),
                datePickerCalendar.get( Calendar.DAY_OF_MONTH ) ).show() );
    }

    private void updateStartDateLabel() {
        DateFormat dateFormat = DateFormat.getDateInstance( DateFormat.LONG );
        startDatePickerText.setText( dateFormat.format( datePickerCalendar.getTime() ) );
    }

    private void setStartTimePickerDialog() {
        final TimePickerDialog.OnTimeSetListener startTime = (view, hourOfDay, minute) -> {
            timePickerCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timePickerCalendar.set(Calendar.MINUTE, minute);
            updateStartTimeLabel();
        };
        startTimePickerText.setOnClickListener(v -> new TimePickerDialog(AddMeetingActivity.this, startTime, timePickerCalendar
                .get(Calendar.HOUR), timePickerCalendar.get(Calendar.MINUTE),
                true).show());
    }

    private void updateStartTimeLabel() {
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        startTimePickerText.setText(timeFormat.format(timePickerCalendar.getTime()));
    }

    // durée en minutes
    private void setDurationsMinutesValues() {
        int sizeSteps = 60 / DURATION_STEP_MINUTES;
        String[] mins = new String[sizeSteps];
        for (int i = 0; i < sizeSteps; i++) {
            mins[i] = String.valueOf( DURATION_STEP_MINUTES * i );
        }
        int ml = sizeSteps - 1;
        durationMinutes.setDisplayedValues( null );
        durationMinutes.setMinValue( 0 );
        durationMinutes.setMaxValue( ml );
        durationMinutes.setDisplayedValues( mins );
    }

    // LISTE DES PARTICIPANTS
    private void setParticipantArrayAdapter() {
        ArrayList<String> mParticipantMail = new ArrayList<>();
        for (int mId = 0; mId < nbParticipant; mId++) {
            String mParticipantEmail = lParticipant.get( mId ).getParticipantMail();
            mParticipantMail.add( mParticipantEmail );
        }
        String[] participantEmailList = mParticipantMail.toArray( new String[0] );
        ArrayAdapter<String> adapterParticipant
                = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, participantEmailList );
        participantEmail.setAdapter( adapterParticipant );
        participantEmail.setThreshold( 1 );                                                  // Sets the minimum number of characters, to show suggestions
        participantEmail.setTokenizer( new MultiAutoCompleteTextView.CommaTokenizer() );     // then separates them with a comma
    }

    // Creation de réunion
    private void createMeeting() {
        String mSubjectString = mSubject.getText().toString();
        mStartDate = getStartMeetingDateTimeFromSelection();

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime( mStartDate );
        mCalendar.add( Calendar.HOUR_OF_DAY, durationHours.getValue() );
        mCalendar.add( Calendar.MINUTE, durationMinutes.getValue() * DURATION_STEP_MINUTES );
        Date mEndDate = mCalendar.getTime();

        getRoomFromRoomNameSelected();

        List<Integer> lParticipantId = getIdParticipantFromParticipantMailSelected();
        int lParticipantIdNb = lParticipantId.size();

        if (mSubjectString.isEmpty()) {
            toastCancelCreation( R.string.toast_subject_empty );
        } else if (durationHours.getValue() == 0 && durationMinutes.getValue() == 0) {
            toastCancelCreation( R.string.toast_duration_empty );
        } else if (topVide) {
            toastCancelCreation( R.string.toast_room_empty );
        } else if (!maReuApiService.checkRoomAvailability(idRoom, mStartDate, mEndDate)) {
            toastCancelCreation( R.string.toast_room_not_available );
        }else if (lParticipantIdNb == 0) {
            toastCancelCreation( R.string.toast_participant_empty );
        } else {
            Meeting mMeeting = new Meeting(
                    System.currentTimeMillis(),
                    idRoom,
                    mSubjectString,
                    mStartDate,
                    mEndDate,
                    lParticipantId );
            finMeeting( mMeeting );

            // appeler service add meeting
        }
    }

    // Obtient l'objet salle à partir du nom de la salle sélectionnée
    private long getRoomFromRoomNameSelected() {
        idRoom = 0;
        topVide = true;
        for (int mId = 0; mId < nbRoom; mId++) {
            Room meetingRoom = lRooms.get( mId );
            if (meetingRoom.getRoomName().equals( sRoom.getSelectedItem().toString() )) {
                idRoom = meetingRoom.getId();
                mId = nbRoom + 1;
                topVide = false;
            }
        }
        return (idRoom);
    }

    // Obtenir la liste des participant à partir de l'adresse mail
    private List<Integer> getIdParticipantFromParticipantMailSelected() {
        List<Integer> lParticipantId = new ArrayList<>();
        String[] ParticipantSelected = participantEmail.getText().toString().split( EMAILS_LIST_SEPARATOR );
        int nbParticipant = lParticipant.size();
        int nbParticipantSelected = ParticipantSelected.length;
        String email = "";
        String email2 = "";

        for (int ind = 0; ind < nbParticipantSelected; ind++) {
            email = ParticipantSelected[ind];
            for (int ind2 = 0; ind2 < nbParticipant; ind2++) {
                email2 = lParticipant.get( ind2 ).getParticipantMail();
                if (email.equals( email2 )) {
                    lParticipantId.add( lParticipant.get( ind2 ).getId() );
                }
            }
        }
        return lParticipantId;
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param mMeeting the meeting to be added to the list
     */
    private void finMeeting(Meeting mMeeting) {
        Intent resultIntent = new Intent();
        maReuApiService.addMeeting(mMeeting);
        finish();
    }

    private void toastCancelCreation(int intString) {
        Toast toastCreateMeeting = Toast.makeText( getApplicationContext(), intString, Toast.LENGTH_LONG );
        toastCreateMeeting.setGravity( Gravity.CENTER, 0, 0 );
        View toastViewCreateMeeting = toastCreateMeeting.getView();
        TextView toastTextCreateMeeting = toastViewCreateMeeting.findViewById( android.R.id.message );
        toastTextCreateMeeting.setTextColor( ContextCompat.getColor( getApplicationContext(), R.color.addMeeting ) );

        toastCreateMeeting.show();
    }

    private Date getStartMeetingDateTimeFromSelection() {
        // Créer le calendrier
        Calendar mCalendar = Calendar.getInstance();

        mCalendar.setTime(datePickerCalendar.getTime());
        mCalendar.set(Calendar.HOUR, timePickerCalendar.get(Calendar.HOUR));
        mCalendar.set(Calendar.MINUTE, timePickerCalendar.get(Calendar.MINUTE));
        mCalendar.set(Calendar.AM_PM, timePickerCalendar.get(Calendar.AM_PM));

        return mCalendar.getTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_meeting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_button_create_meeting) {
            createMeeting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}