package com.example.gaboq.instapoo.FilterView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

import java.io.File;
import java.io.IOException;

public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;

    int[] images = {R.drawable.flores, R.drawable.w_b, R.drawable.gaussiano,
            R.drawable.sepia, R.drawable.negative};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        final String i = getIntent().getStringExtra("img");


        simpleGallery = (Gallery) findViewById(R.id.simpleGallery);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);

        selectedImageView.setImageURI(Uri.parse(i));

        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images);
        simpleGallery.setAdapter(customGalleryAdapter);
        simpleGallery.setSpacing(10);
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui sucede la magia
                //Drawable d = getResources().getDrawable(images[position]);
                //Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(FilterViewActivity.this.getContentResolver(), Uri.parse(i));
                } catch (IOException e) {
                    Drawable d = getResources().getDrawable(images[position]);
                    bitmap = ((BitmapDrawable)d).getBitmap();
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
                MainFactory mFactory = new MainFactory();
                IFilter f = mFactory.getInstance(bitmap, position + 1);
                f.applyFilter();
                bitmap = f.generateBitmap();
                selectedImageView.setImageBitmap(bitmap);

                //selectedImageView.setImageResource(images[position]);

            }
        });
    }

}
