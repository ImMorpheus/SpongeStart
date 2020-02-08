package com.qixalite.spongestart.tasks.setup;

import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class SetupForgeServerTask extends SetupServerTask {

    private final Property<String> version;

    @Inject
    public SetupForgeServerTask(Property<String> minecraft, Property<Path> runDir, Property<String> version) {
        super(minecraft, runDir);
        setDescription("Setup a SpongeForge server");
        this.version = version;
    }

    @Override
    public void setup() {
        super.setup();

        Path destination = this.run.get();
        String config = "general {" + System.lineSeparator() +
                        "    B:disableVersionCheck=true" + System.lineSeparator() +
                        "}" + System.lineSeparator() +
                        "version_checking {" + System.lineSeparator() +
                        "    B:Global=false" + System.lineSeparator() +
                        "}";

        Path conf = destination.resolve("config").resolve("forge.cfg");
        try {
            Files.createDirectories(conf.getParent());
            Files.write(conf, config.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new GradleException("Failed to tweak forge config", e);
        }
        try {
            Process pr = new ProcessBuilder()
                    .command("java", "-jar", "setup.jar", "--installServer")
                    .directory(destination.toFile())
                    .redirectErrorStream(true)
                    .start();
            pr.waitFor();

            Path forge = destination.resolve("forge-" + this.minecraft.get() + '-' + this.version.get() + "-universal.jar");
            Path output = destination.resolve("server.jar");
            Files.move(forge, output, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new GradleException("Failed to setup forge", e);
        }
    }
}
