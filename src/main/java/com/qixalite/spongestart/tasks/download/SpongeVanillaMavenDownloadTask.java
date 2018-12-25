package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.SpongeStartExtension;

import java.io.File;

public class SpongeVanillaMavenDownloadTask extends SpongeDownloadTask {

    @Override
    public String getUrl() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);
        String sv = ext.getSpongeVanilla();

        if (sv != null) {
            getLogger().lifecycle("SpongeVanilla {}", sv);
            return REPO + "spongevanilla/" + sv + "/spongevanilla-" + sv + ".jar";
        }

        File cached = new File(ext.getCacheFolder(), "downloads" + File.separatorChar + "spongevanilla.xml");

        DownloadUtils.downloadToFile(REPO + "spongevanilla/maven-metadata.xml", cached);

        String version = getStableSpongeVersion(cached);

        getLogger().lifecycle("SpongeVanilla latest version: {}", version);

        return REPO + "spongevanilla/" + version + "/spongevanilla-" + version + ".jar";
    }
}
