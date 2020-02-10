package com.mec.engine;

import javax.swing.JFrame;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

public class Window
{
    private JFrame frame;

    private BufferedImage image;
    private Canvas canvas;

    private BufferStrategy bs;
    private Graphics gfx;

    public Window(GameContainer gc)
    {
        image = new BufferedImage(gc.getWindowWidth(), gc.getWindowHeight(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();
        Dimension size = new Dimension((int)(gc.getWindowWidth() * gc.getWindowScale()), (int)(gc.getWindowHeight() * gc.getWindowScale()));
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);

        frame = new JFrame(gc.getWindowTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        gfx = bs.getDrawGraphics();
    }

    public void update()
    {
        gfx.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getImage() { return this.image;}

    public JFrame getFrame() {return this.frame;}

    public Canvas getCanvas() {return this.canvas;}
}