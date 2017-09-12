package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 23/8/2017.
 */

public class Sepia extends Imagen {

    public Sepia(Bitmap bitmap){
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
            int R = (int) (r* .393 + g* .769 + b* .189);
            int G = (int) (r* .349 + g* .686 + b* .168);
            int B = (int) (r* .272 + g* .534 + b* .131);
            R = checkMaximum(R);
            G = checkMaximum(G);
            B = checkMaximum(B);
            pixels[i].setRGB(R,G,B);
            aux[i] = pixels[i].getValue();
        }
    }
    private int checkMaximum(int val){
        if (val>255){
            val = 255;
        }
        return val;
    }

}
