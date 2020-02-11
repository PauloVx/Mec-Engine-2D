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
        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        if(offsetX < -newWidth) return;
        if(offsetY < -newHeight) return;

        if(offsetX >= pW) return;
        if(offsetY >= pH) return;

        if (offsetX < 0) newX -= offsetX;
        if (offsetY < 0) newY -= offsetY;

        if(newWidth + offsetX >= pW) newWidth -= newWidth + offsetX - pW;
        if(newHeight + offsetY >= pH) newHeight -= newHeight + offsetY - pH;

        for(int y = newY; y < newHeight; y++)
        {
            for(int x = newX; x < newWidth; x++)
            {
                setPixel(x + offsetX, y + offsetY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }
}