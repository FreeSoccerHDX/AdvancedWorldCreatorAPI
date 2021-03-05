## This Tutorial will show you how to create a default Overworld with no Custom Settings.

### First we need the [dvancedCreator]:
```
AdvancedCreator ac = new AdvancedCreator("myoverworld");
// We use this Constructor to set the Worldname to 'myoverworld'
// This will alsorate a random Seed for this World
```
With the AdvancedCreator we could change the Seed or some Settings like Gamemode or Difficulty.


### The last thing we need is a [DimensionManager]:
```
DimensionManager dm = CustomDimensionSettings.getOverworldSettings();
// The Method getOverworldSettings() in CustomDimensionSettings will return the default Settings for a normal Overworld
```
An DimensionManager is used to set specific Settings like Ambientlight, BedWorks, RespawnAnchorWorks or even if a DragonBattle should generate


### The next thing we need is a [BiomeGenerator]:
```
OverworldBiomeGenerator obg = new OverworldBiomeGenerator.CREATOR(ac.getSeed()).create();
// We use the getSeed() Method from the AdvancedCreator, to get the same Biome-Layout with the same Seed from the AdvancedCreator
```
Its used to set a specific Biome for a specific Location
Instead of OverworldBiomeGenerator we could also use CheckerBoardBiomeGenerator to generate a CheckerBoard, but for that would need to add a Biome.



## Now we can use the 3 Objects to generate a default Overworld with no specific Custom Settings:
```
AdvancedWorldCreatorAPI.createWorld(ac, dm, obg);
// The first Argument is the AdvancedCreator
// The second Argument is the DimensionManager
// The third Argument is the BiomeGenerator

This will also return the World that got generated if successful
```
https://static.wikia.nocookie.net/minecraft_gamepedia/images/3/3f/Beta.png/revision/latest?cb=20191215152001


[DimensionManager]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/blob/main/Methods/CustomDimensionSettings.md
[BiomeGenerator]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/tree/main/Methods/BiomeGenerators
[dvancedCreator]: https://github.com/FreeSoccerHDX/AdvancedWorldCreatorAPI/blob/main/Methods/AdvancedCreator.md
