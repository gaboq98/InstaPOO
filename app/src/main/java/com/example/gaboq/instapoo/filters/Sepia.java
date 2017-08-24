package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 23/8/2017.
 */

public class Sepia extends Imagen {

    public Sepia(Bitmap bitmap){
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
            int r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            r = (int) Math.floor((r* .393) + (g* .769) + (b* .189));
            g = (int) Math.floor((r* .349) + (g* .686) + (b* .168));
            b = (int) Math.floor((r* .272) + (g* .534) + (b* .131));
            pixels[i].setRGB(r,g,b);
            aux[i] = pixels[i].getValue();
        }
    }

}
