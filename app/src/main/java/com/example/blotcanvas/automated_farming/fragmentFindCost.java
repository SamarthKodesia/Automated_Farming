package com.example.blotcanvas.automated_farming;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class fragmentFindCost extends Fragment implements View.OnClickListener {
    View view;
    private TextView textViewHeader;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private TextView textViewProd;
    private TextView textViewCult;
    private EditText editTextLand;
    private Double land_size=1.0;
    private Button buttonGetCost;
    private String cult;
    private String prod;
    private Double cult_val;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("costs");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        view = inflater.inflate(R.layout.cost_finder,container,false);

        textViewProd = (TextView)view.findViewById(R.id.textViewValProd);
        textViewCult = (TextView)view.findViewById(R.id.textViewValCult);
        buttonGetCost = (Button)view.findViewById(R.id.buttonGetCost);
        buttonGetCost.setOnClickListener(this);
        editTextLand = (EditText)view.findViewById(R.id.editTextlandVal);
        String str = editTextLand.getText().toString().trim();
        textViewHeader = (TextView)view.findViewById(R.id.textViewNotifier);
        textViewHeader.setText("For the selected state and crop :-");


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

        cult=dataSnapshot.child("cult_cost").getValue().toString();
        prod=dataSnapshot.child("prod_cost").getValue().toString();
        textViewProd.setText("₹ "+prod);
        textViewCult.setText("₹ "+cult);
        cult_val=Double.parseDouble(cult);
    }


    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        if(v== buttonGetCost)
        {
            if(editTextLand.getText().toString().trim().equals(null) || editTextLand.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(),"Please Enter the area of land !",Toast.LENGTH_LONG).show();
                editTextLand.setText("1");
            }
            String str = editTextLand.getText().toString().trim();
            land_size= Double.parseDouble(str);
            Double ret_val=cult_val*land_size;
            textViewCult.setText("₹ "+ret_val.toString());
        }
    }
}
