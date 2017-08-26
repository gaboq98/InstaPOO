package com.example.gaboq.instapoo.FilterView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.gaboq.instapoo.MainFactory;
import com.example.gaboq.instapoo.filters.IFilter;

/*
 * Created by gaboq on 24/8/2017.
 */

public class CustomGalleryAdapter extends BaseAdapter {


    private Context context;
    private int[] images;

    public CustomGalleryAdapter(Context c, int[] images) {
        context = c;
        this.images = images;
    }

    // returns the number of images
    public int getCount() {
        return images.length;
    }

    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }

    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]); // set image in ImageView
        //////
        //Drawable d = imageView.getResources().getDrawable(images[position]);
        //Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        //bitmap = Bitmap.createScaledBitmap(bitmap, 800, 533, true);
        //MainFactory mFactory = new MainFactory();
        //IFilter f = mFactory.getInstance(bitmap, position);
        //f.applyFilter();
        //bitmap = f.generateBitmap();
        //imageView.setImageBitmap(bitmap);
        //////
        imageView.setLayoutParams(new Gallery.LayoutParams(200, 200)); // set ImageView param
        return imageView;
    }



}
