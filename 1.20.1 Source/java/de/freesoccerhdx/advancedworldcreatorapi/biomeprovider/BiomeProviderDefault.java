package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.advancedworldcreatorapi.NMSHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
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
            //Registry<Biome> registry = ((CraftServer)Bukkit.getServer()).getServer().registryAccess().registryOrThrow(Registries.BIOME);
            WritableRegistry<Biome> registry = NMSHelper.getWritableRegistry(Registries.BIOME);


            return new BiomeProviderDefault() {

                private Function<org.bukkit.block.Biome, org.bukkit.block.Biome> biomeReplaceFunction = Builder.this.biomeReplaceFunction;
                private Climate.Sampler noiseSampler = null;
                //private HolderLookup<Biome> biomeHolderGetter = ((CraftServer)Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.BIOME).asLookup().filterFeatures(FeatureFlagSet.of());
                private HolderLookup<Biome> biomeHolderGetter = registry.asLookup().filterFeatures(FeatureFlagSet.of());
                private BiomeSource biomeSource = presetBiomes == PresetBiomes.OVERWORLD ? NMSHelper.getOverworldBiomeSource() : NMSHelper.getNetherBiomeSource();


                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {

                    if(noiseSampler == null){
                        CraftWorld craftWorld = (CraftWorld)Bukkit.getWorld(worldInfo.getName());
                        ServerLevel serverLevel = craftWorld.getHandle();
                        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().chunkMap.generator;
                        this.noiseSampler = (Climate.Sampler) serverLevel.getChunkSource().randomState().sampler();

                    }

                    Biome vanilla = biomeSource.getNoiseBiome(x>>2,y>>2,z>>2,noiseSampler).value();

                    if(biomeReplaceFunction != null){
                        org.bukkit.block.Biome bukkitchangebiome = biomeReplaceFunction.apply(CraftBlock.biomeBaseToBiome(registry, vanilla));

                        if(bukkitchangebiome != null){
                            vanilla = CraftBlock.biomeToBiomeBase(registry, bukkitchangebiome).value();
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
