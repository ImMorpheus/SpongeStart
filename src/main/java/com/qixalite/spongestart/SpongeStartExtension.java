package com.qixalite.spongestart;

public class SpongeStartExtension {

    private String minecraft;
    private String spongeForge;
    private String spongeVanilla;
    private String online = "true";
    private String forgeServerFolder;
    private String vanillaServerFolder;
    private String cacheFolder;
    private String forge;

    public String getMinecraft() {
        return this.minecraft;
    }

    public void setMinecraft(String minecraft) {
        this.minecraft = minecraft;
    }

    public String getSpongeForge() {
        return this.spongeForge;
    }

    public void setSpongeForge(String spongeForge) {
        this.spongeForge = spongeForge;
    }

    public String getSpongeVanilla() {
        return this.spongeVanilla;
    }

    public void setSpongeVanilla(String spongeVanilla) {
        this.spongeVanilla = spongeVanilla;
    }

    public String getOnline() {
        return this.online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getForgeServerFolder() {
        return this.forgeServerFolder;
    }

    public void setForgeServerFolder(String forgeServerFolder) {
        this.forgeServerFolder = forgeServerFolder;
    }

    public String getVanillaServerFolder() {
        return this.vanillaServerFolder;
    }

    public void setVanillaServerFolder(String vanillaServerFolder) {
        this.vanillaServerFolder = vanillaServerFolder;
    }

    public String getCacheFolder() {
        return this.cacheFolder;
    }

    public void setCacheFolder(String cacheFolder) {
        this.cacheFolder = cacheFolder;
    }

    public String getForge() {
        return this.forge;
    }

    public void setForge(String forge) {
        this.forge = forge;
    }
}
