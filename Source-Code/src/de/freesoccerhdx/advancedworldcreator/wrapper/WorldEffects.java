package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.resources.MinecraftKey;

public enum WorldEffects {

	OVERWORLD(new MinecraftKey("minecraft","overworld")),
	NETHER(new MinecraftKey("minecraft","the_nether")),
	THE_END(new MinecraftKey("minecraft","the_end"));
	
	private MinecraftKey key;
	
	private WorldEffects(MinecraftKey key) {
		this.key = key;
	}
	
	public MinecraftKey toNMS() {
		return key;
	}
	
	
}
