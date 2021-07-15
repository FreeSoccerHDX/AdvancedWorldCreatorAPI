package de.freesoccerhdx.advancedworldcreator.customstructures;

import java.util.Random;

import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorConfigured;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlock;

/*
import net.minecraft.server.v1_16_R3.Block;

import net.minecraft.server.v1_16_R3.DefinedStructureRuleTest;
import net.minecraft.server.v1_16_R3.DefinedStructureTestBlock;
import net.minecraft.server.v1_16_R3.WorldGenDecorator;
import net.minecraft.server.v1_16_R3.WorldGenDecoratorConfigured;
import net.minecraft.server.v1_16_R3.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenerator;
*/

public class CustomOreStructure implements CustomStructure{

	
	private String name;
	private int minimum = 0;
	private int maximum = 128;
	private int oresize = 4;
	private int perchunk = 10;
	
	private Block ore_material;
	private Block replace_material;
	
	private StructureRule structrule = null;
	
	private WorldGenerator<WorldGenFeatureOreConfiguration> oregenerator = GlobalValues.ORE_CONFIGURATION_GENERATOR;
	

	
	public CustomOreStructure(String name, int minY,
			int maxY, int oresize, int perchunk, Block oremat, Block target) {
		this.name = name;
		this.minimum = minY;
		this.maximum = maxY;
		this.oresize = oresize;
		this.perchunk = perchunk;
		this.ore_material = oremat;
		this.replace_material = target;
	}

	@Override
	public WorldGenFeatureConfigured<?, ?> create() {
		
		
		UniformHeight heightprovider = UniformHeight.a(VerticalAnchor.a(minimum), VerticalAnchor.a(maximum));

		WorldGenDecoratorConfigured<WorldGenFeatureChanceDecoratorRangeConfiguration> range = 
				GlobalValues.WorldGen_ChanceDecorator.a(new WorldGenFeatureChanceDecoratorRangeConfiguration(heightprovider));
		
		DefinedStructureRuleTest rule = null;
		
		if(structrule != null) {
			rule = structrule.getRule();
		}else {
			rule = new DefinedStructureTestBlock(replace_material);
		}
		
		WorldGenFeatureConfigured<?, ?> customore = a(name,
				oregenerator.b(new WorldGenFeatureOreConfiguration(
						rule, ore_material.getBlockData(), oresize)).a(range).a().b(perchunk)); 
		
		return customore;
	}
	
	public void setNoSurfaceOre(boolean b) {
		if(b) {
			oregenerator = GlobalValues.SCATTERED_ORE_CONFIGURATION_GENERATOR;
		}else {
			oregenerator = GlobalValues.ORE_CONFIGURATION_GENERATOR;
		}
	}
	
	public void setVanillaReplaceRule(StructureRule rule) {
		structrule = rule;
	}
	
	/*
	 
	 	public static final DefinedStructureRuleTest Base_Stone_Overworld = WorldGenFeatureOreConfiguration.Target.a;
		public static final DefinedStructureRuleTest STONE_ORE_REPLACEABLES = WorldGenFeatureOreConfiguration.Target.b;
		public static final DefinedStructureRuleTest DEEPSLATE_ORE_REPLACEABLES = WorldGenFeatureOreConfiguration.Target.c;
		public static final DefinedStructureRuleTest NETHERRACK = WorldGenFeatureOreConfiguration.Target.d;
		public static final DefinedStructureRuleTest BASE_STONE_NETHER = WorldGenFeatureOreConfiguration.Target.e;
	 
	 */

	public enum StructureRule{
		
		BASE_STONE_OVERWORLD(GlobalValues.BASE_STONE_OVERWORLD),
		NETHERRACK(GlobalValues.NETHERRACK),
		BASE_STONE_NETHER(GlobalValues.BASE_STONE_NETHER);
		
		private DefinedStructureRuleTest structrule;
		StructureRule(DefinedStructureRuleTest r){
			structrule = r;
		}
		
		public DefinedStructureRuleTest getRule() {
			return structrule;
		}
		
	}
	
}













