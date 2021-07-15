package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurface;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public enum SurfaceType {

	
	/*
	 	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> w = a("default",
			new WorldGenSurfaceDefaultBlock(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> x = a("mountain",
			new WorldGenSurfaceExtremeHills(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> y = a("shattered_savanna",
			new WorldGenSurfaceSavannaMutated(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> z = a("gravelly_mountain",
			new WorldGenSurfaceExtremeHillMutated(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> A = a("giant_tree_taiga",
			new WorldGenSurfaceTaigaMega(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> B = a("swamp",
			new WorldGenSurfaceSwamp(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> C = a("badlands",
			new WorldGenSurfaceMesa(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> D = a("wooded_badlands",
			new WorldGenSurfaceMesaForest(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> E = a("eroded_badlands",
			new WorldGenSurfaceMesaBryce(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> F = a("frozen_ocean",
			new WorldGenSurfaceFrozenOcean(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> G = a("nether",
			new WorldGenSurfaceNether(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> H = a("nether_forest",
			new WorldGenSurfaceNetherForest(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> I = a("soul_sand_valley",
			new WorldGenSurfaceSoulSandValley(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> J = a("basalt_deltas",
			new WorldGenSurfaceBasaltDeltas(WorldGenSurfaceConfigurationBase.a));

	public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> K = a("nope",
			new WorldGenSurfaceEmpty(WorldGenSurfaceConfigurationBase.a)); 
	 
	 */
	
	DEFAULT("default", WorldGenSurface.w),
	MOUNTAIN("mountain", WorldGenSurface.x),
	SHATTERED_SAVANNA("shattered_savanna", WorldGenSurface.y),
	GRAVELLY_MOUNTAIN("gravelly_mountain", WorldGenSurface.z),
	GIANT_TREE_TAIGA("giant_tree_taiga", WorldGenSurface.A),
	SWAMP("swamp", WorldGenSurface.B),
	BADLANDS("badlands", WorldGenSurface.C),
	WOODED_BADLANDS("wooded_badlands", WorldGenSurface.D),
	ERODED_BADLANDS("eroded_badlands", WorldGenSurface.E),
	FROZEN_OCEAN("frozen_ocean", WorldGenSurface.F),
	NETHER("nether", WorldGenSurface.G),
	NETHER_FOREST("nether_forest", WorldGenSurface.H),
	SOUL_SAND_VALLEY("soul_sand_valley", WorldGenSurface.I),
	BASALT_DELTAS("basalt_deltas", WorldGenSurface.J),
	NOPE("nope", WorldGenSurface.K);
	
	private String name;
	private WorldGenSurface<WorldGenSurfaceConfigurationBase> value;
	
	SurfaceType(String name, WorldGenSurface<WorldGenSurfaceConfigurationBase> type) {
		this.name = name;
		this.value = type;
	}
	
	public String getName() {
		return name;
	}
	
	public WorldGenSurface<WorldGenSurfaceConfigurationBase> get(){
		return value;
	}
	
}
