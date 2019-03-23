package com.example.blotcanvas.automated_farming;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class fragmentFarmConditions extends Fragment {
    View view;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private TextView textViewTemp;
    private TextView textViewHumid;
    private TextView textViewMoist;
    private TextView textViewpH;
    private String temp;
    private String hum;
    private String moi;
    private String ph;
    private Date currentTime;
    private TextView textViewUpdate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_farm_conditions,container,false);

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Values");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewTemp =(TextView)view.findViewById(R.id.textViewTemp);
        textViewHumid =(TextView) view.findViewById(R.id.textViewHumid);
        textViewMoist = (TextView) view.findViewById(R.id.textViewMoisture);
        textViewpH = (TextView) view.findViewById(R.id.textViewPh);
        textViewUpdate=(TextView)view.findViewById(R.id.textViewUpdate);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {

        temp=dataSnapshot.child("Temperature").getValue().toString();
        hum=dataSnapshot.child("Humidity").getValue().toString();
        moi=dataSnapshot.child("Moisture").getValue().toString();
        ph=dataSnapshot.child("pH").getValue().toString();
        textViewTemp.setText(temp+" C");
        textViewMoist.setText(moi);
        textViewHumid.setText(hum);
        textViewpH.setText(ph);
        Calendar calendar = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("'Last Updated on 'yyyy-MM-dd' at 'HH:mm");
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        currentTime = Calendar.getInstance().getTime();
        textViewUpdate.setText(sdf.format(currentTime).toString());
    }
}
