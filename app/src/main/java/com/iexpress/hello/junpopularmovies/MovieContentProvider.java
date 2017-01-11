package com.iexpress.hello.junpopularmovies;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

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

  /**
   * @return true if the provider was successfully loaded, false otherwise
   */
  @Override
  public boolean onCreate() {
    return true;
  }

  /**
   * Implement this to handle query requests from clients.
   * This method can be called from multiple threads, as described in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
   * and Threads</a>.
   *
   * @param uri           The URI to query. This will be the full URI sent by the client;
   *                      if the client is requesting a specific record, the URI will end in a record number
   *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
   *                      that _id value.
   * @param projection    The list of columns to put into the cursor. If
   *                      {@code null} all columns are included.
   * @param selection     A selection criteria to apply when filtering rows.
   *                      If {@code null} then all rows are included.
   * @param selectionArgs You may include ?s in selection, which will be replaced by
   *                      the values from selectionArgs, in order that they appear in the selection.
   *                      The values will be bound as Strings.
   * @param sortOrder     How the rows in the cursor should be sorted.
   *                      If {@code null} then the provider is free to define the sort order.
   * @return a Cursor or {@code null}.
   */
  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    return null;
  }

  /**
   * Implement this to handle requests for the MIME type of the data at the
   * given URI.  The returned MIME type should start with
   * <code>vnd.android.cursor.item</code> for a single record,
   * or <code>vnd.android.cursor.dir/</code> for multiple items.
   * This method can be called from multiple threads, as described in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
   * and Threads</a>.
   * <p>
   * <p>Note that there are no permissions needed for an application to
   * access this information; if your content provider requires read and/or
   * write permissions, or is not exported, all applications can still call
   * this method regardless of their access permissions.  This allows them
   * to retrieve the MIME type for a URI when dispatching intents.
   *
   * @param uri the URI to query.
   * @return a MIME type string, or {@code null} if there is no type.
   */
  @Nullable
  @Override
  public String getType(Uri uri) {
    return null;
  }

  /**
   * Implement this to handle requests to insert a new row.
   * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
   * after inserting.
   * This method can be called from multiple threads, as described in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
   * and Threads</a>.
   *
   * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
   * @param values A set of column_name/value pairs to add to the database.
   *               This must not be {@code null}.
   * @return The URI for the newly inserted item.
   */
  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

  /**
   * Implement this to handle requests to delete one or more rows.
   * The implementation should apply the selection clause when performing
   * deletion, allowing the operation to affect multiple rows in a directory.
   * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
   * after deleting.
   * This method can be called from multiple threads, as described in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
   * and Threads</a>.
   * <p>
   * <p>The implementation is responsible for parsing out a row ID at the end
   * of the URI, if a specific row is being deleted. That is, the client would
   * pass in <code>content://contacts/people/22</code> and the implementation is
   * responsible for parsing the record number (22) when creating a SQL statement.
   *
   * @param uri           The full URI to query, including a row ID (if a specific record is requested).
   * @param selection     An optional restriction to apply to rows when deleting.
   * @param selectionArgs
   * @return The number of rows affected.
   * @throws SQLException
   */
  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  /**
   * Implement this to handle requests to update one or more rows.
   * The implementation should update all rows matching the selection
   * to set the columns according to the provided values map.
   * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
   * after updating.
   * This method can be called from multiple threads, as described in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
   * and Threads</a>.
   *
   * @param uri           The URI to query. This can potentially have a record ID if this
   *                      is an update request for a specific record.
   * @param values        A set of column_name/value pairs to update in the database.
   *                      This must not be {@code null}.
   * @param selection     An optional filter to match rows to update.
   * @param selectionArgs
   * @return the number of rows affected.
   */
  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
