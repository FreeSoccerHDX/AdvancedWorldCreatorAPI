package de.freesoccerhdx.advancedworldcreator.main;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import de.freesoccerhdx.advancedworldcreator.wrapper.GeneratorSetting;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldStructureGenerator;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenStage.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.StructureSettingsFeature;

/*
 import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.EnumDifficulty;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.GeneratorSettingBase;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.StructureGenerator;
import net.minecraft.server.v1_16_R3.StructureSettingsFeature;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import net.minecraft.server.v1_16_R3.WorldGenStage.Decoration;
 * */

/*
 
 	public static final StructureGenerator<WorldGenFeatureVillageConfiguration> b = a("Pillager_Outpost",
	public static final StructureGenerator<WorldGenMineshaftConfiguration> c = a("Mineshaft",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> d = a("Mansion",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> e = a("Jungle_Pyramid",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> f = a("Desert_Pyramid",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> g = a("Igloo",
	public static final StructureGenerator<WorldGenFeatureRuinedPortalConfiguration> h = a("Ruined_Portal",
	public static final StructureGenerator<WorldGenFeatureShipwreckConfiguration> i = a("Shipwreck",
	public static final WorldGenFeatureSwampHut j = a("Swamp_Hut",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> k = a("Stronghold",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> l = a("Monument",
	public static final StructureGenerator<WorldGenFeatureOceanRuinConfiguration> m = a("Ocean_Ruin",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> n = a("Fortress",
	public static final StructureGenerator<WorldGenFeatureEmptyConfiguration> o = a("EndCity",
	public static final StructureGenerator<WorldGenFeatureConfigurationChance> p = a("Buried_Treasure",
	public static final StructureGenerator<WorldGenFeatureVillageConfiguration> q = a("Village",
	public static final StructureGenerator<WorldGenFeatureChanceDecoratorRangeConfiguration> r = a("Nether_Fossil",
	public static final StructureGenerator<WorldGenFeatureVillageConfiguration> s = a("Bastion_Remnant",
	
 * */


public class AdvancedCreator {
	
	
	
	private static HashMap<WorldStructureGenerator, Integer[]> structsetts = new HashMap<>();
	static {
		structsetts.put(WorldStructureGenerator.VILLAGE, new Integer[] {32,8,10387312});
		structsetts.put(WorldStructureGenerator.DESERT_PYRAMID, new Integer[] {32,8,14357617});
		structsetts.put(WorldStructureGenerator.IGLOO, new Integer[] {32,8,14357618});
		structsetts.put(WorldStructureGenerator.JUNGLE_PYRAMID, new Integer[] {32,8,14357619});
		structsetts.put(WorldStructureGenerator.SWAMP_HUT, new Integer[] {32,8,14357620});
		structsetts.put(WorldStructureGenerator.PILLAGER_OUTPOST, new Integer[] {32,8,165745296});
		structsetts.put(WorldStructureGenerator.STRONGHOLD, new Integer[] {1,0,0});
		structsetts.put(WorldStructureGenerator.MONUMENT, new Integer[] {32,5,10387313});
		structsetts.put(WorldStructureGenerator.ENDCITY, new Integer[] {20,11,10387313});
		structsetts.put(WorldStructureGenerator.MANSION, new Integer[] {80,20,10387319});
		structsetts.put(WorldStructureGenerator.BURIED_TREASURE, new Integer[] {1,0,0});
		structsetts.put(WorldStructureGenerator.MINESHAFT, new Integer[] {1,0,0});
		structsetts.put(WorldStructureGenerator.RUINED_PORTAL,new Integer[] {25,10, 34222645});
		structsetts.put(WorldStructureGenerator.SHIPWRECK, new Integer[] {24,4,165745295});
		structsetts.put(WorldStructureGenerator.OCEAN_RUIN, new Integer[] {20,8,14357621});
		structsetts.put(WorldStructureGenerator.BASTION_REMNANT, new Integer[] {27,4,30084232});
		structsetts.put(WorldStructureGenerator.FORTRESS, new Integer[] {27,4,30084232});
		structsetts.put(WorldStructureGenerator.NETHER_FOSSIL, new Integer[] {2,1,14357921});
	}


	
	private List<WorldGenStage.Decoration> blacklist_deco = new ArrayList<>();
	private EnumGamemode gamemode = EnumGamemode.a;
	private EnumDifficulty enumdifficulty = EnumDifficulty.b;
	
	private Block topbedrock = Blocks.z;
	private Block bottombedrock = Blocks.z;
	
	private String worldname;
	private long seed;
	
	private boolean generate_surface = true;
	private boolean hardcore = false;
	private boolean gendeco = true;
	private boolean cangeneratestructures = true;
	private boolean allowmonsters = true;
	private boolean allowanimals = true;
	
	private boolean disable_air_caves = false;
	private boolean disable_liquid_caves = false;
	
	private GeneratorSetting worldgen = GeneratorSetting.OVERWORLD;
	private GeneratorSettingBase gsb = null; 
	
	private Map<StructureGenerator<?>, StructureSettingsFeature> worldgenoptions;
	
	
	public AdvancedCreator(String worldname) {
		this(worldname, (new Random()).nextLong());
	}
	
	public AdvancedCreator(String worldname, long seed) {
		this.worldname = worldname;
		this.seed = seed;
		this.worldgenoptions = new HashMap<>();
	}
	
	public boolean isAirCavesDisabled() {
		return disable_air_caves;
	}
	public void setAirCavesDisabled(boolean b) {
		disable_air_caves = b;
	}
	public boolean isLiquidCavesDisabled() {
		return disable_liquid_caves;
	}
	public void setLiquidCavesDisabled(boolean b) {
		disable_liquid_caves = b;
	}
	
	public List<WorldGenStage.Decoration> getBlacklistedDecoration(){
		return blacklist_deco;
	}
	public boolean isDecorationBlacklisted(Decoration dec) {
		return blacklist_deco.contains(dec);
	}
	public void setDecorationBlacklisted(Decoration dec, boolean b) {
		if(b) {
			if(!blacklist_deco.contains(dec)) {
				blacklist_deco.add(dec);
			}
		}else if(isDecorationBlacklisted(dec)){
			blacklist_deco.remove(dec);
		}
	}
	
	
	public AdvancedCreator setWorldSetting(GeneratorSetting wg) {
		this.worldgen = wg;
		return this;
	}
	
	public GeneratorSetting getWorldSetting() {
		return worldgen;
	}
	
	/*
	 StructureSettings.a.fieldOf("structures").forGetter(GeneratorSettingBase::a),
			NoiseSettings.a.fieldOf("noise").forGetter(GeneratorSettingBase::b),
			IBlockData.b.fieldOf("default_block").forGetter(GeneratorSettingBase::c),
			IBlockData.b.fieldOf("default_fluid").forGetter(GeneratorSettingBase::d),
			Codec.INT.fieldOf("bedrock_roof_position").forGetter(GeneratorSettingBase::e),
			Codec.INT.fieldOf("bedrock_floor_position").forGetter(GeneratorSettingBase::f),
			Codec.INT.fieldOf("sea_level").forGetter(GeneratorSettingBase::g),
			Codec.INT.fieldOf("min_surface_level").forGetter(GeneratorSettingBase::h),
			Codec.BOOL.fieldOf("disable_mob_generation").forGetter(GeneratorSettingBase::i),
			Codec.BOOL.fieldOf("aquifers_enabled").forGetter(GeneratorSettingBase::j),
			Codec.BOOL.fieldOf("noise_caves_enabled").forGetter(GeneratorSettingBase::k),
			Codec.BOOL.fieldOf("deepslate_enabled").forGetter(GeneratorSettingBase::l),
			Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(GeneratorSettingBase::m),
			Codec.BOOL.fieldOf("noodle_caves_enabled").forGetter(GeneratorSettingBase::m))
	 * */
	
	public void setCustomWorldSetting(NoiseSettings noisesettings, Block solid_material, Block fluid_material, int bedrock_roof_position, int bedrock_floor_position, int sea_level, 
			int min_surface_level, boolean disable_mob_generation, boolean aquifers_enabled, boolean noise_caves_enabled, boolean deepslate_enabled, boolean ore_veins_enabled, boolean noodle_caves_enabled) {
		try {
			Constructor<GeneratorSettingBase> constructor = (Constructor<GeneratorSettingBase>) GeneratorSettingBase.class.getDeclaredConstructors()[0];
	        constructor.setAccessible(true);
	        
	        // GeneratorSettingBase(StructureSettings var0, NoiseSettings var1, IBlockData var2, IBlockData var3, int var4, int var5, int var6, boolean var7) 
	        gsb = constructor.newInstance(new StructureSettings(true),noisesettings,solid_material.getBlockData(),fluid_material.getBlockData(),bedrock_roof_position,bedrock_floor_position,sea_level,
	        		min_surface_level,disable_mob_generation, aquifers_enabled, noise_caves_enabled, deepslate_enabled, ore_veins_enabled, noodle_caves_enabled);
	       
	    //    Bukkit.broadcastMessage("gsb: " + gsb);
	        
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public GeneratorSettingBase getCustomWorldSetting() {
		return gsb;
	}
	
	public boolean hasCustomWorldSetting() {
		return gsb != null;
	}
	
	
	
	public AdvancedCreator setSeed(long l) {
		seed = l;
		return this;
	}
	
	public long getSeed() {
		return this.seed;
	}
	
	public AdvancedCreator setWorldName(String s) {
		worldname = s;
		return this;
	}
	
	public String getWorldName() {
		return this.worldname;
	}

	public AdvancedCreator setHardcore(boolean b) {
		this.hardcore = b;
		return this;
	}
	
	public boolean isHardcore() {
		return this.hardcore;
	}

	public AdvancedCreator setGenerateStructures(boolean b) {
		this.cangeneratestructures = b;
		return this;
	}
	public boolean canGenerateStructures() {
		return this.cangeneratestructures;
	}
	
	
	public AdvancedCreator addStructureGeneratorConfig(WorldStructureGenerator structuregenerator, int spacing, int seperating) {
		Validate.notNull(structuregenerator, "StructureGenerator may not be null");
		
		int salt = structsetts.get(structuregenerator)[2];
		this.worldgenoptions.put(structuregenerator.get(), new StructureSettingsFeature(spacing, seperating, salt));

		return this;
	}
	
	public AdvancedCreator loadDefaultStructureGeneratorConfig() {
		this.worldgenoptions.clear();
		
		for(WorldStructureGenerator sg : structsetts.keySet()) {
			Integer[] a = structsetts.get(sg);
			this.worldgenoptions.put(sg.get(), new StructureSettingsFeature(a[0], a[1], a[2]));
			
		}
		
		return this;
	}
	
	public Map<StructureGenerator<?>, StructureSettingsFeature> getStructuresGeneratorConfigs() {
		return this.worldgenoptions;
	}


	public Block getTopBedrock() {
		return this.topbedrock;
	}
	public void setTopBedrock(Block block) {
		this.topbedrock = block;
	}
	
	public Block getBottomBedrock() {
		return this.bottombedrock;
	}
	public void setBottomBedrock(Block block) {
		this.bottombedrock = block;
	}



	public void setDifficulty(EnumDifficulty dif) {
		this.enumdifficulty = dif;
	}
	public EnumDifficulty getDifficulty() {
		return this.enumdifficulty;
	}


	public void setGameMode(EnumGamemode gamemode) {
		this.gamemode = gamemode;
	}
	public EnumGamemode getGameMode() {
		return gamemode;
	}

	public void setAllowMonsters(boolean b) {
		allowmonsters = b;
	}
	public boolean getAllowMonsters() {
		return allowmonsters;
	}


	public void setAllowAnimals(boolean b) {
		allowanimals = b;
	}
	public boolean getAllowAnimals() {
		return allowanimals;
	}


	public void setGenerateDecoration(boolean b) {
		gendeco = b;
	}
	public boolean canGenerateDecoration() {
		return gendeco;
	}
	
	public boolean isGeneratingSurface() {
		return generate_surface;
	}
	
	public void setGenerateSurface(boolean b) {
		generate_surface = b;
	}

	
	
	
}
