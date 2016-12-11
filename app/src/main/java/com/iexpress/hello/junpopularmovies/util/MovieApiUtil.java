package com.iexpress.hello.junpopularmovies.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for MovieDB API
 *
 * https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 *
 * http://www.vogella.com/tutorials/AndroidJSON/article.html
 *
 * Created by jwang on 12/10/16.
 */

public class MovieApiUtil {
  // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=...   (note, it's not , before desc;  instead . )
  public final static String TMDB_DISCOVER_URL_BASE = "http://api.themoviedb.org/3/discover/movie?";
  // http://api.themoviedb.org/3/search/movie?query=tiger&api_key=...
  // https://www.themoviedb.org/documentation/api/discover?language=en    (showing more options for 'discover' api
  public final static String TMDB_SEARCH_URL_BASE = "http://api.themoviedb.org/3/search/movie?";
  // https://developers.themoviedb.org/3/find
  // https://api.themoviedb.org/3/find/{external_id}?api_key=<<api_key>>&language=en-US&external_source=imdb_id
  // tried this :   http://api.themoviedb.org/3/find/346672?api_key=...&external_source=imdb_id
  public final static String TMDB_FIND_URL_BASE = "http://api.themoviedb.org/3/find/movie?";

  public final static String TMDB_IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185";

  public final static String RESULTS = "results";
  public final static String POSTER_PATH = "poster_path";
  public final static String BACKDROP_PATH = "backdrop_path";
  public final static String OVERVIEW = "overview";
  public final static String RELEASE_DATE = "release_date";
  public final static String ORIGINAL_TITLE = "original_title";
  public final static String POPULARITY = "popularity";
  public final static String ID = "id";


  public static JSONObject getTmdbJsonObject(String jsonResponse) throws JSONException {
    return new JSONObject(jsonResponse);
  }

  /**
   * Returning list of string of id:poster_path
   *
   * @param movieResponse
   * @return
   * @throws JSONException
   */
  public static List<String> getMovieDataFromJsonResponse(String movieResponse) throws JSONException {
    List<String> results = new ArrayList<>();

    JSONObject movieData = new JSONObject(movieResponse);
    JSONArray moviesArray = movieData.getJSONArray(RESULTS);

    for (int i = 0; i < moviesArray.length(); i++) {
      StringBuilder sb = new StringBuilder();

      sb.append(moviesArray.getJSONObject(i).getInt(ID))
          .append(":")
          .append(moviesArray.getJSONObject(i).getString(POSTER_PATH));

      results.add(sb.toString());
    }

    return results;
  }

}
