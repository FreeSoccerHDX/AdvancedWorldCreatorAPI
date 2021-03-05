## With this BiomeGenerator you can create a CheckerBoard of many different Biomes:
  - CheckerBoardBiomeGenerator.CREATOR
    Methods:
      - ``` addBiome(Biomes) ```<br>
      - ``` addBiome(RegisteredCustomBiome) ``` <br>
        You have to add at least 1 Biome to use this BiomeGenerator
        
      - ``` create() ``` <br>
        Used to create the real CheckerBoardBiomeGenerator-Object <br>
        
     Usage Example:
     ```
    	CheckerBoardBiomeGenerator biomegenerator = new CheckerBoardBiomeGenerator.CREATOR(4/*the sqaure is how big one Square is*/)
		.addBiome(Biomes.DESERT)
		.addBiome(Biomes.PLAINS)
		.create();
          
      ```
      or longer:
      ```
    	CheckerBoardBiomeGenerator.CREATOR biomegenerator_creator = new CheckerBoardBiomeGenerator.CREATOR(4/*the sqaure is how big one Square is*/)
		biomegenerator_creator.addBiome(Biomes.DESERT)
		biomegenerator_creator.addBiome(Biomes.PLAINS)
	    CheckerBoardBiomeGenerator biomegenerator = biomegenerator_creator.create();
          
      ```
