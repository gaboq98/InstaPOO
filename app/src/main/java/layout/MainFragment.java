package layout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gaboq.instapoo.FilterView.FilterViewActivity;
import com.example.gaboq.instapoo.R;

import java.io.File;
import java.util.ArrayList;



public class MainFragment extends GalleryFragment {
    GridView gv;
    ArrayList<File> list;
    public MainFragment() {
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){

            File dir = new File(String.valueOf(Environment.getExternalStorageDirectory().toString()
                    + "//Android/data/com.example.gaboq.instapoo/files/Pictures/"));

            list = imageReader(dir);

            gv = (GridView) v.findViewById(R.id.homeGridView);
            gv.setAdapter(new GridAdapterFragment());
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Intent intent = new Intent(getActivity(), FilterViewActivity.class).putExtra("img",list.get(position).toString());
                    startActivity(intent);

                }
            });
        }
        return v;
    }

    class GridAdapterFragment extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getActivity().getLayoutInflater().inflate(R.layout.photo_image, parent ,false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
            String dir = getItem(position).toString();

            int ivWidth = 330;
            int ivHeigth = 330;
            BitmapFactory.Options bitmapp = new BitmapFactory.Options();
            bitmapp.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(dir, bitmapp);
            int cameraImageWidth = bitmapp.outWidth;
            int cameraImageHeight = bitmapp.outHeight;
            bitmapp.inSampleSize = Math.min(cameraImageWidth/ivWidth, cameraImageHeight/ivHeigth);
            bitmapp.inJustDecodeBounds = false;
            Bitmap bitmap= BitmapFactory.decodeFile(dir, bitmapp);

            bitmap = resize(bitmap, 1080, 1080);

            iv.setImageBitmap(bitmap);

            return convertView;
        }
    }


    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
