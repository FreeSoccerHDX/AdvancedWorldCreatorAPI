package de.freesoccerhdx.advancedworldcreatorapi.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.AdvancedWorldCreatorAPI;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_18_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftSound;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.logging.Level;


/**
 * Represents a Creator for Biomes
 *
 * */
public class BiomeCreator implements Keyed {


    private BiomeCaveSound caveSound = null;
    private BiomeCaveSoundSettings caveSoundSettings = null;
    private Sound ambientSound = null;
    private BiomeParticle particle = null;

    private NamespacedKey key;

    private HashMap<BiomeDecorationType, List<BiomeDecoration>> biomeFeatures = new HashMap<>();
    private HashMap<BiomeDecorationType, List<CustomBiomeFeature>> biomeFeaturesNMS = new HashMap<>();

    private HashMap<BiomeFeatureType, List<BiomeCarvers>> biomeCarvers = new HashMap<>();

    //private List<BiomeFeature> biomeFeatures = new ArrayList<>();
    private List<BiomeStructure> allowed_BiomeStructures = new ArrayList<>();
    private List<BiomeEntity> biomeEntities = new ArrayList<>();

    private BiomeGrassColorModifier grassColorModifier = BiomeGrassColorModifier.NONE;

    private BiomeGeography geography = BiomeGeography.NONE;
    private BiomePrecipitation precipitation = BiomePrecipitation.NONE;
    private BiomeTemperatureModifier temperaturmodifier = BiomeTemperatureModifier.NONE;

    private BiomeMusic biomeMusic = null;

    private Color grass = null;
    private Color foliage = null;
    private Color sky = new Color(0, 0, 0);
    private Color waterfog = new Color(0,0,0);
    private Color fog = new Color(0,0,0);
    private Color water = new Color(0,0,0);

    //private float depth = 0.125F;
    //private float scale = 0.05F;
    private float temper = 0.8F;
    private float downfall = 0.4F;
    private float mobprobability = 0.1F;

    /**
     * Creates a BiomeCreator with the given Plugin as Namespace and Biomename as Key
     *
     * @param plugin The Plugin to use (as namespace for the CustomBiome)
     * @param biomename The Name of the Biome
     * */
    public BiomeCreator(Plugin plugin, String biomename){
        this.key = new NamespacedKey(plugin, biomename);
    }

    /**
     * Creates a BiomeCreator with a customable Namespace + Biomename
     *
     * @param namespace A customable Namespace for the CustomBiome (like minecraft:)
     * @param biomename The Name of the Biome
     * */
    public BiomeCreator(String namespace, String biomename){
        this.key = new NamespacedKey(namespace, biomename);
    }

    /**
     * Gets the specified Key(Namespace+Biomename) of the BiomeCreator
     *
     * @return NamespacedKey
     * */
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Gets all the current whitelisted {@link BiomeStructure} for the CustomBiome
     *
     * @return java.util.List A list with whitelisted Structures
     * */
    public List<BiomeStructure> getAllowedBiomeStructures(){
        return ImmutableList.copyOf(allowed_BiomeStructures);
    }

    /**
     * Whitelist/Blacklist a specific {@link BiomeStructure}
     * If BiomeStructure.VILLAGE is allowed it means it *can* spawn,
     * but it still depends on terrain and World-Configuration!
     *
     * @param biomeStructure The specific {@link BiomeStructure}
     * @param allowed Whether the Structure is now allowed or not
     * */
    public void setAllowedBiomeStructure(BiomeStructure biomeStructure, boolean allowed){
        if(allowed_BiomeStructures.contains(biomeStructure) && !allowed){
            allowed_BiomeStructures.remove(biomeStructure);
        }
        if(!allowed_BiomeStructures.contains(biomeStructure) && allowed){
            allowed_BiomeStructures.add(biomeStructure);
        }
    }

    /**
     * Checks if the given {@link BiomeStructure} is allowed to Generate in the World
     *
     * @param biomeStructure The specific {@link BiomeStructure} to check
     *
     * @return If the Structure is allowed
     * */
    public boolean isBiomeStructureAllowed(BiomeStructure biomeStructure){
        return allowed_BiomeStructures.contains(biomeStructure);
    }

    /**
     * Sets the Temperature of the CustomBiome.
     * This controls some Gameplay-Features like Snow-Golems taking damage.
     *
     * @param val Value between 0.0 and 1.0
     * */
    public void setTemperature(float val) {
        if(val < 1.0 && val >= 0.0){
            this.temper = val;
        }else{
            throw new IllegalArgumentException("Value is out of Range (0.0 >= val < 1.0)");
        }
    }

    /**
     * Gets the current Temperature of the CustomBiome.
     *
     * @return The Temperature
     * */
    public float getTemperature(){
        return this.temper;
    }

    /**
     * Sets the Downfall of the CustomBiome.
     * This controls some Gameplay-Features like how fast fire burn out
     *
     * @param val Value between 0.0 and 1.0
     * */
    public void setDownfall(float val) {
        if(val < 1.0 && val >= 0.0){
            this.downfall = val;
        }else{
            throw new IllegalArgumentException("Value is out of Range (0.0 >= val < 1.0)");
        }
    }

    /**
     * Gets the current Downfall of the CustomBiome.
     *
     * @return The Downfall
     * */
    public float getDownfall() {
        return this.downfall;
    }

    /**
     * Sets the color of the foliage(leaves, vines)
     *
     * @param col The Color to use for this or null
     * */
    public void setFoliageColor(Color col) {
        this.foliage = col;
    }

    /**
     * Gets the current Foliage-Color of the CustomBiome.
     *
     * @return The Foligae-Color or null
     * */
    public Color getFoliageColor(){
        return this.foliage;
    }

    /**
     * Sets the color of the water
     *
     * @param col The Color to use for this
     * */
    public void setWaterColor(Color col) {
        this.water = col;
    }

    /**
     * Gets the current Water-Color of the CustomBiome.
     *
     * @return The Water-Color
     * */
    public Color getWaterColor(){
        return this.water;
    }

    /**
     * Sets the color of the fog that starts far away from the player
     *
     * @param col The Color to use for this
     * */
    public void setFogColor(Color col) {
        this.fog = col;
    }

    /**
     * Gets the current Fog-Color of the CustomBiome.
     *
     * @return The Fog-Color
     * */
    public Color getFogColor(){
        return this.fog;
    }

    /**
     * Sets the color of the water-fog that starts far away from the player under water
     *
     * @param col The Color to use for this
     * */
    public void setWaterFogColor(Color col) {
        this.waterfog = col;
    }

    /**
     * Gets the current Water-Fog-Color of the CustomBiome.
     *
     * @return The Water-Fog-Color
     * */
    public Color getWaterFogColor(){
        return this.waterfog;
    }

    /**
     * Sets the color of the sky
     *
     * @param col The Color to use for this
     * */
    public void setSkyColor(Color col) {
        this.sky = col;
    }

    /**
     * Gets the current Sky-Color of the CustomBiome.
     *
     * @return The Sky-Color
     * */
    public Color getSkyColor(){
        return this.sky;
    }

    /**
     * Sets the color of Grass-Blocks
     *
     * @param col The Color to use for this
     * */
    public void setGrassColor(Color col) {
        this.grass = col;
    }

    /**
     * Gets the current Grass-Color of the CustomBiome.
     *
     * @return The Grass-Color
     * */
    public Color getGrassColor(){
        return this.grass;
    }

    /**
     * Allows the specific Entity to spawn in this Biome
     *
     * @param type {@link EntityType} (EntityType that will be allowed)
     * @param weight A value that is shared between all Entitys in the Biome (high value means more spawnchances)
     * @param mincount Value that defines the minimum of trys to spawn a Entity
     * @param maxcount Value that defines the maximum of trys to spawn a Entity
     *
     * @return Whether it was successful to add the Entity or not
     * */
    public boolean addEntityConfiguration(EntityType type, int weight, int mincount, int maxcount){
        return biomeEntities.add(new BiomeEntity(type,weight,mincount,maxcount));
    }
    /**
     * Allows the specific Entity to spawn in this Biome
     *
     * see {@link #addEntityConfiguration(EntityType, int, int, int)}
     * @param biomeEntity The final {@link BiomeEntity}-Object
     * @return If the {@link BiomeEntity} was added or not
     * */
    public boolean addEntityConfiguration(BiomeEntity biomeEntity){
        return biomeEntities.add(biomeEntity);
    }

    /**
     * Gets all the current allowed Entitys with their SpawnData compacted as {@link BiomeEntity}
     *
     * @return List with all {@link BiomeEntity}
     * */
    public List<BiomeEntity> getBiomeEntities(){
        return biomeEntities;
    }

    /**
     * Gets the specific {@link BiomeGeography} that is used for this Biome
     *
     * @return The BiomeGeography
     * */
    public BiomeGeography getGeography() {
        return this.geography;
    }

    /**
     * Sets the {@link BiomeGeography} to use for the CustomBiome
     *
     * @param geo The {@link BiomeGeography} to use
     * */
    public void setGeography(BiomeGeography geo) {
        this.geography = geo;
    }

    /**
     * Gets the current {@link BiomePrecipitation} to use for the CustomBiome
     *
     * @return The {@link BiomePrecipitation}
     * */
    public BiomePrecipitation getPrecipitation() {
        return this.precipitation;
    }

    /**
     * Sets the {@link BiomePrecipitation} to use for the CustomBiome
     *
     *
     * @param preci The {@link BiomePrecipitation} to use
     * */
    public void setPrecipitation(BiomePrecipitation preci) {
        this.precipitation = preci;
    }

    /**
     * Gets the current {@link BiomeTemperatureModifier} that is used for the CustomBiome
     *
     * @return The {@link BiomeTemperatureModifier} to use
     * */
    public BiomeTemperatureModifier getTemperatureModifier() {
        return this.temperaturmodifier;
    }

    /**
     * Sets the {@link BiomeTemperatureModifier} that should be used for the CustomBiome
     *
     * @param temp The {@link BiomeTemperatureModifier} to use
     * */
    public void setTemperatureModifier(BiomeTemperatureModifier temp) {
        this.temperaturmodifier = temp;
    }

    /**
     * Sets the {@link BiomeGrassColorModifier} that will be used for the GrassColor as modifier.
     *
     * @param modifier The {@link BiomeGrassColorModifier} to use
     * */
    public void setBiomeGrassColorModifier(BiomeGrassColorModifier modifier){
        this.grassColorModifier = modifier;
    }

    /**
     * Gets the {@link BiomeGrassColorModifier} of the CustomBiome
     *
     * @return The current BiomeGrassColorModifier
     * */
    public BiomeGrassColorModifier getBiomeGrassColorModifier(){
        return this.grassColorModifier;
    }

    /**
     * Sets the MobSpawn-Probability of the CustomBiome
     * Larger values result in more Spawn-Attemps for Entitys
     *
     * @param val Value between 0.0 and 1.0
     * */
    public void setMobSpawnProbability(float val) {
        if(val < 1.0 && val >= 0.0){
            this.mobprobability = val;
        }else{
            throw new IllegalArgumentException("Value is out of Range (0.0 >= val < 1.0)");
        }
    }

    /**
     * Gets the current MobSpawnProbability of the CustomBiome
     *
     * @return Current MobSpawnProbability
     * */
    public float getMobSpawnProbability() {
        return mobprobability;
    }


    /**
     * Adds a Decoration (ore, trees...) to the CustomBiome
     *
     * @param biomeFeature Defines the Decoration to generate in the CustomBiome
     * @param decorationType Specifies the Type and how to generate the Decoration
     * @return If the BiomeDecoration was added or not.
     * */
    public boolean addBiomeFeature(BiomeDecorationType decorationType, CustomBiomeFeature biomeFeature){
        if(!this.biomeFeaturesNMS.containsKey(decorationType)) {
            this.biomeFeaturesNMS.put(decorationType,new ArrayList<>());
        }
        return biomeFeaturesNMS.get(decorationType).add(biomeFeature);
    }

    /**
     * Adds a Decoration (ore, trees...) to the CustomBiome
     *
     * @param biomeFeature Defines the Decoration to generate in the CustomBiome
     * @param decorationType Specifies the Type and how to generate the Decoration
     * @return If the BiomeDecoration was added or not.
     * */
    public boolean addBiomeFeature(BiomeDecorationType decorationType, BiomeDecoration biomeFeature){
        if(!this.biomeFeatures.containsKey(decorationType)) {
            this.biomeFeatures.put(decorationType,new ArrayList<>());
        }
        return biomeFeatures.get(decorationType).add(biomeFeature);
    }

    /**
     * Gets a List of added Decorations of the CustomBiome
     *
     * @param decorationType The DecorationType to search for
     * @return A List of {@link BiomeDecoration} for the given DecorationType or null
     * */
    public List<BiomeDecoration> getBiomeFeatures(BiomeDecorationType decorationType){
        if(this.biomeFeatures.containsKey(decorationType)) {
            return this.biomeFeatures.get(decorationType);
        }
        return null;
    }

    /**
     * Gets all currently added Decorations with their DecorationType
     *
     * @return A Map of DecorationTypes with a List of added Decorations
     * */
    public HashMap<BiomeDecorationType, List<CustomBiomeFeature>> getAllCustomBiomeFeatures(){
        return biomeFeaturesNMS;
    }


    /**
     * Gets all currently added Decorations with their DecorationType
     *
     * @return A Map of DecorationTypes with a List of added Decorations
     * */
    public HashMap<BiomeDecorationType, List<BiomeDecoration>> getAllBiomeFeatures(){
        return biomeFeatures;
    }

    /**
     * Adds a Cave/Canyon-Type to the CustomBiome that will generate with Air/Liquid
     *
     * @param featureType Type(Air/Liquid) of the Carver
     * @param biomeCarver The Carver-Generation-Type
     * @return If the Carver was added or not
     * */
    public boolean addBiomeCarver(BiomeFeatureType featureType, BiomeCarvers biomeCarver){
        if(!biomeCarvers.containsKey(featureType)){
            biomeCarvers.put(featureType,new ArrayList<>());
        }
        return biomeCarvers.get(featureType).add(biomeCarver);
    }

    /**
     * Gets a List of {@link BiomeCarvers} linked to the {@link BiomeFeatureType}
     *
     * @param featureType The FeatureType to search for
     * @return A List of Carvers for the given FeatureType
     * */
    public List<BiomeCarvers> getBiomeCarvers(BiomeFeatureType featureType){
        if(biomeCarvers.containsKey(featureType)){
            return biomeCarvers.get(featureType);
        }
        return null;
    }

    /**
     * Gets all added {@link BiomeCarvers} and their {@link BiomeFeatureType} of the CustomBiome
     *
     * @return A Map of {@link BiomeCarvers} linked to the {@link BiomeFeatureType}
     * */
    public HashMap<BiomeFeatureType, List<BiomeCarvers>> getAllBiomeCarvers(){
        return biomeCarvers;
    }

    /**
     * Sets the {@link BiomeMusic} that will randomly starts playing in the CustomBiome
     *
     * @param sound The Sound/Music to play
     * @param min_delay Minimum ticks before Music will start
     * @param max_delay Maximum ticks before Music will start
     * @param replace_current_music Whether it will replace active Music or not
     * */
    public void setCustomMusic(Sound sound, int min_delay, int max_delay, boolean replace_current_music){
        this.setCustomMusic(new BiomeMusic(sound,min_delay,max_delay,replace_current_music));
    }

    /**
     * Sets the {@link BiomeMusic} that will randomly starts playing in the CustomBiome
     *
     * @param biomeMusic see {@link #setCustomMusic(Sound, int, int, boolean)}
     * */
    public void setCustomMusic(BiomeMusic biomeMusic){
        this.biomeMusic = biomeMusic;
    }

    /**
     * Gets the current {@link BiomeMusic} of the CustomBiome or null if not set
     *
     * @return The BiomeMusic or null
     * */
    public BiomeMusic getCustomMusic(){
        return biomeMusic;
    }

    /**
     * Sets the {@link BiomeParticle} that will randomly appear in the CustomBiome
     *
     * @param particle The {@link BiomeParticle} that will be used in the Biome
     * */
    public void setAmbientParticle(BiomeParticle particle){
        this.particle = particle;
    }

    /**
     * Gets the {@link BiomeParticle} of the CustomBiome if it was set
     *
     * @return The BiomeParticle or null
     * */
    public BiomeParticle getParticle() {
        return particle;
    }

    /**
     * Sets the Sound that will randomly play in the CustomBiome
     *
     * @param sound The Ambient-Sound
     * */
    public void setAmbientSound(Sound sound){
        ambientSound = sound;
    }

    /**
     * Gets the Sound that will randomly play in the CustomBiome or null if not set
     *
     * @return The Ambient-Sound or null
     * */
    public Sound getAmbientSound() {
        return ambientSound;
    }

    /**
     * Sets the {@link BiomeCaveSound} that will play randomly in the underground
     *
     * @param caveSound The {@link BiomeCaveSound} that will play randomly in the underground
     * */
    public void setCaveSound(BiomeCaveSound caveSound){
        this.caveSound = caveSound;
    }

    /**
     * Gets the current {@link BiomeCaveSound} for the CustomBiome or null if not set
     *
     * @return The {@link BiomeCaveSound} or null
     * */
    public BiomeCaveSound getCaveSound(){
        return caveSound;
    }

    /**
     * Sets the {@link BiomeCaveSoundSettings} for the CustomBiome
     * It will randomly play the Sound in a random distance of the Player *scary*
     *
     * @param caveSoundSettings The {@link BiomeCaveSoundSettings} to use
     * */
    public void setCaveSoundSettings(BiomeCaveSoundSettings caveSoundSettings){
        this.caveSoundSettings = caveSoundSettings;
    }

    /**
     * Gets the {@link BiomeCaveSoundSettings} that will be used for the CustomBiome or null if not set
     *
     * @return The {@link BiomeCaveSoundSettings} or null
     * */
    public BiomeCaveSoundSettings getCaveSoundSettings(){
        return caveSoundSettings;
    }



    private static int colorToHexaDecimal(Color col){
        String hex = String.format("%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());
        return Integer.parseInt(hex, 16);
    }
    private static net.minecraft.world.entity.EntityType convertBukkitEntityTypeToNMS(EntityType entitytype) {
        try {
            return net.minecraft.world.entity.EntityType.byString(entitytype.getKey().getKey()).orElse(null);

        }catch(IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomBiome without replacing an existing one
     * see {@link #createBiome(boolean)}
     *
     * @return The CustomBiome that was created - or the existing one
     * */
    public CustomBiome createBiome(){
        return createBiome(false);
    }

    /**
     * Creates the CustomBiome and replaces the existing one if replace=true
     *
     * @param replace Whether to replace the existing CustomBiome with the new Settings or not
     * @return The CustomBiome that was created or replaced
     * */
    public CustomBiome createBiome(boolean replace){

        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        RegistryAccess registryAccess = craftServer.getServer().registryAccess();
        WritableRegistry<Biome> biomeRegistry = registryAccess.ownedRegistryOrThrow(Registry.BIOME_REGISTRY);

        String namespace = getKey().getNamespace();
        String key = getKey().getKey();
        ResourceKey<Biome> resourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(namespace,key));

        Optional<Biome> optionalBiome = BuiltinRegistries.BIOME.getOptional(resourceKey);

        if(optionalBiome.isPresent() && !replace){
            Biome endbiome = optionalBiome.get();
            CustomBiome customBiome = new CustomBiome(endbiome, BuiltinRegistries.BIOME.getResourceKey(endbiome).get());
            return customBiome;
        }

        Biome createdBiomeBase = createBiomeBase();

        if(!optionalBiome.isPresent()) {
            //BuiltinRegistries.BIOME.register(resourceKey,createdBiomeBase,Lifecycle.stable());
            biomeRegistry.register(resourceKey,createdBiomeBase,Lifecycle.stable());
            Biome biomedata = BuiltinRegistries.registerMapping(BuiltinRegistries.BIOME, resourceKey, createdBiomeBase);
            //optionalBiome = biomeRegistry.getOptional(resourceKey);
            optionalBiome = BuiltinRegistries.BIOME.getOptional(resourceKey);
         //   System.out.print("was not there: now ? " + optionalBiome.isPresent());
        }else{
            Biome targetBiome = optionalBiome.get();

            boolean overwrite = overwriteFields(targetBiome,createdBiomeBase);

            if(overwrite) {
                AdvancedWorldCreatorAPI.main.getLogger().log(Level.INFO, "Custom-Biome was replaced successfully!");
            }

         //   AdvancedWorldCreatorAPI.main.getLogger().log(Level.INFO, "Custom-Biome was replaced successfully!");
            optionalBiome = BuiltinRegistries.BIOME.getOptional(resourceKey);
          //  System.out.println("Optional: " + optionalBiome.isPresent());
        }


        Biome endbiome = optionalBiome.get();

        CustomBiome customBiome = new CustomBiome(endbiome, BuiltinRegistries.BIOME.getResourceKey(endbiome).get());
       // System.out.println("hasThis now ? " + BuiltinRegistries.BIOME.getResourceKey(endbiome).isPresent());
        return customBiome;
    }



    private boolean overwriteFields(Biome target, Biome input){
        try {
/*
*
*           it.unimi.dsi.fastutil.objects.ObjectList<Biome> bz = null; //byId
            it.unimi.dsi.fastutil.objects.Object2IntMap<Biome> bA = null; //toId
            com.google.common.collect.BiMap<ResourceLocation, Biome> bB = null; //storage
            com.google.common.collect.BiMap<ResourceKey<Biome>, Biome> bC = null; //keyStorage
            java.util.Map<Biome, Lifecycle> bD = null; //lifecycles
* */

            for (Field field : Biome.class.getDeclaredFields()) {
                String s = field.getName();
              //  Bukkit.broadcastMessage("name: " + s + " -> " + field.getType());
                if(s.equals("j") || s.equals("k") || s.equals("l") || s.equals("m") || s.equals("n")) {
                    field.setAccessible(true);


                    //AccessibleObject.setAccessible(new AccessibleObject[]{field},true);

                    Object newvalue = field.get(input);
                    field.set(target, newvalue);
                }
            }

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Represents the created Biome with the NMS-Data stored
     *
     * */
    public class CustomBiome{

        private ResourceKey<Biome> resourceKey;
        private Biome biome;

        private CustomBiome(Biome biome, ResourceKey<Biome> resourceKey){
            Objects.requireNonNull(biome,"The BiomeBase can't be null!");
            Objects.requireNonNull(resourceKey,"The ResourcKey can't be null!");

            this.biome = biome;
            this.resourceKey = resourceKey;
        }

        /**
         * Only for intern uses: public
         *
         * @return NMS-Biome "BiomeBase"
         * */
        public Biome getNMSBiome(){
            return biome;
        }

        /**
         * Only for intern uses
         *
         * @return The NMS-ResourceKey
         * */
        protected ResourceKey<Biome> getResourceKey(){
            return resourceKey;
        }


    }



    private Biome createBiomeBase() {

        BiomeGenerationSettings.Builder bisege = (new BiomeGenerationSettings.Builder());

        for(BiomeDecorationType decorationType : getAllBiomeFeatures().keySet()){
            for(BiomeDecoration biomeFeature : biomeFeatures.get(decorationType)){
                bisege.addFeature(decorationType.get(),biomeFeature.getFeature());
            }
        }
        for(BiomeDecorationType decorationType : getAllCustomBiomeFeatures().keySet()){
            for(CustomBiomeFeature biomeFeature : biomeFeaturesNMS.get(decorationType)){
                bisege.addFeature(decorationType.get(),biomeFeature.getFeature());
            }
        }




        MobSpawnSettings.Builder bisemo = new MobSpawnSettings.Builder();

        bisemo.creatureGenerationProbability(getMobSpawnProbability()); //setMobSpawnProbability

        for(BiomeEntity be : getBiomeEntities()){
            net.minecraft.world.entity.EntityType nmsentity = convertBukkitEntityTypeToNMS(be.getEntityType());

            if(nmsentity != null){
                MobCategory enumCreatureType = nmsentity.getCategory();

                bisemo.addSpawn(enumCreatureType, new MobSpawnSettings.SpawnerData(nmsentity, be.getWeight(), be.getMinCount(), be.getMaxCount()));

            }

        }



        BiomeSpecialEffects.Builder bf = new BiomeSpecialEffects.Builder();


        bf.fogColor(colorToHexaDecimal(getFogColor()));
        bf.waterColor(colorToHexaDecimal(getWaterColor()));
        bf.waterFogColor(colorToHexaDecimal(getWaterFogColor()));
        bf.skyColor(colorToHexaDecimal(getSkyColor()));

        if(getFoliageColor() != null) {
            bf.foliageColorOverride(colorToHexaDecimal(getFoliageColor()));
        }
        if(getGrassColor() != null) {
            bf.grassColorOverride(colorToHexaDecimal(getGrassColor()));
        }

        BiomeMusic music = getCustomMusic();
        if(music != null) {
            bf.backgroundMusic(new Music(CraftSound.getSoundEffect(music.getSound()),music.getMinDelay(),music.getMaxDelay(),music.isReplacingCurrentMusic()));
        }
        BiomeCaveSound caveSound = getCaveSound();
        if(caveSound != null) {
            bf.ambientAdditionsSound(new AmbientAdditionsSettings(CraftSound.getSoundEffect(caveSound.getSound()),caveSound.getTickChance()));
        }

        BiomeCaveSoundSettings caveSoundSettings = getCaveSoundSettings();
        if(caveSoundSettings != null){
            bf.ambientMoodSound(new AmbientMoodSettings(CraftSound.getSoundEffect(caveSoundSettings.getSound()),caveSoundSettings.getTickDelay(),caveSoundSettings.getBlockSearchExtent(),caveSoundSettings.getOffset()));
        }
        Sound ambientSound = getAmbientSound();
        if(ambientSound != null) {
            bf.ambientLoopSound(CraftSound.getSoundEffect(ambientSound));
        }
        BiomeParticle particle = getParticle();
        if(particle != null) {
            bf.ambientParticle(new AmbientParticleSettings(CraftParticle.toNMS(particle.getParticle()), particle.getQuantity()));
        }

        bf.grassColorModifier(getBiomeGrassColorModifier().getGrassColorModifier());

        Biome.BiomeBuilder bb = new Biome.BiomeBuilder();



        bb.biomeCategory(getGeography().getGeography());
        bb.precipitation(getPrecipitation().getPrecipitation());
        bb.temperatureAdjustment(getTemperatureModifier().getTemperaturModifier());
        bb.specialEffects(bf.build());
        bb.generationSettings(bisege.build());
        bb.mobSpawnSettings(bisemo.build());

        //0.0-1.0: Controls gameplay features like grass and foliage color and whether snow golems take damage.
        bb.temperature(getTemperature());
        //0.0-1.0: Controls grass and foliage color, a value above 0.85 also makes fire burn out faster.
        bb.downfall(getDownfall());

        return bb.build();
    }


}
