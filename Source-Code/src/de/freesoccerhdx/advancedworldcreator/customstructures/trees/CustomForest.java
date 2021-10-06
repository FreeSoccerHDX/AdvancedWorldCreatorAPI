package de.freesoccerhdx.advancedworldcreator.customstructures.trees;

import java.util.ArrayList;
import java.util.List;

import de.freesoccerhdx.advancedworldcreator.customstructures.CustomStructure;
import de.freesoccerhdx.advancedworldcreator.main.GlobalValues;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureRandomChoiceConfigurationWeight;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorFrequencyExtraChanceConfiguration;

public class CustomForest implements CustomStructure{

	
	private List<WorldGenFeatureRandomChoiceConfigurationWeight> randomtrees = new ArrayList<>();
	private WorldGenFeatureConfigured<?,?> default_feature = null;
	private String name;
	private int default_count = 1;
	private double extra_chance = 0.0;
	private int extra_count = 0;
	
	private boolean randomtype = false; 
	
	
	public CustomForest(String name,CustomTree default_feature) {
		this.name = name;
		this.default_feature = default_feature.create();
		
	}
	public CustomForest(String name,WorldGenFeatureConfigured<?,?> default_feature) {
		this.name = name;
		this.default_feature = default_feature;

	}
	
	public String getName() {
		return name;
	}
	
	
	public void setDefaultCount(int i) {
		default_count = i;
	}
	public void setExtraChance(Double d) {
		extra_chance = d;
	}
	public void setExtraCount(int i){
		extra_count = i;
	}
	
	public void addTree(CustomTree feature, double chance) {
		float f = (float) chance;
		randomtrees.add(feature.create().a(f));
	}
	
	public void addTree(WorldGenFeatureConfigured<?, ?> feature, double chance) {
		float f = (float) chance;
		randomtrees.add(feature.a(f));
	}
	
	
	public void setRandomForestType(int default_count, double extra_chance, int extra_count) {
		this.default_count = default_count;
		this.extra_chance = extra_chance;
		this.extra_count = extra_count;
		randomtype = true;
	}
	public void setFullForestType() {
		randomtype = false;
	}
	
	
	@Override
	public WorldGenFeatureConfigured<?, ?> create() {
		
		
		
		// e.g.: savanna/plains biome
		if(randomtype) {
			WorldGenFeatureConfigured<?, ?> randomforest = a(name, 
				GlobalValues.RANDOM_SELECTOR_CONFIGURATION_GENERATOR.b(
					new WorldGenFeatureRandomChoiceConfiguration(randomtrees, default_feature)
				).a(CustomTree.b.q)
				 .a(WorldGenDecorator.l.a(new WorldGenDecoratorFrequencyExtraChanceConfiguration(default_count, (float) extra_chance, extra_count)))
				);
			
			return randomforest;
			
		}else { // e.g.: dark oak biome
			
			WorldGenFeatureConfigured<?, ?> fullforest = a(name, 
					GlobalValues.RANDOM_SELECTOR_CONFIGURATION_GENERATOR.b(
					new WorldGenFeatureRandomChoiceConfiguration(randomtrees,default_feature))
			      .a(CustomTree.b.u));
				
			return fullforest;
		}
		
		
		/*
		WorldGenFeatureConfigured<?, ?> feature = a(name, 
			GlobalValues.RANDOM_SELECTOR_CONFIGURATION_GENERATOR.b(
				new WorldGenFeatureRandomChoiceConfiguration((List<WorldGenFeatureRandomChoiceConfigurationWeight>) randomtrees, default_feature)
			).a(CustomTree.b.l)
			 .a(WorldGenDecorator.l.a(new WorldGenDecoratorFrequencyExtraChanceConfiguration(default_count, (float) extra_chance, extra_count)))
			);
		*/
		
	}
	
	
	
	
}
