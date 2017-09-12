package com.example.gaboq.instapoo;


import android.content.Context;
import android.graphics.Bitmap;

import com.example.gaboq.instapoo.filters.Ascii;
import com.example.gaboq.instapoo.filters.Averaging;
import com.example.gaboq.instapoo.filters.DecompositionMax;
import com.example.gaboq.instapoo.filters.DecompositionMin;
import com.example.gaboq.instapoo.filters.Desaturation;
import com.example.gaboq.instapoo.filters.GaussianBlur;
import com.example.gaboq.instapoo.filters.IFilter;
import com.example.gaboq.instapoo.filters.Imagen;
import com.example.gaboq.instapoo.filters.Negative;
import com.example.gaboq.instapoo.filters.Sepia;

/**
 * Created by josu on 20/8/2017.
 */

public class MainFactory {

    public IFilter getInstance(Bitmap bitmap, int opcion, Context context){
        switch (opcion){
            case 0:
                return new Imagen(bitmap);
            case 1:
                return new Averaging(bitmap);
            case 2:
                return new Desaturation(bitmap);
            case 3:
                return new DecompositionMin(bitmap);
            case 4:
                return new DecompositionMax(bitmap);
            case 5:
                return new Sepia(bitmap);
            case 6:
                return new Negative(bitmap);
            case 7:
                return new GaussianBlur(bitmap,context);
            case 8:
                return new Ascii(bitmap);
            default:
                return new Imagen(bitmap);
        }
    }



}
