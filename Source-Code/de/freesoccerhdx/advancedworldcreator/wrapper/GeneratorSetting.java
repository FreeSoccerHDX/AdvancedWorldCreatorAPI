package de.freesoccerhdx.advancedworldcreator.wrapper;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;

public enum GeneratorSetting {

	
	OVERWORLD("overworld",GeneratorSettingBase.c),
	AMPLIFIED("amplified",GeneratorSettingBase.d),
	NETHER("nether",GeneratorSettingBase.e),
	END("end",GeneratorSettingBase.f),
	CAVES("caves",GeneratorSettingBase.g),
	FLOATING_ISLANDS("floating_islands",GeneratorSettingBase.h);
	
	
	private String name;
	private ResourceKey<GeneratorSettingBase> val;
	
	GeneratorSetting(String name, ResourceKey<GeneratorSettingBase> val) {
		this.name = name;
		this.val = val;
	}
	public String getName() {
		return name;
	}
	public ResourceKey<GeneratorSettingBase> get(){
		return val;
	}
	
}
