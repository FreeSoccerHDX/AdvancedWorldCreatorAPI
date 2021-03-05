## With this BiomeGenerator you can create Biome-Lines that will repeat infinity:
  - LinesBiomeGenerator.CREATOR
    Methods:  
      - ``` addBiome(Biomes) ``` <br>
        ``` addBiome(RegisteredCustomBiome) ``` <br>
        Add a Biome to the generation <br>
        For example: Biomes.PLAINS or Biomes.OCEAN (or a Custom one)
        
        
      - ``` create() ``` <br>
        Used to create the real LinesBiomeGenerator-Object <br>
        
     Usage Example:
     ```
     // size= how big the Biome-Area is, direction= in which direction the lines will repeat (default true=x-direction)
    	LinesBiomeGenerator worldchunkmanager = new LinesBiomeGenerator.CREATOR(size,direction)
        .addBiome(Biomes.PLAINS)
        .addBiome(Biomes.OCEAN)
    	.create();
          
      ```
      or longer:
      ```
    	LinesBiomeGenerator.CREATOR biomegenerator_creator = new LinesBiomeGenerator.CREATOR(size,direction);
	    biomegenerator_creator.addBiome(Biomes.PLAINS);
        biomegenerator_creator.addBiome(Biomes.OCEAN);
	    LinesBiomeGenerator worldchunkmanager = biomegenerator_creator.create();
          
      ```
