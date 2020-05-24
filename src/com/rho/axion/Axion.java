package com.rho.axion;

import com.rho.axion.gfx.Font;

/* 
 * The axion class is responsible for implementing the game loop and administrating 
 * the engine's main modules.
 */
public class Axion implements Runnable 
{
	// Declares the main thread and modules
	private Thread thread;
	private GameWindow window;
	private Graphics graphics;
	private KeyboardInput keyboard;
	private MouseInput mouse;
	private Time time;
	private Game game;
	private boolean showFps = false;
	
	// Variable to keep the game loop running forever
	private boolean running = false;
	
	// Game's maximum speed (60 updates per second)
	private final double UPDATE_CAP = 1.0/60.0;
	
	// Game's resolution
	private int width = 432, height = 324;
	
	// Window's scaling -- Maybe move this to the window module...
	private float scale = 2f;
	
	// Title on the window
	private String title = "Axion Engine v0.1";
	
	// Creates the default font, which is Nokia Cellphone
	String symbols = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	private final Font DEFAULT_FONT = new Font("/default-font.png", symbols);
	
	// Current Font
	private Font currentFont;
	
	// Constructor, takes a game as input
	public Axion(Game game) 
	{
		this.game = game;
	}
	
	/*
	 * This method starts the main thread and all the modules are initialized.
	 * This method has to be called BY THE GAME ITSELF in it's main method.
	 * This method will also call the game's load() method.
	 */
	public void start() 
	{
		game.load();
		
		window = new GameWindow(this);
		graphics = new Graphics(this);
		keyboard = new KeyboardInput(this);
		mouse = new MouseInput(this);
		time = new Time();
		setCurrentFont(DEFAULT_FONT);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() 
	{
		
	}
	
	// This method contains the game loop in itself.
	public void run() 
	{
		// Set the variable running to true, which will allow the game loop to start
		running = true;
		
		// This variable is part of a control mechanism that caps the user's fps 
		boolean render = false;
		
		// These variables are used to compute the time between the current iteration and the last iteration of the game loop
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1e9; // <-- This is the same thing as 1_000_000_000.0
		double deltaTime = 0;
		double unprocessedTime = 0;
		
		// These variables are used to compute the user's fps
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		// Finally, the game loop
		while(running)
		{
			// Computes dt
			firstTime = System.nanoTime() / 1e9;
			deltaTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += deltaTime;
			frameTime += deltaTime;
			
			/*
			 * If delta time is way bigger than our game speed, this loop will make sure that 
			 * we update the game as many times as it's necessary before rendering again. If our dt is currently 
			 * twice our game's speed, then the loop will run twice to take this unprocessed time in consideration.
			 * On the other hand, this will also caps our game's update speed to 60 times a second tops!  
			 */
			while(unprocessedTime >= UPDATE_CAP) 
			{
				unprocessedTime -= UPDATE_CAP;
				
				// Allows game to render
				render = true;
				
				// Calls the game update method
				game.update((float)(UPDATE_CAP));
				
				// Update the tables in the input modules
				keyboard.update();
				mouse.update();
				time.update();
				
				// Calculates and prints the fps in the console
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					fps = frames;
					frames = 0;
					
					// System.out.println("FPS: " + fps);
				}
			}
			
			if(render) 
			{
				// Renders the game 
				// FPS currently capped! Comment the line below if you don't want to limit your fps to 60
				render = false;
				frames++;
				
				// Clears the pixel data of the image stored in the canvas
				graphics.clear();
				
				if(showFps)
				{
					graphics.print("fps: " + String.valueOf(fps), 0, 0, 0xffffffff);
				}
				
				// Calls the game's render method
				game.render();
				
				// Draws the canva's image and shows the buffer strategy
				window.update();
			} 
			// If the game didn't render (didn't update) sleep instead
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
	
	// Getters and setters
	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public GameWindow getGameWindow()
	{
		return window;
	}

	public KeyboardInput keyboard()
	{
		return keyboard;
	}

	public MouseInput mouse()
	{
		return mouse;
	}
	
	public Graphics graphics()
	{
		return graphics;
	}

	public Time time()
	{
		return time;
	}

	public Font getCurrentFont()
	{
		return currentFont;
	}

	public void setCurrentFont(Font currentFont)
	{
		this.currentFont = currentFont;
	}

	public void showFps(boolean showFps)
	{
		this.showFps = showFps;
	}
}
