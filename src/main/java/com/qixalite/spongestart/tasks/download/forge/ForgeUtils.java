package com.qixalite.spongestart.tasks.download.forge;

import org.gradle.api.GradleException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class ForgeUtils {

    private ForgeUtils() {
    }

    public static String getRB(Path xml, String minecraft) {
        try (Stream<String> stream = Files.lines(xml)) {
            final String prefix = "    \""+minecraft+"-recommended\": \"";

            return stream.filter(s -> s.startsWith(prefix))
                    .findAny()
                    .map(s -> s.substring(prefix.length(), s.length() - "\",".length()))
                    .orElseThrow(() -> new GradleException("Forge version not found"));
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening " + xml, e);
        }
    }

}
