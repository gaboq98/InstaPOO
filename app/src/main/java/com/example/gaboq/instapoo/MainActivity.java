package com.example.gaboq.instapoo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gaboq.instapoo.Cam.CamActivity;

import layout.GalleryFragment;
import layout.MainFragment;


public class MainActivity extends AbsRuntimePermission {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new MainFragment()).commit();
                    return true;

                case R.id.navigation_gallery:
                    transaction.replace(R.id.content, new GalleryFragment()).commit();
                    return true;

                case R.id.navigation_cam:
                    Intent intent = new Intent(MainActivity.this, CamActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA},
                R.string.msg,
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new MainFragment()).commit();

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }


}
