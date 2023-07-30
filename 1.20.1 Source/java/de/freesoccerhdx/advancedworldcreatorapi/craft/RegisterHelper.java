package de.freesoccerhdx.advancedworldcreatorapi.craft;

import com.mojang.serialization.Lifecycle;
import de.freesoccerhdx.advancedworldcreatorapi.*;
import net.minecraft.core.Holder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData;

import java.util.Optional;
import java.util.OptionalLong;

public class RegisterHelper {

    public static boolean registerCustomEnvironment(EnvironmentBuilder builder) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        ResourceKey<DimensionType> resourceKeyDimension = NMSHelper.createResourceKey(builder.getKey(), Registries.DIMENSION_TYPE);
        WritableRegistry<DimensionType> registryDimensions = NMSHelper.getWritableRegistry(Registries.DIMENSION_TYPE);

        TagKey infiniBurn = TagKey.create(Registries.BLOCK, new ResourceLocation(builder.getInfiniburn().getKey().getNamespace(),builder.getInfiniburn().getKey().getKey()));

        int monsterSpawnLightTest = builder.getMonsterSpawnLightTest();
        int monsterSpawnBlockLightLimit = builder.getMonsterSpawnBlockLightLimit();

        DimensionType.MonsterSettings monsterSettings = new DimensionType.MonsterSettings(builder.isPiglinSafe(),builder.isHasRaids(), ConstantInt.of(monsterSpawnLightTest), monsterSpawnBlockLightLimit);

        DimensionType dimensionManager = new DimensionType(
                builder.getFixedTime() == null ? OptionalLong.empty() : OptionalLong.of(builder.getFixedTime()),
                builder.isHasSkylight(),
                builder.isHasCeiling(),
                builder.isUltraWarm(),
                builder.isNatural(),
                builder.getCoordinateScale(),
                builder.isBedWorks(),
                builder.isRespawnAnchorWorks(),
                builder.getMinY(),
                builder.getHeight(),
                builder.getLogicalHeight(),
                infiniBurn,
                new ResourceLocation(builder.getEffectsLocation().getNamespace(), builder.getEffectsLocation().getKey()),
                builder.getAmbientLight(),
                monsterSettings);



        boolean exist = registryDimensions.get(resourceKeyDimension) != null;

        if(!exist || builder.isOverwriteSettingsIfExist()) {
            Holder.Reference<DimensionType> registered = registryDimensions.register(resourceKeyDimension, dimensionManager, Lifecycle.stable());
            NMSHelper.setPrivateField(registered, "e", dimensionManager); //Field: value
            // Bukkit.broadcastMessage("§c[TEST] registering new dimension[nonnull? "+(dimensionManager!=null)+"]: " + resourceKeyDimension + " -> " + registered);
            //registryDimensions.register(resourceKeyDimension, dimensionManager, Lifecycle.stable());
            return true;
        }else if(exist){
            return true;
        }

        return false;
    }

    public static boolean registerGeneratorConfiguration(GeneratorConfiguration generatorConfiguration) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        GeneratorConfiguration.NoiseGeneration noiseGeneration = generatorConfiguration.getNoiseGeneration();

        WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);
        ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = NMSHelper.createResourceKey(generatorConfiguration.getKey(), Registries.NOISE_SETTINGS);

        boolean alreadyExist = registryGeneratorSettings.get(generatorSettingBaseResourceKey) != null;
        if(alreadyExist && !generatorConfiguration.isOverwriteSettingsIfExist()) {
            return true;
        }

        NoiseSettings noiseSettings = new NoiseSettings(noiseGeneration.getMinY(), noiseGeneration.getHeight(), noiseGeneration.getNoiseSizeHorizontal(), noiseGeneration.getNoiseSizeVertical());

        BlockState baseBlock = ((CraftBlockData)Bukkit.createBlockData(generatorConfiguration.getDefaultBlock())).getState();
        BlockState baseFluid = ((CraftBlockData)Bukkit.createBlockData(generatorConfiguration.getDefaultFluid())).getState();


        SurfaceRule surfaceRule = generatorConfiguration.getSurfaceRule();
        SurfaceRules.RuleSource ruleSource = null;

        switch(surfaceRule) {
            case OVERWORLD:
                ruleSource = SurfaceRuleData.overworld();
                break;
            case NETHER:
                ruleSource = SurfaceRuleData.nether();
                break;
            case AIR:
                ruleSource = SurfaceRuleData.air();
                break;
            case END:
                ruleSource = SurfaceRuleData.end();
                break;
        }

        NoiseRouterData noiseRouterData = generatorConfiguration.getNoiseRouterData();
        NoiseGeneratorSettings noiseRouterGeneratorSettings = registryGeneratorSettings.get(new ResourceLocation(noiseRouterData.getKey().getNamespace(), noiseRouterData.getKey().getKey()));
        SpawnTarget spawnTarget = generatorConfiguration.getSpawnTarget();
        NoiseGeneratorSettings spawnTargetGeneratorSettings = registryGeneratorSettings.get(new ResourceLocation(spawnTarget.getKey().getNamespace(), spawnTarget.getKey().getKey()));

        NoiseRouter c = noiseRouterGeneratorSettings.noiseRouter();
        //DensityFunction df = DensityFunctions.constant(-1);
        //NoiseRouter newNoiseRouter = generatorConfiguration.isDisableFluidLevelGeneration() ? new NoiseRouter(c.barrierNoise(), df/*c.fluidLevelFloodednessNoise()*/, df/*c.fluidLevelSpreadNoise()*/, df/*c.lavaNoise()*/, c.temperature(), c.vegetation(), c.continents(), c.erosion(), c.depth(), c.ridges(), c.initialDensityWithoutJaggedness(), c.finalDensity(), c.veinToggle(), c.veinRidged(), c.veinGap()) : c;

        try{
            NoiseGeneratorSettings generatorSettingBase = new NoiseGeneratorSettings(
                    noiseSettings,
                    baseBlock,
                    baseFluid,
                    c,
                    ruleSource,
                    spawnTargetGeneratorSettings.spawnTarget(),
                    generatorConfiguration.getSeaLevel(),
                    generatorConfiguration.isDisableMobGeneration(),
                    generatorConfiguration.isAquifersEnabled(),
                    generatorConfiguration.isOreVeinsEnabled(),
                    generatorConfiguration.getRandomGenerationType() == GeneratorConfiguration.RandomGenerationType.LEGACY);


            Holder.Reference<NoiseGeneratorSettings> registered = registryGeneratorSettings.register(generatorSettingBaseResourceKey, generatorSettingBase, Lifecycle.stable());
            // Bukkit.broadcastMessage(("registerGenConf: " + registered.toString()));
            NMSHelper.setPrivateField(registered, "e", generatorSettingBase); //Field: value
            // Bukkit.broadcastMessage(("registerGenConf: " + registered.toString()).substring(0, 80));

            Optional<Holder.Reference<NoiseGeneratorSettings>> ref = registryGeneratorSettings.getHolder(generatorSettingBaseResourceKey);
            // Bukkit.broadcastMessage(("registerGenConf 0815: " + ref.get()).substring(0, 80));

            return true;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

}
