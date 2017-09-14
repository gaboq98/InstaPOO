package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Admin on 20/8/2017.
 */

public class DecompositionMin extends Imagen {

    public DecompositionMin(Bitmap bitmap){
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
            int minValue = min(r,g,b);
            aux[i] = Color.argb(255,minValue,minValue,minValue);
        }
    }

}
