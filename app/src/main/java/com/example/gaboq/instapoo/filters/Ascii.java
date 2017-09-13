package com.example.gaboq.instapoo.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;
import static layout.MainFragment.resize;


/*
 * Created by jd_cm on 11/9/2017.
 */

public class Ascii extends Imagen {

    private String asciistr;
    private char[] byteArray;
    private Context context;

    public Ascii(Bitmap bitmap, Context context){
        super(resize(bitmap, 255, 255));
        this.context = context;
        byteArray = new char[(int) length];
        applyFilter();
    }

    @Override
    public void applyFilter(){
        for (int i = 0; i < length ; i++) {
            byteArray[i] = toAscii(Color.red(aux[i]));

        }
        asciistr = new String(byteArray);
    }

    @Override
    public Bitmap generateBitmap() {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        c.drawARGB(0, Color.WHITE,Color.WHITE,Color.WHITE);
        c.drawBitmap(b, 0, 0, null);
        TextPaint textPaint = new TextPaint();
        //textPaint.density = -10;
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(1);
        StaticLayout sl= new StaticLayout(asciistr, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1.0f , 0.0f, false);
        c.translate(0,0);
        sl.draw(c);
        saveText();
        return b;
    }


    private void saveText() {

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "ASCII" + time;
        File file = new File("/data/user/0/com.example.gaboq.instapoo/files/Ascii/", filename + ".txt");

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(asciistr.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private char toAscii(double g)
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str;

    }
}
