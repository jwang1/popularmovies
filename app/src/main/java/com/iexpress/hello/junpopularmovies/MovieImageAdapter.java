package com.iexpress.hello.junpopularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * MovieImageAdapter is used to populate popular movie thumbnails for the GridView; it has little layout handling.
 *
 * Created by jwang on 12/10/16.
 */
public class MovieImageAdapter extends BaseAdapter {
  private final int LAYOUT_PARAMS_WIDTH = 250;
  private final int LAYOUT_PARAMS_HEIGHT = 250;
  private final int IMAGE_VIEW_PADDING = 6;

  private Context context;
  private String[] posterPaths;
  private Integer[] tmdbIds;

  public MovieImageAdapter(Context c) {
    context = c;
  }

  public int getCount() {
    return posterPaths.length;
  }

  public Object getItem(int position) {
    return null;
  }

  /**
   * Returns the tmdb_id of the image of the movie being clicked
   *
   * @param position movie position in the GridView
   * @return tmdb_id of the movie being clicked
   */
  public long getItemId(int position) {
    return tmdbIds[position];
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView;
    if (convertView == null) {
      // if it's not recycled, initialize some attributes
      imageView = new ImageView(context);
      imageView.setLayoutParams(new GridView.LayoutParams(LAYOUT_PARAMS_WIDTH, LAYOUT_PARAMS_HEIGHT));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setPadding(IMAGE_VIEW_PADDING, IMAGE_VIEW_PADDING, IMAGE_VIEW_PADDING, IMAGE_VIEW_PADDING);
    } else {
      imageView = (ImageView) convertView;
    }

    Picasso.with(context)
        .load(posterPaths[position])
        .into(imageView);

    return imageView;
  }

  public void setTmdbIds(Integer[] tmdbIds) {
    this.tmdbIds = tmdbIds;
  }

  public void setPosterPaths(String[] posterPaths) {
    this.posterPaths = posterPaths;
  }
}
