package de.freesoccerhdx.advancedworldcreator.main;

import java.lang.reflect.Field;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.MethodUtils;


import com.mojang.serialization.Codec;
/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeManager;
import net.minecraft.server.v1_16_R3.BiomeSettingsGeneration;
import net.minecraft.server.v1_16_R3.BiomeSettingsMobs;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockColumn;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.BlockPosition.MutableBlockPosition;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.ChunkGenerator;
import net.minecraft.server.v1_16_R3.ChunkGeneratorAbstract;
import net.minecraft.server.v1_16_R3.ChunkSection;
import net.minecraft.server.v1_16_R3.CrashReport;
import net.minecraft.server.v1_16_R3.DefinedStructureManager;
import net.minecraft.server.v1_16_R3.EnumCreatureType;
import net.minecraft.server.v1_16_R3.GeneratorAccess;
import net.minecraft.server.v1_16_R3.GeneratorSettingBase;
import net.minecraft.server.v1_16_R3.HeightMap;
import net.minecraft.server.v1_16_R3.HeightMap.Type;
import net.minecraft.server.v1_16_R3.IBlockAccess;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.IChunkAccess;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.IRegistryCustom;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.NoiseGenerator;
import net.minecraft.server.v1_16_R3.NoiseGenerator3;
import net.minecraft.server.v1_16_R3.NoiseGenerator3Handler;
import net.minecraft.server.v1_16_R3.NoiseGeneratorOctaves;
import net.minecraft.server.v1_16_R3.NoiseGeneratorPerlin;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.ProtoChunk;
import net.minecraft.server.v1_16_R3.RegionLimitedWorldAccess;
import net.minecraft.server.v1_16_R3.ReportedException;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.SectionPosition;
import net.minecraft.server.v1_16_R3.SeededRandom;
import net.minecraft.server.v1_16_R3.SpawnerCreature;
import net.minecraft.server.v1_16_R3.StructureBoundingBox;
import net.minecraft.server.v1_16_R3.StructureFeature;
import net.minecraft.server.v1_16_R3.StructureFeatures;
import net.minecraft.server.v1_16_R3.StructureGenerator;
import net.minecraft.server.v1_16_R3.StructureManager;
import net.minecraft.server.v1_16_R3.StructurePiece;
import net.minecraft.server.v1_16_R3.StructureSettings;
import net.minecraft.server.v1_16_R3.StructureSettingsFeature;
import net.minecraft.server.v1_16_R3.StructureStart;
import net.minecraft.server.v1_16_R3.SystemUtils;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.WorldChunkManager;
import net.minecraft.server.v1_16_R3.WorldChunkManagerTheEnd;
import net.minecraft.server.v1_16_R3.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenFeatureDefinedStructureJigsawJunction;
import net.minecraft.server.v1_16_R3.WorldGenFeatureDefinedStructurePoolTemplate.Matching;
import net.minecraft.server.v1_16_R3.WorldGenStage.Features;
import net.minecraft.server.v1_16_R3.WorldGenFeaturePillagerOutpostPoolPiece;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import net.minecraft.server.v1_16_R3.WorldGenerator;
*/

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.BlockPosition.MutableBlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.biome.WorldChunkManagerTheEnd;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.HeightMap.Type;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenStage.Features;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.StructureSettingsFeature;
import net.minecraft.world.level.levelgen.feature.structures.WorldGenFeatureDefinedStructureJigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.WorldGenFeatureDefinedStructurePoolTemplate.Matching;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.WorldGenFeaturePillagerOutpostPoolPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureManager;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3Handler;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorOctaves;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorPerlin;

// Chunkgenerator has Methods:
/*
 addMobs, addDecorations, buildBase, buildNoise, createBiomes, createStructures, 
 doCarving, findNearestMapFeature, getBaseHJeight 
 getGeneratioonDepth, getMobsFor, getSeaLevel, getSettings(=StructureSettings), 
 getSpawnHeight, getWorldChunkManager, storeStructures,
 updateStructures
 
 */
public class old_DummyChunkGeneratorAbstract /*extends ChunkGenerator */{
	/*
	 public static final Codec<ChunkGeneratorAbstract> d = ChunkGeneratorAbstract.d;
	  
	  private static final float[] i;
	  
	  private static final float[] j;
	  
	  static {
	   
	    i = SystemUtils.<float[]>a(new float[13824], afloat -> {
	          for (int i = 0; i < 24; i++) {
	            for (int j = 0; j < 24; j++) {
	              for (int k = 0; k < 24; k++)
	                afloat[i * 24 * 24 + j * 24 + k] = (float)b(j - 12, k - 12, i - 12); 
	            } 
	          } 
	        });
	    j = SystemUtils.<float[]>a(new float[25], afloat -> {
	          for (int i = -2; i <= 2; i++) {
	            for (int j = -2; j <= 2; j++) {
	              float f = 10.0F / MathHelper.c((i * i + j * j) + 0.2F);
	              afloat[i + 2 + (j + 2) * 5] = f;
	            } 
	          } 
	        });
	  }
	  
	  private static final IBlockData air = Blocks.a.getBlockData();
	  
	  private final int size_vertical;
	  
	  private final int size_horizontal;
	  
	  private final int n;
	  
	  private final int o;
	  
	  private final int p;
	  
	  protected final SeededRandom randomseed;
	  
	  private final NoiseGeneratorOctaves q;
	  
	  private final NoiseGeneratorOctaves r;
	  
	  private final NoiseGeneratorOctaves s;
	  
	  private final NoiseGenerator t;
	  
	  private final NoiseGeneratorOctaves u;

	  private final NoiseGenerator3Handler v;
	  
	  protected final IBlockData basestone;
	  
	  protected final IBlockData basefluid;
	  
	  private final long seed;
	  
	  protected final Supplier<GeneratorSettingBase> generatorsettingbase;
	  
	  private final int height;
	  
	  private List<WorldGenStage.Decoration> blacklist_deco = new ArrayList<>();
	  
	  private IBlockData bedrocktop = Blocks.z.getBlockData();
	  private IBlockData bedrockbottom = Blocks.z.getBlockData();
	  private boolean cancreatestructures = true;
	  private boolean cancreatedeco = true;
	  private boolean generatemobs = true;
	  private boolean generate_surface = true;
	  
	  private old_DummyChunkGeneratorAbstract(WorldChunkManager worldchunkmanager, long seed,StructureSettings structset, Supplier<GeneratorSettingBase> supplier) {
	    this(worldchunkmanager, worldchunkmanager, seed,structset, supplier);
	  }
	  
	  public void setTopBedrock(Block block) {
		  this.bedrocktop = block.getBlockData();
	  }
	  public void setBottomBedrock(Block block) {
		  this.bedrockbottom = block.getBlockData();
	  }
	  

	 
		public void setDecorationBlacklist(List<WorldGenStage.Decoration> deco) {
			blacklist_deco = deco;
		}
	  
	  private old_DummyChunkGeneratorAbstract(WorldChunkManager worldchunkmanager, WorldChunkManager worldchunkmanager1, long seed, StructureSettings structset, Supplier<GeneratorSettingBase> supplier) {
	    super(worldchunkmanager, worldchunkmanager1, structset, seed);
	    this.seed = seed;
	    GeneratorSettingBase generatorsettingbase = supplier.get();
	    try { // needed because: protected boolean h() { return this.p; } in GeneratorSettingBase
			 Field f = GeneratorSettingBase.class.getDeclaredField("p");
			 f.setAccessible(true);
			
			 FieldUtils.removeFinalModifier(f);
				
		     generatemobs = (boolean) f.get(generatorsettingbase);
			 
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
	    this.generatorsettingbase = supplier;
	    NoiseSettings noisesettings = generatorsettingbase.b();
	    this.height = noisesettings.a();
	    this.size_vertical = noisesettings.f() * 4; // size_vertical
	    this.size_horizontal = noisesettings.g() * 4; // size_horizontal
	    // world base block #stone
	    this.basestone = generatorsettingbase.c(); //Blocks.NETHERRACK.getBlockData();//
	    // world base fluid #water/lava
	    this.basefluid = generatorsettingbase.d();
	    this.n = 16 / this.size_horizontal;
	    this.o = noisesettings.a() / this.size_vertical; // height / this.size_vertical
	    this.p = 16 / this.size_horizontal;
	    this.randomseed = new SeededRandom(seed);
	    this.q = new NoiseGeneratorOctaves(this.randomseed, IntStream.rangeClosed(-15, 0));
	    this.r = new NoiseGeneratorOctaves(this.randomseed, IntStream.rangeClosed(-15, 0));
	    this.s = new NoiseGeneratorOctaves(this.randomseed, IntStream.rangeClosed(-7, 0));
	    // noisesettings.i() => simplex_surface_noise
	    this.t = noisesettings.j() ? new NoiseGenerator3(this.randomseed, IntStream.rangeClosed(-3, 0)) : new NoiseGeneratorOctaves(this.randomseed, IntStream.rangeClosed(-3, 0));
	    this.randomseed.a(2620);
	    this.u = new NoiseGeneratorOctaves(this.randomseed, IntStream.rangeClosed(-15, 0));
	    
	    if (noisesettings.k()) { // if island_noise_override
	      SeededRandom seededrandom = new SeededRandom(seed);
	      seededrandom.a(17292);
	      this.v = new NoiseGenerator3Handler(seededrandom);
	      
	    } else {
	      this.v = null;
	    } 
	    
	    
	  }
	  
	  @Override
	  protected Codec<? extends ChunkGenerator> a() {
	    return d;
	  }
	  
	  public boolean a(long seed, ResourceKey<GeneratorSettingBase> resourcekey) {
	    return (this.seed == seed && this.generatorsettingbase.get().a(resourcekey));
	  }
	  
	  private double a(int i, int j, int k, double d0, double d1, double d2, double d3) {
	    double d4 = 0.0D;
	    double d5 = 0.0D;
	    double d6 = 0.0D;
	    boolean flag = true;
	    double d7 = 1.0D;
	    for (int l = 0; l < 16; l++) {
	      double d8 = NoiseGeneratorOctaves.a(i * d0 * d7);
	      double d9 = NoiseGeneratorOctaves.a(j * d1 * d7);
	      double d10 = NoiseGeneratorOctaves.a(k * d0 * d7);
	      double d11 = d1 * d7;
	      NoiseGeneratorPerlin noisegeneratorperlin = this.q.a(l);
	      if (noisegeneratorperlin != null)
	        d4 += noisegeneratorperlin.a(d8, d9, d10, d11, j * d11) / d7; 
	      NoiseGeneratorPerlin noisegeneratorperlin1 = this.r.a(l);
	      if (noisegeneratorperlin1 != null)
	        d5 += noisegeneratorperlin1.a(d8, d9, d10, d11, j * d11) / d7; 
	      if (l < 8) {
	        NoiseGeneratorPerlin noisegeneratorperlin2 = this.s.a(l);
	        if (noisegeneratorperlin2 != null)
	          d6 += noisegeneratorperlin2.a(NoiseGeneratorOctaves.a(i * d2 * d7), NoiseGeneratorOctaves.a(j * d3 * d7), NoiseGeneratorOctaves.a(k * d2 * d7), d3 * d7, j * d3 * d7) / d7; 
	      } 
	      d7 /= 2.0D;
	    } 
	    return MathHelper.b(d4 / 512.0D, d5 / 512.0D, (d6 / 10.0D + 1.0D) / 2.0D);
	  }
	  
	  private double[] b(int i, int j) {
	    double[] adouble = new double[this.o + 1];
	    a(adouble, i, j);
	    return adouble;
	  }
	  
	  private void a(double[] adouble, int i, int j) {
	    double d0, d1;
	    NoiseSettings noisesettings = this.generatorsettingbase.get().b();
	    if (this.v != null) {
	      d0 = (WorldChunkManagerTheEnd.a(this.v, i, j) - 8.0F);
	      if (d0 > 0.0D) {
	        d1 = 0.25D;
	      } else {
	        d1 = 1.0D;
	      } 
	    } else {
	      float f = 0.0F;
	      float f1 = 0.0F;
	      float f2 = 0.0F;
	      boolean flag = true;
	      int k = getSeaLevel();
	      float f3 = this.b.getBiome(i, k, j).h();
	      for (int l = -2; l <= 2; l++) {
	        for (int i1 = -2; i1 <= 2; i1++) {
	          float f6, f7;
	          BiomeBase biomebase = this.b.getBiome(i + l, k, j + i1);
	          float f4 = biomebase.h();
	          float f5 = biomebase.j();
	          if (noisesettings.l() && f4 > 0.0F) {
	            f6 = 1.0F + f4 * 2.0F;
	            f7 = 1.0F + f5 * 4.0F;
	          } else {
	            f6 = f4;
	            f7 = f5;
	          } 
	          if (f6 < -1.8F)
	            f6 = -1.8F; 
	          float f8 = (f4 > f3) ? 0.5F : 1.0F;
	          float f9 = f8 * old_DummyChunkGeneratorAbstract.j[l + 2 + (i1 + 2) * 5] / (f6 + 2.0F);
	          f += f7 * f9;
	          f1 += f6 * f9;
	          f2 += f9;
	        } 
	      } 
	      float f10 = f1 / f2;
	      float f11 = f / f2;
	      double d15 = (f10 * 0.5F - 0.125F);
	      double d16 = (f11 * 0.9F + 0.1F);
	      d0 = d15 * 0.265625D;
	      d1 = 96.0D / d16;
	    } 
	    double d4 = 684.412D * noisesettings.b().a();
	    double d5 = 684.412D * noisesettings.b().b();
	    double d6 = d4 / noisesettings.b().c();
	    double d7 = d5 / noisesettings.b().d();
	    double d2 = noisesettings.c().a();
	    double d3 = noisesettings.c().b();
	    double d8 = noisesettings.c().c();
	    double d9 = noisesettings.d().a();
	    double d10 = noisesettings.d().b();
	    double d11 = noisesettings.d().c();
	    double d12 = noisesettings.j() ? c(i, j) : 0.0D;
	    double d13 = noisesettings.g();
	    double d14 = noisesettings.h();
	    for (int j1 = 0; j1 <= this.o; j1++) {
	      double d15 = a(i, j1, j, d4, d5, d6, d7);
	      double d16 = 1.0D - j1 * 2.0D / this.o + d12;
	      double d17 = d16 * d13 + d14;
	      double d18 = (d17 + d0) * d1;
	      if (d18 > 0.0D) {
	        d15 += d18 * 4.0D;
	      } else {
	        d15 += d18;
	      } 
	      if (d3 > 0.0D) {
	        double d19 = ((this.o - j1) - d8) / d3;
	        d15 = MathHelper.b(d2, d15, d19);
	      } 
	      if (d10 > 0.0D) {
	        double d19 = (j1 - d11) / d10;
	        d15 = MathHelper.b(d9, d15, d19);
	      }
	     // Bukkit.broadcastMessage("val: " +d15);
	      adouble[j1] = d15;
	    } 
	    
	 //   for(int _i = 0; _i < adouble.length; _i++) {
	//    	adouble[_i] = 0.2/512;
	//    }
	    
	  }
	  
	  private double c(int i, int j) {
	    double d1, d0 = this.u.a((i * 200), 10.0D, (j * 200), 1.0D, 0.0D, true);
	    if (d0 < 0.0D) {
	      d1 = -d0 * 0.3D;
	    } else {
	      d1 = d0;
	    } 
	    double d2 = d1 * 24.575625D - 2.0D;
	    return (d2 < 0.0D) ? (d2 * 0.009486607142857142D) : (Math.min(d2, 1.0D) * 0.006640625D);
	  }
	  
	  public int getBaseHeight(int i, int j, HeightMap.Type heightmap_type) {
	   return a(i, j, null, heightmap_type.e());
	  }
	  
	  // not used here
	  @Override
	public IBlockAccess a(int x, int z) {
		 IBlockData[] aiblockdata = new IBlockData[this.o * this.size_vertical];
	    //IBlockData[] aiblockdata = new IBlockData[64];
	    a(x, z, aiblockdata, null);
	    return new BlockColumn(aiblockdata);
	  }
	  
	  private int a(int i, int j, IBlockData[] aiblockdata, Predicate<IBlockData> predicate) {
		
	    int k = Math.floorDiv(i, this.size_horizontal);
	    int l = Math.floorDiv(j, this.size_horizontal);
	    int i1 = Math.floorMod(i, this.size_horizontal);
	    int j1 = Math.floorMod(j, this.size_horizontal);
	    double d0 = i1 / this.size_horizontal;
	    double d1 = j1 / this.size_horizontal;
	    double[][] adouble = { b(k, l), b(k, l + 1), b(k + 1, l), b(k + 1, l + 1) };
	    // this.o =32? maxHeight/8 ?
	    for (int k1 = this.o - 1; k1 >= 0; k1--) {
	      double d2 = adouble[0][k1];
	      double d3 = adouble[1][k1];
	      double d4 = adouble[2][k1];
	      double d5 = adouble[3][k1];
	      double d6 = adouble[0][k1 + 1];
	      double d7 = adouble[1][k1 + 1];
	      double d8 = adouble[2][k1 + 1];
	      double d9 = adouble[3][k1 + 1];
	      // this.l = 8
	      for (int l1 = this.size_vertical - 1; l1 >= 0; l1--) {
	        double d10 = l1 / this.size_vertical;
	        double d11 = MathHelper.a(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
	        int i2 = k1 * this.size_vertical + l1; // the real y value
	        IBlockData iblockdata = a(d11, i2);
	        if (aiblockdata != null)
	          aiblockdata[/*aiblockdata.length-1i2] = iblockdata; 
	        if (predicate != null && predicate.test(iblockdata))
	          return i2 + 1; 
	      } 
	    } 
	    return 0;
	  }
	  
	  protected IBlockData a(double d0, int i) {
	    IBlockData iblockdata;
	     TODO: custom maxWorldHeight
	    if(i > 64) {
	    	return air;
	    }
	    
	    if (d0 > 0.0D) { // not sea
	      iblockdata = this.basestone; // stone material
	    } else if (i < getSeaLevel()) { // under sea
	      iblockdata = this.basefluid; // fluid material
	    } else {
	      iblockdata = air; // air
	    } 
	    return iblockdata;
	  }

	public void setGenerateSurface(boolean b) {
		generate_surface = b;
	}
	
	// toplayer generation
	@Override
	public void buildBase(RegionLimitedWorldAccess regionlimitedworldaccess, IChunkAccess ichunkaccess) {
		  if(generate_surface) {
		    ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
		    int i = chunkcoordintpair.b;
		    int j = chunkcoordintpair.c;
		    SeededRandom seededrandom = new SeededRandom();
		    seededrandom.a(i, j);
		    ChunkCoordIntPair chunkcoordintpair1 = ichunkaccess.getPos();
		    int k = chunkcoordintpair1.d();
		    int l = chunkcoordintpair1.e();
		    double d0 = 0.0625D;
		    //d0 = 0.0000; -> not so flat
		    //d0 = 0.9; -> Very so flat
		    BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
		    for (int i1 = 0; i1 < 16; i1++) {
		      for (int j1 = 0; j1 < 16; j1++) {
		        int k1 = k + i1;
		        int l1 = l + j1;
		        int i2 = ichunkaccess.getHighestBlock(HeightMap.Type.a, i1, j1) + 1;
		        double d1 = this.t.a(k1 * d0, l1 * d0, d0, i1 * d0) * 15.0D;
		        // generates surface of each biome
		        BiomeBase biomebase = regionlimitedworldaccess.getBiome(blockposition_mutableblockposition.d(k + i1, i2, l + j1)); 
		        biomebase.a(seededrandom, ichunkaccess, k1, l1, i2, d1, this.basestone,this.basefluid, getSeaLevel(), regionlimitedworldaccess.getSeed());
		      } 
		    } 
		    a(ichunkaccess, seededrandom);
		  }
	}

	//set bedrock on top and bottom
	private void a(IChunkAccess ichunkaccess, Random random) {
	    BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
	    int i = ichunkaccess.getPos().d();
	    int j = ichunkaccess.getPos().e();
	    GeneratorSettingBase generatorsettingbase = this.h.get();
	    int k = generatorsettingbase.f();
	    int l = this.height - 1 - generatorsettingbase.e();
	    boolean flag = true;
	    boolean flag1 = (l + 4 >= 0 && l < this.height);
	    boolean flag2 = (k + 4 >= 0 && k < this.height);
	    if (flag1 || flag2) {
	      Iterator<BlockPosition> iterator = BlockPosition.b(i, 0, j, i + 15, 0, j + 15).iterator();
	      while (iterator.hasNext()) {
	        BlockPosition blockposition = iterator.next();
	        if (flag1)
	          for (int i1 = 0; i1 < 5; i1++) {
	            if (i1 <= random.nextInt(5))
	            	// top bedrock
	              ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), l - i1, blockposition.getZ()), this.bedrocktop, false); 
	          }  
	        if (flag2)
	          for (int i1 = 4; i1 >= 0; i1--) {
	            if (i1 <= random.nextInt(5))
	            	// bottom bedrock
	              ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), k + i1, blockposition.getZ()), this.bedrockbottom, false); 
	          }  
	      } 
	    } 
	  }
	  
	
	@Override
	public void buildNoise(GeneratorAccess generatoraccess, StructureManager structuremanager, IChunkAccess ichunkaccess) {
	      List<StructurePiece> objectlist = new ArrayList<>(8);
	      List<WorldGenFeatureDefinedStructureJigsawJunction> objectlist1 = new ArrayList<>(32);
	      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
	      int i = chunkcoordintpair.b;
	      int j = chunkcoordintpair.c;
	      int k = i << 4;
	      int l = j << 4;
	      Iterator iterator = StructureGenerator.t.iterator();

	      while(iterator.hasNext()) {
	         StructureGenerator<?> structuregenerator = (StructureGenerator)iterator.next();
	         structuremanager.a(SectionPosition.a(chunkcoordintpair, 0), structuregenerator).forEach((structurestart) -> {
	            Iterator iterator1 = structurestart.d().iterator();

	            while(true) {
	               while(true) {
	                  StructurePiece structurepiece;
	                  do {
	                     if (!iterator1.hasNext()) {
	                        return;
	                     }

	                     structurepiece = (StructurePiece)iterator1.next();
	                  } while(!structurepiece.a(chunkcoordintpair, 12));

	                  if (structurepiece instanceof WorldGenFeaturePillagerOutpostPoolPiece) {
	                     WorldGenFeaturePillagerOutpostPoolPiece worldgenfeaturepillageroutpostpoolpiece = (WorldGenFeaturePillagerOutpostPoolPiece)structurepiece;
	                     Matching worldgenfeaturedefinedstructurepooltemplate_matching = worldgenfeaturepillageroutpostpoolpiece.b().e();
	                     if (worldgenfeaturedefinedstructurepooltemplate_matching == Matching.b) {
	                        objectlist.add(worldgenfeaturepillageroutpostpoolpiece);
	                     }

	                     Iterator iterator2 = worldgenfeaturepillageroutpostpoolpiece.e().iterator();

	                     while(iterator2.hasNext()) {
	                        WorldGenFeatureDefinedStructureJigsawJunction worldgenfeaturedefinedstructurejigsawjunction = (WorldGenFeatureDefinedStructureJigsawJunction)iterator2.next();
	                        int i1 = worldgenfeaturedefinedstructurejigsawjunction.a();
	                        int j1 = worldgenfeaturedefinedstructurejigsawjunction.c();
	                        if (i1 > k - 12 && j1 > l - 12 && i1 < k + 15 + 12 && j1 < l + 15 + 12) {
	                           objectlist1.add(worldgenfeaturedefinedstructurejigsawjunction);
	                        }
	                     }
	                  } else {
	                     objectlist.add(structurepiece);
	                  }
	               }
	            }
	         });
	      }

	      double[][][] adouble = new double[2][this.p + 1][this.o + 1];

	      for(int i1 = 0; i1 < this.p + 1; ++i1) {
	         adouble[0][i1] = new double[this.o + 1];
	         this.a(adouble[0][i1], i * this.n, j * this.p + i1);
	         adouble[1][i1] = new double[this.o + 1];
	      }

	      ProtoChunk protochunk = (ProtoChunk)ichunkaccess;
	      HeightMap heightmap = protochunk.a(Type.OCEAN_FLOOR_WG);
	      HeightMap heightmap1 = protochunk.a(Type.WORLD_SURFACE_WG);
	      MutableBlockPosition blockposition_mutableblockposition = new MutableBlockPosition();
	      Iterator<StructurePiece> objectlistiterator = objectlist.iterator();
	      Iterator<WorldGenFeatureDefinedStructureJigsawJunction> objectlistiterator1 = objectlist1.iterator();

	      for(int j1 = 0; j1 < this.n; ++j1) {
	         int k1;
	         for(k1 = 0; k1 < this.p + 1; ++k1) {
	            this.a(adouble[1][k1], i * this.n + j1 + 1, j * this.p + k1);
	         }

	         for(k1 = 0; k1 < this.p; ++k1) {
	            ChunkSection chunksection = protochunk.a(15);
	            chunksection.a();

	            for(int l1 = this.o - 1; l1 >= 0; --l1) {
	               double d0 = adouble[0][k1][l1];
	               double d1 = adouble[0][k1 + 1][l1];
	               double d2 = adouble[1][k1][l1];
	               double d3 = adouble[1][k1 + 1][l1];
	               double d4 = adouble[0][k1][l1 + 1];
	               double d5 = adouble[0][k1 + 1][l1 + 1];
	               double d6 = adouble[1][k1][l1 + 1];
	               double d7 = adouble[1][k1 + 1][l1 + 1];

	               for(int i2 = this.size_vertical - 1; i2 >= 0; --i2) {
	                  int j2 = l1 * this.size_vertical + i2;
	                  int k2 = j2 & 15;
	                //int l2 = 15- (j2 >> 4); turn around world chunk-chunks
	                  int l2 = (j2 >> 4);
	                  if (chunksection.getYPosition() >> 4 != l2) {
	                     chunksection.b();
	                     chunksection = protochunk.a(l2);
	                     chunksection.a();
	                  }

	                  double d8 = (double)i2 / (double)this.size_vertical;
	                  double d9 = MathHelper.d(d8, d0, d4);
	                  double d10 = MathHelper.d(d8, d2, d6);
	                  double d11 = MathHelper.d(d8, d1, d5);
	                  double d12 = MathHelper.d(d8, d3, d7);

	                  for(int i3 = 0; i3 < this.size_horizontal; ++i3) {
	                     int j3 = k + j1 * this.size_horizontal + i3;
	                     int k3 = j3 & 15;
	                     double d13 = (double)i3 / (double)this.size_horizontal;
	                     double d14 = MathHelper.d(d13, d9, d10);
	                     double d15 = MathHelper.d(d13, d11, d12);

	                     for(int l3 = 0; l3 < this.size_horizontal; ++l3) {
	                        int i4 = l + k1 * this.size_horizontal + l3;
	                        int j4 = i4 & 15;

	                        
	                        double d16 = (double)l3 / (double)this.size_horizontal;
	                        double d17 = MathHelper.d(d16, d14, d15);
	                        double d18 = MathHelper.a(d17 / 200.0D, -1.0D, 1.0D);

	                        int k4;
	                        int l4;
	                        int i5;
	                        for(d18 = d18 / 2.0D - d18 * d18 * d18 / 24.0D; objectlistiterator.hasNext(); d18 += a(k4, l4, i5) * 0.8D) {
	                           StructurePiece structurepiece = objectlistiterator.next();
	                           StructureBoundingBox structureboundingbox = structurepiece.g();
	                           k4 = Math.max(0, Math.max(structureboundingbox.a - j3, j3 - structureboundingbox.d));
	                           l4 = j2 - (structureboundingbox.b + (structurepiece instanceof WorldGenFeaturePillagerOutpostPoolPiece ? ((WorldGenFeaturePillagerOutpostPoolPiece)structurepiece).d() : 0));
	                           i5 = Math.max(0, Math.max(structureboundingbox.c - i4, i4 - structureboundingbox.f));
	                        }
	                        
	                        objectlistiterator = objectlist.iterator();

	                        while(objectlistiterator1.hasNext()) {
	                           WorldGenFeatureDefinedStructureJigsawJunction worldgenfeaturedefinedstructurejigsawjunction = objectlistiterator1.next();
	                           int j5 = j3 - worldgenfeaturedefinedstructurejigsawjunction.a();
	                           k4 = j2 - worldgenfeaturedefinedstructurejigsawjunction.b();
	                           l4 = i4 - worldgenfeaturedefinedstructurejigsawjunction.c();
	                           d18 += a(j5, k4, l4) * 0.4D;
	                        }

	                        objectlistiterator1 = objectlist1.iterator();
	                        IBlockData iblockdata = a(d18, j2);//Blocks.GOLD_BLOCK.getBlockData();// a(d18, j2);
	                        if (iblockdata != air) {
	                           if (iblockdata.f() != 0) {
	                              blockposition_mutableblockposition.d(j3, j2, i4);
	                              protochunk.k(blockposition_mutableblockposition);
	                           }
	                        //   System.out.println("k2: " + k2 + " j2: " + j2);
	                           chunksection.setType(k3, k2, j4, iblockdata, false);
	                           heightmap.a(k3, j2, j4, iblockdata);
	                           heightmap1.a(k3, j2, j4, iblockdata);
	                        }
	                     }
	                  }
	               }
	            }

	            chunksection.b();
	         }

	         double[][] adouble1 = adouble[0];
	         adouble[0] = adouble[1];
	         adouble[1] = adouble1;
	      }

	   }
	  
	  private static double a(int i, int j, int k) {
	    int l = i + 12;
	    int i1 = j + 12;
	    int j1 = k + 12;
	    return (l >= 0 && l < 24) ? ((i1 >= 0 && i1 < 24) ? ((j1 >= 0 && j1 < 24) ? old_DummyChunkGeneratorAbstract.i[j1 * 24 * 24 + l * 24 + i1] : 0.0D) : 0.0D) : 0.0D;
	  }
	  
	  private static double b(int i, int j, int k) {
	    double d0 = (i * i + k * k);
	    double d1 = j + 0.5D;
	    double d2 = d1 * d1;
	    double d3 = Math.pow(Math.E, -(d2 / 16.0D + d0 / 16.0D));
	    double d4 = -d1 * MathHelper.i(d2 / 2.0D + d0 / 2.0D) / 2.0D;
	    return d4 * d3;
	  }
	  
	  @Override
	  public int getGenerationDepth() {
	    return this.height;
	  }
	  
	  @Override
	 public int getSeaLevel() {
	    return this.generatorsettingbase.get().g();
	  }
	  
	  @Override
	public List<BiomeSettingsMobs.c> getMobsFor(BiomeBase biomebase, StructureManager structuremanager, EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
	    if (structuremanager.a(blockposition, true, StructureGenerator.SWAMP_HUT).e()) {
	      if (enumcreaturetype == EnumCreatureType.MONSTER)
	        return StructureGenerator.SWAMP_HUT.c(); 
	      if (enumcreaturetype == EnumCreatureType.CREATURE)
	        return StructureGenerator.SWAMP_HUT.j(); 
	    } 
	    if (enumcreaturetype == EnumCreatureType.MONSTER) {
	      if (structuremanager.a(blockposition, false, StructureGenerator.PILLAGER_OUTPOST).e())
	        return StructureGenerator.PILLAGER_OUTPOST.c(); 
	      if (structuremanager.a(blockposition, false, StructureGenerator.MONUMENT).e())
	        return StructureGenerator.MONUMENT.c(); 
	      if (structuremanager.a(blockposition, true, StructureGenerator.FORTRESS).e())
	        return StructureGenerator.FORTRESS.c(); 
	    } 
	    return super.getMobsFor(biomebase, structuremanager, enumcreaturetype, blockposition);
	  }
	  
	  @Override
	public void addMobs(RegionLimitedWorldAccess regionlimitedworldaccess) {
		  
		 
		  
	    //if (!((GeneratorSettingBase)this.h.get())) {
		  if(! generatemobs) {
		      int i = regionlimitedworldaccess.a();
		      int j = regionlimitedworldaccess.b();
		      BiomeBase biomebase = regionlimitedworldaccess.getBiome((new ChunkCoordIntPair(i, j)).l());
		      SeededRandom seededrandom = new SeededRandom();
		      seededrandom.a(regionlimitedworldaccess.getSeed(), i << 4, j << 4);
		      SpawnerCreature.a(regionlimitedworldaccess, biomebase, i, j, seededrandom);
	    } 
	  }
	  
	  public void setCanCreateStructures(boolean b) {
		  cancreatestructures = b;
	  }
	  public boolean canCreateStructures() {
		  return cancreatestructures;
	  }
	  
	  @Override
	  public void createStructures(IRegistryCustom iregistrycustom, StructureManager structuremanager, IChunkAccess ichunkaccess, DefinedStructureManager definedstructuremanager, long i) {
		  if(canCreateStructures()) {
			  super.createStructures(iregistrycustom, structuremanager, ichunkaccess, definedstructuremanager, i);
		  }
	  }
	  
	  
	  @Override
	  public void createStructures(IRegistryCustom iregistrycustom, StructureManager structuremanager,
				IChunkAccess ichunkaccess, DefinedStructureManager definedstructuremanager, long i) {
		  if(canCreateStructures()) {
			  ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
			  BiomeBase biomebase = this.b.getBiome((chunkcoordintpair.x << 2) + 2, 0, (chunkcoordintpair.z << 2) + 2);
			  // Stronghold
			  a(StructureFeatures.k, iregistrycustom, structuremanager, ichunkaccess, definedstructuremanager, i, chunkcoordintpair, biomebase);
			  
			  Iterator<Supplier<StructureFeature<?, ?>>> iterator = biomebase.e().a().iterator();
			  
			  while (iterator.hasNext()) {
				  Supplier<StructureFeature<?, ?>> supplier = iterator.next();
				  StructureFeature<?, ?> structurefeature = supplier.get();
				  if (StructureFeature.c == StructureGenerator.STRONGHOLD) {
					  synchronized (structurefeature) {
						  a(structurefeature, iregistrycustom, structuremanager, ichunkaccess, definedstructuremanager, i, chunkcoordintpair, biomebase);
					  }
					  continue;
				  }
				  a(structurefeature, iregistrycustom, structuremanager, ichunkaccess, definedstructuremanager, i, chunkcoordintpair, biomebase);
			  }
		  }
	  }
	  private void a(StructureFeature<?, ?> structurefeature, IRegistryCustom iregistrycustom,
				StructureManager structuremanager, IChunkAccess ichunkaccess,
				DefinedStructureManager definedstructuremanager, long i, ChunkCoordIntPair chunkcoordintpair,
				BiomeBase biomebase) {
		  
			StructureStart<?> structurestart = structuremanager.a(SectionPosition.a(ichunkaccess.getPos(), 0), (StructureGenerator<?>) structurefeature.d, ichunkaccess);
			int j = (structurestart != null) ? structurestart.j() : 0;

			
			//Method Privat:	updateStructureSettings(structuremanager.getWorld(), getSettings());
			try {
				
				Class superclass = this.getClass().getSuperclass();
				Method updateStructureSettings = superclass.getDeclaredMethod("updateStructureSettings", World.class, StructureSettings.class);
				updateStructureSettings.setAccessible(true);
				updateStructureSettings.invoke(this, structuremanager.getWorld(), getSettings());
				
			//	MethodUtils.invokeMethod(, "updateStructureSettings", structuremanager.getWorld(), getSettings());
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			

			
			StructureSettingsFeature structuresettingsfeature = getSettings()
					.a((StructureGenerator<?>) structurefeature.d);
			if (structuresettingsfeature != null) {
				StructureStart<?> structurestart1 = structurefeature.a(iregistrycustom, this, this.b,
						definedstructuremanager, i, chunkcoordintpair, biomebase, j, structuresettingsfeature);
				structuremanager.a(SectionPosition.a(ichunkaccess.getPos(), 0), (StructureGenerator<?>) structurefeature.d,
						structurestart1, ichunkaccess);
			}
		}
	  
	  
	  public void setCanCreateDecorations(boolean b) {
		  cancreatedeco = b;
	  }
	  public boolean canCreateDecorations() {
		  return cancreatedeco;
	  }
	  @Override
	  public void addDecorations(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager) {
		  if(canCreateDecorations()) {
				int i = regionlimitedworldaccess.a();
				int j = regionlimitedworldaccess.b();
				int k = i * 16;
				int l = j * 16;
				BlockPosition blockposition = new BlockPosition(k, 0, l);
				BiomeBase biomebase = this.b.getBiome((i << 2) + 2, 2, (j << 2) + 2);
				SeededRandom seededrandom = new SeededRandom();
				long i1 = seededrandom.a(regionlimitedworldaccess.getSeed(), k, l);
				try {
					// 	biomebase.a(structuremanager, this, regionlimitedworldaccess, i1, seededrandom, blockposition);
					a(biomebase,structuremanager, this, regionlimitedworldaccess, i1, seededrandom, blockposition);
				} catch (Exception exception) {
					CrashReport crashreport = CrashReport.a(exception, "Biome decoration");
					crashreport.a("Generation").a("CenterX", Integer.valueOf(i)).a("CenterZ", Integer.valueOf(j))
							.a("Seed", Long.valueOf(i1)).a("Biome", biomebase);
					throw new ReportedException(crashreport);
				}
		  }
	  }
	  private static Field getPrivateFinalDeclaredField(Object target, String name) {
			try {
				Field pfield = target.getClass().getDeclaredField(name);
				pfield.setAccessible(true);
				FieldUtils.removeFinalModifier(pfield);
				
				return pfield;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
//	 	biomebase.a(structuremanager, this, regionlimitedworldaccess, i1, seededrandom, blockposition);
	  
	
	  
	private void a(BiomeBase biomebase, StructureManager structuremanager, ChunkGenerator chunkgenerator, RegionLimitedWorldAccess regionlimitedworldaccess, long i, SeededRandom seededrandom, BlockPosition blockposition) {
		  
		Map<Integer, List<StructureGenerator<?>>> g = null;
		try {
			g = (Map<Integer, List<StructureGenerator<?>>>) FieldUtils.readField(getPrivateFinalDeclaredField(biomebase,"g"), biomebase);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  
		  List<List<Supplier<WorldGenFeatureConfigured<?, ?>>>> list = biomebase.e().c();
	        
	        int k = -1;
	        for (WorldGenStage.Decoration deco : WorldGenStage.Decoration.values()) {
	        	k ++;
	            int l = 0;

	            // GeneratorSettings.shouldGenerateMapFeatures
	            if (structuremanager.a() && g != null) {
	            	
	                List<StructureGenerator<?>> list1 = (List) g.getOrDefault(k, Collections.emptyList());

	                for (Iterator<StructureGenerator<?>> iterator = list1.iterator(); iterator.hasNext(); ++l) {
	                    StructureGenerator<?> structuregenerator = (StructureGenerator<?>) iterator.next();

	                    seededrandom.b(i, l, k);
	                    int i1 = blockposition.getX() >> 4;
	                    int j1 = blockposition.getZ() >> 4;
	                    int k1 = i1 << 4;
	                    int l1 = j1 << 4;

	                    try {
	                        structuremanager.a(SectionPosition.a(blockposition), structuregenerator).forEach((structurestart) -> {
	                            structurestart.a(regionlimitedworldaccess, structuremanager, chunkgenerator, seededrandom, new StructureBoundingBox(k1, l1, k1 + 15, l1 + 15), new ChunkCoordIntPair(i1, j1));
	                        });
	                    } catch (Exception exception) {
	                        CrashReport crashreport = CrashReport.a(exception, "Feature placement");

	                        crashreport.a("Feature").a("Id", (Object) IRegistry.STRUCTURE_FEATURE.getKey(structuregenerator)).a("Description", () -> {
	                            return structuregenerator.toString();
	                        });
	                        throw new ReportedException(crashreport);
	                    }
	                }
	            }
	            
	            if (list.size() > k && !blacklist_deco.contains(deco)) {
	                for (Iterator iterator1 = ((List) list.get(k)).iterator(); iterator1.hasNext(); ++l) {
	                    Supplier<WorldGenFeatureConfigured<?, ?>> supplier = (Supplier) iterator1.next();
	                    WorldGenFeatureConfigured<?, ?> worldgenfeatureconfigured = (WorldGenFeatureConfigured) supplier.get();

	                    seededrandom.b(i, l, k);
	                    
	                    try {
	                        worldgenfeatureconfigured.a(regionlimitedworldaccess, chunkgenerator, seededrandom, blockposition);
	                    } catch (Exception exception1) {
	                        CrashReport crashreport1 = CrashReport.a(exception1, "Feature placement");

	                        crashreport1.a("Feature").a("Id", (Object) IRegistry.FEATURE.getKey(worldgenfeatureconfigured.e)).a("Config", (Object) worldgenfeatureconfigured.f).a("Description", () -> {
	                            return worldgenfeatureconfigured.e.toString();
	                        });
	                        throw new ReportedException(crashreport1);
	                    }
	                }
	            }
	        }
	        
		}
	  
	private boolean disable_air_caves = false;
	private boolean disable_liquid_caves = false;
	  
	public void setAirCavesDisabled(boolean b) {
		disable_air_caves = b;
	}
	public void setLiquidCavesDisabled(boolean b) {
		disable_liquid_caves = b;
	}
	
	@Override
	public void doCarving(long i, BiomeManager biomemanager, IChunkAccess ichunkaccess,
			WorldGenStage.Features worldgenstage_features) {
		
		if(disable_air_caves && worldgenstage_features == Features.a) return;
		if(disable_liquid_caves && worldgenstage_features == Features.b) return;
		
		BiomeManager biomemanager1 = biomemanager.a(this.b);
		SeededRandom seededrandom = new SeededRandom();
		ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
		int j = chunkcoordintpair.b;
		int k = chunkcoordintpair.c;
		BiomeSettingsGeneration biomesettingsgeneration = this.b
				.getBiome(chunkcoordintpair.b << 2, 0, chunkcoordintpair.c << 2).e();
		BitSet bitset = ((ProtoChunk) ichunkaccess).b(worldgenstage_features);
		
		for (int l = j - 8; l <= j + 8; l++) {
			for (int i1 = k - 8; i1 <= k + 8; i1++) {
				List<Supplier<WorldGenCarverWrapper<?>>> list = biomesettingsgeneration.a(worldgenstage_features);
				ListIterator<Supplier<WorldGenCarverWrapper<?>>> listiterator = list.listIterator();
				
				while (listiterator.hasNext()) {
					int j1 = listiterator.nextIndex();
					Supplier<WorldGenCarverWrapper<?>> nextsup = listiterator.next();
					WorldGenCarverWrapper<?> worldgencarverwrapper = nextsup.get();
					
					seededrandom.c(i + j1, l, i1);
					if (worldgencarverwrapper.a(seededrandom, l, i1))
						worldgencarverwrapper.a(ichunkaccess, biomemanager1::a, seededrandom, getSeaLevel(), l, i1, j,
								k, bitset);
				}
			}
		}
	}

	@Override
	public CompletableFuture<IChunkAccess> buildNoise(Executor paramExecutor, StructureManager paramStructureManager,
			IChunkAccess paramIChunkAccess) {
		// TODO Auto-generated method stub
		return null;
	}

	public BlockColumn getBaseColumn(int var0, int var1, LevelHeightAccessor var2) {
	    int var3 = Math.max(((GeneratorSettingBase)this.generatorsettingbase.get()).b().a(), var2.getMinBuildHeight());
	    int var4 = Math.min(((GeneratorSettingBase)this.generatorsettingbase.get()).b().a() + ((GeneratorSettingBase)this.generatorsettingbase.get()).b().b(), var2.getMaxBuildHeight());
	    int var5 = MathHelper.a(var3, this.j);
	    int var6 = MathHelper.a(var4 - var3, this.j);
	    if (var6 <= 0)
	      return new BlockColumn(var3, i); 
	    IBlockData[] var7 = new IBlockData[var6 * this.j];
	    a(var0, var1, var7, (Predicate<IBlockData>)null, var5, var6);
	    return new BlockColumn(var3, var7);
	  }

	@Override
	public int getBaseHeight(int var0, int var1, HeightMap.Type var2, LevelHeightAccessor var3) {
		
		int var4 = Math.max(((GeneratorSettingBase)this.generatorsettingbase.get()).b().a(), var3.getMinBuildHeight());
	    int var5 = Math.min(((GeneratorSettingBase)this.generatorsettingbase.get()).b().a() + ((GeneratorSettingBase)this.generatorsettingbase.get()).b().b(), var3.getMaxBuildHeight());
	    int var6 = MathHelper.a(var4, this.j);
	    int var7 = MathHelper.a(var5 - var4, this.j);
	    if (var7 <= 0)
	      return var3.getMinBuildHeight(); 
	    
	    return a(var0, var1, (IBlockData[])null, var2.e(), var6, var7).orElse(var3.getMinBuildHeight());
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new ChunkGeneratorAbstract(this.b.a(seed), seed, this.generatorsettingbase);
	}
	
	  
	  
	  
	  */
}
