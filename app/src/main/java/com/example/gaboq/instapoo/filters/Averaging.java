package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Josue Canales on 20/8/2017.
 */

public class Averaging extends Imagen {

    public Averaging(Bitmap bitmap){
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
            int average = (r+g+b)/3;
            aux[i] = Color.argb(255,average,average,average);
        }
    }


}
