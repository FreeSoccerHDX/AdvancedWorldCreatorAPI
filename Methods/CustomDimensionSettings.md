With CustomDimensionSettings you can set special Rules for your Custom World.

You need CustomDimensionSettings for creating the Custom World.

But you can also use a Vanilla one from:
```
Overworld: CustomDimensionSettings.getOverworldSettings()
Nether: CustomDimensionSettings.getNetherSettings()
End: CustomDimensionSettings.getEndSettings()

```

How to create:
```
CustomDimensionSettings settings = new CustomDimensionSettings(
fixedtime, /* OptionalLong.of(0-24000) or OptionalLong.empty() for normal time-rotating*/
hasskylight, /* Whether the dimension has skylight access or not. */
hasceiling, /*  Whether the dimension has a bedrock ceiling or not. */
ultrawarm, /* Whether the dimensions behaves like the nether (water evaporates and sponges dry) or not. Also causes lava to spread faster and thinner. */
natural, /* When false, compasses spin randomly. When true, nether portals can spawn zombified piglins */
coordscale, /* Used for Nether-Portal teleportation */
createDragonBattle, /* If a Dragon-Battle should generate at X: 0 and Z: 0 */
piglinSafe, /* Whether piglins shake and transform to zombified piglins */
bedWorks, /* Whether players can use a bed to sleep. */
respawnAnchorWorks, /* Whether players can charge and use respawn anchors. */
hasraids, /* Whether players with the Bad Omen effect can cause a raid. */
maxY, /* Not used yet. (1.17 Feature) */
infiniburn, 
/* Tag of which Blocks can burn infinity, One of:
new MinecraftKey("minecraft","infiniburn_overworld")
new MinecraftKey("minecraft","infiniburn_nether")
new MinecraftKey("minecraft","infiniburn_end")
*/
dimmankey, 
/* Tag of what World-Effects exist, One of:
new MinecraftKey("minecraft","overworld") -> Clouds, Sun, Stars, Moon
new MinecraftKey("minecraft","the_nether") -> thick fog
new MinecraftKey("minecraft","the_end") -> dark sky (ignoring sky and fog color)
*/
ambientlight /* between 0.0 - 1.0, how much light is in caves*/
);

```
