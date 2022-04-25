package fr.flozzy.mareu.MODEL;

public class Room {

    private final int mId;
    private final String mRoomName;
    private final int mImage;

    public Room(int id, String mName, int image) {
        mId = id;
        mRoomName = mName;
        mImage = image;
    }

    public int getId() {
        return mId;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public int getRoomImage() {
        return mImage;
    }

}