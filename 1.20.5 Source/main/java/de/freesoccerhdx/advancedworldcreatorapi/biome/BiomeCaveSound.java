package de.freesoccerhdx.advancedworldcreatorapi.biome;

import org.bukkit.Sound;

/**
 * Used to define the sounds that should be played in a biome in a cave
 */
public class BiomeCaveSound {

    private Sound sound;
    private double tickchance;

    /**
     * Creates the BiomeCaveSound object which can set to a CustomBiome
     *
     * @param sound The sound that should be played
     * @param tickchance The chance that the sound should be played (0.0 - 1.0)
     */
    public BiomeCaveSound(Sound sound, double tickchance){
        if(sound == null) throw new IllegalArgumentException("Sound can't be null!");
        if(tickchance < 0 || tickchance > 1.0) throw new IllegalArgumentException("Tickchance is out of range (0.0 < tickchance <= 1.0)");

        this.sound = sound;
        this.tickchance = tickchance;
    }

    /**
     * Gets the sound that should be played
     *
     * @return the sound
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Gets the chance that the sound should be played (0.0 - 1.0) per tick
     *
     * @return the tickchance
     */
    public double getTickChance() {
        return tickchance;
    }
}
