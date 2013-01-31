package edu.mit.mobile.android.maps;

/*
 * Copyright (C) 2012-2013 MIT Mobile Experience Lab
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
import java.util.Map.Entry;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * <p>
 * Constructs URLs that load static images of a given location. To use, add your Google Static Maps
 * API key (this is <em>not</em> a normal Google Maps API key) to your Android manifest in the
 * &lt;application /&gt; section:
 * </p>
 *
 * <pre>
 *         &lt;meta-data
 *             android:name="edu.mit.mobile.android.maps.GOOGLE_STATIC_MAPS_API_KEY"
 *             android:value="AAeeSUeOKuhEuEu3eu43E2ue#jueEEuuU3hwm0" /&gt;
 *
 * </pre>
 *
 */
public class GoogleStaticMaps {

    public static final String PARAMETER_MARKERS = "markers";

    public static final String PARAMETER_CENTER = "center";

    public static final String PARAMETER_SCALE = "scale";

    public static final String PARAMETER_SENSOR = "sensor";

    public static final String PARAMETER_SIZE = "size";

    public static final String PARAMETER_KEY = "key";

    public static final String PARAMETER_ZOOM = "zoom";

    public static final String PARAMETER_MAPTYPE = "maptype";

    private final String mKey;

    private Map<String, String> mExtraArgs;

    private final int mScale;

    public static final String METADATA_KEY_API_KEY = "edu.mit.mobile.android.maps.GOOGLE_STATIC_MAPS_API_KEY";

    private final static Uri BASE_URL = Uri.parse("https://maps.googleapis.com/maps/api/staticmap");

    /**
     * Constructs URLs that load static images of a given location.
     *
     * @param context
     */
    public GoogleStaticMaps(Context context, Map<String, String> extraArgs) {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (final NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        final Bundle metadata = appInfo.metaData;
        mKey = metadata.getString(METADATA_KEY_API_KEY);

        mExtraArgs = extraArgs;

        final float density = context.getResources().getDisplayMetrics().scaledDensity;
        mScale = density > 1.5 ? 2 : 1;
    }

    public int getScale() {
        return mScale;
    }

    /**
     * Adds the given key/value query parameter to the query string.
     *
     * @param key
     * @param value
     */
    public void setExtraArg(String key, String value) {
        if (mExtraArgs == null) {
            mExtraArgs = new HashMap<String, String>();
        }
        mExtraArgs.put(key, value);
    }

    /**
     * Gets a map URL based on the parameters passed into the constructor and the parameters passed
     * in here. The "scale" parameter is automatically set based on the DPI
     * {@link DisplayMetrics#scaledDensity} from the provided context.
     *
     * @param latitude
     * @param longitude
     * @param width
     *            of the image, in pixels
     * @param height
     *            of the image, in pixels
     * @param sensor
     *            if the result came from a GPS sensor or similar, this should be set to true
     * @param marker
     *            marker style information or null for no marker. Position will be the same as the
     *            center.
     * @return
     */
    public Uri getMap(float latitude, float longitude, int width, int height, boolean sensor,
            String marker) {
        final Builder b = BASE_URL.buildUpon();
        b.appendQueryParameter(PARAMETER_KEY, mKey);
        b.appendQueryParameter(PARAMETER_SIZE, width / mScale + "x" + height / mScale);
        b.appendQueryParameter(PARAMETER_SENSOR, sensor ? "true" : "false");
        b.appendQueryParameter(PARAMETER_SCALE, Integer.toString(mScale));
        final String latlon = latitude + "," + longitude;

        b.appendQueryParameter(PARAMETER_CENTER, latlon);

        if (marker != null) {
            b.appendQueryParameter(PARAMETER_MARKERS, marker + "|" + latlon);
        }
        for (final Entry<String, String> entry : mExtraArgs.entrySet()) {
            b.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return b.build();
    }
}
