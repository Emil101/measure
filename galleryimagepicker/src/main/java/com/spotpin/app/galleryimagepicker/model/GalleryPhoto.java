package com.spotpin.app.galleryimagepicker.model;

/**
 * Created by osagieomon on 11/7/17.
 */

public class GalleryPhoto {

    private String path;
    private String album;
    private String timestamp;
    private String time;

    public GalleryPhoto(String path, String album, String timestamp, String time) {
        this.path = path;
        this.album = album;
        this.timestamp = timestamp;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public String getAlbum() {
        return album;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTime() {
        return time;
    }
}
