package com.qixalite.spongestart.tasks.config;

import com.qixalite.spongestart.SpongeStartExtension;

public class GenerateForgeRunTask extends GenerateSpongeRunTask {

    @Override
    public void setup() {
        super.setup();
        setName("StartForgeServer");
        setDir(getServerPath());
        setMain("net.minecraftforge.fml.relauncher.ServerLaunchWrapper");
        setPargs("--scan-classpath nogui");
        setDescription("Generate Forge run configuration to start a SpongeForge server");
    }

    @Override
    public String getServerPath() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);
        return ext.getForgeServerFolder();
    }
}
