package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.data.worldgen.WorldGenCarvers;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;

public enum WorldGenCarver {

	
	
	CAVE("cave",WorldGenCarvers.a),
	PROTOTYPE_CAVE("prototype_cave",WorldGenCarvers.b),
	CANYON("canyon",WorldGenCarvers.c),
	PROTOTYPE_CANYON("prototype_canyon",WorldGenCarvers.d),
	OCEAN_CAVE("ocean_cave",WorldGenCarvers.e),
	UNDERWATER_CANYON("underwater_canyon",WorldGenCarvers.f),
	UNDERWATER_CAVE("underwater_cave",WorldGenCarvers.g),
	NETHER_CAVE("nether_cave",WorldGenCarvers.h),
	PROTOTYPE_CREVICE("prototype_crevice",WorldGenCarvers.i);
	
	private String name;
	private WorldGenCarverWrapper<?> value;
	WorldGenCarver(String name, WorldGenCarverWrapper<?> value){
		this.name = name;
		this.value = value;
	} 
	
	public String getName() {
		return name;
	}
	
	public WorldGenCarverWrapper<?> get(){
		return value;
	}
	
	
	
}
