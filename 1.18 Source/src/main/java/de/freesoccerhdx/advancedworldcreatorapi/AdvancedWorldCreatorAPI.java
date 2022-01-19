package de.freesoccerhdx.advancedworldcreatorapi;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.biome.AdvancedBiomeProvider;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderDefault;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderLines;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.BiomeProviderMultiNoise;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_18_R1.generator.CraftWorldInfo;
import org.bukkit.craftbukkit.v1_18_R1.generator.CustomWorldChunkManager;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

public final class AdvancedWorldCreatorAPI extends JavaPlugin implements Listener {

    private static final boolean DEBUG = false;
    public static AdvancedWorldCreatorAPI main = null;


    @Override
    public void onEnable() {
        main = this;

        if(DEBUG) Bukkit.getPluginManager().registerEvents(this,this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    private void onCMD(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        String cmd = args[0];

        if(cmd.equalsIgnoreCase("/testMultiNoise")) {
            e.setCancelled(true);

            BiomeProviderMultiNoise.Builder builder = new BiomeProviderMultiNoise.Builder();
            builder.addBiome(Biome.PLAINS, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));
            builder.addBiome(Biome.DARK_FOREST, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));
            builder.addBiome(Biome.DEEP_COLD_OCEAN, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));

            BiomeProviderMultiNoise multiNoise = builder.create();

            for(int i = 0; i < 100; i++) {
                int x = (int) (Math.random() * 1000);
                int z = (int) (Math.random() * 1000);
                System.out.println("For x="+x+", z="+z);
                multiNoise.getBiome(new WorldInfo() {
                    @Override
                    public String getName() {
                        return "world";
                    }

                    @Override
                    public UUID getUID() {
                        return null;
                    }

                    @Override
                    public World.Environment getEnvironment() {
                        return null;
                    }

                    @Override
                    public long getSeed() {
                        return 0;
                    }

                    @Override
                    public int getMinHeight() {
                        return 0;
                    }

                    @Override
                    public int getMaxHeight() {
                        return 0;
                    }
                }, x, 64, z);
            }


        }


        if(cmd.equalsIgnoreCase("/testRegistry")){
            e.setCancelled(true);

            CraftServer craftServer = (CraftServer) Bukkit.getServer();
            RegistryAccess registryAccess = craftServer.getServer().registryAccess();

            Registry<Codec<? extends BiomeSource>> noiseRegistryA = registryAccess.registryOrThrow(Registry.BIOME_SOURCE_REGISTRY);
            for(Map.Entry<ResourceKey<Codec<? extends BiomeSource>>, Codec<? extends BiomeSource>> data : noiseRegistryA.entrySet()){
                ResourceLocation resourceLocation = data.getKey().location();
                Codec<? extends BiomeSource> biomesource = data.getValue();

                Bukkit.broadcastMessage("§cName: " + resourceLocation.toString() + " -> " + biomesource.getClass());
            }

            Registry<NormalNoise.NoiseParameters> noiseRegistry = registryAccess.registryOrThrow(Registry.NOISE_REGISTRY);

            for(Map.Entry<ResourceKey<NormalNoise.NoiseParameters>, NormalNoise.NoiseParameters> data : noiseRegistry.entrySet()){
                ResourceLocation resourceLocation = data.getKey().location();
                NormalNoise.NoiseParameters noiseParameters = data.getValue();

                Bukkit.broadcastMessage("§6Name: " + resourceLocation.toString() + " -> " + noiseParameters.firstOctave() + " list: " + noiseParameters.amplitudes());
            }

        }

        if(cmd.equalsIgnoreCase("/custombiome")){
            e.setCancelled(true);



            BiomeCreator biomeCreator = new BiomeCreator("firstname","secondname");
            biomeCreator.setWaterColor(new java.awt.Color(76, 255, 255));
            biomeCreator.setGrassColor(new java.awt.Color(136, 0, 0));
            biomeCreator.setSkyColor(new java.awt.Color(1, 73, 51));
            biomeCreator.setWaterFogColor(new java.awt.Color(255, 255, 255));


            BiomeCreator.CustomBiome customBiome = biomeCreator.createBiome(true);


            World world = p.getWorld();
            CraftWorld craftWorld = (CraftWorld) world;

            for(int x = -8; x < 8; x++){
                for(int y = -8; y < 8; y++){
                    for(int z = -8; z < 8; z++){
                        craftWorld.setBiome(p.getLocation().getBlockX()+x,p.getLocation().getBlockY()+y,p.getLocation().getBlockZ()+z,customBiome.getNMSBiome());
                    }
                }
            }

        }



        if(cmd.equalsIgnoreCase("/tpw")){
            e.setCancelled(true);

            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());

        }

        if(cmd.equalsIgnoreCase("/unloadcreatorworld")){
            e.setCancelled(true);

            boolean suc = Bukkit.unloadWorld("testnewcreator",false);
            p.sendMessage("§a Success ? " + suc);

        }


        if(cmd.equalsIgnoreCase("/listworlds")){
            e.setCancelled(true);

            for(World world : Bukkit.getWorlds()){
                Bukkit.broadcastMessage("§aWorld: " + world.getName());
            }

        }


        if(cmd.equalsIgnoreCase("/testnewcreator")){
            e.setCancelled(true);


            List<BiomeCreator.CustomBiome> customBiomes = new ArrayList<>();
            for(int i = 0; i < 255; i++){
                BiomeCreator biomeCreator = new BiomeCreator("firstname","colorbiome_"+i);
                java.awt.Color col = new java.awt.Color(i % 50, i % 75, i % 99);
                biomeCreator.setWaterColor(col);
                biomeCreator.setGrassColor(col);
                biomeCreator.setSkyColor(col);
                biomeCreator.setWaterFogColor(col);

                // biomeCreator.createBiome(true);
                BiomeCreator.CustomBiome customBiome = biomeCreator.createBiome(true);
                customBiomes.add(customBiome);
            }
            Collections.shuffle(customBiomes);


            BiomeCreator biomeCreator = new BiomeCreator("firstname","secondname");
            biomeCreator.setWaterColor(new java.awt.Color(76, 255, 255));
            biomeCreator.setGrassColor(new java.awt.Color(136, 0, 0));
            biomeCreator.setSkyColor(new java.awt.Color(1, 73, 51));
            biomeCreator.setWaterFogColor(new java.awt.Color(255, 255, 255));

            BiomeCreator.CustomBiome customBiome = biomeCreator.createBiome(true);


            AdvancedWorldCreator creator = new AdvancedWorldCreator("testnewcreator");
            creator.seed(123456789L);
            boolean hasSkylight = false;
            boolean hasCeiling = true;
            boolean ultraWarm = true;
            boolean natural = false;
            boolean createDragonFight = true;
            boolean piglinSafe = false;
            boolean bedWorks = true;
            boolean respawnAnchorWorks = true;
            boolean hasRaids = true;
        //    creator.setEnvironmentBuilder(new EnvironmentBuilder(null, hasSkylight, hasCeiling, ultraWarm, natural, 1.0, createDragonFight, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, -128, 256+128, 256+128, Tag.INFINIBURN_OVERWORLD.getKey(), NamespacedKey.minecraft("overworld"), 1.0f));
            GeneratorConfiguration generatorConfig = new GeneratorConfiguration();
            generatorConfig.setDefaultBlock(Material.DIAMOND_BLOCK);
            generatorConfig.getNoiseGeneration().setMinY(-128);
            generatorConfig.getNoiseGeneration().setHeight(256+128);
        //    creator.setGeneratorConfiguration(generatorConfig);

            /*
            creator.setAdvancedBiomeProvider(new AdvancedBiomeProvider() {
                @Override
                public Object getBiome(WorldInfo worldInfo, int x, int y, int z) {
                    if(x > z) {
                        return customBiome;
                    }
                    return org.bukkit.block.Biome.OCEAN;
                }

                @Override
                public List<Object> getBiomes(WorldInfo worldInfo) {
                    List<Object> objects = new ArrayList<>();
                    objects.add(customBiome);
                    objects.add(org.bukkit.block.Biome.OCEAN);

                    return objects;
                }
            });
            */
            /*
            WritableRegistry<Biome> registry = ((CraftServer)getServer()).getServer().registryHolder.ownedRegistryOrThrow(Registry.BIOME_REGISTRY);
            Biome biomeOcean = CraftBlock.biomeToBiomeBase(registry, org.bukkit.block.Biome.OCEAN);
            Biome biomePlains = CraftBlock.biomeToBiomeBase(registry, org.bukkit.block.Biome.PLAINS);
            Biome biomeSavanna = CraftBlock.biomeToBiomeBase(registry, org.bukkit.block.Biome.SAVANNA);
            Biome biomeBadlands = CraftBlock.biomeToBiomeBase(registry, org.bukkit.block.Biome.BADLANDS);
            p.sendMessage(biomeOcean+" & "+biomePlains+" & "+biomeSavanna+" & "+biomeBadlands);
            */


            BiomeProviderLines.Builder builder = new BiomeProviderLines.Builder(1,true);
            for(BiomeCreator.CustomBiome cb : customBiomes){
                builder.addBiome(cb);
            }

        //    creator.setAdvancedBiomeProvider(builder.create());


            BiomeProviderMultiNoise.Builder builderMultiNoise = new BiomeProviderMultiNoise.Builder();
            builderMultiNoise.addBiome(Biome.PLAINS, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));
            builderMultiNoise.addBiome(Biome.DARK_FOREST, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));
            builderMultiNoise.addBiome(Biome.DEEP_COLD_OCEAN, new BiomeProviderMultiNoise.NoiseData((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));

            creator.setAdvancedBiomeProvider(builderMultiNoise.create());


            BiomeProviderDefault.Builder builderDefault = new BiomeProviderDefault.Builder((bukkitbiome)->{
                if(bukkitbiome.getKey().toString().contains("ocean")){
                    return Biome.SWAMP;
                }
                if(bukkitbiome.getKey().toString().contains("plains")){
                    return Biome.BASALT_DELTAS;
                }
                return null;
            }, BiomeProviderDefault.PresetBiomes.OVERWORLD);

         //   creator.setAdvancedBiomeProvider(builderDefault.create());

/*
            BiomeProviderMultiNoise multiNoise = new BiomeProviderMultiNoise.Builder()
                    .addBiome(customBiome,new BiomeProviderMultiNoise.NoiseData(0.0f,0.0f,0.2f,0.5f,0.0f,0.02f,0.0f))
                    .addBiome(org.bukkit.block.Biome.OCEAN,new BiomeProviderMultiNoise.NoiseData(0.5f,0.02f,0.0f,0.0f,0.0f,0.0f,0.0f))
                    .create();
            creator.setAdvancedBiomeProvider(multiNoise);
*/
            creator.createWorld();



        }

    }


    /**
     * Creates the {@link World} with the provided {@link AdvancedWorldCreator}
     *
     * @param creator The Custom {@link AdvancedWorldCreator}
     * @return The {@link World} that will be created or exists already
     * */
    public static World createWorld(AdvancedWorldCreator creator) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        //Preconditions.checkState(!this.console.levels.isEmpty(), "Cannot create additional worlds on STARTUP");

        Validate.notNull(creator, "Creator may not be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        AdvancedBiomeProvider customBiomeProvider = creator.getAdvancedBiomeProvider();
        File folder = new File(craftServer.getWorldContainer(), name);
        World world = craftServer.getWorld(name);
        if (world != null) {
            return world;
        } else if (folder.exists() && !folder.isDirectory()) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        } else {
            if (generator == null) {
                generator = craftServer.getGenerator(name);
            }

            if (biomeProvider == null) {
                biomeProvider = craftServer.getBiomeProvider(name);
            }

            ResourceKey actualDimension;

            if(DEBUG) {
                Bukkit.broadcastMessage("§cOrdinal: " + creator.environment().ordinal());
                Bukkit.broadcastMessage("§aType: " + creator.environment());

                for (World.Environment environment : World.Environment.values()) {
                    Bukkit.broadcastMessage("§6" + environment + " -> " + environment.ordinal());
                }
            }

            switch(creator.environment().ordinal()) {
                case 0:
                    actualDimension = LevelStem.OVERWORLD;
                    break;
                case 1:
                    actualDimension = LevelStem.NETHER;
                    break;
                case 2:
                    actualDimension = LevelStem.END;
                    break;
                case 3:
                    if(creator.getEnvironmentBuilder() == null) throw new IllegalArgumentException("Selected Environment.CUSTOM but not specified an EnvironmentBuilder!");
                    actualDimension = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(name));
                    //WritableRegistry<LevelStem> levelStems = craftServer.getServer().registryAccess().ownedRegistryOrThrow(Registry.LEVEL_STEM_REGISTRY);
                    //levelStems.registerOrOverride(OptionalInt.empty(),actualDimension,LevelStem,Lifecycle);

                    //Registry.LEVEL_STEM_REGISTRY
                    break;
                default:
                    throw new IllegalArgumentException("Illegal dimension");
            }

            LevelStorageSource.LevelStorageAccess worldSession;
            try {
                worldSession = LevelStorageSource.createDefault(craftServer.getWorldContainer().toPath()).createAccess(name, actualDimension);
            } catch (IOException var23) {
                throw new RuntimeException(var23);
            }

            if(main.DEBUG) System.out.println("PRE PrimaryLevelData");
            PrimaryLevelData worlddata = null;//(PrimaryLevelData)worldSession.getDataTag(craftServer.getServer().registryreadops, craftServer.getServer().datapackconfiguration);
            if(main.DEBUG) System.out.println("PRE worlddata==null");
            if (worlddata == null) {
                if(main.DEBUG) System.out.println("IN worlddata==null");
                Properties properties = new Properties();
                properties.put("generator-settings", Objects.toString(creator.generatorSettings()));
                properties.put("level-seed", Objects.toString(creator.seed()));
                properties.put("generate-structures", Objects.toString(creator.generateStructures()));
                properties.put("level-type", Objects.toString(creator.type().getName()));

                if(main.DEBUG) System.out.println("BEFORE WorldGenSettings");


                WorldGenSettings generatorsettings = WorldGenSettings.create(craftServer.getServer().registryAccess(), properties);
                if(main.DEBUG) System.out.println("IN#0 WorldGenSettings");

                LevelSettings worldSettings = new LevelSettings(name, GameType.byId(craftServer.getDefaultGameMode().getValue()), creator.hardcore(), Difficulty.EASY, false, new GameRules(), craftServer.getServer().datapackconfiguration);
                if(main.DEBUG) System.out.println("IN#1 WorldGenSettings");

                worlddata = new PrimaryLevelData(worldSettings, generatorsettings, Lifecycle.stable());

                if(main.DEBUG) System.out.println("AFTER WorldGenSettings");


            }


            if(main.DEBUG) System.out.println("AFTER worlddata==null");
            worlddata.checkName(name);
            if(main.DEBUG) System.out.println("PRE setModdedInfo");
            worlddata.setModdedInfo(craftServer.getServer().getServerModName(), craftServer.getServer().getModdedStatus().shouldReportAsModified());
            if (craftServer.getServer().options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), craftServer.getServer().options.has("eraseCache"), () -> {
                    return true;
                }, worlddata.worldGenSettings());
            }
            if(main.DEBUG) System.out.println("PRE Custom API stuff");
            MappedRegistry<LevelStem> registrymaterials = worlddata.worldGenSettings().dimensions();
            GeneratorConfiguration generatorConfiguration = creator.getGeneratorConfiguration();
            ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = null;
            ResourceLocation worldMinecraftKey = new ResourceLocation("minecraft",creator.name());

            if(generatorConfiguration != null){
                generatorSettingBaseResourceKey = createGeneratorSettingBase(worldMinecraftKey,generatorConfiguration);
            }
            if (creator.environment() == World.Environment.CUSTOM) {
                createAndRegisterCustomEnvironment(actualDimension,registrymaterials,worldMinecraftKey,creator.getEnvironmentBuilder(),creator.seed(),generatorSettingBaseResourceKey);
            }

            RegistryAccess registryAccess = craftServer.getServer().registryAccess();
            WritableRegistry<NormalNoise.NoiseParameters> noiseRegistry = registryAccess.ownedRegistryOrThrow(Registry.NOISE_REGISTRY);


            if(main.DEBUG) System.out.println("BEHIND Custom API stuff");


            LevelStem worlddimension = (LevelStem)registrymaterials.get(actualDimension);


            DimensionType dimensionmanager;
            Object chunkgenerator;
            if (worlddimension == null) {
                dimensionmanager = (DimensionType)craftServer.getServer().registryHolder.registryOrThrow(net.minecraft.core.Registry.DIMENSION_TYPE_REGISTRY).getOrThrow(DimensionType.OVERWORLD_LOCATION);
                if (generatorSettingBaseResourceKey == null) {
                    chunkgenerator = WorldGenSettings.makeDefaultOverworld(craftServer.getServer().registryHolder, creator.seed());
                } else {
                    chunkgenerator = WorldGenSettings.makeOverworld(craftServer.getServer().registryHolder, creator.seed(), generatorSettingBaseResourceKey);
                }
            } else {
                dimensionmanager = worlddimension.type();
                chunkgenerator = worlddimension.generator();
            }

            WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), dimensionmanager);
            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }

            if (biomeProvider != null) {
                BiomeSource worldChunkManager = new CustomWorldChunkManager(worldInfo, biomeProvider, craftServer.getServer().registryHolder.ownedRegistryOrThrow(net.minecraft.core.Registry.BIOME_REGISTRY));
                if (chunkgenerator instanceof NoiseBasedChunkGenerator) {
                    chunkgenerator = new NoiseBasedChunkGenerator(((NoiseBasedChunkGenerator)chunkgenerator).noises, worldChunkManager, ((net.minecraft.world.level.chunk.ChunkGenerator)chunkgenerator).strongholdSeed, ((NoiseBasedChunkGenerator)chunkgenerator).settings);
                }
            }else if(customBiomeProvider != null){
                BiomeSource worldChunkManager = new AdvancedCustomWorldChunkManager(worldInfo, customBiomeProvider, craftServer.getServer().registryHolder.ownedRegistryOrThrow(net.minecraft.core.Registry.BIOME_REGISTRY));
                if (chunkgenerator instanceof NoiseBasedChunkGenerator) {
                    chunkgenerator = new NoiseBasedChunkGenerator(((NoiseBasedChunkGenerator)chunkgenerator).noises, worldChunkManager, ((net.minecraft.world.level.chunk.ChunkGenerator)chunkgenerator).strongholdSeed, ((NoiseBasedChunkGenerator)chunkgenerator).settings);
                }
            }


            if(DEBUG) {
                Bukkit.broadcastMessage("§6 ABC - 1");
                for(World world1 : craftServer.getWorlds()) {
                    Bukkit.broadcastMessage("§e " + world1.getName());
                }
            }

            /*
            MappedRegistry<LevelStem> levelStemMappedRegistry = new MappedRegistry(Registry.DIMENSION_REGISTRY, Lifecycle.experimental());

            RegistryAccess registryAccess = craftServer.getServer().registryAccess();

            Registry<DimensionType> registry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);

            //ResourceKey<DimensionType> manager = ResourceKey.create(GlobalValues.DimensionManager_ResourceKey, worldMinecraftKey);
            levelStemMappedRegistry.register(LevelStem.OVERWORLD, new LevelStem(() -> registry.getOrThrow(actualDimension), (net.minecraft.world.level.chunk.ChunkGenerator) chunkgenerator), Lifecycle.stable());
            */

            String levelName = craftServer.getServer().getProperties().levelName;
            ResourceKey worldKey;
            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(name.toLowerCase(Locale.ENGLISH)));
            }
            if(DEBUG) {
                Bukkit.broadcastMessage("§6 ABC - 2");
                for (World world1 : craftServer.getWorlds()) {
                    Bukkit.broadcastMessage("§e " + world1.getName());
                }
            }
            long obfuscateSeed = BiomeManager.obfuscateSeed(creator.seed());
            List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
            ServerLevel internal = new ServerLevel(craftServer.getServer(), craftServer.getServer().executor, worldSession, worlddata, worldKey,
                    dimensionmanager, craftServer.getServer().progressListenerFactory.create(11), (net.minecraft.world.level.chunk.ChunkGenerator)chunkgenerator,
                    worlddata.worldGenSettings().isDebug(), obfuscateSeed, creator.environment() == World.Environment.NORMAL || creator.environment() == World.Environment.CUSTOM ? list : ImmutableList.of(),
                    true, creator.environment(), generator, biomeProvider);

            if(DEBUG) {
                Bukkit.broadcastMessage("§6 ABC - 3");
                for (World world1 : craftServer.getWorlds()) {

                    Bukkit.broadcastMessage("§e " + world1.getName());
                }
            }



            if (craftServer.getWorld(name.toLowerCase(Locale.ENGLISH)) == null) {
                if(DEBUG) Bukkit.broadcastMessage("§6 ABC - 4 error");
                return null;
            } else {
                if(DEBUG) Bukkit.broadcastMessage("§6 ABC - 4");
                craftServer.getServer().initWorld(internal, worlddata, worlddata, worlddata.worldGenSettings());
                internal.setSpawnSettings(true, true);
                craftServer.getServer().levels.put(internal.dimension(), internal);
                if(DEBUG) Bukkit.broadcastMessage("§6 ABC - 5");

                craftServer.getServer().prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                internal.entityManager.tick();
                if(DEBUG) Bukkit.broadcastMessage("§6 ABC - 6");

                craftServer.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));

                if(DEBUG) Bukkit.broadcastMessage("§6 ABC - 7");
                return internal.getWorld();
            }
        }
    }

    /**
     * Define and create the CustomEnvironment of the World
     *
     * */
    private static void createAndRegisterCustomEnvironment(ResourceKey<LevelStem> actualDimension,MappedRegistry<LevelStem> registrymaterials, ResourceLocation minecraftKey, EnvironmentBuilder builder, long seed, ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        ResourceKey<DimensionType> resourceKeyDimension = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, minecraftKey);
        WritableRegistry<DimensionType> registryDimensions = craftServer.getHandle().getServer().registryAccess().ownedRegistryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);

        DimensionType dimensionManager = DimensionType.create(
                builder.getFixedTime() == null ? OptionalLong.empty() : OptionalLong.of(builder.getFixedTime()),
                builder.isHasSkylight(),
                builder.isHasCeiling(),
                builder.isUltraWarm(),
                builder.isNatural(),
                builder.getCoordinateScale(),
                builder.isCreateDragonFight(),
                builder.isPiglinSafe(),
                builder.isBedWorks(),
                builder.isRespawnAnchorWorks(),
                builder.isHasRaids(),
                builder.getMinY(),
                builder.getHeight(),
                builder.getLogicalHeight(),
                new ResourceLocation(builder.getInfiniburn().getNamespace(),builder.getInfiniburn().getKey()),
                new ResourceLocation(builder.getEffectsLocation().getNamespace(),builder.getEffectsLocation().getKey()),
                builder.getAmbientLight());



        registryDimensions.registerOrOverride(OptionalInt.empty(),resourceKeyDimension, dimensionManager, Lifecycle.stable());

        NoiseBasedChunkGenerator generatorAbstract = null;
        if (generatorSettingBaseResourceKey == null) {
            generatorAbstract = WorldGenSettings.makeDefaultOverworld(craftServer.getServer().registryHolder, seed);
        } else {
            generatorAbstract = WorldGenSettings.makeOverworld(craftServer.getServer().registryHolder, seed, generatorSettingBaseResourceKey);
        }

        LevelStem dimension = new LevelStem(()->dimensionManager,generatorAbstract);

        registrymaterials.registerOrOverride(OptionalInt.empty(),actualDimension,dimension,Lifecycle.stable());
    }

    /**
     * Simple Method to generate GeneratorSettingBase==How the Terrain will generate and which structures
     *
     * */
    private static ResourceKey<NoiseGeneratorSettings> createGeneratorSettingBase(ResourceLocation minecraftKey,GeneratorConfiguration generatorConfiguration) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        StrongholdConfiguration stronghold = null;
        HashMap<StructureFeature<?>, StructureFeatureConfiguration> structureMaps = new HashMap<>();

        GeneratorConfiguration.NoiseGeneration noiseGeneration = generatorConfiguration.getNoiseGeneration();
        GeneratorConfiguration.StructureGeneration structureGeneration = generatorConfiguration.getStructureGeneration();
        HashMap<StructureType, GeneratorConfiguration.StructureInfo> strucctureInfos = structureGeneration.getStructureInfos();

        for (StructureType type : strucctureInfos.keySet()) {
            String typename = type.getName();

            if (typename != null) {
                GeneratorConfiguration.StructureInfo structureInfo = strucctureInfos.get(type);
                if (typename.equals("Stronghold")) {
                    stronghold = new StrongholdConfiguration(structureInfo.getSpacing(), structureInfo.getSeparation(), structureInfo.getSalt());
                } else {
                    StructureFeature<?> structureGenerator = StructureFeature.STRUCTURES_REGISTRY.get(typename.toLowerCase(Locale.ROOT));

                    if (structureGenerator != null) {
                        structureMaps.put(structureGenerator, new StructureFeatureConfiguration(structureInfo.getSpacing(), structureInfo.getSeparation(), structureInfo.getSalt()));
                    } else {
                        throw new IllegalStateException("Can't find StructureGenerator for " + typename + " while creating a World!");
                    }
                }
            } else {
                throw new IllegalStateException("Can't find Name for " + type.getName() + " while creating a World!");
            }


        }

        GeneratorConfiguration.SamplingGeneration samplingGeneration = noiseGeneration.getSamplingGeneration();
        NoiseSamplingSettings noiseSamplingSettings = new NoiseSamplingSettings(samplingGeneration.getXzScale(),samplingGeneration.getyScale(),samplingGeneration.getXzFactor(),samplingGeneration.getyFactor());

        GeneratorConfiguration.SliderGeneration topSliderGeneration = noiseGeneration.getTopSlideSettings();
        GeneratorConfiguration.SliderGeneration bottomSliderGeneration = noiseGeneration.getBottomSlideSettings();


        NoiseSlider topNoiseSlider = new NoiseSlider(topSliderGeneration.getTarget(),topSliderGeneration.getSize(),topSliderGeneration.getOffset());
        NoiseSlider bottomNoiseSlider = new NoiseSlider(bottomSliderGeneration.getTarget(),bottomSliderGeneration.getSize(),bottomSliderGeneration.getOffset());

        NoiseSettings noiseSettings = new NoiseSettings(noiseGeneration.getMinY(),noiseGeneration.getHeight(),noiseSamplingSettings,topNoiseSlider,
                bottomNoiseSlider,noiseGeneration.getNoiseSizeHorizontal(),noiseGeneration.getNoiseSizeVertical(),noiseGeneration.isIslandNoiseOverride(),
                noiseGeneration.isAmplified(),noiseGeneration.isLargeBiomes(), TerrainShaper.overworld(false));

        StructureSettings structureSettings = new StructureSettings(Optional.ofNullable(stronghold),structureMaps);

        BlockState baseBlock = ((CraftBlockData)Bukkit.createBlockData(generatorConfiguration.getDefaultBlock())).getState();
        BlockState baseFluid = ((CraftBlockData)Bukkit.createBlockData(generatorConfiguration.getDefaultFluid())).getState();

        try{
            Constructor constructor = NoiseGeneratorSettings.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            NoiseGeneratorSettings generatorSettingBase = (NoiseGeneratorSettings) constructor.newInstance(structureSettings,noiseSettings,baseBlock,baseFluid, SurfaceRuleData.overworld(),
                    generatorConfiguration.getSeaLevel(), generatorConfiguration.isDisableMobGeneration(),generatorConfiguration.isAquifersEnabled(),
                    generatorConfiguration.isNoiseCavesEnabled(),generatorConfiguration.isOreVeinsEnabled(),generatorConfiguration.isNoiseCavesEnabled(),
                    generatorConfiguration.getRandomGenerationType() == GeneratorConfiguration.RandomGenerationType.LEGACY);


            ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, minecraftKey);
            WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = craftServer.getHandle().getServer().registryAccess().ownedRegistryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);


           // NoiseGeneratorSettings alreadyRegistered = registryGeneratorSettings.get(minecraftKey);

            //Bukkit.broadcastMessage("§ctype: " + registryGeneratorSettings.getClass().getName() + " contains ? " + alreadyRegistered);
            //MappedRegistry

            /*
            if(alreadyRegistered != null) {
                int id = registryGeneratorSettings.getId(alreadyRegistered);
                registryGeneratorSettings.registerOrOverride(OptionalInt.of(id),generatorSettingBaseResourceKey, generatorSettingBase, Lifecycle.stable());

            }else{

            }
             */

            registryGeneratorSettings.registerOrOverride(OptionalInt.empty(),generatorSettingBaseResourceKey, generatorSettingBase, Lifecycle.stable());
            //BuiltinRegistries.register(BuiltinRegistries.NOISE_GENERATOR_SETTINGS, generatorSettingBaseResourceKey.location(), generatorSettingBase);



            return generatorSettingBaseResourceKey;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

}
