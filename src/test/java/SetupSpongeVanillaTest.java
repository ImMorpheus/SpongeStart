import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SetupSpongeVanillaTest {

    private final Path projectDir = Paths.get("testplugins").resolve("sponge");;
    private final Path build = this.projectDir.resolve("build.gradle");

    @BeforeEach
    public void before() throws IOException {
        Files.createDirectories(this.projectDir);
    }

    @Test
    public void test() throws IOException {
        String content =
                "plugins {" + System.lineSeparator() +
                "    id 'com.qixalite.spongestart2' " + System.lineSeparator() +
                "}" + System.lineSeparator() +
                "spongestart {" + System.lineSeparator() +
                "    minecraft = '1.12.2'" + System.lineSeparator() +
                "}";

        Files.write(this.build, content.getBytes(), StandardOpenOption.CREATE);

        GradleRunner.create()
                .forwardOutput()
                .withDebug(true)
                .withProjectDir(this.projectDir.toFile())
                .withPluginClasspath()
                .withArguments("setupVanillaServer", "--stacktrace")
                .build();
    }
}
