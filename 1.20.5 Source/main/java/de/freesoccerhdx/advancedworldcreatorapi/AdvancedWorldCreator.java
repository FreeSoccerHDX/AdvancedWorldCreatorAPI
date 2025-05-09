package de.freesoccerhdx.advancedworldcreatorapi;

import org.bukkit.World;
import org.bukkit.WorldCreator;

/**
 * This class is used to create a new world with the AdvancedWorldCreatorAPI
 */
public class AdvancedWorldCreator extends WorldCreator {

    private EnvironmentBuilder environmentBuilder = null;
    private GeneratorConfiguration generatorConfiguration = null;
    //private AdvancedBiomeProvider advancedBiomeProvider = null;

    private StructurePlacementOverride structurePlacementOverride = null;

    /**
     * Creates an empty WorldCreationOptions for the given world name
     *
     * @param worldname Name of the world that will be created
     */
    public AdvancedWorldCreator(String worldname) {
        super(worldname);
    }


    public StructurePlacementOverride getStructurePlacementOverride() {
        return structurePlacementOverride;
    }

    public void setStructurePlacementOverride(StructurePlacementOverride structurePlacementOverride) {
        this.structurePlacementOverride = structurePlacementOverride;
    }

    /**
     * Sets the {@link GeneratorConfiguration} to create a more modifier World.
     * null as Argument will remove the {@link GeneratorConfiguration} and will
     * generate the World with vanilla behaviour
     *
     * @param generatorConfiguration The Custom GeneratorConfiguration
     */
    public void setGeneratorConfiguration(GeneratorConfiguration generatorConfiguration) {
        this.generatorConfiguration = generatorConfiguration;
    }

    /**
     * Gets the {@link GeneratorConfiguration} or null if not set before
     *
     * @return GeneratorConfiguration
     */
    public GeneratorConfiguration getGeneratorConfiguration() {
        return generatorConfiguration;
    }


    /**
     * Sets the {@link EnvironmentBuilder} to create a Custom {@link org.bukkit.World.Environment}
     * null as Argument will remove the {@link EnvironmentBuilder} and will set the {@link org.bukkit.World.Environment}
     * to Normal, otherwise to Custom
     *
     * @param environmentBuilder The Builder for the Custom Environment
     */
    public void setEnvironmentBuilder(EnvironmentBuilder environmentBuilder) {
        this.environmentBuilder = environmentBuilder;
        if (environmentBuilder == null) {
            environment(World.Environment.NORMAL);
        } else {
            environment(World.Environment.CUSTOM);
        }
    }

    /**
     * Gets the {@link EnvironmentBuilder} or null if not set before
     *
     * @return The @{@link EnvironmentBuilder}
     * */
    public EnvironmentBuilder getEnvironmentBuilder() {
        return environmentBuilder;
    }


    @Override
    public World createWorld() {
       return AdvancedWorldCreatorAPI.createWorld(this);
    }

//    /**
//     * Can be used to set a {@link AdvancedBiomeProvider} with {@link de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator.CustomBiome}
//     * Only works if you don't set a Bukkit-BiomeProvider!
//     *
//     * */
//    public void setAdvancedBiomeProvider(AdvancedBiomeProvider advancedBiomeProvider){
//        this.advancedBiomeProvider = advancedBiomeProvider;
//    }
//
//    /**
//     * Gets the {@link AdvancedBiomeProvider} or null if not set.
//     *
//     * @return The {@link AdvancedBiomeProvider} or null if not set.
//     * */
//    public AdvancedBiomeProvider getAdvancedBiomeProvider(){
//        return advancedBiomeProvider;
//    }
//
//





}
