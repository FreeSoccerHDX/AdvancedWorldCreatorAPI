package de.freesoccerhdx.advancedworldcreatorapi.biome;

import de.freesoccerhdx.advancedworldcreatorapi.NMSHelper;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
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
    BiomeCarvers(ResourceKey<ConfiguredWorldCarver<?>> b) {
        //Registry<ConfiguredWorldCarver<?>> placedFeatureRegistry = ((CraftServer) Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.CONFIGURED_CARVER);
        WritableRegistry<ConfiguredWorldCarver<?>> placedFeatureRegistry = NMSHelper.getWritableRegistry(Registries.CONFIGURED_CARVER);
        carver = placedFeatureRegistry.get(b);
    }

    /**
     * Gets the internal ConfiguredWorldCarver
     *
     * @return The ConfiguredWorldCarver
     */
    protected ConfiguredWorldCarver<?> getCarver(){
        return carver;
    }

}
