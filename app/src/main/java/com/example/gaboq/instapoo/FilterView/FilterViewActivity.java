package com.example.gaboq.instapoo.FilterView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.R;

public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;

    int[] images = {R.drawable.flores, R.drawable.w_b, R.drawable.gaussiano, R.drawable.negative,
            R.drawable.sepia, R.drawable.ic_home_black_24dp, R.drawable.ic_cam, R.drawable.tree};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);
        simpleGallery = (Gallery) findViewById(R.id.simpleGallery);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images);
        simpleGallery.setAdapter(customGalleryAdapter);
        simpleGallery.setSpacing(10);

        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedImageView.setImageResource(images[position]);

            }
        });
    }

}
