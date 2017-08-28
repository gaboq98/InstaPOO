package com.example.gaboq.instapoo.FilterView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;
import com.example.gaboq.instapoo.filters.Imagen;

public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;

    int[] images = {R.drawable.flores,
            R.drawable.w_b, R.drawable.w_b, R.drawable.w_b, R.drawable.w_b,
            R.drawable.sepia, R.drawable.negative,
            R.drawable.gaussiano};

    MainFactory mFactory = new MainFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        final String imageString = getIntent().getStringExtra("img");


        simpleGallery = (Gallery) findViewById(R.id.simpleGallery);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
        final Uri uri = Uri.parse(imageString);
        selectedImageView.setImageURI(uri);
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images);
        simpleGallery.setAdapter(customGalleryAdapter);
        simpleGallery.setSpacing(10);
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui sucede la magia
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeFile(imageString);
                IFilter f = mFactory.getInstance(bitmap, position);
                bitmap = f.generateBitmap();
                selectedImageView.setImageBitmap(bitmap);
            }
        });
    }

}
