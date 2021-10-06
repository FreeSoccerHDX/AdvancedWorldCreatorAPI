package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public enum BiomeStructure {
	
	// https://minecraft.gamepedia.com/Generated_structures
	
	PILLAGER_OUTPOST("pillager_outpost",StructureFeatures.a),
	MINESHAFT("mineshaft",StructureFeatures.b),
	MINESHAFT_MESA("mineshaft_mesa",StructureFeatures.c),
	MANSION("mansion",StructureFeatures.d),
	JUNGLE_PYRAMID("jungle_pyramid",StructureFeatures.e),
	DESERT_PYRAMID("desert_pyramid",StructureFeatures.f),
	IGLOO("igloo",StructureFeatures.g),
	SHIPWRECK("shipwreck",StructureFeatures.h),
	SHIPWRECK_BEACHED("shipwreck_beached",StructureFeatures.i),
	SWAMP_HUT("swamp_hut",StructureFeatures.j),
	STRONGHOLD("stronghold",StructureFeatures.k),
	MONUMENT("monument",StructureFeatures.l),
	OCEAN_RUIN_COLD("ocean_ruin_cold",StructureFeatures.m),
	OCEAN_RUIN_WARM("ocean_ruin_warm",StructureFeatures.n),
	FORTRESS("fortress",StructureFeatures.o),
	NETHER_FOSSIL("nether_fossil",StructureFeatures.p),
	END_CITY("end_city",StructureFeatures.q),
	BURIED_TREASURE("buried_treasure",StructureFeatures.r),
	BASTION_REMNANT("bastion_remnant",StructureFeatures.s),
	VILLAGE_PLAINS("village_plains",StructureFeatures.t),
	VILLAGE_DESERT("village_desert",StructureFeatures.u),
	VILLAGE_SAVANNA("village_savanna",StructureFeatures.v),
	VILLAGE_SNOWY("village_snowy",StructureFeatures.w),
	VILLAGE_TAIGA("village_taiga",StructureFeatures.x),
	RUINED_PORTAL("ruined_portal",StructureFeatures.y),
	RUINED_PORTAL_DESERT("ruined_portal_desert",StructureFeatures.z),
	RUINED_PORTAL_JUNGLE("ruined_portal_jungle",StructureFeatures.A),
	RUINED_PORTAL_SWAMP("ruined_portal_swamp",StructureFeatures.B),
	RUINED_PORTAL_MOUNTAIN("ruined_portal_mountain",StructureFeatures.C),
	RUINED_PORTAL_OCEAN("ruined_portal_ocean",StructureFeatures.D),
	RUINED_PORTAL_NETHER("ruined_portal_nether",StructureFeatures.E);
	
	
	private String name;
	private StructureFeature<?,?> sf;
	BiomeStructure(String name,StructureFeature<?,?> sf){
		this.name = name;
		this.sf = sf;
	}
	
	public String getName() {
		return name;
	}
	
	public StructureFeature<?, ?> get(){
		return sf;
	}
}
