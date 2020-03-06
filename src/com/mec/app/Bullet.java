package com.mec.app;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Renderer;
import com.mec.engine.audio.SoundClip;
import com.mec.engine.gfx.Light;

public class Bullet extends GameObject 
{
    private float speed = 1000f;
    private int direction;

    private SoundClip sound;

    public Bullet(float posX, float posY, int direction)
    {
        this.tag = "bullet";
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.width = 5;
        this.height = 5;
        sound = new SoundClip("/res/Audio/shoot.wav");
        sound.setVolume(-20);
        sound.playAudio();
    }

    @Override
    public void update(GameContainer gc, Application app, float deltaTime) 
    {
        switch(direction)
        {
            case 0: posY += speed * deltaTime; break;
            case 1: posX += speed * deltaTime; break;
            case 2: posY -= speed * deltaTime; break;
            case 3: posX -= speed * deltaTime; break;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        renderer.drawFilledRect(Colors.WHITE, (int)posX, (int)posY, width, height);

        renderer.drawText("Bullet X: " + (int)posX + " Y : " + (int)posY, (int)posX, (int)posY, Colors.RED); //TODO: Remove debugging code.
    }
}