package com.qixalite.spongestart.tasks.download.sponge;

import com.qixalite.spongestart.Constants;
import com.qixalite.spongestart.tasks.download.AbstractDownloadTask;
import com.qixalite.spongestart.tasks.download.DownloadUtils;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class SpongeForgeMavenDownloadTask extends AbstractDownloadTask {

    private final Property<Path> run;
    private final Property<String> version;

    @Inject
    public SpongeForgeMavenDownloadTask(Property<Path> cache, Property<String> minecraft, Property<Path> runDir, Property<String> version) {
        super(cache, minecraft);
        setDescription("Download SpongeForge jar");
        this.run = runDir;
        this.version = version;
    }

    @Override
    public String getUrl() {
        String sf = this.version.getOrNull();

        if (sf != null) {
            return Constants.SPONGE_REPO + "spongeforge/" + sf + "/spongeforge-" + sf + ".jar";
        }

        Path xml = this.cache.get().resolve("spongeforge.xml");

        DownloadUtils.save(Constants.SPONGE_REPO + "spongeforge/maven-metadata.xml", xml);

        String rb = SpongeUtils.getRB(xml, this.minecraft.get());

        return Constants.SPONGE_REPO + "spongeforge/" + rb + "/spongeforge-" + rb + ".jar";
    }

    @Override
    public Path getDestination() {
        return this.run.get().resolve("mods").resolve("sponge.jar");
    }
}
