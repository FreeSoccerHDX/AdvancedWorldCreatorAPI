package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.generator.WorldInfo;

import java.util.Arrays;
import java.util.List;


/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider} that places the same Biome in the World
 *
 * */
public class BiomeProviderSingle extends AdvancedBiomeProvider {

    private BiomeProviderSingle(){

    }

    @Override
    public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
        return null;
    }

    @Override
    public List<Object> getBiomes(WorldInfo worldInfo) {
        return null;
    }


    /**
     * The Builder to create the {@link BiomeProviderSingle}
     *
     * */
    public static class Builder{

        private Object biome;

        public Builder(Object biome){
            this.biome = biome;
        }

        public BiomeProviderSingle create(){

            return new BiomeProviderSingle() {
                private Object biomesThis = biome;

                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
                   return biomesThis;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    return Arrays.asList(biomesThis);
                }
            };

        }
    }

}
