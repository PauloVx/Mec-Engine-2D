package com.mec.engine;

public abstract class MecEngineApp
{
    /**Render method.*/
    public abstract void render(GameContainer gc, Renderer r);
    /**Gets called every frame.*/
    public abstract void update(GameContainer gc, float deltaTime);

    /**Initialization method. */
    public abstract void init(GameContainer gc);
}