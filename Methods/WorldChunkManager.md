With the WorldChunkManager's you can set how the Biomes will generate in your custom World.

**Note: This is something you will need to generate your Custom World.

## There are currently 3 different WorldChunkManager's - CREATOR:
  - DummyWorldChunkManagerCheckerBoard.CREATOR
    Methods:
      - ``` addBiome(Biomes) ```<br>
      - ``` addBiome(RegisteredCustomBiome) ``` <br>
        You have to add at least 1 Biome to use this WorldChunkManager
        
      - ``` create() ``` <br>
        Used to create the real DummyWorldChunkManagerCheckerBoard-Object <br>
        
     Usage Example:
     ```
    	DummyWorldChunkManagerCheckerBoard worldchunkmanager = new DummyWorldChunkManagerCheckerBoard.CREATOR(4/*the sqaure is how big one Chunk-Region is*/)
		.addBiome(Biomes.DESERT)
		.addBiome(Biomes.PLAINS)
		.create();
          
      ```
      or longer:
      ```
    	DummyWorldChunkManagerCheckerBoard.CREATOR worldchunkmanager_creator = new DummyWorldChunkManagerCheckerBoard.CREATOR(4/*the sqaure is how big one Chunk-Region is*/)
		worldchunkmanager_creator.addBiome(Biomes.DESERT)
		worldchunkmanager_creator.addBiome(Biomes.PLAINS)
	        DummyWorldChunkManagerCheckerBoard worldchunkmanager = worldchunkmanager.create();
          
      ```
