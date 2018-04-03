package com.spotpin.app.galleryimagepicker.util;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Function {

    public static String getCount(Context c, String album_name) {
        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

        Cursor cursorExternal = c.getContentResolver().query(uriExternal, projection,
                "bucket_display_name = \""+album_name+"\"", null, null);

        Cursor cursorInternal = c.getContentResolver().query(uriInternal, projection,
                "bucket_display_name = \""+album_name+"\"", null, null);

        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});


        return cursor.getCount()+" Photos";
    }

    public static String converToTime(String timestamp) {
        long datetime = Long.parseLong(timestamp);
        Date date = new Date(datetime);
        DateFormat formatter = new SimpleDateFormat("dd/MM HH:mm", Locale.US);
        return formatter.format(date);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

}