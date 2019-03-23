package com.example.blotcanvas.automated_farming;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class fragmentCrop extends Fragment implements View.OnClickListener {
    private Spinner spinnerCrop;
    private Spinner spinnerSelect;
    private Spinner getSpinnerSelectState;
    private Button buttonGetCst;
    public String crop;
    public String selState;
    private Spinner spinner2;
    private Spinner spinner3;

    private Double temrange;
    private Double hum;
    private Double mrange;
    private Double ph;
    private ArrayList<String> myList;


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_plan_crop,container,false);

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Values");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonGetCst = (Button)view.findViewById(R.id.buttonGetCost);
        buttonGetCst.setOnClickListener(this);
        myList = new ArrayList<String>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myList.add("Tomato");



        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinnerCrop);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, myList);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter1);

        String [] valuesAll =
                //{"Wheat","rice","Gram","Corn","Maize","Barley","Potato","Tomato",};
                {"Alpine","Apple","Arhar","Asparagus","Broccoli","Cabbage","Carrot","Cauliflower","Celery","Corn","Cotton","Cucumber",
                        "Eggplant","Fennel","Garlic","Gourd","Gram","Groundnut","Leek","Lettuce","Maize","Melon","Moong","Mustard",
                        "Okra","Onion","Oregano","Paddy","Pea","Pepper","Potato","Pumpkin","Raddish","Rapseed","Raspberry","Rice",
                        "Spinach","Strawberry","Sugarcanes","Tomato","Turnip","Watermelon","Wheat"};
        spinner2 = (Spinner) view.findViewById(R.id.spinnerSelect);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesAll);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);

        String [] states =
                {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chattisgarh","Goa","Gujarat","Hariyana",
                        "Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerela","Madhya Pradesh","Maharashtra","Manipur",
                "Meghalaya","Mozoram","Nagaland","Orrisa","Punjab","Rajasthan","Sikkim","Tamil Nadu",
                "Tripura","Telangana","Uttar Pradesh","Uttrakhand","West Bengal"};
        spinner3 = (Spinner) view.findViewById(R.id.spinnerSelectState);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, states);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner3.setAdapter(adapter3);

        return view;
    }

    private void setData(DataSnapshot dataSnapshot) {

        String temp1=dataSnapshot.child("Temperature").getValue().toString();
        String moi1=dataSnapshot.child("Moisture").getValue().toString();
        String ph1=dataSnapshot.child("pH").getValue().toString();
        temrange=Double.parseDouble(temp1);
        mrange=Double.parseDouble(moi1);
        ph=Double.parseDouble(ph1);

        if((ph>=4.5)& (ph<=6.0)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=17.0)& (temrange<=32.0))
        {
            myList.add("Potato");
        }
        if((ph>=5.5)& (ph>=6.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=9.0)& (temrange<=18.0))
        {
            myList.add("Raspberry");
        }
        if((ph>=5.0)& (ph<=6.5)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=10.0)& (temrange<=23.0))
        {
            myList.add("Apple");
        }
        if((ph>=5.5)& (ph<=7.0)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=18.0)& (temrange<=29.0))
        {
            myList.add("Carrot");
        }
        if((ph>=5.5)& (ph<=7.5)&(mrange<=0.4)& (mrange>=0.6)&(temrange>=25.0)& (temrange<=37.0))
        {
            myList.add("Corn");
        }
        if((ph>=5.5)& (ph<=7.0)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=24.0)& (temrange<=34.0))
        {
            myList.add("Cucumber");}

        if((ph>=5.5)& (ph<=6.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=25.0)& (temrange<=33.0))
        {
            myList.add("Eggplant");
        }
        if((ph>=5.5)& (ph<=7.5)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=30.0)& (temrange<=39.0))
        {
            myList.add("Garlic");
        }
        if((ph>=5.5)& (ph<=6.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=24.0)& (temrange<=38.0))
        {
            myList.add("Melon");
        }
        if((ph>=5.5)& (ph<=7.0)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=15.0)& (temrange<=40.0))
        {
            myList.add("Pepper");
        }
        if((ph>=6.0)& (ph<=6.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=28.0)& (temrange<=36.0))
        {
	         myList.add("Pumpkin");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=32.0)& (temrange<=40.0))
        {
            myList.add("Carrot");
        }
        if((ph>=5.5)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=23.0)& (temrange<=35.0))
        {
            myList.add("Turnip");
        }
        if((ph>=6.0)& (ph<=8.0)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=28.0)& (temrange<=36.0))
        {
            myList.add("Asparagus");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=17.0)& (temrange<=23.0))
        {
            myList.add("Broccoli");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=16.0)& (temrange<=20.0))
        {
            myList.add("Cabbage");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=9.0)& (temrange<=18.0))
        {
            myList.add("Cauliflower");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=22.0)& (temrange<=34.0))
        {
            myList.add("Celery");
        }
        if((ph>=6.0)& (ph<=6.7)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=28.0)& (temrange<=40.0))
        {
            myList.add("Fennel");
        }
        if((ph>=6.5)& (ph<=7.5)&(mrange>=0.2)& (mrange<=0.4)&(temrange>=28.0)& (temrange<=40.0))
        {
            myList.add("Gourd");
        }
        if((ph>=6.0)& (ph<=8.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=30.0)& (temrange<=39.0))
        {
            myList.add("Leek");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=29.0)& (temrange<=35.0))
        {
            myList.add("Lettuce");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=9.0)& (temrange<=18.0))
        {
            myList.add("Mustard");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=18.0)& (temrange<=24.0))
        {
            myList.add("Okra");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=30.0)& (temrange<=42.0))
        {
            myList.add("Onion");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=20.0)& (temrange<=33.0))
        {
            myList.add("Oregano");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=6.0)& (temrange<=18.0))
        {
            myList.add("Pea");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=20.0)& (temrange<=26.0))
        {
            myList.add("Spinach");
        }
        if((ph>=6.0)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=20.0)& (temrange<=35.0))
        {
            myList.add("Watermelon");
        }
        if((ph>=5.0)& (ph<=7.5)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=13.0)& (temrange<=23.0))
        {
            myList.add("Alpine Strawberry");
        }
        if((ph>=5.5)& (ph<=7.5)&(mrange<=0.2)& (mrange>=0.4)&(temrange>=22.0)& (temrange<=33.0))
        {
            myList.add("Tomato");
        }
        if((ph>=5.5)& (ph<=6.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=23.0)& (temrange<=25.0))
        {
            myList.add("Rice");
        }
        if((ph>=6.0)& (ph<=7.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=16.0)& (temrange<=27.0))
        {
            myList.add("Wheat");
        }
        if((ph>=5.8)& (ph<=8.0)&(mrange>=0.8)& (mrange<=1.0)&(temrange>=18.0)& (temrange<=32.0))
        {
            myList.add("Cotton");
        }
        if((ph>=6.5)& (ph<=8.0)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=20.0)& (temrange<=29.0))
        {
            myList.add("Arhar");
        }
        if((ph>=6.5)& (ph<=7.8)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=6.0)& (temrange<=25.0))
        {
            myList.add("Gram");
        }
        if((ph>=5.0)& (ph<=6.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=30.0)& (temrange<=39.0))
        {
            myList.add("Groundnut");
        }
        if((ph>=5.5)& (ph<=7.0)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=29.0)& (temrange<=34.0))
        {
            myList.add("Maize");
        }
        if((ph>=6.2)& (ph<=7.2)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=23.0)& (temrange<=30.0))
        {
            myList.add("Moong");
        }
        if((ph>=6.2)& (ph<=7.2)&(mrange>=0.4)& (mrange<=0.6)&(temrange>=20.0)& (temrange<=36.0))
        {
            myList.add("Paddy");
        }
        if((ph>=5.5)& (ph<=6.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=20.0)& (temrange<=30.0))
        {
            myList.add("Rapseed");
        }
        if((ph>=6.0)& (ph<=8.5)&(mrange>=0.6)& (mrange<=0.8)&(temrange>=32.0)& (temrange<=35.0))
        {
            myList.add("Sugarcane");
        }





    }

    private void saveInfo(){
        databaseReference = FirebaseDatabase.getInstance().getReference("User 1");
        saveData save_data= new saveData(crop, selState);
        databaseReference.setValue(save_data);
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        if(v==buttonGetCst){

            crop = spinner2.getSelectedItem().toString();
            selState = spinner3.getSelectedItem().toString();
            saveInfo();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new
                    fragmentFindCost()).commit();
            Toast.makeText(this.getActivity(),"Getting Production Cost...This may take a while...",Toast.LENGTH_LONG).show();
        }
    }
}
