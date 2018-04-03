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

import com.spotpin.app.galleryimagepicker.adapter.GalleryAlbumAdapter;
import com.spotpin.app.galleryimagepicker.custom.GalleyAlbumClickListener;
import com.spotpin.app.galleryimagepicker.model.GalleryAlbum;
import com.spotpin.app.galleryimagepicker.util.AppTags;
import com.spotpin.app.galleryimagepicker.util.Function;
import com.spotpin.app.galleryimagepicker.util.MapAlbumComparator;

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

public class GalleryFragment extends Fragment implements
        GalleryAlbumAdapter.OnAlbumClickListener {

    ProgressBar galleryPb;

    TextView galleryEv;

    GridView galleryGridView;

    GalleryAlbumAdapter mAdapter;
    Subscription subscription;

    Context mContext;

    GalleyAlbumClickListener galleyAlbumClickListener;
    List<GalleryAlbum> galleryAlbumList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GalleyAlbumClickListener) {
            galleyAlbumClickListener = (GalleyAlbumClickListener) context;
        } else {
            throw new IllegalStateException("Activity must implement GalleyAlbumClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        galleyAlbumClickListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static GalleryFragment getInstance() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mContext = getActivity();
        galleryPb = (ProgressBar) view.findViewById(R.id.gallery_pb);
        galleryEv = (TextView) view.findViewById(R.id.gallery_ev);
        galleryGridView = (GridView) view.findViewById(R.id.galleryGridView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels ;
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if(dp < 360) {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, mContext);
            galleryGridView.setColumnWidth(Math.round(px));
        }

        if (galleryAlbumList != null) {

            processResponse(galleryAlbumList);
            return;
        }

        doReadStorage();
    }

    private void doReadStorage() {
        showProgress();

        subscription = getAlbumListObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GalleryAlbum>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(AppTags.APP_TAG, "onError: " + e.getMessage(), e);
                        showBlank();
                    }

                    @Override
                    public void onNext(List<GalleryAlbum> galleryAlbums) {
                        processResponse(galleryAlbums);
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

    private void processResponse(List<GalleryAlbum> galleryAlbums) {
        if(galleryAlbums.isEmpty()) {
            showEmpty();
            return;
        }

        galleryAlbumList = galleryAlbums;
        mAdapter = new GalleryAlbumAdapter(mContext, galleryAlbums, this);
        galleryGridView.setAdapter(mAdapter);

        showContent();
    }

    private List<GalleryAlbum> getAlbumFromStorage() {
        List<GalleryAlbum> galleryAlbumList = new ArrayList<>();

        String path;
        String album;
        String timestamp;
        String countPhoto;

        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;


        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

        Cursor cursorExternal = mContext.getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);

        Cursor cursorInternal = mContext.getContentResolver().query(uriInternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);

        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});

        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
            countPhoto = Function.getCount(mContext, album);

            GalleryAlbum galleryAlbum = new GalleryAlbum(album, path, timestamp,
                    Function.converToTime(timestamp), countPhoto);
            galleryAlbumList.add(galleryAlbum);
        }
        cursor.close();

        //Collections.sort(galleryAlbumList, new MapAlbumComparator("dsc"));

        return galleryAlbumList;
    }

    private Observable<List<GalleryAlbum>> getAlbumListObservable() {
        return Observable.defer(new Func0<Observable<List<GalleryAlbum>>>() {
            @Override
            public Observable<List<GalleryAlbum>> call() {
                return Observable.just(getAlbumFromStorage());
            }
        });
    }

    @Override
    public void onAlbumClicked(String albumName) {
        galleyAlbumClickListener.onClickedGalleyAlbum(albumName);
    }
}
