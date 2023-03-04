package de.freesoccerhdx.advancedworldcreatorapi;

import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;

import java.util.HashMap;

public class SpawnTarget {

    private static HashMap<NamespacedKey, SpawnTarget> spawnTargetList = new HashMap<>();

    public static SpawnTarget[] values() {
        return spawnTargetList.values().toArray(new SpawnTarget[0]);
    }

    public static final SpawnTarget OVERWORLD = new SpawnTarget(new NamespacedKey("minecraft", "overworld"));
    public static final SpawnTarget NETHER = new SpawnTarget(new NamespacedKey("minecraft", "nether"));
    public static final SpawnTarget END = new SpawnTarget(new NamespacedKey("minecraft", "end"));
    public static final SpawnTarget CAVES = new SpawnTarget(new NamespacedKey("minecraft", "caves"));
    public static final SpawnTarget LARGE_BIOMES = new SpawnTarget(new NamespacedKey("minecraft", "large_biomes"));
    public static final SpawnTarget AMPLIFIED = new SpawnTarget(new NamespacedKey("minecraft", "amplified"));
    public static final SpawnTarget FLOATING_ISLANDS = new SpawnTarget(new NamespacedKey("minecraft", "floating_islands"));


    private final NamespacedKey key;

    public SpawnTarget(NamespacedKey namespacedKey) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = (WritableRegistry<NoiseGeneratorSettings>) server.registryAccess().registryOrThrow(Registries.NOISE_SETTINGS);
        if(!registryGeneratorSettings.getOptional(new ResourceLocation(namespacedKey.getNamespace(), namespacedKey.getKey())).isPresent()) {
            throw new IllegalArgumentException("NoiseGeneratorSettings with key " + namespacedKey + " does not exist!");
        }else {

            if(spawnTargetList.containsKey(namespacedKey)) {
                throw new IllegalArgumentException("SpawnTarget with key " + namespacedKey + " already exists!");
            }else {
                this.key = namespacedKey;
                spawnTargetList.put(namespacedKey, this);
            }
        }
    }

    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "SpawnTarget{key=" + key +"}";
    }
    
}
