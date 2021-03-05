## If you want to use Datapack's to generate a custom World you can use the following Methods to use them with the API.

### Currently there are only 3 Methods you can use to import Datapack-Configurations:

Where to use those Methods:
```
DataPackResolver resolver = AdvancedWorldCreatorAPI.getDataPackResolver();
resolver.getStructureFeature...
resolver.getWorldGenFeature...
resolver.getBiome...
```

#### StructureFeatures: <br>
  ```getStructureFeature(String prekey, String name)``` <br>
  Vanilla Examples: Stronghold, Fortress, Villages <br>
  Add to your Custom Biome with: ```addCustomWorldStructureFeature(StructureFeature)``` <br>
  

#### WorldGenFeatures: <br>
  ```getWorldGenFeature(String prekey, String name)``` <br>
  Vanilla Examples: Diamond-Ore, Jungle Tree, Flowers <br>
  Add to your Custom Biome with: ```addWorldGenDecorationFeature(WorldGenFeature)``` <br>
  

#### Biomes: <br>
  ```getBiome(String prekey, String name)``` <br>
  Vanilla Examples: Plains, Desert, Ocean <br>
  Add to your Biome Generator with: ```addBiome(Biome<,...>)``` <br>



## Getting Prekey and Name <br>
  The Prekey is the Namespace of a MinecraftKey. Vanilla-Default: 'minecraft' <br>
  The Name is the Key of a MinecraftKey. Vanilla-Default: 'Plains', 'Desert', 'spring_lava' <br>
  
  In Datapack's the Prekey is a part of the path to the configuration: <br>
  For example: ```world\datapacks\MyDatapack\data\custom_name\worldgen\biome\mycustom_datapack_biome.json``` <br>
         Prekey: custom_name <br>
         Name:   mycustom_datapack_biome <br>
         
  If you have sub-folders in the specific folders you have to add them to the Name. <br>
  For example: ```world\datapacks\MyDatapack\data\custom_name\worldgen\biome\sub_one_folder\mycustom_datapack_biome.json``` <br>
         Prekey: custom_name <br>
         Name:   sub_one_folder/mycustom_datapack_biome <br>


## Other Methods:
``` 
getListedStructureFeatures()
getListedWorldGenFeature()
getListedBiomes()
```
They will return a List of MinecraftKey's, so for example you could add all listed GenFeatures with the MinecraftKey-Namespace as Prekey and the MinecraftKey-Key as Name. <br>
### Notice: Not all Features are listed and sometimes new show up randomly without changing anything in the world. Those Methods are mostly useful when searching new Features.



