package com.rho.axion.misc;

public class Timer
{
	private boolean isActive = false;
	private int totalUpdates;
	private int remainingUpdates;
	
	public Timer (int updates)
	{
		this.totalUpdates = updates;
		this.remainingUpdates = updates;
	}

	public void decreaseRemainingUpdates()
	{
		if(remainingUpdates > 0)
		{
			remainingUpdates--;
		}
		else
		{
			remainingUpdates = totalUpdates;
			timerEvent();
		}
	}
	
	protected void timerEvent()
	{
		
	}
	
	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public int getRemainingUpdates()
	{
		return remainingUpdates;
	}

	public void setRemainingUpdates(int remainingUpdates)
	{
		this.remainingUpdates = remainingUpdates;
	}

	public int getTotalUpdates()
	{
		return totalUpdates;
	}
	
	
}
