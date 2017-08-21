package com.example.gaboq.instapoo.filters;


/**
 * Created by Admin on 20/8/2017.
 */

public class Desaturation extends Imagen {

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
        }
    }

}
