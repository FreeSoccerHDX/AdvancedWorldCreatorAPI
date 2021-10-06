package de.freesoccerhdx.advancedworldcreator.biomegenerators;

import java.util.ArrayList;
import java.util.function.Supplier;

import org.bukkit.block.Biome;

import com.mojang.serialization.Codec;

import de.freesoccerhdx.advancedworldcreator.biomegenerators.CheckerBoardBiomeGenerator.CREATOR;
import de.freesoccerhdx.advancedworldcreator.main.AdvancedWorldCreatorAPI;
import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
/*
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.WorldChunkManager;
*/

public class SquaresBiomeGenerator extends WorldChunkManager{

	public static class CREATOR {
		
		private int csize = 1;
		private IRegistry<BiomeBase> biomeregist;
		private ArrayList<Supplier<BiomeBase>> biomelist = new ArrayList<>();
		
		
		public CREATOR(int chunksize) {
			csize = chunksize;
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
		
		public SquaresBiomeGenerator create() {
			if(biomelist.size() > 0) {
				return new SquaresBiomeGenerator(biomelist, csize);
			}
			return null;
		}
		
	}
	
	private int biomesize = 1;
	private ArrayList<Supplier<BiomeBase>> biomes = null;
	
	private SquaresBiomeGenerator(ArrayList<Supplier<BiomeBase>> list, int biomesize) {
		super(list.stream());
		this.biomesize = biomesize;
		this.biomes = list;
		// TODO Auto-generated constructor stub
	}


	@Override
	public BiomeBase getBiome(int x, int y, int z) {
		int dir = Math.max(Math.abs(x), Math.abs(z));
		
		return (BiomeBase) ((Supplier<?>) this.biomes.get(Math.floorMod((dir >> this.biomesize), this.biomes.size()))).get();
	}


	@Override
	protected Codec<? extends WorldChunkManager> a() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public WorldChunkManager a(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}

}
