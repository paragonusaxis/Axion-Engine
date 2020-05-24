package com.rho.axion;

/*
 *  This is the interface game. This makes sure your actual game has access to all methods
 *  necessary to create a game using this engine, without the need to mess with the engine code at all.
 *  In other words, this separate the creation of the game from the engine in itself.
 */
public abstract class Game
{	
	public static Axion axion;
	/*
	 * In the load() method you should initialize all your game's assets or global variables. Or anything else you want really.
	 * This method will be called only once when the game starts.
	 */
	public abstract void load();
	
	/*
	 * The update() method will be called at every iteration of the game loop. This is the ideal
	 * place for listening to player inputs, updating variables, changing game states, etc.
	 */
	public abstract void update(float gameSpeed);
	
	/*
	 * The render() method is called whenever our game finishes updating. 
	 * It's the ideal place to call methods from the Graphics module, such as drawImage().
	 */
	public abstract void render();
}
