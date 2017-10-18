package com.qixalite.spongestart.tasks;

import org.apache.commons.io.IOUtils;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class GenerateStartTask extends SpongeStartTask {

    private static final List<String> files = Arrays.asList("StartServer.class", "StartServer$SpongeClassLoader.class");

    private File outputDir;

    @OutputDirectory
    public File getOutputDir() {
        return outputDir;
    }

    @TaskAction
    public void doStuff() {

        if (!getOutputDir().exists()) getOutputDir().mkdir();

        for (String s : files) {
            InputStream link = this.getClass().getClassLoader().getResourceAsStream(s);
            File outputFile = new File(getOutputDir(), s);
            try {
                IOUtils.copy(link, new FileOutputStream(outputFile));
            } catch (IOException e) {
                throw new GradleException("Failed to generate Start class: " + e.getMessage());
            }
        }
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }
}
