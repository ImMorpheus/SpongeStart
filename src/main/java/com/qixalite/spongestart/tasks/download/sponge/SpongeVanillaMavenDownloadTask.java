package com.qixalite.spongestart.tasks.download.sponge;

import com.qixalite.spongestart.Constants;
import com.qixalite.spongestart.tasks.download.AbstractDownloadTask;
import com.qixalite.spongestart.tasks.download.DownloadUtils;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class SpongeVanillaMavenDownloadTask extends AbstractDownloadTask {

    private final Property<Path> run;
    private final Property<String> version;

    @Inject
    public SpongeVanillaMavenDownloadTask(Property<Path> cache, Property<String> minecraft, Property<Path> runDir, Property<String> version) {
        super(cache, minecraft);
        setDescription("Download SpongeVanilla jar");
        this.run = runDir;
        this.version = version;
    }

    @Override
    public String getUrl() {
        String sv = this.version.getOrNull();

        if (sv != null) {
            return Constants.SPONGE_REPO + "spongevanilla/" + sv + "/spongevanilla-" + sv + ".jar";
        }

        Path xml = this.cache.get().resolve("spongevanilla.xml");

        DownloadUtils.save(Constants.SPONGE_REPO + "spongevanilla/maven-metadata.xml", xml);

        String rb = SpongeUtils.getRB(xml, this.minecraft.get());

        return Constants.SPONGE_REPO + "spongevanilla/" + rb + "/spongevanilla-" + rb + ".jar";
    }

    @Override
    public Path getDestination() {
        return this.run.get().resolve("server.jar");
    }
}
