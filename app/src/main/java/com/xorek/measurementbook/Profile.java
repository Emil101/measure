package com.xorek.measurementbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {


    DbHandler dbHandler;
    Context context;
    long id;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.imgv)
    CircleImageView img;

    @BindView(R.id.sexClient_val)
    TextView sex;

    @BindView(R.id.neckcirc_val)
    TextView neck;

    @BindView(R.id.bustcirc_val)
    TextView bustC;

    @BindView(R.id.bustHeight_val)
    TextView bustH;

    @BindView(R.id.waistcirc_val)
    TextView waistC;

    @BindView(R.id.hipcirc_val)
    TextView hipC;

    @BindView(R.id.hiplength_val)
    TextView hipL;

    @BindView(R.id.armholecirc_val)
    TextView armC;

    @BindView(R.id.frontwaistlength_val)
    TextView frontL;

    @BindView(R.id.elbowlength_val)
    TextView elbowL;

    @BindView(R.id.sleevelength_val)
    TextView sleeveL;

    @BindView(R.id.backwaistlength_val)
    TextView backwaisL;

    @BindView(R.id.shoulderLenth_val)
    TextView shoulderL;

    @BindView(R.id.backwidth_val)
    TextView backW;

    @BindView(R.id.kneelength_val)
    TextView kneeL;

    @BindView(R.id.breastdistance_val)
    TextView breastD;

    @BindView(R.id.underbustcirc_val)
    TextView underBustC;

    @BindView(R.id.underbustheight_val)
    TextView underBustH;

    @BindView(R.id.riselength_val)
    TextView riseL;

    @BindView(R.id.trouserlength_val)
    TextView trouserL;

    @BindView(R.id.floorlength_val)
    TextView floorL;

    @BindView(R.id.straplessnecklinecirc⁠⁠⁠⁠_val)
    TextView straplessC;

    @BindView(R.id.text)
    TextView notes;

    @BindView(R.id.nameClient_val)
    TextView nameClient;

    @BindView(R.id.phoneClient_val)
    TextView phoneClient;

    @BindView(R.id.dateClient_val)
    TextView dateClient;

    @BindView(R.id.deliverydateClient_val)
    TextView deliverydateClient;
    @BindView(R.id.style_val)
    TextView style;

    Client client = new Client();
    ExpandableRelativeLayout expandableLayout, expandableLayout1, expandableLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");


        }

        ButterKnife.bind(this);

        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expandableLayout.collapse();

        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout.collapse();

        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout.collapse();

        context = getApplicationContext();



        retrieveRow();

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
        neck.setText(client.getNeckCirc());
        bustC.setText(client.getUnderBustCirc());
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
        underBustH.setText(client.getUnderBustCirc());
        riseL.setText(client.getRiseLength());
        trouserL.setText(client.getTrouserLength());
        floorL.setText(client.getFloorLength());
        straplessC.setText(client.getStraplessNecklineCirc⁠⁠⁠⁠());
        notes.setText(client.getNotes());
        sex.setText(client.getSex());
        style.setText(client.getStyle());

        if (!(client.getImage() == null)) {
            Uri fileuri = Uri.parse(client.getImage());
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileuri.getPath(),
                    options);

            img.setImageBitmap(bitmap);
        }



    }
    public void doToggleView(View view) {
        expandableLayout.toggle();
       ;
    }

    public void doToggleView1(View view) {
        expandableLayout2.toggle();
    }

    public void doToggleView2(View view) {
        expandableLayout1.toggle();
    }

   @OnClick(R.id.fab)
    public void startEdit(){
       Intent in = new Intent(getApplicationContext(), EditProfile.class);
       in.putExtra("name",nameClient.getText().toString());
       in.putExtra("phone",phoneClient.getText().toString());
       in.putExtra("date",dateClient.getText().toString());
       in.putExtra("delivery",deliverydateClient.getText().toString());
       in.putExtra("neck",neck.getText().toString());
       in.putExtra("bustC",bustC.getText().toString());
       in.putExtra("bustH",bustH.getText().toString());
       in.putExtra("waistC",waistC.getText().toString());
       in.putExtra("hipC",hipC.getText().toString());
       in.putExtra("hipL",hipL.getText().toString());
       in.putExtra("armC",armC.getText().toString());
       in.putExtra("frontL",frontL.getText().toString());
       in.putExtra("elbowL",elbowL.getText().toString());
       in.putExtra("sleeveL",sleeveL.getText().toString());
       in.putExtra("backwaisL",backwaisL.getText().toString());
       in.putExtra("shoulderL",shoulderL.getText().toString());
       in.putExtra("backW",backW.getText().toString());
       in.putExtra("kneeL",kneeL.getText().toString());
       in.putExtra("breastD",breastD.getText().toString());
       in.putExtra("underBustC",underBustC.getText().toString());
       in.putExtra("underBustH",underBustH.getText().toString());
       in.putExtra("riseL",riseL.getText().toString());
       in.putExtra("trouserL",trouserL.getText().toString());
       in.putExtra("floorL",floorL.getText().toString());
       in.putExtra("straplessC",straplessC.getText().toString());
       in.putExtra("notes",notes.getText().toString());
       in.putExtra("imagep", client.getImage());
       in.putExtra("sex", sex.getText().toString());
       in.putExtra("style", style.getText().toString());
       in.putExtra("id", id);


       startActivity(in);

    }




}
