package com.example.gaboq.instapoo.filters;

/**
 * Created by Admin on 20/8/2017.
 */

public class DecompositionMin extends Imagen {

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            byte r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int minValue = min(r,g,b);
            pixels[i].setRGB(minValue,minValue,minValue);
        }
    }

}
