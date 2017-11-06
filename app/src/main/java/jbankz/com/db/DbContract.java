package jbankz.com.db;

import android.provider.BaseColumns;

/**
 * Created by King Jaycee on 29/10/2017.
 */

public class DbContract {

    public static final class DbEntry implements BaseColumns {
        public static final String NEWS_TABLE_NAME = "news";
        public static final String _ID = "id";
        public static final String NEWS_AUTHOR = "author";
        public static final String NEWS_DESCRIPTION = "description";
        public static final String NEWS_URL = "url";
        public static final String NEWS_IMAGE_URL = "image_url";
        public static final String NEWS_PUBLISHED_AT = "published_at";

    }
}
