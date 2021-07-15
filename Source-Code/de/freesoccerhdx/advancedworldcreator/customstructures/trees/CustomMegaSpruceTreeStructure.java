package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import java.util.ArrayList;

import java.util.List;

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

public class CustomMegaSpruceTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.N;
	private Block leaves = Blocks.al;
	private Block sapling = Blocks.u;
	
	private int leave_radius_min = 0;
	
	private int leave_offset_min = 0;
	
	private int leave_height_min = 13;
	private int leave_height_spread = 4;
	
	private int wood_height = 13;
	private int wood_randomHeightA = 2;
	private int wood_randomHeightB = 14;
	
	private boolean changeGround = true;
	private Block ground = Blocks.l;
	
	
	public CustomMegaSpruceTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomMegaSpruceTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
	
	public void setChangeGround(boolean b, Block type) {
		changeGround = b;
		ground = type;
	}


	@Override
	public WorldGenFeatureConfigured<?, ?> create() {
		
		List<WorldGenFeatureTree> features = new ArrayList<>();
		
		if(changeGround) {
			features.add(new WorldGenFeatureTreeAlterGround(new WorldGenFeatureStateProviderSimpl(ground.getBlockData())));
		}



		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
				new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
				new TrunkPlacerGiant(wood_height, wood_randomHeightA, wood_randomHeightB),
				new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
				new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()),
				new WorldGenFoilagePlacerMegaPine(ConstantInt.a(leave_radius_min), ConstantInt.a(leave_offset_min), UniformInt.a(leave_height_min, leave_height_spread)),
				new FeatureSizeTwoLayers(1, 1, 2)))

		.a(features).c()));
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
