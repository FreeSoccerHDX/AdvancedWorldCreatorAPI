package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.freesoccerhdx.advancedworldcreatorapi.NMSHelper;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider} that can place Biomes based on different Parameters specified with {@link NoiseData}
 *
 * */
public abstract class BiomeProviderMultiNoise extends AdvancedBiomeProvider {

    private BiomeProviderMultiNoise(){

    }

    /**
     * The Builder to create the {@link BiomeProviderMultiNoise}
     *
     * */
    public static class Builder{

        private HashMap<Object,NoiseData> noiseDatas = new HashMap<>();

        public Builder(){

        }

        public BiomeProviderMultiNoise create(){
            //Registry<Biome> registry = ((CraftServer)Bukkit.getServer()).getServer().registryAccess().registryOrThrow(Registries.BIOME);
            WritableRegistry<Biome> registry = NMSHelper.getWritableRegistry(Registries.BIOME);
            ImmutableList.Builder<Pair<Climate.ParameterPoint, Supplier<Biome>>> pairBuilder = ImmutableList.builder();

            for(Object biomeObj : noiseDatas.keySet()){
                NoiseData nD = noiseDatas.get(biomeObj);

                Climate.ParameterPoint parameterPoint = Climate.parameters(nD.temperature, nD.humidity, nD.continentalness,
                        nD.erosion, nD.depth, nD.weirdness, nD.offset);
                Biome nmsBiome = null;

                if(biomeObj instanceof org.bukkit.block.Biome) {
                    org.bukkit.block.Biome biome = (org.bukkit.block.Biome) biomeObj;
                    Preconditions.checkArgument(biome != org.bukkit.block.Biome.CUSTOM, "Cannot use the biome %s", biome);


                    Biome biomebase = CraftBlock.biomeToBiomeBase(registry, biome).value();
                   // Bukkit.broadcastMessage("§abiomebase for "+biomeObj+" -> " + biomebase);
                    nmsBiome = biomebase;
                }else if(biomeObj instanceof BiomeCreator.CustomBiome){
                    BiomeCreator.CustomBiome customBiome = (BiomeCreator.CustomBiome) biomeObj;
                    nmsBiome = customBiome.getNMSBiome().value();
                }


                final Biome finalNmsBiome = nmsBiome;
                pairBuilder.add(Pair.of(parameterPoint, () -> finalNmsBiome));

            }

           // BiomeSource biomeSource = MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(registry,true);

            Climate.ParameterList<Supplier<Biome>> parameterList = new Climate.ParameterList(pairBuilder.build());
/*
            Climate.ParameterList<Biome> list = new Climate.ParameterList(ImmutableList.of(Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
                return (Biome)registry.getOrThrow(net.minecraft.world.level.biome.Biomes.NETHER_WASTES);
            }), Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
                return (Biome)registry.getOrThrow(net.minecraft.world.level.biome.Biomes.SOUL_SAND_VALLEY);
            }), Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
                return (Biome)registry.getOrThrow(net.minecraft.world.level.biome.Biomes.CRIMSON_FOREST);
            }), Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), () -> {
                return (Biome)registry.getOrThrow(net.minecraft.world.level.biome.Biomes.WARPED_FOREST);
            }), Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), () -> {
                return (Biome)registry.getOrThrow(net.minecraft.world.level.biome.Biomes.BASALT_DELTAS);
            })));
*/

            return new BiomeProviderMultiNoise() {

                private Climate.Sampler noiseSampler = null;
                private MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
                //private Registry<Biome> placedFeatureRegistry = server.registries().compositeAccess().registryOrThrow(Registries.BIOME);
                //HolderLookup<Biome> biomeHolderGetter = server.registries().compositeAccess().registryOrThrow(Registries.BIOME).asLookup().filterFeatures(FeatureFlagSet.of());
                //private BiomeSource biomeSource = MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(biomeHolderGetter,true);
              //  protected Biome defaultVoidBiome = BuiltinRegistries.BIOME.get(Biomes.THE_VOID);

                private Supplier<Biome> getNoiseBiome(Climate.TargetPoint targetPoint) {
                    return parameterList.findValue(targetPoint);
                }

                private String getNMSBiomeName(Biome biome){
                    Optional<ResourceKey<Biome>> optional = server.registries().compositeAccess().registryOrThrow(Registries.BIOME).getResourceKey(biome);

                    if(optional.isPresent()){
                        return optional.get().location().toString();
                    }
                    //CraftServer craftServer = (CraftServer) Bukkit.getServer();
                    //RegistryAccess registryAccess = craftServer.getServer().registryAccess();
                    //WritableRegistry<Biome> biomeRegistry = (WritableRegistry<Biome>) registryAccess.registryOrThrow(Registries.BIOME);

                    WritableRegistry<Biome> biomeRegistry = NMSHelper.getWritableRegistry(Registries.BIOME);


                    ResourceLocation location = biomeRegistry.getKey(biome);
                    if(location != null){
                        return location.toString();
                    }

                    return null;
                }

                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {

                    if(noiseSampler == null){
                        CraftWorld craftWorld = (CraftWorld)Bukkit.getWorld(worldInfo.getName());
                        ServerLevel serverLevel = craftWorld.getHandle();
                        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().chunkMap.generator;
                        this.noiseSampler = serverLevel.getChunkSource().randomState().sampler();

                    }

                    //Biome vanilla = biomeSource.getNoiseBiome(x,y,z,noiseSampler);
                    Biome obj = getNoiseBiome(noiseSampler.sample(x,y,z)).get();
                    //System.out.println("Obj: " + obj + " -> "+ getNMSBiomeName(obj) + " to " + getNMSBiomeName(vanilla));

                    return obj;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    /*
                    int i = noiseDatas.size();
                    while (i < 40) {
                        noiseDatas.put(new ArrayList<>(noiseDatas.keySet()).get(0),noiseDatas.get(0));
                        i++;
                    }
                    */

                    return new ArrayList<>(noiseDatas.keySet());
                }
            };
        }

        public Set<Object> getBiomes(){
            return noiseDatas.keySet();
        }

        public HashMap<Object,NoiseData> getBiomeNoiseData(){
            return noiseDatas;
        }

        public Builder addBiome(Object biome, NoiseData noiseData){
            noiseDatas.put(biome,noiseData);
            return this;
        }
    }

    /**
     * Represents the data that will be used for the Multi-Noise Biome generation
     *
     * */
    public static class NoiseData{

        final float temperature;
        final float humidity;
        final float continentalness;
        final float erosion;
        final float depth;
        final float weirdness;
        final float offset;

        /**
         * Constructor for the NoiseData
         *
         * @param temperature The Value of the temperature
         * @param humidity The Value of the humidity
         * @param continentalness The Value of the continentalness
         * @param erosion The Value of the erosion
         * @param depth The Value of the depth
         * @param weirdness The Value of the weirdness
         * @param offset The Value of the offset
         *
         * */
        public NoiseData(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, float offset){

            this.temperature = temperature;
            this.humidity = humidity;
            this.continentalness = continentalness;
            this.erosion = erosion;
            this.depth = depth;
            this.weirdness = weirdness;
            this.offset = offset;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The offset
         * */
        public float getOffset() {
            return offset;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The temperature
         * */
        public float getTemperature() {
            return temperature;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The humidity
         * */
        public float getHumidity() {
            return humidity;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The continentalness
         * */
        public float getContinentalness() {
            return continentalness;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The erosion
         * */
        public float getErosion() {
            return erosion;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The depth
         * */
        public float getDepth() {
            return depth;
        }

        /**
         * Gets the offset for the NoiseData
         *
         * @return The weirdness
         * */
        public float getWeirdness() {
            return weirdness;
        }
    }


}
