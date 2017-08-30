package com.example.gaboq.instapoo.Cam;


import android.Manifest;
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

import java.io.File;
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

        if (ContextCompat.checkSelfPermission(CamActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(CamActivity.this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        SELECT_PHOTO_ACTION);
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

        String imageFileName = "IMG" + time;

        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        galleryAddPic(imageFile);
        mImageLocation = imageFile.getAbsolutePath();

        return imageFile;
    }


    private Bitmap reduceImage() {

        int imageViewWidth = 1080;
        int imageViewHeigth = 1704;
        BitmapFactory.Options bitmap = new BitmapFactory.Options();
        bitmap.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageLocation, bitmap);

        int cameraImageWidth = bitmap.outWidth;
        int cameraImageHeight = bitmap.outHeight;
        bitmap.inSampleSize = Math.min(cameraImageWidth/imageViewWidth, cameraImageHeight/imageViewHeigth);
        bitmap.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(mImageLocation, bitmap);
    }


    private void rotateImage(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        rotatedBitmap = Bitmap.createScaledBitmap(reduceImage(), 640, 480, true);

        mPhotoCapture.setImageBitmap(rotatedBitmap);
        Intent intent = new Intent(this,FilterViewActivity.class).putExtra("img",mImageLocation);
        startActivity(intent);

    }


    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(this, new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);

    }


}
