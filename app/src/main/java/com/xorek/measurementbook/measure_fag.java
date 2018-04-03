package com.xorek.measurementbook;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;


public class measure_fag extends Fragment {
    DbHandler dbHandler;
    Context context;
    long id;



    @BindView(R.id.fab)
    FloatingActionButton fab;





    Client client = new Client();

    CardView cardv;

    TextView neck, style, bustC, bustH, waistC, hipC, straplessC, hipL, notes, floorL, trouserL, underBustH, riseL, armC, backW, breastD, underBustC, kneeL,  frontL, elbowL, sleeveL, backwaisL, shoulderL;

    public measure_fag() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_measure_fag, container, false);

        id = getArguments().getLong("id");

        context = getActivity().getApplicationContext();



        cardv =view.findViewById(R.id.card1);

         neck = (TextView)view.findViewById(R.id.neckcirc_val);

         bustC = (TextView)view.findViewById(R.id.bustcirc_val);

         bustH = (TextView)view.findViewById(R.id.bustHeight_val);

         waistC = (TextView)view.findViewById(R.id.waistcirc_val);

         hipC = (TextView)view.findViewById(R.id.hipcirc_val);

         hipL = (TextView)view.findViewById(R.id.hiplength_val);

       armC = (TextView)view.findViewById(R.id.armholecirc_val);

        frontL = (TextView)view.findViewById(R.id.frontwaistlength_val);

        elbowL = (TextView)view.findViewById(R.id.elbowlength_val);

        sleeveL = (TextView)view.findViewById(R.id.sleevelength_val);

         backwaisL = (TextView)view.findViewById(R.id.backwaistlength_val);

        shoulderL = (TextView)view.findViewById(R.id.shoulderLenth_val);

        backW = (TextView)view.findViewById(R.id.backwidth_val);

        kneeL = (TextView)view.findViewById(R.id.kneelength_val);

        breastD = (TextView)view.findViewById(R.id.breastdistance_val);

        underBustC = (TextView)view.findViewById(R.id.underbustcirc_val);

        underBustH = (TextView)view.findViewById(R.id.underbustheight_val);

        riseL = (TextView)view.findViewById(R.id.riselength_val);

        trouserL = (TextView)view.findViewById(R.id.trouserlength_val);

       floorL = (TextView)view.findViewById(R.id.floorlength_val);

         straplessC = (TextView)view.findViewById(R.id.straplessnecklinecirc⁠⁠⁠⁠_val);

        notes = (TextView)view.findViewById(R.id.note_val);
        style = (TextView)view.findViewById(R.id.style_val);

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


        neck.setText(client.getNeckCirc());
        bustC.setText(client.getBustCirc());
        waistC.setText(client.getWaistCirc());
        hipC.setText(client.getHipCirc());
        armC.setText(client.getArmholeCirc());
        bustH.setText(client.getBustHeight());
        frontL.setText(client.getFrontWaistLenth());
        hipL.setText(client.getHipLength());
        elbowL.setText(client.getElbowLenth());
        sleeveL.setText(client.getSleeveLength());
        backwaisL.setText(client.getBackWaistLenth());
        shoulderL.setText(client.getShoulderLenth());
        backW.setText(client.getBackWidth());
        kneeL.setText(client.getKneeLength());
        breastD.setText(client.getBreastDistance());
        underBustC.setText(client.getUnderBustCirc());
        underBustH.setText(client.getUnderBustHeight());
        riseL.setText(client.getRiseLength());
        trouserL.setText(client.getTrouserLength());
        floorL.setText(client.getFloorLength());
        straplessC.setText(client.getStraplessNecklineCirc⁠⁠⁠⁠());
        notes.setText(client.getNotes());
        style.setText(client.getStyle());



    }
}