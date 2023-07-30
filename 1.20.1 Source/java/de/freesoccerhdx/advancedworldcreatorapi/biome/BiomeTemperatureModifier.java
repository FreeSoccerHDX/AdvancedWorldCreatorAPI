package de.freesoccerhdx.advancedworldcreatorapi.biome;


import net.minecraft.world.level.biome.Biome;

/**
 * Represents the TemperatureModifier available.
 * (No changes from this Value known.)
 *
 * */
public enum BiomeTemperatureModifier {

    NONE(Biome.TemperatureModifier.NONE),
    FROZEN(Biome.TemperatureModifier.FROZEN);

    private Biome.TemperatureModifier modifier;
    BiomeTemperatureModifier(Biome.TemperatureModifier modifier){
        this.modifier = modifier;
    }

    protected Biome.TemperatureModifier getTemperaturModifier(){
        return this.modifier;
    }

}
