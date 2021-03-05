The AdvancedCreator is very simple to use and controls:
  - Worldname
  - What Structures are generating
  - Seed
  - Hardcoremode
  - Bedrock-Material
  - and more...
  
You will need the AdvancedCreator for creating a Custom World.

First you need to create an AdvancedCreator Object: <br>
``` 
AdvancedCreator advancedcreator = new AdvancedCreator(Worldname, Seed);
or with random Seed:
AdvancedCreator advancedcreator = new AdvancedCreator(Worldname);

```

## Methods:
  - Adds which Structures can be generated in the World, and how often
    ###### ``` addStructureFeatureConfig(StructureGenerator, spacing, seperation) ```<br>
    The first Argument is what Structure you would like to spawn. For Example:<br>
    StructureGenerator.MINESHAFT, StructureGenerator.PILLAGER_OUTPOST, StructureGenerator.VILLAGE
    spacing,seperation(ints) = How close the Feature can spawn to other Features of the same type (2*spacing-separation has to be greater 1)
    
  - Sets how the World looks like
    ###### ``` setWorldGeneration(WorldGeneration) ```<br>
    You can choose between 6 Presets: <br>
      - Overworld (Like normal Overworld generates)
      - Nether (Like normal Nether generates)
      - End (Like normal End generates)
      - Amplified (Overworld with extreme hills and cliffs)
      - Caves (Nether-Generation but with Stone instead of Netherrack)
      - Floating Islands (End-Generation but with Stone instead of Endstone)
      
   - Sets if the World is in Hardcore-Mode
     ###### ``` setHardcore(Hardcore) ```<br>
     
   - Sets the Type of the Bedrock at the Top (if it would generate)
     ###### ``` setTopBedrock(Blocks) ```<br>
     For example: Blocks.DIAMOND_BLOCK
     
   - Sets the Type of the Bedrock at the Bottom (if it would generate)
     ###### ``` setBottomBedrock(Blocks) ```<br>
     For example: Blocks.DIAMOND_BLOCK
     
   - Generates Strucutres(Village...) like Vanilla
     ###### ``` loadDefaultStructureFeatureConfig() ```<br>
     
   - Sets the World's Difficulty
     ###### ``` setDifficulty(EnumDifficulty) ```<br>
     For Example: EnumDifficulty.EASY (Default)
     
   - Sets the Gamemode of Players in the World
     ###### ``` setGameMode(EnumGamemode) ```<br>
     For Example: EnumGamemode.SURVIVAL (Default)
     
   - Sets if Monsters can spawn
      ###### ``` setAllowMonsters(boolean) ```<br>
      Default: true
      
   - Sets if Animals can spawn
      ###### ``` setAllowAnimals(boolean) ```<br>
      Default: true
      
  - Sets if Decoration (Ores, Trees...) can generate
      ###### ``` setGenerateDecoration(boolean) ```<br>
      Default: true
      
  - Sets if Structures (Villages, Mansions...) can generate
      ###### ``` setGenerateStructures(boolean) ```<br>
      Default: true
      
  - Sets a Custom WorldGenerator (like Overworld, Amplified...)
      ###### ``` setCustomWorldGeneration(noisesettings, solid_material, fluid_material, bedrock_roof_position, bedrock_floor_position, sea_level, disable_mob_generation) ```<br>
      Check out the [CustomWorldGeneration] for more information.
      
      
[CustomWorldGeneration]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/blob/main/Methods/CustomWorldGeneration.md
