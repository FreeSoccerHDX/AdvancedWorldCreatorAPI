package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeTwoLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerBlob;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeCocoa;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineLeaves;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineTrunk;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerStraight;

public class CustomSmallJungleTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.P;
	private Block leaves = Blocks.an;
	private Block sapling = Blocks.w;
	
	private int leave_radius_min = 2;
	
	private int leave_offset_min = 0;
	
	private int leave_height_min = 3;
	
	private int wood_height = 4;
	private int wood_randomHeightA = 8;
	private int wood_randomHeightB = 0;
	
	private boolean cocoa = true;
	private boolean woodvine = true;
	private boolean leavevine = true;
	
	public CustomSmallJungleTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomSmallJungleTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
	public void setLeaveHeight(int min) {
		leave_height_min = min;
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

	public void setGenerateCocoa(boolean b) {
		this.cocoa = b;
	}
	public void setGenerateVinesOnLog(boolean b) {
		woodvine = b;
	}
	public void setGenerateVinesOnleaves(boolean b) {
		leavevine = b;
	}
	
	
	@Override
	public WorldGenFeatureConfigured<?, ?> create() {
		
		List<WorldGenFeatureTree> treefeat = new ArrayList<>();
		
		if(cocoa) {
			treefeat.add(new WorldGenFeatureTreeCocoa(0.2F));
		}
		if(woodvine) {
			treefeat.add(WorldGenFeatureTreeVineTrunk.b);
		}
		if(leavevine) {
			treefeat.add(WorldGenFeatureTreeVineLeaves.b);
		}
		
		
		 WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
				new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
				new TrunkPlacerStraight(wood_height, wood_randomHeightA, wood_randomHeightB),
				new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
				new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()), 
				new WorldGenFoilagePlacerBlob(ConstantInt.a(leave_radius_min), ConstantInt.a(leave_offset_min), leave_height_min),
				new FeatureSizeTwoLayers(1, 0, 1)))

				.a(treefeat)

		.a().c()));
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
