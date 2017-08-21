package com.example.gaboq.instapoo.filters;


/**
 * Created by Josue Canales on 20/8/2017.
 */

public class Averaging extends Imagen {


    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            byte r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            int average = (r+g+b)/3;
            pixels[i].setRGB(average,average,average);
        }
    }


}
