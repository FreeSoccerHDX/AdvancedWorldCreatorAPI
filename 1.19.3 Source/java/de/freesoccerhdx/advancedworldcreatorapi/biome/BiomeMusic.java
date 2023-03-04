package de.freesoccerhdx.advancedworldcreatorapi.biome;

import org.bukkit.Sound;

/**
 * Represents an Object to store Data about the Music to play in a CustomBiome
 *
 * */
public class BiomeMusic {

    private Sound sound;
    private int mindelay;
    private int maxdelay;
    private boolean replace_current_music;

    /**
     * Creates the BiomeMusic-Object with provided Data
     *
     * @param sound The Sound/Music to play
     * @param min_delay Minimum ticks before Music will start
     * @param max_delay Maximum ticks before Music will start
     * @param replace_current_music Whether it will replace active Music or not
     * */
    public BiomeMusic(Sound sound, int min_delay, int max_delay, boolean replace_current_music){
        if(sound == null) throw new IllegalArgumentException("Sound can't be null!");
        if(min_delay < 0) throw new IllegalArgumentException("Min_Delay has to be greater 0!");
        if(min_delay < max_delay) throw new IllegalArgumentException("Max_Delay has to be greater or equal Min_Delay!");

        this.sound = sound;
        this.mindelay = min_delay;
        this.maxdelay = max_delay;
        this.replace_current_music = replace_current_music;

    }

    /**
     * Gets the Sound/Music that will randomly play
     *
     * @return The Sound/Music to play
     * */
    public Sound getSound(){
        return sound;
    }

    /**
     * Gets the Minimum ticks before the Music starts
     *
     * @return the min delay in ticks
     * */
    public int getMinDelay(){
        return mindelay;
    }

    /**
     * Gets the Maximum ticks before the Music starts
     *
     * @return the max delay in ticks
     * */
    public int getMaxDelay(){
        return maxdelay;
    }

    /**
     * Gets if this CustomBiome-Music will replace another playing Music or not
     *
     * @return If replace or not
     * */
    public boolean isReplacingCurrentMusic(){
        return replace_current_music;
    }


}
