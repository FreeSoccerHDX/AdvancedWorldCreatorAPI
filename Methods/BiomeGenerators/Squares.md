## With this BiomeGenerator you can create Biome-Sqaures that will repeat:
  - SquaresBiomeGenerator.CREATOR
    Methods:  
      - ``` addBiome(Biomes) ``` <br>
        ``` addBiome(RegisteredCustomBiome) ``` <br>
        Add a Biome to the generation <br>
        For example: Biomes.PLAINS or Biomes.OCEAN (or a Custom one)
        
        
      - ``` create() ``` <br>
        Used to create the real SquaresBiomeGenerator-Object <br>
        
     Usage Example:
     ```
     // size= how big the Biome-Area is
    	SquaresBiomeGenerator worldchunkmanager = new SquaresBiomeGenerator.CREATOR(size)
        .addBiome(Biomes.PLAINS)
        .addBiome(Biomes.OCEAN)
    	.create();
          
      ```
      or longer:
      ```
    	SquaresBiomeGenerator.CREATOR biomegenerator_creator = new SquaresBiomeGenerator.CREATOR(size);
	    biomegenerator_creator.addBiome(Biomes.PLAINS);
        biomegenerator_creator.addBiome(Biomes.OCEAN);
	    SquaresBiomeGenerator worldchunkmanager = biomegenerator_creator.create();
          
      ```
