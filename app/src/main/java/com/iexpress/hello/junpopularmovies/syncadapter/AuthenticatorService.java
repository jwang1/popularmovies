package com.iexpress.hello.junpopularmovies.syncadapter;

import android.accounts.Account;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Bind Authenticator to Sync Adatpor framework.
 *
 * Created by jwang on 12/26/16.
 */

public class AuthenticatorService extends Service {

  private Authenticator authenticator;

  private static final String LOG_TAG = AuthenticatorService.class.getSimpleName();

  private static final String ACCOUNT_TYPE = MovieSyncUtils.CONTENT_AUTHORITY;

  private static final String ACCOUNT_NAME = "movieSync";

  /**
   * Obtain a handle to {@link android.accounts.Account} used for movie sync in this app.
   *
   * @return a handle to application's account (not guaranteed to resolve
   * unless createSyncAccount() is called)
   */
  public static Account getAccount() {
    final String accountName = ACCOUNT_NAME;
    return new Account(accountName, ACCOUNT_TYPE);
  }

  @Override
  public void onCreate() {
    Log.i(LOG_TAG, "AuthenticatorService created.");
    authenticator = new Authenticator(this);
  }

  @Override
  public void onDestroy() {
    Log.i(LOG_TAG, "AuthenticatorService destroyed");
  }

  /**
   * Return the communication channel to the service.  May return null if
   * clients can not bind to the service.  The returned
   * {@link IBinder} is usually for a complex interface
   * that has been <a href="{@docRoot}guide/components/aidl.html">described using
   * aidl</a>.
   * <p>
   * <p><em>Note that unlike other application components, calls on to the
   * IBinder interface returned here may not happen on the main thread
   * of the process</em>.  More information about the main thread can be found in
   * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
   * Threads</a>.</p>
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
    return authenticator.getIBinder();
  }

}
