package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.world.level.levelgen.feature.StructureGenerator;

public enum WorldStructureGenerator{
	
	VILLAGE(StructureGenerator.q),
	DESERT_PYRAMID(StructureGenerator.f),
	IGLOO(StructureGenerator.g),
	JUNGLE_PYRAMID(StructureGenerator.e),
	SWAMP_HUT(StructureGenerator.j),
	PILLAGER_OUTPOST(StructureGenerator.b),
	STRONGHOLD(StructureGenerator.k),
	MONUMENT(StructureGenerator.l),
	ENDCITY(StructureGenerator.o),
	MANSION(StructureGenerator.d),
	BURIED_TREASURE(StructureGenerator.p),
	MINESHAFT(StructureGenerator.c),
	RUINED_PORTAL(StructureGenerator.h),
	SHIPWRECK(StructureGenerator.i),
	OCEAN_RUIN(StructureGenerator.m),
	BASTION_REMNANT(StructureGenerator.s),
	FORTRESS(StructureGenerator.n),
	NETHER_FOSSIL(StructureGenerator.r)
	;
	
	
	private StructureGenerator<?> sg;
	WorldStructureGenerator(StructureGenerator<?> sg) {
		this.sg = sg;
	}
	
	public StructureGenerator<?> get(){
		return sg;
	}
	
}