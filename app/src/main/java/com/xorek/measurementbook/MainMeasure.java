package com.xorek.measurementbook;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainMeasure extends AppCompatActivity {

    ProgressDialog progressDialog;
    @BindView(R.id.imgv2)
    FrameLayout newMeasure;
//
    @BindView(R.id.imgv3)
    FrameLayout seeMeasure;

    private int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5;
    private PrefManager prefManager;

    public static final int MULTIPLE_PERMISSIONS = 10;

    String[] permissions= new String[]{
            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(getApplicationContext(), IntroSlider.class));

            finish();
        }
        setContentView(R.layout.activity_main_measure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);

        ButterKnife.bind(this);
//        seeMeasure = (FrameLayout)findViewById(R.id.imgv2);
//        newMeasure = (FrameLayout)findViewById(R.id.imgv3);

        context = getApplicationContext();
//        SharedPreferences pref = getSharedPreferences("id.config", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("id", 0);
//        editor.apply();
//        Log.d("F", String.valueOf(pref.getInt("id",0)));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (!Settings.canDrawOverlays(this))
//            {
//
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//            }
//        }else
//        {
//            checkPermissions();
//        }

//         progressDialog = new ProgressDialog(this, R.style.MyDialog);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        checkPermissions();




    }
    public void startAct(View view){
//        testPermission();

        Intent intent = new Intent(getApplicationContext(), MeasurementBook.class);

        startActivity(intent);

//        progressDialog.setTitle("Client List");
//        progressDialog.setMessage("Loading..");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    public void startActivit(View view){
//        testPermission();
        startActivity(new Intent(getApplicationContext(), Contact_detail.class));
    }

//    public void testPermission()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if (!Settings.canDrawOverlays(this))
//            {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            //checkPermissions();
        }
    }

    private  boolean checkPermissions()
    {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(MainMeasure.this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permissions granted.
                } else {
                    // no permissions granted.
                }
                return;
            }
        }
    }


}
