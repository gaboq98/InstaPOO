package com.example.gaboq.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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

    Imagen(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
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
        return Bitmap.createBitmap(aux,width,height, Bitmap.Config.RGB_565);
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
        byte r;
        byte g;
        byte b;

        public void setRGB(int r, int g, int b){
            this.r = (byte) r;
            this.g = (byte) g;
            this.b = (byte) b;
        }

        public byte getR() {return r;}

        public byte getG() {return g;}

        public byte getB() {return b;}

        public int getValue( byte r, byte g, byte b){
            return (r & 0xff) << 16 | (g & 0xff) << 16 | (b & 0xff);
        }

        Pixel(int color){
            this.r = (byte) ((color >> 16) & 0xff);
            this.g = (byte) ((color >>  8) & 0xff);
            this.b = (byte) (color & 0xff);
        }
    }

    protected byte max(byte n1, byte n2, byte n3){
        byte temp = 0;
        byte[] array = {n1,n2,n3};
        for (int i = 0; i <3 ; i++) {
            if(array[i]> temp){
                temp = array[i];
            }
        }
        return temp;
    }

    protected byte min(byte n1, byte n2, byte n3){
        byte temp = 0;
        byte[] array = {n1,n2,n3};
        for (int i = 0; i <3 ; i++) {
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

    public Pixel[] getPixels() {
        return pixels;
    }

    public int[] getAux() {
        return aux;
    }

    public long getLength() {
        return length;
    }
}
