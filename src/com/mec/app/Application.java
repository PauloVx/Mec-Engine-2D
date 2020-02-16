package com.mec.app;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
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
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public Application()
    {
        objects.add(new Player(2, 2));
    }

    @Override
    public void init(GameContainer gc) 
    {
        gc.getRenderer().setAmbientColor(Colors.LIGHT_GRAY);
        gc.getRenderer().setFont(Font.COMICSANS_FONT);
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        for (GameObject gameObject : objects) 
        {
            gameObject.render(gc, renderer);
        }
        // TODO: Write game code here.
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).update(gc, this, deltaTime);
            if(objects.get(i).isDead()) 
            {
                objects.remove(i);
                i--;
            }
        }

        // TODO: Write game code here.
    }

    //Adds an object to the objects array.
    public void addObject(GameObject obj)
    {
        objects.add(obj);
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());

        gc.setRenderResolution(1600, 900);
        gc.setWindowScale(0.6f);

        //TODO: Config code here.
        gc.setFramerateLock(true);
        gc.setWindowTitle("MecEngine Demo Game");

        gc.start();
    }

}
