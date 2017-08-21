package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Admin on 20/8/2017.
 */

public interface IFilter {
    //hola jeje
    public Bitmap generateBitmap();
    public void applyFilter();
    public Bitmap generatePreview(Bitmap bitmap, Canvas canvas);
}
