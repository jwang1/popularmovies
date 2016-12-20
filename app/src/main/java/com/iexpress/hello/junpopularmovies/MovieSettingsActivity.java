package com.iexpress.hello.junpopularmovies;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * MovieSettingsActivity uses ListPreference fragment.
 */
public class MovieSettingsActivity extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new TmdbApiPreferenceFragment())
        .commit();
  }

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

  public static class TmdbApiPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferences);
      setHasOptionsMenu(true);
    }
  }

}
