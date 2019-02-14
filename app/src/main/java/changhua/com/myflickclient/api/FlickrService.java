package changhua.com.myflickclient.api;


import changhua.com.myflickclient.model.PhotoAll;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    //static final String API_KEY = "4b37ee018d17b1aa7cd793ede2ea7ee7";
    static final String API_KEY = "0e2b6aaf8a6901c264acb91f151a3350";
    static final String METOD ="flickr.photos.search";
    static final String FORMAT ="json";
    static final String SET ="1";

        @GET("services/rest/")
        Call<PhotoAll> queryPhotos(
            @Query("method")String METOD,
            @Query("api_key") String API_KEY,
            @Query("format") String FORMAT,
            @Query("nojsoncallback") String SET,
            @Query("text") String searchText
        );
}
