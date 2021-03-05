## With this BiomeGenerator you can create a World with fully custom Biomes that generate and how big common they are:
  - MultiNoiseBiomeGenerator.CREATOR
    Methods:
      - ``` addBiome(Biomes, temperature_noise, humidity, altitude, weirdness, offset) ```<br>
      - ``` addBiome(RegisteredCustomBiome, temperature_noise, humidity, altitude, weirdness, offset) ```<br> 
        Adds the Biome to the generation and specified values<br>
        temperature_noise(float) = Used to place similar biomes near each other.  <br>
        humidity(float) = Used to place similar biomes near each other. <br>
        altitude(float, -2.0 - 2.0) =U sed to place similar biomes near each other. <br>
        weirdness(float, -2.0 - 2.0) = Defines how weird the biome is going to appear next to other biomes. <br>
        offset(float, 0.0 - 1.0) = Used to generate Biome-Edges, smaller Values => greater edge
	
        Look also here: https://minecraft.gamepedia.com/Custom#Generator_types <br>
        Note: You have to add at least 1 Biome to work
        
      - ``` create() ``` <br>
        Used to create the real MultiNoiseBiomeGenerator-Object <br>
        
     Usage Example:
     ```
    	MultiNoiseBiomeGenerator worldchunkmanager = new MultiNoiseBiomeGenerator.CREATOR(seed)
					.addBiome(Biomes.PLAINS, temperature_noise, humidity, altitude, weirdness, offset)
					.create();
          
      ```
      or longer:
      ```
    	MultiNoiseBiomeGenerator.CREATOR biomegenerator_creator = new MultiNoiseBiomeGenerator.CREATOR(seed);
					biomegenerator_creator.addBiome(Biomes.PLAINS, temperature_noise, humidity, altitude, weirdness, offset);
				MultiNoiseBiomeGenerator worldchunkmanager = worldchunkmanager_creator.create();
          
      ```
