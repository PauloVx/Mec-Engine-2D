package com.mec.app;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.mec.engine.Camera;
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
    private Camera camera;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public Application()
    {
        camera = new Camera("player");
        objects.add(new Player(2, 2));
    }

    @Override
    public void init(GameContainer gc) 
    {
        gc.getRenderer().setAmbientColor(Colors.WHITE);
        gc.getRenderer().setFont(Font.COMICSANS_FONT);
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        camera.render(renderer);

        for (GameObject gameObject : objects) 
        {
            gameObject.render(gc, renderer);
        }

        int cameraposX = renderer.getCameraX();
        int cameraposY = renderer.getCameraY();

        renderer.drawText("Camera target: " + camera.getTarget(), cameraposX, cameraposY + 90, Colors.YELLOW);
        renderer.drawText("Camera target tag: " + camera.getTargetTag(), cameraposX, cameraposY + 120, Colors.YELLOW);
        renderer.drawText("Camera position X: " + (int)camera.getPosX() + " Y: " + (int)camera.getPosY(), cameraposX, cameraposY + 150, Colors.YELLOW);

        int groundposX = -1000;
        renderer.drawFilledRect(Colors.RED, groundposX, gc.getWindowHeight(), 100000, 16); //Ground
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

        camera.update(gc, this, deltaTime);

        // TODO: Write game code here.
    }

    /**Adds an object to the objects array. */
    public void addObject(GameObject obj)
    {
        objects.add(obj);
    }


    /**Get object by tag. */
    public GameObject getObject(String tag)
    {
        for (int i = 0; i < objects.size(); i++) 
        {
            if(objects.get(i).getTag().equals(tag)) return objects.get(i); 
        }

        return null; //Didn't find the object
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());

        gc.setRenderResolution(1920, 1080);
        gc.setWindowScale(0.5f);

        //TODO: Config code here.
        gc.setFramerateLock(false);
        gc.setWindowTitle("MecEngine Demo Game");

        gc.start();
    }

}
