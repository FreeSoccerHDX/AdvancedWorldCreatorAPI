package de.freesoccerhdx.advancedworldcreatorapi.biome;

import de.freesoccerhdx.mcutils.NMSHelper;
import net.minecraft.core.registries.Registries;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R4.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Represents existing Cave/Canyon Generation-Types
 *
 * */
public class BiomeCarvers implements Keyed {

    public static BiomeCarvers CAVE = new BiomeCarvers(new NamespacedKey("minecraft", "cave"));
    public static BiomeCarvers CAVE_EXTRA_UNDERGROUND = new BiomeCarvers(new NamespacedKey("minecraft", "cave_extra_underground"));
    public static BiomeCarvers CANYON = new BiomeCarvers(new NamespacedKey("minecraft", "canyon"));
    public static BiomeCarvers NETHER_CAVE = new BiomeCarvers(new NamespacedKey("minecraft", "nether_cave"));

    private NamespacedKey key;
    public BiomeCarvers(NamespacedKey key) {
        this.key = key;
        //this(NMSHelper.createResourceKey(Registries.CONFIGURED_CARVER, key));
    }

//    private ConfiguredWorldCarver<?> carver;
//    BiomeCarvers(ResourceKey<ConfiguredWorldCarver<?>> b) {
//        //Registry<ConfiguredWorldCarver<?>> placedFeatureRegistry = ((CraftServer) Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.CONFIGURED_CARVER);
//        WritableRegistry<ConfiguredWorldCarver<?>> placedFeatureRegistry = NMSHelper.getWritableRegistry(Registries.CONFIGURED_CARVER);
//        carver = placedFeatureRegistry.get(b).get().value();
//    }


    public static BiomeCarvers[] values() {
        ArrayList<BiomeCarvers> biomeCarvers = new ArrayList<>();
        NMSHelper.withWriteableRegistry(Registries.CONFIGURED_CARVER, (writeableRegistry) -> {
            writeableRegistry.keySet().forEach(resourceLocation -> {
                biomeCarvers.add(new BiomeCarvers(CraftNamespacedKey.fromMinecraft(resourceLocation)));
            });
        });
        return biomeCarvers.toArray(new BiomeCarvers[0]);
    }

//
//    /**
//     * Gets the internal ConfiguredWorldCarver
//     *
//     * @return The ConfiguredWorldCarver
//     */
//    protected ConfiguredWorldCarver<?> getCarver(){
//        return carver;
//    }



    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
