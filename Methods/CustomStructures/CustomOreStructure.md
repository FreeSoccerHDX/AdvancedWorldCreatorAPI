With the CustomOreStructure you spawn Custom Ore-Chunks.
It allows you to set:
  - Spawn-Height
  - Ore-Size
  - Ores per Chunk
  - Ore-Material
  - Target-Material



### How to use:

```
//Create a new CustomOreStructure:
CustomOreStructure customore = new CustomOreStructure(Name, Bottom_Offset, Top_Offset,
			maxY, OreSize, OresPerChunk, OreMaterial, TargetMaterial);    
```
Name(Only a-c and 0-9 allowed) = How you wanne call the Structure (and register it to Minecraft)<br>
Bottom_Offset(Int) = How much blocks above 0 the Ore can spawn<br>
Top_Offset(Int) = How much blocks under #maxY the Ore can spawn<br>
maxY(Int) = The Range in which the Ore can spawn<br>
-> bottom_offset=0 + top_offset=0 + maxY=256 allows the Ore to spawn on every Height<br>
OreSize(Int) = The maximum Size of the Ore (Iron, Gold have OreSize: 9)<br>
OresPerChunk(int) = How many times the Server will try to generate this Ore in a Chunk<br>
OreMaterial(Block) = What Type the Ore has (For example: Blocks.DIAMOND_BLOCK or Blocks.IRON_BLOCK)<br>
TargetMaterial(Block) = What Type the Ore can overwrite (For example: Blocks.STONE)<br>

Now you can use it while creating a [CustomBiome] with the Method: <br>
```CustomBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration, CustomStructure)```


### Methods:
  - Overwrites the TargetMaterial with Vanilla-Settings
    ```setVanillaReplaceRule(StructureRule)```
    There are 3 Vanilla-StructureRules:
      - NATURAL_STONE (Stone, Andesite, Diorite...)
      - NETHERRACK (Netherrack)
      - NETHER_ORE_REPLACEABLES (Netherrack, Basalt, Blackstone)


  
[CustomBiome]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/blob/main/Methods/CustomBiome.md
  
