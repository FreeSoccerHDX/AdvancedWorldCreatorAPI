package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * BiomeProvider that places Biomes from in small squares around the coordinates x:0/z:0
 *
 * */
public abstract class BiomeProviderSquares extends BiomeProvider {

    private BiomeProviderSquares(){

    }

    /**
     * The Builder to create the {@link BiomeProviderSquares}
     *
     * */
    public static class Builder{

        private int size;
        private List<Biome> biomes = new ArrayList<>();

        public Builder(int size){
            this.size = size;
        }

        public BiomeProviderSquares create(){

            return new BiomeProviderSquares() {
                private int sizeThis = size;
                private List<Biome> biomesThis = biomes;

                @Override
                public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    int dir = Math.max(Math.abs(x), Math.abs(z));

                   // return (BiomeBase) ((Supplier<?>) this.biomes.get(Math.floorMod((dir >> this.biomesize), this.biomes.size()))).get();


                    Biome target = biomesThis.get(Math.floorMod((dir >> this.sizeThis), this.biomesThis.size()));
                    return target;
                }

                @Override
                public List<Biome> getBiomes(WorldInfo worldInfo) {
                    return biomesThis;
                }
            };

        }


        public BiomeProviderSquares.Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public BiomeProviderSquares.Builder addBiome(Biome biome){
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
