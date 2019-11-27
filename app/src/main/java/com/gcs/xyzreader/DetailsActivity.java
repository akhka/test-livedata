package com.gcs.xyzreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;

import com.gcs.xyzreader.models.XYZJson;
import com.squareup.picasso.Picasso;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_imageview)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        XYZJson object = intent.getParcelableExtra("object");

        ButterKnife.bind(this);

        setImageView(object.getPhoto());

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);



    }


    public void setImageView(String imageUrl){
        Bitmap bitmap = null;

        if (imageUrl != null){
            try{
                bitmap = new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        try{
                            return Picasso.get().load(imageUrl).get();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute().get();
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
