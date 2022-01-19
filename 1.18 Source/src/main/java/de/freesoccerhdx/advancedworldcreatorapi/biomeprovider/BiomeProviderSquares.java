package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.advancedworldcreatorapi.biome.AdvancedBiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider} that places Biomes from in small squares around the coordinates x:0/z:0
 *
 * */
public abstract class BiomeProviderSquares extends AdvancedBiomeProvider {

    private BiomeProviderSquares(){

    }

    /**
     * The Builder to create the {@link BiomeProviderSquares}
     *
     * */
    public static class Builder{

        private int size;
        private List<Object> biomes = new ArrayList<>();

        public Builder(int size){
            this.size = size;
        }

        public BiomeProviderSquares create(){

            return new BiomeProviderSquares() {
                private int sizeThis = size;
                private List<Object> biomesThis = biomes;

                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    int dir = Math.max(Math.abs(x), Math.abs(z));

                   // return (BiomeBase) ((Supplier<?>) this.biomes.get(Math.floorMod((dir >> this.biomesize), this.biomes.size()))).get();


                    Object target = biomesThis.get(Math.floorMod((dir >> this.sizeThis), this.biomesThis.size()));
                    return target;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    return biomesThis;
                }
            };

        }


        public BiomeProviderSquares.Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public BiomeProviderSquares.Builder addBiome(Object biome){
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
