package de.freesoccerhdx.advancedworldcreatorapi.biome;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Represents an small Abstract Way to add custom Structures like Ores to the Biome
 *
 * */
public abstract class CustomBiomeFeature {

    /**
     * Should return a valid NMS-Feature for the Generation of the Biome
     *
     * @return The NMS-Feature
     * */
    public abstract PlacedFeature getFeature();

}
