package com.example.gaboq.instapoo.FilterView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

import java.io.FileNotFoundException;

public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;

    int[] images = {R.drawable.flores, R.drawable.w_b, R.drawable.gaussiano,
            R.drawable.sepia, R.drawable.negative};

    MainFactory mFactory = new MainFactory();

    public Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null) {
            try {
                bitmap = BitmapFactory.decodeStream(FilterViewActivity.this.openFileInput("myImage"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        simpleGallery = (Gallery) findViewById(R.id.simpleGallery);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images);
        simpleGallery.setAdapter(customGalleryAdapter);
        simpleGallery.setSpacing(10);
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui sucede la magia
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
                    IFilter f = mFactory.getInstance(bitmap, position + 1);
                    f.applyFilter();
                    bitmap = f.generateBitmap();
                    Matrix matrix = new Matrix();
                    matrix.setRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    selectedImageView.setImageBitmap(bitmap);
                }


            }
        });
    }

}
