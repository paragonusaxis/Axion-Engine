package com.rho.axion;

import java.util.ArrayList;
import com.rho.axion.misc.Timer;

/*
 * This module handles the update of all the timers object currently created.
 */
public class Time
{
	ArrayList<Timer> activeTimers = new ArrayList<>();
	
	public void update()
	{
		for(int i = 0; i < activeTimers.size(); i++)
		{
			if(activeTimers.get(i).isActive())
			{
				activeTimers.get(i).decreaseRemainingUpdates();
			}
			else
			{
				activeTimers.remove(i);
			}
		}
	}
	
	public void startTimer(Timer timer)
	{
		if(timer.isActive())
		{
			return;
		}
		timer.setActive(true);
		activeTimers.add(timer);
	}
	
	public void stopTimer(Timer timer)
	{
		timer.setActive(false);
		activeTimers.remove(timer);
	}
	
	public void restartTimer(Timer timer)
	{
		if(!timer.isActive())
		{
			timer.setActive(true);
			activeTimers.add(timer);
		}
		timer.setRemainingUpdates(timer.getTotalUpdates());
	}
}

