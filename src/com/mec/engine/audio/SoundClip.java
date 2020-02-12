package com.mec.engine.audio;

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

public class SoundClip 
{
    private Clip clip = null;
    private FloatControl volumeControl;

    public SoundClip(String path) 
    {
        try
        {
            InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);

            clip = AudioSystem.getClip();
            clip.open(decodedAudio);

            volumeControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
    }

    public void playAudio()
    {
        if (clip == null) return;

        stopAudio();
        clip.setFramePosition(0);
        
        while(!clip.isRunning())
        {
            clip.start();
        }
    }

    public void stopAudio()
    {
        if(clip.isRunning()) clip.stop();
    }

    public void closeAudio()
    {
        stopAudio();
        clip.drain();
        clip.close();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        playAudio();
    }

    public void setVolume(float volume)
    {
        volumeControl.setValue(volume);
    }

    public boolean isRunning()
    {
        return clip.isRunning();
    }
}