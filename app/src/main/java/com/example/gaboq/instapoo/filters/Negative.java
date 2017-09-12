package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 23/8/2017.
 */

public class Negative extends Imagen {
    public Negative(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = pixels[i].getR()^255;
            g = pixels[i].getG()^255;
            b = pixels[i].getB()^255;
            pixels[i].setRGB(r,g,b);
            aux[i] = pixels[i].getValue();
        }
    }
}
