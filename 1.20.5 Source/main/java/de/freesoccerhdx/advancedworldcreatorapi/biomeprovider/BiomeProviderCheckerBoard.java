package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple BiomeProvider that places Biomes like a CheckerBoard
 *
 * */
public abstract class BiomeProviderCheckerBoard extends BiomeProvider {

    private BiomeProviderCheckerBoard(){
    }

    /**
     * The Builder to create the {@link BiomeProviderCheckerBoard}
     *
     * */
    public static class Builder{

        private int size;
        private List<Biome> biomes = new ArrayList<>();

        public Builder(int gridsize){
            this.size = gridsize;
        }

        public BiomeProviderCheckerBoard create(){

            return new BiomeProviderCheckerBoard() {
                private int sizeThis = size;
                private List<Biome> biomesThis = biomes;

                @Override
                public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    Biome target = biomesThis.get(Math.floorMod((x >> this.sizeThis) + (z >> this.sizeThis), this.biomesThis.size()));
                    return target;
                }

                @Override
                public List<Biome> getBiomes(WorldInfo worldInfo) {
                    return biomes;
                }
            };

        }


        public BiomeProviderCheckerBoard.Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public BiomeProviderCheckerBoard.Builder addBiome(Biome biome){
            biomes.add(biome);
            return this;
        }


        public int getSize() {
            return size;
        }

        public List<Biome> getBiomes() {
            return biomes;
        }
    }

}
