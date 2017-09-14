package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Admin on 20/8/2017.
 */

public class DecompositionMax extends Imagen {

    public DecompositionMax(Bitmap bitmap){
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
            int maxValue = max(r,g,b);
            aux[i] = Color.argb(255,maxValue,maxValue,maxValue);
        }
    }
}
