package com.example.gaboq.instapoo;


import android.graphics.Bitmap;
import com.example.gaboq.instapoo.filters.Averaging;
import com.example.gaboq.instapoo.filters.DecompositionMax;
import com.example.gaboq.instapoo.filters.DecompositionMin;
import com.example.gaboq.instapoo.filters.Desaturation;
import com.example.gaboq.instapoo.filters.IFilter;
import com.example.gaboq.instapoo.filters.Imagen;

/**
 * Created by Admin on 20/8/2017.
 */

public class MainFactory {

    public IFilter getFilter(Bitmap bitmap, int opcion){
        switch (opcion){
            case 1:
                return new Averaging(bitmap);
            case 2:
                return new Desaturation(bitmap);
            case 3:
                return new DecompositionMin(bitmap);
            case 4:
                return new DecompositionMax(bitmap);
            default:
                return new Imagen(bitmap);
        }
    }



}
