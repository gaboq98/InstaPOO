package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by gaboq on 13/9/2017.
 */

public class CRFilter extends Imagen {

    public CRFilter(Bitmap bitmap){
        super(bitmap);
        applyFilter();
    }

    @Override
    public void applyFilter() {
        int segmento = (int) (this.length/6);
        for (int i = 0; i <this.length; i++) {
            int r,g,b;
            r = Color.red(aux[i]);
            g = Color.green(aux[i]);
            b = Color.blue(aux[i]);
            if (i < segmento){
                aux[i] = Color.argb(255,checkValue(r-100),checkValue(g-50),b);
            }else if (i>= segmento && i< 2*segmento){
                aux[i] = Color.argb(255,checkValue(r+100),checkValue(g+100),checkValue(b+100));
            }else if (i>= 2*segmento && i< 4*segmento){
                aux[i] = Color.argb(255,r,checkValue(g-100),checkValue(b-100));
            }else if (i>= 4*segmento && i >5*segmento){
                aux[i] = Color.argb(255,checkValue(r-100),checkValue(g-50),b);
            }else{
                aux[i] = Color.argb(255,checkValue(r+100),checkValue(g+100),checkValue(b+100));
            }
        }
    }



}

