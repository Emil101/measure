package com.spotpin.app.galleryimagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spotpin.app.galleryimagepicker.R;
import com.spotpin.app.galleryimagepicker.model.GalleryAlbum;

import java.io.File;
import java.util.List;

/**
 * Created by osagieomon on 11/7/17.
 */

public class GalleryAlbumAdapter extends BaseAdapter {

    private Context mContext;
    private List<GalleryAlbum> data;
    private OnAlbumClickListener onAlbumClickListener;

    public GalleryAlbumAdapter(Context mContext, List<GalleryAlbum> data,
                               OnAlbumClickListener onAlbumClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.onAlbumClickListener = onAlbumClickListener;
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
        AlbumViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.gallery_album_item, parent, false);

            holder =  new AlbumViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (AlbumViewHolder) convertView.getTag();
        }

        holder.galleryImage.setId(position);
        holder.galleryCount.setId(position);
        holder.galleryTitle.setId(position);

        GalleryAlbum galleryAlbum = data.get(position);

        try {
            holder.galleryTitle.setText(galleryAlbum.getAlbumName());
            holder.galleryCount.setText(galleryAlbum.getCount());

            Glide.with(mContext)
                    .load(new File(galleryAlbum.getPath())) // Uri of the picture
                    .into(holder.galleryImage);


        } catch (Exception ignored) {}

        return convertView;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView galleryImage;
        TextView galleryCount;
        TextView galleryTitle;

        AlbumViewHolder(View itemView) {
            super(itemView);

            galleryImage = (ImageView) itemView.findViewById(R.id.gallery_image);
            galleryCount = (TextView) itemView.findViewById(R.id.gallery_count);
            galleryTitle = (TextView) itemView.findViewById(R.id.gallery_title);

            galleryImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            GalleryAlbum item = data.get(view.getId());
            onAlbumClickListener.onAlbumClicked(item.getAlbumName());
        }
    }

    public interface OnAlbumClickListener {
        void onAlbumClicked(String albumName);
    }
}