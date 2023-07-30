package de.freesoccerhdx.advancedworldcreatorapi;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;

import java.lang.reflect.Field;

public class NMSHelper {

    public static <T> Object registerToRegistryObject(String namespace, String key, Object objectToRegister, Object resourceKey_registry) {
        return registerToRegistry(namespace, key, (T)objectToRegister, (ResourceKey<? extends Registry<T>>) resourceKey_registry);
    }
    public static <T> Object registerToRegistryObject(NamespacedKey key, Object objectToRegister, Object resourceKey_registry) {
        return registerToRegistry(key, (T)objectToRegister, (ResourceKey<? extends Registry<T>>) resourceKey_registry);
    }
    public static <T> Holder.Reference<T> registerToRegistry(NamespacedKey key, T objectToRegister, ResourceKey<? extends Registry<T>> registry) {
        return registerToRegistry(key.getNamespace(), key.getKey(), objectToRegister, registry);
    }
    public static <T> Holder.Reference<T> registerToRegistry(String namespace, String key, T objectToRegister, ResourceKey<? extends Registry<T>> registry) {
        WritableRegistry<T> writableRegistry = (WritableRegistry<T>) getWritableRegistry(registry);
        Holder.Reference<T> registeredObj = writableRegistry.register(ResourceKey.create(registry, new ResourceLocation(namespace, key)), objectToRegister, Lifecycle.stable());
        setPrivateField(registeredObj, "e", objectToRegister);
        return registeredObj;
    }

    public static <T> Object createResourceKey(NamespacedKey key, Object resourceKey_registry) {
        return createResourceKey(key, (ResourceKey<? extends Registry<T>>) resourceKey_registry);
    }
    public static <T> ResourceKey<T> createResourceKey(NamespacedKey key, ResourceKey<? extends Registry<T>> registry) {
        return createResourceKey(key.getNamespace(), key.getKey(), registry);
    }


    public static <T> Object createResourceKey(String namespace, String key, Object resourceKey_registry) {
        return createResourceKey(namespace, key, (ResourceKey<? extends Registry<T>>) resourceKey_registry);
    }
    public static <T> ResourceKey<T> createResourceKey(String namespace, String key, ResourceKey<? extends Registry<T>> registry) {
        return ResourceKey.create(registry, new ResourceLocation(namespace, key));
    }


    public static <T> Object getWritableRegistry(Object resourceKey_registry){
        return getWritableRegistry((ResourceKey<Registry<T>>) resourceKey_registry);
    }
    public static <T> WritableRegistry<T> getWritableRegistry(ResourceKey<Registry<T>> resourceKey_registry){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WritableRegistry<T> writeRegistry = (WritableRegistry<T>) server.registryAccess().registryOrThrow(resourceKey_registry);
        unfreezeRegistry(writeRegistry);
        return writeRegistry;
    }

    // TODO CHECK IF THIS WORKS
    public static BiomeSource getOverworldBiomeSource() {
        HolderLookup<Biome> biomeHolderGetter = getWritableRegistry(Registries.BIOME).asLookup().filterFeatures(FeatureFlagSet.of());
        //BiomeSource biomeSource = MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(biomeHolderGetter);
        MultiNoiseBiomeSourceParameterList parameterList = new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, biomeHolderGetter);
        return MultiNoiseBiomeSource.createFromPreset(Holder.direct(parameterList));
    }

    // TODO CHECK IF THIS WORKS
    public static BiomeSource getNetherBiomeSource() {
        HolderLookup<Biome> biomeHolderGetter = getWritableRegistry(Registries.BIOME).asLookup().filterFeatures(FeatureFlagSet.of());
        //BiomeSource biomeSource = MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(biomeHolderGetter);
        MultiNoiseBiomeSourceParameterList parameterList = new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.NETHER, biomeHolderGetter);
        return MultiNoiseBiomeSource.createFromPreset(Holder.direct(parameterList));
    }

    public static boolean unfreezeRegistry(Object writeableRegistry) {
        return unfreezeRegistry((WritableRegistry) writeableRegistry);
    }
    public static boolean unfreezeRegistry(WritableRegistry registry) {
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
}
