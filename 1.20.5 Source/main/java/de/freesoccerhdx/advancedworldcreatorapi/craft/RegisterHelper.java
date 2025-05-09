package de.freesoccerhdx.advancedworldcreatorapi.craft;

import de.freesoccerhdx.advancedworldcreatorapi.EnvironmentBuilder;
import de.freesoccerhdx.advancedworldcreatorapi.GeneratorConfiguration;
import de.freesoccerhdx.advancedworldcreatorapi.NoiseRouterData;
import de.freesoccerhdx.advancedworldcreatorapi.SpawnTarget;
import de.freesoccerhdx.advancedworldcreatorapi.SurfaceRule;
import de.freesoccerhdx.mcutils.NMSHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
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
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_21_R4.CraftServer;
import org.bukkit.craftbukkit.v1_21_R4.block.data.CraftBlockData;

import java.util.Optional;
import java.util.OptionalLong;

public class RegisterHelper {

    public static boolean registerCustomEnvironment(EnvironmentBuilder builder) {
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        ResourceKey<DimensionType> resourceKeyDimension = NMSHelper.createResourceKey(Registries.DIMENSION_TYPE, builder.getKey());
        WritableRegistry<DimensionType> registryDimensions = NMSHelper.getWritableRegistry(Registries.DIMENSION_TYPE);

        TagKey infiniBurn = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(builder.getInfiniburn().getKey().getNamespace(),builder.getInfiniburn().getKey().getKey()));

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
                ResourceLocation.fromNamespaceAndPath(builder.getEffectsLocation().getNamespace(), builder.getEffectsLocation().getKey()),
                builder.getAmbientLight(),
                monsterSettings);


        Optional<Holder.Reference<DimensionType>> existValue = registryDimensions.get(resourceKeyDimension);
        boolean exist = existValue.isPresent() ? existValue.get().isBound() : false; // registryDimensions.get(resourceKeyDimension) != null;

        if(!exist || builder.isOverwriteSettingsIfExist()) {
            Holder.Reference<DimensionType> registered = NMSHelper.registerElementToRegistry(Registries.DIMENSION_TYPE, resourceKeyDimension, dimensionManager);
                    //registryDimensions.register(resourceKeyDimension, dimensionManager, RegistrationInfo.BUILT_IN);
            //NMSHelper.setPrivateField(registered, "e", dimensionManager); //Field: value
            // Bukkit.broadcastMessage("§c[TEST] registering new dimension[nonnull? "+(dimensionManager!=null)+"]: " + resourceKeyDimension + " -> " + registered);
            //registryDimensions.register(resourceKeyDimension, dimensionManager, Lifecycle.stable());
            return true;
        }else if(exist){
            return true;
        }

        return false;
    }

    public static boolean registerGeneratorConfiguration(GeneratorConfiguration generatorConfiguration) {
        //System.out.println("registerGeneratorConfiguration");
        Server server = Bukkit.getServer();
        CraftServer craftServer = (CraftServer) server;

        GeneratorConfiguration.NoiseGeneration noiseGeneration = generatorConfiguration.getNoiseGeneration();

        WritableRegistry<NoiseGeneratorSettings> registryGeneratorSettings = NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS);
        ResourceKey<NoiseGeneratorSettings> generatorSettingBaseResourceKey = NMSHelper.createResourceKey(Registries.NOISE_SETTINGS, generatorConfiguration.getKey());
        //System.out.println("RETURN generatorSettingBaseResourceKey==" + generatorSettingBaseResourceKey);
        Optional<Holder.Reference<NoiseGeneratorSettings>> getExistValue = registryGeneratorSettings.get(generatorSettingBaseResourceKey);
        boolean alreadyExist = getExistValue.isPresent() ? getExistValue.get().isBound() : false;
        if(alreadyExist && !generatorConfiguration.isOverwriteSettingsIfExist()) {
            //System.out.println("RETURN TRUE");
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
        NoiseGeneratorSettings noiseRouterGeneratorSettings = registryGeneratorSettings.getValue(ResourceLocation.fromNamespaceAndPath(noiseRouterData.getKey().getNamespace(), noiseRouterData.getKey().getKey()));
        SpawnTarget spawnTarget = generatorConfiguration.getSpawnTarget();
        NoiseGeneratorSettings spawnTargetGeneratorSettings = registryGeneratorSettings.getValue(ResourceLocation.fromNamespaceAndPath(spawnTarget.getKey().getNamespace(), spawnTarget.getKey().getKey()));

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

//            NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS).keySet().forEach(ld -> {
//                System.out.println("before: " + ld);
//            });
//            System.out.println("REGI NOW: " + generatorSettingBaseResourceKey);
            Holder.Reference<NoiseGeneratorSettings> registered = NMSHelper.registerElementToRegistry(Registries.NOISE_SETTINGS, generatorSettingBaseResourceKey, generatorSettingBase);
//            NMSHelper.getWritableRegistry(Registries.NOISE_SETTINGS).keySet().forEach(ld -> {
//                System.out.println("now regis: " + ld);
//            });
//            registered.value();
                    //registryGeneratorSettings.register(generatorSettingBaseResourceKey, generatorSettingBase, RegistrationInfo.BUILT_IN);
            // Bukkit.broadcastMessage(("registerGenConf: " + registered.toString()));
            // TODO NMSHelper.setPrivateField(registered, "e", generatorSettingBase); //Field: value
            // Bukkit.broadcastMessage(("registerGenConf: " + registered.toString()).substring(0, 80));

            Optional<Holder.Reference<NoiseGeneratorSettings>> ref = registryGeneratorSettings.get(generatorSettingBaseResourceKey);
            // Bukkit.broadcastMessage(("registerGenConf 0815: " + ref.get()).substring(0, 80));

            return true;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

}
