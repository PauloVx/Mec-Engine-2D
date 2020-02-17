package com.mec.engine;

import com.mec.app.Application;

public class Camera
{
    private float posX;
    private float posY;

    private String targetTag;

    /** The GameObject that the camera is following. */
    private GameObject target = null;

    public Camera(String targetTag)
    {
        this.targetTag = targetTag;
    }

    public void update(GameContainer gc, Application app, float deltaTime)
    {
        if(target == null) target = app.getObject(targetTag);
        if(target == null) return; //Couldn't find the target.

        float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWindowWidth() / 2;
        float targetY = (target.getPosY() + target.getHeight() / 2) - gc.getWindowHeight() / 2;

        //To make the camera following more natural.
        posX -= deltaTime * (posX - targetX) * 10;
        posY -= deltaTime * (posY - targetY) * 10;
    }

    public void render(Renderer renderer) 
    {
        renderer.setCameraX((int)posX);
        renderer.setCameraY((int)posY);
    }

    public float getPosX() {return this.posX;}
    public void setPosX(float posX) {this.posX = posX;}

    public float getPosY() {return this.posY;}
    public void setPosY(float posY) {this.posY = posY;}

    public String getTargetTag() {return this.targetTag;}
    public void setTargetTag(String targetTag) {this.targetTag = targetTag;}

    public GameObject getTarget() {return this.target;}
    public void setTarget(GameObject target) {this.target = target;}
}