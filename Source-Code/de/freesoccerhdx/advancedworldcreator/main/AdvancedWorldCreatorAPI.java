package de.freesoccerhdx.advancedworldcreator.main;



import java.awt.Color;






import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;

import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectList;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
//import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
//import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

import de.freesoccerhdx.advancedworldcreator.biomegenerators.OverworldBiomeGenerator;
import de.freesoccerhdx.advancedworldcreator.biomegenerators.SingleBiomeGenerator;
import de.freesoccerhdx.advancedworldcreator.customstructures.trees.CustomAcaciaTreeStructure;
import de.freesoccerhdx.advancedworldcreator.customstructures.trees.CustomDarkOakTreeStructure;
import de.freesoccerhdx.advancedworldcreator.customstructures.trees.CustomForest;
import de.freesoccerhdx.advancedworldcreator.wrapper.BiomeStructure;
import de.freesoccerhdx.advancedworldcreator.wrapper.GeneratorSetting;
import de.freesoccerhdx.advancedworldcreator.wrapper.SurfaceBuilder;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenCarver;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenDecoration;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenFeature;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldStructureGenerator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.data.worldgen.BiomeDecoratorGroups;
import net.minecraft.data.worldgen.biome.BiomeRegistry;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryReadOps;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.MobSpawnerCat;
import net.minecraft.world.entity.npc.MobSpawnerTrader;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.BiomeSettingsMobs.a;
import net.minecraft.world.level.biome.BiomeSettingsMobs.c;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.GeneratorSettings;
import net.minecraft.world.level.levelgen.MobSpawnerPatrol;
import net.minecraft.world.level.levelgen.MobSpawnerPhantom;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlideSettings;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenStage.Decoration;
import net.minecraft.world.level.levelgen.WorldGenStage.Features;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.StructureSettingsFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureSettingsStronghold;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceComposite;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.WorldDataServer;



public class AdvancedWorldCreatorAPI extends JavaPlugin implements Listener {

	
	protected static AdvancedWorldCreatorAPI main = null;
	
	private static DataPackResolver datapackfeatureresolver;
	
	private static List<ResourceKey<BiomeBase>> vanillabiomes = new ArrayList<>();
	
	@Override
	public void onEnable() {
		main = this;
		
		loadVanillaBiomes();
		
		datapackfeatureresolver = new DataPackResolver();
		
		
		if(false) {
		//	Bukkit.getPluginManager().registerEvents(new TestCommands(), main);
		}else {
			Bukkit.getPluginManager().registerEvents(main, main);
		}
		
	}
	
	
	
	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().split(" ");
		String cmd = args[0];
		Player p = e.getPlayer();
		
		if(cmd.equalsIgnoreCase("/testworld")) {
			
			AdvancedCreator advancedcreator = new AdvancedCreator("mytestworld");
			
			//advancedcreator.setGenerateDecoration(true); 

			
			
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.a, false); //RAW_GENERATION
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.b, false); //LAKES
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.c, false); //LOCAL_MODIFICATIONS
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.d, false); //UNDERGROUND_STRUCTURES
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.e, false); //SURFACE_STRUCTURES
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.f, false); //STRONGHOLDS
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.g, false); //UNDERGROUND_ORES
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.h, false); //UNDERGROUND_DECORATION
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.i, false); //VEGETAL_DECORATION
			advancedcreator.setDecorationBlacklisted(WorldGenStage.Decoration.j, false); //TOP_LAYER_MODIFICATION
			
			advancedcreator.addStructureGeneratorConfig(WorldStructureGenerator.VILLAGE, 40, 2);
			
			 //xz_scale, y_scale, xz_factor, y_factor
	        NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(0.9999999814507745, 0.9999999814507745, 80, 160);
	        // target, size, offset
	        // was -10, 3, 0 // -30 0 0
	        NoiseSlideSettings top_slide = new NoiseSlideSettings(-10, 3, 0);
	        NoiseSlideSettings bottom_slide = new NoiseSlideSettings(-30, 0, 0);
	        int size_horizontal = 1; // was 1
	        int size_vertical = 2; // was 2
	        int miny = -32;
	        int world_height = 256;
	        double density_factor = 1.0;
	        double density_offset = -0.46875;
	        boolean simplex_surface_noise = true;
	        boolean random_density_offset = true;
	        boolean island_noise_override = false;
	        boolean amplified = false;
	 
	        NoiseSettings noisesettings = NoiseSettings.a(miny, world_height, noisesampling, top_slide, bottom_slide, size_horizontal, size_vertical,
	                density_factor, density_offset, simplex_surface_noise, random_density_offset, island_noise_override, amplified);
	 
	        Block solid_material = Blocks.b;
	        Block fluid_material = Blocks.A;
			
			
		//	advancedcreator.setCustomWorldSetting(noisesettings, solid_material, fluid_material, -1000, 0, 63, -32, false, true, true, true, true, true);
			advancedcreator.setWorldSetting(GeneratorSetting.OVERWORLD);
			
			
			
			CustomDimensionSettings dimensionmanager = new CustomDimensionSettings(
					OptionalLong.empty(), true, false, false, true, 1.0, true, true, 
					true, true, true, 
					-32, 256, 256, new MinecraftKey("minecraft","sand"),
					new MinecraftKey("minecraft","overworld"), 1.0f);
			
			WorldChunkManager worldchunkmanager = null;
			
			
			CustomBiome cb = new CustomBiome("myftb", "custombiome");
			cb.setFogColor(new Color(255,255,255));
			cb.setGrassColor(Color.RED);
			cb.setSkyColor(Color.CYAN);
			cb.setWaterColor(Color.YELLOW);
			cb.setWaterFogColor(Color.DARK_GRAY);
			cb.setSurfaceBuilder(SurfaceBuilder.GRASS);
			
			CustomAcaciaTreeStructure cdots = new CustomAcaciaTreeStructure("testdarkoak"); 
			CustomForest cf = new CustomForest("customforesqt", cdots);
			
			
			cb.addWorldGenDecorationFeature(WorldGenDecoration.VEGETAL_DECORATION, cf);
			
		//	cb.addWorldGenDecorationFeature(Decoration.i, BiomeDecoratorGroups.dl);
			
			RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb, true);
			
			
			
			//overworld
		//	worldchunkmanager = new OverworldBiomeGenerator.CREATOR(advancedcreator.getSeed()).create();
			
			//singlebiome
			worldchunkmanager = new SingleBiomeGenerator.CREATOR(rcb).create();
			
			
			AdvancedWorldCreatorAPI.createWorld(advancedcreator, CustomDimensionSettings.getEndSettings(), worldchunkmanager);
			
			p.sendMessage("§aCreated.");
			e.setCancelled(true);
			
			p.teleport(Bukkit.getWorld("mytestworld").getSpawnLocation());
		}
		
		if(cmd.equalsIgnoreCase("/tpworld")) {
			
			p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
			p.sendMessage("§aTeleported.");
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/unloadtestworld")) {
			
			World w = Bukkit.getWorld("mytestworld");
			
			for(Player c : w.getPlayers()) {
				c.teleport(Bukkit.getWorld("world").getSpawnLocation());
			}
			
			Bukkit.getScheduler().runTaskLater(main, new Runnable() {

				@Override
				public void run() {
					Bukkit.unloadWorld(w, false);
					p.sendMessage("§aUnloaded.");
				}
				
			}, 20*3);
			
			
			e.setCancelled(true);
		}
		
	}
	
	
	private static void loadVanillaBiomes() {
		vanillabiomes.clear();
		try {
		
			for(Field f : Biomes.class.getFields()) {
				Object biome = f.get(null);
				if(biome instanceof ResourceKey) {
					vanillabiomes.add((ResourceKey<BiomeBase>) biome);
				}
			}
			System.out.println("[AdvancedWorldCreatorAPI] All Vanilla Biomes: " + vanillabiomes.size());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static List<ResourceKey<BiomeBase>> getAllVanillaBiomes(){
		return ImmutableList.copyOf(vanillabiomes);
	}
	
	public static DataPackResolver getDataPackResolver() {
		return datapackfeatureresolver;
	}
	public static DataPackResolver getDPR() {
		return datapackfeatureresolver;
	}
	
	
	public static boolean isCustomBiome(RegisteredCustomBiome regicustbiome, Location loc) {
		return isCustomBiome(regicustbiome, loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public static boolean isCustomBiome(RegisteredCustomBiome regicustbiome, World world, int x, int y, int z) {
		
		CraftWorld craftworld = (CraftWorld) world;
		WorldServer worldserver = craftworld.getHandle();
		
		
		BiomeBase bb = worldserver.getBiome(x, y, z);
		int biomeid = GlobalValues.BiomeBase_Registry.getId(bb);
		
		
		return biomeid == regicustbiome.getBiomeID();
	
	}
	
	
	
	private static Object getField(Class<?> clazz, String fieldname, boolean removefinal) {
		try {
			Field fs = clazz.getDeclaredField(fieldname);
			fs.setAccessible(true);
			FieldUtils.removeFinalModifier(fs);
			
			if(removefinal) {
				FieldUtils.removeFinalModifier(fs);
				/*
				for(Field f : Field.class.getDeclaredFields()) {
					Bukkit.broadcastMessage("§4" + f.getType() + " .-> " + f);
				}
				
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(fs, fs.getModifiers() &~ Modifier.FINAL);
				*/
			}
			
			
			return fs.get(clazz);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static int colorToHexaDecimal(Color col){
		  String hex = String.format("%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());  
	      return Integer.parseInt(hex, 16);
	}
	
	
	private static boolean overwrite(String fieldname, Object source, Object target) {
	//	Bukkit.broadcastMessage(fieldname + " > " + source + " > " + target);
		try {
			Field g = BiomeBase.class.getDeclaredField(fieldname);
			g.setAccessible(true);
			FieldUtils.removeFinalModifier(g);
			Object value = FieldUtils.readField(g, source);
			FieldUtils.writeField(g, target, value);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	private static boolean overwriteBiomeBase(BiomeBase oldbb, BiomeBase newbb) {
		
		try {
			//Server server = Bukkit.getServer();
			//CraftServer cs = (CraftServer) server;
			//DedicatedServer console = cs.getServer();
			
			/*
			this.g = (Map<Integer, List<StructureGenerator<?>>>)IRegistry.STRUCTURE_FEATURE.g().collect(Collectors.groupingBy(var0 -> Integer.valueOf(var0.f().ordinal())));
			this.q = ThreadLocal.withInitial(() -> (Long2FloatLinkedOpenHashMap)SystemUtils.<Long2FloatLinkedOpenHashMap>a(()));
			this.j = var0;
			this.k = var5;
			this.l = var6;
			this.o = var1;
			this.m = var2;
			this.n = var3;
			this.p = var4;
			 */
			
			
			boolean b = overwrite("g",newbb,oldbb);
			boolean b1 = overwrite("q",newbb,oldbb);
			boolean b2 = overwrite("r",newbb,oldbb);
			boolean b3 = overwrite("k",newbb,oldbb);
			boolean b4 = overwrite("l",newbb,oldbb);
			boolean b5 = overwrite("o",newbb,oldbb);
			boolean b6 = overwrite("m",newbb,oldbb);
			boolean b7 = overwrite("n",newbb,oldbb);
			boolean b8 = overwrite("p",newbb,oldbb);
			
			return (b && b1 && b2 && b3 && b4 && b5 && b6 && b7 && b8); 
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	protected static BiomeBase getBiomeBase(ResourceKey<BiomeBase> rkbb) {
		try {			
			return GlobalValues.BiomeBase_Registry.a(rkbb);
			
			/*
			IRegistryWritable<BiomeBase> registBB = console.customRegistry.b(IRegistry.ay);
			
			return registBB.a(rkbb);
			*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RegisteredCustomBiome registerCustomBiome(CustomBiome cb) {
		return registerCustomBiome(cb, false);
	}
	public static RegisteredCustomBiome registerCustomBiome(CustomBiome cb, boolean replace) {
		try {			
			String biomename = cb.getBiomeName().toLowerCase();
			String prename = cb.getPreKey().toLowerCase();
	
			Object c_obj = getField(BiomeRegistry.class, "c", true);
			//Bukkit.broadcastMessage("§ccobj: " + c_obj.getClass().getName());
			//import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap;	
			org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap<ResourceKey<BiomeBase>> bbb = ( org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap<ResourceKey<BiomeBase>>) c_obj;
			
			ResourceKey<BiomeBase> resourcekeybiome = ResourceKey.a(GlobalValues.BiomeBase_ResourceKey, new MinecraftKey(prename, biomename));
			BiomeBase bb = null;	
			int maxid = -1;
			
			
			for(int i : bbb.keySet()) {
				maxid = Math.max(maxid, i);
				ResourceKey<BiomeBase> otherrkbb = bbb.get(i);
				
				if(otherrkbb.a().getNamespace().equals(prename) && otherrkbb.a().getKey().equals(biomename)) { // biome already found
					
					if(replace) {
						bb = createBiomeBase(cb);
						BiomeBase oldbb = getBiomeBase(otherrkbb);
						boolean replaced = overwriteBiomeBase(oldbb,bb);//unregisterCustomBiome(cb,bb,resourcekeybiome,i,newid);
						if(!replaced) {
							System.err.println("[AdvancedWorldCreatorAPI] The Biome-Settings were not Overwritten!");
						}
					}
					return new RegisteredCustomBiome(otherrkbb, cb, i);
					/*
					if(replace) {
						bb = createBiomeBase(cb);
						BiomeBase oldbb = getOldBiomeBase(otherrkbb);
						boolean replaced = overwriteBiomeBase(oldbb, bb);//unregisterCustomBiome(cb,bb,resourcekeybiome,i,newid);
						lastid = i;
						hasreplaced = replaced;
						Bukkit.broadcastMessage("Replaced: ? " + replaced);
						
						if(replaced) {
							bbb.put(newid, resourcekeybiome);
							return new RegisteredCustomBiome(resourcekeybiome, cb, newid);
						}
					}else {
						return new RegisteredCustomBiome(otherrkbb, cb, i);
					}
					*/
				}
				
			}
			int newbiomeid = maxid + 1;
			
			if(bb == null) {
				bb = createBiomeBase(cb);
			}
			
			
			// register new biome
			
			
			bbb.put(newbiomeid, resourcekeybiome);
			ResourceKey.a(GlobalValues.BiomeBase_ResourceKey, resourcekeybiome.a());		
			GlobalValues.CustomConsoleRegistry.b(GlobalValues.BiomeBase_ResourceKey).a(resourcekeybiome, bb, Lifecycle.stable());

			
			return new RegisteredCustomBiome(resourcekeybiome, cb, newbiomeid);
			
		}catch(NullPointerException | ClassCastException ex2){
			throw new NullPointerException("Registeriing a Custom Biome is not supported with this Server Version");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	protected static BiomeBase createBiomeBase(CustomBiome cb) {
		
		WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> surfacebuilder;
		if(cb.hasCustomSurfaceBuilder()) {
			surfacebuilder = cb.getCustomSurfaceBuilder();
		}else {
			surfacebuilder = cb.getSurfaceBuilder().get();
		}
		
		BiomeSettingsGeneration.a bisege = (new BiomeSettingsGeneration.a()).a(surfacebuilder);
		List<Pair<WorldGenFeature, WorldGenCarver>> worldgenfea = cb.getWorldGenFeatures();
		
		for(Pair<WorldGenFeature,WorldGenCarver> pair : worldgenfea) {
			Features deco = pair.getFirst().get();
			WorldGenCarverWrapper<?> gen = pair.getSecond().get();
			bisege.a(deco, gen);
		}
		List<Pair<WorldGenFeature, WorldGenCarverWrapper>> worldgenfea2 = cb.getWorldGenCarverWrappers();
		
		for(Pair<WorldGenFeature,WorldGenCarverWrapper> pair : worldgenfea2) {
			Features deco = pair.getFirst().get();
			bisege.a(deco, pair.getSecond());
		}
		
		
		
		List<Pair<WorldGenDecoration,WorldGenFeatureConfigured<?, ?>>> worldgendeco = cb.getWorldGenDecorationFeatures();
		
		for(Pair<WorldGenDecoration,WorldGenFeatureConfigured<?, ?>> pair : worldgendeco) {
			Decoration deco = pair.getFirst().get();
			WorldGenFeatureConfigured<?, ?> gen = pair.getSecond();
			bisege.a(deco, gen);
			
		}
		
		List<BiomeStructure> bsfs = cb.getBiomeStructures();
		for(BiomeStructure bsf : bsfs) {
			bisege.a(bsf.get());
		}
		
		List<StructureFeature<?, ?>> customstructfeature = cb.getCustomWorldStructureFeatures();
		for(StructureFeature<?, ?> bsf : customstructfeature) {
			bisege.a(bsf);
		}
		
		a bisemo = new BiomeSettingsMobs.a();
		bisemo.a(cb.getMobSpawnProbability());
		HashMap<EntityTypes,Integer[]> biomemobs = cb.getBiomeMobs();
		HashMap<EntityTypes, EnumCreatureType> mobtype = cb.getMobsSpawnType();
		
		for(EntityTypes et : biomemobs.keySet()) {
			Integer[] spawndata = biomemobs.get(et);
			int weight = spawndata[0];
			int mincount = spawndata[1];
			int maxcount = spawndata[2];
			EnumCreatureType ect = mobtype.get(et);
			bisemo.a(ect, new c(et, weight, mincount, maxcount));
		}
		
		
		BiomeFog.a bf = new BiomeFog.a();
		bf.a(cb.getFogColor());
		bf.b(cb.getWaterColor());
		bf.c(cb.getWaterFogColor());
		bf.d(cb.getSkyColor());
		bf.a(cb.getGrassModifier());
		
		if(cb.getFoliageColor() != -1) {
			bf.e(cb.getFoliageColor());
		}
		
		if(cb.getGrassColor() != -1) {
			bf.f(cb.getGrassColor());
		}
		
		
		
		if(cb.hasBiomeParticles()) {
			bf.a(cb.getBiomeParticles());
		}
		if(cb.hasCaveSoundSettings()) {
			bf.a(cb.getCaveSoundSettings());
		}
		if(cb.hasCaveSound()) {
			bf.a(cb.getCaveSound());
		}
		if(cb.hasMusic()) {
			bf.a(cb.getMusic());
		}
		if(cb.hasAmbientSound()) {
			bf.a(cb.getAmbientSound());
		}
		
		
		BiomeBase.a bb = new BiomeBase.a();  
		
		bb.a(cb.getGeography());
		bb.a(cb.getPrecipitation());
		bb.a(cb.getTemperatureModifier());
		bb.a(bf.a());
		bb.a(bisege.a());
		bb.a(bisemo.b());
		bb.a(cb.getDepth()); //depth: Used for terrain noise generation. Biomes with positive depth are considered land, biomes with negative depth are oceans.
	    bb.b(cb.getScale()); //scale: Used for terrain noise generation. Vertically stretches the terrain. Lower values produce flatter terrain.
	    bb.c(cb.getTemperature()); //0.0-1.0: Controls gameplay features like grass and foliage color and whether snow golems take damage.
	    bb.d(cb.getDownfall()); //0.0-1.0: Controls grass and foliage color, a value above 0.85 also makes fire burn out faster.
		
		
		return bb.a();
	}
	
	
	protected static NewDummyChunkGeneratorAbstract createChunkGenerator(AdvancedCreator creator, WorldChunkManager wcm, IRegistry<BiomeBase> var0, IRegistry<GeneratorSettingBase> var1) {
//		GeneratorSettingBase gsb = GeneratorSettingBase.i();
		Map<StructureGenerator<?>, StructureSettingsFeature> worldgenoptions = creator.getStructuresGeneratorConfigs();
		
		Optional<StructureSettingsStronghold> optional = Optional.of(StructureSettings.c);
		StructureSettings ss = new StructureSettings(optional, worldgenoptions);
	
		
		GeneratorSettingBase settingbase;
		
		if(creator.hasCustomWorldSetting()) {
			settingbase = creator.getCustomWorldSetting();
		}else {
			settingbase = var1.d(creator.getWorldSetting().get());
		}
		
		
		
		NewDummyChunkGeneratorAbstract chunkgenerator = new NewDummyChunkGeneratorAbstract(
        		wcm,
        		creator.getSeed(),
        		ss,
        		() -> settingbase
        		);
		
		chunkgenerator.applyAdvancedCreator(creator);
		
		/*
		DummyChunkGeneratorAbstract chunkgenerator = new DummyChunkGeneratorAbstract(
        		wcm,
        		creator.getSeed(),
        		ss,
        		() -> settingbase
        		);
		*/
		
		/*
	    chunkgenerator.setTopBedrock(creator.getTopBedrock());
	    chunkgenerator.setBottomBedrock(creator.getBottomBedrock());
	    chunkgenerator.setCanCreateStructures(creator.canGenerateStructures());
	    chunkgenerator.setCanCreateDecorations(creator.canGenerateDecoration());
	    chunkgenerator.setDecorationBlacklist(creator.getBlacklistedDecoration());
	    chunkgenerator.setAirCavesDisabled(creator.isAirCavesDisabled());
	    chunkgenerator.setLiquidCavesDisabled(creator.isLiquidCavesDisabled());
	    chunkgenerator.setGenerateSurface(creator.isGeneratingSurface());
		*/
		
		
		return chunkgenerator;
	}	
	protected static RegistryMaterials<WorldDimension> registerWorldDimension(String mc_worldname, ChunkGenerator chunkgenerator, 
			IRegistry<DimensionManager> var0) {
		
		
		
		RegistryMaterials<WorldDimension> var5 = new RegistryMaterials(IRegistry.R, Lifecycle.experimental());
		//b(var1, var2, var3))
		ResourceKey<DimensionManager> manager = ResourceKey.a(IRegistry.P, new MinecraftKey("awc",mc_worldname));
		var5.a(WorldDimension.b, new WorldDimension(() -> var0.d(manager), chunkgenerator), Lifecycle.stable());
		return var5;
	}
	
	private static File getWorldContainer(DedicatedServer console) {
		
		return console.j.a(net.minecraft.world.level.World.f).getParentFile(); 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static World createWorld(AdvancedCreator creator, CustomDimensionSettings dimensionsettings, WorldChunkManager wcm) {
		Validate.notNull(creator, "AdvancedCreator may not be null");
		Validate.notNull(dimensionsettings, "DimensionSettings may not be null");
		Validate.notNull(wcm, "WorldChunkManager may not be null");
		
		Server server = Bukkit.getServer();
		CraftServer cs = (CraftServer) server;
		DedicatedServer console = cs.getServer();
		
		String worldname = creator.getWorldName();
		String mc_worldname = (worldname + System.currentTimeMillis()).toLowerCase();
		boolean hardcore = creator.isHardcore();
		String levelgeneratorsettings = "";
		long seed = creator.getSeed();
		boolean generatestructures = creator.canGenerateStructures();
		boolean generatedeco = creator.canGenerateDecoration();
		Environment environment = Environment.NORMAL;
		WorldType worldtype = WorldType.NORMAL;
		org.bukkit.generator.ChunkGenerator generator = null;
		EnumGamemode worldgamemode = creator.getGameMode();
		
	    ResourceKey<WorldDimension> actualDimension = WorldDimension.b;
	    Convertable.ConversionSession worldSession;
	    NewDummyChunkGeneratorAbstract chunkgenerator = null;
	    Preconditions.checkState(!console.R.isEmpty(), "Cannot create additional worlds on STARTUP");
	    
	  
	    File folder = new File(getWorldContainer(console), worldname);
	    World world = cs.getWorld(worldname);
	    if (world != null) {
	      return world; 
	    }
	    if (folder.exists() && !folder.isDirectory())
	      throw new IllegalArgumentException("File exists with the name '" + worldname + "' and isn't a folder"); 
	
	    
	    try {
	      worldSession = Convertable.a(getWorldContainer(console).toPath()).c(worldname, actualDimension);
	    } catch (IOException ex) {
	      throw new RuntimeException(ex);
	    } 
	    
	    MinecraftServer.convertWorld(worldSession);

	    RegistryReadOps<NBTBase> registryreadops = RegistryReadOps.a((DynamicOps)DynamicOpsNBT.a, console.aC.i(), (IRegistryCustom)console.l);
	    
	    //RegistryReadOps<NBTBase> registryreadops = RegistryReadOps.a((DynamicOps)DynamicOpsNBT.a, console.aC.h(), GlobalValues.CustomConsoleRegistry);
	   
	    WorldDataServer worlddata = (WorldDataServer)worldSession.a(registryreadops, console.datapackconfiguration);
	   
	    if (worlddata == null) {
	      Properties properties = new Properties();
	      properties.put("generator-settings", levelgeneratorsettings);
	      properties.put("level-seed", Objects.toString(seed));
	      properties.put("generate-structures", Objects.toString(generatestructures));
	      properties.put("level-type", Objects.toString(worldtype.getName()));
	      GeneratorSettings generatorsettings = GeneratorSettings.a(console.getCustomRegistry(), properties);
	      // allow commands
	      WorldSettings worldSettings = new WorldSettings(
	    		  worldname, worldgamemode, hardcore, creator.getDifficulty(), 
	    		  false, new GameRules(), console.datapackconfiguration);
	      
	      worlddata = new WorldDataServer(worldSettings, generatorsettings, Lifecycle.stable());
	    } 
	    worlddata.checkName(worldname);
	    worlddata.a(console.getServerModName(), console.getModded().isPresent());
	    
	    
	    
	    if (console.options.has("forceUpgrade"))
	      Main.convertWorld(worldSession, DataConverterRegistry.a(), console.options.has("eraseCache"), () -> true,
	          worlddata.getGeneratorSettings().d().d().stream().map(entry -> ResourceKey.a(GlobalValues.DimensionManager_ResourceKey, ((ResourceKey)entry.getKey()).a()))
	          .collect(ImmutableSet.toImmutableSet())); 
	    
	    long j = BiomeManager.a(creator.getSeed());
	    ImmutableList<MobSpawner> immutableList = ImmutableList.of(new MobSpawnerPhantom(), new MobSpawnerPatrol(), new MobSpawnerCat(), new VillageSiege(), new MobSpawnerTrader(worlddata));
	    
	    ResourceKey<DimensionManager> OVERWORLDMIXER_Manager = ResourceKey.a(GlobalValues.DimensionManager_ResourceKey,
				new MinecraftKey("awc",mc_worldname));
	    
		IRegistryWritable<DimensionManager> dimManagerregist = GlobalValues.CustomConsoleRegistry.b(GlobalValues.DimensionManager_ResourceKey);
		// ResourceKey<DimensionManager> , DimensionManager
		
		
		if(dimensionsettings.get() != CustomDimensionSettings.getOverworldSettings().get() 
				&& dimensionsettings.get() != CustomDimensionSettings.getNetherSettings().get()
				&& dimensionsettings.get() != CustomDimensionSettings.getEndSettings().get()) {
			
			dimManagerregist.a(OVERWORLDMIXER_Manager, dimensionsettings.get(), Lifecycle.stable());
		}
		
	    
		//ResourceKey.a(IRegistry.M, WorldDimension.OVERWORLD.a());
	    
	    
		//worlddata.getGeneratorSettings().d();
	     

	    
	    
	  
	  //  dimensionmanager = worlddimension.b();
	  //  chunkgenerator = worlddimension.c();
	    
		//  chunkgenerator = createChunkGenerator(creator,wcm, console.customRegistry.b(IRegistry.ay), console.customRegistry.b(IRegistry.ar));
		  chunkgenerator = createChunkGenerator(creator,wcm, GlobalValues.BiomeBase_Registry, GlobalValues.GeneratorSettingBase_Registry);
		  
	    
	    RegistryMaterials<WorldDimension> registrymaterials = registerWorldDimension(mc_worldname, chunkgenerator, GlobalValues.DimensionManager_Registry);
	
	    //15.06.2021 no differents noticed    WorldDimension worlddimension = registrymaterials.a(actualDimension);
	    
	    
	   /* 
	    chunkgenerator.setTopBedrock(creator.getTopBedrock());
	    chunkgenerator.setBottomBedrock(creator.getBottomBedrock());
	    chunkgenerator.setCanCreateStructures(generatestructures);
	    chunkgenerator.setCanCreateDecorations(generatedeco);
	    chunkgenerator.setDecorationBlacklist(creator.getBlacklistedDecoration());
	    chunkgenerator.setAirCavesDisabled(creator.isAirCavesDisabled());
	    chunkgenerator.setLiquidCavesDisabled(creator.isLiquidCavesDisabled());
	    chunkgenerator.setGenerateSurface(creator.isGeneratingSurface());
	    */
	    
	    
	    
	    ResourceKey<net.minecraft.world.level.World> worldKey = ResourceKey.a(IRegistry.Q, new MinecraftKey(worldname.toLowerCase(Locale.ENGLISH)));
	    WorldServer internal = new WorldServer(console, console.aA, worldSession, worlddata, worldKey, dimensionsettings.get(), (console.getServer()).L.create(11), 
	        chunkgenerator, worlddata.getGeneratorSettings().isDebugWorld(), j, (environment == World.Environment.NORMAL) ? (List)immutableList : (List)ImmutableList.of(), true, environment, generator);
	   /*
	    if (!cs.getWorlds().contains(name.toLowerCase(Locale.ENGLISH))) {
	    	try {
				internal.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	Bukkit.broadcastMessage("Return null");
	    	return null; 
	    }
	    */
	    
	    boolean allowMonsters = creator.getAllowMonsters();
	    boolean allowAnimals = creator.getAllowAnimals();
	   
	    console.initWorld(internal, worlddata, worlddata, worlddata.getGeneratorSettings());

	    internal.setSpawnFlags(allowMonsters, allowAnimals);
	    console.R.put(internal.getDimensionKey(), internal);
	    server.getPluginManager().callEvent(new WorldInitEvent(internal.getWorld()));
	
	   // loadSpawn((internal.getChunkProvider()).playerChunkMap.worldLoadListener, internal);

	    server.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
	    
	//    internal.getServer().getServer().getServer().loadSpawn(internal, worldserver);
	    return internal.getWorld();
	  }
	
	
	
	
	
	
	
	
	
	
	
	
}
