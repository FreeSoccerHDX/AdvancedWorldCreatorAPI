package de.freesoccerhdx.advancedworldcreator.customstructures;

import net.minecraft.core.IRegistry;
import net.minecraft.data.RegistryGeneration;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;

public interface CustomStructure {

	
	public WorldGenFeatureConfigured<?, ?> create();
	
	default <FC extends WorldGenFeatureConfiguration> WorldGenFeatureConfigured<FC, ?> a(String var0, WorldGenFeatureConfigured<FC, ?> var1) {
		return (WorldGenFeatureConfigured<FC, ?>)IRegistry.<WorldGenFeatureConfigured<?, ?>>a(RegistryGeneration.e, var0, var1);
	}
	
}