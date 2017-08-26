package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Josu on 20/8/2017.
 */

public interface IFilter {
    Bitmap generateBitmap();
    void applyFilter();
    Bitmap generatePreview(Bitmap bitmap, Canvas canvas);
}
