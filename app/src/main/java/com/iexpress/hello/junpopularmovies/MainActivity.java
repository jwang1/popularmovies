package com.iexpress.hello.junpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    final Context context = this;

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.activity_main, new MovieFragment())
          .commit();
    }

    // Adding Stetho support
    Stetho.initialize(Stetho.newInitializerBuilder(context)
      .enableDumpapp(new MyDumperPluginProvider(context))
      .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
      .build());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      // Settings
      Intent intent = new Intent(this, MovieSettingsActivity.class);
      startActivity(intent);

      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private class MyDumperPluginProvider implements DumperPluginsProvider {
    private Context context;

    public MyDumperPluginProvider(Context context) {
      this.context = context;
    }

    @Override
    public Iterable<DumperPlugin> get() {
      List<DumperPlugin> plugins = new ArrayList<>();

      for (DumperPlugin defaultPlugin : Stetho.defaultDumperPluginsProvider(context).get()) {
        plugins.add(defaultPlugin);
      }

      return plugins;
    }
  }

}
