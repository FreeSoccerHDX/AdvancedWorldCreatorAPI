## If you want to use Datapack's to generate a custom World you can use the following Methods to use them with the API.

Currently there are only 2 Methods you can use to import Datapack-Configurations:
  - Custom Biomes
  - World Generation Features-Configured

## Example:
### Custom Biomes:
(Used in creation Process of the World Chunk Manager)
```
  RegisteredCustomBiome custom_biome = AdvancedAdvancedWorldCreatorAPI.getDataPackFeatureResolver().getDatapackBiome(Prekey, Name);
  // If you press F3 you can see the current Biome (for example: 'minecraft:plains')
  // Prekey: default 'minecraft', but for Datapacks you have to use the Filename in the Datapack under "data"
  // Name: The Filename of the Biome (without .json)
  // For example: world\datapacks\MyDatapack\data\mydp\worldgen\biome\mycustom_datapack_biome.json
  // 		Prekey: mydp
	//    Name: mycustom_datapack_biome

```

### World Generation Features-Configured:
(Used in creation Process of a Custom Biome)
```
  WorldGenFeatureConfigured<?, ?> feature = AdvancedAdvancedWorldCreatorAPI.getDataPackFeatureResolver().getDatpackGenFeature(Prekey, Name);
	// Prekey: default 'minecraft', but for Datapacks you have to use the Filename in the Datapack under "data"
  // Name: The Filename of the Feature (+Path starting from configured_feature) (without .json)
	// For example: world\datapacks\MyDatapack\data\mydp\worldgen\configured_feature\mycustom_datapack_feature.json
	// 		Prekey: mydp
	//    Name: mycustom_datapack_feature
	// For example: world\datapacks\MyDatapack\data\mydp\worldgen\configured_feature\between\mycustom_datapack_biome.json
  // 		Prekey: mydp
	//    Name: between/mycustom_datapack_feature
```
  
