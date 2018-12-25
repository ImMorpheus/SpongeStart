package com.qixalite.spongestart.tasks.download;

import com.qixalite.spongestart.SpongeStartExtension;
import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public abstract class SpongeDownloadTask extends DownloadTask {

    static final String REPO = "https://repo.spongepowered.org/maven/org/spongepowered/";

    String getStableSpongeVersion(File maven) {
        try (Stream<String> stream = Files.lines(maven.toPath())) {
            SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

            String prefix = "      <version>" + ext.getMinecraft();

            return stream.filter(s -> s.startsWith(prefix) && !s.contains("-RC"))
                    .reduce((a, b) -> b)
                    .map(s -> s.substring("      <version>".length(), s.length() - "</version>".length()))
                    .orElseThrow(() -> new GradleException("Cannot find latest version for mc " + ext.getMinecraft()));
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening " + maven, e);
        }
    }

}
