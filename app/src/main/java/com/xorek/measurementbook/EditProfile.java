package com.xorek.measurementbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.nameClient_val)
    EditText nameClient;

    @BindView(R.id.sexClient_val)
    EditText sex;

    @BindView(R.id.style_val)
    EditText style;

    @BindView(R.id.phoneClient_val)
    EditText phoneClient;

    @BindView(R.id.dateClient_val)
    EditText dateClient;

    @BindView(R.id.deliverydateClient_val)
    EditText deliverydateClient;

    @BindView(R.id.imgv)
    CircleImageView img;

    @BindView(R.id.neckcirc_val)
    EditText neck;

    @BindView(R.id.bustcirc_val)
    EditText bustC;

    @BindView(R.id.bustHeight_val)
    EditText bustH;

    @BindView(R.id.waistcirc_val)
    EditText waistC;

    @BindView(R.id.hipcirc_val)
    EditText hipC;

    @BindView(R.id.hiplength_val)
    EditText hipL;

    @BindView(R.id.armholecirc_val)
    EditText armC;

    @BindView(R.id.frontwaistlength_val)
    EditText frontL;

    @BindView(R.id.elbowlength_val)
    EditText elbowL;

    @BindView(R.id.sleevelength_val)
    EditText sleeveL;

    @BindView(R.id.backwaistlength_val)
    EditText backwaisL;

    @BindView(R.id.shoulderLenth_val)
    EditText shoulderL;

    @BindView(R.id.backwidth_val)
    EditText backW;

    @BindView(R.id.kneelength_val)
    EditText kneeL;

    @BindView(R.id.breastdistance_val)
    EditText breastD;

    @BindView(R.id.underbustcirc_val)
    EditText underBustC;

    @BindView(R.id.underbustheight_val)
    EditText underBustH;

    @BindView(R.id.riselength_val)
    EditText riseL;

    @BindView(R.id.trouserlength_val)
    EditText trouserL;

    @BindView(R.id.floorlength_val)
    EditText floorL;

    @BindView(R.id.straplessnecklinecirc⁠⁠⁠⁠_val)
    EditText straplessC;

    @BindView(R.id.text)
    EditText notes;

    ExpandableRelativeLayout expandableLayout;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_PICK_IMAGE_REQUEST_CODE = 200;

    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "Measureme";
    private Uri fileUri;

   String namep, phonep, datep, deliverydatep, neckp, bustCp, bustHp, waistCp, hipCp, hipLp, armCp, frontLp, elbowLp, sleeveLp, backwaisLp, shoulderLp, backWp, kneeLp,breastDp, underBustCp, underBustHp, riseLp, trouserLp, floorLp, straplessCp, notesp, image, sexp, stylep;
    long rowid;
    DbHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        ButterKnife.bind(this);
        context = getApplicationContext();



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            namep = extras.getString("name");
            phonep = extras.getString("phone");
            datep = extras.getString("date");
            deliverydatep = extras.getString("delivery");
            neckp = extras.getString("neck");
            bustCp = extras.getString("bustC");
            bustHp = extras.getString("bustH");
            waistCp = extras.getString("waistC");
            hipCp = extras.getString("hipC");
            hipLp = extras.getString("hipL");
            armCp = extras.getString("armC");
            frontLp = extras.getString("frontL");
            elbowLp = extras.getString("elbowL");
            sleeveLp = extras.getString("sleeveL");
            backwaisLp = extras.getString("backwaisL");
            shoulderLp = extras.getString("shoulderL");
            backWp = extras.getString("backW");
            kneeLp = extras.getString("kneeL");
            breastDp = extras.getString("breastD");
            underBustCp = extras.getString("underBustC");
            underBustHp = extras.getString("underBustH");
            riseLp = extras.getString("riseL");
            trouserLp = extras.getString("trouserL");
            floorLp = extras.getString("floorL");
            straplessCp = extras.getString("straplessC");
            notesp = extras.getString("notes");
            image = extras.getString("imagep");
            sexp = extras.getString("sex");
            stylep = extras.getString("style");
            rowid = extras.getLong("id");

        }
        nameClient.setText(namep);
        phoneClient.setText(phonep);
        dateClient.setText(datep);
        deliverydateClient.setText(deliverydatep);
        neck.setText(neckp);
        bustC.setText(bustCp);
        bustH.setText(bustHp);
        waistC.setText(waistCp);
        hipC.setText(hipCp);
        hipL.setText(hipLp);
        armC.setText(armCp);
        frontL.setText(frontLp);
        elbowL.setText(elbowLp);
        sleeveL.setText(sleeveLp);
        backwaisL.setText(backwaisLp);
        shoulderL.setText(shoulderLp);
        backW.setText(backWp);
        kneeL.setText(kneeLp);
        breastD.setText(breastDp);
        underBustC.setText(underBustCp);
        underBustH.setText(underBustHp);
        riseL.setText(riseLp);
        trouserL.setText(trouserLp);
        floorL.setText(floorLp);
        straplessC.setText(straplessCp);
        notes.setText(notesp);
        sex.setText(sexp);
        style.setText(stylep);

    if(!(image==null)) {
        Uri fileuri = Uri.parse(image);
        BitmapFactory.Options options = new BitmapFactory.Options();

    // downsizing image as it throws OutOfMemory Exception for larger
    // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(fileuri.getPath(),
            options);



        img.setImageBitmap(bitmap);

        }
    }
    @OnClick(R.id.fab)
    public void update(){

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



        dbHandler = new DbHandler(context);

        dbHandler.updateDetail(rowid, client);

        Intent intent = new Intent(getApplicationContext(),MeasurementBook.class);
        startActivity(intent);



    }

    //@OnClick(R.id.imgv)
    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);


        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
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

    }
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            }
        }if (requestCode == GALLERY_PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK){
                fileUri = data.getData();
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                image = String.valueOf(fileUri.getPath());

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                        options);

                img.setImageBitmap(bitmap);
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

            image = String.valueOf(fileUri.getPath());

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            img.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.imgv)
    public void selectImage() {
        final CharSequence[] items = {"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Gallery")){

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivityForResult(pickPhoto.createChooser(pickPhoto, "Select File"), GALLERY_PICK_IMAGE_REQUEST_CODE);
                    }

                }else  if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

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

}
