package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.mcutils.McNMSSpecific;
import de.freesoccerhdx.mcutils.NMSHelper;
import de.freesoccerhdx.mcutils.ReflectionHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R4.block.CraftBiome;
import org.bukkit.craftbukkit.v1_21_R4.block.CraftBlock;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * BiomeProvider that can place Biomes based on different Parameters specified with {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderMultiNoise.NoiseData}
 *
 * */
public abstract class BiomeProviderDefault extends BiomeProvider {

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
                private HolderLookup<Biome> biomeHolderGetter = registry.filterFeatures(FeatureFlagSet.of());
                private BiomeSource biomeSource = presetBiomes == PresetBiomes.OVERWORLD ? McNMSSpecific.getOverworldBiomeSource() : McNMSSpecific.getNetherBiomeSource();


                @Override
                public org.bukkit.block.Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {

                    if(noiseSampler == null){
                        CraftWorld craftWorld = (CraftWorld)Bukkit.getWorld(worldInfo.getName());
                        ServerLevel serverLevel = craftWorld.getHandle();
                        //ChunkGenerator chunkGenerator = serverLevel.getChunkSource().chunkMap.generator;
//                        ChunkGenerator chunkGenerator = (ChunkGenerator) ReflectionHelper.callMethodByReturnType(ChunkMap.class, serverLevel.getChunkSource().chunkMap, org.bukkit.generator.ChunkGenerator.class, new Object[0]);
//                                //(ChunkGenerator) NMSHelper.callProtectedMethod(serverLevel.getChunkSource().chunkMap,
                                //NMSHelper.findMethodWithReturnType(serverLevel.getChunkSource().chunkMap, org.bukkit.generator.ChunkGenerator.class),
                                //new Object[0]);

                        this.noiseSampler = serverLevel.getChunkSource().randomState().sampler();

                    }

                    Biome vanilla = biomeSource.getNoiseBiome(x>>2,y>>2,z>>2,noiseSampler).value();
                    org.bukkit.block.Biome bukkitchangebiome = CraftBiome.minecraftToBukkit(vanilla);

                    if(biomeReplaceFunction != null){
                        // 1.20.4 CraftBiome.bukkitToMinecraftHolder(biome);

                        // 1.20.1 biomeReplaceFunction.apply(CraftBlock.biomeBaseToBiome(registry, vanilla));
                        bukkitchangebiome = biomeReplaceFunction.apply(bukkitchangebiome);

//                        if(bukkitchangebiome != null){
//                            // 1.20.4 CraftBiome.bukkitToMinecraftHolder(biome);
//                            vanilla = CraftBiome.bukkitToMinecraft(bukkitchangebiome);
//                            // 1.20.1 vanilla = CraftBlock.biomeToBiomeBase(registry, bukkitchangebiome).value();
//                        }

                    }


                    return bukkitchangebiome;
                }

                @Override
                public List<org.bukkit.block.Biome> getBiomes(WorldInfo worldInfo) {
                    List<org.bukkit.block.Biome> biomes = new ArrayList<>();
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
