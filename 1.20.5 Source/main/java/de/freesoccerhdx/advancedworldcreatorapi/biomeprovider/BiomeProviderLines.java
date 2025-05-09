package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * BiomeProvider that places the given Biomes in lines with a specific width
 *
 * */
public abstract class BiomeProviderLines extends BiomeProvider {

    private BiomeProviderLines(){

    }

    /**
     * The Builder to create the {@link BiomeProviderLines}
     *
     * */
    public static class Builder{

        private int size;
        private boolean xDir;
        private List<Biome> biomes = new ArrayList<>();

        public Builder(int size, boolean xDir){
            this.size = size;
            this.xDir = xDir;
        }

        public BiomeProviderLines create(){

            return new BiomeProviderLines() {
                private int sizeThis = size;
                private boolean xDirThis = xDir;
                private List<Biome> biomesThis = biomes;

                @Override
                public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    int dir = x;

                    if(!xDir) {
                        dir = z;
                    }
                    // Get the biome from the list which is calculated by the dir-value and the size
                    // example: dir = 5, size = 10, biomes.size() = 2 would return the biome at index 0
                    // example: dir = 15, size = 10, biomes.size() = 2 would return the biome at index 1
                    // example: dir = 25, size = 10, biomes.size() = 2 would return the biome at index 0

                    return biomesThis.get(Math.abs(dir/sizeThis % biomesThis.size()));

                    //Object target = biomesThis.get(Math.floorMod((dir >> this.sizeThis), this.biomesThis.size()));
                    //return target;
                }

                @Override
                public List<Biome> getBiomes(WorldInfo worldInfo) {
                    return biomesThis;
                }
            };

        }


        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setxDir(boolean xDir) {
            this.xDir = xDir;
            return this;
        }

        public Builder addBiome(Biome biome){
            biomes.add(biome);
            return this;
        }


        public int getSize() {
            return size;
        }

        public boolean isxDir() {
            return xDir;
        }

        public List<Biome> getBiomes() {
            return biomes;
        }
    }

}
