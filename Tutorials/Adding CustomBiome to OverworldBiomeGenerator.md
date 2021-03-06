## This Tutorial will show you how to create a CustomBiome and how to replace a Biome with the OverworldBiomeGenerator.

#### First we need a CustomBiome-Object:
  ```
      CustomBiome cb = new CustomBiome("not_minecraft", "not_plains");
  ```
  The 2. Arguments will set which Name the Player can see with F3, when he is in the Biome.
  ![alt text](https://timcloud.ddns.net/github/tutorial_biomename.png)
  
  
#### In the next Step we change some Colors for the Biome.
  ```
        cb.setGrassColor(new Color(255,255,255));
	cb.setFoliageColor(new Color(0,0,255));
	cb.setWaterColor(new Color(255,0,0));
  ```
  This will set the Color of Grass to White, (Most)Leaves and Vines to Blue and the Water Color to Red.
  ![alt text](https://timcloud.ddns.net/github/tutorial_biomecolor.png)
  
  
#### Without Mobs and Decoration the Biome seems to be Empty.
  ```
     	//Animals:
     	cb.addMobToBiome(EntityType.PIG, 100, 1, 50);
	cb.addMobToBiome(EntityType.COW, 100, 1, 50);
	cb.addMobToBiome(EntityType.SHEEP, 100, 1, 50);
	cb.addMobToBiome(EntityType.CHICKEN, 100, 1, 50);
	//Monsters:
	cb.addMobToBiome(EntityType.WITHER_SKELETON, 100, 1, 10);
	cb.addMobToBiome(EntityType.SHULKER, 100, 1, 10);
  ```
  This will allow Pigs, Cows, Sheeps and Chickens to spawn in Groups of 1 to 50,
  and also Wither Skeletons and Shulkers to spawn in Groups of 1 to 10.
  Since the 2. Argument is always 100, they all have the same Chance to spawn.
  If you set the 2. Argument of one to 1 it will cause the Mob to spawn way less then the others.
  
  
  ```
     	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FLOWER_FOREST);
	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FOREST_FLOWER_TREES);
			
	cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_COAL);
	for(int i = 0; i < 100; i++) {
		cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
	}
  ```
  With the 1. Method we add many Flowers to the Vegetal Decoration of the Biome (Like the 'flower_forest'-Biome).
  The 2. Method will add some random Trees (Oak and Birch).
  The 3. Method is for Underground Ores, we add the default Coal-Ore Configuration like in every Overworld Biome.
  And the last one: We add 100 times the Diamond-Ore Configuration so it will spawn extremly often in this Biome.
  
  The Biome now:
  ![alt text](https://timcloud.ddns.net/github/tutorial_biomefull.png)
  Note: You can see that Birch-Leaves don't change the Color, but thats not a bug. 
  
  
  #### We now have to add something to the Underground:
  ```
      cb.addWorldGenFeature(Features.AIR, WorldGenCarver.CAVE);
  ```
   This will spawn normal small tunnels to the Underground:
   ![alt text](https://timcloud.ddns.net/github/tutorial_biomeunderground.png)

  
  #### The last thing we add is a Vanilla Village:
  ```
      cb.addBiomeStructure(BiomeStructure.VILLAGE_SNOWY);
  ```
  This will cause Snow-Villages to spawn in this Biome randomly.
  
  
  ## How to use the CustomBiome:
  
  #### We need to Register the CustomBiome in the API like this:
  ```
      RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb);
  ```
  
  #### Add to BiomeGenerator:
  ```
      OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed())
	.overwriteBiome(Biomes.PLAINS, rcb) // <<<<<<<<<<< HERE
	.create();
  ```
  With that BiomeGenerator we replace the normal Plains-Biome with our CustomBiome.
  Note: Every BiomeGenerator works differently, the 'overwriteBiome' only Works for the OverworldBiomeGenerator.
  
  
  
  
  
  ### The full Code to create this World:
  ```
      	CustomBiome cb = new CustomBiome("not_minecraft", "not_plains");
	cb.setGrassColor(new Color(255,255,255));
	cb.setFoliageColor(new Color(0,0,255));
	cb.setWaterColor(new Color(255,0,0));


	cb.addMobToBiome(EntityType.PIG, 100, 1, 50);
	cb.addMobToBiome(EntityType.COW, 100, 1, 50);
	cb.addMobToBiome(EntityType.SHEEP, 100, 1, 50);
	cb.addMobToBiome(EntityType.CHICKEN, 100, 1, 50);

	cb.addMobToBiome(EntityType.WITHER_SKELETON, 100, 1, 10);
	cb.addMobToBiome(EntityType.SHULKER, 100, 1, 50);


	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FLOWER_FOREST);
	cb.addWorldGenDecorationFeature(Decoration.VEGETAL_DECORATION, BiomeDecoratorGroups.FOREST_FLOWER_TREES);
			
	cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_COAL);
	for(int i = 0; i < 100; i++) {
		cb.addWorldGenDecorationFeature(Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
	}

	cb.addWorldGenFeature(Features.AIR, WorldGenCarver.CAVE);

	cb.addBiomeStructure(BiomeStructure.VILLAGE_SNOWY);

	RegisteredCustomBiome rcb = AdvancedWorldCreatorAPI.registerCustomBiome(cb,true);

	AdvancedCreator ac = new AdvancedCreator("myWorld");

	OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed())
			.overwriteBiome(Biomes.PLAINS, rcb)
			.create();

	DimensionManager dm = CustomDimensionSettings.getOverworldSettings();

	AdvancedWorldCreatorAPI.createWorld(ac, dm, obg);
  ```
  
  
  
  
  
  
  
  
