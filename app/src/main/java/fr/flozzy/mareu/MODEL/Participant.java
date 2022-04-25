package fr.flozzy.mareu.MODEL;

public class Participant {
    private final int mId;
    private final String mParticipantName;
    private final String mParticipantUrl;
    private final String mParticipantMail;

    public Participant(int id, String participantName, String participantUrl, String participantMail) {
        mId = id;
        mParticipantName = participantName;
        mParticipantUrl = participantUrl;
        mParticipantMail = participantMail;
    }


    public int getId() {
        return mId;
    }

    public String getParticipantMail() {
        return mParticipantMail;
    }
}
