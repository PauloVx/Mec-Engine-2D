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
import com.mec.engine.audio.SoundClip;
import com.mec.engine.gfx.Font;;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    public Application()
    {
    }

    @Override
    public void render(GameContainer gc, Renderer r) 
    {
        r.setAmbientColor(Colors.WHITE);
        // TODO: Write game code here.
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        // TODO: Write game code here.
    }

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
