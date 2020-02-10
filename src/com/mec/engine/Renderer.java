package com.mec.engine;

import java.awt.image.DataBufferInt;

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
}