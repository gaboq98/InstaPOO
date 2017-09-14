package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Josu on 20/8/2017.
 */

public class Desaturation extends Imagen {

    public Desaturation(Bitmap bitmap){
        super(bitmap);
        applyFilter();
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = Color.red(aux[i]);
            g = Color.green(aux[i]);
            b = Color.blue(aux[i]);
            int desaturated = ( max(r,g,b) + min(r,g,b) )/2;
            aux[i] = Color.argb(255,desaturated,desaturated,desaturated);
        }
    }

}
