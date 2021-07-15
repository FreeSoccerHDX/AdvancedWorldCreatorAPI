package de.freesoccerhdx.advancedworldcreator.main;



import com.google.common.collect.Lists;

import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftMagicNumbers;

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.Cavifier;
import net.minecraft.world.level.levelgen.NoiseInterpolator;
import net.minecraft.world.level.levelgen.DepthBasedReplacingBaseStoneSource;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.NoiseModifier;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoodleCavifier;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.SimpleRandomSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenStage.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureManager;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3Handler;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorOctaves;

public final class NewDummyChunkGeneratorAbstract extends ChunkGenerator {
	
	
	public static final Codec<NewDummyChunkGeneratorAbstract> CODEC = null; 
			/*
			RecordCodecBuilder.create((instance) -> {
        return instance.group(WorldChunkManager.CODEC.fieldOf("biome_source").forGetter((NewDummyChunkGeneratorAbstract) -> {
            return NewDummyChunkGeneratorAbstract.biomeSource;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((NewDummyChunkGeneratorAbstract) -> {
            return NewDummyChunkGeneratorAbstract.seed;
        }), GeneratorSettingBase.CODEC.fieldOf("settings").forGetter((NewDummyChunkGeneratorAbstract) -> {
            return NewDummyChunkGeneratorAbstract.settings;
        })).apply(instance, instance.stable(NewDummyChunkGeneratorAbstract::new));
    });
    */
	
	
    private static final IBlockData AIR = Blocks.a.getBlockData();
    private static final IBlockData[] EMPTY_COLUMN = new IBlockData[0];
    private final int cellHeight;
    private final int cellWidth;
    final int cellCountX;
    final int cellCountY;
    final int cellCountZ;
    private final NoiseGenerator surfaceNoise;
    private final NoiseGeneratorNormal barrierNoise;
    private final NoiseGeneratorNormal waterLevelNoise;
    private final NoiseGeneratorNormal lavaNoise;
    protected final IBlockData defaultBlock;
    protected final IBlockData defaultFluid;
    private final long seed;
    protected final Supplier<GeneratorSettingBase> settings;
    private final int height;
    private final NoiseSampler sampler;
    private final BaseStoneSource baseStoneSource;
    final OreVeinifier oreVeinifier;
    final NoodleCavifier noodleCavifier;
    
    
    
    // CUSTOM - START
    private StructureSettings custom_structure_settings = null; // not rly used
    
    private IBlockData topbedrocktype = null; 
    private IBlockData bottombedrocktype = null;
	
    private boolean cancreatestructures = false;
    private boolean cancreatedecoration = false;
	
    private boolean cancreateaircaves = false;
    private boolean cancreateliqidcaves = false;
	
    private boolean cancreatesurface = false;
    
	private List<WorldGenStage.Decoration> decoration_blacklist = new ArrayList<>();
	
    public void applyAdvancedCreator(AdvancedCreator ac) {
    	
    	
    	topbedrocktype = ac.getTopBedrock().getBlockData(); 
    	bottombedrocktype = ac.getBottomBedrock().getBlockData();
    	
    	cancreatestructures = ac.canGenerateStructures();
    	cancreatedecoration = ac.canGenerateDecoration();
    	
    	cancreateaircaves = !ac.isAirCavesDisabled();
    	cancreateliqidcaves = !ac.isLiquidCavesDisabled();
    	
    	cancreatesurface = ac.isGeneratingSurface();
    	
    	decoration_blacklist = ac.getBlacklistedDecoration();
    	
    	/*
    	chunkgenerator.setTopBedrock(creator.getTopBedrock());
	    chunkgenerator.setBottomBedrock(creator.getBottomBedrock());
	    chunkgenerator.setCanCreateStructures(creator.canGenerateStructures());
	    chunkgenerator.setCanCreateDecorations(creator.canGenerateDecoration());
	    chunkgenerator.setAirCavesDisabled(creator.isAirCavesDisabled());
	    chunkgenerator.setLiquidCavesDisabled(creator.isLiquidCavesDisabled());
	    chunkgenerator.setGenerateSurface(creator.isGeneratingSurface()); 
	    
	    chunkgenerator.setDecorationBlacklist(creator.getBlacklistedDecoration());
    	*/
    	
    	
    	
    }
    
    @Override
    public void createStructures(IRegistryCustom iregistrycustom, StructureManager structuremanager,
			IChunkAccess ichunkaccess, DefinedStructureManager definedstructuremanager, long i) {
    	
    	if(cancreatestructures) {
    		super.createStructures(iregistrycustom, structuremanager, ichunkaccess, definedstructuremanager, i);
    	}
    	
    }
    @Override
    public void addDecorations(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager) {
    	
    	if(cancreatedecoration) {
    		ChunkCoordIntPair chunkcoordintpair = regionlimitedworldaccess.a();
    		int i = chunkcoordintpair.d();
    		int j = chunkcoordintpair.e();
    		BlockPosition blockposition = new BlockPosition(i, regionlimitedworldaccess.getMinBuildHeight(), j);
    		BiomeBase biomebase = this.b.b(chunkcoordintpair);
    		SeededRandom seededrandom = new SeededRandom();
    		long k = seededrandom.a(regionlimitedworldaccess.getSeed(), i, j);
    		try {
    			this.a(biomebase, structuremanager, this, regionlimitedworldaccess, k, seededrandom, blockposition);
    			
    			
    		} catch (Exception exception) {
    			CrashReport crashreport = CrashReport.a(exception, "Biome decoration");
    			crashreport.a("Generation").a("CenterX", Integer.valueOf(chunkcoordintpair.b))
    					.a("CenterZ", Integer.valueOf(chunkcoordintpair.c)).a("Seed", Long.valueOf(k))
    					.a("Biome", biomebase);
    			throw new ReportedException(crashreport);
    		}
    	}
    	
    }
    
    public void a(BiomeBase biomebase,StructureManager var0, ChunkGenerator var1, RegionLimitedWorldAccess var2, long var3,
			SeededRandom var5, BlockPosition var6) {
    	
    	
    	
		List<List<Supplier<WorldGenFeatureConfigured<?, ?>>>> var7 = biomebase.e().c();
		IRegistry<WorldGenFeatureConfigured<?, ?>> var8 = var2.t().d(IRegistry.aK);
		IRegistry<StructureGenerator<?>> var9 = var2.t().d(IRegistry.aV);
		int var10 = (WorldGenStage.Decoration.values()).length;
		for (int var11 = 0; var11 < var10; var11++) {
			
			WorldGenStage.Decoration decotype = WorldGenStage.Decoration.values()[var11];
			int var12 = 0;
			
			if(!decoration_blacklist.contains(decotype)) {
			
				
				if (var0.a()) {
					
					Map<Integer, List<StructureGenerator<?>>> g = null;
					
					try {
						Field pfield = BiomeBase.class.getDeclaredField("g");
						pfield.setAccessible(true);
						FieldUtils.removeFinalModifier(pfield);
						
						g = (Map<Integer, List<StructureGenerator<?>>>) FieldUtils.readField(pfield, biomebase, true);
						
						
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
					
					List<StructureGenerator<?>> var13 = g.getOrDefault(var11, Collections.emptyList());
					
					for (StructureGenerator<?> var15 : var13) {
						var5.b(var3, var12, var11);
						int var16 = SectionPosition.a(var6.getX());
						int var17 = SectionPosition.a(var6.getZ());
						int var18 = SectionPosition.c(var16);
						int var19 = SectionPosition.c(var17);
						Supplier<String> var20 = () -> {
							Optional optional = var9.c(var15).map(Object::toString);
	
	                        Objects.requireNonNull(var15);
	                        return (String) optional.orElseGet(var15::toString);
						};
						try {
							int var21 = var2.getMinBuildHeight() + 1;
							int var22 = var2.getMaxBuildHeight() - 1;
							
							var2.a(var20);
	                        var0.a(SectionPosition.a(var6), var15).forEach((structurestart) -> {
	                            structurestart.a(var2, var0, var1, var5, new StructureBoundingBox(var18, var21, var19, var18 + 15, var22, var19 + 15), new ChunkCoordIntPair(var16, var17));
	                        });
							
						} catch (Exception var21) {
							CrashReport var22 = CrashReport.a(var21, "Feature placement");
							Objects.requireNonNull(var20);
							var22.a("Feature").a("Description", var20::get);
							throw new ReportedException(var22);
						}
						var12++;
					}
				}
			}
			if(true) { // grass and trees

				if (var7.size() > var11)
					for (Supplier<WorldGenFeatureConfigured<?, ?>> var14 : var7.get(var11)) {
						WorldGenFeatureConfigured<?, ?> var15 = var14.get();
						Supplier<String> var16 = () -> {
							Optional optional = var8.c(var15).map(Object::toString);
	
	                        Objects.requireNonNull(var15);
	                        return (String) optional.orElseGet(var15::toString);
						};
						var5.b(var3, var12, var11);
						try {
							var2.a(var16);
							var15.a(var2, var1, var5, var6);
						} catch (Exception var17) {
							CrashReport var18 = CrashReport.a(var17, "Feature placement");
							Objects.requireNonNull(var16);
							var18.a("Feature").a("Description", var16::get);
							throw new ReportedException(var18);
						}
						var12++;
					}
			}
		}
		var2.a((Supplier<String>) null);
	}
    
    // CUSTOM - END
    

    public NewDummyChunkGeneratorAbstract(WorldChunkManager worldchunkmanager, long i, StructureSettings structset, Supplier<GeneratorSettingBase> supplier) {
        this(worldchunkmanager, worldchunkmanager, i,structset, supplier);
    }
//WorldChunkManager worldchunkmanager, WorldChunkManager worldchunkmanager1, long seed, StructureSettings structset, Supplier<GeneratorSettingBase> supplier) {
    private NewDummyChunkGeneratorAbstract(WorldChunkManager worldchunkmanager, WorldChunkManager worldchunkmanager1, long i, StructureSettings structset, Supplier<GeneratorSettingBase> supplier) {
        super(worldchunkmanager, worldchunkmanager1, structset, i);
        
        // CUSTOM - START
        custom_structure_settings = structset;
        
        // CUSTOM - END
        
        
        this.seed = i;
        GeneratorSettingBase generatorsettingbase = (GeneratorSettingBase) supplier.get();

        this.settings = supplier;
        NoiseSettings noisesettings = generatorsettingbase.b();

        this.height = noisesettings.b();
        this.cellHeight = QuartPos.b(noisesettings.g());
        this.cellWidth = QuartPos.b(noisesettings.f());
        this.defaultBlock = generatorsettingbase.c();
        this.defaultFluid = generatorsettingbase.d();
        this.cellCountX = 16 / this.cellWidth;
        this.cellCountY = noisesettings.b() / this.cellHeight;
        this.cellCountZ = 16 / this.cellWidth;
        SeededRandom seededrandom = new SeededRandom(i);
        BlendedNoise blendednoise = new BlendedNoise(seededrandom);

        this.surfaceNoise = (NoiseGenerator) (noisesettings.j() ? new NoiseGenerator3(seededrandom, IntStream.rangeClosed(-3, 0)) : new NoiseGeneratorOctaves(seededrandom, IntStream.rangeClosed(-3, 0)));
        seededrandom.a(2620);
        NoiseGeneratorOctaves noisegeneratoroctaves = new NoiseGeneratorOctaves(seededrandom, IntStream.rangeClosed(-15, 0));
        NoiseGenerator3Handler noisegenerator3handler;

        if (noisesettings.l()) {
            SeededRandom seededrandom1 = new SeededRandom(i);

            seededrandom1.a(17292);
            noisegenerator3handler = new NoiseGenerator3Handler(seededrandom1);
        } else {
            noisegenerator3handler = null;
        }

        this.barrierNoise = NoiseGeneratorNormal.a(new SimpleRandomSource(seededrandom.nextLong()), -3, 1.0D);
        this.waterLevelNoise = NoiseGeneratorNormal.a(new SimpleRandomSource(seededrandom.nextLong()), -3, 1.0D, 0.0D, 2.0D);
        this.lavaNoise = NoiseGeneratorNormal.a(new SimpleRandomSource(seededrandom.nextLong()), -1, 1.0D, 0.0D);
        Object object;

        try {
        	Field f = GeneratorSettingBase.class.getDeclaredField("s");
    		f.setAccessible(true);
    		FieldUtils.removeFinalModifier(f, true);
    		Object obj = FieldUtils.readField(f, this.settings.get());
        	
	        boolean noise_caves_enabled = (boolean) obj;
	        
	        //if (generatorsettingbase.k()) { // TODO: k() is noise_caves_enabled
	        if(noise_caves_enabled) {
	            object = new Cavifier(seededrandom, noisesettings.a() / this.cellHeight);
	        } else {
	            object = NoiseModifier.b;
	        }
        }catch(Exception ex) {
        	ex.printStackTrace();
        	object = NoiseModifier.b;
        }

        this.sampler = new NoiseSampler(worldchunkmanager, this.cellWidth, this.cellHeight, this.cellCountY, noisesettings, blendednoise, noisegenerator3handler, noisegeneratoroctaves, (NoiseModifier) object);
        this.baseStoneSource = new DepthBasedReplacingBaseStoneSource(i, this.defaultBlock, Blocks.pN.getBlockData(), generatorsettingbase);
        this.oreVeinifier = new OreVeinifier(i, this.defaultBlock, this.cellWidth, this.cellHeight, generatorsettingbase.b().a());
        this.noodleCavifier = new NoodleCavifier(i);
    }

    /*
     * Returns true if aquifers enabled 
     */
    private boolean h() {
    	
    	boolean a = false;
    	try {
    		
    		Field f = GeneratorSettingBase.class.getDeclaredField("r");
    		f.setAccessible(true);
    		FieldUtils.removeFinalModifier(f, true);
    		Object obj = FieldUtils.readField(f, this.settings.get());
    		
    		
			a = (boolean) obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return a;
     //   return ((GeneratorSettingBase) this.settings.get()).j(); // TOOD: j() is aquifers_enabled
    }

    @Override
    protected Codec<? extends ChunkGenerator> a() {
        return NewDummyChunkGeneratorAbstract.CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long i) {
        return new NewDummyChunkGeneratorAbstract(this.b.a(i), i,new StructureSettings(false), this.settings);
    }

    public boolean a(long i, ResourceKey<GeneratorSettingBase> resourcekey) {
        return this.seed == i && ((GeneratorSettingBase) this.settings.get()).a(resourcekey);
    }

    private double[] a(int i, int j, int k, int l) {
        double[] adouble = new double[l + 1];

        this.a(adouble, i, j, k, l);
        return adouble;
    }

    private void a(double[] adouble, int i, int j, int k, int l) {
        NoiseSettings noisesettings = ((GeneratorSettingBase) this.settings.get()).b();

        this.sampler.a(adouble, i, j, noisesettings, this.getSeaLevel(), k, l);
    }

    @Override
    public int getBaseHeight(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor) {
        int k = Math.max(((GeneratorSettingBase) this.settings.get()).b().a(), levelheightaccessor.getMinBuildHeight());
        int l = Math.min(((GeneratorSettingBase) this.settings.get()).b().a() + ((GeneratorSettingBase) this.settings.get()).b().b(), levelheightaccessor.getMaxBuildHeight());
        int i1 = MathHelper.a(k, this.cellHeight);
        int j1 = MathHelper.a(l - k, this.cellHeight);

        return j1 <= 0 ? levelheightaccessor.getMinBuildHeight() : this.a(i, j, (IBlockData[]) null, heightmap_type.e(), i1, j1).orElse(levelheightaccessor.getMinBuildHeight());
    }

    @Override
    public BlockColumn getBaseColumn(int i, int j, LevelHeightAccessor levelheightaccessor) {
        int k = Math.max(((GeneratorSettingBase) this.settings.get()).b().a(), levelheightaccessor.getMinBuildHeight());
        int l = Math.min(((GeneratorSettingBase) this.settings.get()).b().a() + ((GeneratorSettingBase) this.settings.get()).b().b(), levelheightaccessor.getMaxBuildHeight());
        int i1 = MathHelper.a(k, this.cellHeight);
        int j1 = MathHelper.a(l - k, this.cellHeight);

        if (j1 <= 0) {
            return new BlockColumn(k, NewDummyChunkGeneratorAbstract.EMPTY_COLUMN);
        } else {
            IBlockData[] aiblockdata = new IBlockData[j1 * this.cellHeight];

            this.a(i, j, aiblockdata, (Predicate) null, i1, j1);
            return new BlockColumn(k, aiblockdata);
        }
    }

    @Override
    public BaseStoneSource g() {
        return this.baseStoneSource;
    }

    private OptionalInt a(int i, int j, @Nullable IBlockData[] aiblockdata, @Nullable Predicate<IBlockData> predicate, int k, int l) {
        int i1 = SectionPosition.a(i);
        int j1 = SectionPosition.a(j);
        int k1 = Math.floorDiv(i, this.cellWidth);
        int l1 = Math.floorDiv(j, this.cellWidth);
        int i2 = Math.floorMod(i, this.cellWidth);
        int j2 = Math.floorMod(j, this.cellWidth);
        double d0 = (double) i2 / (double) this.cellWidth;
        double d1 = (double) j2 / (double) this.cellWidth;
        double[][] adouble = new double[][]{this.a(k1, l1, k, l), this.a(k1, l1 + 1, k, l), this.a(k1 + 1, l1, k, l), this.a(k1 + 1, l1 + 1, k, l)};
        Aquifer aquifer = this.a(k, l, new ChunkCoordIntPair(i1, j1));

        for (int k2 = l - 1; k2 >= 0; --k2) {
            double d2 = adouble[0][k2];
            double d3 = adouble[1][k2];
            double d4 = adouble[2][k2];
            double d5 = adouble[3][k2];
            double d6 = adouble[0][k2 + 1];
            double d7 = adouble[1][k2 + 1];
            double d8 = adouble[2][k2 + 1];
            double d9 = adouble[3][k2 + 1];

            for (int l2 = this.cellHeight - 1; l2 >= 0; --l2) {
                double d10 = (double) l2 / (double) this.cellHeight;
                double d11 = MathHelper.a(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
                int i3 = k2 * this.cellHeight + l2;
                int j3 = i3 + k * this.cellHeight;
                IBlockData iblockdata = this.a(DummyBeardifier.a, aquifer, this.baseStoneSource, NoiseModifier.b, i, j3, j, d11);

                if (aiblockdata != null) {
                    aiblockdata[i3] = iblockdata;
                }

                if (predicate != null && predicate.test(iblockdata)) {
                    return OptionalInt.of(j3 + 1);
                }
            }
        }

        return OptionalInt.empty();
    }

    private Aquifer a(int i, int j, ChunkCoordIntPair chunkcoordintpair) {
        return !this.h() ? Aquifer.a(this.getSeaLevel(), this.defaultFluid) : Aquifer.a(chunkcoordintpair, this.barrierNoise, this.waterLevelNoise, this.lavaNoise, (GeneratorSettingBase) this.settings.get(), this.sampler, i * this.cellHeight, j * this.cellHeight);
    }

    protected IBlockData a(DummyBeardifier beardifier, Aquifer aquifer, BaseStoneSource basestonesource, NoiseModifier noisemodifier, int i, int j, int k, double d0) {
        double d1 = MathHelper.a(d0 / 200.0D, -1.0D, 1.0D);

        d1 = d1 / 2.0D - d1 * d1 * d1 / 24.0D;
        d1 = noisemodifier.modifyNoise(d1, i, j, k);
        d1 += beardifier.a(i, j, k);
        return aquifer.a(basestonesource, i, j, k, d1);
    }

    @Override
    public void buildBase(RegionLimitedWorldAccess regionlimitedworldaccess, IChunkAccess ichunkaccess) {
    	if(cancreatesurface) {
	        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
	        int i = chunkcoordintpair.b;
	        int j = chunkcoordintpair.c;
	        SeededRandom seededrandom = new SeededRandom();
	
	        seededrandom.a(i, j);
	        ChunkCoordIntPair chunkcoordintpair1 = ichunkaccess.getPos();
	        int k = chunkcoordintpair1.d();
	        int l = chunkcoordintpair1.e();
	        double d0 = 0.0625D;
	        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
	
	        for (int i1 = 0; i1 < 16; ++i1) {
	            for (int j1 = 0; j1 < 16; ++j1) {
	                int k1 = k + i1;
	                int l1 = l + j1;
	                int i2 = ichunkaccess.getHighestBlock(HeightMap.Type.a, i1, j1) + 1;
	                double d1 = this.surfaceNoise.a((double) k1 * 0.0625D, (double) l1 * 0.0625D, 0.0625D, (double) i1 * 0.0625D) * 15.0D;
	                int j2 = ((GeneratorSettingBase) this.settings.get()).h();
	
	                regionlimitedworldaccess.getBiome(blockposition_mutableblockposition.d(k + i1, i2, l + j1)).a(seededrandom, ichunkaccess, k1, l1, i2, d1, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), j2, regionlimitedworldaccess.getSeed());
	            }
	        }
	
	        this.a(ichunkaccess, seededrandom);
    	}
    }

    private void a(IChunkAccess ichunkaccess, Random random) {
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
        int i = ichunkaccess.getPos().d();
        int j = ichunkaccess.getPos().e();
        GeneratorSettingBase generatorsettingbase = (GeneratorSettingBase) this.settings.get();
        int k = generatorsettingbase.b().a();
        int l = k + generatorsettingbase.f();
        int i1 = this.height - 1 + k - generatorsettingbase.e();
        boolean flag = true;
        int j1 = ichunkaccess.getMinBuildHeight();
        int k1 = ichunkaccess.getMaxBuildHeight();
        boolean flag1 = i1 + 5 - 1 >= j1 && i1 < k1;
        boolean flag2 = l + 5 - 1 >= j1 && l < k1;

        if (flag1 || flag2) {
            Iterator iterator = BlockPosition.b(i, 0, j, i + 15, 0, j + 15).iterator();

            while (iterator.hasNext()) {
                BlockPosition blockposition = (BlockPosition) iterator.next();
                int l1;

                if (flag1) {
                    for (l1 = 0; l1 < 5; ++l1) {
                        if (l1 <= random.nextInt(5)) {
                            ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), i1 - l1, blockposition.getZ()), topbedrocktype, false);
                        }
                    }
                }

                if (flag2) {
                    for (l1 = 4; l1 >= 0; --l1) {
                        if (l1 <= random.nextInt(5)) {
                            ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), l + l1, blockposition.getZ()), bottombedrocktype, false);
                        }
                    }
                }
            }

        }
    }

    @Override
    public CompletableFuture<IChunkAccess> buildNoise(Executor executor, StructureManager structuremanager, IChunkAccess ichunkaccess) {
        NoiseSettings noisesettings = ((GeneratorSettingBase) this.settings.get()).b();
        int i = Math.max(noisesettings.a(), ichunkaccess.getMinBuildHeight());
        int j = Math.min(noisesettings.a() + noisesettings.b(), ichunkaccess.getMaxBuildHeight());
        int k = MathHelper.a(i, this.cellHeight);
        int l = MathHelper.a(j - i, this.cellHeight);

        if (l <= 0) {
            return CompletableFuture.completedFuture(ichunkaccess);
        } else {
            int i1 = ichunkaccess.getSectionIndex(l * this.cellHeight - 1 + i);
            int j1 = ichunkaccess.getSectionIndex(i);

            return CompletableFuture.supplyAsync(() -> {
                HashSet hashset = Sets.newHashSet();
                boolean flag = false;

                IChunkAccess ichunkaccess1;

                try {
                    flag = true;

                    for (int k1 = i1; k1 >= j1; --k1) {
                        ChunkSection chunksection = ichunkaccess.b(k1);

                        chunksection.a();
                        hashset.add(chunksection);
                    }

                    ichunkaccess1 = this.a(structuremanager, ichunkaccess, k, l);
                    flag = false;
                } finally {
                    if (flag) {
                        Iterator iterator = hashset.iterator();

                        while (iterator.hasNext()) {
                            ChunkSection chunksection1 = (ChunkSection) iterator.next();

                            chunksection1.b();
                        }

                    }
                }

                Iterator iterator1 = hashset.iterator();

                while (iterator1.hasNext()) {
                    ChunkSection chunksection2 = (ChunkSection) iterator1.next();

                    chunksection2.b();
                }

                return ichunkaccess1;
            }, SystemUtils.f());
        }
    }

    private IChunkAccess a(StructureManager structuremanager, IChunkAccess ichunkaccess, int i, int j) {
        HeightMap heightmap = ichunkaccess.a(HeightMap.Type.c);
        HeightMap heightmap1 = ichunkaccess.a(HeightMap.Type.a);
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        int k = chunkcoordintpair.d();
        int l = chunkcoordintpair.e();
        DummyBeardifier beardifier = new DummyBeardifier(structuremanager, ichunkaccess);
        Aquifer aquifer = this.a(i, j, chunkcoordintpair);
        NoiseInterpolator noiseinterpolator = new NoiseInterpolator(this.cellCountX, j, this.cellCountZ, chunkcoordintpair, i, this::a);
        List<NoiseInterpolator> list = Lists.newArrayList(new NoiseInterpolator[]{noiseinterpolator});

        Objects.requireNonNull(list);
        Consumer<NoiseInterpolator> consumer = list::add;
        DoubleFunction<BaseStoneSource> doublefunction = this.b(i, chunkcoordintpair, consumer);
        DoubleFunction<NoiseModifier> doublefunction1 = this.a(i, chunkcoordintpair, consumer);

        list.forEach(NoiseInterpolator::a);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        for (int i1 = 0; i1 < this.cellCountX; ++i1) {
        	
        	for(NoiseInterpolator b : list) {
        		b.a(i1);
        	}
        	

            for (int j1 = 0; j1 < this.cellCountZ; ++j1) {
                ChunkSection chunksection = ichunkaccess.b(ichunkaccess.getSectionsCount() - 1);

                for (int k1 = j - 1; k1 >= 0; --k1) {
                	
                	for(NoiseInterpolator b : list) {
                		b.a(k1, j1);
                	}
                	

                    for (int l1 = this.cellHeight - 1; l1 >= 0; --l1) {
                        int i2 = (i + k1) * this.cellHeight + l1;
                        int j2 = i2 & 15;
                        int k2 = ichunkaccess.getSectionIndex(i2);

                        if (ichunkaccess.getSectionIndex(chunksection.getYPosition()) != k2) {
                            chunksection = ichunkaccess.b(k2);
                        }

                        double d0 = (double) l1 / (double) this.cellHeight;

                        list.forEach((noiseinterpolator1) -> {
                            noiseinterpolator1.a(d0);
                        });

                        for (int l2 = 0; l2 < this.cellWidth; ++l2) {
                            int i3 = k + i1 * this.cellWidth + l2;
                            int j3 = i3 & 15;
                            double d1 = (double) l2 / (double) this.cellWidth;

                            list.forEach((noiseinterpolator1) -> {
                                noiseinterpolator1.b(d1);
                            });

                            for (int k3 = 0; k3 < this.cellWidth; ++k3) {
                                int l3 = l + j1 * this.cellWidth + k3;
                                int i4 = l3 & 15;
                                double d2 = (double) k3 / (double) this.cellWidth;
                                double d3 = noiseinterpolator.c(d2);
                                IBlockData iblockdata = this.a(beardifier, aquifer, (BaseStoneSource) doublefunction.apply(d2), (NoiseModifier) doublefunction1.apply(d2), i3, i2, l3, d3);

                                if (iblockdata != NewDummyChunkGeneratorAbstract.AIR) {
                                    if (iblockdata.f() != 0 && ichunkaccess instanceof ProtoChunk) {
                                        blockposition_mutableblockposition.d(i3, i2, l3);
                                        ((ProtoChunk) ichunkaccess).j(blockposition_mutableblockposition);
                                    }

                                    chunksection.setType(j3, j2, i4, iblockdata, false);
                                    heightmap.a(j3, i2, i4, iblockdata);
                                    heightmap1.a(j3, i2, i4, iblockdata);
                                    if (aquifer.a() && !iblockdata.getFluid().isEmpty()) {
                                        blockposition_mutableblockposition.d(i3, i2, l3);
                                        ichunkaccess.p().a(blockposition_mutableblockposition, iblockdata.getFluid().getType(), 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            list.forEach(NoiseInterpolator::b);
        }

        return ichunkaccess;
    }

    private DoubleFunction<NoiseModifier> a(int i, ChunkCoordIntPair chunkcoordintpair, Consumer<NoiseInterpolator> consumer) {
    	
    	boolean noodle_caves_enabled = false;
		try {
			Field f = GeneratorSettingBase.class.getDeclaredField("v");
    		f.setAccessible(true);
    		FieldUtils.removeFinalModifier(f, true);
    		Object obj = FieldUtils.readField(f, this.settings.get());
			
			noodle_caves_enabled = (boolean) obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!noodle_caves_enabled) {
        //if (!((GeneratorSettingBase) this.settings.get()).n()) { // TODO: n() is noodle_caves_enabled
            return (d0) -> {
                return NoiseModifier.b;
            };
        } else {
            NewDummyChunkGeneratorAbstract.a chunkgeneratorabstract_a = new NewDummyChunkGeneratorAbstract.a(chunkcoordintpair, i);

            chunkgeneratorabstract_a.a(consumer);
            Objects.requireNonNull(chunkgeneratorabstract_a);
            return chunkgeneratorabstract_a::a;
        }
    }

    private DoubleFunction<BaseStoneSource> b(int i, ChunkCoordIntPair chunkcoordintpair, Consumer<NoiseInterpolator> consumer) {
    	
    	boolean ore_veins_enabled = false;
		try {
			Field f = GeneratorSettingBase.class.getDeclaredField("u");
    		f.setAccessible(true);
    		FieldUtils.removeFinalModifier(f, true);
    		Object obj = FieldUtils.readField(f, this.settings.get());
			
			ore_veins_enabled = (boolean) obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!ore_veins_enabled) {
       // if (!((GeneratorSettingBase) this.settings.get()).m()) { // TODO: m() is ore_veins_enabled
            return (d0) -> {
                return this.baseStoneSource;
            };
        } else {
            NewDummyChunkGeneratorAbstract.b chunkgeneratorabstract_b = new NewDummyChunkGeneratorAbstract.b(chunkcoordintpair, i, this.seed + 1L);

            chunkgeneratorabstract_b.a(consumer);
            BaseStoneSource basestonesource = (j, k, l) -> {
                IBlockData iblockdata = chunkgeneratorabstract_b.getBaseBlock(j, k, l);

                return iblockdata != this.defaultBlock ? iblockdata : this.baseStoneSource.getBaseBlock(j, k, l);
            };

            return (d0) -> {
                chunkgeneratorabstract_b.a(d0);
                return basestonesource;
            };
        }
    }

    @Override
    protected Aquifer a(IChunkAccess ichunkaccess) {
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        int i = Math.max(((GeneratorSettingBase) this.settings.get()).b().a(), ichunkaccess.getMinBuildHeight());
        int j = MathHelper.a(i, this.cellHeight);

        return this.a(j, this.cellCountY, chunkcoordintpair);
    }

    @Override
    public int getGenerationDepth() {
        return this.height;
    }

    @Override
    public int getSeaLevel() {
        return ((GeneratorSettingBase) this.settings.get()).g();
    }

    @Override
    public int getMinY() {
        return ((GeneratorSettingBase) this.settings.get()).b().a();
    }

    @Override
    public WeightedRandomList<BiomeSettingsMobs.c> getMobsFor(BiomeBase var0, StructureManager var1, EnumCreatureType var2, BlockPosition var3) {
        if (var1.a(var3, true, (StructureGenerator)StructureGenerator.j).e()) {
          if (var2 == EnumCreatureType.a)
            return StructureGenerator.j.c(); 
          if (var2 == EnumCreatureType.b)
            return StructureGenerator.j.h(); 
        } 
        if (var2 == EnumCreatureType.a) {
          if (var1.a(var3, false, StructureGenerator.b).e())
            return StructureGenerator.b.c(); 
          if (var1.a(var3, false, StructureGenerator.l).e())
            return StructureGenerator.l.c(); 
          if (var1.a(var3, true, StructureGenerator.n).e())
            return StructureGenerator.n.c(); 
        } 
        if (var2 == EnumCreatureType.d && 
          var1.a(var3, false, StructureGenerator.l).e())
          return StructureGenerator.l.i(); 
        return super.getMobsFor(var0, var1, var2, var3);
      }

    @Override
    public void addMobs(RegionLimitedWorldAccess regionlimitedworldaccess) {
    	
    	boolean disableMobGeneration = false;
		try {
			Field f = GeneratorSettingBase.class.getDeclaredField("q");
    		f.setAccessible(true);
    		FieldUtils.removeFinalModifier(f, true);
    		Object obj = FieldUtils.readField(f, this.settings.get());
			
			disableMobGeneration = (boolean) obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!disableMobGeneration) {
      //  if (!((GeneratorSettingBase) this.settings.get()).i()) { //TODO: i() is -> return disableMobGeneration
            ChunkCoordIntPair chunkcoordintpair = regionlimitedworldaccess.a();
            BiomeBase biomebase = regionlimitedworldaccess.getBiome(chunkcoordintpair.l());
            SeededRandom seededrandom = new SeededRandom();

            seededrandom.a(regionlimitedworldaccess.getSeed(), chunkcoordintpair.d(), chunkcoordintpair.e());
            SpawnerCreature.a((WorldAccess) regionlimitedworldaccess, biomebase, chunkcoordintpair, (Random) seededrandom);
        }
    }

    private class a implements NoiseModifier {

        private final NoiseInterpolator toggle;
        private final NoiseInterpolator thickness;
        private final NoiseInterpolator ridgeA;
        private final NoiseInterpolator ridgeB;
        private double factorZ;

        public a(ChunkCoordIntPair chunkcoordintpair, int i) {
            int j = NewDummyChunkGeneratorAbstract.this.cellCountX;
            int k = NewDummyChunkGeneratorAbstract.this.cellCountY;
            int l = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            NoodleCavifier noodlecavifier = NewDummyChunkGeneratorAbstract.this.noodleCavifier;

            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.noodleCavifier);
            this.toggle = new NoiseInterpolator(j, k, l, chunkcoordintpair, i, noodlecavifier::a);
            j = NewDummyChunkGeneratorAbstract.this.cellCountX;
            k = NewDummyChunkGeneratorAbstract.this.cellCountY;
            l = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            noodlecavifier = NewDummyChunkGeneratorAbstract.this.noodleCavifier;
            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.noodleCavifier);
            this.thickness = new NoiseInterpolator(j, k, l, chunkcoordintpair, i, noodlecavifier::b);
            j = NewDummyChunkGeneratorAbstract.this.cellCountX;
            k = NewDummyChunkGeneratorAbstract.this.cellCountY;
            l = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            noodlecavifier = NewDummyChunkGeneratorAbstract.this.noodleCavifier;
            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.noodleCavifier);
            this.ridgeA = new NoiseInterpolator(j, k, l, chunkcoordintpair, i, noodlecavifier::c);
            j = NewDummyChunkGeneratorAbstract.this.cellCountX;
            k = NewDummyChunkGeneratorAbstract.this.cellCountY;
            l = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            noodlecavifier = NewDummyChunkGeneratorAbstract.this.noodleCavifier;
            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.noodleCavifier);
            this.ridgeB = new NoiseInterpolator(j, k, l, chunkcoordintpair, i, noodlecavifier::d);
        }

        public NoiseModifier a(double d0) {
            this.factorZ = d0;
            return this;
        }

        @Override
        public double modifyNoise(double d0, int i, int j, int k) {
            double d1 = this.toggle.c(this.factorZ);
            double d2 = this.thickness.c(this.factorZ);
            double d3 = this.ridgeA.c(this.factorZ);
            double d4 = this.ridgeB.c(this.factorZ);

            return NewDummyChunkGeneratorAbstract.this.noodleCavifier.a(d0, i, j, k, d1, d2, d3, d4, NewDummyChunkGeneratorAbstract.this.getMinY());
        }

        public void a(Consumer<NoiseInterpolator> consumer) {
            consumer.accept(this.toggle);
            consumer.accept(this.thickness);
            consumer.accept(this.ridgeA);
            consumer.accept(this.ridgeB);
        }
    }

    private class b implements BaseStoneSource {

        private final NoiseInterpolator veininess;
        private final NoiseInterpolator veinA;
        private final NoiseInterpolator veinB;
        private double factorZ;
        private final long seed;
        private final SeededRandom random = new SeededRandom();

        public b(ChunkCoordIntPair chunkcoordintpair, int i, long j) {
            int k = NewDummyChunkGeneratorAbstract.this.cellCountX;
            int l = NewDummyChunkGeneratorAbstract.this.cellCountY;
            int i1 = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            OreVeinifier oreveinifier = NewDummyChunkGeneratorAbstract.this.oreVeinifier;

            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.oreVeinifier);
            this.veininess = new NoiseInterpolator(k, l, i1, chunkcoordintpair, i, oreveinifier::a);
            k = NewDummyChunkGeneratorAbstract.this.cellCountX;
            l = NewDummyChunkGeneratorAbstract.this.cellCountY;
            i1 = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            oreveinifier = NewDummyChunkGeneratorAbstract.this.oreVeinifier;
            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.oreVeinifier);
            this.veinA = new NoiseInterpolator(k, l, i1, chunkcoordintpair, i, oreveinifier::b);
            k = NewDummyChunkGeneratorAbstract.this.cellCountX;
            l = NewDummyChunkGeneratorAbstract.this.cellCountY;
            i1 = NewDummyChunkGeneratorAbstract.this.cellCountZ;
            oreveinifier = NewDummyChunkGeneratorAbstract.this.oreVeinifier;
            Objects.requireNonNull(NewDummyChunkGeneratorAbstract.this.oreVeinifier);
            this.veinB = new NoiseInterpolator(k, l, i1, chunkcoordintpair, i, oreveinifier::c);
            this.seed = j;
        }

        public void a(Consumer<NoiseInterpolator> consumer) {
            consumer.accept(this.veininess);
            consumer.accept(this.veinA);
            consumer.accept(this.veinB);
        }

        public void a(double d0) {
            this.factorZ = d0;
        }

        @Override
        public IBlockData getBaseBlock(int i, int j, int k) {
            double d0 = this.veininess.c(this.factorZ);
            double d1 = this.veinA.c(this.factorZ);
            double d2 = this.veinB.c(this.factorZ);

            this.random.a(this.seed, i, j, k);
            return NewDummyChunkGeneratorAbstract.this.oreVeinifier.a(this.random, i, j, k, d0, d1, d2);
        }
    }
}