package com.spotpin.app.galleryimagepicker;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spotpin.app.galleryimagepicker.adapter.GalleryPhotoAdapter;
import com.spotpin.app.galleryimagepicker.custom.GalleryPhotoClickListener;
import com.spotpin.app.galleryimagepicker.model.GalleryPhoto;
import com.spotpin.app.galleryimagepicker.util.AppTags;
import com.spotpin.app.galleryimagepicker.util.Function;
import com.spotpin.app.galleryimagepicker.util.MapComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by osagieomon on 11/8/17.
 */

public class GalleryAlbumFragment extends Fragment implements
        GalleryPhotoAdapter.OnGalleryPhotoClickListener {

    private static final String ARG_ALBUM_NAME = "arg_album_name";

    ProgressBar galleryPb;

    TextView galleryEv;

    GridView galleryGridView;

    Context mContext;
    GalleryPhotoAdapter mAdapter;
    Subscription subscription;

    String albumName;

    GalleryPhotoClickListener galleryPhotoClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GalleryPhotoClickListener) {
            galleryPhotoClickListener = (GalleryPhotoClickListener) context;
        } else {
            throw new IllegalStateException("Activity must implement GalleryPhotoClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        galleryPhotoClickListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static GalleryAlbumFragment getInstance(String albumName) {
        GalleryAlbumFragment fragment = new GalleryAlbumFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ALBUM_NAME, albumName);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_album, container, false);

        mContext = getActivity();

        galleryPb = (ProgressBar) view.findViewById(R.id.gallery_album_pb);

        galleryEv = (TextView) view.findViewById(R.id.gallery_album_ev);
        galleryGridView = (GridView) view.findViewById(R.id.gallery_album_gridView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels ;
        Resources resources = mContext.getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if(dp < 360) {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, mContext);
            galleryGridView.setColumnWidth(Math.round(px));
        }

        if (getArguments().getString(ARG_ALBUM_NAME) != null) {
            albumName = getArguments().getString(ARG_ALBUM_NAME);
        }

        doReadStorage();
    }

    private void doReadStorage() {
        showProgress();

        subscription = getPhotoFromAlbumObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GalleryPhoto>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(AppTags.APP_TAG, "onError: " + e.getMessage(), e);
                        showBlank();
                    }

                    @Override
                    public void onNext(List<GalleryPhoto> galleryPhotos) {
                        processResponse(galleryPhotos);
                    }
                });
    }

    private void processResponse(List<GalleryPhoto> galleryPhotos) {
        if(galleryPhotos.isEmpty()) {
            showEmpty();
            return;
        }

        mAdapter = new GalleryPhotoAdapter(mContext, galleryPhotos, this);
        galleryGridView.setAdapter(mAdapter);

        showContent();
    }

    private List<GalleryPhoto> getPhotoFromAlbumStorage() {
        List<GalleryPhoto> galleryPhotoList = new ArrayList<>();

        String path = null;
        String album = null;
        String timestamp = null;

        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

        Cursor cursorExternal = mContext.getContentResolver().query(uriExternal, projection,
                "bucket_display_name = \""+albumName+"\"", null, null);

        Cursor cursorInternal = mContext.getContentResolver().query(uriInternal, projection,
                "bucket_display_name = \""+albumName+"\"", null, null);

        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});

        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

            GalleryPhoto galleryPhoto = new GalleryPhoto(path, album, timestamp,
                    Function.converToTime(timestamp));
            galleryPhotoList.add(galleryPhoto);
        }

        cursor.close();

        Collections.sort(galleryPhotoList, new MapComparator("dsc"));

        return galleryPhotoList;
    }

    private Observable<List<GalleryPhoto>> getPhotoFromAlbumObservable() {
        return Observable.defer(new Func0<Observable<List<GalleryPhoto>>>() {
            @Override
            public Observable<List<GalleryPhoto>> call() {
                return Observable.just(getPhotoFromAlbumStorage());
            }
        });
    }

    private void showProgress() {
        galleryPb.setVisibility(View.VISIBLE);
        galleryEv.setVisibility(View.INVISIBLE);
        galleryGridView.setVisibility(View.INVISIBLE);
    }

    private void showContent() {
        galleryPb.setVisibility(View.INVISIBLE);
        galleryEv.setVisibility(View.INVISIBLE);
        galleryGridView.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        galleryPb.setVisibility(View.INVISIBLE);
        galleryEv.setVisibility(View.VISIBLE);
        galleryGridView.setVisibility(View.INVISIBLE);
    }

    private void showBlank() {
        galleryPb.setVisibility(View.INVISIBLE);
        galleryEv.setVisibility(View.INVISIBLE);
        galleryGridView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGalleryPhotoClicked(String path) {
        galleryPhotoClickListener.onClickedGalleyPhoto(path);
    }
}
