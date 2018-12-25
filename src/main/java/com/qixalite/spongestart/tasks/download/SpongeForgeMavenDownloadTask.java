package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.SpongeStartExtension;

import java.io.File;

public class SpongeForgeMavenDownloadTask extends SpongeDownloadTask {

    @Override
    public String getUrl() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);
        String sf = ext.getSpongeForge();

        if (sf != null) {
            getLogger().lifecycle("SpongeForge {}", sf);
            return REPO + "spongeforge/" + sf + "/spongeforge-" + sf + ".jar";
        }

        File cached = new File(ext.getCacheFolder(), "downloads" + File.separatorChar + "spongeforge.xml");

        DownloadUtils.downloadToFile(REPO + "spongeforge/maven-metadata.xml", cached);

        String version = getStableSpongeVersion(cached);

        getLogger().lifecycle("SpongeForge ({}) latest version: {}", ext.getMinecraft(), version);

        return REPO + "spongeforge/" + version + "/spongeforge-" + version + ".jar";
    }



}
