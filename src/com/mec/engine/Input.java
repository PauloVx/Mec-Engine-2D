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

    public static final int KEY_A = KeyEvent.VK_A;
    public static final int KEY_B = KeyEvent.VK_B;
    public static final int KEY_C = KeyEvent.VK_C;
    public static final int KEY_D = KeyEvent.VK_D;
    public static final int KEY_E = KeyEvent.VK_E;
    public static final int KEY_F = KeyEvent.VK_F;
    public static final int KEY_G = KeyEvent.VK_G;
    public static final int KEY_H = KeyEvent.VK_H;
    public static final int KEY_I = KeyEvent.VK_I;
    public static final int KEY_J = KeyEvent.VK_J;
    public static final int KEY_K = KeyEvent.VK_K;
    public static final int KEY_L = KeyEvent.VK_L;
    public static final int KEY_M = KeyEvent.VK_M;
    public static final int KEY_N = KeyEvent.VK_N;
    public static final int KEY_O = KeyEvent.VK_O;
    public static final int KEY_P = KeyEvent.VK_P;
    public static final int KEY_Q = KeyEvent.VK_Q;
    public static final int KEY_R = KeyEvent.VK_R;
    public static final int KEY_S = KeyEvent.VK_S;
    public static final int KEY_T = KeyEvent.VK_T;
    public static final int KEY_U = KeyEvent.VK_U;
    public static final int KEY_V = KeyEvent.VK_V;
    public static final int KEY_W = KeyEvent.VK_W;
    public static final int KEY_X = KeyEvent.VK_X;
    public static final int KEY_Y = KeyEvent.VK_Y;
    public static final int KEY_Z = KeyEvent.VK_Z;

    public static final int KEY_ENTER = KeyEvent.VK_ENTER;
    public static final int KEY_BACKSPACE =  KeyEvent.VK_BACK_SPACE;
    public static final int KEY_TAB = KeyEvent.VK_TAB;
    public static final int KEY_SHIFT = KeyEvent.VK_SHIFT;
    public static final int KEY_CTRL = KeyEvent.VK_CONTROL;
    public static final int KEY_ALT = KeyEvent.VK_ALT;
    public static final int KEY_PAUSE = KeyEvent.VK_PAUSE;
    public static final int KEY_CAPS = KeyEvent.VK_CAPS_LOCK;
    public static final int KEY_ESC = KeyEvent.VK_ESCAPE;
    public static final int KEY_SPACE = KeyEvent.VK_SPACE;
    public static final int KEY_PAGEUP = KeyEvent.VK_PAGE_UP;
    public static final int KEY_PAGEDOWN = KeyEvent.VK_PAGE_DOWN;
    public static final int KEY_END = KeyEvent.VK_END;
    public static final int KEY_HOME = KeyEvent.VK_HOME;

    //TODO: Mouse events are not working properly
    public static final int MOUSE_LMB = MouseEvent.BUTTON1;
    public static final int MOUSE_RMB = MouseEvent.BUTTON2;
    //TODO: Add all keys.
}