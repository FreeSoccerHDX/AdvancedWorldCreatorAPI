package de.freesoccerhdx.advancedworldcreator.main;

import org.bukkit.event.Listener;

public class TestCommands implements Listener {
/*
	public static <FC extends WorldGenFeatureConfiguration> WorldGenFeatureConfigured<FC, ?> a(String var0, WorldGenFeatureConfigured<FC, ?> var1) {
		return (WorldGenFeatureConfigured<FC, ?>)IRegistry.<WorldGenFeatureConfigured<?, ?>>a(RegistryGeneration.e, var0, var1);
	}
	
	@EventHandler
	public <T> void onCmd(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().split(" ");
		String cmd = args[0];
		Player p = e.getPlayer();
		
		if(cmd.equalsIgnoreCase("/testworld")) {
			World w = p.getWorld();
			CraftWorld cw = (CraftWorld) w;
			WorldServer ws = cw.getHandle();
			net.minecraft.server.v1_16_R3.World nmsw = ws;
			
			Server server = Bukkit.getServer();
			CraftServer cs = (CraftServer) server;
			DedicatedServer console = cs.getServer();
			
			//Field f0 = FieldUtils.getDeclaredField(net.minecraft.server.v1_16_R3.WorldServer.class, "chunkProvider", true);
			
			try {
				ChunkProviderServer chunkProvider = (ChunkProviderServer) FieldUtils.readDeclaredField(ws, "chunkProvider", true);
				Bukkit.broadcastMessage(chunkProvider.chunkGenerator+"");
				
				
				
				AdvancedCreator ac = new AdvancedCreator("world",w.getSeed());
				ac.setGenerateSurface(false);
				
				OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed()).create();
				
				
				LinesBiomeGenerator lbg = new LinesBiomeGenerator.CREATOR(5, true)
						.addBiome(Biomes.PLAINS)
						.addBiome(Biomes.DESERT)
						.addBiome(Biomes.OCEAN)
						.addBiome(Biomes.BEACH)
						.create();
				
				
				old_DummyChunkGeneratorAbstract dcga = AdvancedWorldCreatorAPI.createChunkGenerator(ac, lbg, console.customRegistry.b(IRegistry.ay), console.customRegistry.b(IRegistry.ar));
				
				
				Field f = FieldUtils.getDeclaredField(ChunkProviderServer.class, "chunkGenerator", true);
				f.setAccessible(true);
				FieldUtils.removeFinalModifier(f);
				
				
				
				try {
					FieldUtils.writeField(f, chunkProvider, dcga);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				AdvancedWorldCreatorAPI.registerWorldDimension("world", dcga, console.customRegistry.b(IRegistry.K));
				Bukkit.broadcastMessage(chunkProvider.chunkGenerator+"");
				
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			
			
			Bukkit.broadcastMessage(nmsw.generator+"");
			
		//	nmsw.generator = null;
			
			CustomDimensionSettings cds = new CustomDimensionSettings(OptionalLong.empty(), true, true, true, false, 200.0, 
					false, true, true, true, true, 150, TagsBlock.aG.a(), new MinecraftKey("minecraft","overworld"), (float) 0.5);
			
			
			//ws.x = cds;
			
			for(Field f : net.minecraft.server.v1_16_R3.World.class.getDeclaredFields()) {
		//		Bukkit.broadcastMessage(f.getName());
			}
			
			
			Field f = FieldUtils.getDeclaredField(net.minecraft.server.v1_16_R3.World.class, "x", true);
			f.setAccessible(true);
			FieldUtils.removeFinalModifier(f);
			
			
			
			try {
				FieldUtils.writeField(f, nmsw, cds);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Bukkit.broadcastMessage("lol ? ");
			
			
		}
		
		if(cmd.equalsIgnoreCase("/unloadmoon")) {
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(AdvancedWorldCreatorAPI.main, ()->Bukkit.unloadWorld("moon", false), 20*5);
			
			e.setCancelled(true);
			p.sendMessage("done");
		}
		if(cmd.equalsIgnoreCase("/moon")) {
			
			AdvancedCreator advancedCreator = new AdvancedCreator("moon");
			 
	        CustomBiome moonBiome = new CustomBiome("tapl_moon", "moon");
	 
	        moonBiome.setTemperature(0);
	        moonBiome.setGrassColor(new Color(0,255,255));
	        moonBiome.setFoliageColor(new Color(60, 100, 180));
	        moonBiome.setWaterColor(new Color(255,255,255));
	        moonBiome.setFogColor(new Color(255, 200, 20));
	        moonBiome.setSkyColor(new Color(255, 200, 20));
	        moonBiome.setBiomeParticles(Particles.ASH, 0.2f);
	 
	        moonBiome.addWorldGenFeature(WorldGenStage.Features.AIR, WorldGenCarver.CAVE);
	 
	        moonBiome.addBiomeStructure(BiomeStructure.VILLAGE_DESERT);
	        moonBiome.addBiomeStructure(BiomeStructure.STRONGHOLD);
	        moonBiome.addBiomeStructure(BiomeStructure.MINESHAFT);
	        moonBiome.addBiomeStructure(BiomeStructure.RUINED_PORTAL);
	 
	        advancedCreator.loadDefaultStructureGeneratorConfig();
	 
	        moonBiome.setGeography(BiomeBase.Geography.DESERT);
	        moonBiome.setCustomSurfaceBuilder(SurfaceType.DEFAULT, Blocks.END_STONE, Blocks.STONE, Blocks.STONE);
	        moonBiome.setPrecipitation(BiomeBase.Precipitation.SNOW);
	        //var0.a(WorldGenStage.Decoration.TOP_LAYER_MODIFICATION, BiomeDecoratorGroups.FREEZE_TOP_LAYER);
	        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.TOP_LAYER_MODIFICATION, BiomeDecoratorGroups.FREEZE_TOP_LAYER);
	 
	        RegisteredCustomBiome registeredMoonBiome = AdvancedWorldCreatorAPI.registerCustomBiome(moonBiome, false);
	 
	        OverworldBiomeGenerator.CREATOR obgCreator = new OverworldBiomeGenerator.CREATOR(advancedCreator.getSeed());
	 
	        
	        Field[] fields = Biomes.class.getDeclaredFields();
	        for (Field f : fields) {
	            if (Modifier.isStatic(f.getModifiers()) && f.getType().equals(Biomes.PLAINS.getClass())) {
	                try {
	                    if (f.get(null).equals(Biomes.DESERT)) {
	                        continue;
	                    }
	                    obgCreator.overwriteBiome((ResourceKey<BiomeBase>) f.get(null), registeredMoonBiome);
	                } catch (IllegalAccessException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
	 
	        //xz_scale, y_scale, xz_factor, y_factor
	        NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(0.65, 0.14, 200, 170);
	        // target, size, offset
	        // was -10, 3, 0 // -30 0 0
	        NoiseSlideSettings top_slide = new NoiseSlideSettings(-49, 25, 0);
	        NoiseSlideSettings bottom_slide = new NoiseSlideSettings(-30, 0, 0);
	        int size_horizontal = 1; // was 1
	        int size_vertical = 2; // was 2
	        int world_height = 256;
	        double density_factor = 1.25;
	        double density_offset = -0.65;
	        boolean simplex_surface_noise = true;
	        boolean random_density_offset = true;
	        boolean island_noise_override = false;
	        boolean amplified = false;
	 
	        NoiseSettings noisesettings = new NoiseSettings(world_height, noisesampling, top_slide, bottom_slide, size_horizontal, size_vertical,
	                density_factor, density_offset, simplex_surface_noise, random_density_offset, island_noise_override, amplified);
	 
	        Block solid_material = Blocks.STONE;
	        Block fluid_material = Blocks.WATER;
	        int bedrock_roof_position = -10;
	        int bedrock_floor_position = 0;
	        int sea_level = 30;
	        boolean disable_mob_generation = false;
	 
	        advancedCreator.setGenerateStructures(true);
	        advancedCreator.setCustomWorldSetting(noisesettings, solid_material, fluid_material, bedrock_roof_position, bedrock_floor_position, sea_level, disable_mob_generation);
	 
	        World world = AdvancedWorldCreatorAPI.createWorld(advancedCreator, CustomDimensionSettings.getOverworldSettings(), obgCreator.create());
	        
	        p.teleport(world.getSpawnLocation());
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/reflect")) {
			try {
				
				 Method target_me = NoiseGeneratorNormal.class.getMethods()[1];
				 Bukkit.broadcastMessage(target_me.getName());
				
				Class target_a = null;
				
				for(Class cl : WorldChunkManagerMultiNoise.class.getDeclaredClasses()) {
					
					if(cl.getName().endsWith("WorldChunkManagerMultiNoise$a")) {
						target_a = cl;
						break;
					}
				}
				
				
				
				
				
				Bukkit.broadcastMessage("claname: " + target_a.getName());
				
				for(Method me : target_a.getMethods()) {
					Bukkit.broadcastMessage("mename: " + me.getName());
				}
			
				Method target_method = target_a.getMethod("b", null);
				
				Bukkit.broadcastMessage(target_method.getReturnType().getName());
				
				
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
			
			
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/unloadtutorial")) {
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(AdvancedWorldCreatorAPI.main, ()->Bukkit.unloadWorld("myWorld", false), 20*5);
			
			e.setCancelled(true);
			p.sendMessage("done");
		}
		
		
		if(cmd.equalsIgnoreCase("/testcolor")) {
			String s = Long.toHexString(AdvancedWorldCreatorAPI.colorToHexaDecimal(new Color(255,120,164)));
			Bukkit.broadcastMessage("s: " + s);
			
			
			
			Bukkit.broadcastMessage("sf_: " + org.bukkit.Color.fromRGB(AdvancedWorldCreatorAPI.colorToHexaDecimal(new Color(255,120,164))));
			
			
			
			Bukkit.broadcastMessage("s_: " + Integer.parseInt(s, 16));

			
				
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testbiomewriter")) {
			CustomBiome cb = new CustomBiome("mc", "plains");
			BiomeWriter.copyVanillaBiome(Biomes.PLAINS, cb, new CloneOptions().COPY_ALL(true));
		//	cb.setGrassColor(new Color(255,2,255));
			RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb,true);

			AdvancedCreator ac = new AdvancedCreator("myWorld");
			
			OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed())
					.overwriteBiome(Biomes.PLAINS, rcb)
					.create();
			

			
			DimensionManager dm = CustomDimensionSettings.getOverworldSettings();
			
			World w = AdvancedWorldCreatorAPI.createWorld(ac, dm, obg);
			
			p.teleport(w.getSpawnLocation());
			
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testdecofeature")) {
			CustomBiome cb = new CustomBiome("", "");
			Bukkit.broadcastMessage("features: " + cb.getWorldGenDecorationFeatures().size());
			Bukkit.broadcastMessage("mobs: " + cb.getBiomeMobs().size());			
			BiomeWriter.copyVanillaBiome(Biomes.PLAINS, cb, new CloneOptions().COPY_ALL(true));
			Bukkit.broadcastMessage("features2: " + cb.getWorldGenDecorationFeatures().size());
			Bukkit.broadcastMessage("mobs2: " + cb.getBiomeMobs().size());
			
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testbiomefog")) {
			CustomBiome cb = new CustomBiome("", "");
			cb.setGrassColor(new Color(255,255,255));
			Bukkit.broadcastMessage("GrassColor: " + cb.getGrassColor());
			
			
			BiomeWriter.copyVanillaBiome(Biomes.PLAINS, cb, new CloneOptions().COPY_ALL(true).COPY_MOBSETTINGS(false));
			
			Bukkit.broadcastMessage("2GrassColor: " + cb.getGrassColor());
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testbiomewriter")) {
			CloneOptions co = new CloneOptions().COPY_BIOMEFOG(true);
			
			CustomBiome cb = new CustomBiome("", "");
			cb.setGrassColor(new Color(255,0,255));
			
			BiomeWriter.overwriteVanillaBiome(Biomes.PLAINS, cb, co);
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testtutorial")) {
			
			
			CustomBiome cb = new CustomBiome("not_minecraft", "not_plains");
			cb.setGrassColor(new Color(0,255,255));
			cb.setFoliageColor(new Color(0,0,255));
			cb.setWaterColor(new Color(255,0,0));
		
			
			cb.addMobToBiome(EntityType.PIG, 100, 1, 50);
			cb.addMobToBiome(EntityType.COW, 100, 1, 50);
			cb.addMobToBiome(EntityType.SHEEP, 100, 1, 50);
			cb.addMobToBiome(EntityType.CHICKEN, 100, 1, 50);
			
			cb.addMobToBiome(EntityType.WITHER_SKELETON, 100, 1, 10);
			cb.addMobToBiome(EntityType.SHULKER, 100, 1, 50);
			cb.addBiomeStructure(BiomeStructure.VILLAGE_DESERT);
			
		//
			cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FLOWER_FOREST);
		//	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FOREST_FLOWER_TREES);
			
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_COAL);
			for(int i = 0; i < 100; i++) {
				cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
			}

			cb.addWorldGenFeature(Features.AIR, WorldGenCarver.CAVE);
			
			cb.addBiomeStructure(BiomeStructure.VILLAGE_SNOWY);
			
			RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb,true);
			
			AdvancedCreator ac = new AdvancedCreator("myWorld");
			ac.loadDefaultStructureGeneratorConfig();
			//ac.setGenerateDecoration(false); // flowers, trees
			//ac.setGenerateStructures(false); // villages, dungeons ...
	//		for(Decoration deco : Decoration.values()) {
//				ac.setDecorationBlacklisted(deco, true);
	//		}
			
			ac.setDecorationBlacklisted(Decoration.VEGETAL_DECORATION, true);
			ac.setAirCavesDisabled(true);
			ac.setLiquidCavesDisabled(true);
			ac.setGenerateSurface(false);
			
			OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed())
					.create();
			
			DimensionManager dm = CustomDimensionSettings.getOverworldSettings();
			
			World w = AdvancedWorldCreatorAPI.createWorld(ac, dm, obg);
			
			p.teleport(w.getSpawnLocation());
			
			
			e.setCancelled(true);
		}
		
		
		if(cmd.equalsIgnoreCase("/testdatapacklist")) {
			
			for(MinecraftKey key : AdvancedWorldCreatorAPI.getDataPackResolver().getListedBiomes()) {
				Bukkit.broadcastMessage("BiomeKeys: " +key);
			}
			
			for(MinecraftKey key : AdvancedWorldCreatorAPI.getDataPackResolver().getListedWorldGenFeature()) {
				Bukkit.broadcastMessage("GenFeatures: " +key);
			}
			
			for(MinecraftKey key : AdvancedWorldCreatorAPI.getDataPackResolver().getListedStructureFeatures()) {
				Bukkit.broadcastMessage("StructFeatures: " +key);
			}
			
			
			
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testcodec")) {
			try {
				
			
			Constructor<GeneratorSettingBase> constructor = (Constructor<GeneratorSettingBase>) GeneratorSettingBase.class.getDeclaredConstructors()[0];
	        constructor.setAccessible(true);
	        
	        GeneratorSettingBase foo = constructor.newInstance(null,null,Blocks.STONE.getBlockData(),Blocks.WATER.getBlockData(),10,10,10,true);
	        System.out.println(foo);
	        
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	        e.setCancelled(true);
		}
		if(cmd.equalsIgnoreCase("/testregistbiome")) {
			CustomBiome cb = new CustomBiome("wonderland");
			
			RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb, true);
			
			p.sendMessage("BiomeCustomNameKey: "+rcb.getBiome().a());
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/dimtp")) {
			p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
			e.setCancelled(true);
		}
		
		
		
		if(cmd.equalsIgnoreCase("/createcustom")) {
			
			
			Block toplace = Blocks.MELON;
			WorldGenFeatureConfigured<?, ?> PATCH_MELON = BiomeDecoratorGroups.PATCH_MELON; a("patch_melon", WorldGenerator.RANDOM_PATCH.b((
					new WorldGenFeatureRandomPatchConfiguration.a(
							new WorldGenFeatureStateProviderSimpl(toplace.getBlockData()), 
							WorldGenBlockPlacerSimple.c)
					)
			        
			        .a(128) // tries
			    //    .b(3) //x spread
			    //    .c(1) //y spread
			    //    .d(3) //z spread
			        .a(ImmutableSet.of(Blocks.GRASS))
			        .a()
			        .b()
			        .d()).a(BiomeDecoratorGroups.b.m));
			
			IBlockData ibd = Blocks.WHEAT.getBlockData();
			
			
			PATCH_MELON = a("patch_melon_custom", WorldGenerator.RANDOM_PATCH.b((new WorldGenFeatureRandomPatchConfiguration.a(
					new WorldGenFeatureStateProviderSimpl(ibd), WorldGenBlockPlacerSimple.c))
			        
			        .a(128)
			        .a(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlockData().getBlock()))
			        .a()
			        .b()
			        .d()).a(BiomeDecoratorGroups.b.m));
			
			
			List<WorldGenFeatureRandomChoiceConfigurationWeight> ffff = new ArrayList<>();
			ffff.add(PATCH_MELON.a(1.0f));
			
			WorldGenFeatureConfigured<?, ?> feature = a("custompatch", 
					WorldGenerator.RANDOM_SELECTOR.b(
						new WorldGenFeatureRandomChoiceConfiguration(ffff, PATCH_MELON)
					).a(BiomeDecoratorGroups.b.l)
					 .a(WorldGenDecorator.f.b(new WorldGenDecoratorFrequencyExtraChanceConfiguration(1, 0.5f, 1)))
					);
			
			/* plains biome single tree chance */
			/*			
			List<WorldGenFeatureRandomChoiceConfigurationWeight> features = new ArrayList<>();
			features.add(BiomeDecoratorGroups.FANCY_OAK_BEES_005.a(0.3F));
			//features.add(BiomeDecoratorGroups.OAK_BEES_005.a(0.3F));
			
			WorldGenFeatureConfigured<?, ?> PLAIN_VEGETATION = a("plain_vegetation", WorldGenerator.RANDOM_SELECTOR.b(
					new WorldGenFeatureRandomChoiceConfiguration(features, BiomeDecoratorGroups.OAK_BEES_005))
      
      .a(BiomeDecoratorGroups.b.l).a(WorldGenDecorator.f.b(new WorldGenDecoratorFrequencyExtraChanceConfiguration(1, 0.99F, 10))));
																										// default_count, count_extra, extra_count
			 
			* /
			
			CustomBiome cb = new CustomBiome("wonderland");
			cb.setGrassColor(new Color(255,0,0));
			cb.setScale(0.02f);
			cb.setMusic(SoundEffects.MUSIC_CREDITS);
			cb.setWaterColor(new Color(0,0,0));
			cb.setBiomeParticles(Particles.ASH, 0.01f);
			//cb.addMobToBiome(EntityType.WITCH, EnumCreatureType.MONSTER, 50, 4, 4);
			cb.addMobToBiome(EntityType.PIG, 50, 4, 4);
			
			for(EntityType et : EntityType.values()) {
				try {
					cb.addMobToBiome(et, 50, 4, 4);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			for(MinecraftKey key : AdvancedWorldCreatorAPI.getDataPackResolver().getListedWorldGenFeature()) {
			//	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, getDataPackFeatureResolver().getDatpackGenFeature(key.getNamespace(), key.getKey()));
			}
			
			for(MinecraftKey key : AdvancedWorldCreatorAPI.getDataPackResolver().getListedStructureFeatures()) {
				for(int i = 0; i < 5; i++) {
					//cb.addCustomWorldStructureFeature(getDataPackFeatureResolver().getDatapackStructureFeature(key.getNamespace(), key.getKey()));
				}
			}
			
			cb.addCustomWorldStructureFeature(AdvancedWorldCreatorAPI.getDataPackResolver().getStructureFeature("mmb", "pillager_settlement"));
			
			
			
			cb.setSurfaceBuilder(SurfaceBuilder.GRASS);
			cb.setCustomSurfaceBuilder(SurfaceType.DEFAULT, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.DIAMOND_ORE);
			
			cb.addWorldGenFeature(Features.LIQUID, WorldGenCarver.OCEAN_CAVE);
			cb.addWorldGenFeature(Features.LIQUID, WorldGenCarver.UNDERWATER_CAVE);
			cb.addWorldGenFeature(Features.LIQUID, WorldGenCarver.UNDERWATER_CANYON);
			
			cb.addWorldGenDecorationFeature(Decoration.SURFACE_STRUCTURES, BiomeDecoratorGroups.LAKE_WATER);
			
			CustomSpruceTreeStructure cos1 = new CustomSpruceTreeStructure("tttttt");
			cos1.setWoodHeight(50);
			
			CustomForest cf = new CustomForest("customforest", cos1, 0, 0.05, 20);
			
			
			cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, AdvancedWorldCreatorAPI.getDPR().getWorldGenFeature("mmb","sequoia_forest/sequoia_supertall"));
			
			
			
		//	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, cf);
			
			CustomOreStructure cos = new CustomOreStructure("testore", 0, 0, 40, 5, 6, Blocks.DIAMOND_BLOCK, Blocks.STONE);
			cos.setVanillaReplaceRule(StructureRule.NATURAL_STONE);
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, cos);
			
			
			
			cb.addWorldGenDecorationFeature(Decoration.SURFACE_STRUCTURES, feature);
	//		cb.addWorldGenDecorationFeature(Decoration.SURFACE_STRUCTURES, BiomeDecoratorGroups.BONUS_CHEST);
			cb.addWorldGenDecorationFeature(Decoration.SURFACE_STRUCTURES, BiomeDecoratorGroups.BAMBOO_LIGHT);
			//cb.addWorldGenFeature(Features.AIR, WorldGenFeature.CANYON);
			cb.addWorldGenFeature(Features.AIR, WorldGenCarver.NETHER_CAVE);
			
			cb.addBiomeStructure(BiomeStructure.MINESHAFT_MESA);

			
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIORITE);
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIRT);
			cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_ANDESITE);
			RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb, true);
	
			
			
			long seed = 123456789;
			AdvancedCreator ac = new AdvancedCreator("testworld", seed);
			ac.setWorldSetting(GeneratorSetting.OVERWORLD);
			
			
			for (StructureGenerator sg : StructureGenerator.t) {
				ac.addStructureGeneratorConfig(sg, 5, 0);
			}
			
			//2*spacing - separation
			ac.addStructureGeneratorConfig(StructureGenerator.MINESHAFT, 1, 0);
			ac.addStructureGeneratorConfig(StructureGenerator.PILLAGER_OUTPOST, 5, 0);
			ac.addStructureGeneratorConfig(StructureGenerator.VILLAGE, 5, 0);
			
			/*
			for(MinecraftKey key : getDataPackFeatureResolver().getListedDatapackStructureFeatures()) {
				StructureFeature<?, ?> bla = getDataPackFeatureResolver().getDatapackStructureFeature(key.getNamespace(), key.getKey());
				ac.addStructureFeatureConfig(bla.d, 5, 0);
			}
			*/
			
			/*
			 intRange(0, 256).fieldOf("height")
			 ("sampling")
			 ("top_slide")
			 ("bottom_slide").
			 intRange(1, 4).fieldOf("size_horizontal").
			 intRange(1, 4).fieldOf("size_vertical").
			 DOUBLE.fieldOf("density_factor").
			 DOUBLE.fieldOf("density_offset").
			 BOOL.fieldOf("simplex_surface_noise")
			 BOOL.optionalFieldOf("random_density_offset", Boolean.valueOf(false), Lifecycle.experimental()).
			 BOOL.optionalFieldOf("island_noise_override", Boolean.valueOf(false), Lifecycle.experimental()).
			 BOOL.optionalFieldOf("amplified", Boolean.valueOf(false), Lifecycle.experimental())
			
			 * /
			
			int world_height = 200; // range of 0-256
			
			//xz_scale, y_scale, xz_factor, y_factor
			NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D);
			// target, size, offset
			NoiseSlideSettings top_slide = new NoiseSlideSettings(1, 1, 1);
			NoiseSlideSettings bottom_slide = new NoiseSlideSettings(1, 1, 1);
			int size_horizontal = 1; // range of 1-4
			int size_vertical = 2;
			double density_factor = 5.0; 
			double density_offset = -0.46875D;
			boolean simplex_surface_noise = true; // default
			boolean random_density_offset = true;
			boolean island_noise_override = false;
			boolean amplified = false;
			
			
			NoiseSettings noisesettings = new NoiseSettings(world_height, noisesampling, top_slide, bottom_slide, size_horizontal, size_vertical,
					density_factor, density_offset, simplex_surface_noise, random_density_offset, island_noise_override, amplified);
			
			Block solid_material = Blocks.STONE;
			Block fluid_material = Blocks.WATER;
			int bedrock_roof_position = -10;
			int bedrock_floor_position = 0;
			int sea_level = 30;
			boolean disable_mob_generation = false;
			
		//	ac.setCustomWorldGeneration(noisesettings, solid_material, fluid_material, bedrock_roof_position, bedrock_floor_position, sea_level, disable_mob_generation);
			
			
			
			
			
			LinesBiomeGenerator wcml = new LinesBiomeGenerator.CREATOR(1, false)
					
					.addBiome(rcb)
					.addBiome(rcb)
					.addBiome(Biomes.BEACH)
					.addBiome(Biomes.DEEP_COLD_OCEAN)
					.addBiome(Biomes.BEACH)
					.addBiome(rcb)
					.addBiome(rcb)
					.create();
			
			
			CheckerBoardBiomeGenerator dwcmcb = new CheckerBoardBiomeGenerator.CREATOR(4)
					.addBiome(rcb)
					.addBiome(Biomes.PLAINS)
					.create();
			
			RegisteredCustomBiome rcbb = AdvancedWorldCreatorAPI.getDataPackResolver().getBiome("mmb", "sequoia_woodland");
			
			MultiNoiseBiomeGenerator dwcmmn = new MultiNoiseBiomeGenerator.CREATOR(seed)
					.addBiome(rcb, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
					.addBiome(rcbb, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f)
					.create();
			
			
			
			
			OverworldBiomeGenerator dwcni = new OverworldBiomeGenerator.CREATOR(seed)
					.setBiomeSize(1)
					.overwriteBiome(Biomes.PLAINS, rcb)
					.create();
			
		
			
			CustomDimensionSettings cds = new CustomDimensionSettings(OptionalLong.of(6000), true, false,
					true, true, 1.0, false, false, true, true, true,
					256, TagsBlock.aG.a(), new MinecraftKey("minecraft","overworld"), 0.0F);
			
			AdvancedWorldCreatorAPI.createWorld(ac, cds, dwcmmn);
			
			
			p.teleport(Bukkit.getWorld("testworld").getSpawnLocation());
			e.setCancelled(true);
		}
		if(cmd.equalsIgnoreCase("/unloadcustom")) {
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(AdvancedWorldCreatorAPI.main, ()->Bukkit.unloadWorld("testworld", false), 20*5);
			
			e.setCancelled(true);
			p.sendMessage("done");
		}
		
		if(cmd.equalsIgnoreCase("/resolvebiome")) {
			String pre = args[1];
			String value = args[2];
			
			Bukkit.broadcastMessage("Resolved: "+AdvancedWorldCreatorAPI.getDataPackResolver().getBiome(pre, value));
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/testdatapackresolver")) {
			AdvancedWorldCreatorAPI.getDataPackResolver().getWorldGenFeature("mmb","sequoia_forest/sequoia_supertall");
			e.setCancelled(true);
		}
		
		if(cmd.equalsIgnoreCase("/getbiomelist")) {
			Server server = Bukkit.getServer();
			CraftServer cs = (CraftServer) server;
			DedicatedServer console = cs.getServer();
			
			CraftWorld craftworld = (CraftWorld) p.getWorld();
			WorldServer worldserver = craftworld.getHandle();
			
			IRegistry<BiomeBase> bregi = worldserver.r().b(IRegistry.ay);
			
			
			//  *
			Int2ObjectMap<ResourceKey<BiomeBase>> bbb = (Int2ObjectMap<ResourceKey<BiomeBase>>) getField(BiomeRegistry.class, "c", true);
			
			for(int i : bbb.keySet()) {
				ResourceKey<BiomeBase> otherrkbb = bbb.get(i);
				Bukkit.broadcastMessage(i+" -> " + otherrkbb);
			}
			* //
			
			
			
			
			Set<Entry<ResourceKey<BiomeBase>, BiomeBase>> allbiomes = bregi.d();
			
			for(Entry<ResourceKey<BiomeBase>, BiomeBase> entry : allbiomes) {
				ResourceKey<BiomeBase> key = entry.getKey();
				BiomeBase biomebase = entry.getValue();
				
				
				
				Bukkit.broadcastMessage(key.a() + " -> " + biomebase);
			}
			
			e.setCancelled(true);
			
		}
		

			
	//		DummyWorldChunkManagerMultiNoise.CREATOR creator = new DummyWorldChunkManagerMultiNoise.CREATOR(1345);
	//		creator.addBiome(Biomes.BAMBOO_JUNGLE, temperature_noise, humidity, altitude, weirdness, offset);
	//		creator.create()
		
		
	}
	
	
	*/
}
