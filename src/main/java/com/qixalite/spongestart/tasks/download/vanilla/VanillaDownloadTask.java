package com.qixalite.spongestart.tasks.download.vanilla;

import com.qixalite.spongestart.Constants;
import com.qixalite.spongestart.tasks.download.AbstractDownloadTask;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.nio.file.Path;

public class VanillaDownloadTask extends AbstractDownloadTask {

    private final Property<Path> run;

    @Inject
    public VanillaDownloadTask(Property<Path> cache, Property<String> minecraft, Property<Path> runDir) {
        super(cache, minecraft);
        setDescription("Download vanilla minecraft-server jar");
        this.run = runDir;
    }

    @Override
    public String getUrl() {
        String mc = this.minecraft.get();
        return Constants.MOJANG_SERVER + mc + "/minecraft_server." + mc + ".jar";
    }

    @Override
    public Path getDestination() {
        return this.run.get().resolve("minecraft_server." + this.minecraft.get() + ".jar");
    }
}
