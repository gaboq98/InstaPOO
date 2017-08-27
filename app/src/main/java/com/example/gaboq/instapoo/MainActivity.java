package com.example.gaboq.instapoo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.gaboq.instapoo.Cam.CamActivity;
import com.example.gaboq.instapoo.FilterView.FilterViewActivity;

import layout.GalleryFragment;
import layout.MainFragment;


public class MainActivity extends AppCompatActivity {

    public static final int SELECT_PHOTO_ACTION = 0;

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
                    //transaction.replace(R.id.content, new GalleryFragment()).commit();
                    Bitmap mBitmap = null;
                    Drawable d = getResources().getDrawable(R.drawable.flores);
                    mBitmap = ((BitmapDrawable)d).getBitmap();
                    mBitmap = Bitmap.createScaledBitmap(mBitmap, 640, 480, true);
                    Intent intent0 = new Intent(MainActivity.this, FilterViewActivity.class);
                    //intent0.putExtra("Bitmap", mBitmap);
                    startActivity(intent0);
                    return true;

                case R.id.navigation_cam:
                    //Aqui va la camara
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new MainFragment()).commit();

    }


}
