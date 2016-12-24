package com.iexpress.hello.junpopularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
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
 *
 * Created by jwang on 12/23/16.
 */

public class FetchMovieTask extends AsyncTask<String, String, String> {
  public final String LOGTAG = FetchMovieTask.class.getSimpleName();

  private String movieResponse;

  private Context context;

  private GridView movieGridView;

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

  public void setContext(Context context) {
    this.context = context;
  }

  public Context getActivity() {
    return getContext();
  }

  public Context getContext() {
    return context;
  }

  public void setMovieGridView(GridView movieGridView) {
    this.movieGridView = movieGridView;
  }

}
