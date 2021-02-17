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
    	DummyWorldChunkManagerCheckerBoard worldchunkmanager = new DummyWorldChunkManagerCheckerBoard.CREATOR(4)
	.addBiome(rcb)
	.addBiome(Biomes.PLAINS)
	.create();
          
      ```
