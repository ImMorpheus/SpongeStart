package com.qixalite.spongestart;

public final class Constants {

    public static final String NAME = "spongestart";
    public static final String SPONGE_REPO = "https://repo.spongepowered.org/maven/org/spongepowered/";
    public static final String FORGE_REPO = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/";
    public static final String FORGE_VERSION_DATA = FORGE_REPO + "promotions_slim.json";
    public static final String MOJANG_SERVER = "https://s3.amazonaws.com/Minecraft.Download/versions/";
    public static final String LAUNCHWRAPPER = "https://libraries.minecraft.net/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar";
    public static final String SV_MAIN_CLASS = "org.spongepowered.server.launch.VersionCheckingMain";
    public static final String SF_MAIN_CLASS = "net.minecraftforge.fml.relauncher.ServerLaunchWrapper";

    private Constants() {
    }

    public static final class Tasks {

        public static final String DOWNLOAD_SPONGE_FORGE = "downloadSpongeForge";
        public static final String DOWNLOAD_SPONGE_VANILLA = "downloadSpongeVanilla";
        public static final String DOWNLOAD_FORGE = "downloadForge";
        public static final String DOWNLOAD_VANILLA = "downloadVanilla";
        public static final String DOWNLOAD_LAUNCHWRAPPER = "downloadLaunchwrapper";
        public static final String SETUP_SF_SERVER = "setupForgeServer";
        public static final String SETUP_SV_SERVER = "setupVanillaServer";
        public static final String SF_IDEA_RUN = "generateForgeRun";
        public static final String SV_IDEA_RUN = "generateVanillaRun";
        public static final String CLEAN_CACHE = "cleanSpongeStartCache";

        private Tasks() {
        }
    }
}
