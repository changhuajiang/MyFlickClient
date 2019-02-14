package changhua.com.myflickclient.ui.main;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MyBinding {


    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        Context context = imageView.getContext();
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
