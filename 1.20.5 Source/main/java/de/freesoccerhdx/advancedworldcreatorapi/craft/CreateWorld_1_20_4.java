package de.freesoccerhdx.advancedworldcreatorapi.craft;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.AdvancedWorldCreator;
import de.freesoccerhdx.advancedworldcreatorapi.EnvironmentBuilder;
import de.freesoccerhdx.advancedworldcreatorapi.GeneratorConfiguration;
import de.freesoccerhdx.advancedworldcreatorapi.StructurePlacementOverride;
import de.freesoccerhdx.mcutils.McNMSSpecific;
import de.freesoccerhdx.mcutils.NMSHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtException;
import net.minecraft.nbt.ReportedNbtException;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.ChunkGenerators;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.validation.ContentValidationException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R4.CraftServer;
import org.bukkit.craftbukkit.v1_21_R4.generator.CraftWorldInfo;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CreateWorld_1_20_4 {

    public static World createWorld(AdvancedWorldCreator creator) {
//VVVV####################################################################################################################################################################################
        CraftServer this_server = (CraftServer) Bukkit.getServer();
        DedicatedServer this_console = this_server.getServer();
        RegistryAccess registryAccess = this_console.registryAccess();

        EnvironmentBuilder envBuilder = creator.getEnvironmentBuilder();
        GeneratorConfiguration generatorConfiguration = creator.getGeneratorConfiguration();
//^^^^####################################################################################################################################################################################

        Preconditions.checkState(this_console.getAllLevels().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
        Preconditions.checkArgument(creator != null, "WorldCreator cannot be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(this_server.getWorldContainer(), name);
        World world = this_server.getWorld(name);
        if (world != null) {
            return world;
        } else {
            if (folder.exists()) {
                Preconditions.checkArgument(folder.isDirectory(), "File (%s) exists and isn't a folder", name);
            }

            if (generator == null) {
                generator = this_server.getGenerator(name);
            }

            if (biomeProvider == null) {
                biomeProvider = this_server.getBiomeProvider(name);
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
//VVVV####################################################################################################################################################################################
                case CUSTOM:
                    if(envBuilder == null) throw new IllegalArgumentException("Selected Environment.CUSTOM but not specified an EnvironmentBuilder!");
                    actualDimension = de.freesoccerhdx.mcutils.NMSHelper.createResourceKey(Registries.LEVEL_STEM, envBuilder.getKey());
                    break;
//^^^^####################################################################################################################################################################################
                default:
                    throw new IllegalArgumentException("Illegal dimension (" + creator.environment() + ")");
            }

            LevelStorageSource.LevelStorageAccess worldSession;
            try {
                worldSession = LevelStorageSource.createDefault(this_server.getWorldContainer().toPath()).validateAndCreateAccess(name, actualDimension);
            } catch (ContentValidationException | IOException var24) {
                throw new RuntimeException(var24);
            }
//VVVV####################################################################################################################################################################################
            ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = null;
            //System.out.println("generatorConfiguration == " + generatorConfiguration);
            if(generatorConfiguration != null) {
                boolean success = RegisterHelper.registerGeneratorConfiguration(generatorConfiguration);
                //System.out.println("        success == " + success);
                if(success) {
                    generatorSettingBaseResourceKey = NMSHelper.createResourceKey(Registries.NOISE_SETTINGS, generatorConfiguration.getKey());
                    //System.out.println("        generatorSettingBaseResourceKey == " + generatorSettingBaseResourceKey);
                }else{
                    throw new IllegalStateException("GeneratorConfiguration could not be created and registered!");
                }
            }
            if (creator.environment() == World.Environment.CUSTOM) {
                RegisterHelper.registerCustomEnvironment(envBuilder);
            }
//^^^^####################################################################################################################################################################################

            Dynamic dynamic;
            if (worldSession.hasWorldData()) {
                LevelSummary worldinfo;
                try {
                    dynamic = worldSession.getDataTag();
                    worldinfo = worldSession.getSummary(dynamic);
                } catch (ReportedNbtException | IOException | NbtException var23) {
                    LevelStorageSource.LevelDirectory convertable_b = worldSession.getLevelDirectory();
                    MinecraftServer.LOGGER.warn("Failed to load world data from {}", convertable_b.dataFile(), var23);
                    MinecraftServer.LOGGER.info("Attempting to use fallback");

                    try {
                        dynamic = worldSession.getDataTagFallback();
                        worldinfo = worldSession.getSummary(dynamic);
                    } catch (ReportedNbtException | IOException | NbtException var22) {
                        MinecraftServer.LOGGER.error("Failed to load world data from {}", convertable_b.oldDataFile(), var22);
                        MinecraftServer.LOGGER.error("Failed to load world data from {} and {}. World files may be corrupted. Shutting down.", convertable_b.dataFile(), convertable_b.oldDataFile());
                        return null;
                    }

                    worldSession.restoreLevelDataFromOld();
                }

                if (worldinfo.requiresManualConversion()) {
                    MinecraftServer.LOGGER.info("This world must be opened in an older version (like 1.6.4) to be safely converted");
                    return null;
                }

                if (!worldinfo.isCompatible()) {
                    MinecraftServer.LOGGER.info("This world was created by an incompatible version.");
                    return null;
                }
            } else {
                dynamic = null;
            }

            boolean hardcore = creator.hardcore();
            WorldLoader.DataLoadContext worldloader_a = this_console.worldLoader;
//VVVV####################################################################################################################################################################################
//            WritableRegistry<LevelStem> iregistry = NMSHelper.getWritableRegistry(Registries.LEVEL_STEM);

            WritableRegistry<LevelStem> iregistry = (WritableRegistry<LevelStem>) worldloader_a.datapackDimensions().lookup(Registries.LEVEL_STEM).get();
            NMSHelper.unfreezeRegistry(iregistry);
            //RegistryAccess.Frozen iregistrycustom_dimension = worldloader_a.datapackDimensions();
            //net.minecraft.core.Registry<LevelStem> iregistry = iregistrycustom_dimension.registryOrThrow(Registries.LEVEL_STEM);
            //net.minecraft.core.Registry<LevelStem> iregistry = worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
//^^^^####################################################################################################################################################################################
            PrimaryLevelData worlddata;
            if (dynamic != null) {
                LevelDataAndDimensions leveldataanddimensions = LevelStorageSource.getLevelDataAndDimensions(dynamic, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen());
                worlddata = (PrimaryLevelData)leveldataanddimensions.worldData();
                iregistry = (WritableRegistry<LevelStem>) leveldataanddimensions.dimensions().dimensions();
            } else {
                WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
                DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));
                LevelSettings worldsettings = new LevelSettings(name, GameType.byId(this_server.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(worldloader_a.dataConfiguration().enabledFeatures()), worldloader_a.dataConfiguration());
                WorldDimensions worlddimensions = properties.create(worldloader_a.datapackWorldgen());
                WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistry);
                Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());
                worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
                iregistry = (WritableRegistry<LevelStem>) worlddimensions_b.dimensions();
            }

           // iregistry.iterator().forEachRemaining(levelStem -> {System.out.println("LevelStem Found: " + levelStem.toString());});

           // worlddata.customDimensions = iregistry;
            worlddata.checkName(name);
            worlddata.setModdedInfo(this_console.getServerModName(), this_console.getModdedStatus().shouldReportAsModified());
            /*
            if (this_console.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), this_console.options.has("eraseCache"), () -> {
                    return true;
                }, iregistry);
            }*/

            if (this_console.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, worlddata, DataFixers.getDataFixer(), this_console.options.has("eraseCache"), () -> {
                    return true;
                }, (RegistryAccess) iregistry, this_console.options.has("recreateRegionFiles"));
            }

            long j = BiomeManager.obfuscateSeed(creator.seed());
            List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));

            Optional<Holder.Reference<LevelStem>> optionalWorlddimension = iregistry.get(actualDimension);
            LevelStem worlddimension = optionalWorlddimension.isEmpty() ? null : optionalWorlddimension.get().value();

//VVVV####################################################################################################################################################################################

            if(worlddimension == null) {
                WritableRegistry<NoiseGeneratorSettings> registryNoiseGenSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);
//                registryNoiseGenSettings.keySet().forEach(resourceLocation -> {
//                    System.out.println("ResourceLoc: " + resourceLocation);
//                });
//                System.out.println("generatorSettingBaseResourceKey: " + generatorSettingBaseResourceKey);

                NoiseGeneratorSettings noiseGeneratorSettings = registryNoiseGenSettings.get(generatorSettingBaseResourceKey).get().value();//.getValue(generatorSettingBaseResourceKey);
                Holder<NoiseGeneratorSettings> noiseSettings = Holder.direct(noiseGeneratorSettings);

                WritableRegistry<DimensionType> registryDimensions = NMSHelper.getWritableRegistry(Registries.DIMENSION_TYPE);
                ResourceKey<DimensionType> resourceKeyDimension = NMSHelper.createResourceKey(Registries.DIMENSION_TYPE, envBuilder.getKey());
                //DimensionType dimensionType = registryDimensions.get(resourceKeyDimension);
                Holder.Reference<DimensionType> holderDimensionType = registryDimensions.get(resourceKeyDimension).get();

                BiomeSource biomeSource = McNMSSpecific.getOverworldBiomeSource();

                NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, noiseSettings);

                //CraftHolder<DimensionType> craftHolder = new CraftHolder<>(dimensionType, resourceKeyDimension);
                //// Bukkit.broadcastMessage("§e[TEST] CraftHolder created: " + dimensionType + " -> " + resourceKeyDimension);
                worlddimension = new LevelStem(holderDimensionType, chunkGenerator);
                /*
                NMSHelper.unfreezeRegistry(iregistry);
                Holder.Reference<LevelStem> registered = iregistry.register(actualDimension, worlddimension, RegistrationInfo.BUILT_IN);
                NMSHelper.setHolderReferenceElement(registered, worlddimension);
                */
                //NMSHelper.setPrivateField(registered, "e", worlddimension);
            }

            //iregistry.iterator().forEachRemaining(levelStem -> {System.out.println("LevelStem Found Now: " + levelStem.toString());});


//^^^^####################################################################################################################################################################################



//VVVV####################################################################################################################################################################################
            WorldInfo worldInfo = null;
            try {
                worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), (DimensionType)worlddimension.type().value());
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
//^^^^####################################################################################################################################################################################

            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }

//VVVV####################################################################################################################################################################################
//            if(advancedBiomeProvider != null) {
//                BiomeSource biomeSource = new AdvancedCustomWorldChunkManager(worldInfo, advancedBiomeProvider, NMSHelper.getWritableRegistry(Registries.BIOME));
//                biomeProvider = null;
//                NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) worlddimension.generator();
//                //NMSHelper.setPrivateField(worlddimension,"f", noiseBasedChunkGenerator);
//                worlddimension = new LevelStem(worlddimension.type(), new NoiseBasedChunkGenerator(biomeSource, noiseBasedChunkGenerator.settings));
//            }

            NMSHelper.unfreezeRegistry(iregistry);
            Holder.Reference<LevelStem> registered = iregistry.register(actualDimension, worlddimension, RegistrationInfo.BUILT_IN);
            NMSHelper.setHolderReferenceElement(registered, worlddimension);
                //NMSHelper.setPrivateField(registered, "e", worlddimension);

//^^^^####################################################################################################################################################################################

            String levelName = this_server.getServer().getProperties().levelName;
            ResourceKey worldKey;
            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace(name.toLowerCase(Locale.ENGLISH)));
            }

            ServerLevel internal = new ServerLevel(this_console, this_console.executor, worldSession, worlddata, worldKey, worlddimension, this_server.getServer().progressListenerFactory.create(11), worlddata.isDebugWorld(), j, creator.environment() == World.Environment.NORMAL ? list : ImmutableList.of(), true, this_console.overworld().getRandomSequences(), creator.environment(), generator, biomeProvider);
            //TODO: FIX/Add KeepSpawnInMemory internal.keepSpawnInMemory = creator.keepSpawnInMemory();

//VVVV####################################################################################################################################################################################

            if(creator.getStructurePlacementOverride() != null) {
                StructurePlacementOverride structurePlacementOverride = creator.getStructurePlacementOverride();
                HashMap<StructurePlacementOverride.StructurePlacementType, Pair<Integer, Integer>> overrides = structurePlacementOverride.getStructurePlacementOverrides();
                ChunkGeneratorStructureState structureState = internal.getChunkSource().getGeneratorState();
                List<Holder<StructureSet>> possibleStructs = structureState.possibleStructureSets();

                for (Holder<StructureSet> holderStructur : possibleStructs) {
                    StructureSet structureSet = holderStructur.value();
                    StructurePlacement placement = structureSet.placement();
                /*
                System.out.println("                StructureSet: " + structureSet);
                Optional<ResourceKey<StructureSet>> test = holderStructur.unwrapKey();
                System.out.println("                ResourceKey: " + (test.isPresent() ? test.get() : "null"));
                */

                    //System.out.println("frequency: " + placement.frequency);
                    //System.out.println("salt: " + placement.salt);
                    //System.out.println("locateOffset: " + placement.locateOffset);

                    if (placement instanceof RandomSpreadStructurePlacement randomPlacement) {
                        //System.out.println("seper " + randomPlacement.separation()); // FIELD: d
                        //System.out.println("spacing " + randomPlacement.spacing()); // FIELD: c
                        //System.out.println("spreadType " + randomPlacement.spreadType()); // FIELD: e

                        // Access specific Fields of randomPlacement and change them, remove final and private modifiers

                        Pair<Integer, Integer> override = null;
                        String toSearch = structureSet.toString().toLowerCase(Locale.ENGLISH);

                        for(StructurePlacementOverride.StructurePlacementType type : overrides.keySet()) {
                            if(toSearch.contains(type.toString().toLowerCase(Locale.ENGLISH))) {
                                override = overrides.get(type);
                                break;
                            }
                        }

                        if(override != null) {
                            try {
                                Field seperationField = randomPlacement.getClass().getDeclaredField("d");
                                seperationField.setAccessible(true);
                                seperationField.set(randomPlacement, override.getSecond());
                                Field spacingField = randomPlacement.getClass().getDeclaredField("c");
                                spacingField.setAccessible(true);
                                spacingField.set(randomPlacement, override.getFirst());

                                // 2* spacing - sepeation

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    /*} else if(placement instanceof ConcentricRingsStructurePlacement concentricPlacement) {


                        System.out.println("distance " + concentricPlacement.distance()); // FIELD: c
                        System.out.println("spread " + concentricPlacement.spread()); // FIELD: d
                        System.out.println("count " + concentricPlacement.count()); // FIELD: e

                        try {
                            Field distanceField = concentricPlacement.getClass().getDeclaredField("c");
                            distanceField.setAccessible(true);
                            distanceField.set(concentricPlacement, 0);
                            Field spreadField = concentricPlacement.getClass().getDeclaredField("d");
                            spreadField.setAccessible(true);
                            spreadField.set(concentricPlacement, 1023);
                            Field countField = concentricPlacement.getClass().getDeclaredField("e");
                            countField.setAccessible(true);
                            countField.set(concentricPlacement, 4095);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        System.out.println("                StructureSet: " + structureSet);*/
                    }

                }
            }


//^^^^####################################################################################################################################################################################

            if(this_server.getWorld(name.toLowerCase(Locale.ENGLISH)) == null) {
                return null;
            } else {
                this_console.initWorld(internal, worlddata, worlddata, worlddata.worldGenOptions());
                internal.setSpawnSettings(true);
                this_console.addLevel(internal);
                this_server.getServer().prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                try { // fix PAPER Bug (removed entityManager from ServerLevel)
                    internal.entityManager.tick();
                } catch (NoSuchFieldError error) {
                    System.err.println("This is not an Error!\nPaper just removed a Part that is probably handled somewhere else. Probably save but not 100%.");
                }

                this_server.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
                return internal.getWorld();
            }
        }
    }

}
