package com.gcs.xyzreader.data;

import android.net.Uri;

public class ItemContract {

    public static final String CONTENT_AUTHORITY = "com.gcs.xyzreader";
    public static final Uri BASE_URI = Uri.parse("content://com.gcs.xyzreader");

    interface ItemsColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String ID = "id";
        /** Type: TEXT NOT NULL */
        String TITLE = "title";
        /** Type: TEXT NOT NULL */
        String AUTHOR = "author";
        /** Type: TEXT NOT NULL */
        String BODY = "body";
        /** Type: TEXT NOT NULL */
        String THUMB_URL = "thumb";
        /** Type: TEXT NOT NULL */
        String PHOTO_URL = "photo";
        /** Type: INTEGER NOT NULL DEFAULT 0 */
        String PUBLISHED_DATE = "published_date";
    }

    public static class Items implements ItemsColumns {

        public static final String DEFAULT_SORT = PUBLISHED_DATE + " DESC";

        /** Matches: /items/ */
        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath("items").build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildItemUri(long id) {
            return BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }

}
