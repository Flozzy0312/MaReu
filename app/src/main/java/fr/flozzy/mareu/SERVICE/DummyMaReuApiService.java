package fr.flozzy.mareu.SERVICE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.MODEL.Participant;
import fr.flozzy.mareu.MODEL.Room;
import fr.flozzy.mareu.UI.AddMeetingActivity;

public class DummyMaReuApiService implements MaReuApiService {

    public List<Meeting> lMeetings = MaReuDummyApiGenerator.generateMeeting();
    public List<Participant> lParticipant = MaReuDummyApiGenerator.generateParticipant();
    public List<Room> lRooms = MaReuDummyApiGenerator.generateRooms();


    @Override
    public List<Meeting> getMeetings() {
        return lMeetings;
    }

    public void deleteMeeting(Meeting dMeeting) {
        lMeetings.remove(dMeeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        lMeetings.add(meeting);
    }

    @Override
    public List<Participant> getParticipant() {
        return lParticipant;
    }

    @Override
    public List<Room> getRooms() {
        return lRooms;
    }

    public void createMeeting(Meeting aMeeting) {
        lMeetings.add(aMeeting);
    }

    public List<Meeting> lMeetingsFilteredId(List<Integer> lRoomSelectedId){
        long nbMeetings=lMeetings.size();
        List<Meeting> lMeetingsFiltered=new ArrayList<>();
        long nbRoomSelected=lRoomSelectedId.size();
        int i=0;
        while(i<nbRoomSelected){
            long idRoomSelected=lRoomSelectedId.get(i) + 1 ;
            for(int j=0;j<nbMeetings; j++){
                long idRoomMeeting = lMeetings.get(j).getIdRoom();
                if( idRoomMeeting == idRoomSelected) {
                    lMeetingsFiltered.add( lMeetings.get( j ) );
                }
            }
            i++;
        }
        return lMeetingsFiltered;
    }
    // Filtre les réunions par date
    public List<Meeting> filterMeetingsByDate(int year, int month, int day) {

        List<Meeting> mMeetingsFiltered = new ArrayList<>();
        int size = lMeetings.size();
        for (int e = 0; e < size; e++) {
            Calendar mMeetingsCalendar = Calendar.getInstance();
            mMeetingsCalendar.setTime(lMeetings.get(e).getMeetingStartDate());
            if (year == mMeetingsCalendar.get(Calendar.YEAR)
                    && (month == mMeetingsCalendar.get(Calendar.MONTH))
                    && (day == mMeetingsCalendar.get(Calendar.DAY_OF_MONTH))){
                mMeetingsFiltered.add(lMeetings.get(e));
            }
        }
        return mMeetingsFiltered;
    }

    // Verifier que la salle est disponible
    public boolean checkRoomAvailability(int roomId, Date startDate, Date endDate) {
        boolean roomAvailable = true;
        for (Meeting meetingIterator : lMeetings) {
            if (roomId == (meetingIterator.getIdRoom())
                    && (startDate.after(meetingIterator.getMeetingStartDate())
                    && startDate.before((meetingIterator.getMeetingEndDate()))))
                roomAvailable = false;
            break;
        }
        return roomAvailable;
    }

    // Determination liste des salles de réunion
    public String[]getRoomsAsStringList(){
        int numberRooms;
        numberRooms=lRooms.size();
        String[]lRoomName=new String[numberRooms];
        for(int i=0;i<numberRooms; i++){
            lRoomName[i]= lRooms.get(i).getRoomName();
        }
        return lRoomName;
    }

    @Override
    public List<String> getParticipantEmails(List<Participant> mParticipantList) {
        return null;
    }

    @Override
    public void getParticipantFromEmailsSelected(AddMeetingActivity addMeetingActivity) {

    }

}
