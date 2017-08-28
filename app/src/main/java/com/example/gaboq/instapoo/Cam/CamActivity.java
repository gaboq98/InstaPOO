package com.example.gaboq.instapoo.Cam;

import android.content.Intent;
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

    private String mImageLocation = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);
        Button mButton = (Button) findViewById(R.id.button);
        mButton.performClick();

    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            rotateImage(Bitmap.createScaledBitmap(reduceImage(), 800, 533, true));

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
        Intent intent = new Intent(this,FilterViewActivity.class).putExtra("img",mImageLocation);
        startActivity(intent);
        //mPhotoCapture.setImageBitmap(rotatedBitmap);
    }


    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(this, new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);

    }

}
