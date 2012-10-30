package edu.mit.mobile.android.maps;

import android.net.Uri;

/**
 * Implement this to receive updates on when the map's size changes and needs to be updated.
 *
 * @author <a href="mailto:spomeroy@mit.edu">Steve Pomeroy</a>
 *
 */
public interface OnMapUpdateListener {

    /**
     * Implement this to set the image of the map. This can be as simple as using
     * {@link GoogleStaticMapView#setImageURI(Uri)} (which will lead to UI latency issues) or as
     * complex as loading an image from a cache. Note: the map image will not be set automatically;
     * you need to do that here.
     * 
     * @param view
     *            the map which was updated
     * @param mapUrl
     *            the new url of the map
     */
    public void onMapUpdate(GoogleStaticMapView view, Uri mapUrl);
}
