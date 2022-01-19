package de.freesoccerhdx.advancedworldcreatorapi.biome;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;

/**
 * Represents existing Cave/Canyon Generation-Types
 *
 * */
public enum BiomeCarvers {
    CAVE(Carvers.CAVE),
    CAVE_EXTRA_UNDERGROUND(Carvers.CAVE_EXTRA_UNDERGROUND),
    CANYON(Carvers.CANYON),
    NETHER_CAVE(Carvers.NETHER_CAVE);

    private ConfiguredWorldCarver<?> carver;
    BiomeCarvers(ConfiguredWorldCarver<?> b) {
        carver = b;
    }
    protected ConfiguredWorldCarver<?> getCarver(){
        return carver;
    }

}
