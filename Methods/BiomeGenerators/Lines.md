With the WorldChunkManager's you can set how the Biomes will generate in your custom World.

**Note: This is something you will need to generate your Custom World.

## With this WorldChunkManager you can create Biome-Lines that will repeat infinity:
  - WorldChunkManagerLines.CREATOR
    Methods:  
      - ``` addBiome(Biomes) ``` <br>
        ``` addBiome(RegisteredCustomBiome) ``` <br>
        Add a Biome to the generation <br>
        For example: Biomes.PLAINS or Biomes.OCEAN (or a Custom one)
        
        
      - ``` create() ``` <br>
        Used to create the real WorldChunkManagerLines-Object <br>
        
     Usage Example:
     ```
     // size= how big the Biome-Area is, direction= in which direction the lines will repeat (default true=x-direction)
    	WorldChunkManagerLines worldchunkmanager = new WorldChunkManagerLines.CREATOR(size,direction)
        .addBiome(Biomes.PLAINS)
        .addBiome(Biomes.OCEAN)
    	.create();
          
      ```
      or longer:
      ```
    	WorldChunkManagerLines.CREATOR worldchunkmanager_creator = new WorldChunkManagerLines.CREATOR(size,direction);
	    worldchunkmanager_creator.addBiome(Biomes.PLAINS);
        worldchunkmanager_creator.addBiome(Biomes.OCEAN);
	    WorldChunkManagerLines worldchunkmanager = worldchunkmanager_creator.create();
          
      ```
