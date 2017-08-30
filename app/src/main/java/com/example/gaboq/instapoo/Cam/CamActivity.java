package com.example.gaboq.instapoo.Cam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gaboq.instapoo.FilterView.FilterViewActivity;

import com.example.gaboq.instapoo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class CamActivity extends AppCompatActivity {

    private ImageView mPhotoCapture;

    private static final int START_CAMERA_APP = 0;

    public Bitmap mBitmap;

    private String mImageLocation = "";

    public static final int SELECT_PHOTO_ACTION = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(CamActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(CamActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        SELECT_PHOTO_ACTION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        setContentView(R.layout.activity_cam);
        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);
        Button mButton = (Button) findViewById(R.id.button);
        mButton.performClick();

    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            mBitmap = Bitmap.createScaledBitmap(reduceImage(), 800, 600, true);
            rotateImage(mBitmap);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void takePhoto(View view) {

        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCamera.resolveActivity(getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e("takePhoto", "IOException", e);
                return;
            }

            if (photoFile != null) {

                callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                startActivityForResult(callCamera, START_CAMERA_APP);
            }
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {

        String time = null;
        time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFht = bitmap.outHeight;
        bitmap.inSampleSize = Math.min(cameraImageWidth/imageViewWidth, cameraImageHeight/imageViewHeigth);
        bitmap.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(mImageLocation, bitmap);
 intent);

    }


    public String createImageFromBitmap(Bitmap bitmap) {