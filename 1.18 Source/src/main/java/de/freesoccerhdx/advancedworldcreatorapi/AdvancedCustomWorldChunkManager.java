package de.freesoccerhdx.advancedworldcreatorapi;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import de.freesoccerhdx.advancedworldcreatorapi.biome.AdvancedBiomeProvider;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R1.generator.CustomWorldChunkManager;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdvancedCustomWorldChunkManager extends BiomeSource {
    private final WorldInfo worldInfo;
    private final AdvancedBiomeProvider biomeProvider;
    private final Registry<Biome> registry;

    private static List<Biome> biomeListToBiomeBaseList(List<Object> biomes, Registry<Biome> registry) {
        List<Biome> biomeBases = new ArrayList();
        Iterator var4 = biomes.iterator();

        while(var4.hasNext()) {
            Object next = var4.next();
            if(next instanceof org.bukkit.block.Biome) {
                org.bukkit.block.Biome biome = (org.bukkit.block.Biome) next;
                Preconditions.checkArgument(biome != org.bukkit.block.Biome.CUSTOM, "Cannot use the biome %s", biome);
                Biome biomebase = CraftBlock.biomeToBiomeBase(registry, biome);
             //   Bukkit.broadcastMessage("§abiomebase for "+next+" -> " + biomebase);
                biomeBases.add(biomebase);
            }else if(next instanceof BiomeCreator.CustomBiome){
                BiomeCreator.CustomBiome customBiome = (BiomeCreator.CustomBiome) next;
                biomeBases.add(customBiome.getNMSBiome());
            }else{
                biomeBases.add((Biome) next);
            }
        }

        return biomeBases;
    }

    public AdvancedCustomWorldChunkManager(WorldInfo worldInfo, AdvancedBiomeProvider biomeProvider, Registry<Biome> registry) {
        super(biomeListToBiomeBaseList(biomeProvider.getBiomes(worldInfo), registry));
        this.worldInfo = worldInfo;
        this.biomeProvider = biomeProvider;
        this.registry = registry;
    }

    protected Codec<? extends BiomeSource> codec() {
        throw new UnsupportedOperationException("Cannot serialize CustomWorldChunkManager");
    }

    public BiomeSource withSeed(long l) {
        throw new UnsupportedOperationException("Cannot copy CustomWorldChunkManager");
    }

    public Biome getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        Object biomeObj = this.biomeProvider.getBiome(this.worldInfo, x << 2, y << 2, z << 2);

        if(biomeObj instanceof org.bukkit.block.Biome) {
            org.bukkit.block.Biome biome = (org.bukkit.block.Biome) biomeObj;
            Preconditions.checkArgument(biome != org.bukkit.block.Biome.CUSTOM, "Cannot set the biome to %s", biome);
            return CraftBlock.biomeToBiomeBase(this.registry, biome);
        }else if(biomeObj instanceof BiomeCreator.CustomBiome){
            BiomeCreator.CustomBiome customBiome = (BiomeCreator.CustomBiome) biomeObj;
            return customBiome.getNMSBiome();
        }

        return (Biome) biomeObj;
    }

}
