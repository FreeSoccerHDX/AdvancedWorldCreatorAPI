package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeTwoLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerBlob;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerFancy;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerMegaPine;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeAlterGround;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerFancy;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerGiant;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerStraight;

public class CustomOakTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.M;
	private Block leaves = Blocks.ak;
	private Block sapling = Blocks.t;
	
	private int leave_radius = 2;
	private int leave_offset = 0;
	private int leave_height = 3;
	
	private int wood_height = 4;
	private int wood_randomHeightA = 2;
	private int wood_randomHeightB = 0;
	
	public CustomOakTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomOakTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
	public void setLeveHeight(int i) {
		leave_height = i;
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
		
		/*
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name,
			WorldGenerator.TREE.b((new WorldGenFeatureTreeConfiguration.a(
				new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
				new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
				new WorldGenFoilagePlacerBlob(IntSpread.a(leave_radius),IntSpread.a(leave_offset), leave_height),// radius(of leaves), offset(up+ or down-), height(from top to down)
				new TrunkPlacerStraight(wood_height, wood_randomHeightA, wood_randomHeightB),//wood: base_height, height_rand_a, height_rand_b
				new FeatureSizeTwoLayers(1, 0, 1)))//???: limit, lower_size, upper_size
	        
	        .a()
	        .b()));
	        
	    */
		
		
		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
				new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
				new TrunkPlacerStraight(wood_height, wood_randomHeightA, wood_randomHeightB), 
				new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
				new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()), 
				new WorldGenFoilagePlacerBlob(ConstantInt.a(leave_radius), ConstantInt.a(leave_offset), leave_height),
				new FeatureSizeTwoLayers(1, 0, 1)))

		.a().c()));
		
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
