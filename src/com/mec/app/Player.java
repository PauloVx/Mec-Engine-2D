package com.mec.app;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Input;
import com.mec.engine.Renderer;
import com.mec.engine.audio.SoundClip;
import com.mec.engine.gfx.Light;

public class Player extends GameObject 
{
    private float speed = 700f;
    private float gravity = 0.3f;

    private float velocityX;
    private float velocityY;

    private SoundClip jumpSound;

    public Player(int posX, int posY)
    {
        this.tag = "player";
        this.posX = posX * 16;
        this.posY = posY * 16;
        this.width = 16;
        this.height = 16;

        jumpSound = new SoundClip("/res/Audio/jump.wav");
    }

    @Override
    public void update(GameContainer gc, Application app, float deltaTime) 
    {
        if(gc.getInput().isKeyPressed(Input.KEY_A)) posX -= deltaTime * speed;
        if(gc.getInput().isKeyPressed(Input.KEY_D)) posX += deltaTime * speed;
        if(gc.getInput().isKeyPressed(Input.KEY_S)) posY += deltaTime * speed;

        posX += velocityX * deltaTime  * (speed / 4);     // Apply horizontal velocity to X position.
        posY += velocityY * deltaTime * (speed / 4);     // Apply vertical velocity to X position.
        velocityY += gravity * deltaTime * (speed / 12); // Apply gravity to vertical velocity.

        if(gc.getInput().isKeyDown(Input.KEY_W))
        {
            velocityY = -10.0f;
            jumpSound.playAudio();
        }

        if(gc.getInput().isKeyUp(Input.KEY_W))
        {
            if(velocityY < -7.0f)       // If character is still ascending in the jump.
                velocityY = -7.0f;      // Limit the speed of ascent.
        }

        if(posY >= gc.getWindowHeight() - 20) posY = gc.getWindowHeight() - 20; //Ground collision
        if(posY <= 0) posY = 0; //Sky collision

        //if(posX > gc.getWindowWidth()) posX = 0; //Go to the other side of the screen when you go out of bounds.
        //if(posX < 0) posX = gc.getWindowWidth();

        //Shooting
        if(gc.getInput().isKeyDown(Input.KEY_SHIFT)) app.addObject(new Bullet(posX, posY, 1));
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        int cameraposX = renderer.getCameraX();
        int cameraposY = renderer.getCameraY();

        renderer.drawFilledRect(Colors.YELLOW, (int)posX, (int)posY, width, height);

        renderer.drawText("Player X: " + (int)posX + " Y: " + (int)posY, cameraposX, cameraposY + 30, Colors.YELLOW);
        renderer.drawText("Player X: " + (int)posX + " Y: " + (int)posY, (int)posX, (int)posY, Colors.GREEN); //TODO: Remove debugging code.
        //TODO: Replace player with animated sprite.
    }
}