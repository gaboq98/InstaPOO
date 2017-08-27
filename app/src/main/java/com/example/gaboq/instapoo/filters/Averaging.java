package com.example.gaboq.instapoo.filters;


import android.graphics.Bitmap;

/**
 * Created by Josue Canales on 20/8/2017.
 */

public class Averaging extends Imagen {

    public Averaging(Bitmap bitmap){
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
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int average = (r+g+b)/3;
            pixels[i].setRGB(average,average,average);
            aux[i] = pixels[i].getValue();
        }
    }


}
