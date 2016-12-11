package com.iexpress.hello.junpopularmovies.util;

/**
 * Movie information from theMovieDb API
 *
 * Created by jwang on 12/10/16.
 */
public class MovieData {
  private int id;
  private String posterPath;
  private boolean adult;
  private String backdropPath;
  private String overview;
  private String releaseDate;
  private String title;
  private String originalTitle;
  private String originalLanguage;
  private double popularity;
  private int voteCount;
  private double voteAverage;
  private boolean video;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MovieData)) return false;

    MovieData movieData = (MovieData) o;

    if (getId() != movieData.getId()) return false;
    if (isAdult() != movieData.isAdult()) return false;
    if (Double.compare(movieData.getPopularity(), getPopularity()) != 0) return false;
    if (getVoteCount() != movieData.getVoteCount()) return false;
    if (Double.compare(movieData.getVoteAverage(), getVoteAverage()) != 0) return false;
    if (isVideo() != movieData.isVideo()) return false;
    if (!getPosterPath().equals(movieData.getPosterPath())) return false;
    if (!getBackdropPath().equals(movieData.getBackdropPath())) return false;
    if (!getOverview().equals(movieData.getOverview())) return false;
    if (!getReleaseDate().equals(movieData.getReleaseDate())) return false;
    if (!getTitle().equals(movieData.getTitle())) return false;
    if (!getOriginalTitle().equals(movieData.getOriginalTitle())) return false;
    return getOriginalLanguage().equals(movieData.getOriginalLanguage());

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getId();
    result = 31 * result + getPosterPath().hashCode();
    result = 31 * result + (isAdult() ? 1 : 0);
    result = 31 * result + getBackdropPath().hashCode();
    result = 31 * result + getOverview().hashCode();
    result = 31 * result + getReleaseDate().hashCode();
    result = 31 * result + getTitle().hashCode();
    result = 31 * result + getOriginalTitle().hashCode();
    result = 31 * result + getOriginalLanguage().hashCode();
    temp = Double.doubleToLongBits(getPopularity());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getVoteCount();
    temp = Double.doubleToLongBits(getVoteAverage());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (isVideo() ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MovieData{" +
        "id=" + id +
        ", posterPath='" + posterPath + '\'' +
        ", adult=" + adult +
        ", backdropPath='" + backdropPath + '\'' +
        ", overview='" + overview + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        ", title='" + title + '\'' +
        ", originalTitle='" + originalTitle + '\'' +
        ", originalLanguage='" + originalLanguage + '\'' +
        ", popularity=" + popularity +
        ", voteCount=" + voteCount +
        ", voteAverage=" + voteAverage +
        ", video=" + video +
        '}';
  }

}
