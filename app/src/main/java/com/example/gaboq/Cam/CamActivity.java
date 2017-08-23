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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gaboq.instapoo.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static java.io.File.createTempFile;

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


    public void takePhoto(View view) {
        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                ///e.printStackTrace();
                Log.e("takePhoto", "IOException", e);
                return;
            }

            if (photoFile != null) {
                callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(callCamera, START_CAMERA_APP);
            }
        }
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {

            Bitmap bitmap = BitmapFactory.decodeFile(mImageLocation);
            mPhotoCapture.setImageBitmap(bitmap);

        }
    }


    File createImageFile() throws IOException {

        String time = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "IMAGE" + time + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageFile = createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageLocation = imageFile.getAbsolutePath();

        return imageFile;
    }

}
