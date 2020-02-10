package com.mec.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener 
{
    private GameContainer gc;

    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS]; //Keys in the last frame

    private final int NUM_BUTTONS = 5;
    private boolean[] btns = new boolean[NUM_BUTTONS];
    private boolean[] btnsLast = new boolean[NUM_BUTTONS]; //Mouse buttons in the last frame

    private int mouseX, mouseY; // Mouse Position
    private int scroll;

    public Input(GameContainer gc) 
    {
        this.gc = gc;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
    }

    public void update()
    {
        scroll = 0;
        for (int i = 0; i < NUM_KEYS; i++)
        {
            keysLast[i] = keys[i];
        }

        for (int i = 0; i < NUM_BUTTONS; i++)
        {
            btnsLast[i] = btns[i];
        }
    }

    /**Will return true when you begin pressing a key.*/
    public boolean isKeyDown(int keycode){return keys[keycode] && !keysLast[keycode];}
    /**Will return true when you stop pressing a key.*/
    public boolean isKeyUp(int keycode){return !keys[keycode] && keysLast[keycode];}
    /**Will return true until you stop pressing a key.*/
    public boolean isKeyPressed(int keycode){return keys[keycode];}

    /**Will return true when you begin pressing a mouse button.*/
    public boolean isButtonDown(int button){return btns[button] && !btnsLast[button];}
    /**Will return true when you stop pressing a mouse button.*/
    public boolean isButtonUp(int button){return !btns[button] && btnsLast[button];}
    /**Will return true until you stop pressing a mouse button.*/
    public boolean isButtonPressed(int button){return btns[button];}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        scroll = e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        mouseX = (int)(e.getX() / gc.getWindowScale());
        mouseY = (int)(e.getY() / gc.getWindowScale());
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = (int)(e.getX() / gc.getWindowScale());
        mouseY = (int)(e.getY() / gc.getWindowScale());
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        btns[e.getButton()] = true;
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        btns[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        btns[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        keys[e.getKeyCode()] = false;
    }

    public int getMouseX(){return this.mouseX;}
    public int getMouseY(){return this.mouseY;}
    public int getScroll() {return this.scroll;}
}