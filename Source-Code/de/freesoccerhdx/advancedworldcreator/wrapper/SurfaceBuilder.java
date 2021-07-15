package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.data.worldgen.WorldGenSurfaceComposites;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceComposite;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public enum SurfaceBuilder {
	
	
	BADLANDS("badlands",WorldGenSurfaceComposites.a),
	BASALT_DELTAS("basalt_deltas",WorldGenSurfaceComposites.b),
	CRIMSON_FOREST("crimson_forest",WorldGenSurfaceComposites.c),
	DESERT("desert",WorldGenSurfaceComposites.d),
	END("end",WorldGenSurfaceComposites.e),
	ERODED_BADLANDS("eroded_badlands",WorldGenSurfaceComposites.f),
	FROZEN_OCEAN("frozen_ocean",WorldGenSurfaceComposites.g),
	FULL_SAND("full_sand",WorldGenSurfaceComposites.h),
	GIANT_TREE_TAIGA("giant_tree_taiga",WorldGenSurfaceComposites.i),
	GRASS("grass",WorldGenSurfaceComposites.j),
	GRAVELLY_MOUNTAIN("gravelly_mountain",WorldGenSurfaceComposites.k),
	ICE_SPIKES("ice_spikes",WorldGenSurfaceComposites.l),
	MOUNTAIN("mountain",WorldGenSurfaceComposites.m),
	MYCELIUM("mycelium",WorldGenSurfaceComposites.n),
	NETHER("nether",WorldGenSurfaceComposites.o),
	NOPE("nope",WorldGenSurfaceComposites.p),
	OCEAN_SAND("ocean_sand",WorldGenSurfaceComposites.q),
	SHATTERED_SAVANNA("shattered_savanna",WorldGenSurfaceComposites.r),
	SOUL_AND_VALLEY("soul_sand_valley",WorldGenSurfaceComposites.s),
	STONE("stone",WorldGenSurfaceComposites.t),
	SWAMP("swamp",WorldGenSurfaceComposites.u),
	WARPED_FOREST("warped_forest",WorldGenSurfaceComposites.v),
	WOODED_BADLANDS("wooded_badlands",WorldGenSurfaceComposites.w);
	
	private String name;
	private WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> value;
	
	SurfaceBuilder(String name, WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> value){
		this.name = name;
		this.value = value;
		
	}
	
	public String getName() {
		return name;
	}
	
	public WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> get(){
		return value;
	}
	
	
	
	
	
	
}
