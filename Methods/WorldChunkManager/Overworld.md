With the WorldChunkManager's you can set how the Biomes will generate in your custom World.

**Note: This is something you will need to generate your Custom World.

## There are currently 3 different WorldChunkManager's - CREATOR:
  - DummyWorldChunkManagerOverworld.CREATOR
    Methods:
      - ``` setBiomeSize(size) ```<br>
        How big the Biomes will generate
        
      - ``` overwriteBiome(Biomes,RegisteredCustomBiome) ``` <br>
        You can overwrite existing Biomes, but not add some
        
      - ``` create() ``` <br>
        Used to create the real DummyWorldChunkManagerOverworld-Object <br>
        
     Usage Example:
     ```
    	DummyWorldChunkManagerOverworld worldchunkmanager = new DummyWorldChunkManagerOverworld.CREATOR(seed)
		.setBiomeSize(1)
		.overwriteBiome(Biomes.PLAINS, RegisteredCustomBiome)
		.create();
          
      ```
      or longer:
      ```
    	DummyWorldChunkManagerOverworld.CREATOR worldchunkmanager_creator = new DummyWorldChunkManagerOverworld.CREATOR(seed);
		worldchunkmanager_creator.setBiomeSize(1);
		worldchunkmanager_creator.overwriteBiome(Biomes.PLAINS, RegisteredCustomBiome);
		DummyWorldChunkManagerOverworld worldchunkmanager = worldchunkmanager_creator.create();
          
      ```
