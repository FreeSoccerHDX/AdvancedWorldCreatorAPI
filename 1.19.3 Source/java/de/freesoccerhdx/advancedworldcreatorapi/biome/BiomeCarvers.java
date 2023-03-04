package de.freesoccerhdx.advancedworldcreatorapi.biome;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;

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
        Registry<ConfiguredWorldCarver<?>> placedFeatureRegistry = ((CraftServer) Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.CONFIGURED_CARVER);

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
