package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import de.freesoccerhdx.advancedworldcreator.customstructures.CustomStructure;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration2;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeBeehive;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.WaterDepthThresholdConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorConfigured;


public class CustomTree implements CustomStructure{

	public CustomTree() {
		
	}
	
	@Override
	public WorldGenFeatureConfigured<?, ?> create() {
		// TODO Auto-generated method stub
		return null;
	}

	
	protected static final class b {
		
		
		
		public static final WorldGenFeatureTreeBeehive a = new WorldGenFeatureTreeBeehive(0.002F);

		public static final WorldGenFeatureTreeBeehive b = new WorldGenFeatureTreeBeehive(0.02F);

		public static final WorldGenFeatureTreeBeehive c = new WorldGenFeatureTreeBeehive(0.05F);

		public static final WorldGenDecoratorConfigured<HeightmapConfiguration> d = WorldGenDecorator.n
				.a(new HeightmapConfiguration(HeightMap.Type.e));

		public static final WorldGenDecoratorConfigured<HeightmapConfiguration> e = WorldGenDecorator.n
				.a(new HeightmapConfiguration(HeightMap.Type.c));

		public static final WorldGenDecoratorConfigured<HeightmapConfiguration> f = WorldGenDecorator.n
				.a(new HeightmapConfiguration(HeightMap.Type.a));

		public static final WorldGenDecoratorConfigured<HeightmapConfiguration> g = WorldGenDecorator.n
				.a(new HeightmapConfiguration(HeightMap.Type.d));

		public static final WorldGenDecoratorConfigured<HeightmapConfiguration> h = WorldGenDecorator.o
				.a(new HeightmapConfiguration(HeightMap.Type.e));

		public static final WorldGenFeatureChanceDecoratorRangeConfiguration i = new WorldGenFeatureChanceDecoratorRangeConfiguration(
				UniformHeight.a(VerticalAnchor.a(), VerticalAnchor.b()));

		public static final WorldGenFeatureChanceDecoratorRangeConfiguration j = new WorldGenFeatureChanceDecoratorRangeConfiguration(
				UniformHeight.a(VerticalAnchor.b(10), VerticalAnchor.c(10)));

		public static final WorldGenFeatureChanceDecoratorRangeConfiguration k = new WorldGenFeatureChanceDecoratorRangeConfiguration(
				UniformHeight.a(VerticalAnchor.b(8), VerticalAnchor.c(8)));

		public static final WorldGenFeatureChanceDecoratorRangeConfiguration l = new WorldGenFeatureChanceDecoratorRangeConfiguration(
				UniformHeight.a(VerticalAnchor.b(4), VerticalAnchor.c(4)));

		public static final WorldGenFeatureChanceDecoratorRangeConfiguration m = new WorldGenFeatureChanceDecoratorRangeConfiguration(
				UniformHeight.a(VerticalAnchor.a(), VerticalAnchor.a(60)));

		public static final WorldGenDecoratorConfigured<?> n = WorldGenDecorator.r.a(l).a().c(5);

		public static final WorldGenDecoratorConfigured<?> o = WorldGenDecorator.s
				.a(WorldGenFeatureEmptyConfiguration2.c);

		public static final WorldGenDecoratorConfigured<?> p = g
				.a(WorldGenDecorator.p.a(new WaterDepthThresholdConfiguration(0)));

		public static final WorldGenDecoratorConfigured<?> q = p.a();

		public static final WorldGenDecoratorConfigured<?> r = d.a();

		public static final WorldGenDecoratorConfigured<?> s = h.a();

		public static final WorldGenDecoratorConfigured<?> t = e.a();

		public static final WorldGenDecoratorConfigured<?> u = p
				.a(WorldGenDecorator.f.a(WorldGenFeatureDecoratorConfiguration.b));
	}
	
	
	
}


