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
