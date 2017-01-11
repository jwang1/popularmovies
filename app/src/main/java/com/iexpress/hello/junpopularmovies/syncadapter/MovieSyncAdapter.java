package com.iexpress.hello.junpopularmovies.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Sync adapter for the app.
 *
 * This class is instantiated in {@link MovieSyncService}, which also binds SyncAdapter
 * to the system. SyncAdapter should only be initialized in SyncService, never anywhere else.
 *
 * The system calls onPerformSync() via an RPC call through the IBinder object.
 *
 * Created by jwang on 12/27/16.
 */

public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {
  public static final String TAG = "MovieSyncAdapter";

  private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;

  private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

  ContentResolver contentResolver;

  /**
   * Creates an {@link AbstractThreadedSyncAdapter}.
   *
   * @param context        the {@link Context} that this is running within.
   * @param autoInitialize if true then sync requests that have
   *                       {@link ContentResolver#SYNC_EXTRAS_INITIALIZE} set will be internally handled by
   *                       {@link AbstractThreadedSyncAdapter} by calling
   *                       {@link ContentResolver#setIsSyncable(Account, String, int)} with 1 if it
   */
  public MovieSyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
    contentResolver = context.getContentResolver();
  }

  /**
   * Perform a sync for this account. SyncAdapter-specific parameters may
   * be specified in extras, which is guaranteed to not be null. Invocations
   * of this method are guaranteed to be serialized.
   *
   * @param account    the account that should be synced
   * @param extras     SyncAdapter-specific parameters
   * @param authority  the authority of this sync request
   * @param provider   a ContentProviderClient that points to the ContentProvider for this
   *                   authority
   * @param syncResult SyncAdapter-specific parameters
   */
  @Override
  public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

  }
}
