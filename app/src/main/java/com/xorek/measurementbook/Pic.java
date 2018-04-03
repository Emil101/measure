package com.xorek.measurementbook;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import butterknife.BindView;
import butterknife.ButterKnife;

public class Pic extends AppCompatActivity {

    SubsamplingScaleImageView img;

    long rowid;
    DbHandler dbHandler;
    Context context;
    static String text = "";


    Client client = new Client();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//
//        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            rowid = extras.getLong("id");

        }
        ButterKnife.bind(this);
        context = getApplicationContext();
        img = findViewById(R.id.imgv);
        new add().execute();
    }

    public void retrieveRow() {
        dbHandler = new DbHandler(context);
        Cursor cr = dbHandler.getRow(dbHandler, rowid);

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
//            if (!(client.getImage() == null)) {
//
//                Uri fileuri = Uri.parse("file://" + client.getImage());
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                // downsizing image as it throws OutOfMemory Exception for larger
//                // images
//                options.inSampleSize = 4;
//                Log.d("URI", String.valueOf(fileuri));
//
//                final Bitmap bitmap = BitmapFactory.decodeFile(fileuri.getPath(),
//                        options);
//
//                try {
//                    img.setImage(ImageSource.bitmap(handleSamplingAndRotationBitmap(context, fileuri)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
////            img.setImage(ImageSource.resource(R.drawable.profile));
//
//            }
//        }else {

                            }
//                try {
//                    img.setImage(ImageSource.bitmap(handleSamplingAndRotationBitmap(context, fileuri)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }





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

            progressBar = new ProgressBar(Pic.this);
//            progressBar.getProgressDrawable().setColorFilter(
//                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
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
                text = client.getImage();


                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.upload);

                if ((client.getImage().startsWith("content://com.google.android.apps.photos.contentprovider"))){
                    try {


                        img.setImage(ImageSource.bitmap(rotateImageIfRequired(context, bitmap, fileuri)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ((client.getImage().startsWith("file:///"))) {
                    try {

////                        if (text.equals("")){
////                            String temp = client.getImage().replace("file:///", "/");
////                            text = temp;
////                            Log.d("TEMP", text);
////                        }
                        text ="";
                        img.setImage(ImageSource.bitmap(rotateImageIfRequired(context, bitmap, fileuri)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if((client.getImage().startsWith("content://media/external/images/"))) {
                    try {
                        img.setImage(ImageSource.bitmap(rotateImageIfRequired(context, bitmap, fileuri)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        img.setImage(ImageSource.bitmap(rotateImageIfRequired(context, bitmap, fileuri)));
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                Picasso.with(Pic.this)
//                        .load(new File(client.getImage())).resize(300, 300).centerCrop()
//                        .into((Target) img);

//                Glide.with(context).load(new File(text)).centerCrop().into(img);

//                try {
//                    img.setImage(ImageSource.bitmap(rotateImageIfRequired(context, bitmap, fileuri)));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }

        }
    }


//    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
//            throws IOException {
//        int MAX_HEIGHT = 2024;
//        int MAX_WIDTH = 2024;
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
//        BitmapFactory.decodeStream(imageStream, null, options);
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


//    @Override
//    public void onBackPressed() {
//
//        ProgressDialog progressDialog = new ProgressDialog(Pic.this, R.style.MyDialog);
//        progressDialog.setTitle("Client Details");
//        progressDialog.setMessage("Loading..");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        super.onBackPressed();
//    }

}
