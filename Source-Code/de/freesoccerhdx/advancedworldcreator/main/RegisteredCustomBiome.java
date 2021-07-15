package de.freesoccerhdx.advancedworldcreator.main;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;


//import net.minecraft.server.v1_16_R3.BiomeBase;
//import net.minecraft.server.v1_16_R3.ResourceKey;

public class RegisteredCustomBiome {

	
	
	private ResourceKey<BiomeBase> resourcekeybiome;
	private CustomBiome origin;
	private int biomeid;
	
	protected RegisteredCustomBiome(ResourceKey<BiomeBase> resourcekeybiome, CustomBiome origin, int biomeid) {
		Validate.notNull(resourcekeybiome, "You can't create a RegisteredCustomBiome without the ResourceKey");
		
		
		this.resourcekeybiome = resourcekeybiome;
		this.origin = origin;
		this.biomeid = biomeid;
	}
	
	public boolean isDataPackBiome() {
		return origin == null;
	}
	
	public int getBiomeID() {
		return biomeid;
	}
	
	public CustomBiome getOrigin() {
		return origin;
	}
	
	public ResourceKey<BiomeBase> getBiome(){
		return resourcekeybiome;
	}
	
	
}
