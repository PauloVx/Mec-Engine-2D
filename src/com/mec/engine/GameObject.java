package com.mec.engine;

import com.mec.app.Application;

/**Represents any object in the game. */
public abstract class GameObject
{
    /**Id of the object */
    protected String tag;
    protected float posX;
    protected float posY;
    protected int width;
    protected int height;
    protected boolean dead = false;

    public abstract void update(GameContainer gc, Application app, float deltaTime);
    public abstract void render(GameContainer gc, Renderer renderer);

    public String getTag() {return this.tag;}
    public void setTag(String tag) {this.tag = tag;}

    public float getPosX() {return this.posX;}
    public void setPosX(float posX) {this.posX = posX;}

    public float getPosY() {return this.posY;}
    public void setPosY(float posY) {this.posY = posY;}

    public int getWidth() {return this.width;}
    public void setWidth(int width) {this.width = width;}

    public int getHeight() {return this.height;}
    public void setHeight(int height) {this.height = height;}

    public boolean isDead() {return this.dead;}
    public void setDead(boolean dead) {this.dead = dead;}
}