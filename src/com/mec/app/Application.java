package com.mec.app;

import java.awt.event.KeyEvent;

import com.mec.engine.GameContainer;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    public Application()
    {
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        // TODO: Write game code here.
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
         // TODO: Write game code here.
        if(gc.getInput().isKeyPressed(KeyEvent.VK_A)) System.out.println("Key A Pressed!");
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());
        gc.start();
    }
}
