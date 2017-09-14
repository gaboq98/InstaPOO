package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by jd_cm on 18/8/2017.
 */

public class Imagen implements IFilter{


    int width;
    int height;
    int[] aux;
    long length;


    Imagen(){}

    public Imagen(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
        bitmap.getPixels(aux, 0, this.width, 0, 0, this.width, this.height);
        this.length = this.height*this.width;
    }

    @Override
    public Bitmap generateBitmap() {
        return Bitmap.createBitmap(this.aux,width,height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void applyFilter() {
    }

    @Override
    public Bitmap generatePreview(Bitmap bitmap, Canvas canvas) {
        return Bitmap.createScaledBitmap(bitmap,canvas.getWidth(),canvas.getHeight(),false);
    }


    protected int max(int n1, int n2, int n3){
        int temp = 0;
        int[] array = {n1,n2,n3};
        for (int i = 0; i <3 ; i++) {
            if(array[i]> temp){
                temp = array[i];
            }
        }
        return temp;
    }

    protected int min(int n1, int n2, int n3){
        int temp =255;
        int[] array = {n1,n2,n3};
        for (int i = 0; i <3 ; i++) {
            if(array[i] < temp){
                temp = array[i];
            }
        }
        return temp;
    }

    protected int checkValue(int value){
        if (value<0){
            value=0;
        }else if(value>255){
            value = 255;
        }
        return value;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }








}
