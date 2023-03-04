package de.freesoccerhdx.advancedworldcreatorapi;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CraftHolder<T> implements Holder<T> {

    private T value;
    private ResourceKey<T> key;

    public CraftHolder(T obj, ResourceKey<T> key) {
        this.value = obj;
        this.key = key;
    }

    public boolean isBound() {
        return true;
    }

    public boolean is(ResourceLocation var0) {
        return key.location().equals(var0);
    }

    public boolean is(ResourceKey<T> var0) {
        return key.equals(var0);
    }

    public boolean is(TagKey<T> var0) {
        return false;
    }

    public boolean is(Predicate<ResourceKey<T>> var0) {
        return false;
    }

    public Either<ResourceKey<T>, T> unwrap() {
        return Either.right(this.value);
    }

    public Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of(key);
    }

    public Kind kind() {
        return Kind.REFERENCE;
    }

    public String toString() {
        return "CraftHolder{" + this.value + "}";
    }

    public boolean canSerializeIn(HolderOwner<T> var0) {
        return true;
    }

    public Stream<TagKey<T>> tags() {
        return Stream.of();
    }

    public T value() {
        return this.value;
    }

}
