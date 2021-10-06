package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import java.util.OptionalInt;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeTwoLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerFancy;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerFancy;

public class CustomFancyOakTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.M;
	private Block leaves = Blocks.ak;
	private Block sapling = Blocks.t;
	
	private int leave_radius = 2;
	private int leave_offset = 4;
	private int leave_height = 4;
	
	private int wood_height = 3;
	private int wood_randomHeightA = 11;
	private int wood_randomHeightB = 0;
	
	public CustomFancyOakTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomFancyOakTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name,
				WorldGenerator.g.b((new WorldGenFeatureTreeConfiguration.a(
						new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
						new TrunkPlacerFancy(wood_height, wood_randomHeightA, wood_randomHeightB),
						new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
						new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()), 
						new WorldGenFoilagePlacerFancy(ConstantInt.a(leave_radius), ConstantInt.a(leave_offset), leave_height),
						new FeatureSizeTwoLayers(0, 0, 0, OptionalInt.of(4))))

				.a().c()));
		
		return tree;
	}

	
	
	
	
	
	
	
	
	
	
}
