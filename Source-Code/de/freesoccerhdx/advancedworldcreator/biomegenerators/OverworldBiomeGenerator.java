package de.freesoccerhdx.advancedworldcreator.biomegenerators;


import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
//import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import net.minecraft.core.IRegistry;
import net.minecraft.data.worldgen.biome.BiomeRegistry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
//import net.minecraft.server.v1_16_R3.BiomeBase;
//import net.minecraft.server.v1_16_R3.BiomeRegistry;
//import net.minecraft.server.v1_16_R3.DedicatedServer;
//import net.minecraft.server.v1_16_R3.GenLayer;
//import net.minecraft.server.v1_16_R3.GenLayers;
//import net.minecraft.server.v1_16_R3.IRegistry;
//import net.minecraft.server.v1_16_R3.RegistryLookupCodec;
//import net.minecraft.server.v1_16_R3.ResourceKey;
//import net.minecraft.server.v1_16_R3.WorldChunkManager;
import net.minecraft.world.level.newbiome.layer.GenLayer;
import net.minecraft.world.level.newbiome.layer.GenLayers;

public class OverworldBiomeGenerator extends WorldChunkManager{
	
	public static class CREATOR {
		private long seed;
		private boolean legacy_biome_init_layer;
		private int biomesize = 4; //large = 6
		private IRegistry<BiomeBase> biomeregist;
		private HashMap<Integer, Integer> overwirtebiomes = new HashMap<>();

		
		public CREATOR(long seed) {
			this.seed = seed;
			this.legacy_biome_init_layer = false;
		
			biomeregist = GlobalValues.BiomeBase_Registry;
		}
		public CREATOR setBiomeSize(int i) {
			this.biomesize = i;
			return this;
		}
		
		public OverworldBiomeGenerator create() {
			OverworldBiomeGenerator du = new OverworldBiomeGenerator(seed, legacy_biome_init_layer, biomesize, biomeregist);
			du.setOverwriteBiomes(overwirtebiomes);
			return du;
		}
		
		public CREATOR overwriteBiome(ResourceKey<BiomeBase> target, RegisteredCustomBiome newbiome) {
			overwirtebiomes.put(biomeregist.getId(biomeregist.a(target)), newbiome.getBiomeID());
			return this;
		}
		
		public CREATOR overwriteBiome(ResourceKey<BiomeBase> target, ResourceKey<BiomeBase> newbiome) {
			overwirtebiomes.put(biomeregist.getId(biomeregist.a(target)), biomeregist.getId(biomeregist.a(newbiome)));
			
			
			
			return this;
		}
		
	}
	
	public static final Codec<OverworldBiomeGenerator> e = RecordCodecBuilder.create((var0) -> {
	      return var0.group(Codec.LONG.fieldOf("seed").stable().forGetter((var0x) -> {
	         return var0x.h;
	      }), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", false, Lifecycle.stable()).forGetter((var0x) -> {
	         return var0x.i;
	      }), Codec.INT.fieldOf("biomesize").orElse(4).stable().forGetter((var0x) -> {
	         return var0x.j;
	      }), RegistryLookupCodec.a(GlobalValues.BiomeBase_ResourceKey).forGetter((var0x) -> {
	         return var0x.k;
	      })).apply(var0, var0.stable(OverworldBiomeGenerator::new));
	   });
	
	  
	private HashMap<Integer, Integer> overwirtebiomes = new HashMap<>();
	private final GenLayer f;
	private static List<ResourceKey<BiomeBase>> gs;
	private final long h;
	private final boolean i;
	private final int j;
	private final IRegistry<BiomeBase> k;

	private OverworldBiomeGenerator(long seed, boolean var2, int var3, IRegistry<BiomeBase> var4) {   
		super(gs.stream().map((var1) -> {
			return () -> {
				//Bukkit.broadcastMessage("ddd: " + var1 + " -> " + var4);
				return var4.d(var1);
			};
		}));
	      
		this.h = seed;
		this.i = var2;
		this.j = var3;
		this.k = var4;
		this.f = GenLayers.a(seed, var2, var3, 4);
	      
	}
	public void setOverwriteBiomes(HashMap<Integer, Integer> overwirtebiomes) {
		this.overwirtebiomes = overwirtebiomes;
	}

	@Override
	protected Codec<? extends WorldChunkManager> a() {
		return e;
	}
  
	   
	
	@Override
	public BiomeBase getBiome(int var0, int var1, int var2) {
		BiomeBase bb = this.f.a(this.k, var0, var2);
	 
		int biomeid = k.getId(bb);
		if(this.overwirtebiomes.containsKey(biomeid)) {
			return this.k.a(BiomeRegistry.a(this.overwirtebiomes.get(biomeid)));
		}

		return bb;
	}

	static {	   
		IRegistry.a(GlobalValues.WorldChunkManager_Registry, "custom_vanilla_layered", OverworldBiomeGenerator.e);
		// biome check list if not exist => error on load
		gs = new ArrayList<>();//ImmutableList.of(Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, new ResourceKey[]{Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU});

	   }

	@Override
	public WorldChunkManager a(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}
}















