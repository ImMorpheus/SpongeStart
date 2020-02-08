import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenIDEARun {

    private final Path projectDir = Paths.get("testplugins").resolve("TotalEconomy");

    @BeforeEach
    public void before() {
        if (Files.notExists(this.projectDir) || Files.notExists(this.projectDir.resolve(".idea"))) {
            throw new IllegalStateException("No IDEA project found");
        }
    }

    @Test
    public void test() {
        GradleRunner.create()
                .forwardOutput()
                .withDebug(true)
                .withProjectDir(this.projectDir.toFile())
                .withPluginClasspath()
                .withArguments("generateForgeRun", "--stacktrace")
                .build();
    }
}
