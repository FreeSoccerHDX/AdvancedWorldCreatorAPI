Custom Biomes can be created to spawn specific Mobs or generate special Strucutres. 
You can also edit which Particles are spawned, which Sounds are used and what Colors are used.

##### Important: You need to rejoin after registration to the server **OR** register the Custom Biome on Server-Start. Otherwhise Particles, Sounds, Biome Name and Colors may not show to the player!

First you need to create an CustomBiome Object:

``` CustomBiome custombiome = new CustomBiome(BiomeName); ```

The BiomeName is also displayed ingame when you press F3:

![alt text](https://timcloud.ddns.net/github/BiomeName.png)

After adding your settings you have to register the Custom Biome to use it:

``` RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(custombiome); ```

## Methods:
  - Adds a StrucutreFeature(Villages, Fortress, Nether-Fossil, Pyramid...) to the Biome-Generation
    ``` addBiomeStructureFeature(BiomeStructureFeature) ```
  
  - Adding Caves, filled with Air or Liquid to the Biome-Generation
    ``` addWorldGenFeature(WorldGenStage.Features, WorldGenFeature) ```
    The first Argument can be Features.AIR or Features.LIQUID
    The second Argument can be CAVE, CANYON, OCEAN_CAVE, UNDERWATER_CANYON, UNDERWATER_CAVE or NETHER_CAVE
    Note: Adding the same WorldGenFeature multiple times increases it spawn-chance

  -
