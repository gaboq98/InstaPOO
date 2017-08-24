package com.example.gaboq.instapoo.Cam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
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
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.filters.IFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static com.example.gaboq.instapoo.R.id.imageView;
import static java.io.File.createTempFile;

public class CamActivity extends AppCompatActivity {

    private ImageView mPhotoCapture;

    private static final int START_CAMERA_APP = 1;      //Antes tenía un 0

    private String mImageLocation = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        mPhotoCapture = (ImageView) findViewById(R.id.photoCaptureImageView);


    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            rotateImage(reduceImage());

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "IMG" + time + "_";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        galleryAddPic(imageFile);
        mImageLocation = imageFile.getAbsolutePath();

        return imageFile;
    }


    private Bitmap reduceImage() {

        int imageViewWidth = mPhotoCapture.getWidth();
        int imageViewHeigth = mPhotoCapture.getHeight();
        BitmapFactory.Options bitmap = new BitmapFactory.Options();
        bitmap.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageLocation, bitmap);

        int cameraImageWidth = bitmap.outWidth;
        int cameraImageHeight = bitmap.outHeight;
        int scale = Math.min(cameraImageWidth/imageViewWidth, cameraImageHeight/imageViewHeigth);
        bitmap.inSampleSize = scale;
        bitmap.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(mImageLocation, bitmap);


    }


    private void rotateImage(Bitmap bitmap) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(mImageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);       //Siempre retorna 0

        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        MainFactory mainFactory = new MainFactory();
        IFilter filter = mainFactory.getInstance(rotatedBitmap,5);
        filter.applyFilter();
        Bitmap bitmap1 = filter.generateBitmap();

        mPhotoCapture.setImageBitmap(bitmap1);
    }


    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(this, new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);

    }

}
