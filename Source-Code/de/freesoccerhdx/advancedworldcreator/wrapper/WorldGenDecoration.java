package de.freesoccerhdx.advancedworldcreator.wrapper;

import java.util.HashMap;

import net.minecraft.world.level.levelgen.WorldGenStage;

public enum WorldGenDecoration {

	
	
	
	
	RAW_GENERATION(WorldGenStage.Decoration.a),
	LAKES(WorldGenStage.Decoration.b),
	LOCAL_MODIFICATIONS(WorldGenStage.Decoration.c),
	UNDERGROUND_STRUCTURES(WorldGenStage.Decoration.d),
	SURFACE_STRUCTURES(WorldGenStage.Decoration.e),
	STRONGHOLDS(WorldGenStage.Decoration.f),
	UNDERGROUND_ORES(WorldGenStage.Decoration.g),
	UNDERGROUND_DECORATION(WorldGenStage.Decoration.h),
	VEGETAL_DECORATION(WorldGenStage.Decoration.i),
	TOP_LAYER_MODIFICATION(WorldGenStage.Decoration.j);
	
	private static HashMap<WorldGenStage.Decoration, WorldGenDecoration> valuemap = new HashMap<>();
	private WorldGenStage.Decoration deco;
	
	WorldGenDecoration(WorldGenStage.Decoration deco){
		this.deco = deco;
	}
	
	public WorldGenStage.Decoration get(){
		return deco;
	}
	
	public static WorldGenDecoration fromMCDecoration(WorldGenStage.Decoration decoration) {
		return valuemap.get(decoration);
	}
	
	
	static {
		for(WorldGenDecoration deco : WorldGenDecoration.values()) {
			valuemap.put(deco.get(), deco);
		}
	}
	
	
}
