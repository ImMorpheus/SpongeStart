package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.tasks.SpongeStartTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class AbstractDownloadTask extends SpongeStartTask implements DownloadTask {

    protected final Property<Path> cache;
    protected final Property<String> minecraft;

    protected AbstractDownloadTask(Property<Path> cache, Property<String> minecraft) {
        this.cache = cache;
        this.minecraft = minecraft;
    }

    @TaskAction
    public void doStuff() {
        String link = getUrl();

        Path cached = this.cache.get().resolve(link.substring(link.lastIndexOf('/') + 1));

        DownloadUtils.save(link, cached);

        Path destination = getDestination();
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(cached, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new GradleException("Couldn't copy the file", e);
        }
    }

}
