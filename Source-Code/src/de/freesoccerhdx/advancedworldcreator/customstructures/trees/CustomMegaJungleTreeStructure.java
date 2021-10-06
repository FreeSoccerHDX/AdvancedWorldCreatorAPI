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
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerJungle;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeCocoa;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineLeaves;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineTrunk;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerMegaJungle;

public class CustomMegaJungleTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.P;
	private Block leaves = Blocks.an;
	private Block sapling = Blocks.w;
	
	private int leave_radius = 2;
	private int leave_offset = 0;
	private int leave_height = 2;
	
	private int wood_height = 10;
	private int wood_randomHeightA = 2;
	private int wood_randomHeightB = 19;

	private boolean cocoa = true;
	private boolean woodvine = true;
	private boolean leavevine = true;
	
	
	public CustomMegaJungleTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomMegaJungleTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
		
		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name,
				WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
						new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
						new TrunkPlacerMegaJungle(wood_height, wood_randomHeightA, wood_randomHeightB), 
						new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
						new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()), 
						new WorldGenFoilagePlacerJungle(ConstantInt.a(leave_radius), ConstantInt.a(leave_offset), leave_height),
						new FeatureSizeTwoLayers(1, 1, 2)))

				.a(treefeat)
				.c()));
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
