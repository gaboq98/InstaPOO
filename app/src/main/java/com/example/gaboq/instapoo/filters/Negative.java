package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by jd_cm on 23/8/2017.
 */

public class Negative extends Imagen {
    public Negative(Bitmap bitmap){
        super(bitmap);
        applyFilter();
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = Color.red(aux[i])^255;
            g = Color.green(aux[i])^255;
            b = Color.blue(aux[i])^255;
            aux[i] = Color.argb(255,r,g,b);
        }
    }
}
