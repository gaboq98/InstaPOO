package com.example.gaboq.Cam;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.gaboq.instapoo.R;

public class CamActivity extends AppCompatActivity {

    private ImageView mPhotoCapture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);

    }


}
