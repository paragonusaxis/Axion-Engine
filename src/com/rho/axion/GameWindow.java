package com.rho.axion;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/* 
 * The window module is responsible for creating a frame, a canvas and setting up
 * a triple buffer strategy. At every update in our game's loop, this module will draw 
 * an image on our buffer strategy. By manipulating this image's pixel data, we will e able to
 * draw our game's graphics.
 */

public class GameWindow 
{
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	// Calls the constructor
	public GameWindow(Axion axion)
	{
		// Creates a buffered image that has the resolution set for our game
		image = new BufferedImage(axion.getWidth(), axion.getHeight(), BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		
		/* 
		 * We make a new dimension that is our resolution times our scale and after we set it
		 * to our canvas. This creates a virtual resolution to our game window. In other words, our game's resolution will
		 * always be the one set in our buffered image, but the game window can be bigger or smaller depending on the scale.
		 */
		Dimension s = new Dimension((int)(axion.getWidth() * axion.getScale()), (int)(axion.getHeight() * axion.getScale()));
		
		// Makes sure our canvas is always at the same size
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		canvas.setFocusable(true);
		
		// Creates the frame and pass the title
		frame = new JFrame(axion.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		
		// Positions the frame in the center of the screen, resizable is false, visibility true!
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Creates the buffer strategy, a triple one in this case
		canvas.createBufferStrategy(3);
		
		// Gets the graphics object from the buffer strategy
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update() 
	{
		// Draws the image onto the buffer strategy and shows
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);		
		bs.show();
	}

	// Getters and setters
	public BufferedImage getImage()
	{
		return image;
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	public JFrame getFrame()
	{
		return frame;
	}
}
