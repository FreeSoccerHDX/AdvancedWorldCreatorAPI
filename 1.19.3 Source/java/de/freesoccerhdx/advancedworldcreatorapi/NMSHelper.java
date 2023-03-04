package de.freesoccerhdx.advancedworldcreatorapi;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;

import java.lang.reflect.Field;

public class NMSHelper {



    public static boolean unfreezeRegistry(Registry registry){
        MappedRegistry mappedRegistry = (MappedRegistry) registry;
        try {
            Field field = MappedRegistry.class.getDeclaredField("l"); // frozen-field
            field.setAccessible(true);
            field.set(mappedRegistry, false);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return false;
    }

    /*
    public static NoiseRouterWithOnlyNoises getNoiseRouterWithOnlyNoises(NoiseSettings settings, boolean large){

        try {


            Method[] methods = NoiseRouterData.class.getDeclaredMethods();
            for(Method me : methods) {
                if(me.getName().equals("a")) {
                    if (me.getReturnType() == NoiseRouterWithOnlyNoises.class) {
                        if (me.getParameterCount() == 2) {
                            //System.out.println("Method: " + me.toGenericString());
                            me.setAccessible(true);
                            return (NoiseRouterWithOnlyNoises) me.invoke(null,settings, large);
                            /*
                            Class[] parameterClass = me.getParameterTypes();
                            if (parameterClass[0] == NoiseSettings.class && parameterClass[1] == Boolean.class) {

                                me.setAccessible(true);
                                return (NoiseRouterWithOnlyNoises) me.invoke(settings, large);
                            }*  /
                        }
                    }
                }
            }

            Method method = NoiseRouterData.class.getDeclaredMethod("b", NoiseSettings.class, Boolean.class);
            method.setAccessible(true);

            return (NoiseRouterWithOnlyNoises) method.invoke(settings, large);

        }catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }
    */

    public static Object getPrivateFinalField(Object obj, String varname) {

        try {
            Field field = obj.getClass().getDeclaredField(varname);
            field.setAccessible(true);

            return field.get(obj);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static void setPrivateField(Object obj, String key, Object value) {

        try {
            Field field = obj.getClass().getDeclaredField(key);
            if(field == null) {
                field = obj.getClass().getField(key);
            }

            field.setAccessible(true);
            field.set(obj, value);


        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

/*
    public World createWorld(WorldCreator creator) {
        Preconditions.checkState(this.console.getAllLevels().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
        Validate.notNull(creator, "Creator may not be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(this.getWorldContainer(), name);
        World world = this.getWorld(name);
        if (world != null) {
            return world;
        } else if (folder.exists() && !folder.isDirectory()) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        } else {
            if (generator == null) {
                generator = this.getGenerator(name);
            }

            if (biomeProvider == null) {
                biomeProvider = this.getBiomeProvider(name);
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
                default:
                    throw new IllegalArgumentException("Illegal dimension");
            }

            LevelStorageSource.LevelStorageAccess worldSession;
            try {
                worldSession = LevelStorageSource.createDefault(this.getWorldContainer().toPath()).createAccess(name, actualDimension);
            } catch (IOException var21) {
                throw new RuntimeException(var21);
            }

            boolean hardcore = creator.hardcore();
            PrimaryLevelData worlddata = (PrimaryLevelData)worldSession.getDataTag(this.console.registryreadops, this.console.datapackconfiguration, this.console.registryHolder.allElementsLifecycle());
            if (worlddata == null) {
                DedicatedServerProperties.WorldGenProperties properties = new DedicatedServerProperties.WorldGenProperties(Objects.toString(creator.seed()), GsonHelper.parse(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.generateStructures(), creator.type().name().toLowerCase(Locale.ROOT));
                WorldGenSettings generatorsettings = properties.create(this.console.registryAccess());
                LevelSettings worldSettings = new LevelSettings(name, GameType.byId(this.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(), this.console.datapackconfiguration);
                worlddata = new PrimaryLevelData(worldSettings, generatorsettings, Lifecycle.stable());
            }

            worlddata.checkName(name);
            worlddata.setModdedInfo(this.console.getServerModName(), this.console.getModdedStatus().shouldReportAsModified());
            if (this.console.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), this.console.options.has("eraseCache"), () -> {
                    return true;
                }, worlddata.worldGenSettings());
            }

            long j = BiomeManager.obfuscateSeed(creator.seed());
            List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
            net.minecraft.core.Registry<LevelStem> iregistry = worlddata.worldGenSettings().dimensions();
            LevelStem worlddimension = (LevelStem)iregistry.get(actualDimension);
            WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), (DimensionType)worlddimension.typeHolder().value());
            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }

            String levelName = this.getServer().getProperties().levelName;
            ResourceKey worldKey;
            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(net.minecraft.core.Registry.DIMENSION_REGISTRY, new ResourceLocation(name.toLowerCase(Locale.ENGLISH)));
            }

            ServerLevel internal = new ServerLevel(this.console, this.console.executor, worldSession, worlddata, worldKey, worlddimension, this.getServer().progressListenerFactory.create(11), worlddata.worldGenSettings().isDebug(), j, creator.environment() == World.Environment.NORMAL ? list : ImmutableList.of(), true, creator.environment(), generator, biomeProvider);
            if (!this.worlds.containsKey(name.toLowerCase(Locale.ENGLISH))) {
                return null;
            } else {
                this.console.initWorld(internal, worlddata, worlddata, worlddata.worldGenSettings());
                internal.setSpawnSettings(true, true);
                this.console.addLevel(internal);
                this.getServer().prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                internal.entityManager.tick();
                this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
                return internal.getWorld();
            }
        }
    }


*/
}
