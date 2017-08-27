package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 23/8/2017.
 */

public class Negative extends Imagen {
    public Negative(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
        this.pixels = new Pixel[this.height*this.width];
        bitmap.getPixels(aux, 0, this.width, 0, 0, this.width, this.height);
        this.length = this.height*this.width;
        fillpixels();
        applyFilter();
    }

    @Override
    private void applyFilter() {
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
