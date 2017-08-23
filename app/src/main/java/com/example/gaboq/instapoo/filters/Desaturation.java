package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;

/**
 * Created by Josu on 20/8/2017.
 */

public class Desaturation extends Imagen {

    public Desaturation(Bitmap bitmap){
        Imagen(bitmap);
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            byte r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int desaturate = ( max(r,g,b)+ min(r,g,b) )/2;
            desaturate = (byte) desaturate;
            pixels[i].setRGB(desaturate,desaturate,desaturate);
            aux[i] = pixels[i].getValue();
        }
    }

}
