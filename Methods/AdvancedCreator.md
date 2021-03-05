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


## Methods to change World-Settings:
  * ```setWorldSetting(GeneratorSetting) ``` <br>
    - Change how the Terrain will generate <br>
    - Example: GeneratorSetting.END <br>

  * ```setCustomWorldSetting(NoiseSettings, Solid_material, Fluid_material, bedrock_roof_position, bedrock_floor_position, sea_level, disable_mob_generation) ``` <br>
    - Change how the Terrain will generate <br>
    - Check out [CustomWorldSetting] for more information. <br>

  * ```setSeed(long) ``` <br>
    - Sets the Seed for the World <br>
    - Example: 1234567890 <br>

  * ```setWorldName(Name) ``` <br>
    - Sets the Name for the World <br>
    - Example: CustomWorld <br>

  * ```setHardcore(Boolean) ``` <br>
    - Sets if the World is in Hardcore-Mode <br>
    - Default: false <br>

  * ```setGenerateStructures(Boolean) ``` <br>
    - Sets if Structures(Villages, Pyramids...) can generate <br>
    - Default: true <br>

  * ```addStructureGeneratorConfig(StructureGenerator, spacing, seperation) ``` <br>
    - Sets which Structure can generate and how often <br>
    - Example: StructureGenerator.VILLAGE <br>
    - Note: 2*spacing-separation has to be greater 1 <br>
    - Note: If you don't add an StructureGenerator here, the World can't generate this Structure <br>

  * ```loadDefaultStructureGeneratorConfig() ``` <br>
    - Loads the default settings for each StructureGenerator <br>
    - A list can be found [here]

  * ```setTopBedrock(Block) ``` <br>
    - Sets which Block will be used for Bedrock on the Top <br>
    - Example: Blocks.STONE, Blocks.BEDROCK <br>

  * ```setBottomBedrock(Block) ``` <br>
    - Sets which Block will be used for Bedrock on the Bottom <br>
    - Example: Blocks.STONE, Blocks.BEDROCK <br>

  * ```setDifficulty(EnumDifficulty) ``` <br>
    - Sets which Difficulty the World has <br>
    - Example: EnumDifficulty.EASY, EnumDifficulty.HARD  <br>

  * ```setGameMode(EnumGamemode) ``` <br>
    - Sets which Gamemode the World has <br>
    - Example: EnumGamemode.SURVIVAL, EnumGamemode.CREATIVE  <br>

  * ```setAllowMonsters(Boolean) ``` <br>
    - Sets if Monsters can spawn in the World <br>
    - Default: true  <br>

  * ```setAllowAnimals(Boolean) ``` <br>
    - Sets if Animals can spawn in the World <br>
    - Default: true  <br>

  * ```setGenerateDecoration(Boolean) ``` <br>
    - Sets if Decoration can spawn in the World <br>
    - Default: true  <br>
    
    
    
    
    
## Methods to get Information about World-Settings:
  * ```getWorldSetting() ``` <br>
    - Returns which GeneratorSetting is used <br>
    - Note: If you use ```setCustomWorldGeneration(<...>)``` this will not return the real GeneratorSetting <br>

  * ```getCustomWorldSetting() ``` <br>
    - Returns which Custom GeneratorSetting is used, null if not set before <br>

  * ```hasCustomWorldSetting() ``` <br>
    - Returns true if a Custom WorldSetting is used <br>

  * ```getSeed() ``` <br>
    - Returns the used Seed <br>

  * ```isHardcore() ``` <br>
    - Returns true if the World is in Hardcore-Mode <br>

  * ```canGenerateStructures() ``` <br>
    - Returns true if the World can generate Structures <br>
   
  * ```getStructuresGeneratorConfigs() ``` <br>
    - Returns a Map with the StructureGenerator(Village, Pyramid...) as Key and the StructureSettingsFeature as Value <br>
    - The StructureSettingsFeature has 3 Values: <be>
      - a() is the spacing
      - b() is the seperating
      - c() is the salt (should not get changed)
      
  * ```getTopBedrock() ``` <br>
    - Returns the Block that will be used for the Top Bedrock <br>
    - Note: It will only generate Top Bedrock if the DimensionSetting has a valid height for it<br>

  * ```getBottomBedrock() ``` <br>
    - Returns the Block that will be used for the Bottom Bedrock <br>
    - Note: It will only generate Bottom Bedrock if the DimensionSetting has a valid height for it<br>

  * ```getDifficulty() ``` <br>
    - Returns which EnumDifficulty will be in the World <br>

  * ```getGameMode() ``` <br>
    - Returns which EnumGamemode will be in the World <br>

  * ```getAllowMonsters() ``` <br>
    - Returns true if Monsters can spawn in the World <br>

  * ```getAllowAnimals() ``` <br>
    - Returns true if Animals can spawn in the World <br>

  * ```canGenerateDecoration() ``` <br>
    - Returns true if Decoration(Trees, Ores...) can spawn in the World <br>
      
      
[Here]: https://minecraft.gamepedia.com/Custom#Structure_defaults
[CustomWorldSetting]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/blob/main/Methods/CustomWorldGeneration.md
