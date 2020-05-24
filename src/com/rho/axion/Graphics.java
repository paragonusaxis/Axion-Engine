package com.rho.axion;

import java.awt.image.DataBufferInt;
import com.rho.axion.gfx.Font;
import com.rho.axion.gfx.Image;
import com.rho.axion.gfx.SpriteSheet;

/*
 * This module is responsible for drawing stuff to our screen, instead of using native Java methods to do it.
 * By having access to the array of pixel data from the buffered image added to our canvas, we are able
 * to modify that array to display whatever we want.
 */
public class Graphics
{
	Axion axion;
	
	// Pixel width and pixel height
	private int pW, pH;
	
	// Array of pixels
	private int[] p;
	
	
	// Gets the canvas (and it's image) resolution and the image's pixel data
	public Graphics(Axion axion)
	{
		this.axion = axion;
		pW = axion.getWidth();
		pH = axion.getHeight();
		p = ((DataBufferInt)axion.getGameWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	// Iterates trough all of the images pixels and sets its values to 0, effectively clearing it.
	public void clear()
	{
		for (int i = 0; i < p.length; i++)
		{
			p[i] = 0;
		}
	}
	
	// This method sets the value of a given pixel stored in an array
	public void setPixel(int x, int y, int value)
	{
		// Skip transparent pixels and prevents out of bounds exception
		if(x < 0 || x >= pW || y < 0 || y >= pH || value == 0xffff00ff)
		{
			return;
		}
		
		p[x + y * pW] = value;
	}
	
	// Method that draws an image to the screen
	public void drawImage(Image image, int x, int y, int offX, int offY, float xScale, float yScale)
	{		
		// If an image was to be drawn outside of the canvas in its entirety, just return
		if(x + offX < -image.getW()) {return;}
		if(y + offY < -image.getH()) {return;}
		if(x + offX >= pW) {return;}
		if(y + offY >= pH) {return;}
		
		int newX = 0;
		int newY = 0;
		int newW = image.getW();
		int newH = image.getH();		
		
		// If part of the image is outside the canvas, just render the image up to the point that is going to be displayed
		// This makes our draw function more efficient, as we will iterate through less pixels and call setPixel() less times
		if(x + offX < 0) {newX -= x + offX;}
		if(y + offY < 0) {newY -= y + offY;}
		if(newW + x + offX >= pW) {newW -= newW + x + offX - pW;}
		if(newH + y + offY >= pH) {newH -= newH + y + offY - pH;}
		
		// Iterates through every pixel in an image and sets it on the appropriate place in the canvas
		// Also, if scale is reversed, reverse the image in that axis. 
		for(int iy = newY; iy < newH; iy++)
		{
			for(int ix = newX; ix < newW; ix++)
			{
				if(xScale == 1)
				{
					if(yScale == 1)
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[ix + iy * image.getW()]);
					}
					else
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[ix + (image.getH() - iy - 1) * image.getW()]);
					}					
				}
				else
				{
					if(yScale == 1)
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(image.getW() - ix - 1) + iy * image.getW()]);
					}
					else
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(image.getW() - ix - 1) + (image.getH() - iy - 1) * image.getW()]);
					}
				}
			}
		}
	}
	
	// Same thing as above but with less parameters, i.e.: overloaded!
	public void drawImage(Image image, int x, int y)
	{		
		// Cull image
		if(x < -image.getW()) {return;}
		if(y < -image.getH()) {return;}
		if(x >= pW) {return;}
		if(y >= pH) {return;}
		
		int newX = 0;
		int newY = 0;
		int newW = image.getW();
		int newH = image.getH();		
		
		// Clip image
		if(x < 0) {newX -= x;}
		if(y < 0) {newY -= y ;}
		if(newW + x >= pW) {newW -= newW + x - pW;}
		if(newH + y >= pH) {newH -= newH + y - pH;}
		
		for(int iy = newY; iy < newH; iy++)
		{
			for(int ix = newX; ix < newW; ix++)
			{
				setPixel(ix + x, iy + y, image.getP()[ix + iy * image.getW()]);
			}
		}
	}
	
	// This method draws a tile from a sprite sheet. Besides passing which tile you want to draw, it's the same as above
	public void drawImageTile(SpriteSheet image, int x, int y, int tileX, int tileY, int offX, int offY, int xScale, int yScale)
	{
		// Cull image
		if(x + offX < -image.getTileW()) {return;}
		if(y + offY < -image.getTileH()) {return;}
		if(x + offX >= pW) {return;}
		if(y + offY >= pH) {return;}
		
		int newX = 0;
		int newY = 0;
		int newW = image.getTileW();
		int newH = image.getTileH();		
		
		// Clip image
		if(x + offX < 0) {newX -= x + offX;}
		if(y + offY < 0) {newY -= y + offY;}
		if(newW + x + offX >= pW) {newW -= newW + x + offX - pW;}
		if(newH + y + offY >= pH) {newH -= newH + y + offY - pH;}
		
		for(int iy = newY; iy < newH; iy++)
		{
			for(int ix = newX; ix < newW; ix++)
			{
				if(xScale == 1)
				{
					if(yScale == 1)
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(ix + tileX * image.getTileW()) + (iy + tileY * image.getTileH()) * image.getW()]);
					}
					else
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(ix + tileX * image.getTileW()) + (tileY * image.getTileH() + image.getTileH() - iy - 1) * image.getW()]);
					}					
				}
				else
				{
					if(yScale == 1)
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(tileX * image.getTileW() + image.getTileW() - ix - 1) + iy * image.getW()]);
					}
					else
					{
						setPixel(ix + x + offX, iy + y + offY, image.getP()[(tileX * image.getTileW() + image.getTileW() - ix - 1) + (tileY * image.getTileH() + image.getTileH() - iy - 1) * image.getW()]);
					}
				}
			}
		}
	}

	// I'm a benevolent soul, so I also overloaded this method...
	public void drawImageTile(SpriteSheet image, int x, int y, int tileX, int tileY)
	{
		// Cull image
		if(x < -image.getTileW()) {return;}
		if(y  < -image.getTileH()) {return;}
		if(x >= pW) {return;}
		if(y >= pH) {return;}
		
		int newX = 0;
		int newY = 0;
		int newW = image.getTileW();
		int newH = image.getTileH();		
		
		// Clip image
		if(x < 0) {newX -= x;}
		if(y < 0) {newY -= y ;}
		if(newW + x >= pW) {newW -= newW + x - pW;}
		if(newH + y >= pH) {newH -= newH + y - pH;}
		
		for(int iy = newY; iy < newH; iy++)
		{
			for(int ix = newX; ix < newW; ix++)
			{
				setPixel(ix + x, iy + y, image.getP()[(ix + tileX * image.getTileW()) + (iy + tileY * image.getTileH()) * image.getW()]);
			}
		}
	}
	
	// Prints text using the current font, at the x/y location and with the specified color
	public void print(String text, int x, int y, int color)
	{		
		Font currentFont = axion.getCurrentFont();
		int charPosition = 0;
		int charIndex;
		int length = text.length();
		
		for(int i = 0; i < length; i++)
		{
			charIndex = currentFont.getSymbolIndex().indexOf(text.charAt(i));
			
			for(int iy = 1; iy < currentFont.getFontImage().getH(); iy++)
			{
				for(int ix = 0; ix < currentFont.getWidths()[charIndex]; ix++)
				{
					int p = currentFont.getFontImage().getP()[(ix + currentFont.getOffsets()[charIndex] + 1) + iy * currentFont.getFontImage().getW()];
					if(p == 0xffffffff)
					{
						setPixel(x + charPosition + ix, y + iy, color);							
					}									
				}
			}
			charPosition += currentFont.getWidths()[charIndex] - 1 + currentFont.getFontSpacing();
		}
	}
	
	// Same as above but with vertical and horizontal alignment
	public void printf(String text, int x, int y, int color, String xAlignment, String yAlignment)
	{		
		Font currentFont = axion.getCurrentFont();
		int charPosition = 0;
		int charIndex;
		int length = text.length();
		
		if(xAlignment.equals("center"))
		{
			int pixelLenght = 0;
			for(int i = 0; i < length; i++)
			{
				charIndex = currentFont.getSymbolIndex().indexOf(text.charAt(i));
				pixelLenght += currentFont.getWidths()[charIndex] - 1 + currentFont.getFontSpacing();
			}
			
			x = x - pixelLenght/2;
		} 
		else if(xAlignment.equals("right"))
		{
			int pixelLenght = 0;
			for(int i = 0; i < length; i++)
			{
				charIndex = currentFont.getSymbolIndex().indexOf(text.charAt(i));
				pixelLenght += currentFont.getWidths()[charIndex] - 1 + currentFont.getFontSpacing();
			}
			
			x = x - pixelLenght;
		}
		
		if(yAlignment.equals("center"))
		{
			y = y - 1 - currentFont.getFontImage().getH()/2;
		}
		else if(yAlignment.equals("bottom"))
		{
			y = y - 1 - currentFont.getFontImage().getH();
		}
		
		for(int i = 0; i < length; i++)
		{
			charIndex = currentFont.getSymbolIndex().indexOf(text.charAt(i));
			
			for(int iy = 1; iy < currentFont.getFontImage().getH(); iy++)
			{
				for(int ix = 0; ix < currentFont.getWidths()[charIndex]; ix++)
				{
					int p = currentFont.getFontImage().getP()[(ix + currentFont.getOffsets()[charIndex] + 1) + iy * currentFont.getFontImage().getW()];
					if(p == 0xffffffff)
					{
						setPixel(x + charPosition + ix, y + iy, color);							
					}									
				}
			}
			charPosition += currentFont.getWidths()[charIndex] - 1 + currentFont.getFontSpacing();
		}
	}
	
	// Draws rectangle
	public void drawRect(int x, int y, int width, int heigth, int color)
	{
		for(int ix = 0; ix < width; ix++)
		{
			setPixel(ix + x, y, color);
		}
		
		for(int iy = 1; iy < heigth - 1; iy++)
		{
			setPixel(x, y + iy, color);
			setPixel(x + width - 1, y + iy, color);
		}
		
		for(int ix = 0; ix < width; ix++)
		{
			setPixel(ix + x, y + heigth - 1, color);
		}
	}
	
	// Draws filled rectangle
	public void drawFillRect(int x, int y, int width, int heigth, int color)
	{
		for(int iy = 0; iy < heigth; iy++)
		{
			for(int ix = 0; ix < width; ix++)
			{
				setPixel(x + ix, y + iy, color);
			}
		}
	}
	
	/*
	 *  So, this is a variation of Bresenham algorithm for drawing circles.
	 *  Sauce: https://web.engr.oregonstate.edu/~sllu/bcircle.pdf
	 *  It's written using delphi (yeah) so I just translated it to java.
	 */
	public void drawCircle(int x, int y, int radius, int color) 
	{ 
		int xx = radius;
		int yy = 0;
		int xChange = 1 - 2 * radius;
		int yChange = 1;
		int radiusError = 0;
		
		while(xx >= yy)
		{
			plot(xx, yy, x, y, color, radius);
			yy++;
			radiusError += yChange;
			yChange += 2;
			if(2 * radiusError + xChange > 0)
			{
				xx--;
				radiusError += xChange;
				xChange += 2;
			}
		}
    } 
	
	// This is the companion function for the circle function
	private void plot(int x, int y, int cx, int cy, int color, int r)
	{
		setPixel(cx + x + r, cy + y + r, color);
		setPixel(cx - x + r, cy + y + r, color);
		setPixel(cx - x + r, cy - y + r, color);
		setPixel(cx + x + r, cy - y + r, color);
		setPixel(cx + y + r, cy + x + r, color);
		setPixel(cx - y + r, cy + x + r, color);
		setPixel(cx - y + r, cy - x + r, color);
		setPixel(cx + y + r, cy - x + r, color);
	}
	
	// https://stackoverflow.com/questions/1201200/fast-algorithm-for-drawing-filled-circles
	// Not very good but I think this will do for now...
	public void drawFillCircle (int x, int y, int radius, int color)
	{
		int xx = radius;
	    int yy = 0;
	    int xChange = 1 - (radius << 1);
	    int yChange = 0;
	    int radiusError = 0;

	    while (xx >= yy)
	    {
	        for (int i = x - xx; i <= x + xx; i++)
	        {
	            setPixel(i + radius, y + yy + radius, color);
	            setPixel(i + radius, y - yy + radius, color);
	        }
	        for (int i = x - yy; i <= x + yy; i++)
	        {
	            setPixel(i + radius, y + xx + radius, color);
	            setPixel(i + radius, y - xx + radius, color);
	        }

	        yy++;
	        radiusError += yChange;
	        yChange += 2;
	        if (((radiusError << 1) + xChange) > 0)
	        {
	            xx--;
	            radiusError += xChange;
	            xChange += 2;
	        }
	    }
	}
	
	/*
	 *  Another algorithm I copied from the internet! :))
	 *  https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#Java
	 *  It's Bresenham again.
	 */ 
	
	public void drawLine(int x1, int y1, int x2, int y2, int color) 
	{
        // delta of exact value and rounded value of the dependent variable
        int d = 0;
 
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
 
        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
 
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
 
        int x = x1;
        int y = y1;
 
        if (dx >= dy) 
        {
            while (true) 
            {
                setPixel(x, y , color);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) 
                {
                    y += iy;
                    d -= dx2;
                }
            }
        } 
        else 
        {
            while (true) 
            {
            	setPixel(x, y , color);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) 
                {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
}
