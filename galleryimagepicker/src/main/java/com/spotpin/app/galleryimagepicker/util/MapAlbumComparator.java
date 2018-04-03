package com.spotpin.app.galleryimagepicker.util;


import com.spotpin.app.galleryimagepicker.model.GalleryAlbum;

import java.util.Comparator;

/**
 * Created by osagieomon on 11/7/17.
 */

public class MapAlbumComparator implements Comparator<GalleryAlbum> {

    private final String order;

    public MapAlbumComparator(String order) {
        this.order = order;
    }


    @Override
    public int compare(GalleryAlbum galleryAlbum, GalleryAlbum t1) {
        String firstValue = galleryAlbum.getTimestamp();
        String secondValue = t1.getTimestamp();

        if(this.order.toLowerCase().contentEquals("asc")) {

            return firstValue.compareTo(secondValue);
        } else {

            return secondValue.compareTo(firstValue);
        }
    }
}