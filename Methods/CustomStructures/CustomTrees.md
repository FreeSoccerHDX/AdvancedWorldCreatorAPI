### CustomTree's are like the Vanilla Tree's that will normaly spawn. <br>
##### Until you change one of those:
  - Log-Hight
  - Leaves-Size
  - Log-Type
  - Leaves-Type
  - ...
  
Currently Trees you can change:
  - Acacia
  - Birch
  - DarkOak
  - FancyOak
  - Mega Jungle
  - Mega Pine
  - Mega Spruce
  - Oak
  - Pine
  - Small Jungle
  - Spruce
  

For example:
``` 
CustomOakTreeStructure custom_tree = new CustomOakTreeStructure(Name); 

```

If you just add the 'custom_tree' to your CustomBiome you will see that they will spawn once every Chunk at the same position.
To avoid that you have to create a 'CustomForest' and add this to your CustomBiome
```
CustomForest custom_forest = new CustomForest(Name, custom_tree, default_amount, extra_chance, extra_count);
//Name(limited to a-z, 0-9) = Structurename
//custom_tree = Your Tree object
//default_amount = how many will generate by default
//extra_chance(0.0 - 1.0) = chance to generate extra trees
//extra_count = count extra trees to generate

```
