package com.qixalite.spongestart;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import java.nio.file.Path;

public class SpongeStartExtension {

    private final Property<String> minecraft;
    private final Property<String> forge;
    private final Property<String> spongeForge;
    private final Property<String> spongeVanilla;
    private final Property<Path> forgeRunDir;
    private final Property<Path> vanillaRunDir;
    private final Property<Path> cacheDir;

    public SpongeStartExtension(Project project) {
        this.minecraft = project.getObjects().property(String.class);
        this.forge = project.getObjects().property(String.class);
        this.spongeForge = project.getObjects().property(String.class);
        this.spongeVanilla = project.getObjects().property(String.class);
        this.forgeRunDir = project.getObjects().property(Path.class);
        this.forgeRunDir.set(project.getProjectDir().toPath().resolve("run").resolve("forge"));
        this.vanillaRunDir = project.getObjects().property(Path.class);
        this.vanillaRunDir.set(project.getProjectDir().toPath().resolve("run").resolve("vanilla"));
        this.cacheDir = project.getObjects().property(Path.class);
        this.cacheDir.set(project.getGradle().getGradleUserHomeDir().toPath().resolve("cache").resolve(Constants.NAME));
    }

    public Property<String> getMinecraft() {
        return this.minecraft;
    }

    public Property<String> getForge() {
        return this.forge;
    }

    public Property<String> getSpongeForge() {
        return this.spongeForge;
    }

    public Property<String> getSpongeVanilla() {
        return this.spongeVanilla;
    }

    public Property<Path> getForgeRunDir() {
        return this.forgeRunDir;
    }

    public Property<Path> getVanillaRunDir() {
        return this.vanillaRunDir;
    }

    public Property<Path> getCacheDir() {
        return this.cacheDir;
    }
}
