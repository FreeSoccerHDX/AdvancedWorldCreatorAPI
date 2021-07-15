package de.freesoccerhdx.advancedworldcreator.biomegenerators;



import java.lang.reflect.Field;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.plaf.multi.MultiOptionPaneUI;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils;
//import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import net.minecraft.core.IRegistry;
import net.minecraft.data.worldgen.biome.BiomeRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.WorldChunkManager;
/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeRegistry;
import net.minecraft.server.v1_16_R3.Biomes;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.NoiseGeneratorNormal;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.SeededRandom;
import net.minecraft.server.v1_16_R3.WorldChunkManager;
import net.minecraft.server.v1_16_R3.WorldChunkManagerMultiNoise;
*/
import net.minecraft.world.level.biome.WorldChunkManagerMultiNoise;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.SimpleRandomSource;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class MultiNoiseBiomeGenerator extends WorldChunkManager {
	   private static final MultiNoiseBiomeGenerator.aa g = new MultiNoiseBiomeGenerator.aa(-7, ImmutableList.of(1.0D, 1.0D));
	   
	   public static final MapCodec<WorldChunkManagerMultiNoise> e = WorldChunkManagerMultiNoise.e;/*RecordCodecBuilder.mapCodec((var0) -> {
	      return var0.group(Codec.LONG.fieldOf("seed").forGetter((var0x) -> {
	         return var0x.r;
	      }), RecordCodecBuilder.create((var0x) -> {
	         return var0x.group(net.minecraft.server.v1_16_R3.BiomeBase.c.a.fieldOf("parameters").forGetter(Pair::getFirst), BiomeBase.d.fieldOf("biome").forGetter(Pair::getSecond)).apply(var0x, Pair::of);
	      }).listOf().fieldOf("biomes").forGetter((var0x) -> {
	         return var0x.p;
	      }), DummyWorldChunkManagerMultiNoise.aa.a.fieldOf("temperature_noise").forGetter((var0x) -> {
	         return var0x.h;
	      }), DummyWorldChunkManagerMultiNoise.aa.a.fieldOf("humidity_noise").forGetter((var0x) -> {
	         return var0x.i;
	      }), DummyWorldChunkManagerMultiNoise.aa.a.fieldOf("altitude_noise").forGetter((var0x) -> {
	         return var0x.j;
	      }), DummyWorldChunkManagerMultiNoise.aa.a.fieldOf("weirdness_noise").forGetter((var0x) -> {
	         return var0x.k;
	      })).apply(var0, DummyWorldChunkManagerMultiNoise::new);
	   });
	   */
	   public static final Codec<MultiNoiseBiomeGenerator> f;
	  // private final MultiNoiseBiomeGenerator.aa h; not used
	   // private final MultiNoiseBiomeGenerator.aa i; not used
	   // private final MultiNoiseBiomeGenerator.aa j; not used
	   //private final MultiNoiseBiomeGenerator.aa k; not used
	   private  NoiseGeneratorNormal l;
	   private NoiseGeneratorNormal m;
	   private  NoiseGeneratorNormal n;
	   private  NoiseGeneratorNormal o;
	   private final List<Pair<BiomeBase.c, Supplier<BiomeBase>>> p;
	   private final boolean q;
	   private final long r;
	   private final Optional<Pair<IRegistry<BiomeBase>, MultiNoiseBiomeGenerator.bb>> s;

	   private MultiNoiseBiomeGenerator(long var0, List<Pair<BiomeBase.c, Supplier<BiomeBase>>> var2) {
		   this(var0,var2,g,g,g,g,null);
	   }
	   
	   private MultiNoiseBiomeGenerator(long var0, List<Pair<BiomeBase.c, Supplier<BiomeBase>>> var2, Optional<Pair<IRegistry<BiomeBase>, MultiNoiseBiomeGenerator.bb>> var3) {
	      this(var0, var2, g, g, g, g, var3);
	   }
	   private MultiNoiseBiomeGenerator(long var0, List<Pair<BiomeBase.c, Supplier<BiomeBase>>> var2, MultiNoiseBiomeGenerator.aa var3, MultiNoiseBiomeGenerator.aa var4, MultiNoiseBiomeGenerator.aa var5, MultiNoiseBiomeGenerator.aa var6, Optional<Pair<IRegistry<BiomeBase>, MultiNoiseBiomeGenerator.bb>> var7) {
	      super(var2.stream().map(Pair::getSecond));
	      this.r = var0;
	      this.s = var7;
	   //   this.h = var3;
	   //   this.i = var4;
	   //   this.j = var5;
	   //   this.k = var6;
	      try {
	    	  
	    	  
	    	  
	    	  this.l = NoiseGeneratorNormal.a(new SeededRandom(var0), var3.a(), new DoubleArrayList(var3.c_list));
	    	  this.m = NoiseGeneratorNormal.a(new SeededRandom(var0+1), var4.a(), new DoubleArrayList(var4.c_list));
	    	  this.n = NoiseGeneratorNormal.a(new SeededRandom(var0+2), var5.a(), new DoubleArrayList(var5.c_list));
	    	  this.o = NoiseGeneratorNormal.a(new SeededRandom(var0+3), var6.a(), new DoubleArrayList(var6.c_list));
	    	  
	    	 /*
	    	  Method target_me = NoiseGeneratorNormal.class.getMethods()[1];
	    	  this.l = (NoiseGeneratorNormal) target_me.invoke(NoiseGeneratorNormal.class, new SeededRandom(var0),var3.a(),var3.b());
	    	  this.m = (NoiseGeneratorNormal) target_me.invoke(NoiseGeneratorNormal.class, new SeededRandom(var0+1L),var4.a(),var4.b());
	    	  this.n = (NoiseGeneratorNormal) target_me.invoke(NoiseGeneratorNormal.class, new SeededRandom(var0+2L),var5.a(),var5.b());
	    	  this.o = (NoiseGeneratorNormal) target_me.invoke(NoiseGeneratorNormal.class, new SeededRandom(var0+3L),var6.a(),var6.b());
		      
		      */
	      
	      }catch(Exception ex) {
	    	  ex.printStackTrace();
	    	  this.l = null;
	    	  this.m = null;
	    	  this.n = null;
	    	  this.o = null;
	      }
	      
	      //this.l = null;//NoiseGeneratorNormal.a(new SeededRandom(var0), var3.a(), var3.b());
	      //this.m = null;//NoiseGeneratorNormal.a(new SeededRandom(var0 + 1L), var4.a(), var4.b());
	      //this.n = null;//NoiseGeneratorNormal.a(new SeededRandom(var0 + 2L), var5.a(), var5.b());
	      //this.o = null;//NoiseGeneratorNormal.a(new SeededRandom(var0 + 3L), var6.a(), var6.b());
	      
	      this.p = var2;
	      this.q = false;
	   }

	   @Override
	protected Codec<? extends WorldChunkManager> a() {
	      return f;
	   }

	   private Optional<MultiNoiseBiomeGenerator.cc> d() {
	      return this.s.map((var0) -> {
	         return new MultiNoiseBiomeGenerator.cc(var0.getSecond(), var0.getFirst(), this.r);
	      });
	   }

	   @Override
	public BiomeBase getBiome(int var0, int var1, int var2) {
	      int var3 = this.q ? var1 : 0;
	      BiomeBase.c var4 = new BiomeBase.c((float)this.l.a(var0, var3, var2), (float)this.m.a(var0, var3, var2), (float)this.n.a(var0, var3, var2), (float)this.o.a(var0, var3, var2), 0.0F);
	   
	      BiomeBase returning = this.p.stream().min(Comparator.comparing((var1x) -> {
	         return var1x.getFirst().a(var4);
	      })).map(Pair::getSecond).map(Supplier::get).orElse(BiomeRegistry.b); 
	  
	     
	      return returning;
	   }

	   public boolean b(long var0) {
	      return this.r == var0 && this.s.isPresent() && Objects.equals(((Pair)this.s.get()).getSecond(), MultiNoiseBiomeGenerator.bb.NETHER);
	   }

	   // $FF: synthetic method
	   MultiNoiseBiomeGenerator(long var0, List var2, Optional var3, Object var4) {
	      this(var0, var2, var3); 
	     
	   }

	   static {
	      f = Codec.mapEither(MultiNoiseBiomeGenerator.cc.a, e).xmap((var0) -> {
	         return (MultiNoiseBiomeGenerator)var0.map(MultiNoiseBiomeGenerator.cc::d, Function.identity());
	      }, (var0) -> {
	         return (Either)var0.d().map(Either::left).orElseGet(() -> {
	            return Either.right(var0);
	         });
	      }).codec();
	   }

	   public static class CREATOR {
		   
		   private long seed;
		   private List<Pair<BiomeBase.c, Supplier<BiomeBase>>> biomelist = new ArrayList<>();
		   private IRegistry<BiomeBase> biomeregist;
		   
		   public CREATOR(long seed) {
			   this.seed = seed;
			   
			   biomeregist = GlobalValues.BiomeBase_Registry; 
		   }
		   // ("temperature").("humidity").Of("altitude").f("weirdness")..("offset").fative)var0, c::new));
		   public CREATOR addBiome(ResourceKey<BiomeBase> biome, float temperature_noise, float humidity, float altitude, float weirdness, float offset) {
			   biomelist.add(Pair.of(new BiomeBase.c(temperature_noise, humidity, altitude, weirdness, offset), () -> 
			   {return biomeregist.d(biome);}));
			   return this;
		   }
		   public CREATOR addBiome(RegisteredCustomBiome biome, float temperature_noise, float humidity, float altitude, float weirdness, float offset) {
			   return this.addBiome(biome.getBiome(), temperature_noise, humidity, altitude, weirdness, offset);
		   }
		   public MultiNoiseBiomeGenerator create() {
			   if(biomelist.size() > 0) {
				   return new MultiNoiseBiomeGenerator(seed, biomelist);
			   }
			   return null;
		   }
		   
	   }
	   
	   private static class bb {
	      
		   private static Function3<MultiNoiseBiomeGenerator.bb, IRegistry<BiomeBase>, Long, MultiNoiseBiomeGenerator> function(){
			   return (var0,var1,seed) -> {
				   
				   List<Pair<BiomeBase.c,Supplier<BiomeBase>>> list = new ArrayList<>();
				   
				   Supplier<BiomeBase> sup1 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.i);};
				   Supplier<BiomeBase> sup2 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.ax);};
				   Supplier<BiomeBase> sup3 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.ay);};
				   Supplier<BiomeBase> sup4 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.az);};
				   Supplier<BiomeBase> sup5 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.aA);};
				   
				   list.add(Pair.of(new BiomeBase.c(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), sup1));
				   list.add(Pair.of(new BiomeBase.c(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), sup2));
				   list.add(Pair.of(new BiomeBase.c(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), sup3));
				   list.add(Pair.of(new BiomeBase.c(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), sup4));
				   list.add(Pair.of(new BiomeBase.c(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), sup5));
				   
				   MultiNoiseBiomeGenerator mnbg = new MultiNoiseBiomeGenerator(seed, list, Optional.of(Pair.of(var1, var0)));
				   return mnbg;
			   };
		   }
		   
		   public static final MultiNoiseBiomeGenerator.bb NETHER = new bb(new MinecraftKey("nether"), function());
				  
		   /*
		   static {
			   
			   List<Pair<BiomeBase.c,Supplier<BiomeBase>>> list = new ArrayList<>();
			   
			   Supplier<BiomeBase> sup1 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.a);};
			   Supplier<BiomeBase> sup2 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.a);};
			   Supplier<BiomeBase> sup3 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.a);};
			   Supplier<BiomeBase> sup4 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.a);};
			   Supplier<BiomeBase> sup5 = ()->{return GlobalValues.BiomeBase_Registry.a(Biomes.a);};
			   
			   list.add(Pair.of(new BiomeBase.c(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), sup1));
			   list.add(Pair.of(new BiomeBase.c(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), sup2));
			   list.add(Pair.of(new BiomeBase.c(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), sup3));
			   list.add(Pair.of(new BiomeBase.c(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), sup4));
			   list.add(Pair.of(new BiomeBase.c(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), sup5));
			   
			   // or(long var0, List<Pair<BiomeBase.c, Supplier<BiomeBase>>> var2) {
			   MultiNoiseBiomeGenerator mnbg = new MultiNoiseBiomeGenerator(123456L, list);
					   
			   
		   }
		   
	      
	      public static final MultiNoiseBiomeGenerator.bb NETHER = new MultiNoiseBiomeGenerator.bb(new MinecraftKey("netherf"), (var0, var1, var2) -> {
	         return new MultiNoiseBiomeGenerator(var2, ImmutableList.of(
	        	Pair.of(new BiomeBase.c(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
	        	 return var1.d(Biomes.NETHER_WASTES);
	         }), Pair.of(new BiomeBase.c(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), () -> {
	            return var1.d(Biomes.SOUL_SAND_VALLEY);
	         }), Pair.of(new BiomeBase.c(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
	            return var1.d(Biomes.CRIMSON_FOREST);
	         }), Pair.of(new BiomeBase.c(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), () -> {
	           return var1.d(Biomes.WARPED_FOREST);
	         }), Pair.of(new BiomeBase.c(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), () -> {
	            return var1.d(Biomes.BASALT_DELTAS); 
	         })), Optional.of(Pair.of(var1, var0)));
	      });
	      */
		   
	      private final MinecraftKey c;
	      private final Function3<MultiNoiseBiomeGenerator.bb, IRegistry<BiomeBase>, Long, MultiNoiseBiomeGenerator> d;
	      
	      public bb(MinecraftKey var0, Function3<MultiNoiseBiomeGenerator.bb, IRegistry<BiomeBase>, Long, MultiNoiseBiomeGenerator> var1) {
	         this.c = var0;
	         this.d = var1;
	         
	         
	      }

	      public MultiNoiseBiomeGenerator a(IRegistry<BiomeBase> var0, long var1) {
	         return this.d.apply(this, var0, var1);
	      }
	   }

	   private static final class cc{
		   public static MapCodec<cc> a;
		    private final MultiNoiseBiomeGenerator.bb b;
		    private final IRegistry<BiomeBase> ca;
		    private final long d;
		    
		    private cc(final MultiNoiseBiomeGenerator.bb var0, final IRegistry<BiomeBase> var1, final long var2) {
		        this.b = var0;
		        this.ca = var1;
		        this.d = var2;
		    }
		    
		    public MultiNoiseBiomeGenerator.bb a() {
		        return this.b;
		    }
		    
		    public IRegistry<BiomeBase> b() {
		        return this.ca;
		    }
		    
		    public long c() {
		        return this.d;
		    }
		    
		    public MultiNoiseBiomeGenerator d() {
		        return this.b.a(this.ca, this.d);
		    }
		    
		    static {
		    	Class[] clazz = WorldChunkManagerMultiNoise.class.getDeclaredClasses();
		    	for(Class cla : clazz) {
		    		if(cla.getName().equals("net.minecraft.server.v1_16_R3.WorldChunkManagerMultiNoise$c")) {
		    			try {
		    			Field field = cla.getDeclaredField("a");
		    			field.setAccessible(true);
		    			FieldUtils.removeFinalModifier(field);
		    			
		    			a = (MapCodec<cc>) field.get("a");
		    			
		    			 //a = (MapCodec<cc>) testmethod.getValue(cla, "a");
		    			}catch(Exception ex) {
		    				ex.printStackTrace();
		    			}
		    		}
		    		
		    	}
		    }
	   }

	   private static class aa {
	      private final int b;
	    //  private final DoubleList c;
	      private final List<Double> c_list; // DoubleList
	      
	   //   public static final Codec<MultiNoiseBiomeGenerator.aa> a = RecordCodecBuilder.create((var0) -> {
	  //       return var0.group(Codec.INT.fieldOf("firstOctave").forGetter(MultiNoiseBiomeGenerator.aa::a), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(MultiNoiseBiomeGenerator.aa::b)).apply(var0, MultiNoiseBiomeGenerator.aa::new);
	  //    });

	      public aa(int var0, List<Double> var1) {
	         this.b = var0;
	        // this.c = new DoubleArrayList(var1);
	         this.c_list = var1;
	         
	      }

	      public int a() {
	         return this.b;
	      }

	      public Object b() {
	    	  
	    	  try {
	    	  
	    		  // it/unimi/dsi/fastutil/doubles/DoubleList	    	  
	    		  return new org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.doubles.DoubleArrayList(c_list);
	         
	    	  }catch(NoClassDefFoundError error) {
	    		  it.unimi.dsi.fastutil.doubles.DoubleList fastutil = new it.unimi.dsi.fastutil.doubles.DoubleArrayList(c_list);
	    		  return fastutil;  
	    	  }
	      }
	   }


	@Override
	public WorldChunkManager a(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}
	}


