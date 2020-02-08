package com.qixalite.spongestart.tasks.config;

import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class GenerateVanillaRunTask extends GenerateSpongeRunTask {

    @Inject
    public GenerateVanillaRunTask(String name, String main, String pargs, Property<Path> runDir) {
        super(name, main, pargs, runDir);
        setDescription("Generate Vanilla run configuration to start a SpongeVanilla server");
    }
}
