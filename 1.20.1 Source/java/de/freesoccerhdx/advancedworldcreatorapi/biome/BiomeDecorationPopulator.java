package de.freesoccerhdx.advancedworldcreatorapi.biome;

import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.bukkit.HeightMap;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R1.CraftHeightMap;

import java.util.List;

public abstract class BiomeDecorationPopulator {

    private PlacementType[] placementTypes;

    private static PlacementType[] toPlacementTypesList(List<PlacementType> types) {
        return types.toArray(new PlacementType[0]);
    }

    public BiomeDecorationPopulator(List<PlacementType> placementTypes) {
        this(toPlacementTypesList(placementTypes));
    }

    public BiomeDecorationPopulator(PlacementType[] placementTypes) {
        if(placementTypes == null || placementTypes.length < 1) {
            throw new IllegalArgumentException("PlacementTypes must not be null and empty");
        }
        this.placementTypes = placementTypes;
    }

    protected final PlacementModifier[] getPlacementModifiers() {
        PlacementModifier[] mods = new PlacementModifier[placementTypes.length+1];
        for(int i = 0; i < placementTypes.length; i++) {
            mods[i] = placementTypes[i].getPlacementModifier();
        }
        mods[mods.length-1] = BiomeFilter.biome();

        return mods;
    }

    /**
     * Used to generate custom decoration at a specific location in the biome
     *
     * @param world The world that the decoration should be placed in
     * @param x The x position of the decoration (lowest X in the Chunk)
     * @param y The y position of the decoration (based on PlacementTypes)
     * @param z The z position of the decoration (lowest Z in the Chunk)
     */
    public abstract void populateDecoration(WorldModifier world, int x, int y, int z);

    public static abstract class WorldModifier {

        abstract public String getWorldName();
        abstract public BlockData getBlockData(int x, int y, int z);
        abstract public boolean setBlockData(int x, int y, int z, BlockData data);

    }

    public static abstract class PlacementType {
        abstract PlacementModifier getPlacementModifier();
    }

    public static class RangePlacementType extends PlacementType {

        private final int distanceBottom;
        private final int distanceTop;
        public RangePlacementType(int distanceBottom, int distanceTop) {
            this.distanceBottom = distanceBottom;
            this.distanceTop = distanceTop;
        }

        @Override
        PlacementModifier getPlacementModifier() {
            return HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(distanceBottom), VerticalAnchor.belowTop(distanceTop));
        }
    }


    public static class HeightmapPlacementType extends PlacementType {

        private final HeightMap heightmap;
        public HeightmapPlacementType(HeightMap heightMap) {
            if(heightMap == null) throw new IllegalArgumentException("HeightMap can't be null!");
            this.heightmap = heightMap;
        }

        public HeightMap getHeightmap() {
            return heightmap;
        }

        @Override
        PlacementModifier getPlacementModifier() {
            return HeightmapPlacement.onHeightmap(CraftHeightMap.toNMS(heightmap));
        }
    }




}
