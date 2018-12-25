package com.qixalite.spongestart;

import com.qixalite.spongestart.tasks.CleanFolderTask;
import com.qixalite.spongestart.tasks.download.ForgeDownloadTask;
import com.qixalite.spongestart.tasks.config.GenerateForgeRunTask;
import com.qixalite.spongestart.tasks.config.GenerateVanillaRunTask;
import com.qixalite.spongestart.tasks.setup.SetupForgeServerTask;
import com.qixalite.spongestart.tasks.setup.SetupVanillaServerTask;
import com.qixalite.spongestart.tasks.download.SpongeForgeMavenDownloadTask;
import com.qixalite.spongestart.tasks.download.SpongeVanillaMavenDownloadTask;
import org.gradle.api.NonNullApi;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

@NonNullApi
public class SpongeStart implements Plugin<Project> {

    public static final String NAME = "SpongeStart";

    @Override
    public void apply(Project target) {

        target.getPlugins().apply("idea");

        SpongeStartExtension extension = target.getExtensions().create(NAME.toLowerCase(), SpongeStartExtension.class);

        target.afterEvaluate(project -> {

            setupDirs(project, extension);

            //SpongeForge Download Task
            SpongeForgeMavenDownloadTask downloadSpongeForge = project.getTasks().create("downloadSpongeForge", SpongeForgeMavenDownloadTask.class, task -> {
                task.setDestination(new File(extension.getForgeServerFolder(), "mods" + File.separatorChar + "sponge.jar"));
                task.setDescription("Download SpongeForge jar");
            });

            //SpongeVanilla Download Task
            SpongeVanillaMavenDownloadTask downloadSpongeVanilla = project.getTasks().create("downloadSpongeVanilla", SpongeVanillaMavenDownloadTask.class, task -> {
                task.setDestination(new File(extension.getVanillaServerFolder(), "server.jar"));
                task.setDescription("Download SpongeVanilla jar");
            });

            //Forge Download Task
            ForgeDownloadTask downloadForge = project.getTasks().create("downloadForge", ForgeDownloadTask.class, task -> {
                task.dependsOn(downloadSpongeForge);
                task.setDestination(new File(extension.getForgeServerFolder(), "setup.jar"));
                task.setDescription("Download Forge jar");
            });


            //generate intellij tasks
            GenerateVanillaRunTask generateVanillaRun = project.getTasks().create("GenerateVanillaRun", GenerateVanillaRunTask.class,
                    task -> task.setDescription("Generate SpongeVanilla run configuration for IntelliJ")
            );

            GenerateForgeRunTask generateForgeRun = project.getTasks().create("GenerateForgeRun", GenerateForgeRunTask.class,
                    task -> task.setDescription("Generate SpongeForge run configuration for IntelliJ")
            );


            //Setup Forge task
            SetupForgeServerTask setupForgeServer = project.getTasks().create("setupForgeServer", SetupForgeServerTask.class, task -> {
                task.dependsOn(downloadForge);
                task.setDestination(new File(extension.getForgeServerFolder()));
                task.setDescription("Setup a SpongeForge server");
            });

            //Setup Vanilla task
            SetupVanillaServerTask setupVanillaServer = project.getTasks().create("setupVanillaServer", SetupVanillaServerTask.class, task -> {
                task.dependsOn(downloadSpongeVanilla);
                task.setDestination(new File(extension.getVanillaServerFolder()));
                task.setDescription("Setup a SpongeVanilla server");
            });

            //clean tasks
            project.getTasks().create("cleanSpongeStartCache", CleanFolderTask.class, task -> {
                task.setFolder(new File(extension.getCacheFolder()));
                task.setDescription("Clean SpongeStart cache folder");
            });

        });
    }


    private void setupDirs(Project project, SpongeStartExtension extension) {
        String cacheDir = extension.getCacheFolder();
        if (cacheDir == null) {
            cacheDir = project.getGradle().getGradleUserHomeDir().getAbsolutePath() + File.separator + "caches" + File.separatorChar + NAME;
            extension.setCacheFolder(cacheDir);
        }
        new File(cacheDir).mkdirs();

        new File(extension.getCacheFolder(), "downloads").mkdirs();

        String forgeDir = extension.getForgeServerFolder();
        if (forgeDir == null) {
            forgeDir = project.getProjectDir().getAbsolutePath() + File.separatorChar + "run" + File.separatorChar + "forge";
            extension.setForgeServerFolder(forgeDir);
        }

        String vanillaDir = extension.getVanillaServerFolder();
        if (vanillaDir == null) {
            vanillaDir = project.getProjectDir().getAbsolutePath() + File.separatorChar + "run" + File.separatorChar + "vanilla";
            extension.setVanillaServerFolder(vanillaDir);
        }
    }

}
