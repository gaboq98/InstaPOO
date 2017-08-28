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

        String imageFileName = "IMG" + time;

        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        galleryAddPic(imageFile);
        mImageLocation = imageFile.getAbsolutePath();

        return imageFile;
    }


    private Bitmap reduceImage() {

        int imageViewWidth = 1080;        //mPhotoCapture.getWidth();
        int imageViewHeigth = 1704;      //mPhotoCapture.getHeight();
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
        Bitmap.createScaledBitmap(reduceImage(), 800, 533, true);
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


    public void openEditPhoto(View view) {

        Intent intent = new Intent(this, FilterViewActivity.class);
        String str = createImageFromBitmap(mBitmap);
        intent.putExtra("Bitmap", str);
        startActivity(intent);

    }


    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SELECT_PHOTO_ACTION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
