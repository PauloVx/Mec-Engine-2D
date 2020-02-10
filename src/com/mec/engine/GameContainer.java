package com.mec.engine;

public class GameContainer implements Runnable
{
	private MecEngineApp app;
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;

	private int windowWidth = 960;
	private int windowHeight = 540;

	/** Window Scale (1f = 540p, 2f = 1080p, 2.667f = 1440p, 4f = 4K) */
	private float windowScale = 1f;
	private String windowTitle = "MecEngine Default Window Title";

	private boolean running = false;

	/**Basically the framerate cap. */
	private final double UPDATE_CAP = 1.0/60.0;
	
	public GameContainer(MecEngineApp app)
	{
		this.app = app;
	}
	
	public void start()
	{
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);

		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() 
	{
		
	}
	
	public void run()
	{
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int renderedFrames = 0;
		int currentFramerate = 0;
		
		while(running)
		{
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while (unprocessedTime >= UPDATE_CAP) 
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				app.update(this, (float)UPDATE_CAP);
				input.update();
				
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					currentFramerate = renderedFrames;
					renderedFrames = 0;
					System.out.println("Current FPS: " + currentFramerate);
				}
			}
			
			if(render)
			{
				renderer.clear();
				app.render(this, renderer);
				window.update();
				renderedFrames++;
			}
			else 
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		dispose();
	}
	
	private void dispose()
	{
		
	}

	public Window getWindow() {return this.window;}

	public Input getInput() {return this.input;}

	public int getWindowWidth() {return this.windowWidth;}
	public void setWindowWidth(int windowWidth) {this.windowWidth = windowWidth;}

	public int getWindowHeight() {return this.windowHeight;}
	public void setWindowHeight(int windowHeight) {this.windowHeight = windowHeight;}

	public float getWindowScale() {return this.windowScale;}
	public void setWindowScale(float windowScale) {this.windowScale = windowScale;}

	public String getWindowTitle() {return this.windowTitle;}
	public void setWindowTitle(String windowTitle) {this.windowTitle = windowTitle;}

}
