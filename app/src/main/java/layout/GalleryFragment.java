package layout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
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

import static android.os.Environment.DIRECTORY_DCIM;

public class GalleryFragment extends Fragment {
    GridView gv;
    ArrayList<File> list;
    private OnFragmentInteractionListener mListener;
    public GalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){

            list = imageReader(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM));

            gv = (GridView) v.findViewById(R.id.gridView);
            gv.setAdapter(new GridAdapter());
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), FilterViewActivity.class).putExtra("img",list.get(position).toString());
                    startActivity(intent);
                }
            });
        }
        return v;
    }

    class GridAdapter extends BaseAdapter {

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
            convertView = getActivity().getLayoutInflater().inflate(R.layout.single_grid, parent ,false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView2);
            iv.setImageURI(Uri.parse(getItem(position).toString()));
            return convertView;
        }
    }


    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();

        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                a.addAll(imageReader(files[i]));
            }else{
                if(files[i].getName().endsWith(".jpg")){
                    a.add(files[i]);
                }
            }
        }
        return a;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            OnFragmentInteractionListener mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Gallery", Toast.LENGTH_SHORT).show();
        }
    }
}
