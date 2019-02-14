package changhua.com.myflickclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageView imageView = (ImageView) findViewById(R.id.imageFull);
        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Bundle bundle = getIntent().getExtras();
        String url = "";
        if (bundle != null) {
            url = bundle.getString("imageUrl");

            Glide.with(this)
                    .load(url)
                    //.apply(options)
                    .into(imageView);

        }



    }



}
