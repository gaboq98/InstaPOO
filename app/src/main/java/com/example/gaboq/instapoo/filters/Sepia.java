package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

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
            int a,r,g,b;
            r = Color.red(aux[i]);
            g = Color.green(aux[i]);
            b = Color.blue(aux[i]);
            int R = (int) (r* .393 + g* .769 + b* .189);
            int G = (int) (r* .349 + g* .686 + b* .168);
            int B = (int) (r* .272 + g* .534 + b* .131);
            R = checkMaximum(R);
            G = checkMaximum(G);
            B = checkMaximum(B);
            aux[i] = Color.argb(255,R,G,B);
        }
    }
    private int checkMaximum(int val){
        if (val>255){
            val = 255;
        }
        return val;
    }

}
