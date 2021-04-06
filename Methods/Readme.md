## List of all Vanilla Biomes:
```
AdvancedWorldCreatorAPI.getAllVanillaBiomes()
```
Idea behind this: If you want to replace settings in all Biomes you can iterate through this List and don't have to figure it out by yourself.



## Creating a simple Custom World with a Custom Biome:

```
CustomBiome cb = new CustomBiome("wonderland");
cb.setGrassColor(new Color(255,0,0));
cb.setWaterColor(new Color(0,0,0));
RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb);


long seed = 123456123456;
AdvancedCreator ac = new AdvancedCreator("testworld", seed);
ac.addStructureFeatureConfig(StructureGenerator.MINESHAFT, 1, 0);


DummyWorldChunkManagerCheckerBoard dwcmcb = new DummyWorldChunkManagerCheckerBoard.CREATOR(2)
		.addBiome(rcb)
		.addBiome(Biomes.PLAINS)
		.create();


CustomDimensionSettings cds = new CustomDimensionSettings(OptionalLong.of(6000), true, false,
		true, true, 1.0, false, false, true, true, true,
		256, TagsBlock.aG.a(), new MinecraftKey("minecraft","overworld"), 0.0F);
			
      
World newworld = AdvancedWorldCreatorAPI.createWorld(ac, cds, dwcmcb);

```

## The Result: 
(after relogging to recive the new Biome-Settings)

![alt text](https://timcloud.ddns.net/github/example1.png)

![alt text](https://timcloud.ddns.net/github/example2.png)

![alt text](https://timcloud.ddns.net/github/example3.png)
