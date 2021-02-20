With the CustomOreStructure you spawn Custom Ore-Chunks.
It allows you to set:
  - Spawn-Height
  - Ore-Size
  - Ores per Chunk
  - Ore-Material
  - Target-Material



### How to use:

```

/*
Create a new CustomOreStructure:
*/
CustomOreStructure customore = new CustomOreStructure(Name, Bottom_Offset, Top_Offset,
			maxY, OreSize, OresPerChunk, OreMaterial, TargetMaterial);
      
```
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
  
