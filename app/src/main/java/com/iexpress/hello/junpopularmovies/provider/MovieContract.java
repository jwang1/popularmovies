package com.iexpress.hello.junpopularmovies.provider;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.iexpress.hello.junpopularmovies.MovieContentProvider;
import com.iexpress.hello.junpopularmovies.syncadapter.MovieSyncUtils;

/**
 * Field and table name contants for {@link MovieContentProvider}
 *
 * Created by jwang on 1/12/17.
 */

public class MovieContract {
  private MovieContract() {
  }

  public static final String PATH_POPULAR_MOVIES = "popular";
  public static final String PATH_TOP_RATED_MOVIES = "toprated";
  public static final String PATH_FAVORITE_MOVIES = "favorite";
  public static final String PATH_MOVIE_DETAIL = "detail";


  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieSyncUtils.CONTENT_AUTHORITY);


  public static class MovieRanking implements BaseColumns {
    /** MIME type for record */
    public static final String POPULAR_MOVIE_CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.moviesyncadapter.popular";
    public static final String TOP_RATED_MOVIE_CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.moviesyncadapter.toprated";

    public static final Uri POPULAR_MOVIE_CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();

    public static final Uri FAVORITE_TOP_RATED_MOVIES =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_RATED_MOVIES).build();

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
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.moviesyncadapter.favorites";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

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
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.moviesyncadapter.detail";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_DETAIL).build();

    public static final String TABLE_NAME = "movie_detail";

    public static final String COLUMN_NAME_TMDB_ID = "tmdb_id";

    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_POSTER = "poster";
    public static final String COLUMN_NAME_PUBLISH_DATE = "publish_date";
    public static final String COLUMN_NAME_RATING = "rating";
    public static final String COLUMN_NAME_SUMMARY = "summary";
    public static final String COLUMN_NAME_VIDEO = "video";
    public static final String COLUMN_NAME_REVIEW = "review";
    public static final String COLUMN_NAME_INFO = "info";   // holding original JSON

    public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
    public static final String COLUMN_NAME_LAST_UPDATE_DATE = "last_update_date";
  }

}
