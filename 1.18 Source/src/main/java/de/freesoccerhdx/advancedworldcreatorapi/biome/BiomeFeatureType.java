package de.freesoccerhdx.advancedworldcreatorapi.biome;


import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Represents whether a Generation-Canyon/Cave will be filled with Air or Water
 *
 * */
public enum BiomeFeatureType {

    AIR(GenerationStep.Carving.AIR),
    LIQUID(GenerationStep.Carving.LIQUID);

    private GenerationStep.Carving feature;
    BiomeFeatureType(GenerationStep.Carving a) {
        this.feature = a;
    }

    protected GenerationStep.Carving getFeature(){
        return feature;
    }

}
