package de.freesoccerhdx.advancedworldcreator.main;

import java.util.OptionalLong;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.biome.GenLayerZoomVoronoi;
import net.minecraft.world.level.biome.GenLayerZoomer;
import net.minecraft.world.level.dimension.DimensionManager;



public class CustomDimensionSettings {

	
	private DimensionManager dm = null;
	
	public CustomDimensionSettings(OptionalLong fixedtime,
			boolean hasskylight, boolean hasceiling, 
			boolean ultrawarm, boolean natural,
			double coordscale, boolean createDragonBattle,
			boolean piglinSafe, boolean bedWorks,
			boolean respawnAnchorWorks, boolean hasraids,
			int minY,
			int height, int logical_height, MinecraftKey infiniburn,
			MinecraftKey worldeffects,float ambientlight) {
		
		  
		dm = DimensionManager.a(fixedtime,hasskylight,hasceiling,ultrawarm,natural,coordscale,piglinSafe,
				bedWorks,respawnAnchorWorks,hasraids,hasraids, minY, height,logical_height, GenLayerZoomVoronoi.a, infiniburn,worldeffects,ambientlight);
	}
	
	// used for vanilla DimensionManagers
	private CustomDimensionSettings() {
		
	}

	public DimensionManager get() {
		return dm;
	}
	
	
	public static CustomDimensionSettings getOverworldSettings() {
		CustomDimensionSettings cds = new CustomDimensionSettings();
		try {
			cds.dm = (DimensionManager) FieldUtils.readStaticField(DimensionManager.class, "n", true);
			return cds;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return DimensionManager.n;
		return null;
	}
	
	public static CustomDimensionSettings getNetherSettings() {
		CustomDimensionSettings cds = new CustomDimensionSettings();
		try {
			cds.dm = (DimensionManager) FieldUtils.readStaticField(DimensionManager.class, "o", true);
			return cds;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return DimensionManager.o;
		return null;
	}
	
	public static CustomDimensionSettings getEndSettings() {
		CustomDimensionSettings cds = new CustomDimensionSettings();
		try {
			cds.dm = (DimensionManager) FieldUtils.readStaticField(DimensionManager.class, "p", true);
			return cds;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return DimensionManager.p;
		return null;
	}
	
	
}
