package de.freesoccerhdx.advancedworldcreator.main;

import java.awt.Color;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;

import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenDecoration;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenFeature;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeBase.Geography;
import net.minecraft.world.level.biome.BiomeBase.Precipitation;
import net.minecraft.world.level.biome.BiomeBase.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeFog.GrassColor;
import net.minecraft.world.level.biome.BiomeParticles;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.BiomeSettingsMobs.c;
import net.minecraft.world.level.biome.CaveSound;
import net.minecraft.world.level.biome.CaveSoundSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenStage.Decoration;
import net.minecraft.world.level.levelgen.WorldGenStage.Features;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;

/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeBase.Geography;
import net.minecraft.server.v1_16_R3.BiomeBase.Precipitation;
import net.minecraft.server.v1_16_R3.BiomeBase.TemperatureModifier;
import net.minecraft.server.v1_16_R3.BiomeFog;
import net.minecraft.server.v1_16_R3.BiomeFog.GrassColor;
import net.minecraft.server.v1_16_R3.BiomeParticles;
import net.minecraft.server.v1_16_R3.BiomeSettingsGeneration;
import net.minecraft.server.v1_16_R3.BiomeSettingsMobs;
import net.minecraft.server.v1_16_R3.BiomeSettingsMobs.c;
import net.minecraft.server.v1_16_R3.CaveSound;
import net.minecraft.server.v1_16_R3.CaveSoundSettings;
import net.minecraft.server.v1_16_R3.EnumCreatureType;
import net.minecraft.server.v1_16_R3.Music;
import net.minecraft.server.v1_16_R3.ParticleParam;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.StructureFeature;
import net.minecraft.server.v1_16_R3.StructureGenerator;
import net.minecraft.server.v1_16_R3.WeightedRandom;
import net.minecraft.server.v1_16_R3.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import net.minecraft.server.v1_16_R3.WorldGenStage.Decoration;
import net.minecraft.server.v1_16_R3.WorldGenStage.Features;
*/

public class BiomeWriter {

	
	/*
	
	//villages, endcity, dungeons
	COPY_BIOMESTRUCTURES			->		private final Map<Integer, List<StructureGenerator<?>>> g;
	
	//"precipitation" "temperature" "temperature_modifier" "downfall"
	COPY_CLIMATE					->		private final d j;  
	
	//flowers, ores, trees
	COPY_DECORATIONFEATURES			->		private final BiomeSettingsGeneration k;  
	
	//mobs
	COPY_MOBSETTINGS				->		private final BiomeSettingsMobs l;  
	
	//how deep
	COPY_DEPTH						->		private final float m;  
	
	// how large
	COPY_SCALE						->		private final float n;  
	
	// Biome-Geography-Categorie
	COPY_GEOGRAPHY					->		private final Geography o;
	
	// Colors
	COPY_BIOMEFOG					->		private final BiomeFog p;
	 
	 
	 * */
	private static boolean overwrite(String fieldname, Object source, Object target) {
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
	
	
	protected static boolean overwriteVanillaBiome(ResourceKey<BiomeBase> targetbiome, CustomBiome sourcebiome, CloneOptions options) {
		
		try {
			BiomeBase oldbb = AdvancedWorldCreatorAPI.getBiomeBase(targetbiome);
			BiomeBase newbb = AdvancedWorldCreatorAPI.createBiomeBase(sourcebiome);
			
			boolean success = true;
			
			if(options.COPY_BIOMESTRUCTURES) { // g
				// var 'g' is useless, BiomeStructures are in: BiomeSettingsGeneration bsg = vanillabiome.e();
				//boolean b = overwrite("g", newbb, oldbb);
				//success = success ? b : success;
				
				BiomeSettingsGeneration bsg_new = newbb.e();
				
				BiomeSettingsGeneration bsg_old = oldbb.e();
				
				// var g in: BiomeSettingsGeneration
				boolean b = overwrite("g", bsg_new, bsg_old);
				success = success ? b : success;
				
			}

			if(options.COPY_DECORATIONFEATURES) { // k
				boolean b = overwrite("k", newbb, oldbb);
				success = success ? b : success;
			}

			if(options.COPY_MOBSETTINGS) { // l
				boolean b = overwrite("l", newbb, oldbb);
				success = success ? b : success;
			}
			
			if(options.COPY_CLIMATE) { // j
				boolean b = overwrite("j", newbb, oldbb);
				success = success ? b : success;
			}
	
			if(options.COPY_BIOMEFOG) { // p
				boolean b = overwrite("p", newbb, oldbb);
				success = success ? b : success;
			}
			
			if(options.COPY_GEOGRAPHY) { // o
				boolean b = overwrite("o", newbb, oldbb);
				success = success ? b : success;			
			}
			
			if(options.COPY_SCALE) { // n
				boolean b = overwrite("n", newbb, oldbb);
				success = success ? b : success;
			}
			
			if(options.COPY_DEPTH) { // m
				boolean b = overwrite("m", newbb, oldbb);
				success = success ? b : success;
			}
			
			
			
			return success;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
		BiomeFog bf;
 		bf.a(cb.getFogColor());
		bf.b(cb.getWaterColor());
		bf.c(cb.getWaterFogColor());
		bf.d(cb.getSkyColor());
		bf.e(cb.getFoliageColor());
		bf.f(cb.getGrassColor());
		bf.a(BiomeFog.GrassColor.NONE);
		
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
 
 
	*/
	
	private static Color hexToColor(int i) {
		
		org.bukkit.Color col = org.bukkit.Color.fromRGB(i);
		
		return new Color(col.getRed(),col.getGreen(),col.getBlue());
		
	}
	
	private static Field getPrivateFinalDeclaredField(Object target, String name) {
		try {
			Field pfield = target.getClass().getDeclaredField(name);
			pfield.setAccessible(true);
			FieldUtils.removeFinalModifier(pfield);
			
			return pfield;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected static boolean copyVanillaBiome(ResourceKey<BiomeBase> sourcebiome, CustomBiome targetbiome, CloneOptions options) {
		
		try {
			BiomeBase vanillabiome = AdvancedWorldCreatorAPI.getBiomeBase(sourcebiome);
			
			boolean success = true;
			
			if(options.COPY_BIOMESTRUCTURES) { // g
				// JUST A LIST OF ALL STRUCUTRES;
				//Map<Integer, List<StructureGenerator<?>>> g = (Map<Integer, List<StructureGenerator<?>>>) FieldUtils.readField(getPrivateFinalDeclaredField(vanillabiome,"g"), vanillabiome);
				//Bukkit.broadcastMessage("Data: " + g);
				// REAL DATA HERE: BiomeSettingsGeneration
				BiomeSettingsGeneration bsg = vanillabiome.e();
				Collection<Supplier<StructureFeature<?, ?>>> structurefeature = bsg.a();
				
				for(Supplier<StructureFeature<?, ?>> ssf : structurefeature) {
					targetbiome.addCustomWorldStructureFeature(ssf.get());
				}
				
			}

			if(options.COPY_DECORATIONFEATURES) { // k
				BiomeSettingsGeneration bsg = vanillabiome.e();

				
				// private final Supplier<WorldGenSurfaceComposite<?>> d;
				targetbiome.setSurfaceBuilder(bsg.d().get());
				
				// private final Map<WorldGenStage.Features, List<Supplier<WorldGenCarverWrapper<?>>>> e;
				Map<WorldGenStage.Features, List<Supplier<WorldGenCarverWrapper<?>>>> e = (Map<Features, List<Supplier<WorldGenCarverWrapper<?>>>>) FieldUtils.readField(getPrivateFinalDeclaredField(bsg,"e"), bsg);
				
				for(WorldGenStage.Features feat : e.keySet()) {
					for(Supplier<WorldGenCarverWrapper<?>> wgcw : e.get(feat)) {
						WorldGenCarverWrapper<?> w = (WorldGenCarverWrapper<?>) wgcw.get();
						targetbiome.addWorldGenFeature(WorldGenFeature.fromMCFeature(feat), w);
					}
				}
				
				// private final List<List<Supplier<WorldGenFeatureConfigured<?, ?>>>> f;
				List<List<Supplier<WorldGenFeatureConfigured<?, ?>>>> f = bsg.c();
				
				int i = 0;
				for(List<Supplier<WorldGenFeatureConfigured<?, ?>>> f1 : f) {
					
					Decoration deco = null;
					for(Decoration d : Decoration.values()) {
						deco = d.ordinal() == i ? d : deco;
					}
					if(deco == null) {
						System.err.println("[AdvancedWorldCreatorAPI] Couldn't find any Value for Decoration while copy Vanilla Biome");
					}else {
						for(Supplier<WorldGenFeatureConfigured<?, ?>> f2 : f1) {
							targetbiome.addWorldGenDecorationFeature(WorldGenDecoration.fromMCDecoration(deco), f2.get());
						}
					}
					
					
					
					i++;
				}
				
				// Same as List<List<Supplier<WorldGenFeatureConfigured<?, ?>>>> f; but without Decoration-Element
				// private final List<WorldGenFeatureConfigured<?, ?>> h;
				//List<WorldGenFeatureConfigured<?, ?>> h = bsg.b(); 
				
			}

			if(options.COPY_MOBSETTINGS) { // l
				BiomeSettingsMobs bsm = vanillabiome.b();
				float prohability = bsm.a();
				targetbiome.setMobSpawnProbability(prohability);
				
				for(EnumCreatureType type : EnumCreatureType.values()) {
					List<c> list = bsm.a(type).d();
					for(c mobdata : list) {
						//mobdata.
						
						//WeightedEntry.a wrc = mobdata;
						//Field field = wrc.getClass().getField("a");
						
						
						
						//Field f = wrc.getClass().getSuperclass().getDeclaredField("a");
						//f.setAccessible(true);
						//FieldUtils.removeFinalModifier(f);
						int weight = mobdata.a().a();//(int) FieldUtils.readField(f, wrc);
						targetbiome.addMobToBiome(mobdata.b, weight, mobdata.c, mobdata.d);
					}
				}
				
			}
			
			if(options.COPY_CLIMATE) { // j
				Object climate = FieldUtils.readField(getPrivateFinalDeclaredField(vanillabiome,"j"), vanillabiome);
				
			    //private final BiomeBase.Precipitation b;
				BiomeBase.Precipitation b = (Precipitation) FieldUtils.readField(getPrivateFinalDeclaredField(climate,"b"), climate);
			    targetbiome.setPrecipitation(b);
			    
			    //private final float c;
			    float c = (float) FieldUtils.readField(getPrivateFinalDeclaredField(climate,"c"), climate);
			    targetbiome.setTemperature(c);
			    
			    //private final BiomeBase.TemperatureModifier d;
			    BiomeBase.TemperatureModifier d = (TemperatureModifier) FieldUtils.readField(getPrivateFinalDeclaredField(climate,"d"), climate);
			    targetbiome.setTemperatureModifier(d);
			    
			    //private final float e;
			    float e = (float) FieldUtils.readField(getPrivateFinalDeclaredField(climate,"e"), climate);
			    targetbiome.setDownfall(e);
			    
			    
			}
	
			if(options.COPY_BIOMEFOG) { // p
				
				Object p = FieldUtils.readField(getPrivateFinalDeclaredField(vanillabiome,"p"), vanillabiome); //BiomeFog Values=[b...m]
				

				// b -> a 						-> setFogColor 
				Object b = FieldUtils.readField(getPrivateFinalDeclaredField(p,"b"), p);
				targetbiome.setFogColor(hexToColor((int) b)); 
				
				// c -> b						-> setWaterColor
				Object c = FieldUtils.readField(getPrivateFinalDeclaredField(p,"c"), p);
				targetbiome.setWaterColor(hexToColor((int) c)); 
				
				// d -> c						-> setWaterFogColor
				Object d = FieldUtils.readField(getPrivateFinalDeclaredField(p,"d"), p);
				targetbiome.setWaterFogColor(hexToColor((int) d)); 
				
				// e -> d						-> setSkyColor
				Object e = FieldUtils.readField(getPrivateFinalDeclaredField(p,"e"), p);
				targetbiome.setSkyColor(hexToColor((int) e)); 
				
				// f -> e						-> setFoliageColor
				Optional<Integer> f = (Optional<Integer>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"f"), p);
				if(f.isPresent()) {
					targetbiome.setFoliageColor(hexToColor(f.get()));
				}
				
				// g -> f						-> setGrassColor
				Optional<Integer> g = (Optional<Integer>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"g"), p);
				if(g.isPresent()) {
					targetbiome.setGrassColor(hexToColor(g.get()));
				}
				
				// h -> a#2 GrassColor(Modi)	-> /*not available*/
				GrassColor h = (GrassColor) FieldUtils.readField(getPrivateFinalDeclaredField(p,"h"), p);
				targetbiome.setGrassModifier(h);
				
				
				// i -> a#3 BiomeParticles		-> targetbiome.setBiomeParticles(particletype, quantity);
				Optional<BiomeParticles> i = (Optional<BiomeParticles>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"i"), p);
				if(i.isPresent()) {
					ParticleParam _b = (ParticleParam) FieldUtils.readField(i.get(), "b");
					float _c = (float) FieldUtils.readField(getPrivateFinalDeclaredField(i.get(),"b"), i.get());
					
					targetbiome.setBiomeParticles(_b, _c);
				}
				
				
				// j -> a#4 SoundEffect			-> setAmbientSound
				Optional<SoundEffect> j = (Optional<SoundEffect>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"j"), p);
				if(j.isPresent()) {
					targetbiome.setAmbientSound(j.get());
				}
				
				// k -> a#5 CaveSoundSettings	-> setCaveSoundSettings
				Optional<CaveSoundSettings> k = (Optional<CaveSoundSettings>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"k"), p);
				if(k.isPresent()) {
					CaveSoundSettings css = k.get();
					SoundEffect _c = (SoundEffect) FieldUtils.readField(getPrivateFinalDeclaredField(css,"c"), css);
					int _d = (int) FieldUtils.readField(getPrivateFinalDeclaredField(css,"d"), css);
					int _e = (int) FieldUtils.readField(getPrivateFinalDeclaredField(css,"e"), css);
					double _f = (double) FieldUtils.readField(getPrivateFinalDeclaredField(css,"f"), css);
					targetbiome.setCaveSoundSettings(_c, _d, _e, _f);
				}
				
				
				// l -> a#6 CaveSound			-> setCaveSound
				Optional<CaveSound> l = (Optional<CaveSound>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"l"), p);
				if(l.isPresent()) {
					CaveSound cs = l.get();
					SoundEffect _b = (SoundEffect) FieldUtils.readField(getPrivateFinalDeclaredField(cs,"b"), cs);
					double _c = (double) FieldUtils.readField(getPrivateFinalDeclaredField(cs,"c"), cs);
					targetbiome.setCaveSound(_b, _c);
				}
				
				// m -> a#7 Music				-> setMusic
				Optional<Music> m = (Optional<Music>) FieldUtils.readField(getPrivateFinalDeclaredField(p,"m"), p);
				if(m.isPresent()) {
					Music cs = m.get();
					SoundEffect _b = (SoundEffect) FieldUtils.readField(getPrivateFinalDeclaredField(cs,"b"), cs);
					targetbiome.setMusic(_b);
				}
				
			}
			
			if(options.COPY_GEOGRAPHY) { // o
				Geography o = vanillabiome.t();
				targetbiome.setGeography(o);
			}
			
			if(options.COPY_SCALE) { // m
				float scale = vanillabiome.h();
				targetbiome.setScale(scale);
			}
			
			if(options.COPY_DEPTH) { // n
				float depth = vanillabiome.j();
				targetbiome.setDepth(depth);
			}
			
			return success;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}








