package com.iexpress.hello.junpopularmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.iexpress.hello.junpopularmovies.provider.MovieContract;
import com.iexpress.hello.junpopularmovies.provider.MovieDatabase;
import com.iexpress.hello.junpopularmovies.syncadapter.MovieSyncUtils;

/**
 * SyncAdapter to replace the preliminary AsyncTask
 *
 * 1. from the reivew, showed the following:
 *
 * Asynctask is a good choice but in my opinion a authenticator is a better solution.
 * Here (http://stackoverflow.com/questions/33087153/confused-between-syncadapter-services-loaders-providers-and-asynctask)
 * you can learn something about these solutions.
 *
 * Other useful links: about the reasons (http://android-restful-pattern.blogspot.it/)
 * for keeping the local SQLite database
 * (if you'll decide to implement this pattern) in sync with the REST API
 * and a tutorial (http://www.coderzheaven.com/2015/07/04/sync-adapter-android-simple-example/).
 *
 * Created by jwang on 12/24/16.
 */

public class MovieContentProvider extends ContentProvider {
  MovieDatabase databaseHelper;

  /**
   * @return true if the provider was successfully loaded, false otherwise
   */
  @Override
  public boolean onCreate() {
    databaseHelper = new MovieDatabase(getContext());
    return true;
  }

  // UriMatcher decoding incoming URIs
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  public static final int ROUTE_POPULAR_MOVIES = 1;
  public static final int ROUTE_TOP_RATED_MOVIES = 2;
  public static final int ROUTE_FAVORITE_MOVIES = 3;
  public static final int ROUTE_MOVIE_DETAIL = 4;

  static {
    uriMatcher.addURI(MovieSyncUtils.CONTENT_AUTHORITY, MovieContract.PATH_POPULAR_MOVIES, ROUTE_POPULAR_MOVIES);
    uriMatcher.addURI(MovieSyncUtils.CONTENT_AUTHORITY, MovieContract.PATH_TOP_RATED_MOVIES, ROUTE_TOP_RATED_MOVIES);
    uriMatcher.addURI(MovieSyncUtils.CONTENT_AUTHORITY, MovieContract.PATH_FAVORITE_MOVIES, ROUTE_FAVORITE_MOVIES);
    uriMatcher.addURI(MovieSyncUtils.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_DETAIL + "/*", ROUTE_MOVIE_DETAIL);
  }


  @Override
  public String getType(Uri uri) {
    final int match = uriMatcher.match(uri);

    switch (match) {
      case ROUTE_POPULAR_MOVIES:
        return MovieContract.MovieRanking.POPULAR_MOVIE_CONTENT_TYPE;
      case ROUTE_TOP_RATED_MOVIES:
        return MovieContract.MovieRanking.TOP_RATED_MOVIE_CONTENT_TYPE;
      case ROUTE_FAVORITE_MOVIES:
        return MovieContract.FavoriteMovie.CONTENT_TYPE;
      case ROUTE_MOVIE_DETAIL:
        return MovieContract.MovieDetail.CONTENT_TYPE;
      default:
        throw new UnsupportedOperationException("Unknow URI : " + uri);
    }
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
