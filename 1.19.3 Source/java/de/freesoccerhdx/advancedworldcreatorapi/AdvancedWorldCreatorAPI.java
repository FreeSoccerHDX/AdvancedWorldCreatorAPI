package de.freesoccerhdx.advancedworldcreatorapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeDecoration;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeDecorationPopulator;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeDecorationType;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderLines;
import de.freesoccerhdx.advancedworldcreatorapi.craft.CreateWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.bukkit.Bukkit;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.block.data.CraftBlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;


import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;

/**
 * Represents the main class of the AdvancedWorldCreatorAPI
 */
public final class AdvancedWorldCreatorAPI extends JavaPlugin implements Listener {

    private static final boolean DEBUG = true;


    /**
     * @hidden This is a hidden method for javadocs
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        if(false && DEBUG) {

            GeneratorConfiguration generatorConfig = new GeneratorConfiguration(new NamespacedKey("my_cutom_namespace","the_key_to_use"));
            generatorConfig.setOverwriteSettingsIfExist(true);
            generatorConfig.setDefaultBlock(Material.DIAMOND_BLOCK);
            generatorConfig.getNoiseGeneration().setMinY(-256);
            generatorConfig.getNoiseGeneration().setHeight(256+320);

            //creator.setEnvironmentBuilder(EnvironmentBuilder.OVERWORLD);

            EnvironmentBuilder environmentBuilder = new EnvironmentBuilder(new NamespacedKey("my_cutom_environment","the_environment_to_use"));
            environmentBuilder.setOverwriteSettingsIfExist(true);
            environmentBuilder.setHeight(256+320);
            environmentBuilder.setMinY(-256);
            environmentBuilder.setLogicalHeight(256+320);
            /*
            GeneratorConfiguration genConf = GeneratorConfiguration.OVERWORLD;

            System.out.println("genConf: " + genConf);

            genConf.setOverwriteSettingsIfExist(true);
            genConf.setDefaultBlock(Material.GRAY_STAINED_GLASS);
            genConf.setDefaultFluid(Material.BLUE_STAINED_GLASS);
            genConf.getNoiseGeneration().setMinY(-320);
            genConf.getNoiseGeneration().setHeight(640);
            registerGeneratorConfiguration(genConf);


            EnvironmentBuilder envBuilder = EnvironmentBuilder.OVERWORLD;

            System.out.println("envBuilder: " + envBuilder);

            envBuilder.setOverwriteSettingsIfExist(true);
            envBuilder.setAmbientLight(1f);
            envBuilder.setHeight(640);
            envBuilder.setMinY(-320);
            envBuilder.setCreateDragonFight(true);
            registerCustomEnvironment(envBuilder);
            */
        }

    }

    /**
     * @hidden This is a hidden method for javadocs
     */
    @EventHandler
    private void onCMD(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        String cmd = args[0];

        if(cmd.equalsIgnoreCase("/registry")) {
            e.setCancelled(true);

            MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
            Registry<PlacedFeature> placedFeatureRegistry = server.registries().compositeAccess().registryOrThrow(Registries.PLACED_FEATURE);

            placedFeatureRegistry.entrySet().forEach((entry) -> {
                Bukkit.broadcastMessage("BiomeDecoration " + entry.getKey().location().getPath().toUpperCase(Locale.ENGLISH) + " = new BiomeDecoration(\""+entry.getKey().location().getNamespace()+"\", \""+entry.getKey().location().getPath()+"\");");
            });

        }

        if(cmd.equalsIgnoreCase("/testnewcreation")) {
            e.setCancelled(true);

            AdvancedWorldCreator creator = new AdvancedWorldCreator("testworld");
            EnvironmentBuilder environmentBuilder = new EnvironmentBuilder(new NamespacedKey("my_cutom_environment","the_environment_to_use"));
            environmentBuilder.setOverwriteSettingsIfExist(true);
            environmentBuilder.setHeight(384+64);
            environmentBuilder.setMinY(-128);
            environmentBuilder.setLogicalHeight(384+64);
            environmentBuilder.setAmbientLight(0.0f);
            environmentBuilder.setBedWorks(false);
            environmentBuilder.setUltraWarm(true);
            environmentBuilder.setRespawnAnchorWorks(true);
            environmentBuilder.setNatural(false);
            creator.setEnvironmentBuilder(environmentBuilder);

            GeneratorConfiguration generatorConfig = new GeneratorConfiguration(new NamespacedKey("my_cutom_genconfig","the_genconfig_to_use"));
            //generatorConfig.setDefaultBlock(Material.GRAY_STAINED_GLASS);
            //generatorConfig.setDefaultFluid(Material.BLUE_STAINED_GLASS);
            generatorConfig.setNoiseRouterData(NoiseRouterData.OVERWORLD);
            generatorConfig.setSeaLevel(64);
            generatorConfig.getNoiseGeneration().setMinY(-128);
            generatorConfig.getNoiseGeneration().setHeight(384+64);
            creator.setGeneratorConfiguration(generatorConfig);

            BiomeCreator biomeCreator = new BiomeCreator("test", "test");
            biomeCreator.setGrassColor(new Color(0, 0, 255));
            biomeCreator.setFogColor(new Color(255,0,0));
            biomeCreator.setWaterColor(new Color(0,255,0));
            biomeCreator.addEntityConfiguration(EntityType.ALLAY, 200, 1, 2);

            // You can use one of the following PlacementType's to influence the y-coordinate of the placement
            BiomeDecorationPopulator.PlacementType placementType_heightMap = new BiomeDecorationPopulator.HeightmapPlacementType(HeightMap.MOTION_BLOCKING);
            BiomeDecorationPopulator.PlacementType placementType_range = new BiomeDecorationPopulator.RangePlacementType(10, 10);

            // Use at least one or more of the PlacementType's like in the following example
            biomeCreator.addCustomDecorationPopulator(BiomeDecorationType.TOP_LAYER_MODIFICATION, new BiomeDecorationPopulator(new BiomeDecorationPopulator.PlacementType[]{placementType_heightMap}) {
                @Override
                public void populateDecoration(WorldModifier worldModifier, int x, int y, int z) {
                    // worldModifier only has a few methods, but you can use them to modify the world
                    // one Method allows to get a BlockData at a specific position
                    // the other Method allows to set a BlockData at a specific position
                    // Note: NEVER! try to set Blocks via Bukket-API at this point, because it will cause a lot of lag
                    //       and will probably cause a crash
                    // The following example will check the Block at the given position and will set it to a Diamond Block
                    // if the Block is AIR
                    // x and z are the lowest x and z coordinates of the chunk that is currently being generated
                    // y is the y coordinate which is modified by the PlacementTypes
                    BlockData blockData = worldModifier.getBlockData(x,y,z);
                    if(blockData.getMaterial() == Material.AIR) {
                        worldModifier.setBlockData(x, y, z, Material.DIAMOND_BLOCK.createBlockData());
                    }
                }
            });


            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_STRUCTURES, BiomeDecoration.MONSTER_ROOM);


            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_DIRT);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_GRAVEL);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_COAL_UPPER);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_IRON_MIDDLE);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_GOLD);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_DIAMOND);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.ORE_LAPIS);
            biomeCreator.addBiomeDecoration(BiomeDecorationType.UNDERGROUND_ORES, BiomeDecoration.UNDERWATER_MAGMA);


            biomeCreator.addBiomeDecoration(BiomeDecorationType.VEGETAL_DECORATION, BiomeDecoration.PATCH_TALL_GRASS_2);


            BiomeCreator.CustomBiome customBiome = biomeCreator.createBiome(true);
            BiomeProviderLines.Builder builder = new BiomeProviderLines.Builder(10, true);
            for(Biome biome : Biome.values()) {

                if(biome != Biome.CUSTOM) {
                    System.out.println("Adding Biome: " + biome.getKey().getKey());
                    builder.addBiome(biome);
                }

            }
            builder.addBiome(customBiome);
            BiomeProviderLines biomeProvider = builder.create(); //new BiomeProviderLines.Builder(200, true).addBiome(customBiome).addBiome(Biome.PLAINS)/*.addBiome(Biome.OCEAN)*/.create();
            creator.setAdvancedBiomeProvider(biomeProvider);

            /*
            creator.biomeProvider(new BiomeProvider() {
                @Override
                public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    return Biome.PLAINS;
                }

                @Override
                public List<Biome> getBiomes(WorldInfo worldInfo) {
                    return List.of(Biome.PLAINS);
                }
            });
             */

            World world = CreateWorld.createWorld(creator);
            p.teleport(world.getSpawnLocation());
            p.sendMessage("§a Erfolgreich neue Welt erstellt!");
        }

        if(cmd.equalsIgnoreCase("/tpworld")){
            e.setCancelled(true);
            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
        }

        if(cmd.equalsIgnoreCase("/unloadworld")){
            e.setCancelled(true);

            boolean suc = Bukkit.unloadWorld(args[1],false);
            p.sendMessage("§a Success ? " + suc);

        }

        if(cmd.equalsIgnoreCase("/listworlds")){
            e.setCancelled(true);

            for(World world : Bukkit.getWorlds()){
                Bukkit.broadcastMessage("§aWorld: " + world.getName());
            }

        }


        if(cmd.equalsIgnoreCase("/testnewversion")) {
            e.setCancelled(true);

            HashMap<String, Object> jsonmap = new HashMap<>();

            jsonmap.put("legacy_random_source", false);
            jsonmap.put("ore_veins_enabled", true);
            jsonmap.put("aquifers_enabled", true);
            jsonmap.put("disable_mob_generation", false);
            jsonmap.put("sea_level", 63);
            jsonmap.put("spawn_target", JsonParser.parseString("[{\"continentalness\":[-0.11,1.0],\"depth\":0.0,\"erosion\":[-1.0,1.0],\"humidity\":[-1.0,1.0],\"offset\":0.0,\"temperature\":[-1.0,1.0],\"weirdness\":[-1.0,-0.16]},{\"continentalness\":[-0.11,1.0],\"depth\":0.0,\"erosion\":[-1.0,1.0],\"humidity\":[-1.0,1.0],\"offset\":0.0,\"temperature\":[-1.0,1.0],\"weirdness\":[0.16,1.0]}]")); // ParameterPoint.CODEC

            jsonmap.put("surface_rule", JsonParser.parseString("{\"type\":\"minecraft:block\",\"result_state\":{\"Name\":\"minecraft:bedrock\"}}")); // RuleSource.CODEC
            //jsonmap.put("noise_router", JsonParser.parseString("{\"test\":\"noise_router\"}")); // NoiseRouter.CODEC
            //jsonmap.put("default_fluid", "minecraft:water"); // BlockState.CODEC
            //jsonmap.put("default_block", "minecraft:stone"); // BlockState.CODEC
            jsonmap.put("noise", JsonParser.parseString("{\"test\":\"noise\"}")); // NoiseSettings.CODEC


            Lightable light = (Lightable) Bukkit.createBlockData(Material.REDSTONE_ORE);
            light.setLit(true);
            BlockState blockState = Blocks.REDSTONE_ORE.defaultBlockState();
            blockState = ((CraftBlockData) light).getState();

            JsonElement block = BlockState.CODEC.encodeStart(JsonOps.INSTANCE, blockState).resultOrPartial(System.out::println).get();

            Bukkit.broadcastMessage("§6: BlockJSON: " + block);
            //jsonmap.put("default_block", block); // BlockState.CODEC
            /*
            *
            * https://minecraft.fandom.com/wiki/Custom_world_generation
            * https://minecraft.fandom.com/wiki/Density_function#interpolated

            *
            * */

            HashMap<String, Integer> noise = new HashMap<>();
            noise.put("size_vertical", 2);
            noise.put("size_horizontal", 1);
            noise.put("height", 384);
            noise.put("min_y", -64);

            jsonmap.put("noise", noise);

            String[] routernames = new String[]{"vein_gap", "vein_ridged", "vein_toggle", "final_density", "initial_density_without_jaggedness", "ridges", "depth", "erosion", "continents", "vegetation", "temperature", "lava", "fluid_level_spread", "fluid_level_floodedness", "barrier"};

            HashMap<String, Object> aquiferLava = new HashMap<>();
            aquiferLava.put("firstOctave", -1);
            aquiferLava.put("amplitudes", new double[]{1.0});

            HashMap<String, Object> noise_router = new HashMap<>();
            for (String routername : routernames) {
                HashMap<String,Object> noisetype = new HashMap<>();
                noisetype.put("type", "noise");
                noisetype.put("noise", aquiferLava);
                noisetype.put("xz_scale", 1.0);
                noisetype.put("y_scale", 1.0);

                noise_router.put(routername, noisetype); // NoiseRouter.CODEC
            }
            jsonmap.put("noise_router", noise_router);

            Gson gson = new Gson();

            HashMap<String,String> default_block = new HashMap<>();
            default_block.put("Name","minecraft:bedrock");

            HashMap<String,String> default_fluid = new HashMap<>();
            default_fluid.put("Name","minecraft:water");

            jsonmap.put("default_block", default_block);
            jsonmap.put("default_fluid", default_fluid);

            String json = gson.toJson(jsonmap);
            JsonElement element = JsonParser.parseString(json);
            NoiseGeneratorSettings test0 = NoiseGeneratorSettings.DIRECT_CODEC.decode(JsonOps.INSTANCE, element).getOrThrow(false, System.err::println).getFirst();
            Bukkit.broadcastMessage("§aNoiseGeneratorSettings: " + test0);

            CraftServer craftServer = (CraftServer) Bukkit.getServer();
            MinecraftServer server = craftServer.getServer();
            WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = (WritableRegistry<NoiseGeneratorSettings>) server.registryAccess().registryOrThrow(Registries.NOISE_SETTINGS);
            ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("minecraft", "overworld"));
            NMSHelper.unfreezeRegistry(registryGeneratorSettings);
            Holder.Reference<NoiseGeneratorSettings> holder = registryGeneratorSettings.register(generatorSettingBaseResourceKey, test0, Lifecycle.stable());

            Bukkit.broadcastMessage("§aNoiseGeneratorSettings-Key: " + holder.key());


            registryGeneratorSettings.entrySet().forEach(entry -> {
                Bukkit.broadcastMessage("§aNoiseGeneratorSettings-Entry: " + entry.getKey() + " - " + entry.getValue().toString().substring(0,20));
            });

        }

    }


    /**
     * Creates the {@link World} with the provided {@link AdvancedWorldCreator}
     *
     * Modified version of {@link CraftServer#createWorld(WorldCreator)}
     * @param creator The Custom {@link AdvancedWorldCreator}
     * @return The {@link World} that will be created or exists already
     *
     */
    public static World createWorld(AdvancedWorldCreator creator) {
        return CreateWorld.createWorld(creator);
    }

    /**
     * Allows to create a custom Environment or even override an existing one
     *
     * @param builder The {@link EnvironmentBuilder} to create the Environment
     * @return true if creating and registering of the Environment was successful
     */
    public static boolean registerCustomEnvironment(EnvironmentBuilder builder) {
        return CreateWorld.registerCustomEnvironment(builder);
    }

    /**
     * Simple Method to generate GeneratorSettingBase (How the Terrain will generate and which structures)
     *
     * @param generatorConfiguration The {@link GeneratorConfiguration} to generate the GeneratorSettingBase
     * @return true if creating and registering of the GeneratorConfiguration was successful
     */
    public static boolean registerGeneratorConfiguration(GeneratorConfiguration generatorConfiguration) {
        return CreateWorld.registerGeneratorConfiguration(generatorConfiguration);
    }

}
