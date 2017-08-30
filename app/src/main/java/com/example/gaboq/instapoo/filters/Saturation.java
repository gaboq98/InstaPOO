package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 27/8/2017.
 */

public class Saturation extends Imagen {
    public Saturation(Bitmap bitmap){
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
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int c = min(r,g,b);
            if (c > 90){
                c = max(r,g,b);
            }
            pixels[i].setRGB(c,c,c);
            //pixels[i].setRGB(checkValue(r+max(r,g,b)-min(r,g,b)),
            //                          checkValue(g+max(r,g,b)-min(r,g,b)),
            //                         checkValue(b+max(r,g,b)-min(r,g,b)));
            aux[i] = pixels[i].getValue();
        }
    }

}