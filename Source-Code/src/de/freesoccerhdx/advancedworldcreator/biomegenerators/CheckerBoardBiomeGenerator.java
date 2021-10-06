package de.freesoccerhdx.advancedworldcreator.biomegenerators;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.block.Biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.freesoccerhdx.advancedworldcreator.main.AdvancedWorldCreatorAPI;
import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;

//import net.minecraft.server.v1_17_R1.BiomeBase;
//import net.minecraft.server.v1_17_R1.DedicatedServer;
//import net.minecraft.server.v1_17_R1.IRegistry;
//import net.minecraft.server.v1_17_R1.ResourceKey;
//import net.minecraft.server.v1_17_R1.WorldChunkManager;

public class CheckerBoardBiomeGenerator extends WorldChunkManager {

	
	 public static class CREATOR {
		   
		 int gridsize = 1;
		 private List<Supplier<BiomeBase>> biomelist = new ArrayList<>();
		 private IRegistry<BiomeBase> biomeregist;
		   
		 public CREATOR(int gridsize) {
			 this.gridsize = gridsize;
		
			 biomeregist = GlobalValues.BiomeBase_Registry; 
		 }

		 public CREATOR addBiome(Biome biome) {
			 return addBiome(AdvancedWorldCreatorAPI.getNMSBiome(biome));
		 }
		 
		 public CREATOR addBiome(ResourceKey<BiomeBase> biome) {
			 biomelist.add(()->biomeregist.a(biome));
			 
			 
			 return this;
		 }
		 public CREATOR addBiome(RegisteredCustomBiome biome) {
			 return this.addBiome(biome.getBiome());
		 }
		 public CheckerBoardBiomeGenerator create() {
			 if(biomelist.size() > 0) {
				 return new CheckerBoardBiomeGenerator(biomelist, gridsize);
			 }
			 return null;
		 }
		   
	 }
	
	
	
    private static final Codec<CheckerBoardBiomeGenerator> e = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeBase.e.fieldOf("biomes").forGetter((worldchunkmanagercheckerboard) -> {
            return worldchunkmanagercheckerboard.f;
        }), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter((worldchunkmanagercheckerboard) -> {
            return worldchunkmanagercheckerboard.h;
        })).apply(instance, CheckerBoardBiomeGenerator::new);
    });
    private final List<Supplier<BiomeBase>> f;
    private final int g;
    private final int h;

    private CheckerBoardBiomeGenerator(List<Supplier<BiomeBase>> list, int i) {
        super(list.stream());
        this.f = list;
        this.g = i + 2;
        this.h = i;
    }

    @Override
    protected Codec<? extends WorldChunkManager> a(){
    	
        return CheckerBoardBiomeGenerator.e;
    }

    @Override
    public BiomeBase getBiome(int i, int j, int k) {
        return (BiomeBase) ((Supplier) this.f.get(Math.floorMod((i >> this.g) + (k >> this.g), this.f.size()))).get();
    }

	@Override
	public WorldChunkManager a(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
