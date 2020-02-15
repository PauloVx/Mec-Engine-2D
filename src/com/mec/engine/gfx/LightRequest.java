package com.mec.engine.gfx;

public class LightRequest
{
    public Light light;
    public int posX;
    public int posY;

    public LightRequest(Light light, int posX, int posY)
    {
        this.light = light;
        this.posX = posX;
        this.posY = posY;
    }
}