package com.mec.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageRequest;
import com.mec.engine.gfx.Font;
import com.mec.engine.gfx.ImageTile;
import com.mec.engine.gfx.Light;
import com.mec.engine.gfx.LightRequest;

public class Renderer
{
    private Font font = Font.COMICSANS_FONT; //Set the font

    private ArrayList<ImageRequest> imageRequests = new ArrayList<ImageRequest>();
    private ArrayList<LightRequest> lightRequests = new ArrayList<LightRequest>();

    private int pW, pH; //Pixel Width/Height
    private int ambientColor = Colors.WHITE;
    private int zDepth = 0;

    private boolean processing = false;

    private int[] p; //Pixels
    private int[] zBuffer;

    private int[] lightMap;
    private int[] lightBlock;

    private int cameraX;
    private int cameraY;

    public Renderer(GameContainer gc)
    {
        pW = gc.getWindowWidth();
        pH = gc.getWindowHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[p.length];
        lightMap = new int[p.length];
        lightBlock = new int[p.length];
    }

    /**Will clear the screen */
    public void clear()
    {
        for(int i = 0; i < p.length; i++)
        {
            p[i] = 0xff222222;
            zBuffer[i] = 0;

            lightMap[i] = ambientColor;
            lightBlock[i] = 0;
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

        //Draw lighting
        for(int i = 0; i < lightRequests.size(); i++)
        {
            LightRequest lr = lightRequests.get(i);
            drawLightRequest(lr.light, lr.posX, lr.posY);
        }

        for (int i = 0 ; i < p.length; i++)
        {
           float red = ((lightMap[i] >> 16) & 0xff) / 255f;
           float green = ((lightMap[i] >> 8) & 0xff) / 255f;
           float blue = (lightMap[i] & 0xff) / 255f;

           p[i] = ((int)(((p[i] >> 16) & 0xff) * red) << 16 | (int)(((p[i] >> 8) & 0xff) * green) << 8 | (int)((p[i] & 0xff) * blue));
        }

        imageRequests.clear();
        lightRequests.clear();
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

        x -= cameraX;
        y -= cameraY;

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

            p[index] = (newRed << 16 | newGreen << 8 | newBlue);
        }
    }

    /**
     * Placeholder Comment
     * @param x
     * @param y
     * @param value
     */
    public void setLightMap(int x, int y, int value)
    {
        x -= cameraX;
        y -= cameraY;

        if(x < 0 || x >= pW || y < 0 || y >= pH) return;

        int index = x + y * pW;
        int baseColor = lightMap[index];

        int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
        int maxGreen = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
        int maxBlue = Math.max(baseColor & 0xff, value & 0xff);

        lightMap[index] = (maxRed << 16 | maxGreen << 8 | maxBlue);
    }

    /**
     * Placeholder Comment
     * @param x
     * @param y
     * @param value
     */
    public void setLightBlock(int x, int y, int value)
    {
        if(x < 0 || x >= pW || y < 0 || y >= pH) return;
        int index = x + y * pW;
        if(zBuffer[index]  > zDepth) return;
        lightBlock[index] = value;
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
        int offset = 0;

        for(int i = 0; i < text.length(); i++)
        {
            int unicode = text.codePointAt(i);

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
        offsetX -= cameraX;
        offsetY -= cameraY;

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
                setLightBlock(x + offsetX, y + offsetY, image.getLightBlock());
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
        offsetX -= cameraX;
        offsetY -= cameraY;

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
                setLightBlock(x + offsetX, y + offsetY, tile.getLightBlock());
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
        offsetX -= cameraX;
        offsetY -= cameraY;

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
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                setPixel(x + offsetX, y + offsetY, color);
            }
        }
    }

    public void drawLight(Light light, int offsetX, int offsetY)
    {
        lightRequests.add(new LightRequest(light, offsetX, offsetY));
    }

    /**Internal use only */
    private void drawLightRequest(Light light, int offsetX, int offsetY)
    {
        offsetX -= cameraX;
        offsetY -= cameraY;

        for(int i = 0; i <= light.getDiameter(); i++)
        {
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offsetX, offsetY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offsetX, offsetY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offsetX, offsetY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offsetX, offsetY);
        }
    }

    private void drawLightLine(Light light, int x0, int y0, int x1, int y1, int offsetX, int offsetY)
    {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int err2;

        while(true)
        {
            int screenX = x0 - light.getRadius() + offsetX;
            int screenY = y0 - light.getRadius() + offsetY;

            if(screenX < 0 || screenX >= pW || screenY < 0 || screenY >= pH) return;

            int lightColor = light.getLightValue(x0, y0);
            if(lightColor == 0) return;

            if(lightBlock[screenX + screenY * pW] == Light.FULL) return;

            setLightMap(screenX, screenY, lightColor);

            if(x0 == x1 && y0 == y1) break;

            err2 = 2 * err;

            if(err2 > -1 * dy)
            {
                err -= dy;
                x0 += sx;
            }

            if(err2 < dx)
            {
                err += dx;
                y0 += sy;
            }
        }
    }

    public int getZDepth() {return this.zDepth;}
    public void setZDepth(int zDepth) {this.zDepth = zDepth;}

    /**Sets the font to one of the fonts defined in Fonts class. 
     * Be careful not to import the standard java font class instead of the engine one.
     * If the text gets corrupted after you set a new font, make sure you set the right amount of characters in
     * Font.offsets and widths.
     * Keep in mind that if you don't set a font in your game code the default one will be Font.COMICSANS_FONT.
    */
    public void setFont(Font font) 
    {
        this.font = font;
    }

    /**Color when there's no lights in the scene, by default it's set to white. 
     * Keep in mind that if you don't set an ambient color, the default white color won't let you see the lights
     * you use in the scene.
     * The recommended ambient color to use with lights is Colors.DARK_GRAY, which will make the scene dark
     * but not pitch black.
    */
    public void setAmbientColor(int color) {this.ambientColor = color;}

    public int getCameraX() {return this.cameraX;}
    public void setCameraX(int cameraX) {this.cameraX = cameraX;}

    public int getCameraY() {return this.cameraY;}
    public void setCameraY(int cameraY) {this.cameraY = cameraY;}
}