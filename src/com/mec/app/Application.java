package com.mec.app;

import java.awt.event.KeyEvent;

import com.mec.engine.GameContainer;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;
import com.mec.engine.gfx.Image;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    private Image image;
    private int imgPosX, imgPosY;

    public Application()
    {
        image = new Image("/res/Placeholder.png");
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        // TODO: Write game code here.

        //Demo
        r.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        r.drawImage(image, imgPosX, imgPosY);
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        // TODO: Write game code here.

        //Demo
        if(gc.getInput().isKeyPressed(KeyEvent.VK_A)) imgPosX -= 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_D)) imgPosX += 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_W)) imgPosY -= 2;
        if(gc.getInput().isKeyPressed(KeyEvent.VK_S)) imgPosY += 2;
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());
        gc.start();
    }
}
