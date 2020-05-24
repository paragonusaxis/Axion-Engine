package com.rho.axion.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// This class handles our audio inputs
public class SoundClip
{
	private Clip clip = null;
	private FloatControl gainControl;
	
	// Creates our sound clip by creating a new decoded input stream
	public SoundClip(String path)
	{
		try
		{
			InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);		
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
														baseFormat.getSampleRate(),
														16,
														baseFormat.getChannels(),
														baseFormat.getChannels() * 2,
														baseFormat.getSampleRate(),
														false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			clip = AudioSystem.getClip();
			clip.open(dais);
			
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		} 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
	
	// Plays our clip
	public void play()
	{
		if(clip == null)
		{
			return;
		}
		
		// Stop the clip if running
		stop();
		// Reset clip
		clip.setFramePosition(0);
		// This loop makes sure our clip runs
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
	// Stop clip if currently running
	public void stop()
	{
		if(clip.isRunning())
		{
			clip.stop();
		}
	}
	
	// Closes the clip
	public void close()
	{
		stop();
		clip.drain();
		clip.close();
	}
	
	// Play clip in a loop
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	// Set the volume of the clip
	public void setVolume(float value)
	{
		gainControl.setValue(value);
	}
	
	// Utility function
	public boolean isRunning()
	{
		return clip.isRunning();
	}
}
