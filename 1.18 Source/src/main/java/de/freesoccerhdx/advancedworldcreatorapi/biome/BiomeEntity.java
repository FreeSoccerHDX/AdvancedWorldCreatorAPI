package de.freesoccerhdx.advancedworldcreatorapi.biome;

import org.bukkit.entity.EntityType;

public class BiomeEntity {

    private EntityType type;
    private int weight;
    private int mincount;
    private int maxcount;

    public BiomeEntity(EntityType type, int weight, int mincount, int maxcount){
        this.type = type;
        this.weight = weight;
        this.mincount = mincount;
        this.maxcount = maxcount;

    }

    public EntityType getEntityType(){
        return type;
    }
    public int getWeight(){
        return weight;
    }
    public int getMinCount(){
        return mincount;
    }
    public int getMaxCount(){
        return maxcount;
    }



}
