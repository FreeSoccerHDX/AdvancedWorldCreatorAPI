Custom Biomes can be created to spawn specific Mobs or generate special Strucutres. 
You can also edit which Particles are spawned, which Sounds are used and what Colors are used.

Most Information can be found here too: https://minecraft.gamepedia.com/Biome/JSON_format


##### Important: You need to rejoin after registration to the server **OR** register the Custom Biome on Server-Start. Otherwhise Particles, Sounds, Biome Name and Colors may not show to the player!

First you need to create an CustomBiome Object:

###### ``` CustomBiome custombiome = new CustomBiome(BiomeName); ```
The BiomeName(lowercase) is also displayed ingame when you press F3:

![alt text](https://timcloud.ddns.net/github/BiomeName.png) <br>
(You can change the prefix "awc" with the Method: ```setPreKey(String)```, but it is limited to a-c and 0-9)

After adding your settings you have to register the Custom Biome to use it:

###### ``` RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(custombiome); ```

## Methods:
  - Adds a StrucutreFeature(Villages, Fortress, Nether-Fossil, Pyramid...) to the Biome-Generation
    ###### ``` addBiomeStructureFeature(BiomeStructureFeature) ```
  
  - Adding Caves, filled with Air or Liquid to the Biome-Generation
    ###### ``` addWorldGenFeature(WorldGenStage.Features, WorldGenFeature) ```
    The first Argument can be Features.AIR or Features.LIQUID
    The second Argument can be CAVE, CANYON, OCEAN_CAVE, UNDERWATER_CANYON, UNDERWATER_CAVE or NETHER_CAVE<br>
    **Note: Adding the same WorldGenFeature multiple times increases it spawn-chance**

  - Adding Structures to the Biome-Generation that spawn mostly random in the Biome
    ###### ``` addWorldGenDecorationFeature(Decoration, BiomeDecoratorGroups) ```
    The first Argument can be Decoration.SURFACE_STRUCTURES, Decoration.UNDERGROUND_ORES...
    It is used to determine where the Structure will spawn.
    The second Argument can be BiomeDecoratorGroups.ORE_DIAMOND ...
    There is almost every Structure listed that is used in Vanilla Generation <br>
    **Note: Many Structures have requirements to work. For example:** <br>
      Ores(from Overworld) will need Stone to replace <br>
      Ores(from Nether) will need Netherrack to replace <br>
      Trees will only spawn on dirt, grass ... <br>
   
  - Sets which Blocks are used to generate the Surface of the Biome
    ###### ``` setSurfaceBuilder(SurfaceBuilder) ```
    There are many SurfaceBuilders provided like SAND, END or just GRASS
    
  - Sets which Color is seen far away when in the Biome
    ###### ``` setFogColor(java.awt.Color) ```
    
  - Sets which Color water has in this Biome
    ###### ``` setWaterColor(java.awt.Color) ```
   
  - Sets which Color is seen far away under water in the Biome
    ###### ``` setWaterFogColor(java.awt.Color) ```
  
  - Sets which Color the Sky has when in the Biome
    ###### ``` setSkyColor(java.awt.Color) ```
  
  - Sets which Color Leaves, Vines etc. has in the Biome
    ###### ``` setFoliageColor(java.awt.Color) ```
   
  - Sets which Color Grass has in the Biome
    ###### ``` setGrassColor(java.awt.Color) ```
   
  - Sets what Particle is spawned randomly in the Air of the Biome
    ###### ``` setBiomeParticles(Particles, quantity) ```<br>
    The first Argument is a class with ParticleType's <br>
    For example: Particles.CLOUD or Particles.BUBBLE<br>
    quantity(float, 0.0-1.0) = how many particles are spawned
       
  - Sets what Music starts playing near player
    ###### ``` setCaveSoundSettings(SoundEffects, tickdelay, blockdistance, offset) ```<br>
    The first Argument is a class with SoundEffect's you can use for it<br>
    tickdelay(int) = how often it is playing (20 ticks = 1s, normally 6000 is used here)<br>
    blockdistance(int) = in which range of blocks the sound can play<br>
    offset(double) = used for how the sound sounds
    
  - Sets what Sound is playing near player
    ###### ``` setCaveSound(SoundEffects, tickchance) ```<br>
    The first Argument is a class with SoundEffect's you can use for it<br>
    tickchance(double, 0.0-1.0) = A chance for every tick (20 ticks = 1s) to play it
    
  - Sets what Music is used randomly
    ###### ``` setMusic(SoundEffects) ```<br>
    The first Argument is a class with SoundEffect's you can use for it
    
  - Sets what Sound is used randomly (in caves)
    ###### ``` setAmbientSound(SoundEffects) ```<br>
    The first Argument is a class with SoundEffect's you can use for it  
    
  - Sets in which Category this Biome is (no generation impact found)
    ###### ``` setGeography(Geography) ```<br>
    For example: Geography.DESERT
  
  - Sets if it can raining, snowing or nothing
    ###### ``` setPrecipitation(Precipitation) ```<br>
    For example: Precipitation.RAIN
  
  - Sets if water is Frozen or not
    ###### ``` setTemperatureModifier(TemperatureModifier) ```<br>
    TemperatureModifier.FROZEN or TemperatureModifier.NONE 
    
  - Used for terrain noise generation
    ###### ``` setDepth(value) ```<br>
    value(float, -2 - +2 ?) = how tall or flat the Biome is generated <br>
    Negative: water, Positive: Mountains
    
  - Used for terrain noise generation
    ###### ``` setScale(value) ```<br>
    value(float, 0 - +2 ?) = stretches the generation <br>
    Lower value flats the terrain
    
  - Modifys color of grass/leaves and if Snowman's take dmg
    ###### ``` setTemperature(value) ```<br>
    value(float, 0-1.0) = Scale between cold(0) and warm(1)
    
  - Modifys color of grass/leaves and how fast Fire burns out
    ###### ``` setDownfall(value) ```<br>
    value(float, 0-1.0) = Scale between fast fire(0) and slow fire(1)
    
  - Modifys color of grass/leaves and how fast Fire burns out
    ###### ``` setMobSpawnProbability(value) ```<br>
    value(float, 0-1.0) = Scale between no mobs(0) and to many mobs(1)
    
