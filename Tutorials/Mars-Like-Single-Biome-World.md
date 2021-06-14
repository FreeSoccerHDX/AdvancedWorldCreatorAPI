
![alt text](https://timcloud.ddns.net/github/mars1.png)

AdvancedCreator advancedcreator = new AdvancedCreator("mytestworld");
advancedcreator.addStructureGeneratorConfig(WorldStructureGenerator.VILLAGE, 4, 2);
//xz_scale, y_scale, xz_factor, y_factor
NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(0.9999999814507745, 0.9999999814507745, 80, 160);
// target, size, offset
NoiseSlideSettings top_slide = new NoiseSlideSettings(-10, 3, 0);
NoiseSlideSettings bottom_slide = new NoiseSlideSettings(-30, 0, 0);
int size_horizontal = 1;
int size_vertical = 2;
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
	 
Block solid_material = Blocks.b; // Variable for Stone in 1.17
Block fluid_material = Blocks.A; // Variable for Water in 1.17
			
			
advancedcreator.setCustomWorldSetting(noisesettings, solid_material, fluid_material, -1000, 0, 63, -32, false, true, true, true, true, true);
			
			
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
cb.setSurfaceBuilder(SurfaceBuilder.BADLANDS);
RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb);

//singlebiome
worldchunkmanager = new SingleBiomeGenerator.CREATOR(rcb).create();
			
			
AdvancedWorldCreatorAPI.createWorld(advancedcreator, dimensionmanager, worldchunkmanager);
