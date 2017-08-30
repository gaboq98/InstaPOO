package com.example.gaboq.instapoo;


import android.content.Context;
import android.graphics.Bitmap;
import com.example.gaboq.instapoo.filters.Averaging;
import com.example.gaboq.instapoo.filters.DecompositionMax;
import com.example.gaboq.instapoo.filters.DecompositionMin;
import com.example.gaboq.instapoo.filters.Desaturation;
import com.example.gaboq.instapoo.filters.GaussianBlur;
import com.example.gaboq.instapoo.filters.IFilter;
import com.example.gaboq.instapoo.filters.Imagen;
import com.example.gaboq.instapoo.filters.Negative;
import com.example.gaboq.instapoo.filters.Saturation;
import com.example.gaboq.instapoo.filters.Sepia;

/**
 * Created by josu on 20/8/2017.
 */

public class MainFactory {
    final static int ORIGINAL_IMAGE = 0;
    final static int AVERAGING_IMAGE = 1;
    final static int DESATURATION_IMAGE = 2;
    final static int DECOMPOSITION_MIN_IMAGE = 3;
    final static int DECOMPOSITION_MAX_IMAGE = 4;
    final static int SEPIA_IMAGE = 5;    
    final static int NEGATIVE_IMAGE  = 6;
    final static int GAUSSIAN_BLUR = 7;
    final static int SATURATION_IMAGE = 8;

    public IFilter getInstance(Bitmap bitmap, int opcion, Context context){
        switch (opcion){
            case ORIGINAL_IMAGE:
                return new Imagen(bitmap);
            case AVERAGING_IMAGE:
                return new Averaging(bitmap);
            case DESATURATION_IMAGE:
                return new Desaturation(bitmap);
            case DECOMPOSITION_MIN_IMAGE:
                return new DecompositionMin(bitmap);
            case DECOMPOSITION_MAX_IMAGE:
                return new DecompositionMax(bitmap);
            case SEPIA_IMAGE:
                return new Sepia(bitmap);
            case NEGATIVE_IMAGE:
                return new Negative(bitmap);
            case GAUSSIAN_BLUR:
                return new GaussianBlur(bitmap,context);
            case SATURATION_IMAGE:
                return new Saturation(bitmap);
            default:
                return new Imagen(bitmap);
        }
    }



}
