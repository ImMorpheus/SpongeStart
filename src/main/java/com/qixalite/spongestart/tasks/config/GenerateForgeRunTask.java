package com.qixalite.spongestart.tasks.config;

import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class GenerateForgeRunTask extends GenerateSpongeRunTask {

    @Inject
    public GenerateForgeRunTask(String name, String main, String pargs, Property<Path> runDir) {
        super(name, main, pargs, runDir);
        setDescription("Generate Forge run configuration to start a SpongeForge server");
    }
}
