package fr.flozzy.mareu.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import fr.flozzy.mareu.DI.DI;
import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.MODEL.Participant;
import fr.flozzy.mareu.MODEL.Room;
import fr.flozzy.mareu.R;
import fr.flozzy.mareu.SERVICE.MaReuApiService;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private static final String TEXT_SEPARATOR = " - ";
    private List<Meeting> lMeetings;
    private MaReuApiService mMaReuApiService;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.fragment_item_list, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        mMaReuApiService = DI.getMaReuApiService();

        List<Room> lRoomsMeeting = mMaReuApiService.getRooms();
        List<Participant> lParticipant = mMaReuApiService.getParticipant();

        int mId = (int) lMeetings.get( position ).getIdRoom();
        String mRoomName = lRoomsMeeting.get( mId - 1 ).getRoomName();
        int mRoomImage = lRoomsMeeting.get( mId - 1 ).getRoomImage();
        holder.mMeetingRoomImage.setImageResource(mRoomImage);

        // Thème de la réunion
        String subjectMeeting = lMeetings.get( position ).getMeetingSubject();

        // Date début réunion
        Date mStartDate = lMeetings.get( position ).getMeetingStartDate();
        DateFormat meetingStartDate = DateFormat.getDateInstance( DateFormat.MEDIUM );

        // Heure début réunion
        DateFormat meetingStartTime = DateFormat.getTimeInstance( DateFormat.SHORT );

        // Participants réunion
        List<Integer> listParticipant = lMeetings.get( position ). getMeetingParticipantListId();
        String mParticipantListMail = "";
        int nbParticipant = listParticipant.size() ;
        int idParticipant = 0;

        for (int ind = 0; ind < nbParticipant; ind ++ ) {
            idParticipant = listParticipant.get( ind );
            mParticipantListMail += lParticipant.get( idParticipant - 1 ).getParticipantMail() + " - ";
        }
        
        String mFirstLineString = subjectMeeting
                + TEXT_SEPARATOR + meetingStartDate.format( mStartDate )
                + TEXT_SEPARATOR + meetingStartTime.format( mStartDate )
                + TEXT_SEPARATOR + mRoomName;
        holder.mFirstLine.setText( mFirstLineString );

        holder.mSecondLine.setText( mParticipantListMail );

        // boutton supprimer réunion
        deleteButton( holder, position );
    }


    private void deleteButton(@NonNull MyViewHolder holder, final int position) {
        holder.mButtonDeleteMeeting.setOnClickListener( view -> {
            Toast.makeText( view.getContext(), "Suppression de la réunion " + lMeetings.get( position ).getMeetingSubject(), Toast.LENGTH_SHORT ).show();
            Meeting dMeeting = lMeetings.get( position );
            mMaReuApiService.deleteMeeting( dMeeting );
            setData( lMeetings);
        } );
    }

    @Override
    public int getItemCount() {
        if (lMeetings != null)
            return lMeetings.size();
        else
            return 0;
    }


    public void setData(List<Meeting> lMeetings) {
        this.lMeetings = lMeetings;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mMeetingRoomImage;
        TextView mFirstLine;
        TextView mSecondLine;
        ImageButton mButtonDeleteMeeting;

        MyViewHolder(@NonNull View itemView) {
            super( itemView );
            mMeetingRoomImage = itemView.findViewById( R.id.item_image_meeting );
            mFirstLine = itemView.findViewById( R.id.item_meeting_first_line );
            mSecondLine = itemView.findViewById( R.id.item_meeting_second_line );
            mButtonDeleteMeeting = itemView.findViewById( R.id.item_image_meeting_delete );
        }
    }
}