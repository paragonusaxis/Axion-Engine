package com.rho.axion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/*
 * Same as the KeyboardInput module, this module handles all of the user's mouse actions using
 * java's native mouse listeners, and then processes the informations with the help of a array.
 */
public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener
{
	private Axion axion;
	
	// Array maximum length is five, which is the amount of button events for a mouse in Java
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS]; 
	
	private int mouseX, mouseY;
	private int scroll;
	
	// Initializes mouse's position variables and adds a listener to the canvas
	public MouseInput(Axion axion)
	{
		this.axion = axion;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		axion.getGameWindow().getCanvas().addMouseListener(this);
		axion.getGameWindow().getCanvas().addMouseMotionListener(this);
		axion.getGameWindow().getCanvas().addMouseWheelListener(this);	
	}
	
	// At every update, keeps track of buttons pressed in the current update and buttons pressed in the last update
	public void update()
	{
		scroll = 0; // Reset scroll to 0 to keep it from infinitely activating
		
		for (int i = 0; i < NUM_BUTTONS; i++)
		{
			buttonsLast[i] = buttons[i];
		}
	}
	
	
	// Returns true if button is currently pressed
	public boolean isButtonDown(int button)
	{
		return buttons[button];
	}
	
	// Returns true if button is currently not pressed but was pressed in the last update
	public boolean isButtonReleased(int button)
	{
		return !buttons[button] && buttonsLast[button];
	}
	
	// Returns true if button is currently pressed but was not pressed in the last update
	public boolean isButtonPressed(int button)
	{
		return buttons[button] && !buttonsLast[button];
	}

	// Sets scroll direction when scrolling
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{		
		scroll = e.getWheelRotation();
	}

	// Sets mouse position when mouse moves with a button pressed
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = (int)(e.getX() / axion.getScale());
		mouseY = (int)(e.getY() / axion.getScale());		
	}

	// Sets mouse position when mouse moves
	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = (int)(e.getX() / axion.getScale());
		mouseY = (int)(e.getY() / axion.getScale());
	}

	// Sets button to true when a button is pressed
	@Override
	public void mousePressed(MouseEvent e)
	{
		buttons[e.getButton()] = true;		
	}

	// Sets a button to false when a button is released
	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;		
	}

	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Empty
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{	
		// Empty
	}

	@Override
	public void mouseExited(MouseEvent e)
	{		
		// Empty
	}

	// Getters and setters 
	public int getMouseX()
	{
		return mouseX;
	}

	public int getMouseY()
	{
		return mouseY;
	}

	public int getScroll()
	{
		return scroll;
	}

}
