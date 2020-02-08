package com.qixalite.spongestart.tasks;

import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CleanCacheTask extends SpongeStartTask {

    private final Property<Path> path;

    @Inject
    public CleanCacheTask(Property<Path> path) {
        setDescription("Clean SpongeStart cache folder");
        this.path = path;
    }

    @TaskAction
    public void doStuff() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path.get())) {
            for (Path p : stream) {
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new GradleException("Failed to clean cache", e);
        }
    }
}
