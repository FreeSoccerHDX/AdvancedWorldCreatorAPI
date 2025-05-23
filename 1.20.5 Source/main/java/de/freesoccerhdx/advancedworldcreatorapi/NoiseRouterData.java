package de.freesoccerhdx.advancedworldcreatorapi;

import de.freesoccerhdx.mcutils.NMSHelper;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class NoiseRouterData {

    private static final HashMap<NamespacedKey, NoiseRouterData> noiseRouterDataList = new HashMap<>();

    public static NoiseRouterData[] values() {
        return noiseRouterDataList.values().toArray(new NoiseRouterData[0]);
    }

    public static final NoiseRouterData OVERWORLD = new NoiseRouterData(NamespacedKey.minecraft("overworld"));
    public static final NoiseRouterData NETHER = new NoiseRouterData(NamespacedKey.minecraft("nether"));
    public static final NoiseRouterData END = new NoiseRouterData(NamespacedKey.minecraft("end"));
    public static final NoiseRouterData CAVES = new NoiseRouterData(NamespacedKey.minecraft("caves"));
    public static final NoiseRouterData LARGE_BIOMES = new NoiseRouterData(NamespacedKey.minecraft("large_biomes"));
    public static final NoiseRouterData AMPLIFIED = new NoiseRouterData(NamespacedKey.minecraft("amplified"));
    public static final NoiseRouterData FLOATING_ISLANDS = new NoiseRouterData(NamespacedKey.minecraft("floating_islands"));


    private final NamespacedKey key;

    public NoiseRouterData(NamespacedKey namespacedKey) {
        //MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        //WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = (WritableRegistry<NoiseGeneratorSettings>) server.registryAccess().registryOrThrow(Registries.NOISE_SETTINGS);

        WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);

        if(registryGeneratorSettings.getOptional(ResourceLocation.fromNamespaceAndPath(namespacedKey.getNamespace(), namespacedKey.getKey())).isEmpty()) {
            throw new IllegalArgumentException("NoiseGeneratorSettings with key " + namespacedKey + " does not exist!");
        }else {

            if(noiseRouterDataList.containsKey(namespacedKey)) {
                throw new IllegalArgumentException("NoiseRouterData with key " + namespacedKey + " already exists!");
            }else {
                this.key = namespacedKey;
                noiseRouterDataList.put(namespacedKey, this);
            }
        }
    }

    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "NoiseRouterData{key=" + key +"}";
    }
}
