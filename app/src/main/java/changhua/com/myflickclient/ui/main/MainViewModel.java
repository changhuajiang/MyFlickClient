package changhua.com.myflickclient.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import java.util.List;

import changhua.com.myflickclient.model.PhotoData;
import changhua.com.myflickclient.model.PhotoItem;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public ObservableField<String> input = new ObservableField("");

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<List<PhotoItem>> photoItems;

    private PhotoData dataModel;

    public MainViewModel(final PhotoData dataModel) {
        super();
        this.dataModel = dataModel;
        photoItems = Transformations.switchMap(query, new Function<String, LiveData<List<PhotoItem>>>() {
            @Override
            public LiveData<List<PhotoItem>> apply(String userInput) {
                if (TextUtils.isEmpty(userInput)) {
                    return dataModel.searchPhoto("dog");
                } else {
                    return dataModel.searchPhoto(userInput);
                }
            }
        });
    }

    LiveData<List<PhotoItem>> getPhotoItems() {
        return photoItems;
    }

    public void searchPhotos() {
        query.setValue(input.get());
    }
}
