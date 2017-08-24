package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;

/**
 * Created by Josu on 20/8/2017.
 */

public class Desaturation extends Imagen {

    public Desaturation(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
        this.pixels = new Pixel[this.height*this.width];
        bitmap.getPixels(aux, 0, this.width, 0, 0, this.width, this.height);
        this.length = this.height*this.width;
        fillpixels();
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            byte r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            byte desaturate = (byte) (( max(r,g,b)+ min(r,g,b) )/2);
            pixels[i].setRGB(desaturate,desaturate,desaturate);
            aux[i] = pixels[i].getValue();
        }
    }

}
