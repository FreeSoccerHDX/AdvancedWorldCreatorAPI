package de.freesoccerhdx.advancedworldcreator.biomegenerators;


import java.util.Arrays;
import java.util.function.Supplier;

import org.bukkit.block.Biome;

import com.mojang.serialization.Codec;

import de.freesoccerhdx.advancedworldcreator.main.AdvancedWorldCreatorAPI;
import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;


public class SingleBiomeGenerator extends WorldChunkManager{

	public static class CREATOR {
		private IRegistry<BiomeBase> biomeregist;
		
		
		private Supplier<BiomeBase> targetbiome = null;
		
		
		public CREATOR(Biome biome) {
			this(AdvancedWorldCreatorAPI.getNMSBiome(biome));
		}
		public CREATOR(RegisteredCustomBiome biome) {
			this(biome.getBiome());
		}
		public CREATOR(ResourceKey<BiomeBase> biome) {
			biomeregist = GlobalValues.BiomeBase_Registry;
			targetbiome = () -> biomeregist.a(biome);
		}
		
		public SingleBiomeGenerator create() {
			return new SingleBiomeGenerator(targetbiome);
		}
		
	}
	
	private Supplier<BiomeBase> targetbiome = null;
	
	private SingleBiomeGenerator(Supplier<BiomeBase> targetbiome) {
		super(Arrays.asList(targetbiome).stream());
		this.targetbiome = targetbiome;
	}


	@Override
	public BiomeBase getBiome(int x, int y, int z) {
		return targetbiome.get();
	}




	@Override
	public WorldChunkManager a(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Codec<? extends WorldChunkManager> a() {
		// TODO Auto-generated method stub
		return null;
	}

}
