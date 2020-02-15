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

	private float windowScale = 1f;
	private String windowTitle = "MecEngine Default Window Title";

	private boolean running = false;

	/**False to unlock the fps, true will lock it to the default value (60). */
	private boolean framerateLock = true;
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
		
		app.init(this);

		while(running)
		{
			render = !framerateLock;
			
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
					//System.out.println("Current FPS: " + currentFramerate);
				}
			}
			
			if(render)
			{
				renderer.clear();
				app.render(this, renderer);
				renderer.process();
				renderer.drawText("FPS: " + currentFramerate, 0, 0, Colors.WHITE);
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

	/**False to unlock the fps, true will lock it to the default value (60). 
	 * @param value boolean value.
	*/
	public void setFramerateLock(boolean value){this.framerateLock = value;}

	public Window getWindow() {return this.window;}

	public Input getInput() {return this.input;}

	public int getWindowWidth() {return this.windowWidth;}
	private void setWindowWidth(int windowWidth) {this.windowWidth = windowWidth;}

	public int getWindowHeight() {return this.windowHeight;}
	private void setWindowHeight(int windowHeight) {this.windowHeight = windowHeight;}

	/**Use this mehod to change the rendering resolution of the window.
	 * If you don't call this method, the default resolution will be 960x540.
	 */
	public void setRenderResolution(int pixelsX, int pixelsY)
	{
		setWindowWidth(pixelsX);
		setWindowHeight(pixelsY);
	}

	public float getWindowScale() {return this.windowScale;}
	/** Window Scale, Default = 1f.
	 * Keep in mind that this method will not change the rendering resolution of the window,
	 * it will just make the window bigger.
	 * If you want to change the rendering resolution use setRenderResolution().
	 * @param windowScale float value.
	*/
	public void setWindowScale(float windowScale) {this.windowScale = windowScale;}

	public String getWindowTitle() {return this.windowTitle;}
	public void setWindowTitle(String windowTitle) {this.windowTitle = windowTitle;}

	public Renderer getRenderer() {return this.renderer;}

}
