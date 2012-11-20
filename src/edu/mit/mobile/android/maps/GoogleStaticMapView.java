package edu.mit.mobile.android.maps;

/*
 * Copyright (C) 2012 MIT Mobile Experience Lab
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * <p>
 * A view that will generate a URL which can be used to display a map within it. This will not
 * display the map image out of the box; the display of the map is intentionally separated from the
 * loading of the image, so as to allow hosting fragments / activities to manage the network load
 * lifecycle themselves.
 * </p>
 *
 * <p>
 * A recommended solution for loading the map image is our own <a
 * href="https://github.com/mitmel/Android-Image-Cache">Android Image Cache</a>.
 * </p>
 *
 * @author <a href="mailto:spomeroy@mit.edu">Steve Pomeroy</a>
 *
 */
public class GoogleStaticMapView extends ImageView {

    private static final String TAG = GoogleStaticMapView.class.getSimpleName();
    private GoogleStaticMaps mStaticMapUtil;
    // XXX move the below to an xml attribute
    private String mMarker = "size:mid|color:red";
    private boolean mSensor;
    private float mLongitude;
    private float mLatitude;

    private boolean mHasReceivedSet = false;

    private OnMapUpdateListener mMapUpdateListener;
    private int mMapHeight;
    private int mMapWidth;

    public GoogleStaticMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GoogleStaticMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoogleStaticMapView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        final Map<String, String> mapArgs = new HashMap<String, String>();

        // XXX move this to xml attributes
        mapArgs.put("zoom", "14");
        mapArgs.put("maptype", "terrain");

        mStaticMapUtil = new GoogleStaticMaps(context, mapArgs);
    }

    public void setMarker(String marker) {
        mMarker = marker;
    }

    public void setMap(float latitude, float longitude, boolean sensor) {
        mSensor = sensor;
        mLatitude = latitude;
        mLongitude = longitude;
        mHasReceivedSet = true;

        updateMap();
    }

    public void clearMap() {
        mHasReceivedSet = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateMap();
        }
    }

    public void setOnMapUpdateListener(OnMapUpdateListener l) {
        mMapUpdateListener = l;
    }

    public int getMapWidth() {
        return mMapWidth;
    }

    public int getMapHeight() {
        return mMapHeight;
    }

    private void updateMap() {
        if (!mHasReceivedSet) {
            return;
        }

        final int mapWidth = getWidth() - (getPaddingRight() + getPaddingLeft());
        final int mapHeight = getHeight() - (getPaddingTop() + getPaddingBottom());

        if (mapWidth <= 0 || mapHeight <= 0) {
            Log.e(TAG, "mapWidth or mapHeight were <=0. Not updating.");
            return;
        }

        mMapWidth = mapWidth;
        mMapHeight = mapHeight;

        final Uri staticMap = mStaticMapUtil.getMap(mLatitude, mLongitude, mapWidth, mapHeight,
                mSensor, mMarker);

        if (mMapUpdateListener != null) {
            mMapUpdateListener.onMapUpdate(this, staticMap);
        }
    }
}
