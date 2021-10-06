package de.freesoccerhdx.advancedworldcreator.wrapper;

import java.util.HashMap;

import net.minecraft.world.level.levelgen.WorldGenStage;

public enum WorldGenFeature {

	AIR(WorldGenStage.Features.a),
	LIQUID(WorldGenStage.Features.b);
	
	private static HashMap<WorldGenStage.Features, WorldGenFeature> valuemap = new HashMap<>();
	private WorldGenStage.Features feature;
	
	WorldGenFeature(WorldGenStage.Features feature) {
		this.feature = feature;
	}
	
	public WorldGenStage.Features get(){
		return feature;
	}
	
	public static WorldGenFeature fromMCFeature(WorldGenStage.Features feature) {
		return valuemap.get(feature);
	}
	
	static {
		for(WorldGenFeature wgf : WorldGenFeature.values()) {
			valuemap.put(wgf.get(), wgf);
		}
	}
	
	
}
