package de.freesoccerhdx.advancedworldcreatorapi.biome;

import org.bukkit.Sound;

public class BiomeCaveSound {

    private Sound sound;
    private double tickchance;
    public BiomeCaveSound(Sound sound, double tickchance){
        if(sound == null) throw new IllegalArgumentException("Sound can't be null!");
        if(tickchance < 0 || tickchance > 1.0) throw new IllegalArgumentException("Tickchance is out of range (0.0 < tickchance <= 1.0)");

        this.sound = sound;
        this.tickchance = tickchance;
    }

    public Sound getSound() {
        return sound;
    }
    public double getTickChance() {
        return tickchance;
    }
}
