package com.xorek.measurementbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MEA on 6/15/2017.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tailorDB.db" ;
    private static final String TABLE_DETAILS = "details";

    public static final String COLUMN_ID ="_ID";
    public static final String COLUMN_CONTACTNAME = "contactname";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DILIVERYDATE = "diliveryDate";
    public static final String COLUMN_NECKCIRC = "neckCirc";
    public static final String COLUMN_BURSTCIRC = "BustCirc";
    public static final String COLUMN_WAISTCIRC = "waistCirc";
    public static final String COLUMN_HIPCIRC = "hipCirc";
    public static final String COLUMN_ARMHOLECIRC = "armholeCirc";
    public static final String COLUMN_BUSTHEIGHT = "bustHeight";
    public static final String COLUMN_FRONTWAISTLENGTH = "frontWaistLength";
    public static final String COLUMN_HIPLENGTH = "hipLength";
    public static final String COLUMN_ELBOWLENGTH = "elbowLength";
    public static final String COLUMN_SLEEVELENGTH = "sleeveLength";
    public static final String COLUMN_BACKWAISTLEN = "backWaistLenth";
    public static final String COLUMN_SHOULDERLEN = "shoulderLenth";
    public static final String COLUMN_BACKWIDTH = "backWidth";
    public static final String COLUMN_KNEELEN = "kneeLength";
    public static final String COLUMN_BREASTDISTANCE= "breastDistance";
    public static final String COLUMN_UNDERBUSTCIRC = "underBustCirc";
    public static final String COLUMN_UNDERBUSTHEIGHT = "underBustHeight";
    public static final String COLUMN_RAISELENGTH = "riseLength";
    public static final String COLUMN_TROUSERLEN = "trouserLength";
    public static final String COLUMN_FLOORLEN = "floorLength";
    public static final String COLUMN_STRAPLESSNECKLINECIRC = "straplessNecklineCirc";
    public static final String COLUMN_NOTES ="notes";
    public static final String COLUMN_IMAGES = "image";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_STYLE = "style";
    public static final String COLUMN_IMAGEPATH = "imagepath";



    public DbHandler(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CONTACTNAME + " TEXT, " + COLUMN_PHONENUMBER + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_DILIVERYDATE + " TEXT, "+ COLUMN_NECKCIRC + " TEXT, " + COLUMN_BURSTCIRC +" TEXT, " + COLUMN_WAISTCIRC + " TEXT, " + COLUMN_HIPCIRC + " TEXT, "+ COLUMN_ARMHOLECIRC +" TEXT, "+ COLUMN_BUSTHEIGHT + " TEXT, " + COLUMN_FRONTWAISTLENGTH + " TEXT," + COLUMN_HIPLENGTH + " TEXT, " + COLUMN_ELBOWLENGTH + " TEXT, " + COLUMN_SLEEVELENGTH + " TEXT, " + COLUMN_BACKWAISTLEN + " TEXT, " + COLUMN_SHOULDERLEN +  " TEXT, "+ COLUMN_BACKWIDTH + " TEXT, "+ COLUMN_KNEELEN + " TEXT, " + COLUMN_BREASTDISTANCE + " TEXT, " + COLUMN_UNDERBUSTCIRC + " TEXT, " + COLUMN_UNDERBUSTHEIGHT + " TEXT, " + COLUMN_RAISELENGTH + " TEXT, " + COLUMN_TROUSERLEN + " TEXT, " + COLUMN_FLOORLEN + " TEXT, " + COLUMN_STRAPLESSNECKLINECIRC + " TEXT, " + COLUMN_NOTES + " TEXT, " + COLUMN_IMAGES + " TEXT, " + COLUMN_SEX + " TEXT, " + COLUMN_STYLE + " TEXT, " + COLUMN_IMAGEPATH + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        onCreate(db);
    }

    public void addContact(Client Client){

        ContentValues values = new ContentValues();

        values.put(COLUMN_CONTACTNAME, Client.getName());
        values.put(COLUMN_PHONENUMBER, Client.getPhoneNumber());
        values.put(COLUMN_DATE, Client.getDate());
        values.put(COLUMN_DILIVERYDATE, Client.getDiliveryDate());
        values.put(COLUMN_NECKCIRC, Client.getNeckCirc());
        values.put(COLUMN_BURSTCIRC, Client.getBustCirc());
        values.put(COLUMN_WAISTCIRC, Client.getWaistCirc());
        values.put(COLUMN_HIPCIRC, Client.getHipCirc());
        values.put(COLUMN_ARMHOLECIRC, Client.getArmholeCirc());
        values.put(COLUMN_BUSTHEIGHT, Client.getBustHeight());
        values.put(COLUMN_FRONTWAISTLENGTH, Client.getFrontWaistLenth());
        values.put(COLUMN_HIPLENGTH, Client.getHipLength());
        values.put(COLUMN_ELBOWLENGTH, Client.getElbowLenth());
        values.put(COLUMN_SLEEVELENGTH, Client.getSleeveLength());
        values.put(COLUMN_BACKWAISTLEN, Client.getBackWaistLenth());
        values.put(COLUMN_SHOULDERLEN, Client.getShoulderLenth());
        values.put(COLUMN_BACKWIDTH, Client.getBackWidth());
        values.put(COLUMN_KNEELEN, Client.getKneeLength());
        values.put(COLUMN_BREASTDISTANCE, Client.getBreastDistance());
        values.put(COLUMN_UNDERBUSTCIRC, Client.getUnderBustCirc());
        values.put(COLUMN_UNDERBUSTHEIGHT, Client.getUnderBustHeight());
        values.put(COLUMN_RAISELENGTH, Client.getRiseLength());
        values.put(COLUMN_TROUSERLEN, Client.getTrouserLength());
        values.put(COLUMN_FLOORLEN, Client.getFloorLength());
        values.put(COLUMN_STRAPLESSNECKLINECIRC, Client.getStraplessNecklineCirc⁠⁠⁠⁠());
        values.put(COLUMN_NOTES, Client.getNotes());
        values.put(COLUMN_IMAGES, Client.getImage());
        values.put(COLUMN_SEX, Client.getSex());
        values.put(COLUMN_STYLE, Client.getStyle());
        values.put(COLUMN_IMAGEPATH, Client.getImagepath());
Log.d("Image path", Client.getImagepath());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_DETAILS, null, values);
        db.close();

    }

    public Cursor getRow(DbHandler db, long rowId){

        String where = COLUMN_ID + "=" + rowId;
        SQLiteDatabase sq = db.getReadableDatabase();
        String [] columns = {COLUMN_CONTACTNAME, COLUMN_PHONENUMBER, COLUMN_DATE, COLUMN_DILIVERYDATE, COLUMN_NECKCIRC, COLUMN_BURSTCIRC, COLUMN_WAISTCIRC, COLUMN_HIPCIRC, COLUMN_ARMHOLECIRC, COLUMN_BUSTHEIGHT, COLUMN_FRONTWAISTLENGTH, COLUMN_HIPLENGTH, COLUMN_ELBOWLENGTH, COLUMN_SLEEVELENGTH, COLUMN_BACKWAISTLEN, COLUMN_SHOULDERLEN, COLUMN_BACKWIDTH, COLUMN_KNEELEN, COLUMN_BREASTDISTANCE, COLUMN_UNDERBUSTCIRC, COLUMN_UNDERBUSTHEIGHT, COLUMN_RAISELENGTH, COLUMN_TROUSERLEN, COLUMN_FLOORLEN, COLUMN_STRAPLESSNECKLINECIRC, COLUMN_NOTES, COLUMN_IMAGES, COLUMN_SEX, COLUMN_STYLE, COLUMN_IMAGEPATH};
        Cursor cr = sq.query(TABLE_DETAILS, columns, where, null, null, null, null);

        return cr;

    }

    public Cursor getAll(DbHandler db){
        String where = null;
        SQLiteDatabase sq = db.getReadableDatabase();
        String [] columns = {COLUMN_ID, COLUMN_CONTACTNAME, COLUMN_DILIVERYDATE,  COLUMN_IMAGES, COLUMN_IMAGEPATH};
        Cursor cr = sq.query(TABLE_DETAILS, columns, where, null, null, null, COLUMN_ID+" DESC");

        return cr;
    }

    public void updateDetail(Long row, Client Client){

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, (long)Client.getId());
        values.put(COLUMN_CONTACTNAME, Client.getName());
        values.put(COLUMN_PHONENUMBER, Client.getPhoneNumber());
        values.put(COLUMN_DATE, Client.getDate());
        values.put(COLUMN_DILIVERYDATE, Client.getDiliveryDate());
        values.put(COLUMN_NECKCIRC, Client.getNeckCirc());
        values.put(COLUMN_BURSTCIRC, Client.getBustCirc());
        values.put(COLUMN_WAISTCIRC, Client.getWaistCirc());
        values.put(COLUMN_HIPCIRC, Client.getHipCirc());
        values.put(COLUMN_ARMHOLECIRC, Client.getArmholeCirc());
        values.put(COLUMN_BUSTHEIGHT, Client.getBustHeight());
        values.put(COLUMN_FRONTWAISTLENGTH, Client.getFrontWaistLenth());
        values.put(COLUMN_HIPLENGTH, Client.getHipLength());
        values.put(COLUMN_ELBOWLENGTH, Client.getElbowLenth());
        values.put(COLUMN_SLEEVELENGTH, Client.getSleeveLength());
        values.put(COLUMN_BACKWAISTLEN, Client.getBackWaistLenth());
        values.put(COLUMN_SHOULDERLEN, Client.getShoulderLenth());
        values.put(COLUMN_BACKWIDTH, Client.getBackWidth());
        values.put(COLUMN_KNEELEN, Client.getKneeLength());
        values.put(COLUMN_BREASTDISTANCE, Client.getBreastDistance());
        values.put(COLUMN_UNDERBUSTCIRC, Client.getUnderBustCirc());
        values.put(COLUMN_UNDERBUSTHEIGHT, Client.getUnderBustHeight());
        values.put(COLUMN_RAISELENGTH, Client.getRiseLength());
        values.put(COLUMN_TROUSERLEN, Client.getTrouserLength());
        values.put(COLUMN_FLOORLEN, Client.getFloorLength());
        values.put(COLUMN_STRAPLESSNECKLINECIRC, Client.getStraplessNecklineCirc⁠⁠⁠⁠());
        values.put(COLUMN_NOTES, Client.getNotes());
        values.put(COLUMN_IMAGES, Client.getImage());
        values.put(COLUMN_SEX, Client.getSex());
        values.put(COLUMN_STYLE, Client.getStyle());
        values.put(COLUMN_IMAGEPATH, Client.getImagepath());
        Log.d("IMMMMMM", String.valueOf(Client.getImagepath()));


        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_ID + "=" + row;
        Log.d("row", String.valueOf(row));
        db.update(TABLE_DETAILS, values, where, null);





    }

    public Boolean deleteCon(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_ID +"="+id;
        return db.delete(TABLE_DETAILS, where, null) !=0;
    }

    public Client findContact(String contactname){
        String query = "Select * FROM " + TABLE_DETAILS +" WHERE " + COLUMN_CONTACTNAME + " =  \"" + contactname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Client contact = new Client();

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));

            contact.setPhoneNumber((cursor.getString(2)));
            cursor.close();
        }else{
            contact = null;
        }
        db.close();

        return contact;
    }
}
