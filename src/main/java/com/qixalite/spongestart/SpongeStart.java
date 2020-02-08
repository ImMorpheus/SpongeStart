package com.qixalite.spongestart;

import com.qixalite.spongestart.tasks.CleanCacheTask;
import com.qixalite.spongestart.tasks.download.forge.ForgeDownloadTask;
import com.qixalite.spongestart.tasks.config.GenerateForgeRunTask;
import com.qixalite.spongestart.tasks.config.GenerateVanillaRunTask;
import com.qixalite.spongestart.tasks.download.sponge.SpongeVanillaMavenDownloadTask;
import com.qixalite.spongestart.tasks.download.vanilla.LaunchWrapperDownloadTask;
import com.qixalite.spongestart.tasks.download.vanilla.VanillaDownloadTask;
import com.qixalite.spongestart.tasks.setup.SetupForgeServerTask;
import com.qixalite.spongestart.tasks.download.sponge.SpongeForgeMavenDownloadTask;
import com.qixalite.spongestart.tasks.setup.SetupVanillaServerTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SpongeStart implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        SpongeStartExtension ext = target.getExtensions().create(Constants.NAME, SpongeStartExtension.class, target);

        target.afterEvaluate(project -> {
            // Sponge
            project.getTasks().register(Constants.Tasks.DOWNLOAD_SPONGE_FORGE, SpongeForgeMavenDownloadTask.class,
                    ext.getCacheDir(), ext.getMinecraft(), ext.getForgeRunDir(), ext.getSpongeForge()
            );
            project.getTasks().register(Constants.Tasks.DOWNLOAD_SPONGE_VANILLA, SpongeVanillaMavenDownloadTask.class,
                    ext.getCacheDir(), ext.getMinecraft(), ext.getVanillaRunDir(), ext.getSpongeVanilla()
            );


            // Forge
            project.getTasks().register(Constants.Tasks.DOWNLOAD_FORGE, ForgeDownloadTask.class,
                    ext.getCacheDir(), ext.getMinecraft(), ext.getForgeRunDir(), ext.getForge()
            );


            // Vanilla
            project.getTasks().register(Constants.Tasks.DOWNLOAD_LAUNCHWRAPPER, LaunchWrapperDownloadTask.class,
                    ext.getCacheDir(), ext.getMinecraft(), ext.getVanillaRunDir()
            );
            project.getTasks().register(Constants.Tasks.DOWNLOAD_VANILLA, VanillaDownloadTask.class,
                    ext.getCacheDir(), ext.getMinecraft(), ext.getVanillaRunDir()
            );


            // Setup SpongeForge
            project.getTasks().register(Constants.Tasks.SETUP_SF_SERVER, SetupForgeServerTask.class,
                    ext.getMinecraft(), ext.getForgeRunDir(), ext.getForge()
            ).configure(a -> a.dependsOn(Constants.Tasks.DOWNLOAD_SPONGE_FORGE, Constants.Tasks.DOWNLOAD_FORGE));


            // Setup SpongeVanilla
            project.getTasks().register(Constants.Tasks.SETUP_SV_SERVER, SetupVanillaServerTask.class,
                    ext.getMinecraft(), ext.getVanillaRunDir()
            ).configure(a -> a.dependsOn(Constants.Tasks.DOWNLOAD_VANILLA, Constants.Tasks.DOWNLOAD_LAUNCHWRAPPER, Constants.Tasks.DOWNLOAD_SPONGE_VANILLA));


            // IDEA run configs
            project.getTasks().register(Constants.Tasks.SV_IDEA_RUN, GenerateVanillaRunTask.class,
                    "StartVanillaServer", Constants.SV_MAIN_CLASS, "--scan-classpath", ext.getVanillaRunDir()
            );
            project.getTasks().register(Constants.Tasks.SF_IDEA_RUN, GenerateForgeRunTask.class,
                    "StartForgeServer", Constants.SF_MAIN_CLASS, "--scan-classpath nogui", ext.getForgeRunDir()
            );


            // Clean cache
            project.getTasks().register(Constants.Tasks.CLEAN_CACHE, CleanCacheTask.class, ext.getCacheDir());
        });
    }

}
