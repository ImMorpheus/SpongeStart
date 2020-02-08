package com.qixalite.spongestart.tasks.setup;

import com.qixalite.spongestart.tasks.SpongeStartTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class SetupServerTask extends SpongeStartTask {

    protected final Property<String> minecraft;
    protected final Property<Path> run;

    protected SetupServerTask(Property<String> minecraft, Property<Path> runDir) {
        this.minecraft = minecraft;
        this.run = runDir;
    }

    @TaskAction
    public void doStuff() {
        acceptEula();
        setup();
    }

    private void acceptEula() {
        try {
            Files.write(this.run.get().resolve("eula.txt"), "eula=true".getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new GradleException("Failed to accept eula", e);
        }
    }

    public void setup() {
        String config = "max-tick-time=-1" + System.lineSeparator() +
                        "snooper-enabled=false" + System.lineSeparator() +
                        "allow-flight=true";

        try {
            Files.write(this.run.get().resolve("server.properties"), config.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new GradleException("Failed to tweak server.properties", e);
        }
    }

}
