package com.iexpress.hello.junpopularmovies.util;

import java.util.List;

/**
 * The Movie DB Json Response
 *
 * https://developers.themoviedb.org/3/discover
 *
 * Created by jwang on 12/10/16.
 */

public class TmdbData {
  private int page;
  private List<MovieData> results;
  private int totalResults;
  private int totalPages;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<MovieData> getResults() {
    return results;
  }

  public void setResults(List<MovieData> results) {
    this.results = results;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TmdbData)) return false;

    TmdbData tmdbData = (TmdbData) o;

    if (getPage() != tmdbData.getPage()) return false;
    if (getTotalResults() != tmdbData.getTotalResults()) return false;
    if (getTotalPages() != tmdbData.getTotalPages()) return false;
    return getResults().equals(tmdbData.getResults());

  }

  @Override
  public int hashCode() {
    int result = getPage();
    result = 31 * result + getResults().hashCode();
    result = 31 * result + getTotalResults();
    result = 31 * result + getTotalPages();
    return result;
  }
}

/*

 */
