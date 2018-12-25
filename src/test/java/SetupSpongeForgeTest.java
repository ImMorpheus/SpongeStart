import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SetupSpongeForgeTest {

    private final File testProjectDir = new File("tesplugins");
    private File buildFile;

    @BeforeEach
    public void setup() {
        this.testProjectDir.mkdirs();
        this.buildFile = new File(this.testProjectDir, "build.gradle");
    }

    @Test
    public void testHelloWorldTask() throws IOException {
        String buildFileContent =
                "plugins {\n" +
                "    id 'com.qixalite.spongestart2' \n" +
                "}\n" +
                "\n" +
                "spongestart {\n" +
                "    minecraft '1.12.2'\n" +
                "}";

        try (BufferedWriter output = new BufferedWriter(new FileWriter(this.buildFile))) {
            output.write(buildFileContent);
        }

        GradleRunner.create()
                .forwardOutput()
                .withProjectDir(this.testProjectDir)
                .withPluginClasspath()
                .withArguments("setupForgeServer", "--stacktrace")
                .build();

    }
}