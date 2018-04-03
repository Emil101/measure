package com.spotpin.app.galleryimagepicker;

import android.app.Activity;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * Created by osagieomon on 11/8/17.
 */

public class GalleryImagePicker {

    public static final int REQUEST_GALLERY_IMAGE = 70;
    public static final String RESULT_FILE_PATH = "result_file_path";

    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;

    private Activity mActivity;
    private Fragment mFragment;
    private android.support.v4.app.Fragment mSupportFragment;

    private Class<? extends GalleryActivity> mGalleryActivityClass = GalleryActivity.class;

    private Intent mGalleryIntent;
    private Bundle mGalleryOptionsBundle;

    public static GalleryImagePicker of() {
        return new GalleryImagePicker();
    }

    private GalleryImagePicker() {
        mGalleryIntent = new Intent();
        mGalleryOptionsBundle = new Bundle();
    }

    /**
     * Specifies activity, which will be used to
     * start file picker
     */
    public GalleryImagePicker withActivity(Activity activity) {
        if (mSupportFragment != null || mFragment != null) {
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }

        mActivity = activity;
        return this;
    }

    /**
     * Specifies fragment, which will be used to
     * start file picker
     */
    public GalleryImagePicker withFragment(Fragment fragment) {
        if (mSupportFragment != null || mActivity != null) {
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }

        mFragment = fragment;
        return this;
    }

    /**
     * Specifies support fragment which will be used to
     * start file picker
     */
    public GalleryImagePicker withSupportFragment(android.support.v4.app.Fragment fragment) {
        if (mActivity != null || mFragment != null) {
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }

        mSupportFragment = fragment;
        return this;
    }

    public GalleryImagePicker withOptions(@NonNull Options options) {
        mGalleryOptionsBundle.putAll(options.getOptionBundle());
        return this;
    }

    /**
     * @return Intent that can be used to start Gallery Image Picker
     */
    public Intent getIntent() {

        Activity activity;
        if (mActivity != null) {
            activity = mActivity;
        } else if (mFragment != null) {
            activity = mFragment.getActivity();
        } else {
            activity = mSupportFragment.getActivity();
        }

        mGalleryIntent.setClass(activity, mGalleryActivityClass);
        mGalleryIntent.putExtras(mGalleryOptionsBundle);
        return mGalleryIntent;
    }

    /**
     * Open Galley Image Picker activity.
     * You should set Activity or Fragment before calling this method
     *
     * @see GalleryImagePicker#withActivity(Activity)
     * @see GalleryImagePicker#withFragment(Fragment)
     * @see GalleryImagePicker#withSupportFragment(android.support.v4.app.Fragment)
     */
    public void start() {
        if (mActivity == null && mFragment == null && mSupportFragment == null) {
            throw new RuntimeException("You must pass Activity/Fragment by calling withActivity/withFragment/withSupportFragment method");
        }

        Intent intent = getIntent();

        if (mActivity != null) {
            mActivity.startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
        } else if (mFragment != null) {
            mFragment.startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
        } else {
            mSupportFragment.startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
        }
    }

    // Option Class-------------------------------------------------------------------------------------
    public static class Options {

        public static final String EXTRA_TOOL_BAR_COLOR = EXTRA_PREFIX + ".ToolbarColor";
        public static final String EXTRA_STATUS_BAR_COLOR = EXTRA_PREFIX + ".StatusBarColor";
        public static final String EXTRA_WIDGET_COLOR_TOOLBAR = EXTRA_PREFIX + ".ToolbarWidgetColor";

        private final Bundle mOptionBundle;

        public Options() {
            mOptionBundle = new Bundle();
        }

        @NonNull
        public Bundle getOptionBundle() {
            return mOptionBundle;
        }

        /**
         * @param color - desired resolved color of the toolbar
         */
        public void setToolbarColor(@ColorInt int color) {
            mOptionBundle.putInt(EXTRA_TOOL_BAR_COLOR, color);
        }

        /**
         * @param color - desired resolved color of the statusbar
         */
        public void setStatusBarColor(@ColorInt int color) {
            mOptionBundle.putInt(EXTRA_STATUS_BAR_COLOR, color);
        }

        /**
         * @param color - desired resolved color of Toolbar text and buttons (default is darker orange)
         */
        public void setToolbarWidgetColor(@ColorInt int color) {
            mOptionBundle.putInt(EXTRA_WIDGET_COLOR_TOOLBAR, color);
        }
    }
}
