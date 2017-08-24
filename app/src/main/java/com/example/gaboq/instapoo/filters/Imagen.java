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
    Pixel[] pixels;
    int[] aux;
    long length;


    Imagen(){}

    public Imagen(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
        this.pixels = new Pixel[this.height*this.width];
        bitmap.getPixels(aux, 0, this.width, 0, 0, this.width, this.height);
        this.length = this.height*this.width;
        fillpixels();
    }

    protected void fillpixels(){
        for(int i =0; i < this.length; i++ ){
            pixels[i] = new Pixel(aux[i]);
        }
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
    
    
    /*
        //function to Filter Class //
    public Bitmap generateBitmap(int[] aux, int width, int height){
        return Bitmap.createBitmap(aux,width,height,RGB_565);
    }
    */

    protected class Pixel{
        byte a;
        byte r;
        byte g;
        byte b;

        public void setRGB(byte r, byte g, byte b){
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public byte getR() {return r;}

        public byte getG() {return g;}

        public byte getB() {return b;}

        @ColorInt
        public int getValue(){
            return Color.argb(a,r,g,b);
        }

        Pixel(int color){
            this.a = (byte) Color.alpha(color);
            this.r = (byte) Color.red(color);
            this.g = (byte) Color.green(color);
            this.b = (byte) Color.blue(color);
        }
    }

    protected byte max(byte n1, byte n2, byte n3){
        byte temp = 0;
        byte[] array = {n1,n2,n3};
        for (byte i = 0; i <3 ; i++) {
            if(array[i]> temp){
                temp = array[i];
            }
        }
        return temp;
    }

    protected byte min(byte n1, byte n2, byte n3){
        byte temp = 0;
        byte[] array = {n1,n2,n3};
        for (byte i = 0; i <3 ; i++) {
            if(array[i] < temp){
                temp = array[i];
            }
        }
        return temp;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
