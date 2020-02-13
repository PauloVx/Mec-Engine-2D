package com.mec.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageRequest;
import com.mec.engine.gfx.Font;
import com.mec.engine.gfx.ImageTile;

public class Renderer
{
    private Font font = Font.STD_FONT;
    private ArrayList<ImageRequest> imageRequests = new ArrayList<ImageRequest>();

    private int pW, pH; //Pixel Width/Height
    private int zDepth = 0;

    private boolean processing = false;

    private int[] p; //Pixels
    private int[] zBuffer;

    public Renderer(GameContainer gc)
    {
        pW = gc.getWindowWidth();
        pH = gc.getWindowHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[p.length];
    }

    /**Will clear the screen */
    public void clear()
    {
        for(int i = 0; i < p.length; i++)
        {
            p[i] = 0xff222222;
            zBuffer[i] = 0;
        }
    }

    public void process()
    {
        processing = true;

        Collections.sort(imageRequests, new Comparator<ImageRequest>(){

            @Override
            public int compare(ImageRequest i0, ImageRequest i1) {
                if (i0.zDepth < i1.zDepth) return -1;
                if (i0.zDepth > i1.zDepth) return 1;

                return 0;
            }

        });

        for(int i = 0; i < imageRequests.size(); i++) 
        {
            ImageRequest imageRequest = imageRequests.get(i);
            setZDepth(imageRequest.zDepth);
            drawImage(imageRequest.image, imageRequest.offsetX, imageRequest.offsetY);
        }

        imageRequests.clear();
        processing = false;
    }

    /**
     * Will set a pixel to a given color.
     * @param x Pixel position X in screen.
     * @param y Pixel position Y in screen.
     * @param color Color in hex format.
     */
    public void setPixel(int x, int y, int color)
    {
        int alpha = ((color >> 24) & 0xff);

        if( (x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) return;

        int index = x + y * pW;
        
        if(zBuffer[index]  > zDepth) return;

        zBuffer[index] = zDepth;

        if(alpha == 0xff) p[index] = color;
        else
        {
            int pixelColor = p[index];

            int newRed = ((pixelColor >> 16) & 0xff) - (int)(((pixelColor >> 16) & 0xff - ((color >> 16) & 0xff)) * (alpha / 255f));
            int newGreen = ((pixelColor >> 8) & 0xff) - (int)(((pixelColor >> 8) & 0xff - ((color >> 8) & 0xff)) * (alpha / 255f));
            int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (color & 0xff)) * (alpha / 255f));

            p[index] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }
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
        if(image.isAlpha() && !processing)
        {
            imageRequests.add(new ImageRequest(image, zDepth, offsetX, offsetY));
            return;
        }

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
        if(tile.isAlpha() && !processing)
        {
            imageRequests.add(new ImageRequest(tile.getTileImage(tileX, tileY), zDepth, offsetX, offsetY));
            return;
        }

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

        for(int y = newY; y < newHeight; y++)
        {
            for(int x = newX; x < newWidth; x++)
            {
                setPixel(x + offsetX, y + offsetY, color);
            }
        }
    }

    public int getZDepth() {return this.zDepth;}
    public void setZDepth(int zDepth) {this.zDepth = zDepth;}
}