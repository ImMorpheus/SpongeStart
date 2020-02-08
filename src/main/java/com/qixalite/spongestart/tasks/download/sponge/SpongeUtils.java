package com.qixalite.spongestart.tasks.download.sponge;

import org.gradle.api.GradleException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class SpongeUtils {

    private SpongeUtils() {
    }

    public static String getRB(Path xml, String minecraft) {
        try (Stream<String> stream = Files.lines(xml)) {
            final String prefix = "      <version>" + minecraft;

            return stream.filter(s -> s.startsWith(prefix) && !s.contains("-RC"))
                    .reduce((a, b) -> b)
                    .map(s -> s.substring("      <version>".length(), s.length() - "</version>".length()))
                    .orElseThrow(() -> new GradleException("Cannot find RB for mc " + minecraft));
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening " + xml, e);
        }
    }

}
