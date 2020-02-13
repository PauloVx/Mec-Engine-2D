package com.mec.engine;

import java.awt.image.DataBufferInt;

import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.Font;
import com.mec.engine.gfx.ImageTile;

public class Renderer
{
    private int pW, pH; //Pixel Width/Height
    private int[] p; //Pixels

    private Font font = Font.STD_FONT;

    public Renderer(GameContainer gc)
    {
        pW = gc.getWindowWidth();
        pH = gc.getWindowHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    /**Will clear the screen */
    public void clear()
    {
        for(int i = 0; i < p.length; i++)
        {
            p[i] = 0xff222222;
        }
    }

    /**
     * Will set a pixel to a given color.
     * @param x Pixel position X in screen.
     * @param y Pixel position Y in screen.
     * @param color Color in hex format.
     */
    public void setPixel(int x, int y, int color)
    {
        if( (x < 0 || x >= pW || y < 0 || y >= pH) || ((color >> 24) & 0xff ) == 0) return;
        
        p[x + y * pW] = color;
    }

    /**
     * Draws text to the screen.
     * @param text The text
     * @param offsetX Position in screen X.
     * @param offsetY Position in screen Y.
     * @param color Color in hex format.
     */
    public void drawText(String text, int offsetX, int offsetY, int color)
    {
        text = text.toUpperCase();
        int offset = 0;

        for(int i = 0; i < text.length(); i++)
        {
            int unicode = text.codePointAt(i) - 32;

            for(int y = 0; y < font.getFontImage().getHeight(); y++)
            {
                for(int x = 0 ; x < font.getWidths()[unicode]; x++)
                {
                    if(font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff)
                        setPixel(x + offsetX + offset, y + offsetY, color);
                }
            }

            offset += font.getWidths()[unicode];
        }
    }

    /**
     * Draws an image to the screen.
     * @param image The image.
     * @param offsetX Position in screen X.
     * @param offsetY Position in screen Y.
     */
    public void drawImage(Image image, int offsetX, int offsetY)
    {
        if(offsetX < -image.getWidth()) return;
        if(offsetY < -image.getHeight()) return;

        if(offsetX >= pW) return;
        if(offsetY >= pH) return;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

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

    /**
     * Draws a tile.
     * @param tile The tile.
     * @param offsetX Position in screen X.
     * @param offsetY Position in screen Y.
     * @param tileX Width of the tile.
     * @param tileY Height of the tile.
     */
    public void drawImageTile(ImageTile tile, int offsetX, int offsetY, int tileX, int tileY)
    {
        if(offsetX < -tile.getTileWidth()) return;
        if(offsetY < -tile.getTileHeight()) return;

        if(offsetX >= pW) return;
        if(offsetY >= pH) return;

        int newX = 0;
        int newY = 0;
        int newWidth = tile.getTileWidth();
        int newHeight = tile.getTileHeight();

        if (offsetX < 0) newX -= offsetX;
        if (offsetY < 0) newY -= offsetY;

        if(newWidth + offsetX >= pW) newWidth -= newWidth + offsetX - pW;
        if(newHeight + offsetY >= pH) newHeight -= newHeight + offsetY - pH;

        for(int y = newY; y < newHeight; y++)
        {
            for(int x = newX; x < newWidth; x++)
            {
                setPixel(x + offsetX, y + offsetY, tile.getPixels()[(x + tileX * tile.getTileWidth()) + (y + tileY * tile.getTileHeight()) * tile.getWidth()]);
            }
        }
    }

    /**
     * Draws a hollow rectangle
     * @param color Color in hex format.
     * @param offsetX Position in screen X.
     * @param offsetY Position in screen Y.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
    */
    public void drawRect(int color, int offsetX, int offsetY, int width, int height)
    {
        for(int y = 0; y <= height; y++)
        {
            setPixel(offsetX, y + offsetY, color);
            setPixel(offsetX + width, y + offsetY, color);

        }
        
        for(int x = 0; x <= width; x++)
        {
            setPixel(x + offsetX, offsetY, color);
            setPixel(x + offsetX, offsetY + height, color);
        }
    }

    /**
     * Draws a filled rectangle
     * @param color Color in hex format.
     * @param offsetX Position in screen X.
     * @param offsetY Position in screen Y.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
    */
    public void drawFilledRect(int color, int offsetX, int offsetY, int width, int height)
    {
        if(offsetX < -width) return;
        if(offsetY < -height) return;

        if(offsetX >= pW) return;
        if(offsetY >= pH) return;

        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        if (offsetX < 0) newX -= offsetX;
        if (offsetY < 0) newY -= offsetY;

        if(newWidth + offsetX >= pW) newWidth -= newWidth + offsetX - pW;
        if(newHeight + offsetY >= pH) newHeight -= newHeight + offsetY - pH;

        for(int y = newY; y <= newHeight; y++)
        {
            for(int x = newX; x <= newWidth; x++)
            {
                setPixel(x + offsetX, y + offsetY, color);
            }
        }
    }
}