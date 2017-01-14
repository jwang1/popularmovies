package com.iexpress.hello.junpopularmovies.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Static helper apis for working with sync framework.
 *
 * Created by jwang on 1/12/17.
 */

public class MovieSyncUtils {

  public static final String CONTENT_AUTHORITY = "com.iexpress.hello.junpopularmovies.moviesyncadapter";

  private static final long SYNC_FREQUENCY = 60 * 60;  // in seconds

  private static final String PREF_SETUP_COMPLETE = "setup_complete";

  public static void createSyncAccount(Context context) {
    boolean newAccount = false;
    boolean setupComplete = PreferenceManager
        .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

    Account account = AuthenticatorService.getAccount();

    AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

    if (accountManager.addAccountExplicitly(account, null, null)) {
      // this account supports "sync"
      ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);

      // this account eligible for "auto sync when network is up"
      ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);

      // a schedule for automatic synchronization. System may modify it based on
      // other scheduled syncs or/and network utiliztion
      ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY,
          new Bundle(), SYNC_FREQUENCY);

      newAccount = true;
    }

    if (newAccount || !setupComplete) {
      triggerRefresh();
      PreferenceManager.getDefaultSharedPreferences(context).edit()
          .putBoolean(PREF_SETUP_COMPLETE, true)
          //.commit();
          .apply();
    }
  }

  public static void triggerRefresh() {
    Bundle b = new Bundle();

    // sync right now
    b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
    b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

    ContentResolver.requestSync(
        AuthenticatorService.getAccount(),
        CONTENT_AUTHORITY,
        b);
  }

}
