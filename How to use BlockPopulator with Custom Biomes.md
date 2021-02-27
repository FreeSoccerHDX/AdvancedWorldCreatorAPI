## If you want to use BlockPopulators(Bukkit) to generate Custom Structures in Custom Biomes you can't use the Bukkit .getBiome() Method (it will return null)

For this you can check if the Location is a RegisteredCustomBiome with this Method:
```

AdvancedWorldCreatorAPI.isCustomBiome(RegisteredCustomBiome, Location)
or
AdvancedWorldCreatorAPI.isCustomBiome(RegisteredCustomBiome, World, x, y, z)

```

This will return true (if it is the RegisteredCustomBiome) or false (if it is not the RegisteredCustomBiome).
