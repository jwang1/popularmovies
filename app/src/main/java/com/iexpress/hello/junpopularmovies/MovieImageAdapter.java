package com.iexpress.hello.junpopularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * MovieImageAdapter is used to populate popular movie thumbnails for the GridView; it has little layout handling.
 *
 * Regarding the Placeholder image and error handling for Picasso API:
 *
 * http://stackoverflow.com/questions/33412856/how-to-add-the-place-holder-image-in-android
 * -->  and its GitHub example :
 *
 * https://futurestud.io/tutorials/picasso-placeholders-errors-and-fading
 *
 * http://stackoverflow.com/questions/22143157/android-picasso-placeholder-and-error-image-styling
 * http://stackoverflow.com/questions/18648573/use-placeholder-image-from-drawable-if-the-image-url-is-null-or-empty
 *
 *
 * Created by jwang on 12/10/16.
 */
public class MovieImageAdapter extends BaseAdapter {
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
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setAdjustViewBounds(true);
    } else {
      imageView = (ImageView) convertView;
    }

    Picasso.with(context)
        .load(posterPaths[position])
        .placeholder(R.drawable.user_placeholder)
        .error(R.drawable.user_placeholder_error)
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
