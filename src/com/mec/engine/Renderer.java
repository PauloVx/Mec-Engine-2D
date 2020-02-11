package com.mec.engine;

import java.awt.image.DataBufferInt;

import com.mec.engine.gfx.Image;

public class Renderer
{
    private int pW, pH; //Pixel Width/Height
    private int[] p; //Pixels


    public Renderer(GameContainer gc)
    {
        pW = gc.getWindowWidth();
        pH = gc.getWindowHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear()
    {
        for(int i = 0; i < p.length; i++)
        {
            p[i] = 0xff222222;
        }
    }

    public void setPixel(int x, int y, int value)
    {
        if( (x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff ) return;
        
        p[x + y * pW] = value;
    }

    public void drawImage(Image image, int offsetX, int offsetY)
    {
        for(int y = 0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                setPixel(x + offsetX, y + offsetY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }
}