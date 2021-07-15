package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import java.util.List;
import java.util.OptionalInt;


import com.google.common.collect.ImmutableList;

import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import net.minecraft.data.worldgen.BiomeDecoratorGroups;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeThreeLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerDarkOak;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerDarkOak;
import net.minecraft.world.level.levelgen.placement.WaterDepthThresholdConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorConfigured;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorFrequencyExtraChanceConfiguration;




public class CustomDarkOakTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.R;
	private Block leaves = Blocks.ap;
	
	private Block sappling = Blocks.v;
	
	
	
	private int leave_radius = 0;
	private int leave_offset = 0;
	
	private int wood_height = 6;
	private int wood_randomHeightA = 2;
	private int wood_randomHeightB = 0;
	
	public CustomDarkOakTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomDarkOakTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
		this.name = name;
		if(woodmaterial != null) {
			this.wood = woodmaterial;
		}
		if(leavesmaterial != null) {
			this.leaves = leavesmaterial;
		}
	}
	
	public void setLeaveRadius(int i) {
		leave_radius = i;
	}
	public void setLeaveOffset(int i) {
		leave_offset = i;
	}
	
	
	public void setWoodHeight(int i) {
		wood_height = i;
	}
	public void setRandomWoodHeightA(int i) {
		wood_randomHeightA = i;
	}
	public void setRandomWoodHeightB(int i) {
		wood_randomHeightB = i;
	}

	
	@Override
	public WorldGenFeatureConfigured<?, ?> create() {


		
		
		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, WorldGenerator.g.b(
					new WorldGenFeatureTreeConfiguration.a(
						(WorldGenFeatureStateProvider)new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
						(TrunkPlacer)new TrunkPlacerDarkOak(wood_height, wood_randomHeightA, wood_randomHeightB),
						(WorldGenFeatureStateProvider)new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
						(WorldGenFeatureStateProvider)new WorldGenFeatureStateProviderSimpl(sappling.getBlockData()),
						(WorldGenFoilagePlacer)new WorldGenFoilagePlacerDarkOak(
	            
	            (IntProvider)ConstantInt.a(leave_radius), (IntProvider)ConstantInt.a(leave_offset)), (FeatureSize)new FeatureSizeThreeLayers(1, 1, 0, 1, 2, 
	            OptionalInt.empty()))
	        
	        .a()
	        .c()));
		
		
		/*
		
		// e.g.: dark oak biome
		WorldGenFeatureConfigured<?, ?> fullforest = a(name, 
				GlobalValues.RANDOM_SELECTOR_CONFIGURATION_GENERATOR.b(new WorldGenFeatureRandomChoiceConfiguration(
		          (List)ImmutableList.of(),
		          tree))
		      .a(CustomTree.b.u));
		
		
		
		
		// e.g.: savanna/plains biome
		WorldGenFeatureConfigured<?, ?> randomtrees = a(name, 
				GlobalValues.RANDOM_SELECTOR_CONFIGURATION_GENERATOR.b(
					new WorldGenFeatureRandomChoiceConfiguration((List)ImmutableList.of(), tree)
				).a(CustomTree.b.q)
				 .a(WorldGenDecorator.l.a(new WorldGenDecoratorFrequencyExtraChanceConfiguration(0, (float) 0.05, 2)))
				);
		
		*/
		
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
