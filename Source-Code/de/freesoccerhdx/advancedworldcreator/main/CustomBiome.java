package de.freesoccerhdx.advancedworldcreator.main;

import java.awt.Color;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;

import com.mojang.datafixers.util.Pair;

import de.freesoccerhdx.advancedworldcreator.customstructures.CustomStructure;
import de.freesoccerhdx.advancedworldcreator.customstructures.trees.CustomForest;
import de.freesoccerhdx.advancedworldcreator.wrapper.BiomeStructure;
import de.freesoccerhdx.advancedworldcreator.wrapper.SurfaceBuilder;
import de.freesoccerhdx.advancedworldcreator.wrapper.SurfaceType;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenCarver;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenDecoration;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenFeature;

import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeBase.Geography;
import net.minecraft.world.level.biome.BiomeBase.Precipitation;
import net.minecraft.world.level.biome.BiomeBase.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeFog.GrassColor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeParticles;
import net.minecraft.world.level.biome.CaveSound;
import net.minecraft.world.level.biome.CaveSoundSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceComposite;
/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeFog;
import net.minecraft.server.v1_16_R3.BiomeBase.Geography;
import net.minecraft.server.v1_16_R3.BiomeBase.Precipitation;
import net.minecraft.server.v1_16_R3.BiomeBase.TemperatureModifier;
import net.minecraft.server.v1_16_R3.BiomeFog.GrassColor;
import net.minecraft.server.v1_16_R3.BiomeParticles;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.CaveSound;
import net.minecraft.server.v1_16_R3.CaveSoundSettings;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumCreatureType;
import net.minecraft.server.v1_16_R3.Music;
import net.minecraft.server.v1_16_R3.Musics;
import net.minecraft.server.v1_16_R3.ParticleParam;
import net.minecraft.server.v1_16_R3.ParticleType;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.StructureFeature;
import net.minecraft.server.v1_16_R3.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import net.minecraft.server.v1_16_R3.WorldGenSurfaceComposite;
import net.minecraft.server.v1_16_R3.WorldGenSurfaceConfigurationBase;
*/
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public class CustomBiome {

	private static int a(Color col) {
		return AdvancedWorldCreatorAPI.colorToHexaDecimal(col);
	}

	
	
	
	private SurfaceBuilder surface = SurfaceBuilder.GRASS;
	private WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> custom_surface = null;
	
	
	private SoundEffect ambientsound;
	private Music music = Musics.a;
	private CaveSound cavesound = null;
	private CaveSoundSettings cavesoundset = CaveSoundSettings.b;
	private BiomeParticles biomeparticles = null;

	private Color grass = null;
	private Color foliage = null;
	private Color sky = null;
	private Color waterfog = null;
	private Color fog = null;
	private Color water = null;
	
	// defaults
	private int grasscolor = -1;
	private int foliagecolor = -1;
	private int skycolor = 7907327;
	private int waterfogcolor = 329011;
	private int fogcolor = 12638463;
	private int watercolor = 4159204;
	
	private float depth = 0.125F;
	private float scale = 0.05F;
	private float temper = 0.8F;
	private float downfall = 0.4F;
	
	private float mobprobability = 0.1F;
	
	
	// TODO: ADD GEOGRAPHY_ENUM
	private Geography geography = BiomeBase.Geography.f; 
	// TODO: ADD PRECIPITATION_ENUM
	private Precipitation precipitation = BiomeBase.Precipitation.b;
	// TODO: ADD TEMPERATUREMODIFIER_ENUM
	private TemperatureModifier temperaturmodifier = BiomeBase.TemperatureModifier.a;
	private HashMap<EntityTypes,Integer[]> biomemobs = new HashMap<>();
	private HashMap<EntityTypes, EnumCreatureType> mobtype = new HashMap<>();
	
	
	private ArrayList<Pair<WorldGenFeature,WorldGenCarver>> features = new ArrayList<>(); //Features
	private ArrayList<Pair<WorldGenFeature,WorldGenCarverWrapper>> features2 = new ArrayList<>(); //Features
	
	private ArrayList<Pair<WorldGenDecoration,WorldGenFeatureConfigured<?, ?>>> generationfeatures = new ArrayList<>(); //Decoration
	private ArrayList<BiomeStructure> structfeatures = new ArrayList<>();
	private ArrayList<StructureFeature<?, ?>> custom_structfeatures = new ArrayList<>();

	private GrassColor grassmodifier = BiomeFog.GrassColor.a;
	
	private String biomename;
	private String prekey = "awc";
	
	public CustomBiome(String biomename) {
		this.biomename = biomename;
	}
	public CustomBiome(String prekey, String biomename) {
		this.prekey = prekey;
		this.biomename = biomename;
	}
	
	public String getBiomeName() {
		return biomename;
	}
	
	public String getPreKey() {
		return prekey;
	}
	public void setPreKey(String s) {
		prekey = s;
	}
	
	public boolean hasCustomSurfaceBuilder() {
		return custom_surface != null;
	}
	
	public void setCustomSurfaceBuilder(SurfaceType surfacetype,Block surface_block,Block under_surface_block,Block under_water_block) {
		
		//WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> 
		WorldGenSurfaceConfigurationBase surfacebase = new WorldGenSurfaceConfigurationBase(surface_block.getBlockData(), under_surface_block.getBlockData(), under_water_block.getBlockData());
		custom_surface = surfacetype.get().a(surfacebase);
		
	}
	
	public void setSurfaceBuilder(WorldGenSurfaceComposite<?> worldGenSurfaceComposite) {
		custom_surface = (WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase>) worldGenSurfaceComposite;
	}
	
	public WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> getCustomSurfaceBuilder(){
		return custom_surface;
	}
	
	public void addBiomeStructure(BiomeStructure bsf) {
		structfeatures.add(bsf);
	}
	public List<BiomeStructure> getBiomeStructures(){
		return structfeatures;
	}
	
	
	public void addWorldGenFeature(WorldGenFeature type, WorldGenCarver what) {
		features.add(Pair.of(type, what));
	}
	public void addWorldGenFeature(WorldGenFeature type, WorldGenCarverWrapper what) {
		features2.add(Pair.of(type, what));
	}
	
	public List<Pair<WorldGenFeature,WorldGenCarver>> getWorldGenFeatures(){
		return this.features;
	}
	public List<Pair<WorldGenFeature,WorldGenCarverWrapper>> getWorldGenCarverWrappers(){
		return this.features2;
	}
	
	public void addForest(WorldGenDecoration where, CustomForest forest) {
		addWorldGenDecorationFeature(where, forest.create());
	}
	public void addWorldGenDecorationFeature(WorldGenDecoration where, CustomStructure what) {
		addWorldGenDecorationFeature(where, what.create());
	}
	public void addWorldGenDecorationFeature(WorldGenDecoration where, WorldGenFeatureConfigured<?, ?> what) {
		generationfeatures.add(Pair.of(where, what));
	}
	public List<Pair<WorldGenDecoration,WorldGenFeatureConfigured<?, ?>>> getWorldGenDecorationFeatures(){
		return this.generationfeatures;
	}
	
	
	
	public void setSurfaceBuilder(SurfaceBuilder sb) {
		this.surface = sb;
	}
	public SurfaceBuilder getSurfaceBuilder() {
		return surface;
	}
	
	public void setDepth(float f) {
		this.depth = f;
	}
	public float getDepth() {
		return this.depth;
	}
	
	public void setScale(float f) {
		this.scale = f;
	}
	public float getScale() {
		return this.scale;
	}
	
	public void setTemperature(float f) {
		this.temper = f;
	}
	public float getTemperature(){
		return this.temper;
	}
	
	public void setDownfall(float f) {
		this.downfall = f;
	}
	public float getDownfall() {
		return this.downfall;
	}
	
	
	public void setAmbientSound(SoundEffect soundeffect) {
		this.ambientsound = soundeffect;
	}
	public SoundEffect getAmbientSound() {
		return ambientsound;
	}
	public boolean hasAmbientSound() {
		return ambientsound != null;
	}
	public void setMusic(SoundEffect soundeffect) {
		music = Musics.a(soundeffect);
	}
	public Music getMusic() {
		return music;
	}
	public boolean hasMusic() {
		return music != null;
	}
	public void setCaveSound(SoundEffect soundeffect, double tickchance) {
		this.cavesound = new CaveSound(soundeffect, tickchance);
	}
	public CaveSound getCaveSound() {
		return cavesound;
	}
	public boolean hasCaveSound() {
		return cavesound != null;
	}
	public void setCaveSoundSettings(SoundEffect soundeffect, int tickdelay, int blockdistance, double offset) {
		cavesoundset = new CaveSoundSettings(soundeffect, tickdelay, blockdistance, offset);
		
	}
	public CaveSoundSettings getCaveSoundSettings() {
		return cavesoundset;
	}
	public boolean hasCaveSoundSettings() {
		return cavesoundset != null;
	}
	
	public void setBiomeParticles(ParticleParam particletype, float quantity) {
		setBiomeParticles((ParticleType)particletype, quantity);
	}
	public void setBiomeParticles(ParticleType particletype, float quantity) {
		biomeparticles = new BiomeParticles(particletype, quantity);
	}
	public BiomeParticles getBiomeParticles() {
		return biomeparticles;
	}
	public boolean hasBiomeParticles() {
		return biomeparticles != null;
	}
	
	
	public static EntityTypes convertBukkitEntityTypeToNMS(EntityType entitytype) {
		try {
			return EntityTypes.a(entitytype.getKey().getKey()).orElse(null);
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	//public boolean addMobToBiome(EntityType entitytype, EnumCreatureType creaturetype,int weight, int mincount, int maxcount) {
	
	public boolean addMobToBiome(EntityTypes entitytype,int weight, int mincount, int maxcount) {
		try {

			biomemobs.put(entitytype, new Integer[] {weight,mincount,maxcount});
			mobtype.put(entitytype, entitytype.f());
			
			return true;

		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	public boolean addMobToBiome(EntityType entitytype,int weight, int mincount, int maxcount) {
		if(entitytype == EntityType.UNKNOWN) {
			return false;
		}
		try {
			EntityTypes nmsentity = convertBukkitEntityTypeToNMS(entitytype);
			
			if(nmsentity != null) {
				biomemobs.put(nmsentity, new Integer[] {weight,mincount,maxcount});
				mobtype.put(nmsentity, nmsentity.f());
				
				return true;
			}else {
				System.out.println("[AdvancedWorldCreatorAPI] Can't find the NMS-Entity for " + entitytype);
				
			}
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	public boolean hasMobInBiome(EntityType entitytype) {
		EntityTypes nmsentity = convertBukkitEntityTypeToNMS(entitytype);
		return this.biomemobs.containsKey(nmsentity);
	}
	public HashMap<EntityTypes,Integer[]> getBiomeMobs() {
		return this.biomemobs;
	}
	public HashMap<EntityTypes, EnumCreatureType> getMobsSpawnType(){
		return this.mobtype;
	}
	
	public void setFoliageColor(Color col) {
		this.foliage = col;
	}
	public void setWaterColor(Color col) {
		this.water = col;
	}
	public void setFogColor(Color col) {
		this.fog = col;
	}
	public void setWaterFogColor(Color col) {
		this.waterfog = col;
	}
	public void setSkyColor(Color col) {
		this.sky = col;
	}
	public void setGrassColor(Color col) {
		this.grass = col;
	}

	public int getFoliageColor() {
		if(this.foliage != null) {
			return a(this.foliage);
		}
		return this.foliagecolor;
	}
	public int getWaterColor() {
		if(this.water != null) {
			return a(this.water);
		}
		return this.watercolor;
	}
	public int getFogColor() {
		if(this.fog != null) {
			return a(this.fog);
		}
		return this.fogcolor ;
	}
	public int getWaterFogColor() {
		if(this.waterfog != null) {
			return a(this.waterfog);
		}
		return this.waterfogcolor ;
	}
	public int getSkyColor() {
		if(this.sky != null) {
			return a(this.sky);
		}
		return this.skycolor ;
	}
	public int getGrassColor() {
		if(this.grass != null) {
			return a(this.grass);
		}
		return this.grasscolor ;
	}
	
	public Geography getGeography() {
		return this.geography;
	}
	public void setGeography(Geography geo) {
		this.geography = geo;
	}

	public Precipitation getPrecipitation() {
		return this.precipitation;
	}
	public void setPrecipitation(Precipitation preci) {
		this.precipitation = preci;
	}

	public TemperatureModifier getTemperatureModifier() {
		return this.temperaturmodifier;
	}
	public void setTemperatureModifier(TemperatureModifier temp) {
		this.temperaturmodifier = temp;
	}
	
	public void setMobSpawnProbability(float val) {
		this.mobprobability = val;
	}
	public float getMobSpawnProbability() {
		return mobprobability;
	}
	
	
	public void addCustomWorldStructureFeature(StructureFeature<?, ?> datapackStructureFeature) {
		custom_structfeatures.add(datapackStructureFeature);
	}
	
	public ArrayList<StructureFeature<?,?>> getCustomWorldStructureFeatures(){
		return custom_structfeatures;
	}
	
	
	protected GrassColor getGrassModifier() {
		return grassmodifier;
	}
	protected void setGrassModifier(GrassColor grassmodifier) {
		this.grassmodifier = grassmodifier; 
	}
	
	
	public boolean copyVanillaBiomeSettings(ResourceKey<BiomeBase> vanillabiome, CloneOptions options) {
		return BiomeWriter.copyVanillaBiome(vanillabiome, this, options);
	}
	public boolean overwriteVanillaBiomeSettings(ResourceKey<BiomeBase> vanillabiome, CloneOptions options) {
		return BiomeWriter.overwriteVanillaBiome(vanillabiome, this, options);
	}
	
	
	
	
}
