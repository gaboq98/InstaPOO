package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 27/8/2017.
 */

public class Saturation extends Imagen {
    public Saturation(Bitmap bitmap){
        super(bitmap);
        applyFilter();
    }

    @Override
    public void applyFilter() {
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = pixels[i].getR();
            g = pixels[i].getG();
            b = pixels[i].getB();
            pixels[i].setRGB(checkValue(r+max(r,g,b)-min(r,g,b)),
                                      checkValue(g+max(r,g,b)-min(r,g,b)),
                                      checkValue(b+max(r,g,b)-min(r,g,b)));
            aux[i] = pixels[i].getValue();
        }
    }

    protected int checkValue(int val){
        if (val>255){
            val = 255;
        }else if(val<0){
            val=0;
        }
        return val;
    }
}