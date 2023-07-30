package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider}  that places Biomes like a CheckerBoard
 *
 * */
public abstract class BiomeProviderCheckerBoard extends AdvancedBiomeProvider {

    private BiomeProviderCheckerBoard(){
    }

    /**
     * The Builder to create the {@link BiomeProviderCheckerBoard}
     *
     * */
    public static class Builder{

        private int size;
        private List<Object> biomes = new ArrayList<>();

        public Builder(int gridsize){
            this.size = gridsize;
        }

        public BiomeProviderCheckerBoard create(){

            return new BiomeProviderCheckerBoard() {
                private int sizeThis = size;
                private List<Object> biomesThis = biomes;

                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    Object target = biomesThis.get(Math.floorMod((x >> this.sizeThis) + (z >> this.sizeThis), this.biomesThis.size()));
                    return target;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    return biomes;
                }
            };

        }


        public BiomeProviderCheckerBoard.Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public BiomeProviderCheckerBoard.Builder addBiome(Object biome){
            biomes.add(biome);
            return this;
        }


        public int getSize() {
            return size;
        }

        public List<Object> getBiomes() {
            return biomes;
        }
    }

}
