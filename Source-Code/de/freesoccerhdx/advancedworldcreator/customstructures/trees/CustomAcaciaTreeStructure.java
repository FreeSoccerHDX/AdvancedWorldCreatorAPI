package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeTwoLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerAcacia;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerForking;

/*
 
import net.minecraft.server.v1_16_R3.Block;

import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.FeatureSizeTwoLayers;
import net.minecraft.server.v1_16_R3.IntSpread;
import net.minecraft.server.v1_16_R3.TrunkPlacerForking;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenFeatureStateProviderSimpl;
import net.minecraft.server.v1_16_R3.WorldGenFeatureTreeConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFoilagePlacerAcacia;
import net.minecraft.server.v1_16_R3.WorldGenerator;

 * */
public class CustomAcaciaTreeStructure extends CustomTree{
	


	private String name;
	private Block wood = Blocks.Q;
	private Block leaves = Blocks.ao;
	private Block sapling = Blocks.x;
	
	
	private int leave_radius = 2;
	private int leave_offset = 0;
	
	private int wood_height = 5;
	private int wood_randomHeightA = 2;
	private int wood_randomHeightB = 2;
	
	public CustomAcaciaTreeStructure(String name) {
		this(name,null,null);
	}
	

	public CustomAcaciaTreeStructure(String name,Block woodmaterial, Block leavesmaterial) {
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
		
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> cG = a(name, 
				GlobalValues.TREE_CONFIGURATION_GENERATOR.b(
						(new WorldGenFeatureTreeConfiguration.a((
								new WorldGenFeatureStateProviderSimpl(wood.getBlockData())),
								new TrunkPlacerForking(wood_height, wood_randomHeightA, wood_randomHeightB),
								new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
								new WorldGenFeatureStateProviderSimpl(sapling.getBlockData()),
								new WorldGenFoilagePlacerAcacia((IntProvider)ConstantInt.a(leave_radius), (IntProvider)ConstantInt.a(leave_offset)), (FeatureSize)new FeatureSizeTwoLayers(1, 0, 2)))
	        
	        .a()
	        .c()));
		
		/*
		WorldGenFeatureConfigured<WorldGenFeatureTreeConfiguration, ?> tree = a(name, 
				GlobalValues.TREE_CONFIGURATION_GENERATOR.a((new WorldGenFeatureTreeConfiguration.a(
						new WorldGenFeatureStateProviderSimpl(wood.getBlockData()),
						new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
						new WorldGenFeatureStateProviderSimpl(leaves.getBlockData()),
						new WorldGenFoilagePlacerAcacia(IntSpread.a(leave_radius), IntSpread.a(leave_offset)),
						new TrunkPlacerForking(wood_height, wood_randomHeightA, wood_randomHeightB), 
						new FeatureSizeTwoLayers(1, 0, 2))
				)
	        
	        .a()
	        .b()));
		
		*/
		
		return cG;
	}

	
	
	
	
	
	
	
	
	
	
}
