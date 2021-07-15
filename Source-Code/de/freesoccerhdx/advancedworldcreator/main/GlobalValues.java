package de.freesoccerhdx.advancedworldcreator.main;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;

import com.mojang.serialization.Codec;

import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;

import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureRuleTest;

public class GlobalValues {

	public static final ResourceKey<IRegistry<BiomeBase>> BiomeBase_ResourceKey = IRegistry.aO;
	public static final ResourceKey<IRegistry<GeneratorSettingBase>> GeneratorSettingBase_ResourceKey = IRegistry.aH;
	public static final ResourceKey<IRegistry<DimensionManager>> DimensionManager_ResourceKey = IRegistry.P;
	
	
	public static final IRegistry<Codec<? extends WorldChunkManager>> WorldChunkManager_Registry =  IRegistry.br;
	public static final IRegistry<GeneratorSettingBase> GeneratorSettingBase_Registry;
	public static final IRegistry<DimensionManager> DimensionManager_Registry;
	
	public static final WorldGenerator<WorldGenFeatureOreConfiguration> ORE_CONFIGURATION_GENERATOR = WorldGenerator.K;
	public static final WorldGenerator<WorldGenFeatureOreConfiguration> SCATTERED_ORE_CONFIGURATION_GENERATOR = WorldGenerator.ag;
	public static final WorldGenerator<WorldGenFeatureTreeConfiguration> TREE_CONFIGURATION_GENERATOR = WorldGenerator.g;
	public static final WorldGenerator<WorldGenFeatureRandomChoiceConfiguration> RANDOM_SELECTOR_CONFIGURATION_GENERATOR = WorldGenerator.ah;

	
	
	public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> WorldGen_ChanceDecorator = WorldGenDecorator.r;
	
	
	
	
	public static final DefinedStructureRuleTest BASE_STONE_OVERWORLD = WorldGenFeatureOreConfiguration.Target.a;
	public static final DefinedStructureRuleTest STONE_ORE_REPLACEABLES = WorldGenFeatureOreConfiguration.Target.b;
	public static final DefinedStructureRuleTest DEEPSLATE_ORE_REPLACEABLES = WorldGenFeatureOreConfiguration.Target.c;
	public static final DefinedStructureRuleTest NETHERRACK = WorldGenFeatureOreConfiguration.Target.d;
	public static final DefinedStructureRuleTest BASE_STONE_NETHER = WorldGenFeatureOreConfiguration.Target.e;
	
	
	
	public static final IRegistry<BiomeBase> BiomeBase_Registry;
	public static final IRegistryCustom CustomConsoleRegistry;
	
	static {
		 Server server = Bukkit.getServer();	
		 CraftServer cs = (CraftServer) server;
		 DedicatedServer console = cs.getServer();
		 
		 
		 
		 CustomConsoleRegistry = console.getCustomRegistry();
		 
		 BiomeBase_Registry = CustomConsoleRegistry.b(BiomeBase_ResourceKey);
		 GeneratorSettingBase_Registry = CustomConsoleRegistry.b(GeneratorSettingBase_ResourceKey);
		 DimensionManager_Registry = CustomConsoleRegistry.b(DimensionManager_ResourceKey);
	}
	
	public static Color hexToColor(int i) {
		
		org.bukkit.Color col = org.bukkit.Color.fromRGB(i);
		
		return new Color(col.getRed(),col.getGreen(),col.getBlue());
		
	}
	public static int colorToHexaDecimal(Color col){
		  String hex = String.format("%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());  
	      return Integer.parseInt(hex, 16);
	}


}
