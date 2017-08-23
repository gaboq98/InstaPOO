package com.example.gaboq.Cam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CamActivity extends AppCompatActivity {

    private ImageView mPhotoCapture;
    private static final int START_CAMERA_APP = 0;
    private String mImageLocation = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void takePhoto(View view) {
        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCamera, START_CAMERA_APP);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {

            //Bundle extras = data.getExtras();
            //Bitmap bitmap = (Bitmap) extras.get("data");
            //MainFactory mFactory = new MainFactory();
            //IFilter f = mFactory.getFilter(bitmap, 1);
            //bitmap = f.generateBitmap();
            Bitmap bitmap = BitmapFactory.decodeFile(mImageLocation);
            mPhotoCapture.setImageBitmap(bitmap);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    File createImageFile() throws IOException {

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE" + time + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageLocation = imageFile.getAbsolutePath();

        return imageFile;
    }

}
