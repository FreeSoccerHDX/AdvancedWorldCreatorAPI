package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.world.level.levelgen.WorldGenStage;

public enum WorldDecoration {

	RAW_GENERATION(WorldGenStage.Decoration.a),
	LAKES(WorldGenStage.Decoration.a),
	LOCAL_MODIFICATIONS(WorldGenStage.Decoration.a),
	UNDERGROUND_STRUCTURES(WorldGenStage.Decoration.a),
	SURFACE_STRUCTURES(WorldGenStage.Decoration.a),
	STRONGHOLDS(WorldGenStage.Decoration.a),
	UNDERGROUND_ORES(WorldGenStage.Decoration.a),
	UNDERGROUND_DECORATION(WorldGenStage.Decoration.a),
	VEGETAL_DECORATION(WorldGenStage.Decoration.a),
	TOP_LAYER_MODIFICATION(WorldGenStage.Decoration.a);
	
	
	private WorldGenStage.Decoration deco;
	
	WorldDecoration(WorldGenStage.Decoration realvalue) {
		deco = realvalue;	
	}
	
	
	public WorldGenStage.Decoration toNMS(){
		return deco;
	}
	
	
}
