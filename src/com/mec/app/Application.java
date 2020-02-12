package com.mec.app;

import java.awt.event.KeyEvent;

import com.mec.engine.GameContainer;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;
import com.mec.engine.gfx.Font;
import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageTile;

import com.mec.engine.audio.SoundClip;;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    private ImageTile explosionDemo;
    private int imgPosX, imgPosY;

    private SoundClip sound;

    public Application()
    {
        explosionDemo = new ImageTile("/res/fire_anim.mectexture", 32, 40);

        sound = new SoundClip("/res/Audio/figure_it_out.wav");
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        // TODO: Write game code here.

        //Demo
        r.drawText("TEXT RENDERING TEST", 0, 16, 0xffff0000);
        r.drawImageTile(explosionDemo, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int)tileX, 5);
        r.drawImageTile(explosionDemo, imgPosX, imgPosY, (int)tileX, 5);
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

        if (tileX > 2560) tileX = 0;
       
    }

    float tileX = 0;
    float tileY = 0;

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());
        gc.start();
    }
}
