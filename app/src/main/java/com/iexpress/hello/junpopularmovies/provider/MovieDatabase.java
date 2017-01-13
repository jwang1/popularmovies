package com.iexpress.hello.junpopularmovies.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Access SQLite datastore utlized by {@link MovieContentProvider}.
 * This database not to be accessed by other parts of the application.
 * (can be a inner class, but made it standalone, so trim down
 * the size of {@line MovieContentProvider} class).
 *
 * Created by jwang on 1/12/17.
 */

public class MovieDatabase extends SQLiteOpenHelper {
  private static final String LOGTAG = MovieDatabase.class.getSimpleName();

  /** schema version */
  public static final int DATABASE_VERSION = 1;

  /** filename for SQLite file */
  public static final String DATABASE_NAME = "movie.db";

  private static final String TYPE_TEXT = "TEXT";
  private static final String TYPE_INTEGER = "INTEGER";
  private static final String COMMA_DELIMITOR = ",";

  /** DML creating movie_ranking table */
  private static final String DDL_CREATE_MOVIE_RANKING =
      "CREATE TABLE " + MovieContract.MovieRanking.TABLE_NAME + " ("
      + MovieContract.MovieRanking.COLUMN_NAME_TMDB_ID
      + MovieContract.MovieRanking.COLUMN_NAME_RANK_ID
      + MovieContract.MovieRanking.COLUMN_NAME_TYPE
      + MovieContract.MovieRanking.COLUMN_NAME_CREATION_DATE
      + MovieContract.MovieRanking.COLUMN_NAME_LAST_UPDATE_DATE + ")";

  private static final String DDL_DROP_MOVIE_RANKING =
      "DROP TABLE IF EXISTS " + MovieContract.MovieRanking.TABLE_NAME;

  private static final String DDL_CREATE_FAVORITE_MOVIE =
      "CREATE TABLE " + MovieContract.FavoriteMovie.TABLE_NAME + " ("
      + MovieContract.FavoriteMovie.COLUMN_NAME_TMDB_ID
      + MovieContract.FavoriteMovie.COLUMN_NAME_CURRENT_FAVORITE
      + MovieContract.FavoriteMovie.COLUMN_NAME_CREATION_DATE
      + MovieContract.FavoriteMovie.COLUMN_NAME_LAST_UPDATE_DATE + ")";

  private static final String DDL_DROP_FAVORITE_MOVIE =
      "DROP TABLE IF EXISTS " + MovieContract.FavoriteMovie.TABLE_NAME;

  private static final String DDL_CREATE_MOVIE_DETAIL =
      "CREATE TABLE " + MovieContract.MovieDetail.TABLE_NAME + " ("
          + MovieContract.MovieDetail.COLUMN_NAME_TMDB_ID
          + MovieContract.MovieDetail.COLUMN_NAME_INFO
          + MovieContract.MovieDetail.COLUMN_NAME_VIDEO
          + MovieContract.MovieDetail.COLUMN_NAME_REVIEW
          + MovieContract.MovieDetail.COLUMN_NAME_CREATION_DATE
          + MovieContract.MovieDetail.COLUMN_NAME_LAST_UPDATE_DATE + ")";

  private static final String DDL_DROP_MOVIE_DETAIL =
      "DROP TABLE IF EXISTS " + MovieContract.MovieDetail.TABLE_NAME;


  public MovieDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    Log.i(LOGTAG, "MovieDatabase instantiation...");
  }

  /**
   * Called when the database is created for the first time. This is where the
   * creation of tables and the initial population of the tables should happen.
   *
   * @param db The database.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.i(LOGTAG, "create tables : " + MovieContract.MovieRanking.TABLE_NAME
      + ", " + MovieContract.FavoriteMovie.TABLE_NAME
      + ", " + MovieContract.MovieDetail.TABLE_NAME);

    db.execSQL(DDL_CREATE_MOVIE_RANKING);
    db.execSQL(DDL_CREATE_FAVORITE_MOVIE);
    db.execSQL(DDL_CREATE_MOVIE_DETAIL);
  }

  /**
   * Called when the database needs to be upgraded. The implementation
   * should use this method to drop tables, add tables, or do anything else it
   * needs to upgrade to the new schema version.
   * <p>
   * This method executes within a transaction.  If an exception is thrown, all changes
   * will automatically be rolled back.
   * </p>
   *
   * @param db         The database.
   * @param oldVersion The old database version.
   * @param newVersion The new database version.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.i(LOGTAG, "MovieDatabase onUpgrade...");

    // If only treat the movie_db as "cache" for TMDB data; we can discard the data and start over again.
    db.execSQL(DDL_DROP_MOVIE_RANKING);
    db.execSQL(DDL_DROP_MOVIE_RANKING);
    db.execSQL(DDL_DROP_MOVIE_RANKING);
  }
}
