package com.qixalite.spongestart.tasks.download.vanilla;

import com.qixalite.spongestart.Constants;
import com.qixalite.spongestart.tasks.download.AbstractDownloadTask;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class LaunchWrapperDownloadTask extends AbstractDownloadTask {

    private final Property<Path> run;

    @Inject
    public LaunchWrapperDownloadTask(Property<Path> cache, Property<String> minecraft, Property<Path> runDir) {
        super(cache, minecraft);
        setDescription("Download launchwrapper");
        this.run = runDir;
    }

    @Override
    public String getUrl() {
        return Constants.LAUNCHWRAPPER;
    }

    @Override
    public Path getDestination() {
        return this.run.get().resolve("libraries").resolve("net").resolve("minecraft").resolve("launchwrapper").resolve("1.12").resolve("launchwrapper-1.12.jar");
    }
}

