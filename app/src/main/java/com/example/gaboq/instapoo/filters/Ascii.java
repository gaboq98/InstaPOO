package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by jd_cm on 11/9/2017.
 */

public class Ascii extends Imagen {

    String asciistr;
    byte[] byteArray;

    public Ascii(Bitmap bitmap){
        super(bitmap);
        byteArray = new byte[(int) length];
        applyFilter();
    }

    @Override
    public void applyFilter(){
        for (int i = 0; i < length ; i++) {
            byteArray[i] = (byte) ((Color.red(aux[i])+Color.green(aux[i])+Color.blue(aux[i]))/3);
        }
        asciistr = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public Bitmap generateBitmap() {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.drawARGB(0, Color.WHITE,Color.WHITE,Color.WHITE);
        c.drawBitmap(b, 0, 0, null);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(1);
        StaticLayout sl= new StaticLayout(asciistr, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1.0f , 0.0f, false);
        c.translate(0,0);
        sl.draw(c);
        return b;
    }
}
