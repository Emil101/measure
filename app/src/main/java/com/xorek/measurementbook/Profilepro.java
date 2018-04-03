package com.xorek.measurementbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profilepro extends AppCompatActivity {


    CircleImageView img;
    DbHandler dbHandler;
    Context context;
    FloatingActionButton fab;
    long id;

    Client client = new Client();
    TabLayout tabLayout;
    ViewPager viewPager;
    static String text;
    ProgressDialog progressDialog;
    static ByteArrayOutputStream bytearrayoutputstream;
    static byte[] BYTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
//




        ButterKnife.bind(this);
        context = getApplicationContext();

        img = (CircleImageView)findViewById(R.id.imgv);
        fab = findViewById(R.id.fab);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");

            bytearrayoutputstream = new ByteArrayOutputStream();

            progressDialog = new ProgressDialog(Profilepro.this, R.style.MyDialog);
//        progressDialog.setTitle("Client Details");
//        progressDialog.setMessage("Loading..");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();


        }





        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager, extras);
        tabLayout =(TabLayout)  findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tape));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tape));
        tabLayout.setupWithViewPager(viewPager);


//        final PagerAdapter adapter = new tab_pager_adapter
//                (getSupportFragmentManager(),
//                        tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {

//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });



//        retrieveRow();

        new add().execute();



    }

    @Override
    protected void onStart(){

        progressDialog.dismiss();
        super.onStart();

    }

    public void openPic(){

        Intent in = new Intent(getApplicationContext(), Pic.class);
        in.putExtra("id", id);
//        Log.d("Pro", String.valueOf(id));

        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        startActivity(in);

    }


    private void setupViewPager(ViewPager viewPager, Bundle bundle) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        Fragment fragmentOne = new contact_frag();
        fragmentOne.setArguments(bundle);
        adapter.addFragment(fragmentOne, "Details");

        Fragment fragmentTwo = new measure_fag();
        fragmentTwo.setArguments(bundle);
        adapter.addFragment(fragmentTwo, "Measurement");

        viewPager.setAdapter(adapter);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount(){
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void retrieveRow() {
        dbHandler = new DbHandler(context);
        Cursor cr = dbHandler.getRow(dbHandler, id);

        if ((cr != null) && cr.moveToFirst()) {


            do {


                client.setImage(cr.getString(26));
                client.setImagepath(cr.getString(29));


//                    Toast.makeText(getApplicationContext(),cr.getString(0),Toast.LENGTH_SHORT).show();
            } while (cr.moveToNext());

        }
        if (cr != null) {
            cr.close();
        }


//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//            if (!(client.getImage().equals(""))) {
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
//
//
//                img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        openPic();
//                    }
//                });
//            }
//        }else {



    }



    public void startEdit(View view){
        Intent in = new Intent(getApplicationContext(), EditProfilePro.class);
        in.putExtra("id", id);
        Log.d("Pro", String.valueOf(id));


        startActivity(in);

    }

//    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
//        ExifInterface ei = new ExifInterface(image_absolute_path);
//        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                return rotate(bitmap, 90);
//
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                return rotate(bitmap, 180);
//
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                return rotate(bitmap, 270);
//
//            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
//                return flip(bitmap, true, false);
//
//            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
//                return flip(bitmap, false, true);
//
//            default:
//                return bitmap;
//        }
//    }
//
//    public static Bitmap rotate(Bitmap bitmap, float degrees) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degrees);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//
//    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
//        Matrix matrix = new Matrix();
//        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//
//    public String getImagePath(Uri uri){
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
//        cursor.close();
//
//        cursor = getContentResolver().query(
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }

    private class add extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(MeasurementBook.this, R.style.MyDialog);
//            progressDialog.setTitle("Client List");
//            progressDialog.setMessage("Loading..");
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            //   Toast.makeText(secure.this, "ScreenBgrd process started", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Cursor doInBackground(Object... voids) {
           retrieveRow();
            return null;
        }

        @Override
        protected void onPostExecute(Cursor cr) {

            if (!(client.getImage().equals(""))) {


                Uri fileuri = Uri.parse(client.getImage());
                context.grantUriPermission("com.xorek.measurementbook", fileuri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Log.d("clientImg", client.getImage());
                Log.d("Tag", String.valueOf(fileuri));
                text = client.getImagepath();
                Log.d("Image", text);
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 5;

                final Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.upload);

//                if ((client.getImage().startsWith("content://com.google.android.apps.photos.contentprovider"))) {
//                    try {
//
//
//                        img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                Picasso.with(context).load(client.getImagepath()).resize(300, 300).centerCrop().into(img);
//                }else if((client.getImage().startsWith("content://com.simplemobiletools.gallery.provider/"))){
//                    try {
//
//
////                        img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
//                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else if((client.getImage().startsWith("file:///"))) {
//                    try {
//
////                        if (text.equals("")){
////                            String temp = client.getImage().replace("file:///", "/");
////                            text = temp;
////                            Log.d("TEMP", text);
////                        }
//
////                        img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
//                        text = "";
//                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
//                } else {
//                    try {
//                        img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));//here
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
//                }
//
////                    img.setImageBitmap(bitmap);


                Picasso.with(Profilepro.this)
                        .load(new File(client.getImage())).resize(300, 300).centerCrop()
                        .into(img);

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    if((client.getImage().startsWith("file:///"))){
                        Glide.with(context).load(client.getImage()).centerCrop().into(img);
                    }else {
                        Glide.with(context).load(new File(client.getImage())).centerCrop().into(img);
                    }
                } else {
                    if((client.getImage().startsWith("file:///"))){
                        Picasso.with(context).load(client.getImage()).resize(300, 300).centerCrop().into(img);
                    }else{
                        Picasso.with(Profilepro.this)
                                .load(new File(client.getImage())).resize(300, 300).centerCrop()
                                .into(img);                    }
                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openPic();
                    }
                });


//        }



            }

        }
    }

//            public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
//            throws IOException {
//        int MAX_HEIGHT = 200;
//        int MAX_WIDTH = 200;
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
//        BitmapFactory.decodeStream(imageStream, null, options);
////        try {
////            imageStream.reset();
////        } catch (IOException e) {
////            return null;
////        }
//        imageStream.close();
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        imageStream = context.getContentResolver().openInputStream(selectedImage);
//        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);
//
//        img = rotateImageIfRequired(context,img, selectedImage);
//        return img;
//    }
//
//    private static int calculateInSampleSize(BitmapFactory.Options options,
//                                             int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            // Calculate ratios of height and width to requested height and width
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
//            // with both dimensions larger than or equal to the requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//
//            // This offers some additional logic in case the image has a strange
//            // aspect ratio. For example, a panorama may have a much larger
//            // width than height. In these cases the total pixels might still
//            // end up being too large to fit comfortably in memory, so we should
//            // be more aggressive with sample down the image (=larger inSampleSize).
//
//            final float totalPixels = width * height;
//
//            // Anything more than 2x the requested pixels we'll sample down further
//            final float totalReqPixelsCap = reqWidth * reqHeight * 2;
//
//            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
//                inSampleSize++;
//            }
//        }
//        return inSampleSize;
//    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

//        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        InputStream input;
        if(text.equals("")){

            input = context.getContentResolver().openInputStream(selectedImage);

        }else {
            Log.d("TEXTT", text);
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
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return rotateImage(img, 90);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length,options);
                    return rotateImage(img, 90);
                }
            case ExifInterface.ORIENTATION_ROTATE_180:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);

                    return rotateImage(img, 180);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return rotateImage(img, 180);
                }
            case ExifInterface.ORIENTATION_ROTATE_270:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return rotateImage(img, 270);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return rotateImage(img, 270);
                }
            default:
                if (text.equals("")) {

                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return img;
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

                    BYTE = bytearrayoutputstream.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;

                    final Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length, options);
                    return img;
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

//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(this, MeasurementBook.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//        ProgressDialog progressDialog = new ProgressDialog(Profilepro.this, R.style.MyDialog);
//        progressDialog.setTitle("Client List");
//        progressDialog.setMessage("Loading..");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
////        progressDialog.show();
//        super.onBackPressed();
//    }

    protected void onStop(){
        progressDialog.dismiss();
        super.onStop();
    }

    protected void onDestroy(){
        progressDialog.dismiss();
        super.onDestroy();
    }


}
