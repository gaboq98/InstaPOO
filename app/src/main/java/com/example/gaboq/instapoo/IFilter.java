package com.example.gaboq.instapoo;

import android.graphics.Bitmap;

/**
 * Created by Admin on 20/8/2017.
 */

public interface IFilter {
    public Bitmap generateBitmap();
    public void applyFilter();
    public Bitmap generatePreview();
}
