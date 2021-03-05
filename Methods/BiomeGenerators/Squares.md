With the WorldChunkManager's you can set how the Biomes will generate in your custom World.

**Note: This is something you will need to generate your Custom World.

## With this WorldChunkManager you can create Biome-Sqaures that will repeat:
  - WorldChunkManagerSquares.CREATOR
    Methods:  
      - ``` addBiome(Biomes) ``` <br>
        ``` addBiome(RegisteredCustomBiome) ``` <br>
        Add a Biome to the generation <br>
        For example: Biomes.PLAINS or Biomes.OCEAN (or a Custom one)
        
        
      - ``` create() ``` <br>
        Used to create the real WorldChunkManagerSquares-Object <br>
        
     Usage Example:
     ```
     // size= how big the Biome-Area is
    	WorldChunkManagerSquares worldchunkmanager = new WorldChunkManagerSquares.CREATOR(size)
        .addBiome(Biomes.PLAINS)
        .addBiome(Biomes.OCEAN)
    	.create();
          
      ```
      or longer:
      ```
    	WorldChunkManagerSquares.CREATOR worldchunkmanager_creator = new WorldChunkManagerSquares.CREATOR(size);
	    worldchunkmanager_creator.addBiome(Biomes.PLAINS);
        worldchunkmanager_creator.addBiome(Biomes.OCEAN);
	    WorldChunkManagerSquares worldchunkmanager = worldchunkmanager_creator.create();
          
      ```
