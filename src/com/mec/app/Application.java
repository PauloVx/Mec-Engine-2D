package com.mec.app;

import java.awt.event.KeyEvent;

import com.mec.engine.GameContainer;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;
import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageTile;

import com.mec.engine.audio.SoundClip;;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    private Image ground;

    private ImageTile tile;
    private ImageTile door;
    private int imgPosX, imgPosY;

    private SoundClip sound;

    public Application()
    {
        tile = new ImageTile("/res/Textures/fire_anim.mct", 32, 32);
        door = new ImageTile("/res/Textures/door_animated.mct", 32, 32);

        ground = new Image("/res/Textures/rock.mct");

        sound = new SoundClip("/res/Audio/figure_it_out.wav");
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        // TODO: Write game code here.

        //Demo
        r.drawImageTile(tile, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int)tileX, 5);
        r.drawImageTile(tile, imgPosX, imgPosY, (int)tileX, 3);

        r.drawImageTile(door, 360, 360, (int)tileDoor, 0);

        r.drawText("TEXT RENDERING TEST", 0, 16, 0xffff0000);

        r.drawFilledRect(0xff00ff00, 360, 360, 180, 90);

        r.drawImage(ground, 0, 510);
        r.drawImage(ground, 32, 510);
        r.drawImage(ground, 64, 510);
        r.drawImage(ground, 96, 510);
        r.drawImage(ground, 128, 510);
        r.drawImage(ground, 160, 510);
        r.drawImage(ground, 192, 510);
        r.drawImage(ground, 224, 510);
        r.drawImage(ground, 256, 510);
        r.drawImage(ground, 288, 510);
        r.drawImage(ground, 320, 510);
        r.drawImage(ground, 352, 510);
        r.drawImage(ground, 384, 510);
        r.drawImage(ground, 416, 510);
        r.drawImage(ground, 448, 510);
        r.drawImage(ground, 448+32, 510);
        r.drawImage(ground, 448+32*2, 510);
        r.drawImage(ground, 448+32*3, 510);
        r.drawImage(ground, 448+32*4, 510);
        r.drawImage(ground, 448+32*5, 510);
        r.drawImage(ground, 448+32*6, 510);
        r.drawImage(ground, 448+32*7, 510);
        r.drawImage(ground, 448+32*8, 510);
        r.drawImage(ground, 448+32*9, 510);
        r.drawImage(ground, 448+32*10, 510);
        r.drawImage(ground, 448+32*11, 510);
        r.drawImage(ground, 448+32*12, 510);
        r.drawImage(ground, 448+32*13, 510);
        r.drawImage(ground, 448+32*14, 510);
        r.drawImage(ground, 448+32*15, 510);

        r.setPixel(200, 200, 0xffffff00);

    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        // TODO: Write game code here.
        if(gc.getInput().isKeyPressed(KeyEvent.VK_P)) sound.playAudio();
        if(gc.getInput().isKeyPressed(KeyEvent.VK_O)) sound.setVolume(+6);
        if(gc.getInput().isKeyPressed(KeyEvent.VK_L)) sound.setVolume(-6);

        //Demo
        if(gc.getInput().isKeyPressed(KeyEvent.VK_A)) imgPosX -= 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_D)) imgPosX += 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_W)) imgPosY -= 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_S)) imgPosY += 2;

        tileX += deltaTime * 5;
        tileDoor += deltaTime * 4;

        if (tileX > 2560) tileX = 0;
        if (tileDoor > 384) tileX = 0;
       
    }
    
    float tileX = 0;
    float tileDoor = 0;

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());
        gc.start();
    }
}
