package com.spotpin.app.galleryimagepicker.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.spotpin.app.galleryimagepicker.R;
import com.spotpin.app.galleryimagepicker.model.GalleryPhoto;

import java.io.File;
import java.util.List;

/**
 * Created by osagieomon on 11/7/17.
 */

public class GalleryPhotoAdapter extends BaseAdapter {

    private Context mContext;
    private List<GalleryPhoto> data;
    private OnGalleryPhotoClickListener onGalleryPhotoClickListener;

    public GalleryPhotoAdapter(Context mContext, List<GalleryPhoto> data,
                               OnGalleryPhotoClickListener onGalleryPhotoClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.onGalleryPhotoClickListener = onGalleryPhotoClickListener;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GalleryPhotoHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.gallery_photo_item, parent, false);

            holder = new GalleryPhotoHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (GalleryPhotoHolder) convertView.getTag();
        }

        holder.galleryPhoto.setId(position);

        GalleryPhoto galleryPhoto = data.get(position);

        try {

            Glide.with(mContext)
                    .load(new File(galleryPhoto.getPath()))
                    .into(holder.galleryPhoto);


        } catch (Exception ignored) {}

        return convertView;
    }

    class GalleryPhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView galleryPhoto;

        GalleryPhotoHolder(View itemView) {
            super(itemView);

            galleryPhoto = (ImageView) itemView.findViewById(R.id.gallery_photo);

            galleryPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            GalleryPhoto item = data.get(galleryPhoto.getId());
            onGalleryPhotoClickListener.onGalleryPhotoClicked(item.getPath());
        }
    }

    public interface OnGalleryPhotoClickListener {
        void onGalleryPhotoClicked(String path);
    }
}
