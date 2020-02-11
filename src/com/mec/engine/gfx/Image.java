package com.mec.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image 
{
    private int width, height;
    private int[] pixels;

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

    public int getWidth() { return this.width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return this.height; }
    public void setHeight(int height) { this.height = height; }

    public int[] getPixels() { return this.pixels; }
    public void setPixels(int[] pixels) { this.pixels = pixels; }
}