package de.freesoccerhdx.advancedworldcreatorapi.craft;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.*;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.validation.ContentValidationException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.generator.CraftWorldInfo;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class NewCreateWorld {


    public static World createWorld(AdvancedWorldCreator creator) {
        CraftServer this_server = (CraftServer)Bukkit.getServer();
        DedicatedServer this_console = this_server.getServer();
        RegistryAccess registryAccess = this_console.registryAccess();
        EnvironmentBuilder envBuilder = creator.getEnvironmentBuilder();
        GeneratorConfiguration generatorConfiguration = creator.getGeneratorConfiguration();
        AdvancedBiomeProvider advancedBiomeProvider = creator.getAdvancedBiomeProvider();

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

            ResourceKey actualDimension;
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
                    throw new IllegalArgumentException("Illegal dimension (" + creator.environment() + ")");
            }

            LevelStorageSource.LevelStorageAccess worldSession;
            try {
                worldSession = LevelStorageSource.createDefault(this_server.getWorldContainer().toPath()).validateAndCreateAccess(name, actualDimension);
            } catch (ContentValidationException | IOException var23) {
                throw new RuntimeException(var23);
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


            boolean hardcore = creator.hardcore();
            WorldLoader.DataLoadContext worldloader_a = this_console.worldLoader;
            WritableRegistry<LevelStem> iregistry = NMSHelper.getWritableRegistry(Registries.LEVEL_STEM);//(WritableRegistry<LevelStem>) worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);

            DynamicOps<Tag> dynamicops = RegistryOps.create(NbtOps.INSTANCE, worldloader_a.datapackWorldgen());
            //Pair<WorldData, WorldDimensions.Complete> pair = worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen().allRegistriesLifecycle());

            Pair<WorldData, WorldDimensions.Complete> pair = null;

            try {
                pair = worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen().allRegistriesLifecycle());
            } catch (Exception e) {
                //e.printStackTrace();
                AdvancedWorldCreatorAPI.main.getLogger().warning("Failed to load world data for world " + name + "!");
            }

            PrimaryLevelData worlddata;
            if (pair != null) {
                worlddata = (PrimaryLevelData)pair.getFirst();
                iregistry = (WritableRegistry<LevelStem>) ((WorldDimensions.Complete)pair.getSecond()).dimensions();
            } else {
                WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
                DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));
                LevelSettings worldsettings = new LevelSettings(name, GameType.byId(this_server.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(), worldloader_a.dataConfiguration());
                WorldDimensions worlddimensions = properties.create(worldloader_a.datapackWorldgen());
                WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistry);
                Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());
                worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
                iregistry = (WritableRegistry<LevelStem>) worlddimensions_b.dimensions();
            }

            worlddata.customDimensions = iregistry;
            worlddata.checkName(name);
            worlddata.setModdedInfo(this_console.getServerModName(), this_console.getModdedStatus().shouldReportAsModified());
            if (this_console.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), this_console.options.has("eraseCache"), () -> {
                    return true;
                }, iregistry);
            }



            long j = BiomeManager.obfuscateSeed(creator.seed());
            List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
            LevelStem worlddimension = (LevelStem)iregistry.get(actualDimension);

            if(worlddimension == null) {
                WritableRegistry<NoiseGeneratorSettings> registryNoiseGenSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);
                NoiseGeneratorSettings noiseGeneratorSettings = registryNoiseGenSettings.get(generatorSettingBaseResourceKey);
                Holder<NoiseGeneratorSettings> noiseSettings = Holder.direct(noiseGeneratorSettings);

                WritableRegistry<DimensionType> registryDimensions = NMSHelper.getWritableRegistry(Registries.DIMENSION_TYPE);
                ResourceKey<DimensionType> resourceKeyDimension = NMSHelper.createResourceKey(envBuilder.getKey(), Registries.DIMENSION_TYPE);
                //DimensionType dimensionType = registryDimensions.get(resourceKeyDimension);
                Holder.Reference<DimensionType> holderDimensionType = registryDimensions.getHolder(resourceKeyDimension).get();

                BiomeSource biomeSource = NMSHelper.getOverworldBiomeSource();


                NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, noiseSettings);

                //CraftHolder<DimensionType> craftHolder = new CraftHolder<>(dimensionType, resourceKeyDimension);
                //// Bukkit.broadcastMessage("§e[TEST] CraftHolder created: " + dimensionType + " -> " + resourceKeyDimension);
                worlddimension = new LevelStem(holderDimensionType, chunkGenerator);
                NMSHelper.unfreezeRegistry(iregistry);
                Holder.Reference<LevelStem> registered = iregistry.register(actualDimension, worlddimension, Lifecycle.stable());
                NMSHelper.setPrivateField(registered, "e", worlddimension);
            }

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

            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }

            if(advancedBiomeProvider != null) {
                BiomeSource biomeSource = new AdvancedCustomWorldChunkManager(worldInfo, advancedBiomeProvider, NMSHelper.getWritableRegistry(Registries.BIOME));
                biomeProvider = null;
                NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) worlddimension.generator();
                //NMSHelper.setPrivateField(worlddimension,"f", noiseBasedChunkGenerator);
                worlddimension = new LevelStem(worlddimension.type(), new NoiseBasedChunkGenerator(biomeSource, noiseBasedChunkGenerator.settings));
                NMSHelper.unfreezeRegistry(iregistry);
                Holder.Reference<LevelStem> registered = iregistry.register(actualDimension, worlddimension, Lifecycle.stable());
                NMSHelper.setPrivateField(registered, "e", worlddimension);
            }

            String levelName = this_server.getServer().getProperties().levelName;
            ResourceKey worldKey;
            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(name.toLowerCase(Locale.ENGLISH)));
            }

            ServerLevel internal = new ServerLevel(this_console, this_console.executor, worldSession, worlddata, worldKey, worlddimension, this_server.getServer().progressListenerFactory.create(11), worlddata.isDebugWorld(), j, creator.environment() == World.Environment.NORMAL ? list : ImmutableList.of(), true, this_console.overworld().getRandomSequences(), creator.environment(), generator, biomeProvider);

            // TODO: Structure-Set-Modification

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

            //TODO: if (!this_server.worlds.containsKey(name.toLowerCase(Locale.ENGLISH))) {
            if(this_server.getWorld(name.toLowerCase(Locale.ENGLISH)) == null) {
                return null;
            } else {
                this_console.initWorld(internal, worlddata, worlddata, worlddata.worldGenOptions());
                internal.setSpawnSettings(true, true);
                this_console.addLevel(internal);
                this_server.getServer().prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                internal.entityManager.tick();

                //TODO: this_server.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
                this_server.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));

                return internal.getWorld();
            }
        }
    }

}
