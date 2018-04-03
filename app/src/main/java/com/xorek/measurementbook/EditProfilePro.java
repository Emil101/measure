package com.xorek.measurementbook;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.spotpin.app.galleryimagepicker.GalleryImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.xorek.measurementbook.Measure.writeToTempImageAndGetPathUri;

public class EditProfilePro extends AppCompatActivity {

//    @BindView(R.id.fab)
    FloatingActionButton fab;

//    @BindView(R.id.nameClient_val)
    EditText nameClient;

//    @BindView(R.id.sexClient_val)
    EditText sex;

//    @BindView(R.id.style_val)
    EditText style;

//    @BindView(R.id.phoneClient_val)
    EditText phoneClient;

//    @BindView(R.id.dateClient_val)
    EditText dateClient;

//    @BindView(R.id.deliverydateClient_val)
    TextView deliverydateClient;

//    @BindView(R.id.imgv)
    CircleImageView img;

//    @BindView(R.id.neckcirc_val)
    EditText neck;

//    @BindView(R.id.bustcirc_val)
    EditText bustC;

//    @BindView(R.id.bustHeight_val)
    EditText bustH;

//    @BindView(R.id.waistcirc_val)
    EditText waistC;

//    @BindView(R.id.hipcirc_val)
    EditText hipC;

//    @BindView(R.id.hiplength_val)
    EditText hipL;

//    @BindView(R.id.armholecirc_val)
    EditText armC;

//    @BindView(R.id.frontwaistlength_val)
    EditText frontL;

//    @BindView(R.id.elbowlength_val)
    EditText elbowL;

//    @BindView(R.id.sleevelength_val)
    EditText sleeveL;

//    @BindView(R.id.backwaistlength_val)
    EditText backwaisL;

//    @BindView(R.id.shoulderLenth_val)
    EditText shoulderL;

//    @BindView(R.id.backwidth_val)
    EditText backW;

//    @BindView(R.id.kneelength_val)
    EditText kneeL;

//    @BindView(R.id.breastdistance_val)
    EditText breastD;

//    @BindView(R.id.underbustcirc_val)
    EditText underBustC;

//    @BindView(R.id.underbustheight_val)
    EditText underBustH;

//    @BindView(R.id.riselength_val)
    EditText riseL;

//    @BindView(R.id.trouserlength_val)
    EditText trouserL;

//    @BindView(R.id.floorlength_val)
    EditText floorL;

//    @BindView(R.id.straplessnecklinecirc⁠⁠⁠⁠_val)
    EditText straplessC;

//    @BindView(R.id.text)
    EditText notes;

    ExpandableRelativeLayout expandableLayout;
    public static final int REQUEST_GALLERY_IMAGE = 70;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_PICK_IMAGE_REQUEST_CODE = 200;

    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "MeasurementBook";
    private Uri fileUri;
    AlarmManager alarmManager, alarmag;
    ExifInterface ei;
    Long alarm, remind;

    private Calendar calendar;
    private int year, month, day, hour, minute;
//    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


    String namep, phonep, datep, deliverydatep, neckp, bustCp, bustHp, waistCp, hipCp, hipLp, armCp, frontLp, elbowLp, sleeveLp, backwaisLp, shoulderLp, backWp, kneeLp, breastDp, underBustCp, underBustHp, riseLp, trouserLp, floorLp, straplessCp, notesp, image, sexp, stylep;
    long rowid;
    DbHandler dbHandler;
    Context context;
    Long id;
    static String text = "";
    static String tex = "";
    String name;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_pro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        ButterKnife.bind(this);
        context = getApplicationContext();
        phoneClient = findViewById(R.id.phoneClient_val);
        sex = findViewById(R.id.sexClient_val);
        nameClient= findViewById(R.id.nameClient_val);
        dateClient = findViewById(R.id.dateClient_val);
        deliverydateClient =findViewById(R.id.deliverydateClient_val);
        fab = findViewById(R.id.fab);
        img = findViewById(R.id.imgv);
        style= findViewById(R.id.style_val);
        neck= findViewById(R.id.neckcirc_val);
        bustC= findViewById(R.id.bustcirc_val);
        bustH= findViewById(R.id.bustHeight_val);
        waistC= findViewById(R.id.waistcirc_val);
        hipC= findViewById(R.id.hipcirc_val);
        hipL= findViewById(R.id.hiplength_val);
        armC= findViewById(R.id.armholecirc_val);
        frontL= findViewById(R.id.frontwaistlength_val);
        elbowL= findViewById(R.id.elbowlength_val);
        sleeveL= findViewById(R.id.sleevelength_val);
        backwaisL= findViewById(R.id.backwaistlength_val);
        shoulderL= findViewById(R.id.shoulderLenth_val);
        backW= findViewById(R.id.backwidth_val);
        kneeL= findViewById(R.id.kneelength_val);
        breastD= findViewById(R.id.breastdistance_val);
        underBustC= findViewById(R.id.underbustcirc_val);
        underBustH= findViewById(R.id.underbustheight_val);
        riseL= findViewById(R.id.riselength_val);
        trouserL= findViewById(R.id.trouserlength_val);
        floorL= findViewById(R.id.floorlength_val);
        straplessC= findViewById(R.id.straplessnecklinecirc⁠⁠⁠⁠_val);
        notes= findViewById(R.id.text);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rowid = extras.getLong("id");
            Log.d("EditPro", String.valueOf(rowid));


        }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        retrieveRow();

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, R.style.MyDialog,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
                    deliverydateClient.setText(new StringBuilder().append(arg3).append("/")
                            .append(arg2+1).append("/").append(arg1));
                    day = arg3;
                    month= arg2;
                    year = arg1;

                }
            };

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public void retrieveRow() {
        Client client = new Client();
        dbHandler = new DbHandler(context);
        Cursor cr = dbHandler.getRow(dbHandler, rowid);

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
                client.setImagepath(cr.getString(29));

//                    Toast.makeText(getApplicationContext(),cr.getString(0),Toast.LENGTH_SHORT).show();
            } while (cr.moveToNext());

            cr.close();
        }
        image = client.getImage();

        nameClient.setText(client.getName());
        phoneClient.setText(client.getPhoneNumber());
        dateClient.setText(client.getDate());
        deliverydateClient.setText(client.getDiliveryDate());
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
        underBustH.setText(client.getUnderBustCirc());
        riseL.setText(client.getRiseLength());
        trouserL.setText(client.getTrouserLength());
        floorL.setText(client.getFloorLength());
        straplessC.setText(client.getStraplessNecklineCirc⁠⁠⁠⁠());
        notes.setText(client.getNotes());
        sex.setText(client.getSex());
        style.setText(client.getStyle());
        date = client.getDiliveryDate();

        if (!(client.getImage().equals(""))) {
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//
//
//                Uri fileuri = Uri.parse("file://" + client.getImage());
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                // downsizing image as it throws OutOfMemory Exception for larger
//                // images
//                options.inSampleSize = 4;
//
//                final Bitmap bitmap = BitmapFactory.decodeFile(fileuri.getPath(),
//                        options);
//
//                try {
//                    img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else {

            text = client.getImagepath();


//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileuri);
            // Log.d(TAG, String.valueOf(bitmap));

            Uri fileuri = Uri.parse(client.getImage());

            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.upload);
//            if ((client.getImage().startsWith("content://com.google.android.apps.photos.contentprovider"))) {
//                try {
//
//
//                    img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if ((client.getImage().startsWith("file:///"))) {
//                try {
//                    if (text.equals("")){
//                        String temp = client.getImage().replace("file:///", "/");
//                        text = temp;
//                        Log.d("TEMP", text);
//                    }
//
//                    img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                try {
//                    img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if ((client.getImage().startsWith("content://com.google.android.apps.photos.contentprovider"))) {
////                try {
////
////
////                    img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(client.getImagepath()).centerCrop().into(img);
//                } else {
//                    Picasso.with(context).load(client.getImagepath()).resize(300, 300).centerCrop().into(img);
//                }
//            } else if ((client.getImage().startsWith("file:///"))) {
////                try {
////
////                    if (text.equals("")){
////                        String temp = client.getImage().replace("file:///", "/");
////                        text = temp;
////                        Log.d("TEMP", text);
////                    }
////
////                    img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(client.getImage()).centerCrop().into(img);
//                } else {
//                    Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
//                }
//            } else {
////                try {
////                    img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(client.getImage()).centerCrop().into(img);
//                } else {
//                    Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
//                }
//            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if ((client.getImage().startsWith("file:///"))) {
                    Glide.with(context).load(client.getImage()).centerCrop().into(img);
                } else {
                    Glide.with(context).load(new File(client.getImage())).centerCrop().into(img);
                }
            } else {
                if ((client.getImage().startsWith("file:///"))) {
                    Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
                } else {
                    Picasso.with(context)
                            .load(new File(client.getImage())).resize(300, 300).centerCrop()
                            .into(img);
                }
            }
        }
//        }


    }
    public void setate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();

    }


    public void update(View view){

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        alarm = calendar.getTimeInMillis();

        if(!(date.equals(deliverydateClient.getText().toString())))
            startingAlarm();




        Client client = new Client();

        client.setId((int) rowid);
        client.setName(nameClient.getText().toString());
        client.setPhoneNumber(phoneClient.getText().toString());
        client.setDate(dateClient.getText().toString());
        client.setDiliveryDate(deliverydateClient.getText().toString());
        client.setNeckCirc(neck.getText().toString());
        client.setBustCirc(bustC.getText().toString());
        client.setBustHeight(bustH.getText().toString());
        client.setWaistCirc(waistC.getText().toString());
        client.setHipCirc(hipC.getText().toString());
        client.setHipLength(hipL.getText().toString());
        client.setArmholeCirc(armC.getText().toString());
        client.setFrontWaistLenth(frontL.getText().toString());
        client.setElbowLenth(elbowL.getText().toString());
        client.setSleeveLength(sleeveL.getText().toString());
        client.setBackWaistLenth(backwaisL.getText().toString());
        client.setShoulderLenth(shoulderL.getText().toString());
        client.setBackWidth(backW.getText().toString());
        client.setKneeLength(kneeL.getText().toString());
        client.setBreastDistance(breastD.getText().toString());
        client.setUnderBustCirc(underBustC.getText().toString());
        client.setUnderBustHeight(underBustH.getText().toString());
        client.setRiseLength(riseL.getText().toString());
        client.setTrouserLength(trouserL.getText().toString());
        client.setFloorLength(floorL.getText().toString());
        client.setStraplessNecklineCirc⁠⁠⁠⁠(straplessC.getText().toString());
        client.setNotes(notes.getText().toString());
        client.setImage(image);
        client.setSex(sex.getText().toString());
        client.setStyle(style.getText().toString());
        client.setImagepath(text);

        Log.d("IMM",client.getImagepath());



        dbHandler = new DbHandler(context);


        dbHandler.updateDetail(rowid, client);

        Intent intent = new Intent(getApplicationContext(),MeasurementBook.class);
        startActivity(intent);



    }

//    @OnClick(R.id.imgv)
//    public void captureImage() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//        // start the image capture Intent
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//    }


    @OnClick(R.id.imgv)
    public void selectImage() {
//        final CharSequence[] items = {"CAMERA","GALLERY", "CANCEL"};

//        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfilePro.this,  R.style.MyDialog);
//        builder.setTitle("ADD IMAGE");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (items[i].equals("CAMERA")){
//
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//
//                } else if (items[i].equals("GALLERY")){
//
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    pickPhoto.setType("image/*");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        startActivityForResult(pickPhoto.createChooser(pickPhoto, "Select File"), GALLERY_PICK_IMAGE_REQUEST_CODE);
//                    }
//
//                }else  if(items[i].equals("CANCEL")){
//                    dialogInterface.dismiss();
//                }
//            }
//        });
//        builder.show();

        final String [] items = new String[] {"Gallery", "Camera", "Cancel"};
        final Integer[] icons = new Integer[] {R.drawable.gallery, R.drawable.cam, R.drawable.ic_action_cancel};
        ListAdapter adapter = new ArrayAdapterWithIcon(EditProfilePro.this, items, icons);

        new AlertDialog.Builder(EditProfilePro.this).setTitle("SELECT IMAGE")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {

                        if (items[item].equals("Camera")){

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                        } else if (items[item].equals("Gallery")){

//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            pickPhoto.setType("image/*");
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                startActivityForResult(pickPhoto.createChooser(pickPhoto, "Select File"), GALLERY_PICK_IMAGE_REQUEST_CODE);
//                            }
                            GalleryImagePicker.Options options = new GalleryImagePicker.Options();
                            options.setStatusBarColor(ContextCompat.getColor(EditProfilePro.this, R.color.app_black));
                            options.setToolbarColor(ContextCompat.getColor(EditProfilePro.this, R.color.colorRed));

                            GalleryImagePicker.of()
                                    .withActivity(EditProfilePro.this)
                                    .withOptions(options)
                                    .start();

                        }else  if(items[item].equals("Cancel")){
                            dialog.dismiss();
                        }

                    }
                }).show();

    }
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
        try {
            ei= new ExifInterface(String.valueOf(fileUri));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                text = "";
                previewCapturedImage();
            }


        }
        if (requestCode == GalleryImagePicker.REQUEST_GALLERY_IMAGE) {

            if (resultCode == RESULT_OK) {
                String imagePath = data.getStringExtra(GalleryImagePicker.RESULT_FILE_PATH);

                Picasso.with(EditProfilePro.this)
                        .load(new File(imagePath))
                        .into(img);
                image = imagePath;
            }

        }
        if (requestCode == GALLERY_PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK){
//                fileUri = data.getData();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                // downsizing image as it throws OutOfMemory Exception for larger
//                // images
//               // options.inSampleSize = 8;
//
//
//                String im =String.valueOf(fileUri.getPath());
//                Uri uri = Uri.parse("file://"+im);
////                final Bitmap bitmap = BitmapFactory.decodeFile(text);
////                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
////                    try {
////                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, uri));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }else {
//
//                if(fileUri.toString().startsWith("content://com.google.android.apps.photos.contentprovider"))
//                {
//                    image = getImageUrlWithAuthority(context, fileUri);
//                    text = getRealPathFromURI(fileUri);
//                }
//                else if(fileUri.toString().startsWith("file://"))
//                {
//                    String temp = fileUri.toString().replace("file:///", "/");
//
//                    image =fileUri.toString();
//                    text = temp;
//                    Log.d("path1", text);
//
//                }else{
//                    image = fileUri.toString();
//                }
////                text =  getRealPathFromURI(fileUri);
////                Log.d("snsd", text);
//
//                    try {
//                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileUri));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                }
            }
        }
    }

    /*
 * Display image from a path to ImageView
 */
    private void previewCapturedImage() {
        try {

            img.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            image = fileUri.toString();
            Log.d("path", fileUri.getPath());
//            Log.d("path", getRealPathFromURI(fileUri));
            String im = String.valueOf(fileUri.getPath());
//            text =  getRealPathFromURI(fileUri);
            Uri uri = Uri.parse("file://"+im);

//            final Bitmap bitmap = BitmapFactory.decodeFile(text);
            try {
                img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }else {
            return null;
        }

        return mediaFile;
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context,img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input;
        if(text.equals("")){

            input = context.getContentResolver().openInputStream(selectedImage);

        }else {

            input = new FileInputStream(text);

        }



        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else {
            if (text.equals("")) {
                ei = new ExifInterface(selectedImage.getPath());
            } else {
                ei = new ExifInterface(text);
            }
        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 5;
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return rotateImage(bitmap, 90);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return rotateImage(bitmap, 90);
                }
            case ExifInterface.ORIENTATION_ROTATE_180:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return rotateImage(bitmap, 180);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return rotateImage(bitmap, 180);
                }
            case ExifInterface.ORIENTATION_ROTATE_270:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return rotateImage(bitmap, 270);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return rotateImage(bitmap, 270);
                }
            default:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return bitmap;
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return bitmap;
                }
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void startingAlarm() {

        name = nameClient.getText().toString();
        SharedPreferences pref = getSharedPreferences("id.config", Context.MODE_PRIVATE);
        int g = pref.getInt("id", 0);
        Log.d("G", String.valueOf(g));
        g++;
        Log.d("G", String.valueOf(g));
        pref = getSharedPreferences("id.config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id", g);
        editor.apply();
        Log.d("G", String.valueOf(pref.getInt("id", 0)));


        Intent myIntent = new Intent(context, AlarmReceiver.class);
        myIntent.putExtra("name", name);
        myIntent.putExtra("id", g);
//      sendBroadcast(myIntent);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//      Intent intent = new Intent(context, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);


        //Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;
        //Log.d("Delievry", String.valueOf(alarm));


        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm, PendingIntent.getBroadcast(context, g, myIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("TIMEALARM", String.valueOf((alarm)));
    }








    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}
