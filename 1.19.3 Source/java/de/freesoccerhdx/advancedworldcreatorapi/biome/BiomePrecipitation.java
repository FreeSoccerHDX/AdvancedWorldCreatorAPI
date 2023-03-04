package de.freesoccerhdx.advancedworldcreatorapi.biome;


import net.minecraft.world.level.biome.Biome;


/**
 * Represents all types of precipitations
 * Defines whether it will rain, snow or nothing happens.
 *
 * */
public enum BiomePrecipitation {

    NONE(Biome.Precipitation.NONE),
    RAIN(Biome.Precipitation.RAIN),
    SNOW(Biome.Precipitation.SNOW);

    private Biome.Precipitation precipitation;
    BiomePrecipitation(Biome.Precipitation precipitation){
        this.precipitation = precipitation;
    }

    protected Biome.Precipitation getPrecipitation(){
        return this.precipitation;
    }

}
