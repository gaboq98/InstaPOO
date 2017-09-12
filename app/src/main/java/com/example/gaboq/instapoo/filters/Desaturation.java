package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;

/**
 * Created by Josu on 20/8/2017.
 */

public class Desaturation extends Imagen {

    public Desaturation(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int desaturated = ( max(r,g,b) + min(r,g,b) )/2;
            pixels[i].setRGB(desaturated,desaturated,desaturated);
            aux[i] = pixels[i].getValue();
        }
    }

}
