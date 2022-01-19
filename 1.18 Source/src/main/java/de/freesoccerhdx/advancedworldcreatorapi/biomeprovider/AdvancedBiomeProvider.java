package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;

import java.util.List;

/**
 * Advanced BiomeProvider that also allows {@link de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator.CustomBiome} as input.
 * Any Object that those Methods want you to return should only contain {@link Biome} or {@link de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator.CustomBiome}
 *
 * */
public abstract class AdvancedBiomeProvider {

    /**
     * Gets the specific Biome for given x/y/z - coordination
     *
     * @return A {@link Biome} or {@link BiomeCreator.CustomBiome}
     * */
    public abstract Object getBiome(WorldInfo worldInfo, int x, int y, int z);

    /**
     * This Method has to list all Biomes you want to use for the BiomeProvider.
     *
     *
     * @return A List of {@link Biome} and {@link BiomeCreator.CustomBiome}
     * to use for the BiomeProvider
     * */
    public abstract List<Object> getBiomes(WorldInfo worldInfo);

}
