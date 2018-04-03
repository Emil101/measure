package com.spotpin.app.galleryimagepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by osagieomon on 11/7/17.
 */

public class GalleryAlbum implements Parcelable{

    private String albumName;
    private String path;
    private String timestamp;
    private String time;
    private String count;

    public GalleryAlbum(String albumName, String path, String timestamp, String time, String count) {
        this.albumName = albumName;
        this.path = path;
        this.timestamp = timestamp;
        this.time = time;
        this.count = count;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getPath() {
        return path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTime() {
        return time;
    }

    public String getCount() {
        return count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.albumName);
        dest.writeString(this.path);
        dest.writeString(this.timestamp);
        dest.writeString(this.time);
        dest.writeString(this.count);
    }

    private GalleryAlbum(Parcel in) {
        this.albumName = in.readString();
        this.path = in.readString();
        this.timestamp = in.readString();
        this.time = in.readString();
        this.count = in.readString();
    }

    public static final Creator<GalleryAlbum> CREATOR = new Creator<GalleryAlbum>() {
        @Override
        public GalleryAlbum createFromParcel(Parcel source) {
            return new GalleryAlbum(source);
        }

        @Override
        public GalleryAlbum[] newArray(int size) {
            return new GalleryAlbum[size];
        }
    };
}
