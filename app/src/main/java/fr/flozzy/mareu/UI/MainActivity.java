package fr.flozzy.mareu.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.flozzy.mareu.DI.DI;
import fr.flozzy.mareu.MODEL.Meeting;
import fr.flozzy.mareu.R;
import fr.flozzy.mareu.SERVICE.MaReuApiService;

public class MainActivity<lMeetings> extends AppCompatActivity {
    public boolean[] FILTER_ROOM;
    public MaReuApiService maReuApiService;

    private MyAdapter adapter;

    private Menu menu;
    private final int ADD_MEETING_REQUEST_CODE = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
        createNewMeetingAction();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        final RecyclerView rv = findViewById(R.id.list_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        rv.setAdapter(adapter);
        maReuApiService = DI.getMaReuApiService();
        adapter.setData(maReuApiService.getMeetings());
    }

    //  Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_filtre, menu);
        return true;
    }

    //  Filtres
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        List<Meeting> listMeetingsFiltered = new ArrayList<>();
        switch(id){
            case R.id.menu_filter_date: {
                setDateFilter();
                return true;
            }

            case R.id.menu_filter_room: {
                setRoomsFilter();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Meeting> setDateFilter(){
        Calendar mCalendarPicker=Calendar.getInstance();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        final DatePicker datePicker=new DatePicker(this);
        datePicker.setCalendarViewShown(false);

        builder.setView(datePicker);

        builder.setPositiveButton(R.string.filter_ok_text,(dialog,id)->{
            int year=datePicker.getYear();
            int month=datePicker.getMonth();
            int day=datePicker.getDayOfMonth();
            mCalendarPicker.set(year,month,day);
            List<Meeting> lMeetingsFiltered= maReuApiService.filterMeetingsByDate(year, month, day);
            adapter.setData(lMeetingsFiltered);

        });
        builder.setNeutralButton(R.string.filter_reset_text,(dialog,id)->{
            adapter.setData(maReuApiService.getMeetings());
        });
        builder.show();
        return null;
    }

    public void setRoomsFilter() {
        // Créer une boîte de dialogue d'alerte
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // String pour les éléments à choix multiples de la boîte de dialogue d'alerte
        final int numberRooms = maReuApiService.getRooms().size();
        String[] mRooms = maReuApiService.getRoomsAsStringList();
        // booléen pour les éléments sélectionnés initiaux
        final boolean[] checkedRooms = new boolean[numberRooms];
        // Garder en mémoire la sélection du filtre
        if (FILTER_ROOM != null) {
            System.arraycopy(FILTER_ROOM, 0, checkedRooms, 0, numberRooms);
        }

        // Définir plusieurs éléments à choix pour la boîte de dialogue d'alerte
        builder.setMultiChoiceItems(mRooms, checkedRooms, (dialog, which, isChecked) -> {
            // Mettre à jour le statut coché de l'élément ciblé actuel
            checkedRooms[which] = isChecked;
        });

        // Spécifiez que la boîte de dialogue n'est pas effacable
        builder.setCancelable(false);

        // Définir un titre pour la boîte de dialogue d'alerte
        builder.setTitle(R.string.filter_rooms_text);

        // Définir l'écouteur de clic posotif sur bouton
        builder.setPositiveButton(R.string.filter_ok_text, (dialog, which) -> {
        });

        // Définir l'écouteur de clic négatif sur bouton
        builder.setNeutralButton(R.string.filter_reset_text, (dialog, which) -> {
            adapter.setData(maReuApiService.getMeetings());
            FILTER_ROOM = null;
        });

        final AlertDialog dialog = builder.create();
        // Afficher la boîte de dialogue d'alerte sur l'interface
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                //Vérifie les salle cochées
                boolean atLeastOneChecked = false;
                for (boolean checked : checkedRooms) {
                    if (checked) {
                        atLeastOneChecked = true;
                        break;
                    }
                }

                // si aucune salle n'est cochée, Toast pour alerter l'utilisateur
                if (!atLeastOneChecked) {
                    Toast toastRoomNotSelected = Toast.makeText(getApplicationContext(), R.string.toast_room_not_selected, Toast.LENGTH_SHORT);
                    toastRoomNotSelected.setGravity(Gravity.CENTER, 0, 0);
                    View toastViewCreateMeeting = toastRoomNotSelected.getView();
                    TextView toastTextCreateMeeting = toastViewCreateMeeting.findViewById(android.R.id.message);
                    toastTextCreateMeeting.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.toast_add_meeting_color));
                    toastRoomNotSelected.show();
                } else {
                    List<Integer> lRoomSelectedId=filterRoom(checkedRooms);
                    List<Meeting> lMeetingsFiltered=maReuApiService.meetingsByRoomIds(lRoomSelectedId);
                    dialog.dismiss();
                    adapter.setData(lMeetingsFiltered);
                    FILTER_ROOM = checkedRooms;
                }
            });
        });
        dialog.show();

    }


    // Créer une liste avec l'identifiant sélectionné de la salle
    public List<Integer> filterRoom(boolean[]checkedRooms){
        List<Integer> lRoomSelectedId=new ArrayList<>();

        String[]mRooms=maReuApiService.getRoomsAsStringList();
        for(int i=0;i<checkedRooms.length;i++){
            boolean checked=checkedRooms[i];
            if(checked){
                lRoomSelectedId.add(i);
            }
        }
        return lRoomSelectedId;
    }


    private void createNewMeetingAction(){
        FloatingActionButton mButtonNewMeeting=findViewById(R.id.button_add_meeting);
        mButtonNewMeeting.setOnClickListener(v->{
            Intent addMeetingIntent=new Intent(getApplicationContext(),AddMeetingActivity.class );
            startActivityForResult(addMeetingIntent,ADD_MEETING_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        if(requestCode==ADD_MEETING_REQUEST_CODE&&resultCode==RESULT_OK){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}