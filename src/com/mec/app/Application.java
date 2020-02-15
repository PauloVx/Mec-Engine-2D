package com.mec.app;

import java.awt.event.KeyEvent;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.Input;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;
import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageTile;
import com.mec.engine.gfx.Light;
import com.mec.engine.audio.SoundClip;;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    private Image background;
    private Image ground;
    private Image lightBulb;

    private Light light;

    private ImageTile tile;

    private int imgPosX, imgPosY;

    private SoundClip sound;

    public Application()
    {
        background = new Image("/res/Textures/background.mct");

        tile = new ImageTile("/res/Textures/fire_anim.mct", 32, 32);
        tile.setAlpha(false);
        tile.setLightBlock(Light.FULL);

        ground = new Image("/res/Textures/rock.mct");
        ground.setAlpha(true);
        ground.setLightBlock(Light.FULL);

        sound = new SoundClip("/res/Audio/figure_it_out.wav");

        light = new Light(400, Colors.WHITE);
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        // TODO: Write game code here.

        //Demo

        r.drawImage(background, 0, 0);

        //r.drawImageTile(tile, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int)tileX, 5);
        r.drawImageTile(tile, imgPosX, imgPosY, (int)tileX, 3);
        r.drawImageTile(tile, imgPosX+100, imgPosY+100, (int)tileX, 3);
        r.drawImageTile(tile, imgPosX, imgPosY+100, (int)tileX, 3);
        r.drawImageTile(tile, imgPosX+100, imgPosY, (int)tileX, 3);
        
        int floorY = gc.getWindowHeight() - 32;
        r.drawImage(ground, 0, floorY);
        r.drawImage(ground, 32, floorY);
        r.drawImage(ground, 64, floorY);
        r.drawImage(ground, 96, floorY);
        r.drawImage(ground, 128, floorY);
        r.drawImage(ground, 160, floorY);
        r.drawImage(ground, 192, floorY);
        r.drawImage(ground, 224, floorY);
        r.drawImage(ground, 256, floorY);
        r.drawImage(ground, 288, floorY);
        r.drawImage(ground, 320, floorY);
        r.drawImage(ground, 352, floorY);
        r.drawImage(ground, 384, floorY);
        r.drawImage(ground, 416, floorY);
        r.drawImage(ground, 448, floorY);
        r.drawImage(ground, 448+32, floorY);
        r.drawImage(ground, 448+32*2, floorY);
        r.drawImage(ground, 448+32*3, floorY);
        r.drawImage(ground, 448+32*4, floorY);
        r.drawImage(ground, 448+32*5, floorY);
        r.drawImage(ground, 448+32*6, floorY);
        r.drawImage(ground, 448+32*7, floorY);
        r.drawImage(ground, 448+32*8, floorY);
        r.drawImage(ground, 448+32*9, floorY);
        r.drawImage(ground, 448+32*10, floorY);
        r.drawImage(ground, 448+32*11, floorY);
        r.drawImage(ground, 448+32*12, floorY);
        r.drawImage(ground, 448+32*13, floorY);
        r.drawImage(ground, 448+32*14, floorY);
        r.drawImage(ground, 448+32*15, floorY);
        
        r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        r.drawText("Mec Engine v0.5 Demo Scene", 0, 32, 0xffff0000);
        //r.drawFilledRect(0x880000ff, 0, 0, gc.getWindowWidth(), gc.getWindowHeight());

    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        // TODO: Write game code here.
        if(gc.getInput().isKeyPressed(Input.KEY_CTRL)) sound.playAudio();
        if(gc.getInput().isKeyPressed(Input.KEY_O)) sound.setVolume(+6);
        if(gc.getInput().isKeyPressed(Input.KEY_L)) sound.setVolume(-6);

        //Demo
        if(gc.getInput().isKeyPressed(Input.KEY_A)) imgPosX -= 2;
        if(gc.getInput().isKeyPressed(Input.KEY_D)) imgPosX += 2;
        if(gc.getInput().isKeyPressed(Input.KEY_W)) imgPosY -= 2;
        if(gc.getInput().isKeyPressed(Input.KEY_S)) imgPosY += 2;

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

        gc.setWindowWidth(960);
        gc.setWindowHeight(540);
        gc.setWindowScale(1f);

        //TODO: Config code here.
        gc.setFramerateLock(false);

        gc.start();
    }
}
