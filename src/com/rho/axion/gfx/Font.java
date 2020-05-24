package com.rho.axion.gfx;

import java.util.ArrayList;

// This is Axion's default Font class
public class Font
{
	private Image fontImage;
	private ArrayList<Character> symbolIndex;
	private int[] offsets;
	private int[] widths;
	private int fontSpacing = 1;
	
	/*
	 * In order to use this class, you need to pass to the constructor the path to the font image and also
	 * a string with the characters contained in that image IN ORDER!! The symbols must be all in the same row.
	 * I don't know what I'm doing, so the font will have a static kerning. Make sure the symbols in the image are aligned
	 * properly. If you need, please refer to the default font in the engine's resource folder, which I have not yet added,
	 * so jokes on you.
	 * (I've added it now!)
	 */
	public Font(String path, String symbols)
	{
		symbolIndex = new ArrayList<>();
		fontImage = new Image(path);
		int length = symbols.length();
		offsets = new int[length + 1];
		widths = new int[length + 1];
		int index = 0;
		
		for(int i = 0; i < length; i++)
		{
			symbolIndex.add(symbols.charAt(i));
		}
		
		for(int i = 0; i < fontImage.getW(); i++)
		{
			if(fontImage.getP()[i] == 0xff0000ff)
			{
				offsets[index] = i;
			}
			else if(fontImage.getP()[i] == 0xffffff00)
			{
				widths[index] = i - offsets[index];
				index++;
			}
		}
		
	}

	public Image getFontImage()
	{
		return fontImage;
	}

	public ArrayList<Character> getSymbolIndex()
	{
		return symbolIndex;
	}

	public int[] getOffsets()
	{
		return offsets;
	}

	public int[] getWidths()
	{
		return widths;
	}

	public int getFontSpacing()
	{
		return fontSpacing;
	}

	public void setFontSpacing(int font_spacing)
	{
		this.fontSpacing = font_spacing;
	}
}
