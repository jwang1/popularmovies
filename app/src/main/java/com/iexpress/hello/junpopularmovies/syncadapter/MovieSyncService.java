package com.iexpress.hello.junpopularmovies.syncadapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Service handling sync requests.
 *
 * It is invoked in response to Intents with action android.content.SyncAdpater,
 * and returns a Binder connection to SyncAdapter.
 *
 * SyncService itself is not notified when a new sync occurs.
 * Its role is to manage the lifecycle of {@link MovieSyncAdapter} and provide a handle to
 * aid {@link MovieSyncAdapter} on request.
 *
 * Created by jwang on 1/10/17.
 */

public class MovieSyncService extends Service {
  private static final String LOG_TAG = MovieSyncService.class.getSimpleName();

  private static final Object movieSyncAdapterLock = new Object();

  private static MovieSyncAdapter movieSyncAdapter = null;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i(LOG_TAG, "MovieSyncService created");

    synchronized (movieSyncAdapterLock) {
      if (movieSyncAdapter == null) {
        movieSyncAdapter = new MovieSyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i(LOG_TAG, "MovieSyncService destroyed");
  }

  /**
   * Return the communication channel to the service.  May return null if clients cannot bind to the service.
   *
   * @param intent The Intent that was used to bind to this service,
   *               as given to {@link Context#bindService
   *               Context.bindService}.  Note that any extras that were included with
   *               the Intent at that point will <em>not</em> be seen here.
   * @return Return an IBinder through which clients can call on to the
   * service.
   */
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return movieSyncAdapter.getSyncAdapterBinder();
  }
}
