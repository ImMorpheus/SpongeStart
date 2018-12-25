package com.qixalite.spongestart.tasks.setup;

import com.qixalite.spongestart.SpongeStartExtension;
import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class SetupForgeServerTask extends SetupServerTask {

    @Override
    public void setupServer() {
        try {
            Process pr = new ProcessBuilder()
                    .command("java -jar setup.jar --installServer".split(" "))
                    .directory(getDestination())
                    .redirectErrorStream(true)
                    .start();
            pr.waitFor();
        } catch (InterruptedException e) {
            throw new GradleException("Failed to setup forge: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


        new File(getDestination(), "setup.jar").delete();

        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

        File forge = new File(getDestination(), "forge-" + ext.getMinecraft() + '-' + ext.getForge() + "-universal.jar");
        File output = new File(getDestination(), "server.jar");

        forge.renameTo(output);

        new File(getDestination(), "libraries" + File.separatorChar + "net" + File.separatorChar + "minecraftforge").delete();
        new File(getDestination(), "mods" + File.separatorChar + "mod_list.json").delete();

    }

    @Override
    public void tweakServer() {
        File conf = new File(getDestination(), "config" + File.separatorChar + "forge.cfg");
        conf.getParentFile().mkdirs();
        List<String> lines = Arrays.asList(
                "general {",
                "    B:disableVersionCheck=true",
                "}",
                "version_checking {",
                "    B:Global=false",
                "}"
        );

        try {
            Files.write(conf.toPath(), lines, Charset.defaultCharset());
        } catch (IOException e) {
            throw new GradleException("Failed to tweak forge config: " + e.getMessage());
        }
    }
}
