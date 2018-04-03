package com.xorek.measurementbook;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MEA on 7/20/2017.
 */

public class MeasureAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Client> clients;

    Context context;
    long id;

    private static String text = "";

    public MeasureAdapter(ArrayList<Client> clients,   Context context) {
        this.clients = clients;
        this.context = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.update_row, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v, context);





        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Uri fileuri = null;

//        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolveInfo : resInfoList) {
//            String packageName = resolveInfo.activityInfo.packageName;
//            grantUriPermission(packageName, fileuri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }


        if (!(clients.get(position).getImage().equals(""))) {

//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                fileuri = Uri.parse("file://" + clients.get(position).getImage());
//
//            } else {

            Log.d("Imagestring", clients.get(position).getImage());


            fileuri =Uri.parse(clients.get(position).getImage());

//            if((clients.get(position).getImagepath().equals(""))){
//
//            }else {

                text = clients.get(position).getImagepath();

//            }


//                fileuri = Uri.parse(getImageUrlWithAuthority(context, Uri.parse(clients.get(position).getImage())));






//            for (ResolveInfo resolveInfo : resolveInfos) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                Log.d("Pack", packageName);
//                context.grantUriPermission(packageName, fileuri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            }
//            }
        }else {

            fileuri = null;

        }

             id = clients.get(position).getId();


        if(clients.get(position).getImage().equals("")){
            holder.name.setText(clients.get(position).getName());
            holder.userName.setText(clients.get(position).getDiliveryDate());
            holder.id1.setText(String.valueOf(id));
            holder.id1.setVisibility(View.INVISIBLE);
        }else{
            holder.id1.setText(String.valueOf(id));
            holder.id1.setVisibility(View.INVISIBLE);
            holder.name.setText(clients.get(position).getName());
            holder.userName.setText(clients.get(position).getDiliveryDate());
////            BitmapFactory.Options options = new BitmapFactory.Options();
////
////            // downsizing image as it throws OutOfMemory Exception for larger
////            // images
////            options.inSampleSize = 8;
////
//            final Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.upload);
//            if ((clients.get(position).getImage().startsWith("content://com.google.android.apps.photos.contentprovider"))) {
////                try {
////
////                    holder.img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
////
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(clients.get(position).getImage()).centerCrop().into(holder.img);
//                } else {
//                    Picasso.with(context).load(clients.get(position).getImagepath()).resize(100, 100).centerCrop().into(holder.img);
//                }
//
//
//            }else if ((clients.get(position).getImage().startsWith("file:///"))) {
////                try {
////                    if (text.equals("")){
////                        String temp = clients.get(position).getImage().replace("file:///", "/");
////                        text = temp;
////                        Log.d("TEMP", text);
////                    }
////                    holder.img.setImageBitmap(rotateImageIfRequired(context, bitmap, fileuri));
////                    Picasso.with(context).load(clients.get(position).getImage()).resize(100, 100).centerCrop().into(holder.img);
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(clients.get(position).getImage()).centerCrop().into(holder.img);
//                } else {
//                    Picasso.with(context).load(clients.get(position).getImage()).resize(100, 100).centerCrop().into(holder.img);
//                }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//            }else{
////                try {
////
//
////                    holder.img.setImageBitmap(handleSamplingAndRotationBitmap(context, fileuri));
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                    Glide.with(context).load(clients.get(position).getImage()).centerCrop().into(holder.img);
//                } else {
//                    Picasso.with(context).load(clients.get(position).getImage()).resize(100, 100).centerCrop().into(holder.img);
//                }
//
//            }
//
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if((clients.get(position).getImage().startsWith("file:///"))){
                    Glide.with(context).load(clients.get(position).getImage()).centerCrop().into(holder.img);
                }else {
                    Glide.with(context).load(new File(clients.get(position).getImage())).centerCrop().into(holder.img);
                }
            } else {
                if((clients.get(position).getImage().startsWith("file:///"))){
                    Picasso.with(context).load(clients.get(position).getImage()).resize(150, 150).centerCrop().into(holder.img);
                }else {
                    Picasso.with(context).load(new File(clients.get(position).getImage())).resize(150, 150).centerCrop().into(holder.img);
                }
            }
        }



    }

    @Override
    public int getItemCount() {

        return  this.clients.size();
    }

    public void setFilter(ArrayList<Client> newlist){

        clients = new ArrayList<>();
        clients.addAll(newlist );
        notifyDataSetChanged();
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

//        InputStream input = context.getContentResolver().openInputStream(Uri.parse(text));
        InputStream input;
        if(text.equals("")){

            input = context.getContentResolver().openInputStream(selectedImage);

        }else {
            input = new FileInputStream(text);
            Log.d("TEXTT", text);
        }


        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
//            ei = new ExifInterface(text);
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

//                options = new BitmapFactory.Options();
//                options.inSampleSize = 5;
                if (text.equals("")) {
                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return rotateImage(bitmap, 180);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return rotateImage(bitmap, 180);
                }
            case ExifInterface.ORIENTATION_ROTATE_270:
//                options = new BitmapFactory.Options();
//                options.inSampleSize = 5;
                if (text.equals("")) {
                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                    return rotateImage(bitmap, 270);
                }else{
                    final Bitmap bitmap = BitmapFactory.decodeFile(text);
                    return rotateImage(bitmap, 270);
                }
            default:
//                options = new BitmapFactory.Options();
//                options.inSampleSize = 5;
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

//    public static String getImageUrlWithAuthority(Context context, Uri uri) {
//        InputStream is = null;
//        if (uri.getAuthority() != null) {
//            try {
//                is = context.getContentResolver().openInputStream(uri);
//                Bitmap bmp = BitmapFactory.decodeStream(is);
//                return writeToTempImageAndGetPathUri(context, bmp).toString();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }finally {
//                try {
//                    if(is != null)
//                        is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
//
//    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }



}

