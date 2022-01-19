package de.freesoccerhdx.advancedworldcreatorapi.biomeprovider;

import de.freesoccerhdx.advancedworldcreatorapi.biome.AdvancedBiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider}  that places the given Biomes in lines with a specific width
 *
 * */
public abstract class BiomeProviderLines extends AdvancedBiomeProvider {

    private BiomeProviderLines(){

    }

    /**
     * The Builder to create the {@link BiomeProviderLines}
     *
     * */
    public static class Builder{

        private int size;
        private boolean xDir;
        private List<Object> biomes = new ArrayList<>();

        public Builder(int size, boolean xDir){
            this.size = size;
            this.xDir = xDir;
        }

        public BiomeProviderLines create(){

            return new BiomeProviderLines() {
                private int sizeThis = size;
                private boolean xDirThis = xDir;
                private List<Object> biomesThis = biomes;

                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    int dir = x;

                    if(!xDir) {
                        dir = z;
                    }

                    Object target = biomesThis.get(Math.floorMod((dir >> this.sizeThis), this.biomesThis.size()));
                    return target;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
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

        public Builder addBiome(Object biome){
            biomes.add(biome);
            return this;
        }


        public int getSize() {
            return size;
        }

        public boolean isxDir() {
            return xDir;
        }

        public List<Object> getBiomes() {
            return biomes;
        }
    }

}
