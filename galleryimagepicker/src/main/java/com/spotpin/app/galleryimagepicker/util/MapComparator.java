package com.spotpin.app.galleryimagepicker.util;


import com.spotpin.app.galleryimagepicker.model.GalleryPhoto;

import java.util.Comparator;

/**
 * Created by osagieomon on 11/7/17.
 */

public class MapComparator implements Comparator<GalleryPhoto> {

    private final String order;

    public MapComparator(String order) {
        this.order = order;
    }

    @Override
    public int compare(GalleryPhoto galleryPhoto, GalleryPhoto t1) {
        String firstValue = galleryPhoto.getTimestamp();
        String secondValue = t1.getTimestamp();

        if(this.order.toLowerCase().contentEquals("asc")) {

            return firstValue.compareTo(secondValue);
        } else {

            return secondValue.compareTo(firstValue);
        }
    }
}