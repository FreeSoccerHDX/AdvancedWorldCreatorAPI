package de.freesoccerhdx.advancedworldcreator.main;

import java.util.Iterator;

import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectList;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectListIterator;

import net.minecraft.SystemUtils;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.structures.WorldGenFeatureDefinedStructureJigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.WorldGenFeaturePillagerOutpostPoolPiece;

public class DummyBeardifier{
	public static final DummyBeardifier a = new DummyBeardifier();

	public static final int b = 12;

	private static final int c = 24;

	private static final float[] d;

	private final ObjectList<StructurePiece> e;

	private final ObjectList<WorldGenFeatureDefinedStructureJigsawJunction> f;

	private final ObjectListIterator<StructurePiece> g;

	private final ObjectListIterator<WorldGenFeatureDefinedStructureJigsawJunction> h;

	static {
		d = SystemUtils.<float[]>a(new float[13824], var0 -> {
			for (int var1 = 0; var1 < 24; var1++) {
				for (int var2 = 0; var2 < 24; var2++) {
					for (int var3 = 0; var3 < 24; var3++)
						var0[var1 * 24 * 24 + var2 * 24 + var3] = (float) d(var2 - 12, var3 - 12, var1 - 12);
				}
			}
		});
	}

	public DummyBeardifier(StructureManager var0, IChunkAccess var1) {
		ChunkCoordIntPair var2 = var1.getPos();
		int var3 = var2.d();
		int var4 = var2.e();
		this.f = new ObjectArrayList<>(32);
		this.e = new ObjectArrayList<>(10);
		for (Iterator<StructureGenerator<?>> iterator = StructureGenerator.t.iterator(); iterator.hasNext();) {
			StructureGenerator<?> var6 = iterator.next();
			var0.a(SectionPosition.a(var1), var6).forEach(var300 -> {
				for (StructurePiece var5 : var300.d()) {
					if (!var5.a(var2, 12))
						continue;
					if (var5 instanceof WorldGenFeaturePillagerOutpostPoolPiece) {
						WorldGenFeaturePillagerOutpostPoolPiece var600 = (WorldGenFeaturePillagerOutpostPoolPiece) var5;
						WorldGenFeatureDefinedStructurePoolTemplate.Matching var7 = var600.b().e();
						if (var7 == WorldGenFeatureDefinedStructurePoolTemplate.Matching.b)
							this.e.add(var600);
						for (WorldGenFeatureDefinedStructureJigsawJunction var9 : var600.e()) {
							int var10 = var9.a();
							int var11 = var9.c();
							if (var10 <= var3 - 12 || var11 <= var4 - 12 || var10 >= var3 + 15 + 12
									|| var11 >= var4 + 15 + 12)
								continue;
							this.f.add(var9);
						}
						continue;
					}
					this.e.add(var5);
				}
			});
		}
		this.g = this.e.iterator();
		this.h = this.f.iterator();
	}

	public DummyBeardifier() {
		this.f = new ObjectArrayList<>();
		this.e = new ObjectArrayList<>();
		this.g = this.e.iterator();
		this.h = this.f.iterator();
	}

	protected double a(int var0, int var1, int var2) {
		double var3 = 0.0D;
		while (this.g.hasNext()) {
			StructurePiece var5 = this.g.next();
			StructureBoundingBox var6 = var5.f();
			int var7 = Math.max(0, Math.max(var6.g() - var0, var0 - var6.j()));
			int var8 = var1 - var6.h()
					+ ((var5 instanceof WorldGenFeaturePillagerOutpostPoolPiece)
							? ((WorldGenFeaturePillagerOutpostPoolPiece) var5).d()
							: 0);
			int var9 = Math.max(0, Math.max(var6.i() - var2, var2 - var6.l()));
			NoiseEffect var10 = var5.ad_();
			if (var10 == NoiseEffect.b) {
				var3 += b(var7, var8, var9);
				continue;
			}
			if (var10 == NoiseEffect.c)
				var3 += c(var7, var8, var9) * 0.8D;
		}
		this.g.back(this.e.size());
		while (this.h.hasNext()) {
			WorldGenFeatureDefinedStructureJigsawJunction var5 = this.h.next();
			int var6 = var0 - var5.a();
			int var7 = var1 - var5.b();
			int var8 = var2 - var5.c();
			var3 += c(var6, var7, var8) * 0.4D;
		}
		this.h.back(this.f.size());
		return var3;
	}

	private static double b(int var0, int var1, int var2) {
		double var3 = MathHelper.a(var0, var1 / 2.0D, var2);
		return MathHelper.a(var3, 0.0D, 6.0D, 1.0D, 0.0D);
	}

	private static double c(int var0, int var1, int var2) {
		int var3 = var0 + 12;
		int var4 = var1 + 12;
		int var5 = var2 + 12;
		if (var3 < 0 || var3 >= 24)
			return 0.0D;
		if (var4 < 0 || var4 >= 24)
			return 0.0D;
		if (var5 < 0 || var5 >= 24)
			return 0.0D;
		return d[var5 * 24 * 24 + var3 * 24 + var4];
	}

	private static double d(int var0, int var1, int var2) {
		double var3 = (var0 * var0 + var2 * var2);
		double var5 = var1 + 0.5D;
		double var7 = var5 * var5;
		double var9 = Math.pow(Math.E, -(var7 / 16.0D + var3 / 16.0D));
		double var11 = -var5 * MathHelper.h(var7 / 2.0D + var3 / 2.0D) / 2.0D;
		return var11 * var9;
	}
}