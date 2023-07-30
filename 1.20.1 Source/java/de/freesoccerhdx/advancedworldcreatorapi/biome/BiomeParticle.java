package de.freesoccerhdx.advancedworldcreatorapi.biome;

import org.bukkit.Particle;

/**
 * Represents an Object to store the Particle-Type
 * and how many Particles will appear in a CustomBiome
 *
 * */
public class BiomeParticle {

    private Particle particle;
    private float quantity;

    /**
     * Creates the BiomeParticle-Object with provided Parameters
     *
     * @param particle - The {@link Particle}-Type to use
     * @param quantity - The Spawning-QUantity
     * */
    public BiomeParticle(Particle particle, float quantity){
        this.particle = particle;
        this.quantity = quantity;
    }

    /**
     * Gets the Quantity of the {@link Particle} in a CustomBiome
     * Higher Value means more Particles
     *
     * @return The Quantity
     * */
    public float getQuantity() {
        return quantity;
    }

    /**
     * Gets the provided {@link Particle}-Type
     *
     * @return The {@link Particle}-Type
     * */
    public Particle getParticle() {
        return particle;
    }

}
