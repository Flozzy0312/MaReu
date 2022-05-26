package fr.flozzy.mareu.SERVICE;

import java.util.Date;
import java.util.List;

import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.MODEL.Participant;
import fr.flozzy.mareu.MODEL.Room;
import fr.flozzy.mareu.UI.AddMeetingActivity;

public interface MaReuApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    List<Participant> getParticipant();

    List<Room> getRooms();

    String[] getRoomsAsStringList();

    List<String> getParticipantEmails(List<Participant> mParticipantList);

    void getParticipantFromEmailsSelected(AddMeetingActivity addMeetingActivity);

    boolean checkRoomAvailability(int roomId, Date startDate, Date endDate) ;

    List<Meeting> meetingsByRoomIds(List<Integer> lRoomSelectedId);

    List<Meeting> filterMeetingsByDate(int year, int month, int day);

}
