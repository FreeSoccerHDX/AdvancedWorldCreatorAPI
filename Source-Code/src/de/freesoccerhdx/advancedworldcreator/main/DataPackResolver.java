package de.freesoccerhdx.advancedworldcreator.main;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.StructureFeature;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
*/

public class DataPackResolver {

	
	/*
	 public static final ResourceKey<IRegistry<GeneratorSettingBase>> ar = a("worldgen/noise_settings");
  
  public static final ResourceKey<IRegistry<WorldGenSurfaceComposite<?>>> as = a("worldgen/configured_surface_builder");
  
  public static final ResourceKey<IRegistry<WorldGenCarverWrapper<?>>> at = a("worldgen/configured_carver");
  
  public static final ResourceKey<IRegistry<ProcessorList>> aw = a("worldgen/processor_list");
  
  public static final ResourceKey<IRegistry<WorldGenFeatureDefinedStructurePoolTemplate>> ax = a("worldgen/template_pool");
  
  public static final ResourceKey<IRegistry<WorldGenSurface<?>>> az = a("worldgen/surface_builder");
  
  public static final IRegistry<WorldGenSurface<?>> SURFACE_BUILDER = a(az, () -> WorldGenSurface.v);
  
  public static final ResourceKey<IRegistry<WorldGenCarverAbstract<?>>> aB = a("worldgen/carver");
  
  public static final ResourceKey<IRegistry<WorldGenerator<?>>> aD = a("worldgen/feature");
  
  public static final ResourceKey<IRegistry<StructureGenerator<?>>> aF = a("worldgen/structure_feature");
  
  public static final ResourceKey<IRegistry<WorldGenFeatureStructurePieceType>> aH = a("worldgen/structure_piece");
  
  public static final ResourceKey<IRegistry<WorldGenDecorator<?>>> aJ = a("worldgen/decorator");
  
  public static final ResourceKey<IRegistry<WorldGenFeatureStateProviders<?>>> aL = a("worldgen/block_state_provider_type");
  
  public static final ResourceKey<IRegistry<WorldGenBlockPlacers<?>>> aM = a("worldgen/block_placer_type");
  
  	public static final ResourceKey<IRegistry<WorldGenFoilagePlacers<?>>> aN = a("worldgen/foliage_placer_type");
  
  	public static final ResourceKey<IRegistry<TrunkPlacers<?>>> aO = a("worldgen/trunk_placer_type");
  
  	public static final ResourceKey<IRegistry<WorldGenFeatureTrees<?>>> aP = a("worldgen/tree_decorator_type");
  
  public static final ResourceKey<IRegistry<FeatureSizeType<?>>> aQ = a("worldgen/feature_size_type");
  
  public static final ResourceKey<IRegistry<Codec<? extends WorldChunkManager>>> aR = a("worldgen/biome_source");
  
  public static final ResourceKey<IRegistry<Codec<? extends ChunkGenerator>>> aS = a("worldgen/chunk_generator");
  
  public static final ResourceKey<IRegistry<DefinedStructureStructureProcessorType<?>>> aT = a("worldgen/structure_processor");
  
  public static final ResourceKey<IRegistry<WorldGenFeatureDefinedStructurePools<?>>> aU = a("worldgen/structure_pool_element"); 
	 */
	
	

	  

	
	// Not Functional:
	//public static final ResourceKey<IRegistry<StructureGenerator<?>>> aF = a("worldgen/structure_feature");
	//private IRegistry<StructureGenerator<?>> structuregenerator_registry;
	
	
	
	//public static final ResourceKey<IRegistry<BiomeBase>> ay = a("worldgen/biome");
	private IRegistry<BiomeBase> biomebase_registry;
	//public static final ResourceKey<IRegistry<WorldGenFeatureConfigured<?, ?>>> au = a("worldgen/configured_feature");	
	private IRegistry<WorldGenFeatureConfigured<?, ?>> worldgenfeature_registry;
	//public static final ResourceKey<IRegistry<StructureFeature<?, ?>>> av = a("worldgen/configured_structure_feature");
	private IRegistry<StructureFeature<?, ?>> structurefeature_registry;
	
	protected DataPackResolver() {
	
		try {
			biomebase_registry = GlobalValues.BiomeBase_Registry;
			worldgenfeature_registry = GlobalValues.CustomConsoleRegistry.a(IRegistry.aK).get();
			structurefeature_registry = GlobalValues.CustomConsoleRegistry.a(IRegistry.aL).get();
		//	structuregenerator_registry = console.customRegistry.a(IRegistry.aF).get();
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		// TODO: Add more registry 
	}

	
	public StructureFeature<?,?> getStructureFeature(String prekey, String featurename){
		StructureFeature<?, ?> struct = structurefeature_registry.getOptional(new MinecraftKey(prekey, featurename)).orElseGet(()->null);
		
		if(struct != null) {
			return struct;
		}
		
		return null;
	}
	public List<MinecraftKey> getListedStructureFeatures(){
		return getList(structurefeature_registry);
	} 
	
	
	
	public WorldGenFeatureConfigured<?, ?> getWorldGenFeature(String prekey, String featurename){
		WorldGenFeatureConfigured<?, ?> feature = worldgenfeature_registry.getOptional(new MinecraftKey(prekey, featurename)).orElseGet(()->null);
		if(feature != null) {
			return feature;
		}
		return null;
	}
	public List<MinecraftKey> getListedWorldGenFeature(){
		return getList(worldgenfeature_registry);
	}
	
	
	public RegisteredCustomBiome getBiome(String prekey, String biomename) {
		BiomeBase biomebase = biomebase_registry.getOptional(new MinecraftKey(prekey, biomename)).orElseGet(()->null);
		
		if(biomebase != null) {
			
			ResourceKey<BiomeBase> rk = biomebase_registry.c(biomebase).orElseGet(()->null);
			int biomeid = biomebase_registry.getId(biomebase);
			
			if(rk != null && biomeid != -1) {
				return new RegisteredCustomBiome(rk, null, biomeid);
			}
		}

		return null;
	}
	public List<MinecraftKey> getListedBiomes(){
		return getList(biomebase_registry);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private <T> List<MinecraftKey> getList(IRegistry<?> registry){
		return Lists.newArrayList(registry.keySet());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
