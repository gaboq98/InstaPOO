package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by Admin on 20/8/2017.
 */

public class DecompositionMin extends Imagen {

    public DecompositionMin(Bitmap bitmap){
        Imagen(bitmap);
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            byte r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int minValue = min(r,g,b);
            pixels[i].setRGB(minValue,minValue,minValue);
            aux[i] = pixels[i].getValue();
        }
    }

}
