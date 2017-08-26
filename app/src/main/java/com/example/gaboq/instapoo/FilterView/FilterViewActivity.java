package com.example.gaboq.instapoo.FilterView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;

    int[] images = {R.drawable.flores, R.drawable.w_b, R.drawable.gaussiano, R.drawable.negative,
            R.drawable.sepia};

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
                //Aqui sucede la magia
                Drawable d = getResources().getDrawable(images[position]);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
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
