package com.mec.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image 
{
    private int width, height;
    private int lightBlock = Light.NONE;

    private int[] pixels;

    private boolean alpha = false;

    public Image(String path) 
    {
        BufferedImage image = null;
        try 
        {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        image.flush();
    }

    public Image(int[] pixels, int width, int height)
    {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return this.width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return this.height; }
    public void setHeight(int height) { this.height = height; }

    public int[] getPixels() { return this.pixels; }
    public void setPixels(int[] pixels) { this.pixels = pixels; }

    public boolean isAlpha() {return this.alpha;}
    public void setAlpha(boolean alpha) {this.alpha = alpha;}

    public int getLightBlock() {return this.lightBlock;}
    public void setLightBlock(int lightBlock) {this.lightBlock = lightBlock;}
}