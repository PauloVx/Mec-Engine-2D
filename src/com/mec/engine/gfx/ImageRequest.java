package com.mec.engine.gfx;

public class ImageRequest
{
    public Image image;
    public int zDepth;
    public int offsetX;
    public int offsetY;

    public ImageRequest(Image image, int zDepth, int offsetX, int offsetY)
    {
        this.image = image;
        this.zDepth = zDepth;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}