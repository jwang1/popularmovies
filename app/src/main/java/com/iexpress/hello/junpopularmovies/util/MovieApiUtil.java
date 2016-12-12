package com.iexpress.hello.junpopularmovies.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for MovieDB API
 *
 * Thought about using Apache Jackson
 * https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 *
 * Created by jwang on 12/10/16.
 */

public class MovieApiUtil {

  // ----------------------------------------------------------------------------
  // Get popular movies, let TMDB give us some results
  // ----------------------------------------------------------------------------
  // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=...   (note, it's not , before desc;  instead . )
  public final static String TMDB_DISCOVER_URL_BASE = "http://api.themoviedb.org/3/discover/movie?";

  // ----------------------------------------------------------------------------
  // Search TMDB based on some query string, eg: actor's name etc
  // ----------------------------------------------------------------------------
  // http://api.themoviedb.org/3/search/movie?query=tiger&api_key=...
  // https://www.themoviedb.org/documentation/api/discover?language=en    (showing more options for 'discover' api
  public final static String TMDB_SEARCH_URL_BASE = "http://api.themoviedb.org/3/search/movie?";

  // ----------------------------------------------------------------------------
  // Using "external" resource_id, for instance, imdb_id;  to find Record in TMDB
  // ----------------------------------------------------------------------------
  // https://developers.themoviedb.org/3/find
  // https://api.themoviedb.org/3/find/{external_id}?api_key=<<api_key>>&language=en-US&external_source=imdb_id
  // tried this :   http://api.themoviedb.org/3/find/346672?api_key=...&external_source=imdb_id
  public final static String TMDB_FIND_URL_BASE = "http://api.themoviedb.org/3/find/movie?";


  // ----------------------------------------------------------------------------
  // Get TMDB movie details based on its tmdb_id
  // example : http://api.themoviedb.org/3/movie/553?api_key=...
  // ----------------------------------------------------------------------------
  public final static String TMDB_MOVIE_BY_ID_URL_BASE = "http://api.themoviedb.org/3/movie";


  public final static String TMDB_IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185";

  public final static String RESULTS = "results";
  public final static String POSTER_PATH = "poster_path";
  public final static String BACKDROP_PATH = "backdrop_path";
  public final static String OVERVIEW = "overview";
  public final static String RELEASE_DATE = "release_date";
  public final static String TITLE = "title";
  public final static String ORIGINAL_TITLE = "original_title";
  public final static String ORIGINAL_LANGUAGE = "original_language";
  public final static String POPULARITY = "popularity";
  public final static String ID = "id";
  public final static String IMDB_ID = "imdb_id";
  public final static String ADULT = "adult";
  public final static String VIDEO = "video";
  public final static String VOTE_AVERAGE = "vote_average";
  public final static String VOTE_COUNT = "vote_count";


  public static JSONObject getTmdbJsonObject(String jsonResponse) throws JSONException {
    return new JSONObject(jsonResponse);
  }

  /**
   * Map TMDB JSON data to MovieData object.
   *
   * @param jsonResponse TMDB JSON Response
   * @return a list of MovieData objects
   * @throws JSONException exception raised by JSON library.
   */
  public static List<MovieData> parseTmdbJson(String jsonResponse) throws JSONException {
    List<MovieData> movies = new ArrayList<>();
    JSONObject tmdbJsonObj = getTmdbJsonObject(jsonResponse);
    JSONArray moviesArray = tmdbJsonObj.getJSONArray(RESULTS);
    MovieData data;
    JSONObject movieJsonObj;

    for (int i = 0; i < moviesArray.length(); i++) {
      movieJsonObj = moviesArray.getJSONObject(i);

      data = new MovieData();
      movies.add(data);

      data.setAdult(movieJsonObj.getBoolean(ADULT));
      data.setBackdropPath(movieJsonObj.getString(BACKDROP_PATH));
      data.setId(movieJsonObj.getInt(ID));
      data.setOriginalLanguage(movieJsonObj.getString(ORIGINAL_LANGUAGE));
      data.setOriginalTitle(movieJsonObj.getString(ORIGINAL_TITLE));
      data.setOverview(movieJsonObj.getString(OVERVIEW));
      data.setPopularity(movieJsonObj.getDouble(POPULARITY));
      data.setReleaseDate(movieJsonObj.getString(RELEASE_DATE));
      data.setVideo(movieJsonObj.getBoolean(VIDEO));
      data.setVoteAverage(movieJsonObj.getDouble(VOTE_AVERAGE));
      data.setVoteCount(movieJsonObj.getInt(VOTE_COUNT));
      data.setTitle(movieJsonObj.getString(TITLE));
    }

    return movies;
  }

  /**
   * Returning list of string of id:poster_path
   *
   * @param movieResponse TMDB JSON Response
   * @return a list of string of id:poster_path information
   * @throws JSONException exception from JSON library if raised during JSON parsing.
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
