package de.freesoccerhdx.advancedworldcreatorapi.biome;


import org.bukkit.Sound;

/**
 * Represents an Object that holds Data about the Cave-Sound that will
 * randomly play when the Player is in caves
 *
 * */
public record BiomeCaveSoundSettings(Sound sound, int tickDelay, int blockSearchExtent, double offset) {

    /**
     * @param sound The {@link Sound}-Type to use for this
     * @param tickDelay A delay in ticks before the Sound will play again
     * @param blockSearchExtent Maximum Block-Radius where the Sound will play
     * @param offset The Sound-Offset
     * */
    public BiomeCaveSoundSettings(Sound sound, int tickDelay, int blockSearchExtent, double offset){
        this.sound = sound;
        this.tickDelay = tickDelay;
        this.blockSearchExtent = blockSearchExtent;
        this.offset = offset;
    }

    /**
     * Gets the {@link Sound}-Type to use
     *
     * @return The Sound-Type that will be used
     * */
    public Sound getSound() {
        return sound;
    }

    /**
     * Gets the Sound-Offset which is used to customaze the Sound a little bit
     *
     * @return The Sound-Offset
     * */
    public double getOffset() {
        return offset;
    }

    /**
     * Gets the Maximum Block-Range where the Sound can play
     *
     * @return The Block-Range
     * */
    public int getBlockSearchExtent() {
        return blockSearchExtent;
    }

    /**
     * Gets the Delay before this Sound will play again in ticks
     *
     * @return The Delay in ticks
     * */
    public int getTickDelay() {
        return tickDelay;
    }
}
