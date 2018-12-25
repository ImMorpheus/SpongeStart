package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.SpongeStartExtension;
import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ForgeDownloadTask extends DownloadTask {

    private static final String FORGE_REPO = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/";
    private static final String FORGE_VERSION_DATA = FORGE_REPO + "promotions_slim.json";

    @Override
    public String getUrl() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

        String f = ext.getForge();

        if (f != null) {
            String key = ext.getMinecraft() + '-' + f;
            return FORGE_REPO + key + "/forge-" + key + "-installer.jar";
        }

        File cached = new File(ext.getCacheFolder(), "downloads" + File.separatorChar + "forge.json");

        DownloadUtils.downloadToFile(FORGE_VERSION_DATA, cached);

        String version = getRecommendedForgeVersion(cached);

        getProject().getLogger().lifecycle("Latest version: " + version);

        String key = ext.getMinecraft() + '-' + version;
        ext.setForge(version);

        return FORGE_REPO + key + "/forge-" + key + "-installer.jar";
    }

    private String getRecommendedForgeVersion(File forge) {
        try (Stream<String> stream = Files.lines(forge.toPath())) {

            SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);
            String prefix = "    \""+ext.getMinecraft()+"-recommended\": \"";

            return stream.filter(s -> s.startsWith(prefix)).findFirst()
                    .map(s -> s.substring(prefix.length(), s.length() - "\",".length()))
                    .orElseThrow(() -> new GradleException("Forge version not found"));
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening " + forge, e);
        }

    }

}
