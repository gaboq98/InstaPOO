package com.example.gaboq.Cam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

public class CamActivity extends AppCompatActivity {

    private ImageView mPhotoCapture;

    private static final int START_CAMERA_APP = 0;
    MainFactory mFactory = new MainFactory();
    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);

    }

    public void takePhoto(View view) {
        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCamera, START_CAMERA_APP);
    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            image = bitmap;
            mPhotoCapture.setImageBitmap(bitmap);

        }
    }

    protected void btnClick(View v){
        IFilter filter;
        switch (v.getId()){
            case R.id.button1:
                filter = mFactory.getInstance(image,4);
                break;
            case R.id.button2:
                filter = mFactory.getInstance(image,2);
                break;
            case R.id.button3:
                filter = mFactory.getInstance(image,3);
                break;
            default:
                filter = mFactory.getInstance(image,6);
        }
        filter.applyFilter();
        mPhotoCapture.setImageBitmap(filter.generateBitmap());
    }

}
