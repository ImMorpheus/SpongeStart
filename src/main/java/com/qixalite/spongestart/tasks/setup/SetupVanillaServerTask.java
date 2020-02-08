package com.qixalite.spongestart.tasks.setup;

import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class SetupVanillaServerTask extends SetupServerTask {

    @Inject
    public SetupVanillaServerTask(Property<String> minecraft, Property<Path> runDir) {
        super(minecraft, runDir);
        setDescription("Setup a SpongeVanilla server");
    }
}
