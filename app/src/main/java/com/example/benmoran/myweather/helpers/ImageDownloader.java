package com.example.benmoran.myweather.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ben Moran on 14/06/16.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final String TAG = "ImageDownloader";

    ImageView imageView;
    OkHttpClient httpClient;

    public ImageDownloader(ImageView imageView) {
        this.imageView = imageView;
        httpClient = new OkHttpClient();
    }

    @Override
    protected Bitmap doInBackground(String... iconIds) {
        Bitmap bitmap = null;
        String url = "http://openweathermap.org/img/w/" + iconIds[0] + ".png";
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        } catch (Exception e) {

        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
