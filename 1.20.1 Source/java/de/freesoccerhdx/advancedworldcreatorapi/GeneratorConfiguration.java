package de.freesoccerhdx.advancedworldcreatorapi;

import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.StructureType;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;

import java.util.HashMap;

/**
 * Represents the Configuration of the WorldGenerator
 *
 * */
public class GeneratorConfiguration implements Keyed {
    

    public static final GeneratorConfiguration OVERWORLD = new GeneratorConfiguration(NamespacedKey.minecraft("overworld"));
    public static final GeneratorConfiguration LARGE_BIOMES = new GeneratorConfiguration(NamespacedKey.minecraft("large_biomes"));
    public static final GeneratorConfiguration AMPLIFIED = new GeneratorConfiguration(NamespacedKey.minecraft("amplified"));
    public static final GeneratorConfiguration NETHER = new GeneratorConfiguration(NamespacedKey.minecraft("nether"));
    public static final GeneratorConfiguration END = new GeneratorConfiguration(NamespacedKey.minecraft("end"));
    public static final GeneratorConfiguration CAVES = new GeneratorConfiguration(NamespacedKey.minecraft("caves"));
    public static final GeneratorConfiguration FLOATING_ISLANDS = new GeneratorConfiguration(NamespacedKey.minecraft("floating_islands"));

    private RandomGenerationType randomGenerationType = RandomGenerationType.XOROSHIRO;
    //private StructureGeneration structureGeneration = new StructureGeneration();
    private NoiseGeneration noiseGeneration = new NoiseGeneration();
    private Material defaultBlock = Material.STONE;
    private Material defaultFluid = Material.WATER;

    private int seaLevel = 63;
    private boolean disableMobGeneration = false;
    private boolean aquifersEnabled = true;
    //private boolean noiseCavesEnabled = true;
    private boolean oreVeinsEnabled = true;
    //private boolean noodleCavesEnabled = true;
    private SurfaceRule surfaceRule = SurfaceRule.OVERWORLD;
    private NoiseRouterData noiseRouterData = NoiseRouterData.OVERWORLD;
    private SpawnTarget spawnTarget = SpawnTarget.OVERWORLD;

    private NamespacedKey key = null;
    private boolean overwriteSettingsIfExist = false;

    //private boolean disableFluidLevelGeneration = false;


    /**
     * Creates a Generator-Config with Overworld-Settings as default
     * Setting an already existing NamespacedKey will load stored settings, but not 100% of them or correct
     *
     * @param namespacedKey A Key that can be used to load predefined settings (e.g. Datapacks or Mojang)
     **/
    public GeneratorConfiguration(NamespacedKey namespacedKey) {
        if(namespacedKey == null){
            throw new IllegalArgumentException("NamespacedKey can not be null!");
        }
        this.key = namespacedKey;


        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;
        ResourceLocation resourceLocation = new ResourceLocation(namespacedKey.getNamespace(), namespacedKey.getKey());
        ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = ResourceKey.create(Registries.NOISE_SETTINGS, resourceLocation);

        WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);


        NoiseGeneratorSettings ngs = registryGeneratorSettings.get(generatorSettingBaseResourceKey);
        if(ngs != null) {

            this.randomGenerationType = ngs.getRandomSource() == WorldgenRandom.Algorithm.LEGACY ? RandomGenerationType.LEGACY : RandomGenerationType.XOROSHIRO;
            this.defaultBlock = CraftMagicNumbers.getMaterial(ngs.defaultBlock().getBlock());
            this.defaultFluid = CraftMagicNumbers.getMaterial(ngs.defaultFluid().getBlock());
            this.seaLevel = ngs.seaLevel();
            this.disableMobGeneration = ngs.disableMobGeneration();
            this.aquifersEnabled = ngs.aquifersEnabled();
            this.oreVeinsEnabled = ngs.oreVeinsEnabled();


        }
    }

    /*
    public boolean isDisableFluidLevelGeneration() {
        return disableFluidLevelGeneration;
    }

    public void setDisableFluidLevelGeneration(boolean disableFluidLevelGeneration) {
        this.disableFluidLevelGeneration = disableFluidLevelGeneration;
    }*/

    public void setNoiseRouterData(NoiseRouterData noiseRouterData) {
        if(noiseRouterData != null && noiseRouterData.getKey() != null) {
            this.noiseRouterData = noiseRouterData;
        }
    }

    public void setSpawnTarget(SpawnTarget spawnTarget) {
        if(spawnTarget != null && spawnTarget.getKey() != null) {
            this.spawnTarget = spawnTarget;
        }
    }

    public SpawnTarget getSpawnTarget() {
        return spawnTarget;
    }

    public NoiseRouterData getNoiseRouterData() {
        return noiseRouterData;
    }


    /**
     * Sets if those settings will overwrite existing settings with the same key
     *
     * @param overwriteSettingsIfExist If true allows to replace registered settings
     * */
    public void setOverwriteSettingsIfExist(boolean overwriteSettingsIfExist) {
        this.overwriteSettingsIfExist = overwriteSettingsIfExist;
    }

    /**
     * Gets whether settings can be replaced with new one(true) or not(false)
     *
     * @return If replacing is allowed or not
     * */
    public boolean isOverwriteSettingsIfExist() {
        return overwriteSettingsIfExist;
    }

    /**
     * Gets the settings about the Sealevel in the World
     *
     * @return The hight of the SeaLevel
     **/
    public int getSeaLevel() {
        return seaLevel;
    }

    /**
     * Gets the settings about the Material for the default Block
     *
     * @return The Material for default Blocks
     **/
    public Material getDefaultBlock() {
        return defaultBlock;
    }

    /**
     * Gets the settings about the Material for the default Fluid
     *
     * @return The Material for default Fluids
     **/
    public Material getDefaultFluid() {
        return defaultFluid;
    }

    /**
     * Gets the settings about the settings for the Generation of the World
     *
     * @return The NoiseGeneration of the World
     **/
    public NoiseGeneration getNoiseGeneration() {
        return noiseGeneration;
    }

    /**
     * Gets the settings about the source for the Randomness
     *
     * @return The Type of the Random-Source
     **/
    public RandomGenerationType getRandomGenerationType() {
        return randomGenerationType;
    }

    /**
     * Gets the settings about which Structures(Villages,Mineshafts...) will Spawn how often
     *
     * @return The StructureGeneration of the World
     *
    public StructureGeneration getStructureGeneration() {
        return structureGeneration;
    }*/

    /**
     * Gets the settings whether Aquifers can generate
     *
     * @return If Aquifers will generate
     **/
    public boolean isAquifersEnabled() {
        return aquifersEnabled;
    }

    /**
     * Gets the settings whether Mobs can not spawn
     *
     * @return If Mobs are disabled
     **/
    public boolean isDisableMobGeneration() {
        return disableMobGeneration;
    }

    /**
     * Gets the settings whether NoiseCaves can generate
     *
     * @return If NoiseCaves will generate
     **
    public boolean isNoiseCavesEnabled() {
        return noiseCavesEnabled;
    }

    /**
     * Gets the settings whether NoodleCaves can generate
     *
     * @return If NoodleCaves will generate
     **
    public boolean isNoodleCavesEnabled() {
        return noodleCavesEnabled;
    }
    */
    /**
     * Gets the settings whether OreVeins can generate
     *
     * @return If OreVeins will generate
     **/
    public boolean isOreVeinsEnabled() {
        return oreVeinsEnabled;
    }

    /**
     * Sets if the World should generate Aquifers
     *
     * @param aquifersEnabled If Aquifers are enabled
     **/
    public void setAquifersEnabled(boolean aquifersEnabled) {
        this.aquifersEnabled = aquifersEnabled;
    }

    /**
     * Sets the default {@link Material} for Blocks in the World
     *
     * @param defaultBlock The {@link Material} of Blocks
     **/
    public void setDefaultBlock(Material defaultBlock) {
        if (defaultBlock == null || (!defaultBlock.isBlock() && !(defaultBlock != Material.WATER || defaultBlock != Material.LAVA)))
            throw new IllegalArgumentException("The default Block '"+defaultBlock+"' is not a Block or null!");
        this.defaultBlock = defaultBlock;
    }

    /**
     * Sets the default {@link Material} for Fluids in the World
     *
     * @param defaultFluid The {@link Material} of Fluids
     **/
    public void setDefaultFluid(Material defaultFluid) {
        if (defaultBlock == null || (defaultBlock != Material.WATER && defaultBlock != Material.LAVA && !defaultBlock.isBlock()))
            throw new IllegalArgumentException("The default Fluid '"+defaultBlock+"' is not a Fluid or null!");
        this.defaultFluid = defaultFluid;
    }

    /**
     * Sets if Mobs can spawn in the World or not
     *
     * @param disableMobGeneration If Mobs are disabled
     **/
    public void setDisableMobGeneration(boolean disableMobGeneration) {
        this.disableMobGeneration = disableMobGeneration;
    }

    /**
     * Sets if the World can generate NoiseCaves
     *
     * @param noiseCavesEnabled If NoiseCaves will generate
     **
    public void setNoiseCavesEnabled(boolean noiseCavesEnabled) {
        this.noiseCavesEnabled = noiseCavesEnabled;
    }
    */

    /**
     * Sets the Noise-Generation of the World
     *
     * @param noiseGeneration The NoiseGeneration of the World
     **/
    public void setNoiseGeneration(NoiseGeneration noiseGeneration) {
        if (noiseGeneration == null)
            throw new IllegalArgumentException("NoiseGeneration can not be null!");
        this.noiseGeneration = noiseGeneration;
    }

    /**
     * Sets if the World can generate NoodleCaves
     *
     * @param noodleCavesEnabled If NoodleCaves will generate
     **
    public void setNoodleCavesEnabled(boolean noodleCavesEnabled) {
        this.noodleCavesEnabled = noodleCavesEnabled;
    }
    */

    /**
     * Sets if the World can generate OreVeins
     *
     * @param oreVeinsEnabled If OreVeins will generate
     **/
    public void setOreVeinsEnabled(boolean oreVeinsEnabled) {
        this.oreVeinsEnabled = oreVeinsEnabled;
    }

    /**
     * Sets if the source for Randomness in the Generation
     *
     * @param randomGenerationType The Type of the Random-Source
     **/
    public void setRandomGenerationType(RandomGenerationType randomGenerationType) {
        if (randomGenerationType == null)
            throw new IllegalArgumentException("RandomGenerationType can not be null!");
        this.randomGenerationType = randomGenerationType;
    }

    /**
     * Sets the Y-Level of the normal Fluid-Blocks like in Oceans
     *
     * @param seaLevel The Height of the Water-Level
     **/
    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     * Sets the Settings about Structures like Villages and Mineshafts.
     *
     * @param structureGeneration The StructureGeneration-Settings
     *
    public void setStructureGeneration(StructureGeneration structureGeneration) {
        if (structureGeneration == null)
            throw new IllegalArgumentException("StructureGeneration can not be null!");
        this.structureGeneration = structureGeneration;
    }*/

    /**
     * Sets the Surface-Generation to Overworld, Nether or End
     *
     * @param surfaceRule The SurfaceRule to use
     * */
    public void setSurfaceRule(SurfaceRule surfaceRule){
        if(surfaceRule == null){
            throw new IllegalArgumentException("SurfaceRule can not be null!");
        }
        this.surfaceRule = surfaceRule;
    }

    /**
     * Gets the current set SurfaceRule
     *
     * @return SurfaceRule which will be used
     * */
    public SurfaceRule getSurfaceRule() {
        return this.surfaceRule;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }


    /**
     * Small Enum to identify which Random
     * Source will be used for the Generation-Process
     * LEGACY is equal to earlier Generation while
     * XOROSHIRO is the new Source for Randomness
     *
     * */
    public static enum RandomGenerationType {
        LEGACY,XOROSHIRO;
    }


    @Override
    public String toString() {
        return "GeneratorConfiguration{" +
                "randomGenerationType=" + randomGenerationType +
                ", noiseGeneration=" + noiseGeneration +
                ", defaultBlock=" + defaultBlock +
                ", defaultFluid=" + defaultFluid +
                ", seaLevel=" + seaLevel +
                ", disableMobGeneration=" + disableMobGeneration +
                ", aquifersEnabled=" + aquifersEnabled +
                ", oreVeinsEnabled=" + oreVeinsEnabled +
                ", surfaceRule=" + surfaceRule +
                ", key=" + key +
                ", overwriteSettingsIfExist=" + overwriteSettingsIfExist +
                '}';
    }

    /**
     * Represents the Information for the Structures that can spawn in the World
     * Holds Information about how often the Structures can spawn
     *
     * */
    public static class StructureInfo {
        private int spacing;
        private int separation;
        private int salt;

        /**
         * Generates a simple Object that holds Information about how often Structes can spawn in the World
         *
         * @param salt See {@link #setSalt(int)}
         * @param separation See {@link #setSeparation(int)}
         * @param spacing See {@link #setSpacing(int)}
         * */
        public StructureInfo(int spacing, int separation, int salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
        }

        /**
         * Gets the settings about the Salt of the Structure
         *
         * @return The Salt of the Structure
         * */
        public int getSalt() {
            return salt;
        }

        /**
         * Gets the settings about the Seperation of the Structure
         *
         * @return The Seperation of the Structure
         * */
        public int getSeparation() {
            return separation;
        }

        /**
         * Gets the settings about the Salt of the Structure
         *
         * @return The Salt of the Structure
         * */
        public int getSpacing() {
            return spacing;
        }

        /**
         * Sets the salt for the Structure
         * A number that assists in randomization
         *
         * @param salt The salt to use
         * */
        public void setSalt(int salt) {
            this.salt = salt;
        }

        /**
         * Sets the minimum distance between two structures of this type in chunks.
         * Must be less than spacing.
         *
         * @param separation The separation to use
         * */
        public void setSeparation(int separation) {
            if (separation >= spacing || separation < 1)
                throw new IllegalArgumentException("Seperation must be less then Spacing and greater 1!");
            this.separation = separation;
        }
        /**
         * Sets the average distance between two structure placement attempts of this type in chunks
         *
         * @param spacing The spacing to use
         * */
        public void setSpacing(int spacing) {
            if (spacing <= separation || separation < 2)
                throw new IllegalArgumentException("Spacing must be greater seperation and greater 2");
            this.spacing = spacing;
        }
    }

    /**
     * Represents the Configuration of the Structureplacement
     * e.g. How often Structures like Villages or Mineshafts can spawn
     *
     */
    public static class StructureGeneration {
        private static final HashMap<StructureType, StructureInfo> structsetts = new HashMap<>();
        static {
            structsetts.put(StructureType.VILLAGE, new StructureInfo(32,8,10387312));
            structsetts.put(StructureType.DESERT_PYRAMID, new StructureInfo(32,8,14357617));
            structsetts.put(StructureType.IGLOO, new StructureInfo(32,8,14357618));
            structsetts.put(StructureType.JUNGLE_PYRAMID, new StructureInfo(32,8,14357619));
            structsetts.put(StructureType.SWAMP_HUT, new StructureInfo(32,8,14357620));
            structsetts.put(StructureType.PILLAGER_OUTPOST, new StructureInfo(32,8,165745296));
            structsetts.put(StructureType.STRONGHOLD, new StructureInfo(1,0,0));
            structsetts.put(StructureType.OCEAN_MONUMENT, new StructureInfo(32,5,10387313));
            structsetts.put(StructureType.END_CITY, new StructureInfo(20,11,10387313));
            structsetts.put(StructureType.WOODLAND_MANSION, new StructureInfo(80,20,10387319));
            structsetts.put(StructureType.BURIED_TREASURE, new StructureInfo(1,0,0));
            structsetts.put(StructureType.MINESHAFT, new StructureInfo(1,0,0));
            structsetts.put(StructureType.RUINED_PORTAL,new StructureInfo(25,10, 34222645));
            structsetts.put(StructureType.SHIPWRECK, new StructureInfo(24,4,165745295));
            structsetts.put(StructureType.OCEAN_RUIN, new StructureInfo(20,8,14357621));
            structsetts.put(StructureType.BASTION_REMNANT, new StructureInfo(27,4,30084232));
            structsetts.put(StructureType.NETHER_FORTRESS, new StructureInfo(27,4,30084232));
            structsetts.put(StructureType.NETHER_FOSSIL, new StructureInfo(2,1,14357921));
        }


        private HashMap<StructureType, StructureInfo> structureInfos = new HashMap<>();

        /**
         * Creates the Structure-Object with all the default Structureinformation from the Overworld
         *
         * @param useOverworld - Whether the World has default Overworld-Settings or not
         * */
        public StructureGeneration(boolean useOverworld) {
            if(useOverworld){
                structureInfos = (HashMap<StructureType, StructureInfo>) structsetts.clone();
            }
        }

        /**
         * Creates the Structure-Object with no allowed Structure to spawn by default
         *
         * */
        public StructureGeneration() {
            this(false);
        }

        /**
         * Adds a StructureType with its StructureInfo to the StructureGeneration
         *
         * @param structureType The Structure to add
         * @param structureInfo The Create-Information
         * */
        public void addStructureTypeInfo(StructureType structureType,StructureInfo structureInfo) {
            if (structureType == null)
                throw new IllegalArgumentException("StructureType can not be null!");
            if (structureInfo == null)
                throw  new IllegalArgumentException("StructureInfo can not be null!");

            structureInfos.put(structureType,structureInfo);
        }

        /**
         * Gets the full Data of the StructureGeneration for the World
         *
         * @return The StructureGeneration-Data
         * */
        public HashMap<StructureType,StructureInfo> getStructureInfos() {
            return structureInfos;
        }



    }

    /**
     * Represents Methods to use for Configure the Generation of the World.
     * This includes also where the lowest Block of the World will generate or how tall the World will generate.
     *
     * */
    public static class NoiseGeneration{

        private int minY = -64;
        private int height = 384;
        private int noiseSizeHorizontal = 1;
        private int noiseSizeVertical = 2;
        private boolean largeBiomes = false;

        /**
         * Creates a default Overworld-like Noise-Generation
         * @see <a href="https://minecraft.fandom.com/wiki/Custom_world_generation">Noise settings</a>
         *
         * */
        public NoiseGeneration() {

        }

        /**
         * Sets the lowest Y-Coordinate e.g. where the Bottom-Bedrock will be set
         *
         * @param minY The lowest Y-Coordinate
         * */
        public void setMinY(int minY) {
            this.minY = minY;
        }

        /**
         * Sets the total height of the World
         * The highest Block where Blocks can be set is -minY+height
         *
         * @param height The total height
         * */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * Sets if the World is amplified or not
         *
         * @param amplified If amplified
         *
        public void setAmplified(boolean amplified) {
            isAmplified = amplified;
        }
        */



        /**
         * Sets if the World has a island Override
         * e.g. Make a island looking terrain
         *
         * @param islandNoiseOverride If island Override
         * *
        public void setIslandNoiseOverride(boolean islandNoiseOverride) {
            this.islandNoiseOverride = islandNoiseOverride;
        }*/

        /**
         * Sets if the World has larger Biomes
         *
         * @param largeBiomes If large Biomes is enabled
         * */
        public void setLargeBiomes(boolean largeBiomes) {
            this.largeBiomes = largeBiomes;
        }

        /**
         * Sets the Worlds horizontal noise
         *
         * @param noiseSizeHorizontal Horizontal Noise
         * */
        public void setNoiseSizeHorizontal(int noiseSizeHorizontal) {
            this.noiseSizeHorizontal = noiseSizeHorizontal;
        }

        /**
         * Sets the Worlds vertical noise
         *
         * @param noiseSizeVertical Vertical Noise
         * */
        public void setNoiseSizeVertical(int noiseSizeVertical) {
            this.noiseSizeVertical = noiseSizeVertical;
        }




        @Override
        public String toString() {
            return "NoiseGeneration{" +
                    "minY=" + minY +
                    ", height=" + height +
                    ", noiseSizeHorizontal=" + noiseSizeHorizontal +
                    ", noiseSizeVertical=" + noiseSizeVertical +
                    ", largeBiomes=" + largeBiomes +
                    '}';
        }

        /**
         * Gets the setting about if the World has larger Biomes
         *
         * @return If is large Biomes
         * */
        public boolean isLargeBiomes() {
            return largeBiomes;
        }


        /**
         * Gets the setting about the lowest World-Location where Blocks will be created while generation
         * e.g. Where the bottom Bedrock will spawn
         *
         * @return The lowest Y-Coordinate
         * */
        public int getMinY() {
            return minY;
        }

        /**
         * Gets the setting about the lowest World-Location where Blocks will be created while generation
         * e.g. Where the bottom Bedrock will spawn
         *
         * @return The height of the World
         * */
        public int getHeight() {
            return height;
        }

        /**
         * Gets the setting about the horizontal Noise
         *
         * @return The horizontal Noise
         * */
        public int getNoiseSizeHorizontal() {
            return noiseSizeHorizontal;
        }

        /**
         * Gets the setting about the vertical Noise
         *
         * @return The horizontal Noise
         * */
        public int getNoiseSizeVertical() {
            return noiseSizeVertical;
        }

    }

}

