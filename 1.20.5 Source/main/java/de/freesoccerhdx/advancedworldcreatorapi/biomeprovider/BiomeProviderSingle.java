package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.Arrays;
import java.util.List;


/**
 * BiomeProvider that places the same Biome in the World
 *
 * */
public class BiomeProviderSingle extends BiomeProvider {

    private BiomeProviderSingle(){

    }

    @Override
    public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        return null;
    }

    @Override
    public List<Biome> getBiomes(WorldInfo worldInfo) {
        return null;
    }


    /**
     * The Builder to create the {@link BiomeProviderSingle}
     *
     * */
    public static class Builder{

        private Biome biome;

        public Builder(Biome biome){
            this.biome = biome;
        }

        public BiomeProviderSingle create(){

            return new BiomeProviderSingle() {
                private Biome biomesThis = biome;

                @Override
                public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                   return biomesThis;
                }

                @Override
                public List<Biome> getBiomes(WorldInfo worldInfo) {
                    return Arrays.asList(biomesThis);
                }
            };

        }
    }

}
