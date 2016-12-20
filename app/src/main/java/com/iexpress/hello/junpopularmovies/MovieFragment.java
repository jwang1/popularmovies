package com.iexpress.hello.junpopularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.iexpress.hello.junpopularmovies.util.MovieApiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * MovieFragment class to hold movie thumbnails in the grid.
 *
 * The movieDB api :
 *
 * http://api.themoviedb.org/3/discover/movie?sort_by=popularity,desc&api_key=....
 *
 * Using Picasso load images for GridView
 * http://www.101apps.co.za/index.php/articles/gridview-tutorial-using-the-picasso-library.html
 *
 * Created by jwang on 12/6/16.
 */
public class MovieFragment extends Fragment {
  private final static String LOGTAG = MovieFragment.class.getSimpleName();

  private GridView movieGridView;

  private String apiUrl = MovieApiUtil.TMDB_POPULAR_URL_BASE;

  public MovieFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.movie_fragment, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_refresh) {
      FetchMovieTask task = new FetchMovieTask();
      task.execute(apiUrl);
      return true;
    }

    return true;
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    //movieListView = (ListView) rootView.findViewById(R.id.listview_movie);
    //movieTextView = (TextView) rootView.findViewById(R.id.textView_movie);

    movieGridView = (GridView) rootView.findViewById(R.id.gridview_movie);

    FetchMovieTask task = new FetchMovieTask();
    task.execute(apiUrl);

    movieGridView.setOnItemClickListener((adapterView, view, i, l) -> {
      Intent detailIntent = new Intent(getActivity(), DetailActivity.class)
          .putExtra(Intent.EXTRA_TEXT, "" + ((GridView) adapterView).getAdapter().getItemId(i));

      startActivity(detailIntent);
    });

    return rootView;
  }



  public class FetchMovieTask extends AsyncTask<String, String, String> {

    private String movieResponse;

    // runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    // runs in UI, it's called from background thread
    @Override
    protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);
      Toast.makeText(getActivity(), "movieDb API in progress ... " + movieResponse, Toast.LENGTH_LONG)
          .show();
    }

    // runs in background thread
    @Override
    protected String doInBackground(String... params) {
      try {
        Uri builtUri = Uri.parse(params[0]).buildUpon()
            .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
            .build();

        URL url = new URL(builtUri.toString());

        Log.v(LOGTAG, "built URI: " + builtUri.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while((line = br.readLine()) != null) {
          sb.append(line);
        }

        movieResponse = sb.toString();

        Log.v(LOGTAG, "theMovieDb API response: " + movieResponse);

      } catch (Exception e) {
        e.printStackTrace();
        Log.e(LOGTAG, "caught exception!");
      }
      return movieResponse;
    }

    // runs in UI when background thread finishes
    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);

      if (movieResponse != null && movieResponse.length() > 0) {
        try {
          JSONObject tmdbJsonObj =  MovieApiUtil.getTmdbJsonObject(movieResponse);
          JSONArray tmdbMovies = tmdbJsonObj.getJSONArray(MovieApiUtil.RESULTS);
          Integer[] tmdbIds = new Integer[tmdbMovies.length()];
          String[] tmdbPosterPaths = new String[tmdbMovies.length()];

          for (int i = 0; i < tmdbMovies.length(); i ++) {
            tmdbIds[i] = tmdbMovies.getJSONObject(i).getInt(MovieApiUtil.ID);
            tmdbPosterPaths[i] = MovieApiUtil.TMDB_IMAGE_URL_BASE + tmdbMovies.getJSONObject(i).getString(MovieApiUtil.POSTER_PATH);
          }

          MovieImageAdapter adapter = new MovieImageAdapter(getActivity());
          adapter.setPosterPaths(tmdbPosterPaths);
          adapter.setTmdbIds(tmdbIds);

          movieGridView.setAdapter(adapter);

        } catch (JSONException e) {
          Log.e(LOGTAG, "caught exception parsing JSON string: " + e.getMessage());
        } catch (Exception e) {
          Log.e(LOGTAG, "caught exception: " + e.getMessage());
        }
      }
    }
  }

}
