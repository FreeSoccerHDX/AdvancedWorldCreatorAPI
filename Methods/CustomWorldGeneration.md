## CustomWorldGeneration allows you to set how the Terrain will generate <br> 
(Normaly you can use Overworld, Nether, End, Amplified, Floating Islands or Caves)

This will only show how to set anything and use it, not what which value will do. <br>
If you want to know what all those values do check out the [Wiki-Page].
There you can find also the default values for the existing WorldGenerators.

First you will need to create your NoiseSettings:
```
int world_height = 200; 
			
//xz_scale, y_scale, xz_factor, y_factor
NoiseSamplingSettings noisesampling = new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D);
// target, size, offset
NoiseSlideSettings top_slide = new NoiseSlideSettings(1, 1, 1);
NoiseSlideSettings bottom_slide = new NoiseSlideSettings(1, 1, 1);
int size_horizontal = 1; // range of 1-4
int size_vertical = 2;
double density_factor = 5.0; 
double density_offset = -0.46875D;
boolean simplex_surface_noise = true;
boolean random_density_offset = true;
boolean island_noise_override = false;
boolean amplified = false;


NoiseSettings noisesettings = new NoiseSettings(world_height, noisesampling, top_slide, bottom_slide, size_horizontal, size_vertical,
    density_factor, density_offset, simplex_surface_noise, random_density_offset, island_noise_override, amplified);

```

After creating the NoiseSettings you can call the Method 'setCustomWorldGeneration' with some extra arguments:
```
Block solid_material = Blocks.STONE;
Block fluid_material = Blocks.WATER;
int bedrock_roof_position = -10;
int bedrock_floor_position = 0;
int sea_level = 30;
boolean disable_mob_generation = false;

advancedcreator.setCustomWorldGeneration(noisesettings, solid_material, fluid_material, bedrock_roof_position, bedrock_floor_position, sea_level, disable_mob_generation);
			
```
Note: You will not find a Field for StructureSettings, but you can set them with the Method 'addStructureFeatureConfig' from the AdvancedCreator



https://timcloud.ddns.net/github/wg1.png
[Wiki-Page]: https://minecraft.gamepedia.com/Custom_world_generation#Noise_settings
