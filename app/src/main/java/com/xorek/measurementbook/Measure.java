package com.xorek.measurementbook;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.spotpin.app.galleryimagepicker.GalleryImagePicker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Measure extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_PICK_IMAGE_REQUEST_CODE = 200;
    public final static int SOME_WIDTH = 430;


    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "MeasurementBook";

    private Uri fileUri; // file url to store image/video

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.imgv)
    CircleImageView img;


    @BindView(R.id.style_val)
    EditText style;

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

    @BindView(R.id.note_val)
    EditText notes;

    @BindView(R.id.nameClient_val)
    TextView nameClient;


    @BindView(R.id.phoneClient_val)
    TextView phoneClient;


    @BindView(R.id.dateClient_val)
    TextView dateClient;

    @BindView(R.id.deliverydateClient_val)
    TextView deliverydateClient;

    @BindView(R.id.sexClient_val)
    TextView sexClient;


    String name;
    String phone;
    String date;
    String deliverydate;
    String sx;
    static String text = "";
    static String image = "";

    Context context;

    AlarmManager alarmManager, alarmag;
    ExifInterface ei;
    Long alarm, remind;

    String SAVEPIC = "/storage/emulated/0/Pictures/MeasurementBook/";

    private PendingIntent pendingIntent;

    ExpandableRelativeLayout expandableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


        ButterKnife.bind(this);
        phoneClient = findViewById(R.id.phoneClient_val);
        nameClient= findViewById(R.id.nameClient_val);
        dateClient = findViewById(R.id.dateClient_val);
        deliverydateClient =findViewById(R.id.deliverydateClient_val);
        sexClient =findViewById(R.id.sexClient_val);
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
        notes= findViewById(R.id.note_val);





//        img = (ImageView)findViewById(R.id.imgv);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               dispatchTakePictureIntent();
//            }
//        });



        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        context= getApplicationContext();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            phone = extras.getString("phone");
            date = extras.getString("date");
            deliverydate = extras.getString("delivery");
            alarm = extras.getLong("alarm");
            sx = extras.getString("sex");
            remind = extras.getLong("remind");


        }

        nameClient.setText(name);
        nameClient.setVisibility(View.INVISIBLE);
        phoneClient.setText(phone);
        phoneClient.setVisibility(View.INVISIBLE);
        dateClient.setText(date);
        dateClient.setVisibility(View.INVISIBLE);
        deliverydateClient.setVisibility(View.INVISIBLE);
        sexClient.setText(sx);
        sexClient.setVisibility(View.INVISIBLE);





        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



//    public void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }

    /*
 * Capturing Camera Image will lauch camera app requrest image capture
 */
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

    public void selectImage(View view) {
//        final CharSequence[] items = {"CAMERA","GALLERY", "CANCEL"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Measure.this);
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
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        ListAdapter adapter = new ArrayAdapterWithIcon(Measure.this, items, icons);

        new AlertDialog.Builder(Measure.this).setTitle("SELECT IMAGE")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {

                        if (items[item].equals("Camera")){

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                        } else if (items[item].equals("Gallery")){

                            GalleryImagePicker.Options options = new GalleryImagePicker.Options();
                            options.setStatusBarColor(ContextCompat.getColor(Measure.this, R.color.app_black));
                            options.setToolbarColor(ContextCompat.getColor(Measure.this, R.color.colorRed));

                            GalleryImagePicker.of()
                                    .withActivity(Measure.this)
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
                previewCapturedImage();
            }

        }

        if (requestCode == GalleryImagePicker.REQUEST_GALLERY_IMAGE) {

            if (resultCode == RESULT_OK) {
                String imagePath = data.getStringExtra(GalleryImagePicker.RESULT_FILE_PATH);

                Picasso.with(Measure.this)
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
//                options.inSampleSize = 4;
//
//
//
////
//
//                String im =  String.valueOf(fileUri.getPath());
//
//                Uri uri = Uri.parse("file://"+im);
//                if(fileUri.toString().startsWith("content://com.google.android.apps.photos.contentprovider"))
//                {
//                    image = getImageUrlWithAuthority(context, fileUri);
//                    text = getRealPathFromURI(fileUri);
//
////                    try {
////                        copyFileOrDirectory(new File(text), getOutputMediaFile(MEDIA_TYPE_IMAGE));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//                }
//                else if(fileUri.toString().startsWith("file://"))
//                {
//                    String temp = fileUri.toString().replace("file:///", "/");
//
//                    image =fileUri.toString();
//                    text = temp;
//                    Log.d("path1", text);
//
//                        copyFileOrDirectory(text, SAVEPIC);
//
//
//                }else if(fileUri.toString().startsWith("content://media/external/"))
//                {
//
//                    image =fileUri.toString();
//                    text = getRealPathFromURI(fileUri);
//                    Log.d("path2", text);
////                    try {
////                        copyFileOrDirectory(text, getOutputMediaFile(MEDIA_TYPE_IMAGE));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//                }else if(fileUri.toString().startsWith("content://com.simplemobiletools.gallery.provider/")) {
//                    image =fileUri.toString();
////                    text = getRealPathFromURI(fileUri);
//                    text = image.replace("content://com.simplemobiletools.gallery.provider/external_files/","/storage/");
//
//
////                   String selectedImagePath = ImageFilePath.getPath(getApplicationContext(), fileUri);
//                    Log.d("path3", fileUri.getPath());
//                    Log.d("pathh", image);
//                    Log.d("path2", text);
//                }
//                else {
//                        image = fileUri.toString();
////                    try {
////                        copyFileOrDirectory(new File(image), getOutputMediaFile(MEDIA_TYPE_IMAGE));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//
//                    }
//                }
//
//                Log.d("furi", String.valueOf(fileUri));
//                Log.d("furi1", fileUri.getPath());
//                Log.d("ur", String.valueOf(image));
//                Log.d("path", text);
//
//
//
//
////                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
////                        options);
////
////                img.setImageBitmap(bitmap);
//
////                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
////
////                    try {
////
////                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, uri));
////
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }else {
//
//                    try {
//
//                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileUri));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
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



//            image = fileUri.toString();

            String im =  String.valueOf(fileUri.getPath());


            Uri uri = Uri.parse("file://"+im);
//            text = getRealPathFromURI(fileUri);


            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            if(fileUri.toString().startsWith("content://com.google.android.apps.photos.contentprovider"))
            {
                image = getImageUrlWithAuthority(context, fileUri);
                text = getRealPathFromURI(fileUri);
            }
            else if(fileUri.toString().startsWith("file://"))
            {
                String temp = fileUri.toString().replace("file:///", "/");

                image =fileUri.toString();
                text = temp;
                Log.d("path1", text);

            }else if(fileUri.toString().startsWith("content://media/external/"))
            {

                image =fileUri.toString();
                text = getRealPathFromURI(fileUri);
                Log.d("path2", text);

            }else {
                image = fileUri.toString();

            }


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



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            File imgFile  = new File(mCurrentPhotoPath);
//
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
//            img.setImageBitmap(bitmap);
//            image = getBytes(bitmap);
//        }
//    }





//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    public static byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }

    @OnClick(R.id.fab)
    public void newContact(View view){
        Log.d("Remind", String.valueOf(remind));
        startingAlarm();
        if (remind != 0) {
            startingReminder();
        }
        String date1 = getDate(alarm, "dd/MM/yyyy");

        deliverydateClient.setText(date1);

        DbHandler dbHandler = new DbHandler(this);

        Client client = new Client(nameClient.getText().toString(), phoneClient.getText().toString(), dateClient.getText().toString(), deliverydateClient.getText().toString(), neck.getText().toString(), bustC.getText().toString(), waistC.getText().toString(),hipC.getText().toString(),armC.getText().toString(),bustH.getText().toString(),frontL.getText().toString(),hipL.getText().toString(),elbowL.getText().toString(),sleeveL.getText().toString(),backwaisL.getText().toString(),shoulderL.getText().toString(),backW.getText().toString(),kneeL.getText().toString(),breastD.getText().toString(),underBustC.getText().toString(),underBustH.getText().toString(),riseL.getText().toString(),trouserL.getText().toString(),floorL.getText().toString(),straplessC.getText().toString(),notes.getText().toString(), image, style.getText().toString(), sexClient.getText().toString(), text);
//        Log.d("TAG", nameClient.getText().toString());
//        Log.d("TAG", phoneClient.getText().toString());



        dbHandler.addContact(client);
        Intent intent = new Intent(getApplicationContext(),MeasurementBook.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mylist", (Serializable) resInfoList);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public void startingAlarm() {

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



            alarmManager.set(AlarmManager.RTC_WAKEUP,alarm  , PendingIntent.getBroadcast(context,g,myIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            Log.d("TIMEALARM", String.valueOf((alarm)));






    }

    public void startingReminder() {

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



        Intent myIntent = new Intent(context, ReminderReceiver.class);
        myIntent.putExtra("name", name);
        myIntent.putExtra("id", g);
//      sendBroadcast(myIntent);
        alarmag = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//      Intent intent = new Intent(context, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);


        //Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;
        //Log.d("Delievry", String.valueOf(alarm));



        alarmag.set(AlarmManager.RTC_WAKEUP,remind  , PendingIntent.getBroadcast(context,g,myIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("TIMEREMIND", String.valueOf((remind)));






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

        InputStream input = context.getContentResolver().openInputStream(selectedImage);



        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else {
            if(text.equals("")){
                ei = new ExifInterface(selectedImage.getPath());
            }else {
                ei = new ExifInterface(text);
            }
        }

        if(selectedImage.toString().startsWith("file://"))
        {
            String temp = selectedImage.toString().replace("file:///", "/");

            image =selectedImage.toString();
            text = temp;
            Log.d("path1", text);

        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
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

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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


//    public static void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
//            throws IOException {
//
//        if (sourceLocation.isDirectory()) {
//            Log.d("Truely Dir", "True");
//            if (!targetLocation.exists()) {
//                targetLocation.mkdir();
//            }
//
//            String[] children = sourceLocation.list();
//            for (int i = 0; i < sourceLocation.listFiles().length; i++) {
//
//                copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
//                        new File(targetLocation, children[i]));
//            }
//        } else {
//
//            InputStream in = new FileInputStream(sourceLocation);
//
//            OutputStream out = new FileOutputStream(targetLocation);
//
//            // Copy the bits from instream to outstream
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = in.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }
//            in.close();
//            out.close();
//        }
//
////        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TongueTwister/tt_1A.3gp";
////        File destination = new File(destinationPath);
////        try
////        {
////            FileUtils.copyFile(source, destination);
////        }
////        catch (IOException e)
////        {
////            e.printStackTrace();
////        }
//    }

    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }




}
