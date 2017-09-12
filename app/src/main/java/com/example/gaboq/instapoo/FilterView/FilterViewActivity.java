package com.example.gaboq.instapoo.FilterView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static layout.MainFragment.resize;


public class FilterViewActivity extends AppCompatActivity {

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;


    int[] images = {R.drawable.flores,
            R.drawable.w_b, R.drawable.w_b, R.drawable.w_b, R.drawable.w_b,
            R.drawable.sepia, R.drawable.negative,
            R.drawable.gaussiano,R.drawable.w_b};

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
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeFile(imageString);
                //bitmap = resize(bitmap, 1080, 1080);
                IFilter f = mFactory.getInstance(bitmap, position, getApplicationContext());
                bitmap = f.generateBitmap();
                selectedImageView.setImageBitmap(bitmap);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveImageFile(View view) throws IOException {

        String time = null;
        time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG" + time;
        File storageDirectory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        galleryAddPic(imageFile);
        Bitmap bitmap = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(this, new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);

    }

}
