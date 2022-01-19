package de.freesoccerhdx.advancedworldcreatorapi;

import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

/**
 * Represents various types of options that may change the Environment of a World created with the WorldCreator
 */
public class EnvironmentBuilder {

    private Long fixedTime = null;
    private boolean hasSkylight = true;
    private boolean hasCeiling = true;
    private boolean ultraWarm = false;
    private boolean natural = true;
    private double coordinateScale = 1.0;
    private boolean createDragonFight = false;
    private boolean piglinSafe = false;
    private boolean bedWorks = true;
    private boolean respawnAnchorWorks = false;
    private boolean hasRaids = true;
    private int minY = -64;
    private int height = 384;
    private int logicalHeight = 384;
    private NamespacedKey infiniburn = Tag.INFINIBURN_OVERWORLD.getKey();
    private NamespacedKey effectsLocation = NamespacedKey.minecraft("overworld");
    private float ambientLight = 0.0f;


    /**
     * Creates an EnvironmentBuilder with default Overworld-Settings
     *
     * */
    public EnvironmentBuilder() {
    }

    /**
     * Creates an EnvironmentBuilder with every Option this Class has as Argument
     *
     * @param fixedTime see {@link #setFixedTime(Long)}}
     * @param hasSkylight see {@link #setHasSkylight(boolean)}
     * @param hasCeiling see {@link #setHasCeiling(boolean)}
     * @param ultraWarm see {@link #setUltraWarm(boolean)}
     * @param natural see {@link #setNatural(boolean)}
     * @param coordinateScale see {@link #setCoordinateScale(double)}
     * @param createDragonFight see {@link #setCreateDragonFight(boolean)}
     * @param piglinSafe see {@link #setPiglinSafe(boolean)}
     * @param bedWorks see {@link #setBedWorks(boolean)}
     * @param respawnAnchorWorks see {@link #setRespawnAnchorWorks(boolean)}
     * @param hasRaids see {@link #setHasRaids(boolean)}
     * @param minY see {@link #setMinY(int)}
     * @param height see {@link #setHeight(int)}
     * @param logicalHeight see {@link #setLogicalHeight(int)}
     * @param infiniburn see {@link #setInfiniburn(NamespacedKey)}
     * @param effectsLocation see {@link #setEffectsLocation(NamespacedKey)}
     * @param ambientLight see {@link #setAmbientLight(Float)}
     *
     * */
    public EnvironmentBuilder(Long fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean createDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int minY, int height, int logicalHeight, NamespacedKey infiniburn, NamespacedKey effectsLocation, float ambientLight) {
        this.fixedTime = fixedTime;
        this.hasSkylight = hasSkylight;
        this.hasCeiling = hasCeiling;
        this.ultraWarm = ultraWarm;
        this.natural = natural;
        this.coordinateScale = coordinateScale;
        this.createDragonFight = createDragonFight;
        this.piglinSafe = piglinSafe;

        this.bedWorks = bedWorks;
        this.respawnAnchorWorks = respawnAnchorWorks;
        this.hasRaids = hasRaids;
        this.minY = minY;
        this.height = height;
        this.logicalHeight = logicalHeight;
        this.infiniburn = infiniburn;
        this.effectsLocation = effectsLocation;
        this.ambientLight = ambientLight;
    }

    /**
     * How much light the dimension has, default is 0.1 for the Nether and 0.0 for Overworld and End
     *
     * @param ambientLight A float to set the Ambient-Light
     * @return This object, for chaining
     */
    public EnvironmentBuilder setAmbientLight(Float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * When false, the bed blows up when trying to sleep.
     *
     * @param bedWorks Whether to allow Beds
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setBedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    /**
     * The multiplier applied to coordinates when traveling to the dimension.
     * Nether has 8.0 while End and Overworld have 1.0
     *
     * @param coordinateScale The scale of the coordinates
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setCoordinateScale(double coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    /**
     * Whether the World will have a Dragon at X=0 and Z=0
     * This will not generate End-Towers or the End-Portal,
     * but will create End-Gateways when the Enderdragon dies
     *
     * @param createDragonFight Whether allow a Dragonfight
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setCreateDragonFight(boolean createDragonFight) {
        this.createDragonFight = createDragonFight;
        return this;
    }

    /**
     * Can be "minecraft:overworld", "minecraft:the_nether" and "minecraft:the_end"
     * minecraft:overworld: World will have clouds, sun, stars and moon (like the Overworld)
     * the_nether: World will have thick fog that blocks sight (like the Nether)
     * the_end: World will have dark spotted sky (like the End)
     *
     * @param effectsLocation {@link NamespacedKey} to use for World-Effects
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setEffectsLocation(NamespacedKey effectsLocation) {
        this.effectsLocation = effectsLocation;
        return this;
    }

    /**
     * Can be null or any value from 0 to 24000
     * If set to null the World will have a normal Day and Night Cycle
     *
     * @param fixedTime Time to set
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setFixedTime(Long fixedTime) {
        this.fixedTime = fixedTime;
        return this;
    }

    /**
     * Whether the dimension has a bedrock ceiling or not.
     * If set to true it will generate Bedrock at the Top like in the Nether
     *
     * @param hasCeiling If a Ceiling should generate
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setHasCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    /**
     * Whether players with the Bad Omen effect can cause a raid.
     *
     * @param hasRaids If a Bad Omen can trigger
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setHasRaids(boolean hasRaids) {
        this.hasRaids = hasRaids;
        return this;
    }

    /**
     * Whether the World has skylight access or not.
     * If set to false Mobs will probably spawn all the time at the surface
     *
     * @param hasSkylight If the World has Skylight
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setHasSkylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    /**
     * The total height in which blocks can exist within this World.
     * This will count up from the {@link #setMinY(int)}:
     * The maxY will be minY+Height (assuming minY is the exact value(-64 by default))
     * Should be between 15 and 4064 and be a multiple of 16.
     * Note: THIS WILL NOT GENERATE EXTRA TERRAIN!
     * It only allows to place blocks there.
     * See {@link GeneratorConfiguration} to change the Terrain too.
     *
     * @param height The total height
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setHeight(int height) {
        if (height > 4064 || height < 16 || height%16 != 0)
            throw new IllegalStateException("The height("+height+") is below 16 or not a multiple of 16 or is greater 4064");
        this.height = height;
        return this;
    }

    /**
     * A resource location defining what block tag to use for infiniburn.
     * Blocks with the same resource location will burn normally forever.
     * see {@link Tag} for some examples to use
     *
     * @param infiniburn The {@link NamespacedKey} to use
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setInfiniburn(NamespacedKey infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    /**
     * The maximum height to which chorus fruits and nether portals
     * can bring players within this World.
     * This excludes portals that were already built above the limit
     * as they still connect normally. May not be greater than height.
     *
     * @param logicalHeight The logical Height of the World
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setLogicalHeight(int logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }
    /**
     * The minimum height in which blocks can exist within this dimension.
     * Should be between -2032 and 2016 and be a multiple of 16.
     *
     * @param minY The lowest Y-Coordinate for usage
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setMinY(int minY) {
        if (minY > 2016 || minY < -2032 || minY%16 != 0)
            throw new IllegalArgumentException("The minY("+minY+") is below -2032 or higher then 2016 or not a multiple of 16!");
        this.minY = minY;
        return this;
    }
    /**
     * When false, compasses spin randomly, and using a bed
     * to set the respawn point or sleep, is disabled.
     * When true, nether portals can spawn zombified piglins.
     *
     * @param natural If the World is natural
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setNatural(boolean natural) {
        this.natural = natural;
        return this;
    }
    /**
     * Whether piglins shake and transform to zombified piglins.
     *
     * @param piglinSafe If the World is safe for piglins
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setPiglinSafe(boolean piglinSafe) {
        this.piglinSafe = piglinSafe;
        return this;
    }

    /**
     * Whether players can charge and use respawn anchors.
     *
     * @param respawnAnchorWorks If Players can use respawn anchors
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setRespawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }
    /**
     * Whether the dimensions behaves like the nether
     * (water evaporates and sponges dry) or not.
     *
     * @param ultraWarm If the World is ultra Warm
     * @return This object, for chaining
     * */
    public EnvironmentBuilder setUltraWarm(boolean ultraWarm) {
        this.ultraWarm = ultraWarm;
        return this;
    }

    /**
     * Gets the settings about if Bed Works
     *
     * @return If Bed works in the World
     */
    public boolean isBedWorks() {
        return bedWorks;
    }

    /**
     * Gets the settings about if a Enderdragon-Fight will be created
     *
     * @return If a Enderdragon-FIght will be created
     */
    public boolean isCreateDragonFight() {
        return createDragonFight;
    }

    /**
     * Gets the settings about if Bedrock at the top of the World will generate
     *
     * @return If Top-Bedrock will generate
     */
    public boolean isHasCeiling() {
        return hasCeiling;
    }

    /**
     * Gets the settings about if Players with Bad Omen-Effect can trigger Raids
     *
     * @return If Bad Omen triggers Raids
     */
    public boolean isHasRaids() {
        return hasRaids;
    }

    /**
     * Gets the settings about the World has Skylight
     *
     * @return If the World has Skylight
     */
    public boolean isHasSkylight() {
        return hasSkylight;
    }

    /**
     * Gets the settings about the World is natural or not
     *
     * @return If the World is natural
     */
    public boolean isNatural() {
        return natural;
    }

    /**
     * Gets the settings about if Piglins shake and transform
     *
     * @return If the World is safe for Piglins
     */
    public boolean isPiglinSafe() {
        return piglinSafe;
    }

    /**
     * Gets the settings about if Players can use respawn anchors.
     *
     * @return If the respawn anchors will work
     */
    public boolean isRespawnAnchorWorks() {
        return respawnAnchorWorks;
    }

    /**
     * Gets the settings about if Players with Bad Omen-Effect can trigger Raids
     *
     * @return If Bad Omen triggers Raids
     */
    public boolean isUltraWarm() {
        return ultraWarm;
    }

    /**
     * Gets the settings about the scale of Coordinates when using Portals
     *
     * @return Gets the Coordinate scale
     */
    public double getCoordinateScale() {
        return coordinateScale;
    }

    /**
     * Gets the settings about if there is ambient light
     *
     * @return Gets the ambient light
     */
    public float getAmbientLight() {
        return ambientLight;
    }

    /**
     * Gets the settings about the total height of the World
     *
     * @return The total Height of the World
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the settings about the logical height of the World
     *
     * @return The logical height of the World
     */
    public int getLogicalHeight() {
        return logicalHeight;
    }

    /**
     * Gets the settings about the lowest usable Y-Coordinate in the World
     *
     * @return The lowest Y-Coordinate
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Gets the settings about if the World has a fixed Worldtime or null for default Day-Night-Time
     *
     * @return Gets the fixed Worldtime or null
     */
    public Long getFixedTime() {
        return fixedTime;
    }

    /**
     * Gets the settings about the resource location for effects in the World
     *
     * @return The {@link NamespacedKey} for effects
     */
    public NamespacedKey getEffectsLocation() {
        return effectsLocation;
    }

    /**
     * Gets the settings about the resource location for infinity burning blocks in the World
     *
     * @return The {@link NamespacedKey} for infinity burning blocks
     */
    public NamespacedKey getInfiniburn() {
        return infiniburn;
    }

}
