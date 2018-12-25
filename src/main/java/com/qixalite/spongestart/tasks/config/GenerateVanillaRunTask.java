package com.qixalite.spongestart.tasks.config;

import com.qixalite.spongestart.SpongeStartExtension;

public class GenerateVanillaRunTask extends GenerateSpongeRunTask {

    @Override
    public void setup() {
        super.setup();
        setDescription("Generate Vanilla run configuration to start a SpongeVanilla server");
        setName("StartVanillaServer");
        setDir(getServerPath());
        setMain("org.spongepowered.server.launch.VersionCheckingMain");
        setPargs("--scan-classpath");
    }

    @Override
    public String getServerPath() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);
        return ext.getVanillaServerFolder();
    }
}
