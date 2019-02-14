package changhua.com.myflickclient.model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import changhua.com.myflickclient.api.FlickrService;
import changhua.com.myflickclient.api.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoData {

    public PhotoAll photos;

    public List<PhotoItem> items = new ArrayList<PhotoItem>();

    public void loadPhotoFromJson(Context c) {


        try {
            InputStream is = c.getAssets().open("photos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            photos = gson.fromJson(json, PhotoAll.class);


            for (Photo ph : photos.photos.photo) {
                String url = String.format("http://farm%d.static.flickr.com/%s/%s_%s.jpg", ph.farm, ph.server, ph.id, ph.secret);

                PhotoItem pi = new PhotoItem();
                pi.setImageUrl(url);
                items.add(pi);
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    void convertToPhotoUrl() {
        items.clear();
        for (Photo ph : photos.photos.photo) {
            String url = String.format("http://farm%d.static.flickr.com/%s/%s_%s.jpg", ph.farm, ph.server, ph.id, ph.secret);

            PhotoItem pi = new PhotoItem();
            pi.setImageUrl(url);
            items.add(pi);
        }
    }

    private FlickrService githubService = RetrofitManager.getAPI();

    public MutableLiveData<List<PhotoItem>> searchPhoto(String query) {
        final MutableLiveData<List<PhotoItem>> photoItems = new MutableLiveData<>();
        githubService.queryPhotos(FlickrService.METOD,
                FlickrService.API_KEY,
                FlickrService.FORMAT,
                FlickrService.SET,
                query)
                .enqueue(new Callback<PhotoAll>() {
                    @Override
                    public void onResponse(@NonNull Call<PhotoAll> call, @NonNull Response<PhotoAll> response) {
                        //repos.setValue(response.body().getItems());
                        photos = response.body();
                        convertToPhotoUrl();
                        photoItems.setValue(items);

                    }

                    @Override
                    public void onFailure(@NonNull Call<PhotoAll> call, @NonNull Throwable t) {
                        // TODO: error handle
                    }
                });
        return photoItems;
    }
}
