package de.freesoccerhdx.advancedworldcreatorapi.biome;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Represents all Vanilla available Decoration-Types but also can be used to register custom ones
 */
public class BiomeDecoration {

    private static HashMap<NamespacedKey, BiomeDecoration> decorations = new HashMap<>();

    /**
     * Gets an Array of all already registered BiomeDecorations
     *
     * @return An Array of all already registered BiomeDecorations
     */
    public static BiomeDecoration[] values() {
        return decorations.values().toArray(new BiomeDecoration[0]);
    }

    /**
     * Gets all registered BiomDecoration-Keys
     *
     * @return all registered BiomDecoration-Keys
     */
    public static List<NamespacedKey> getBiomeDecorationKeys() {
        return new ArrayList<>(decorations.keySet());
    }

    /**
     * Gets a registered BiomeDecoration by its key
     *
     * @param key The key of the BiomeDecoration
     * @return The BiomeDecoration with the given key
     */
    public static BiomeDecoration getBiomeDecoration(NamespacedKey key) {
        return decorations.get(key);
    }

    public static final BiomeDecoration TREES_TAIGA = new BiomeDecoration("minecraft", "trees_taiga");
    public static final BiomeDecoration ORE_GOLD_LOWER = new BiomeDecoration("minecraft", "ore_gold_lower");
    public static final BiomeDecoration FLOWER_DEFAULT = new BiomeDecoration("minecraft", "flower_default");
    public static final BiomeDecoration DESERT_WELL = new BiomeDecoration("minecraft", "desert_well");
    public static final BiomeDecoration MANGROVE_CHECKED = new BiomeDecoration("minecraft", "mangrove_checked");
    public static final BiomeDecoration SEAGRASS_SIMPLE = new BiomeDecoration("minecraft", "seagrass_simple");
    public static final BiomeDecoration BIRCH_BEES_0002 = new BiomeDecoration("minecraft", "birch_bees_0002");
    public static final BiomeDecoration SPRING_OPEN = new BiomeDecoration("minecraft", "spring_open");
    public static final BiomeDecoration END_GATEWAY_RETURN = new BiomeDecoration("minecraft", "end_gateway_return");
    public static final BiomeDecoration DISK_GRAVEL = new BiomeDecoration("minecraft", "disk_gravel");
    public static final BiomeDecoration PINE_ON_SNOW = new BiomeDecoration("minecraft", "pine_on_snow");
    public static final BiomeDecoration FOSSIL_LOWER = new BiomeDecoration("minecraft", "fossil_lower");
    public static final BiomeDecoration ORE_IRON_SMALL = new BiomeDecoration("minecraft", "ore_iron_small");
    public static final BiomeDecoration ORE_LAPIS_BURIED = new BiomeDecoration("minecraft", "ore_lapis_buried");
    public static final BiomeDecoration ORE_INFESTED = new BiomeDecoration("minecraft", "ore_infested");
    public static final BiomeDecoration WARM_OCEAN_VEGETATION = new BiomeDecoration("minecraft", "warm_ocean_vegetation");
    public static final BiomeDecoration ORE_CLAY = new BiomeDecoration("minecraft", "ore_clay");
    public static final BiomeDecoration VOID_START_PLATFORM = new BiomeDecoration("minecraft", "void_start_platform");
    public static final BiomeDecoration PATCH_GRASS_SAVANNA = new BiomeDecoration("minecraft", "patch_grass_savanna");
    public static final BiomeDecoration ORE_GOLD_NETHER = new BiomeDecoration("minecraft", "ore_gold_nether");
    public static final BiomeDecoration LARGE_DRIPSTONE = new BiomeDecoration("minecraft", "large_dripstone");
    public static final BiomeDecoration ORE_DEBRIS_SMALL = new BiomeDecoration("minecraft", "ore_debris_small");
    public static final BiomeDecoration MEGA_JUNGLE_TREE_CHECKED = new BiomeDecoration("minecraft", "mega_jungle_tree_checked");
    public static final BiomeDecoration SPORE_BLOSSOM = new BiomeDecoration("minecraft", "spore_blossom");
    public static final BiomeDecoration FANCY_OAK_BEES_002 = new BiomeDecoration("minecraft", "fancy_oak_bees_002");
    public static final BiomeDecoration PATCH_LARGE_FERN = new BiomeDecoration("minecraft", "patch_large_fern");
    public static final BiomeDecoration TREES_OLD_GROWTH_PINE_TAIGA = new BiomeDecoration("minecraft", "trees_old_growth_pine_taiga");
    public static final BiomeDecoration KELP_COLD = new BiomeDecoration("minecraft", "kelp_cold");
    public static final BiomeDecoration ORE_MAGMA = new BiomeDecoration("minecraft", "ore_magma");
    public static final BiomeDecoration BAMBOO_VEGETATION = new BiomeDecoration("minecraft", "bamboo_vegetation");
    public static final BiomeDecoration PATCH_SUGAR_CANE_DESERT = new BiomeDecoration("minecraft", "patch_sugar_cane_desert");
    public static final BiomeDecoration ORE_DIAMOND_BURIED = new BiomeDecoration("minecraft", "ore_diamond_buried");
    public static final BiomeDecoration PATCH_GRASS_JUNGLE = new BiomeDecoration("minecraft", "patch_grass_jungle");
    public static final BiomeDecoration PATCH_TAIGA_GRASS = new BiomeDecoration("minecraft", "patch_taiga_grass");
    public static final BiomeDecoration SEAGRASS_COLD = new BiomeDecoration("minecraft", "seagrass_cold");
    public static final BiomeDecoration ORE_COAL_UPPER = new BiomeDecoration("minecraft", "ore_coal_upper");
    public static final BiomeDecoration OAK_BEES_0002 = new BiomeDecoration("minecraft", "oak_bees_0002");
    public static final BiomeDecoration PATCH_DEAD_BUSH_BADLANDS = new BiomeDecoration("minecraft", "patch_dead_bush_badlands");
    public static final BiomeDecoration AMETHYST_GEODE = new BiomeDecoration("minecraft", "amethyst_geode");
    public static final BiomeDecoration ORE_ANCIENT_DEBRIS_LARGE = new BiomeDecoration("minecraft", "ore_ancient_debris_large");
    public static final BiomeDecoration VINES = new BiomeDecoration("minecraft", "vines");
    public static final BiomeDecoration SPRUCE = new BiomeDecoration("minecraft", "spruce");
    public static final BiomeDecoration POINTED_DRIPSTONE = new BiomeDecoration("minecraft", "pointed_dripstone");
    public static final BiomeDecoration GLOWSTONE = new BiomeDecoration("minecraft", "glowstone");
    public static final BiomeDecoration TREES_PLAINS = new BiomeDecoration("minecraft", "trees_plains");
    public static final BiomeDecoration PATCH_GRASS_BADLANDS = new BiomeDecoration("minecraft", "patch_grass_badlands");
    public static final BiomeDecoration CRIMSON_FOREST_VEGETATION = new BiomeDecoration("minecraft", "crimson_forest_vegetation");
    public static final BiomeDecoration PATCH_BERRY_COMMON = new BiomeDecoration("minecraft", "patch_berry_common");
    public static final BiomeDecoration SEAGRASS_DEEP_COLD = new BiomeDecoration("minecraft", "seagrass_deep_cold");
    public static final BiomeDecoration SEAGRASS_DEEP = new BiomeDecoration("minecraft", "seagrass_deep");
    public static final BiomeDecoration ORE_QUARTZ_DELTAS = new BiomeDecoration("minecraft", "ore_quartz_deltas");
    public static final BiomeDecoration SEAGRASS_RIVER = new BiomeDecoration("minecraft", "seagrass_river");
    public static final BiomeDecoration TREES_GROVE = new BiomeDecoration("minecraft", "trees_grove");
    public static final BiomeDecoration FOREST_FLOWERS = new BiomeDecoration("minecraft", "forest_flowers");
    public static final BiomeDecoration BLUE_ICE = new BiomeDecoration("minecraft", "blue_ice");
    public static final BiomeDecoration SEAGRASS_SWAMP = new BiomeDecoration("minecraft", "seagrass_swamp");
    public static final BiomeDecoration PATCH_GRASS_TAIGA_2 = new BiomeDecoration("minecraft", "patch_grass_taiga_2");
    public static final BiomeDecoration RED_MUSHROOM_NORMAL = new BiomeDecoration("minecraft", "red_mushroom_normal");
    public static final BiomeDecoration PATCH_DEAD_BUSH = new BiomeDecoration("minecraft", "patch_dead_bush");
    public static final BiomeDecoration NETHER_SPROUTS = new BiomeDecoration("minecraft", "nether_sprouts");
    public static final BiomeDecoration PATCH_DEAD_BUSH_2 = new BiomeDecoration("minecraft", "patch_dead_bush_2");
    public static final BiomeDecoration RED_MUSHROOM_SWAMP = new BiomeDecoration("minecraft", "red_mushroom_swamp");
    public static final BiomeDecoration LUSH_CAVES_VEGETATION = new BiomeDecoration("minecraft", "lush_caves_vegetation");
    public static final BiomeDecoration PATCH_FIRE = new BiomeDecoration("minecraft", "patch_fire");
    public static final BiomeDecoration TREES_WINDSWEPT_FOREST = new BiomeDecoration("minecraft", "trees_windswept_forest");
    public static final BiomeDecoration OAK_BEES_002 = new BiomeDecoration("minecraft", "oak_bees_002");
    public static final BiomeDecoration ORE_COPPER = new BiomeDecoration("minecraft", "ore_copper");
    public static final BiomeDecoration SPRUCE_CHECKED = new BiomeDecoration("minecraft", "spruce_checked");
    public static final BiomeDecoration PATCH_BERRY_BUSH = new BiomeDecoration("minecraft", "patch_berry_bush");
    public static final BiomeDecoration WARPED_FOREST_VEGETATION = new BiomeDecoration("minecraft", "warped_forest_vegetation");
    public static final BiomeDecoration CHORUS_PLANT = new BiomeDecoration("minecraft", "chorus_plant");
    public static final BiomeDecoration ORE_DIAMOND_LARGE = new BiomeDecoration("minecraft", "ore_diamond_large");
    public static final BiomeDecoration END_SPIKE = new BiomeDecoration("minecraft", "end_spike");
    public static final BiomeDecoration PILE_MELON = new BiomeDecoration("minecraft", "pile_melon");
    public static final BiomeDecoration BAMBOO = new BiomeDecoration("minecraft", "bamboo");
    public static final BiomeDecoration BROWN_MUSHROOM_TAIGA = new BiomeDecoration("minecraft", "brown_mushroom_taiga");
    public static final BiomeDecoration SPRING_DELTA = new BiomeDecoration("minecraft", "spring_delta");
    public static final BiomeDecoration PILE_PUMPKIN = new BiomeDecoration("minecraft", "pile_pumpkin");
    public static final BiomeDecoration DISK_GRASS = new BiomeDecoration("minecraft", "disk_grass");
    public static final BiomeDecoration FLOWER_FLOWER_FOREST = new BiomeDecoration("minecraft", "flower_flower_forest");
    public static final BiomeDecoration DELTA = new BiomeDecoration("minecraft", "delta");
    public static final BiomeDecoration ORE_ANDESITE_UPPER = new BiomeDecoration("minecraft", "ore_andesite_upper");
    public static final BiomeDecoration DISK_CLAY = new BiomeDecoration("minecraft", "disk_clay");
    public static final BiomeDecoration PATCH_GRASS_NORMAL = new BiomeDecoration("minecraft", "patch_grass_normal");
    public static final BiomeDecoration FLOWER_MEADOW = new BiomeDecoration("minecraft", "flower_meadow");
    public static final BiomeDecoration LUSH_CAVES_CLAY = new BiomeDecoration("minecraft", "lush_caves_clay");
    public static final BiomeDecoration ROOTED_AZALEA_TREE = new BiomeDecoration("minecraft", "rooted_azalea_tree");
    public static final BiomeDecoration BROWN_MUSHROOM_NORMAL = new BiomeDecoration("minecraft", "brown_mushroom_normal");
    public static final BiomeDecoration ORE_GOLD = new BiomeDecoration("minecraft", "ore_gold");
    public static final BiomeDecoration ORE_LAPIS = new BiomeDecoration("minecraft", "ore_lapis");
    public static final BiomeDecoration DARK_FOREST_VEGETATION = new BiomeDecoration("minecraft", "dark_forest_vegetation");
    public static final BiomeDecoration TREES_FLOWER_FOREST = new BiomeDecoration("minecraft", "trees_flower_forest");
    public static final BiomeDecoration BIRCH_TALL = new BiomeDecoration("minecraft", "birch_tall");
    public static final BiomeDecoration END_ISLAND_DECORATED = new BiomeDecoration("minecraft", "end_island_decorated");
    public static final BiomeDecoration PATCH_GRASS_FOREST = new BiomeDecoration("minecraft", "patch_grass_forest");
    public static final BiomeDecoration GRASS_BONEMEAL = new BiomeDecoration("minecraft", "grass_bonemeal");
    public static final BiomeDecoration PATCH_GRASS_TAIGA = new BiomeDecoration("minecraft", "patch_grass_taiga");
    public static final BiomeDecoration PILE_ICE = new BiomeDecoration("minecraft", "pile_ice");
    public static final BiomeDecoration ORE_TUFF = new BiomeDecoration("minecraft", "ore_tuff");
    public static final BiomeDecoration ORE_GRANITE_LOWER = new BiomeDecoration("minecraft", "ore_granite_lower");
    public static final BiomeDecoration ICEBERG_BLUE = new BiomeDecoration("minecraft", "iceberg_blue");
    public static final BiomeDecoration MUSHROOM_ISLAND_VEGETATION = new BiomeDecoration("minecraft", "mushroom_island_vegetation");
    public static final BiomeDecoration ORE_GRANITE_UPPER = new BiomeDecoration("minecraft", "ore_granite_upper");
    public static final BiomeDecoration UNDERWATER_MAGMA = new BiomeDecoration("minecraft", "underwater_magma");
    public static final BiomeDecoration OAK = new BiomeDecoration("minecraft", "oak");
    public static final BiomeDecoration FLOWER_PLAINS = new BiomeDecoration("minecraft", "flower_plains");
    public static final BiomeDecoration ORE_DIAMOND = new BiomeDecoration("minecraft", "ore_diamond");
    public static final BiomeDecoration PINE = new BiomeDecoration("minecraft", "pine");
    public static final BiomeDecoration PILE_HAY = new BiomeDecoration("minecraft", "pile_hay");
    public static final BiomeDecoration CLASSIC_VINES_CAVE_FEATURE = new BiomeDecoration("minecraft", "classic_vines_cave_feature");
    public static final BiomeDecoration ICE_PATCH = new BiomeDecoration("minecraft", "ice_patch");
    public static final BiomeDecoration PATCH_TALL_GRASS = new BiomeDecoration("minecraft", "patch_tall_grass");
    public static final BiomeDecoration SCULK_VEIN = new BiomeDecoration("minecraft", "sculk_vein");
    public static final BiomeDecoration LAKE_LAVA_SURFACE = new BiomeDecoration("minecraft", "lake_lava_surface");
    public static final BiomeDecoration TREES_WINDSWEPT_HILLS = new BiomeDecoration("minecraft", "trees_windswept_hills");
    public static final BiomeDecoration SEA_PICKLE = new BiomeDecoration("minecraft", "sea_pickle");
    public static final BiomeDecoration PATCH_TALL_GRASS_2 = new BiomeDecoration("minecraft", "patch_tall_grass_2");
    public static final BiomeDecoration FANCY_OAK_BEES = new BiomeDecoration("minecraft", "fancy_oak_bees");
    public static final BiomeDecoration FOREST_ROCK = new BiomeDecoration("minecraft", "forest_rock");
    public static final BiomeDecoration OAK_CHECKED = new BiomeDecoration("minecraft", "oak_checked");
    public static final BiomeDecoration ORE_DIRT = new BiomeDecoration("minecraft", "ore_dirt");
    public static final BiomeDecoration ORE_COPPER_LARGE = new BiomeDecoration("minecraft", "ore_copper_large");
    public static final BiomeDecoration DRIPSTONE_CLUSTER = new BiomeDecoration("minecraft", "dripstone_cluster");
    public static final BiomeDecoration ORE_BLACKSTONE = new BiomeDecoration("minecraft", "ore_blackstone");
    public static final BiomeDecoration BIRCH_CHECKED = new BiomeDecoration("minecraft", "birch_checked");
    public static final BiomeDecoration TREES_BIRCH_AND_OAK = new BiomeDecoration("minecraft", "trees_birch_and_oak");
    public static final BiomeDecoration TREES_WATER = new BiomeDecoration("minecraft", "trees_water");
    public static final BiomeDecoration SPRUCE_ON_SNOW = new BiomeDecoration("minecraft", "spruce_on_snow");
    public static final BiomeDecoration PATCH_MELON_SPARSE = new BiomeDecoration("minecraft", "patch_melon_sparse");
    public static final BiomeDecoration PATCH_MELON = new BiomeDecoration("minecraft", "patch_melon");
    public static final BiomeDecoration ACACIA = new BiomeDecoration("minecraft", "acacia");
    public static final BiomeDecoration ORE_REDSTONE = new BiomeDecoration("minecraft", "ore_redstone");
    public static final BiomeDecoration BROWN_MUSHROOM_NETHER = new BiomeDecoration("minecraft", "brown_mushroom_nether");
    public static final BiomeDecoration TREES_JUNGLE = new BiomeDecoration("minecraft", "trees_jungle");
    public static final BiomeDecoration JUNGLE_TREE = new BiomeDecoration("minecraft", "jungle_tree");
    public static final BiomeDecoration FANCY_OAK_BEES_0002 = new BiomeDecoration("minecraft", "fancy_oak_bees_0002");
    public static final BiomeDecoration ORE_COAL_LOWER = new BiomeDecoration("minecraft", "ore_coal_lower");
    public static final BiomeDecoration TREES_SWAMP = new BiomeDecoration("minecraft", "trees_swamp");
    public static final BiomeDecoration PATCH_SUNFLOWER = new BiomeDecoration("minecraft", "patch_sunflower");
    public static final BiomeDecoration ICE_SPIKE = new BiomeDecoration("minecraft", "ice_spike");
    public static final BiomeDecoration BASALT_BLOBS = new BiomeDecoration("minecraft", "basalt_blobs");
    public static final BiomeDecoration TREES_SPARSE_JUNGLE = new BiomeDecoration("minecraft", "trees_sparse_jungle");
    public static final BiomeDecoration TALL_MANGROVE_CHECKED = new BiomeDecoration("minecraft", "tall_mangrove_checked");
    public static final BiomeDecoration DISK_SAND = new BiomeDecoration("minecraft", "disk_sand");
    public static final BiomeDecoration PATCH_SUGAR_CANE_BADLANDS = new BiomeDecoration("minecraft", "patch_sugar_cane_badlands");
    public static final BiomeDecoration PATCH_CACTUS_DESERT = new BiomeDecoration("minecraft", "patch_cactus_desert");
    public static final BiomeDecoration SPRING_CLOSED = new BiomeDecoration("minecraft", "spring_closed");
    public static final BiomeDecoration PATCH_SUGAR_CANE = new BiomeDecoration("minecraft", "patch_sugar_cane");
    public static final BiomeDecoration TREES_MEADOW = new BiomeDecoration("minecraft", "trees_meadow");
    public static final BiomeDecoration PATCH_PUMPKIN = new BiomeDecoration("minecraft", "patch_pumpkin");
    public static final BiomeDecoration TREES_MANGROVE = new BiomeDecoration("minecraft", "trees_mangrove");
    public static final BiomeDecoration FLOWER_FOREST_FLOWERS = new BiomeDecoration("minecraft", "flower_forest_flowers");
    public static final BiomeDecoration ORE_GOLD_DELTAS = new BiomeDecoration("minecraft", "ore_gold_deltas");
    public static final BiomeDecoration MEGA_PINE_CHECKED = new BiomeDecoration("minecraft", "mega_pine_checked");
    public static final BiomeDecoration PINE_CHECKED = new BiomeDecoration("minecraft", "pine_checked");
    public static final BiomeDecoration ORE_GRAVEL = new BiomeDecoration("minecraft", "ore_gravel");
    public static final BiomeDecoration LUSH_CAVES_CEILING_VEGETATION = new BiomeDecoration("minecraft", "lush_caves_ceiling_vegetation");
    public static final BiomeDecoration SMALL_BASALT_COLUMNS = new BiomeDecoration("minecraft", "small_basalt_columns");
    public static final BiomeDecoration ORE_ANDESITE_LOWER = new BiomeDecoration("minecraft", "ore_andesite_lower");
    public static final BiomeDecoration SUPER_BIRCH_BEES = new BiomeDecoration("minecraft", "super_birch_bees");
    public static final BiomeDecoration DARK_OAK_CHECKED = new BiomeDecoration("minecraft", "dark_oak_checked");
    public static final BiomeDecoration SCULK_PATCH_DEEP_DARK = new BiomeDecoration("minecraft", "sculk_patch_deep_dark");
    public static final BiomeDecoration SEAGRASS_DEEP_WARM = new BiomeDecoration("minecraft", "seagrass_deep_warm");
    public static final BiomeDecoration BROWN_MUSHROOM_OLD_GROWTH = new BiomeDecoration("minecraft", "brown_mushroom_old_growth");
    public static final BiomeDecoration SEAGRASS_NORMAL = new BiomeDecoration("minecraft", "seagrass_normal");
    public static final BiomeDecoration RED_MUSHROOM_OLD_GROWTH = new BiomeDecoration("minecraft", "red_mushroom_old_growth");
    public static final BiomeDecoration TREES_WINDSWEPT_SAVANNA = new BiomeDecoration("minecraft", "trees_windswept_savanna");
    public static final BiomeDecoration ORE_EMERALD = new BiomeDecoration("minecraft", "ore_emerald");
    public static final BiomeDecoration JUNGLE_BUSH = new BiomeDecoration("minecraft", "jungle_bush");
    public static final BiomeDecoration RED_MUSHROOM_NETHER = new BiomeDecoration("minecraft", "red_mushroom_nether");
    public static final BiomeDecoration GLOW_LICHEN = new BiomeDecoration("minecraft", "glow_lichen");
    public static final BiomeDecoration WEEPING_VINES = new BiomeDecoration("minecraft", "weeping_vines");
    public static final BiomeDecoration MONSTER_ROOM = new BiomeDecoration("minecraft", "monster_room");
    public static final BiomeDecoration SPRING_LAVA_FROZEN = new BiomeDecoration("minecraft", "spring_lava_frozen");
    public static final BiomeDecoration ORE_SOUL_SAND = new BiomeDecoration("minecraft", "ore_soul_sand");
    public static final BiomeDecoration CAVE_VINES = new BiomeDecoration("minecraft", "cave_vines");
    public static final BiomeDecoration MEGA_SPRUCE_CHECKED = new BiomeDecoration("minecraft", "mega_spruce_checked");
    public static final BiomeDecoration MONSTER_ROOM_DEEP = new BiomeDecoration("minecraft", "monster_room_deep");
    public static final BiomeDecoration TREES_SNOWY = new BiomeDecoration("minecraft", "trees_snowy");
    public static final BiomeDecoration LAKE_LAVA_UNDERGROUND = new BiomeDecoration("minecraft", "lake_lava_underground");
    public static final BiomeDecoration SPRING_CLOSED_DOUBLE = new BiomeDecoration("minecraft", "spring_closed_double");
    public static final BiomeDecoration FLOWER_SWAMP = new BiomeDecoration("minecraft", "flower_swamp");
    public static final BiomeDecoration SEAGRASS_WARM = new BiomeDecoration("minecraft", "seagrass_warm");
    public static final BiomeDecoration TREES_SAVANNA = new BiomeDecoration("minecraft", "trees_savanna");
    public static final BiomeDecoration TWISTING_VINES = new BiomeDecoration("minecraft", "twisting_vines");
    public static final BiomeDecoration PATCH_CRIMSON_ROOTS = new BiomeDecoration("minecraft", "patch_crimson_roots");
    public static final BiomeDecoration KELP_WARM = new BiomeDecoration("minecraft", "kelp_warm");
    public static final BiomeDecoration ORE_DIORITE_LOWER = new BiomeDecoration("minecraft", "ore_diorite_lower");
    public static final BiomeDecoration GLOWSTONE_EXTRA = new BiomeDecoration("minecraft", "glowstone_extra");
    public static final BiomeDecoration BROWN_MUSHROOM_SWAMP = new BiomeDecoration("minecraft", "brown_mushroom_swamp");
    public static final BiomeDecoration SUPER_BIRCH_BEES_0002 = new BiomeDecoration("minecraft", "super_birch_bees_0002");
    public static final BiomeDecoration SPRING_WATER = new BiomeDecoration("minecraft", "spring_water");
    public static final BiomeDecoration SPRING_LAVA = new BiomeDecoration("minecraft", "spring_lava");
    public static final BiomeDecoration PATCH_CACTUS = new BiomeDecoration("minecraft", "patch_cactus");
    public static final BiomeDecoration ORE_DIORITE_UPPER = new BiomeDecoration("minecraft", "ore_diorite_upper");
    public static final BiomeDecoration PATCH_GRASS_PLAIN = new BiomeDecoration("minecraft", "patch_grass_plain");
    public static final BiomeDecoration PATCH_CACTUS_DECORATED = new BiomeDecoration("minecraft", "patch_cactus_decorated");
    public static final BiomeDecoration TREES_OLD_GROWTH_SPRUCE_TAIGA = new BiomeDecoration("minecraft", "trees_old_growth_spruce_taiga");
    public static final BiomeDecoration FLOWER_WARM = new BiomeDecoration("minecraft", "flower_warm");
    public static final BiomeDecoration FLOWER_PLAIN = new BiomeDecoration("minecraft", "flower_plain");
    public static final BiomeDecoration PATCH_WATERLILY = new BiomeDecoration("minecraft", "patch_waterlily");
    public static final BiomeDecoration LARGE_BASALT_COLUMNS = new BiomeDecoration("minecraft", "large_basalt_columns");
    public static final BiomeDecoration ORE_GOLD_EXTRA = new BiomeDecoration("minecraft", "ore_gold_extra");
    public static final BiomeDecoration FANCY_OAK_CHECKED = new BiomeDecoration("minecraft", "fancy_oak_checked");
    public static final BiomeDecoration ORE_GRAVEL_NETHER = new BiomeDecoration("minecraft", "ore_gravel_nether");
    public static final BiomeDecoration PILE_SNOW = new BiomeDecoration("minecraft", "pile_snow");
    public static final BiomeDecoration ORE_IRON_MIDDLE = new BiomeDecoration("minecraft", "ore_iron_middle");
    public static final BiomeDecoration BLACKSTONE_BLOBS = new BiomeDecoration("minecraft", "blackstone_blobs");
    public static final BiomeDecoration ACACIA_CHECKED = new BiomeDecoration("minecraft", "acacia_checked");
    public static final BiomeDecoration TREES_BIRCH = new BiomeDecoration("minecraft", "trees_birch");
    public static final BiomeDecoration PATCH_BERRY_RARE = new BiomeDecoration("minecraft", "patch_berry_rare");
    public static final BiomeDecoration ICEBERG_PACKED = new BiomeDecoration("minecraft", "iceberg_packed");
    public static final BiomeDecoration CRIMSON_FUNGI = new BiomeDecoration("minecraft", "crimson_fungi");
    public static final BiomeDecoration BASALT_PILLAR = new BiomeDecoration("minecraft", "basalt_pillar");
    public static final BiomeDecoration PATCH_SUGAR_CANE_SWAMP = new BiomeDecoration("minecraft", "patch_sugar_cane_swamp");
    public static final BiomeDecoration BIRCH_BEES_002 = new BiomeDecoration("minecraft", "birch_bees_002");
    public static final BiomeDecoration ORE_QUARTZ_NETHER = new BiomeDecoration("minecraft", "ore_quartz_nether");
    public static final BiomeDecoration ORE_REDSTONE_LOWER = new BiomeDecoration("minecraft", "ore_redstone_lower");
    public static final BiomeDecoration PATCH_SOUL_FIRE = new BiomeDecoration("minecraft", "patch_soul_fire");
    public static final BiomeDecoration TREES_BADLANDS = new BiomeDecoration("minecraft", "trees_badlands");
    public static final BiomeDecoration RED_MUSHROOM_TAIGA = new BiomeDecoration("minecraft", "red_mushroom_taiga");
    public static final BiomeDecoration FREEZE_TOP_LAYER = new BiomeDecoration("minecraft", "freeze_top_layer");
    public static final BiomeDecoration SCULK_PATCH_ANCIENT_CITY = new BiomeDecoration("minecraft", "sculk_patch_ancient_city");
    public static final BiomeDecoration ORE_IRON_UPPER = new BiomeDecoration("minecraft", "ore_iron_upper");
    public static final BiomeDecoration BAMBOO_LIGHT = new BiomeDecoration("minecraft", "bamboo_light");
    public static final BiomeDecoration FOSSIL_UPPER = new BiomeDecoration("minecraft", "fossil_upper");
    public static final BiomeDecoration WARPED_FUNGI = new BiomeDecoration("minecraft", "warped_fungi");


    private final PlacedFeature feature;

    /**
     * Registeres a new BiomeDecoration with the given namespace and name
     *
     * @param namespace The namespace of the BiomeDecoration.
     * @param name The name of the BiomeDecoration.
     * @throws IllegalArgumentException if there is no BiomeDecoration with  the given namespace and name
     */
    public BiomeDecoration(String namespace, String name) {
        this(new NamespacedKey(namespace, name));
    }

    /**
     * Registeres a new BiomeDecoration with the given namespace and name
     *
     * @param key The NamespacedKey of the BiomeDecoration.
     * @throws IllegalArgumentException if there is no BiomeDecoration with  the given namespace and name
     */
    public BiomeDecoration(NamespacedKey key) {
        if(decorations.containsKey(key)) {
            throw new IllegalArgumentException("BiomeDecoration with the key: " + key + " already exists!");
        }
        Registry<PlacedFeature> placedFeatureRegistry = ((CraftServer)Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.PLACED_FEATURE);

        Optional<PlacedFeature> optional = placedFeatureRegistry.getOptional(new ResourceLocation(key.getNamespace(), key.getKey()));
        if(optional.isEmpty()) {
            throw new IllegalArgumentException("[BiomeDecoration] No such feature with the key: " + key);
        }
        feature = optional.get();
        decorations.put(key, this);

        //feature = Registries.PLACED_FEATURE.get(new ResourceLocation(name().toLowerCase(Locale.ROOT)));
    }

    /**
     * Only for internal uses
     *
     * @return the feature
     */
    protected PlacedFeature getFeature(){
        return feature;
    }

}
