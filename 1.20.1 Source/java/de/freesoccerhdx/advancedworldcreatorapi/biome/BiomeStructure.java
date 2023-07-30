package de.freesoccerhdx.advancedworldcreatorapi.biome;

import de.freesoccerhdx.advancedworldcreatorapi.NMSHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.bukkit.Bukkit;


/**
 * Represents all larger Structures like Villages and Mineshafts
 * that can be whitelisted in a {@link BiomeCreator}
 *
 * */
public enum BiomeStructure {

    PILLAGER_OUTPOST("pillager_outpost"),
    MINESHAFT("mineshaft"),
    MINESHAFT_MESA("mineshaft_mesa"),
    MANSION("mansion"),
    JUNGLE_PYRAMID("jungle_pyramid"),
    DESERT_PYRAMID("desert_pyramid"),
    IGLOO("igloo"),
    SHIPWRECK("shipwreck"),
    SHIPWRECK_BEACHED("shipwreck_beached"),
    SWAMP_HUT("swamp_hut"),
    STRONGHOLD("stronghold"),
    MONUMENT("monument"),
    OCEAN_RUIN_COLD("ocean_ruin_cold"),
    OCEAN_RUIN_WARM("ocean_ruin_warm"),
    FORTRESS("fortress"),
    NETHER_FOSSIL("nether_fossil"),
    END_CITY("end_city"),
    BURIED_TREASURE("buried_treasure"),
    BASTION_REMNANT("bastion_remnant"),
    VILLAGE_PLAINS("village_plains"),
    VILLAGE_DESERT("village_desert"),
    VILLAGE_SAVANNA("village_savanna"),
    VILLAGE_SNOWY("village_snowy"),
    VILLAGE_TAIGA("village_taiga"),
    RUINED_PORTAL("ruined_portal"),
    RUINED_PORTAL_DESERT("ruined_portal_desert"),
    RUINED_PORTAL_JUNGLE("ruined_portal_jungle"),
    RUINED_PORTAL_SWAMP("ruined_portal_swamp"),
    RUINED_PORTAL_MOUNTAIN("ruined_portal_mountain"),
    RUINED_PORTAL_OCEAN("ruined_portal_ocean"),
    RUINED_PORTAL_NETHER("ruined_portal_nether");

    private String name;
    private ConfiguredFeature<?, ?> sf;

    BiomeStructure(String name){
        this.name = name;
        //MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        //Registry<ConfiguredFeature> placedFeatureRegistry = server.registries().compositeAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        WritableRegistry<ConfiguredFeature<?,?>> placedFeatureRegistry = NMSHelper.getWritableRegistry(Registries.CONFIGURED_FEATURE);

        this.sf = placedFeatureRegistry.get(new ResourceLocation(name));
    }

    /**
     * Gets the proper nms-Name of the Structure
     *
     * @return String
     * */
    public String getName() {

        return name;
    }

    protected ConfiguredFeature<?, ?> getStructureFeature(){
        return sf;
    }

}
