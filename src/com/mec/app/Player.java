package com.mec.app;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Input;
import com.mec.engine.Renderer;

public class Player extends GameObject 
{
    private float speed = 1000f;

    public Player(int posX, int posY)
    {
        this.tag = "player";
        this.posX = posX * 16;
        this.posY = posY * 16;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        if(gc.getInput().isKeyPressed(Input.KEY_A)) posX -= deltaTime * speed;
        if(gc.getInput().isKeyPressed(Input.KEY_D)) posX += deltaTime * speed;
        if(gc.getInput().isKeyPressed(Input.KEY_W)) posY -= deltaTime * speed;
        if(gc.getInput().isKeyPressed(Input.KEY_S)) posY += deltaTime * speed;
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        renderer.drawFilledRect(Colors.YELLOW, (int)posX, (int)posY, width, height);
        //TODO: Replace player with animated sprite.
    }
}