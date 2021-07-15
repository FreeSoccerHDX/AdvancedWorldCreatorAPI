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
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerPine;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeAlterGround;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerFancy;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerGiant;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerStraight;

public class CustomPineTreeStructure extends CustomTree{
	


	private String name;
	
	private Block wood = Blocks.N;
	private Block leaves = Blocks.al;
	private Block sapling = Blocks.u;
	
	private int leave_radius_min = 1;
	
	private int leave_offset_min = 1;
	
	private int leave_height_min = 3;
	private int leave_height_spread = 1;
	
	private int wood_height = 6;
	private int wood_randomHeightA = 4;
	private int wood_randomHeightB = 0;
	
	public CustomPineTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomPineTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
		this.name = name;
		if(woodmaterial != null) {
			this.wood = woodmaterial;
		}
		if(leavesmaterial != null) {
			this.leaves = leavesmaterial;
		}
	}
	
	public void setLeaveRadius(int min) {
		leave_radius_min = min;
	}
	public void setLeaveOffset(int min) {
		leave_offset_min = min;
	}
	public void setLeaveHeight(int min, int spread) {
		leave_height_min = min;
		leave_height_spread = spread;
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
		
		
		 WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
				 new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
				 new TrunkPlacerStraight(wood_height, wood_randomHeightA, wood_randomHeightB), 
				 new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
				 new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()),
				 new WorldGenFoilagePlacerPine(ConstantInt.a(leave_radius_min), ConstantInt.a(leave_offset_min), UniformInt.a(leave_height_min, leave_height_spread)),
				 new FeatureSizeTwoLayers(2, 0, 2)))
 
			.a().c()));
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
