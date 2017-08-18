package com.example.gaboq.instapoo;

import android.graphics.Bitmap;

/**
 * Created by jd_cm on 18/8/2017.
 */

public class Imagen {

    private class Pixel{
        byte r;
        byte g;
        byte b;

        protected void setRGB(byte r, byte g, byte b){
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public byte getR() {
            return r;
        }

        public byte getG() {
            return g;
        }

        public byte getB() {
            return b;
        }

        Pixel(int color){
            this.r = (byte) ((color >> 16) & 0xff);
            this.g = (byte) ((color >>  8) & 0xff);
            this.b = (byte) (color & 0xff);


        }
    }

    int width;
    int height;
    Pixel[] pixels;
    int[] aux;
    long length;

    Imagen(Bitmap bitmap){
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.aux = new int[this.height*this.width];
        bitmap.getPixels(aux, 0, this.width, 0, 0, this.width, this.height);
        this.length = this.height*this.width;
        fillpixels();
    }

    private void fillpixels(){
        for(int i =0; i < this.length; i++ ){
            pixels[i] = new Pixel(aux[i]);
        }
    }
}
