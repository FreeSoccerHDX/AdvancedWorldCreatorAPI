### This Code will generate a World that goes from -512 Blocks all the Way up to 760 Blocks. <br>
### (This will need "some" more RAM :) )

 <br>
 <br>
 <br>
 
 

```
AdvancedCreator ac = new AdvancedCreator("world_new",-8695917198943384614L);
						
//xz_scale, y_scale, xz_factor, y_factor
NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(0.9999999814507745, 0.9999999814507745, 80, 160);

// target, size, offset
NoiseSlideSettings top_slide = new NoiseSlideSettings(-10, 3, 0);
NoiseSlideSettings bottom_slide = new NoiseSlideSettings(-30, 0, 0);

int size_horizontal = 1;
int size_vertical = 2;
int miny = -512;
int world_height = 1280;
int sealevel = 63;
double density_factor = 1.0;
double density_offset = -0.46875;
boolean simplex_surface_noise = true;
boolean random_density_offset = true;
boolean island_noise_override = false;
boolean amplified = false;

NoiseSettings noisesettings = NoiseSettings.a(miny, world_height, noisesampling, top_slide, bottom_slide, size_horizontal, size_vertical,
      density_factor, density_offset, simplex_surface_noise, random_density_offset, island_noise_override, amplified);

BlockData solid_material = Bukkit.createBlockData(Material.STONE);
BlockData fluid_material = Bukkit.createBlockData(Material.WATER);

boolean disablemobgeneration = false;
boolean aquifers = true;
boolean noise_caves = true;
boolean deepslate = true;
boolean oreveins = true;
boolean noodlecaves = true;
int bedrockroof = -10;
int bedrockfloor = 0;
int minsurface = 0;



ac.setCustomWorldSetting(noisesettings, solid_material, fluid_material, bedrockroof, bedrockfloor, sealevel, minsurface, 
  disablemobgeneration, aquifers, noise_caves, deepslate, oreveins, noodlecaves);

CustomDimensionSettings cds = new CustomDimensionSettings(OptionalLong.empty(), true, false, false, true, 1.0, false, false,
true, false, true, miny, world_height , world_height, Tag.INFINIBURN_OVERWORLD,
WorldEffects.OVERWORLD, 0.0f);

OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed()).create(); 
ac.loadDefaultStructureGeneratorConfig();
ac.addStructureGeneratorConfig(WorldStructureGenerator.MINESHAFT, 8, 3);
ac.addStructureGeneratorConfig(WorldStructureGenerator.VILLAGE, 16, 8);



World newworld = AdvancedWorldCreatorAPI.createWorld(ac, cds, obg);

```
