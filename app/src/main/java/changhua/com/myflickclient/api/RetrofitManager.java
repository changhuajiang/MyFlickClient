package changhua.com.myflickclient.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mInstance = new RetrofitManager();

    private FlickrService flickrService;

    private RetrofitManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        flickrService = retrofit.create(FlickrService.class);
    }

    public static FlickrService getAPI() {
        return mInstance.flickrService;
    }
}
