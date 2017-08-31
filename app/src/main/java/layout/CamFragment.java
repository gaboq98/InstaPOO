package layout;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gaboq.instapoo.FilterView.FilterViewActivity;
import com.example.gaboq.instapoo.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class CamFragment extends Fragment {

    private ImageView mPhotoCapture;

    private static final int START_CAMERA_APP = 0;

    public Bitmap mBitmap;

    private String mImageLocation = "";

    public static final int SELECT_PHOTO_ACTION = 0;


    public CamFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        SELECT_PHOTO_ACTION);
            }
        }
        View v = inflater.inflate(R.layout.activity_cam, container, false);
        mPhotoCapture = (ImageView) v.findViewById(R.id.photoCaptureImageView);
        takePhoto(v);
        return v;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            mBitmap = Bitmap.createScaledBitmap(reduceImage(), 800, 600, true);
            rotateImage(mBitmap);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void takePhoto(View view) {

        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCamera.resolveActivity(getActivity().getPackageManager()) != null) {
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

        File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

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
        Intent intent = new Intent(getActivity(),FilterViewActivity.class).putExtra("img",mImageLocation);
        startActivity(intent);

    }


    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.getActivity().sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(getActivity(), new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);

    }







}
