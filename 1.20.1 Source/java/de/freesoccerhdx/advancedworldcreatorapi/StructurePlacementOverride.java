package de.freesoccerhdx.advancedworldcreatorapi;

import com.mojang.datafixers.util.Pair;

import java.util.HashMap;

public class StructurePlacementOverride {


    private HashMap<StructurePlacementType, Pair<Integer, Integer>> overrides = new HashMap<>();

    public StructurePlacementOverride() {

    }



    public void setStructurePlacement(StructurePlacementType type, int spacing, int separation) {
        overrides.put(type, new Pair<>(spacing, separation));
    }

    public boolean removeStructurePlacement(StructurePlacementType type) {
        return overrides.remove(type) != null;
    }

    public HashMap<StructurePlacementType, Pair<Integer, Integer>> getStructurePlacementOverrides() {
        return overrides;
    }

    public enum StructurePlacementType {

        ANCIENT_CITY,
        BURIED_TREASURE,
        DESERT_PYRAMID,
        END_CITY,
        IGLOO,
        JUNGLE_PYRAMID,
        MINESHAFT,
        FORTRESS,
        NETHER_FOSSIL,
        MONUMENT,
        OCEAN_RUIN_COLD,
        PILLAGER_OUTPOST,
        RUINED_PORTAL,
        SHIPWRECK,
        // TODO: STRONGHOLD can't be modified
        //STRONGHOLD,
        SWAMP_HUT,
        TRAIL_RUINS,
        VILLAGE,
        MANSION


    }


}
