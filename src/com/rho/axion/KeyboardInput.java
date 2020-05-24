package com.rho.axion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 *  This module handles all of the player's keyboard inputs.
 *  The way this works is that we use Java's key listener to listen for key inputs from the keyboard,
 *  but we have our own code that processes the information. This allows us to access this information 
 *  in a way that is more modular and that makes more sense in the context the engine was built in. Also, this
 *  circumvents the problem that when a key is held down by the user, there is a initial delay before the
 *  first trigger of the listener and all subsequential triggers. 
 */
public class KeyboardInput implements KeyListener
{
	
	// This will be the number of accepted inputs from the user's keyboard (all possible key events, apparently)
	private final int NUM_KEYS = 527;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS]; 
	
	// The module's constructor, which adds a key listener to the frame's canvas
	public KeyboardInput(Axion axion)
	{		
		axion.getGameWindow().getCanvas().addKeyListener(this);
	}
	
	/*
	 *  At every game update, this method will pass all of the keys[] data to keysLast[].
	 *  This means that at every update we will be able to keep track of the keys pressed during
	 *  the current update and the last update.
	 */
	public void update()
	{
		for (int i = 0; i < NUM_KEYS; i++)
		{
			keysLast[i] = keys[i];
		}
	}
	
	// Returns true if the key is currently pressed
	public boolean isKeyDown(int keyCode)
	{
		if(keyCode == 65406)
		{
			return keys[526];
		}
		return keys[keyCode];
	}
	
	// Returns true if the key is currently not pressed but was pressed last update
	public boolean isKeyReleased(int keyCode)
	{
		if(keyCode == 65406)
		{
			return !keys[526] && keysLast[526];
		}
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	// Returns true if key is currently pressed but was not pressed last update
	public boolean isKeyPressed(int keyCode)
	{
		if(keyCode == 65406)
		{
			return keys[526] && !keysLast[526];
		}
		return keys[keyCode] && !keysLast[keyCode];
	}
	

	// Set key to true when pressed
	@Override
	public void keyPressed(KeyEvent e)
	{
		// Handles Alt Graph key in my system... not sure how this works in other OS
		if(e.getKeyCode() == 65406)
		{
			keys[526] = true;
		}
		else 
		{
			keys[e.getKeyCode()] = true;
		}			
	}

	// Set a key to false when released
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == 65406)
		{
			keys[526] = false;
		}
		else
		{
			keys[e.getKeyCode()] = false;	
		}	
	}

	@Override
	public void keyTyped(KeyEvent e)
	{		
		// Empty
	}

}
