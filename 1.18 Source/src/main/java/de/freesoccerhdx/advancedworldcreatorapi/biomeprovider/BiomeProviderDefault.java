package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.advancedworldcreatorapi.biome.AdvancedBiomeProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseSampler;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider} that can place Biomes based on different Parameters specified with {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderMultiNoise.NoiseData}
 *
 * */
public abstract class BiomeProviderDefault extends AdvancedBiomeProvider {

    private BiomeProviderDefault(){

    }

    /**
     * The Builder to create the {@link BiomeProviderDefault}
     *
     * */
    public static class Builder{
        private Function<org.bukkit.block.Biome, org.bukkit.block.Biome> biomeReplaceFunction;
        private PresetBiomes presetBiomes;

        public Builder(Function<org.bukkit.block.Biome, org.bukkit.block.Biome> biomeReplaceFunction, PresetBiomes presetBiomes){
            this.biomeReplaceFunction = biomeReplaceFunction;
            this.presetBiomes = presetBiomes;
        }


        public BiomeProviderDefault create(){
            WritableRegistry<Biome> registry = ((CraftServer)Bukkit.getServer()).getServer().registryHolder.ownedRegistryOrThrow(Registry.BIOME_REGISTRY);

            return new BiomeProviderDefault() {

                private Function<org.bukkit.block.Biome, org.bukkit.block.Biome> biomeReplaceFunction = Builder.this.biomeReplaceFunction;
                private NoiseSampler noiseSampler = null;
                private BiomeSource biomeSource = presetBiomes == PresetBiomes.OVERWORLD ? MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(registry,true) : MultiNoiseBiomeSource.Preset.NETHER.biomeSource(registry,true);


                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {

                    if(noiseSampler == null){
                        CraftWorld craftWorld = (CraftWorld)Bukkit.getWorld(worldInfo.getName());
                        ServerLevel serverLevel = craftWorld.getHandle();
                        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().chunkMap.generator;
                        this.noiseSampler = (NoiseSampler) chunkGenerator.climateSampler();

                    }

                    Biome vanilla = biomeSource.getNoiseBiome(x>>2,y>>2,z>>2,noiseSampler);

                    if(biomeReplaceFunction != null){
                        org.bukkit.block.Biome bukkitchangebiome = biomeReplaceFunction.apply(CraftBlock.biomeBaseToBiome(registry, vanilla));

                        if(bukkitchangebiome != null){
                            vanilla = CraftBlock.biomeToBiomeBase(registry, bukkitchangebiome);
                        }

                    }


                    return vanilla;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    List<Object> biomes = new ArrayList<>();
                    for(org.bukkit.block.Biome biome : org.bukkit.block.Biome.values()) {
                        if(biome != org.bukkit.block.Biome.CUSTOM){
                            biomes.add(biome);
                        }
                    }

                    return biomes;
                }
            };
        }
    }


    public static enum PresetBiomes{

        OVERWORLD,
        NETHER;

    }


}
