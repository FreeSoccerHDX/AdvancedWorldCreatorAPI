package de.freesoccerhdx.advancedworldcreatorapi.biome;


import net.minecraft.world.level.biome.Biome;

/**
 * Represents all available Geography-Types
 * Controls just a few spawning things in the Biome
 * @see <a href="https://minecraft.fandom.com/wiki/Biome/JSON_format">JSON format</a>
 *
 * */
public enum BiomeGeography {

    NONE(Biome.BiomeCategory.NONE),
    TAIGA(Biome.BiomeCategory.TAIGA),
    EXTREME_HILLS(Biome.BiomeCategory.EXTREME_HILLS),
    JUNGLE(Biome.BiomeCategory.JUNGLE),
    MESA(Biome.BiomeCategory.MESA),
    PLAINS(Biome.BiomeCategory.PLAINS),
    SAVANNA(Biome.BiomeCategory.SAVANNA),
    ICY(Biome.BiomeCategory.ICY),
    THE_END(Biome.BiomeCategory.THEEND),
    BEACH(Biome.BiomeCategory.BEACH),
    FOREST(Biome.BiomeCategory.FOREST),
    OCEAN(Biome.BiomeCategory.OCEAN),
    DESERT(Biome.BiomeCategory.DESERT),
    RIVER(Biome.BiomeCategory.RIVER),
    SWAMP(Biome.BiomeCategory.SWAMP),
    MUSHROOM(Biome.BiomeCategory.MUSHROOM),
    NETHER(Biome.BiomeCategory.NETHER),
    UNDERGROUND(Biome.BiomeCategory.UNDERGROUND),
    MOUNTAIN(Biome.BiomeCategory.MOUNTAIN);

    private Biome.BiomeCategory geography;

    BiomeGeography(Biome.BiomeCategory geography){
        this.geography = geography;
    }

    protected Biome.BiomeCategory getGeography(){
        return this.geography;
    }

}
