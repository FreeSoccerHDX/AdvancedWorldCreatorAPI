package de.freesoccerhdx.advancedworldcreatorapi.biome;


import net.minecraft.world.level.biome.BiomeSpecialEffects;

/**
 * Represents all Modifier for the GrassColor.
 * If you want to use your own GrassColor you should use "NONE" within the {@link BiomeCreator}
 *
 * */
public enum BiomeGrassColorModifier {

    NONE(BiomeSpecialEffects.GrassColorModifier.NONE),
    DARK_FOREST(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST),
    SWAMP(BiomeSpecialEffects.GrassColorModifier.SWAMP);

    private BiomeSpecialEffects.GrassColorModifier modifier;

    BiomeGrassColorModifier(BiomeSpecialEffects.GrassColorModifier modi) {
        this.modifier = modi;
    }

    protected BiomeSpecialEffects.GrassColorModifier getGrassColorModifier() {
        return this.modifier;
    }




}
