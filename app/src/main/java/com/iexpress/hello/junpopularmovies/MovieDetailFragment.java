package com.iexpress.hello.junpopularmovies;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iexpress.hello.junpopularmovies.util.MovieApiUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

  private final static int MOVIE_VIEW_LAYOUT_HEIGHT = 550;
  private final static int MOVIE_VIEW_LAYOUT_WIDTH = 450;

  private final static String MOVIE_SHARE_HASHTAG = "#PopularMovie";
  private final static String LOG_TAG = MovieDetailFragment.class.getSimpleName();

  private String tmdbId;
  private String imdbId;
  private ShareActionProvider shareActionProvider;
  private String movieInfo;

  private ImageView movieImageView;
  private TextView movieTextView;

  public MovieDetailFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.movie_detail_fragment, menu);

    MenuItem item = menu.findItem(R.id.menu_item_share);

    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

    if (shareActionProvider != null) {
      shareActionProvider.setShareIntent(createShareMovieIntent());
    } else {
      String msg = "Share Action Provider is Null";
      Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

      Log.d(LOG_TAG, msg);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

    movieImageView = (ImageView) rootView.findViewById(R.id.imageView_movie_poster);
    movieTextView = (TextView) rootView.findViewById(R.id.textview_movie_details);

    // http://stackoverflow.com/questions/3144940/set-imageview-width-and-height-programmatically
    movieImageView.requestLayout();
    movieImageView.setAdjustViewBounds(true);
    movieImageView.getLayoutParams().height = MOVIE_VIEW_LAYOUT_HEIGHT;
    movieImageView.getLayoutParams().width = MOVIE_VIEW_LAYOUT_WIDTH;
    movieImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    Intent intent = getActivity().getIntent();
    if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
      tmdbId = intent.getStringExtra(Intent.EXTRA_TEXT);
      movieInfo = MovieApiUtil.TMDB_MOVIE_BY_ID_URL_BASE + "/" + tmdbId;
    }

    FetchMovieDetailTask task = new FetchMovieDetailTask();
    task.execute(tmdbId);

    return rootView;
  }

  private Intent createShareMovieIntent() {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, MOVIE_SHARE_HASHTAG + " : " + movieInfo);
    return shareIntent;
  }

  public class FetchMovieDetailTask extends AsyncTask<String, String, String> {

    private String movieDetailResponse;

    // runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    // runs in UI, it's called from background thread
    @Override
    protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);
      Toast.makeText(getActivity(), "movieDb API in progress ... " + movieDetailResponse, Toast.LENGTH_LONG)
          .show();
    }

    // runs in background thread
    @Override
    protected String doInBackground(String... params) {
      try {
        Uri builtUri = Uri.parse(MovieApiUtil.TMDB_MOVIE_BY_ID_URL_BASE + "/" + params[0]).buildUpon()
            .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
            .build();

        URL url = new URL(builtUri.toString());

        Log.v(LOG_TAG, "built URI: " + builtUri.toString());

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

        movieDetailResponse = sb.toString();

        Log.v(LOG_TAG, "theMovieDb API response: " + movieDetailResponse);

      } catch (Exception e) {
        e.printStackTrace();
        Log.e(LOG_TAG, "caught exception!");
      }
      return movieDetailResponse;
    }

    // runs in UI when background thread finishes
    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);

      if (movieDetailResponse != null && movieDetailResponse.length() > 0) {
        try {
          JSONObject tmdbJsonObj =  MovieApiUtil.getTmdbJsonObject(movieDetailResponse);
          String title = tmdbJsonObj.getString(MovieApiUtil.TITLE);
          imdbId = tmdbJsonObj.getString(MovieApiUtil.IMDB_ID);
          String tmdbPosterPaths = tmdbJsonObj.getString(MovieApiUtil.POSTER_PATH);
          String overview = tmdbJsonObj.getString(MovieApiUtil.OVERVIEW);
          String releaseDate = tmdbJsonObj.getString(MovieApiUtil.RELEASE_DATE);

          Picasso.with(getActivity())
              .load(MovieApiUtil.TMDB_IMAGE_URL_BASE + tmdbPosterPaths)
              .into(movieImageView);

          movieInfo = "\n\n" + title + "\n\n" + releaseDate + "\n\n" + overview;

          movieTextView.setText(movieInfo);

        } catch (JSONException e) {
          Log.e(LOG_TAG, "caught exception parsing JSON string.");
        }
      }
    }
  }
}
