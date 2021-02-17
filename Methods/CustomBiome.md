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
  ``` addBiomeStructureFeature(BiomeStructureFeature) ```
  
  - Adds a StrucutreFeature(Villages, Fortress, Nether-Fossil, Pyramid...) to the Biome-Generation
  
