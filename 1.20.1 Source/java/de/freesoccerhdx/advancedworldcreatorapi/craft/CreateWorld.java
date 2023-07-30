package de.freesoccerhdx.advancedworldcreatorapi.craft;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.AdvancedCustomWorldChunkManager;
import de.freesoccerhdx.advancedworldcreatorapi.AdvancedWorldCreator;
import de.freesoccerhdx.advancedworldcreatorapi.EnvironmentBuilder;
import de.freesoccerhdx.advancedworldcreatorapi.GeneratorConfiguration;
import de.freesoccerhdx.advancedworldcreatorapi.NMSHelper;
import de.freesoccerhdx.advancedworldcreatorapi.NoiseRouterData;
import de.freesoccerhdx.advancedworldcreatorapi.SpawnTarget;
import de.freesoccerhdx.advancedworldcreatorapi.SurfaceRule;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R1.generator.CraftWorldInfo;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CreateWorld {

    public static World createWorld(AdvancedWorldCreator creator) {
        if(true) {
            return NewCreateWorld.createWorld(creator);
        }

        //Preconditions.checkState(this.console.getAllLevels().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer server = craftServer.getServer();
        RegistryAccess registryAccess = server.registryAccess();
        EnvironmentBuilder envBuilder = creator.getEnvironmentBuilder();
        GeneratorConfiguration generatorConfiguration = creator.getGeneratorConfiguration();
        AdvancedBiomeProvider advancedBiomeProvider = creator.getAdvancedBiomeProvider();

        Validate.notNull(creator, "Creator may not be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(craftServer.getWorldContainer(), name);
        World world = craftServer.getWorld(name);
        if (world != null) {
            // Bukkit.broadcastMessage("§cWorld §e" + name + " §calready exists!");
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

            ResourceKey<LevelStem> actualDimension;
            switch (creator.environment()) {
                case NORMAL:
                    actualDimension = LevelStem.OVERWORLD;
                    break;
                case NETHER:
                    actualDimension = LevelStem.NETHER;
                    break;
                case THE_END:
                    actualDimension = LevelStem.END;
                    break;
                case CUSTOM:
                    if(envBuilder == null) throw new IllegalArgumentException("Selected Environment.CUSTOM but not specified an EnvironmentBuilder!");
                    actualDimension = NMSHelper.createResourceKey(envBuilder.getKey(), Registries.LEVEL_STEM);
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



            boolean hardcore = creator.hardcore();
            WorldLoader.DataLoadContext worldloader_a = server.worldLoader;
            /*
            worldloader_a.datapackDimensions().registries().forEach((registryEntry) -> {
                // Bukkit.broadcastMessage("§cRegistryEntry: " + registryEntry.key() + " >>>> " + registryEntry.value());
            });*/

            WritableRegistry<LevelStem> iregistryLevelStem = (WritableRegistry<LevelStem>) worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
            DynamicOps<Tag> dynamicops = RegistryOps.create(NbtOps.INSTANCE, worldloader_a.datapackWorldgen());
            Pair<WorldData, WorldDimensions.Complete> pair = null; //worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistryLevelStem, worldloader_a.datapackWorldgen().allRegistriesLifecycle());
            PrimaryLevelData worlddata;
            if (pair != null) {
                worlddata = (PrimaryLevelData)pair.getFirst();
                iregistryLevelStem = (WritableRegistry<LevelStem>) pair.getSecond().dimensions();
            } else {
                WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
                DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));
                LevelSettings worldsettings = new LevelSettings(name, GameType.byId(craftServer.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(), worldloader_a.dataConfiguration());
                WorldDimensions worlddimensions = properties.create(worldloader_a.datapackWorldgen());
                WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistryLevelStem);
                Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());
                worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
                iregistryLevelStem = (WritableRegistry<LevelStem>) worlddimensions_b.dimensions();
            }

            worlddata.customDimensions = iregistryLevelStem;
            worlddata.checkName(name);
            worlddata.setModdedInfo(server.getServerModName(), server.getModdedStatus().shouldReportAsModified());
            if (server.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), server.options.has("eraseCache"), () -> {
                    return true;
                }, iregistryLevelStem);
            }

            ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = null;

            if(generatorConfiguration != null) {
                boolean success = RegisterHelper.registerGeneratorConfiguration(generatorConfiguration);

                // Bukkit.broadcastMessage("§a registerGeneratorConfiguration: " + success);

                if(success){
                    generatorSettingBaseResourceKey = NMSHelper.createResourceKey(generatorConfiguration.getKey(), Registries.NOISE_SETTINGS);
                }else{
                    throw new IllegalStateException("GeneratorConfiguration could not be created and registered!");
                }
            }
            if (creator.environment() == World.Environment.CUSTOM) {
                RegisterHelper.registerCustomEnvironment(envBuilder);
            }

            long j = BiomeManager.obfuscateSeed(creator.seed());
            List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
            LevelStem worlddimension = iregistryLevelStem.get(actualDimension);
            // Bukkit.broadcastMessage("§2WorldDimension: " + worlddimension);

            if(worlddimension == null || creator.environment() == World.Environment.CUSTOM || generatorSettingBaseResourceKey != null) {
                WritableRegistry<NoiseGeneratorSettings> registryNoiseGenSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);
                NoiseGeneratorSettings noiseGeneratorSettings = registryNoiseGenSettings.get(generatorSettingBaseResourceKey);
                Holder<NoiseGeneratorSettings> noiseSettings = Holder.direct(noiseGeneratorSettings);

                WritableRegistry<DimensionType> registryDimensions = NMSHelper.getWritableRegistry(Registries.DIMENSION_TYPE);
                ResourceKey<DimensionType> resourceKeyDimension = NMSHelper.createResourceKey(envBuilder.getKey(), Registries.DIMENSION_TYPE);
                //DimensionType dimensionType = registryDimensions.get(resourceKeyDimension);
                Holder.Reference<DimensionType> holderDimensionType = registryDimensions.getHolder(resourceKeyDimension).get();

                // TODO CHECK IF THIS WORKS
                //HolderLookup<Biome> biomeHolderGetter = getWritetableRegistry(registryAccess, Registries.BIOME).asLookup().filterFeatures(FeatureFlagSet.of());
                //BiomeSource biomeSource = MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(biomeHolderGetter);
                //MultiNoiseBiomeSourceParameterList parameterList = new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, biomeHolderGetter);
                //BiomeSource biomeSource = MultiNoiseBiomeSource.createFromPreset(Holder.direct(parameterList));
                BiomeSource biomeSource = NMSHelper.getOverworldBiomeSource();


                NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, noiseSettings);

                //CraftHolder<DimensionType> craftHolder = new CraftHolder<>(dimensionType, resourceKeyDimension);
                //// Bukkit.broadcastMessage("§e[TEST] CraftHolder created: " + dimensionType + " -> " + resourceKeyDimension);
                worlddimension = new LevelStem(holderDimensionType, chunkGenerator);
                Holder.Reference<LevelStem> registered = iregistryLevelStem.register(actualDimension, worlddimension, Lifecycle.stable());
                NMSHelper.setPrivateField(registered, "e", worlddimension);

              //  // Bukkit.broadcastMessage("§c[TEST] created new dimension: " + worlddimension);
            }
            WorldInfo worldInfo = null;

            try {
                worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), worlddimension.type().value());
            } catch (NoSuchMethodError e) {
                try {
                    Class aClass = CraftWorldInfo.class;
                    Constructor[] constructors = aClass.getConstructors();
                    for (Constructor constructor : constructors) {
                        if (constructor.getParameterCount() == 6) {
                            constructor.setAccessible(true);
                            /*
                            Arrays.stream(constructor.getParameterTypes()).toList().forEach(System.out::println);
                            System.out.println("" + worlddata.getClass());
                            System.out.println("" + worldSession.getClass());
                            System.out.println("" + creator.environment().getClass());
                            System.out.println("" + worlddimension.type().value().getClass());
                            System.out.println("" + worlddimension.generator().getClass());
                            System.out.println("" + registryAccess.getClass());*/
                            worldInfo = (WorldInfo) constructor.newInstance(worlddata, worldSession, creator.environment(), worlddimension.type().value(), (net.minecraft.world.level.chunk.ChunkGenerator) worlddimension.generator(), registryAccess);
                            break;
                        }
                    }
                    // check functionality of this
                    worldInfo.getName();

                } catch (Error | InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }

            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }


            if(advancedBiomeProvider != null) {
                BiomeSource biomeSource = new AdvancedCustomWorldChunkManager(worldInfo, advancedBiomeProvider, NMSHelper.getWritableRegistry(Registries.BIOME));
                biomeProvider = null;
                NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) worlddimension.generator();
                //NMSHelper.setPrivateField(worlddimension,"f", noiseBasedChunkGenerator);
                worlddimension = new LevelStem(worlddimension.type(), new NoiseBasedChunkGenerator(biomeSource, noiseBasedChunkGenerator.settings));
                Holder.Reference<LevelStem> registered = iregistryLevelStem.register(actualDimension, worlddimension, Lifecycle.stable());
                NMSHelper.setPrivateField(registered, "e", worlddimension);
            }


            String levelName = server.getProperties().levelName;
            ResourceKey<Level> worldKey;
            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(name.toLowerCase(Locale.ENGLISH)));
            }
            ServerLevel internal = new ServerLevel(server, server.executor, worldSession, worlddata, worldKey, worlddimension, server.progressListenerFactory.create(11), worlddata.isDebugWorld(), j, creator.environment() == World.Environment.NORMAL ? list : new ArrayList<CustomSpawner>(), true, null, creator.environment(), generator, biomeProvider);

            if (false && !craftServer.getWorlds().contains(name.toLowerCase(Locale.ENGLISH))) {
                // Bukkit.broadcastMessage("§cERROR: world not in worldlist after creation");
                return null;
            } else {
                server.initWorld(internal, worlddata, worlddata, worlddata.worldGenOptions());
                internal.setSpawnSettings(true, true);
                server.addLevel(internal);
                server.prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                try {
                    internal.entityManager.tick();
                } catch (NoSuchFieldError e) {
                }
                craftServer.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
                return internal.getWorld();
            }
        }
    }



}
