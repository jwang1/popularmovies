package com.iexpress.hello.junpopularmovies.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.iexpress.hello.junpopularmovies.syncadapter.MovieSyncUtils;

/**
 * Field and table name contants for {@link MovieContentProvider}
 *
 * Created by jwang on 1/12/17.
 */

public class MovieContract {
  private MovieContract() {
  }

  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieSyncUtils.CONTENT_AUTHORITY);

  private static final String PATH_MOVIE_RANKING = "movie_ranking";
  private static final String PATH_FAVORITE_MOVIE = "favorite_movie";
  private static final String PATH_MOVIE_DETAIL = "movie_detail";

  public static class MovieRanking implements BaseColumns {
    /** MIME type for record */
    public static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.syncadapter.rankings";
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.syncadapter.ranking";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_RANKING).build();

    public static final String TABLE_NAME = "movie_ranking";

    public static final String COLUMN_NAME_RANK_ID = "rank_id";
    public static final String COLUMN_NAME_TYPE = "type"; // popular or top_rated, ie, the TMDB API types
    public static final String COLUMN_NAME_TMDB_ID = "tmdb_id";
    public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
    public static final String COLUMN_NAME_LAST_UPDATE_DATE = "last_update_date";
  }

  public static class FavoriteMovie implements BaseColumns {
    /** MIME type for record */
    public static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.syncadapter.favorites";
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.syncadapter.favorite";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_RANKING).build();

    public static final String TABLE_NAME = "favorite_movie";

    public static final String COLUMN_NAME_TMDB_ID = "tmdb_id";
    // entry here means 'favorite'; but 'false' value means was-favorite-before, but not right now
    public static final String COLUMN_NAME_CURRENT_FAVORITE = "current_favorite";

    public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
    public static final String COLUMN_NAME_LAST_UPDATE_DATE = "last_update_date";

  }

  public static class MovieDetail implements BaseColumns {
    /** MIME type for record */
    public static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.syncadapter.details";
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.syncadapter.detail";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_RANKING).build();

    public static final String TABLE_NAME = "movie_detail";

    public static final String COLUMN_NAME_TMDB_ID = "tmdb_id";

    public static final String COLUMN_NAME_INFO = "info";
    public static final String COLUMN_NAME_VIDEO = "video";
    public static final String COLUMN_NAME_REVIEW = "review";

    public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
    public static final String COLUMN_NAME_LAST_UPDATE_DATE = "last_update_date";
  }

}
