package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.SpongeStartExtension;
import com.qixalite.spongestart.tasks.SpongeStartTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class DownloadTask extends SpongeStartTask {

    private File destination;

    @TaskAction
    public void doStuff() {
        String link = getUrl();

        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

        File cached = new File(ext.getCacheFolder(), "downloads" + File.separatorChar + link.substring(link.lastIndexOf('/') + 1));

        DownloadUtils.downloadToFile(link, cached);

        this.destination.getParentFile().mkdirs();

        try {
            Files.copy(cached.toPath().toAbsolutePath(), this.destination.toPath().toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new GradleException("Couldn't copy the file", e);
        }
    }

    public abstract String getUrl();

    public void setDestination(File destination) {
        this.destination = destination;
    }
}
