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
## Methods for 'CustomTree':
  - setLeaveRadius(Blocks) <br>
    Radius of Leaves in X-/Z-Direction
  - setLeaveOffset(Blocks) <br>
    Blocks up or down to spawn Leaves
  - setLeveHeight(Blocks) <br>
    How many Blocks the Leaves will generate downwards
  - setWoodHeight(Blocks) <br>
    How many Logs will generate upwards
  - setRandomWoodHeightA(Blocks) <br>
    Random Number of how High the Logs can spawn
  - setRandomWoodHeightB(Blocks) <br>
    Random Number of how High the Logs can spawn
 
 ## Extra-Methods for Jungle-Trees:
  - setGenerateCocoa(should_generate) default=true
  - setGenerateVinesOnLog(should_generate) default=true
  - setGenerateVinesOnleaves(should_generate) default=true
  
 ## Extra-Methods for Spruce-Trees:
  - setChangeGround(should_change, Block)
    Block is what Material the Tree will generate below (Defualt: Podzol) <br>
    You can set this to Blocks.DIAMOND_BLOCK or Blocks.STONE for example

Note1: Not every CustomTree has all Methods (For example: Acacia has no Leave-Height) <br>
Note2: Some Methods in CustomTree can have 2 Arguments <br>
For example: MegaSpruceTree's Method 'setLeaveHeight' takes the Argument 'min': what's the lowest value and 'spread': which can increase the size

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
## Extra-Methods of 'CustomForest':
  - addTree(CustomTree, Chance) <br>
    You can add more Trees to your Forest that will generate with the specific Chance(0.0-1.0) <br>
    Note: All CustomTrees together should have a maximum chance of 1.0  <br> 
    The Default CustomTree of the Constructor will have the the left amount of Chance <br> 
    For example:
      Adding a CustomTree with the Chance of 0.5 will cause the first CustomTree to have a chance of 0.5 to spawn
    
    
    
