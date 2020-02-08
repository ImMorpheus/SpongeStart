package com.qixalite.spongestart.tasks.download.forge;

import com.qixalite.spongestart.Constants;
import com.qixalite.spongestart.tasks.download.AbstractDownloadTask;
import com.qixalite.spongestart.tasks.download.DownloadUtils;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class ForgeDownloadTask extends AbstractDownloadTask {

    private final Property<Path> run;
    private final Property<String> version;

    @Inject
    public ForgeDownloadTask(Property<Path> cache, Property<String> minecraft, Property<Path> runDir, Property<String> version) {
        super(cache, minecraft);
        setDescription("Download Forge jar");
        this.run = runDir;
        this.version = version;
    }

    @Override
    public String getUrl() {
        String v = this.version.getOrNull();
        String mc = this.minecraft.get();

        if (v != null) {
            String key = mc + '-' + v;
            return Constants.FORGE_REPO + key + "/forge-" + key + "-installer.jar";
        }

        Path json = this.cache.get().resolve("forge.json");

        DownloadUtils.save(Constants.FORGE_VERSION_DATA, json);

        String rb = ForgeUtils.getRB(json, mc);
        this.version.set(rb);

        String key = mc + '-' + rb;
        return Constants.FORGE_REPO + key + "/forge-" + key + "-installer.jar";
    }

    @Override
    public Path getDestination() {
        return this.run.get().resolve("setup.jar");
    }
}
