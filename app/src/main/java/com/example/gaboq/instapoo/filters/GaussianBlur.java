package com.example.gaboq.instapoo.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * Created by jd_cm on 22/8/2017.
 */

public class GaussianBlur extends Imagen {

    private static float BITMAP_SCALE =0.4f;
    private static float BLUR_RADIUS = 7.4f;
    private Context context;
    private Bitmap bitmap;


    public GaussianBlur(Bitmap bitmap, Context context){
        this.width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
        this.height = Math.round(bitmap.getHeight() * BITMAP_SCALE);
        this.bitmap = bitmap;
        this.context = context;
    }

    @Override
    public void applyFilter() {
    }

    @Override
    public Bitmap generateBitmap(){
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
}
