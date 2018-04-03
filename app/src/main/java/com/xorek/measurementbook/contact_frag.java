package com.xorek.measurementbook;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class contact_frag extends Fragment {





    public contact_frag() {
        // Required empty public constructor
    }



    View view;
    DbHandler dbHandler;
    Context context;
    long id;

    TextView nameClient, phoneClient, dateClient, deliverydateClient, sex;


    Client client = new Client();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_frag, container, false);

        id = getArguments().getLong("id");
        Log.d("ID", String.valueOf(id));
        context = getActivity().getApplicationContext();

         nameClient = (TextView)view.findViewById(R.id.nameClient_val);

        phoneClient = (TextView)view.findViewById(R.id.phoneClient_val);

         dateClient = (TextView)view.findViewById(R.id.dateClient_val);

        deliverydateClient = (TextView)view.findViewById(R.id.deliverydateClient_val);

        sex = (TextView)view.findViewById(R.id.sexClient_val);

        retrieveRow();

        return view;
    }


    public void retrieveRow() {
        dbHandler = new DbHandler(context);
        Cursor cr = dbHandler.getRow(dbHandler, id);

        if ((cr != null) && cr.moveToFirst()) {


            do {

                client.setName(cr.getString(0));
                client.setPhoneNumber(cr.getString(1));
                client.setDate(cr.getString(2));
                client.setDiliveryDate(cr.getString(3));
                client.setNeckCirc(cr.getString(4));
                client.setBustCirc(cr.getString(5));
                client.setWaistCirc(cr.getString(6));
                client.setHipCirc(cr.getString(7));
                client.setArmholeCirc(cr.getString(8));
                client.setBustHeight(cr.getString(9));
                client.setFrontWaistLenth(cr.getString(10));
                client.setHipLength(cr.getString(11));
                client.setElbowLenth(cr.getString(12));
                client.setSleeveLength(cr.getString(13));
                client.setBackWaistLenth(cr.getString(14));
                client.setShoulderLenth(cr.getString(15));
                client.setBackWidth(cr.getString(16));
                client.setKneeLength(cr.getString(17));
                client.setBreastDistance(cr.getString(18));
                client.setUnderBustCirc(cr.getString(19));
                client.setUnderBustHeight(cr.getString(20));
                client.setRiseLength(cr.getString(21));
                client.setTrouserLength(cr.getString(22));
                client.setFloorLength(cr.getString(23));
                client.setStraplessNecklineCirc⁠⁠⁠⁠(cr.getString(24));
                client.setNotes(cr.getString(25));
                client.setImage(cr.getString(26));
                client.setSex(cr.getString(27));
                client.setStyle(cr.getString(28));

//                    Toast.makeText(getApplicationContext(),cr.getString(0),Toast.LENGTH_SHORT).show();
            } while (cr.moveToNext());

        }

        nameClient.setText(client.getName());
        phoneClient.setText(client.getPhoneNumber());
        dateClient.setText(client.getDate());
        deliverydateClient.setText(client.getDiliveryDate());
        sex.setText(client.getSex());



    }
}
