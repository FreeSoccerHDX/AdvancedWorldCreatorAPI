package de.freesoccerhdx.advancedworldcreatorapi;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import de.freesoccerhdx.advancedworldcreatorapi.biome.BiomeCreator;
import de.freesoccerhdx.advancedworldcreatorapi.biomeprovider.AdvancedBiomeProvider;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import org.bukkit.craftbukkit.v1_19_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftNamespacedKey;
import org.bukkit.generator.WorldInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Used internal to create a valid BiomeSource
 */
public class AdvancedCustomWorldChunkManager extends FixedBiomeSource implements BiomeManager.NoiseBiomeSource {


    private final WorldInfo worldInfo;
    private final AdvancedBiomeProvider biomeProvider;
    private final Registry<Biome> registry;
    private final Set<Holder<Biome>> possibleBiomes;

    private static List<Holder<Biome>> biomeListToBiomeBaseList(List<Object> biomes, Registry<Biome> registry) {
        List<Holder<Biome>> biomeBases = new ArrayList();
        Iterator var4 = biomes.iterator();

        while(var4.hasNext()) {
            Object next = var4.next();
            Holder.Reference<Biome> biomeReference = null;

            if(next instanceof org.bukkit.block.Biome biome) {
                Preconditions.checkArgument(biome != org.bukkit.block.Biome.CUSTOM, "Cannot use the biome %s", biome);
                biomeReference = registry.getHolderOrThrow(ResourceKey.create(Registries.BIOME, CraftNamespacedKey.toMinecraft(biome.getKey())));
                //Biome biomebase = CraftBlock.biomeToBiomeBase(registry, biome).value();
                //biomeBases.add(Holder.direct(biomebase));
            }else if(next instanceof BiomeCreator.CustomBiome){
                BiomeCreator.CustomBiome customBiome = (BiomeCreator.CustomBiome) next;
                biomeReference = registry.getHolderOrThrow(customBiome.getResourceKey());
                //biomeBases.add(Holder.direct(customBiome.getNMSBiome().value()));
            }else{
                Biome biomebase = (Biome) next;
                biomeReference = registry.getHolderOrThrow(registry.getResourceKey(biomebase).get());
               // biomeBases.add(Holder.direct((Biome) next));
            }

            biomeBases.add(biomeReference);
        }
        //biomeBases.forEach(holder -> System.err.println(holder.value()));
        return biomeBases;
    }

    /**
     * Constructor for the CustomWorldChunkManager
     *
     * @param worldInfo The WorldInfo of the World
     * @param biomeProvider The AdvancedBiomeProvider to use
     * @param registry The Registry of Biomes
     */
    public AdvancedCustomWorldChunkManager(WorldInfo worldInfo, AdvancedBiomeProvider biomeProvider, Registry<Biome> registry) {
        super(biomeListToBiomeBaseList(biomeProvider.getBiomes(worldInfo), registry).get(0));
        possibleBiomes = new ObjectLinkedOpenHashSet(biomeListToBiomeBaseList(biomeProvider.getBiomes(worldInfo), registry));
        this.worldInfo = worldInfo;
        this.biomeProvider = biomeProvider;
        this.registry = registry;
    }

    @Override
    public Set<Holder<Biome>> possibleBiomes() {
        return this.possibleBiomes;
    }


    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return getNoiseBiome(x, y, z);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z) {
        Object biomeObj = this.biomeProvider.getBiome(this.worldInfo, x << 2, y << 2, z << 2);

        if(biomeObj instanceof org.bukkit.block.Biome biome) {
            Preconditions.checkArgument(biome != org.bukkit.block.Biome.CUSTOM, "Cannot set the biome to %s", biome);
            return CraftBlock.biomeToBiomeBase(this.registry, biome);
        }else if(biomeObj instanceof BiomeCreator.CustomBiome){
            BiomeCreator.CustomBiome customBiome = (BiomeCreator.CustomBiome) biomeObj;
            return registry.getHolderOrThrow(registry.getResourceKey(customBiome.getNMSBiome().value()).get());
        }
        return registry.getHolderOrThrow(registry.getResourceKey((Biome) biomeObj).get());
    }

    @Override
    public Set<Holder<Biome>> getBiomesWithin(int var0, int var1, int var2, int var3, Climate.Sampler var4) {
        int var5 = QuartPos.fromBlock(var0 - var3);
        int var6 = QuartPos.fromBlock(var1 - var3);
        int var7 = QuartPos.fromBlock(var2 - var3);
        int var8 = QuartPos.fromBlock(var0 + var3);
        int var9 = QuartPos.fromBlock(var1 + var3);
        int var10 = QuartPos.fromBlock(var2 + var3);
        int var11 = var8 - var5 + 1;
        int var12 = var9 - var6 + 1;
        int var13 = var10 - var7 + 1;
        Set<Holder<Biome>> var14 = Sets.newHashSet();

        for(int var15 = 0; var15 < var13; ++var15) {
            for(int var16 = 0; var16 < var11; ++var16) {
                for(int var17 = 0; var17 < var12; ++var17) {
                    int var18 = var5 + var16;
                    int var19 = var6 + var17;
                    int var20 = var7 + var15;
                    var14.add(this.getNoiseBiome(var18, var19, var20, var4));
                }
            }
        }

        return var14;
    }

    @Nullable
    @Override
    public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int var0, int var1, int var2, int var3, Predicate<Holder<Biome>> var4, RandomSource var5, Climate.Sampler var6) {
        return this.findBiomeHorizontal(var0, var1, var2, var3, 1, var4, var5, false, var6);
    }

    @Nullable
    @Override
    public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(BlockPos var0, int var1, int var2, int var3, Predicate<Holder<Biome>> var4, Climate.Sampler var5, LevelReader var6) {
        Set<Holder<Biome>> var7 = this.possibleBiomes().stream().filter(var4).collect(Collectors.toUnmodifiableSet());
        if (var7.isEmpty()) {
            return null;
        } else {
            int var8 = Math.floorDiv(var1, var2);
            int[] var9 = Mth.outFromOrigin(var0.getY(), var6.getMinBuildHeight() + 1, var6.getMaxBuildHeight(), var3).toArray();
            Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.spiralAround(BlockPos.ZERO, var8, Direction.EAST, Direction.SOUTH).iterator();

            while(iterator.hasNext()) {
                BlockPos.MutableBlockPos var11 = (BlockPos.MutableBlockPos)iterator.next();
                int var12 = var0.getX() + var11.getX() * var2;
                int var13 = var0.getZ() + var11.getZ() * var2;
                int var14 = QuartPos.fromBlock(var12);
                int var15 = QuartPos.fromBlock(var13);
                int[] var17 = var9;
                int var18 = var9.length;

                for(int var19 = 0; var19 < var18; ++var19) {
                    int var19_ = var17[var19];
                    int var20 = QuartPos.fromBlock(var19_);
                    Holder<Biome> var21 = this.getNoiseBiome(var14, var20, var15, var5);
                    if (var7.contains(var21)) {
                        return Pair.of(new BlockPos(var12, var19_, var13), var21);
                    }
                }
            }

            return null;
        }
    }

    @Nullable
    @Override
    public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int var0, int var1, int var2, int var3, int var4, Predicate<Holder<Biome>> var5, RandomSource var6, boolean var7, Climate.Sampler var8) {
        int var9 = QuartPos.fromBlock(var0);
        int var10 = QuartPos.fromBlock(var2);
        int var11 = QuartPos.fromBlock(var3);
        int var12 = QuartPos.fromBlock(var1);
        Pair<BlockPos, Holder<Biome>> var13 = null;
        int var14 = 0;
        int var15 = var7 ? 0 : var11;

        for(int var16 = var15; var16 <= var11; var16 += var4) {
            for(int var17 = SharedConstants.debugGenerateSquareTerrainWithoutNoise ? 0 : -var16; var17 <= var16; var17 += var4) {
                boolean var18 = Math.abs(var17) == var16;

                for(int var19 = -var16; var19 <= var16; var19 += var4) {
                    if (var7) {
                        boolean var20 = Math.abs(var19) == var16;
                        if (!var20 && !var18) {
                            continue;
                        }
                    }

                    int var20 = var9 + var19;
                    int var21 = var10 + var17;
                    Holder<Biome> var22 = this.getNoiseBiome(var20, var12, var21, var8);
                    if (var5.test(var22)) {
                        if (var13 == null || var6.nextInt(var14 + 1) == 0) {
                            BlockPos var23 = new BlockPos(QuartPos.toBlock(var20), var1, QuartPos.toBlock(var21));
                            if (var7) {
                                return Pair.of(var23, var22);
                            }

                            var13 = Pair.of(var23, var22);
                        }

                        ++var14;
                    }
                }
            }
        }

        return var13;
    }

}
