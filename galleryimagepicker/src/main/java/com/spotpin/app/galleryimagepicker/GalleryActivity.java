package com.spotpin.app.galleryimagepicker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.spotpin.app.galleryimagepicker.custom.GalleryPhotoClickListener;
import com.spotpin.app.galleryimagepicker.custom.GalleyAlbumClickListener;

public class GalleryActivity extends AppCompatActivity implements
        GalleyAlbumClickListener,
        GalleryPhotoClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 999;

    FrameLayout container;

    private int mToolbarColor;
    private int mStatusBarColor;
    private int mToolbarWidgetColor;

    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final Intent intent = getIntent();

        setupViews(intent);

        container = (FrameLayout) findViewById(R.id.gallery_container);

        result = checkPermission();

        if(result) {
            initFragment();
        } else {
            doPermissionErrorDialog();
        }
    }

    private void doPermissionErrorDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle("Permission required");
        alertBuilder.setMessage("Read Storage permission is required to access gallery.");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                setErrorAndFinish();
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.gallery_container, GalleryFragment.getInstance())
                .addToBackStack(null)
                .commit();
    }

    private void setupViews(@NonNull Intent intent) {
        mStatusBarColor = intent.getIntExtra(GalleryImagePicker.Options.EXTRA_STATUS_BAR_COLOR, 0);
        mToolbarColor = intent.getIntExtra(GalleryImagePicker.Options.EXTRA_TOOL_BAR_COLOR, 0);
        mToolbarWidgetColor = intent.getIntExtra(GalleryImagePicker.Options.EXTRA_WIDGET_COLOR_TOOLBAR, 0);
        setUpAppBar();
    }

    private void setUpAppBar() {
        if(!(mStatusBarColor == 0)) {
            setStatusBarColor(mStatusBarColor);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set all of the Toolbar coloring
        if(!(mToolbarColor == 0)) {
            toolbar.setBackgroundColor(mToolbarColor);
        }

        if(!(mToolbarWidgetColor == 0)) {
            toolbar.setTitleTextColor(mToolbarWidgetColor);
        }

        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Sets status-bar color for L devices.
     *
     * @param color - status-bar color
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    private void goToGalleryPhotoFragment(String albumName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.gallery_container, GalleryAlbumFragment.getInstance(albumName))
                .addToBackStack(null)
                .commit();
    }

    private void updateTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() == 1) {
            setErrorAndFinish();
        } else {
            fm.popBackStack();
            updateTitle(getString(R.string.title_activity_gallery));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if(currentAPIVersion >= android.os.Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Read Storage permission is required to access gallery");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(GalleryActivity.this,
                                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE },
                            MY_PERMISSIONS_REQUEST_READ_STORAGE);
                }

                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                    initFragment();
                } else {
                    setErrorAndFinish();
                }
                break;
        }
    }

    @Override
    public void onClickedGalleyAlbum(String albumName) {
        updateTitle(albumName);
        goToGalleryPhotoFragment(albumName);
    }

    @Override
    public void onClickedGalleyPhoto(String photoPath) {
        setResultAndFinish(photoPath);
    }

    private void setErrorAndFinish() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void setResultAndFinish(String filePath) {
        Intent data = new Intent();
        data.putExtra(GalleryImagePicker.RESULT_FILE_PATH, filePath);
        setResult(RESULT_OK, data);
        finish();
    }
}