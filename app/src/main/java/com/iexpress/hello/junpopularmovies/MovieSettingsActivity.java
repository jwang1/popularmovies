package com.iexpress.hello.junpopularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

/**
 * MovieSettingsActivity uses ListPreference fragment.
 *
 * Got good understanding on this ActionBar "Up" button's Setting (AndroidManifest.xml),  and onCreate,
 * and Responding in onMenuItemSelected , on OptionMenuItemSelected
 *
 * 1. https://developer.android.com/training/appbar/up-action.html
 *
 * 2. https://developer.android.com/training/implementing-navigation/ancestral.html
 *
 *
 */
public class MovieSettingsActivity extends PreferenceActivity {

  private AppCompatDelegate delegate;

  /**
   * Handle "Up" button in ActionBar
   *
   * @param featureId
   * @param item
   * @return
   */
  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      if (!super.onMenuItemSelected(featureId, item)) {
        NavUtils.navigateUpFromSameTask(this);
      }
      return true;
    }
    return super.onMenuItemSelected(featureId, item);
  }

  private static Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
      String stringValue = value.toString();

      if (preference instanceof ListPreference) {
        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(stringValue);
        if (prefIndex >= 0) {
          preference.setSummary(listPreference.getEntries()[prefIndex]);
        } else {
          preference.setSummary(stringValue);
        }
      }

      return true;
    }
  };

  private static void bindPreferenceSummaryToValue(Preference preference) {
    preference.setOnPreferenceChangeListener(preferenceChangeListener);

    preferenceChangeListener.onPreferenceChange(preference,
        PreferenceManager
            .getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), ""));
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new TmdbApiPreferenceFragment())
        .commit();

    // setup Up button
    if (delegate == null) {
      delegate = AppCompatDelegate.create(this, null);
    }
    ActionBar actionBar = delegate.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  public static class TmdbApiPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferences);
      setHasOptionsMenu(true);

      bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_api_key)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
        startActivity(new Intent(getActivity(), MainActivity.class));
        return true;
      }
      return super.onOptionsItemSelected(item);
    }
  }

}
