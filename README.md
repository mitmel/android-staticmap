Android StaticMaps and other location widgets
=============================================

A simple library to easily add [Google static maps][] to your app.

Features
--------

* provides a UI widget to automatically determine map size and scale based which can be incorporated into an XML layout
* generates map URL and pulls the API key from the application's manifest
* also contains a LocationButton widget which can display a current/saved location and toggle between them interactively

Using
-----

To use, add this project as an Android library project and then add this to the
`<application />` section of your manifest:

    <meta-data
        android:name="edu.mit.mobile.android.maps.GOOGLE_STATIC_MAPS_API_KEY"
        android:value="AAeeSUeOKuhEuEu3eu43E2ue#jueEEuuU3hwm0" />

You can then either use the `GoogleStaticMapView` and register an
`OnMapUpdateListener` to get automatic map size updates, or you can directly
use the `GoogleStaticMap` class to generate the URL.

If you use the `GoogleStaticMapView` widget, we recommend using our
[ImageCache][] library to make it easier to asynchronously load / cache
rendered maps.

License
=======

MEL Android Static Map  
Copyright (C) 2012 [MIT Mobile Experience Lab][mel]

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License version 2.1 as published by the Free Software Foundation.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

[mel]: http://mobile.mit.edu/
[Google static maps]: https://developers.google.com/maps/documentation/staticmaps/index
[ImageCache]: https://github.com/mitmel/Android-Image-Cache/
