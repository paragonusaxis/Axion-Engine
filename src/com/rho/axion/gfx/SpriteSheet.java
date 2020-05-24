package com.rho.axion.gfx;

// SpriteSheet is a class that extends Image, so it also has all of Image's fields and methods, plus its own's
public class SpriteSheet extends Image
{
	// tileW and tileH are the width and size of specific tile in the sprite sheet.
	// Yes, they have to be all the same size for this to work!
	private int tileW, tileH;
	
	public SpriteSheet(String path, int tileW, int tileH)
	{
		// Calls the image constructor and then sets its unique fields
		super(path);
		this.tileW = tileW;
		this.tileH = tileH;
	}

	// Getters and setters for unique fields
	public int getTileW()
	{
		return tileW;
	}

	public void setTileW(int tileW)
	{
		this.tileW = tileW;
	}

	public int getTileH()
	{
		return tileH;
	}

	public void setTileH(int tileH)
	{
		this.tileH = tileH;
	}
	
	
}
