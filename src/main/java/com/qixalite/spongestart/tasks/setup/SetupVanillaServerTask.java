package com.qixalite.spongestart.tasks.setup;

import com.qixalite.spongestart.SpongeStartExtension;
import com.qixalite.spongestart.tasks.download.DownloadUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SetupVanillaServerTask extends SetupServerTask {

    private static final String MOJANG_SERVER = "https://s3.amazonaws.com/Minecraft.Download/versions/";

    //Need a better solution for when the launchwrapper is updated
    private static final String LAUNCHWRAPPER = "https://libraries.minecraft.net/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar";

    @Override
    public void setupServer() {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

        String mc = ext.getMinecraft();
        String url = MOJANG_SERVER + mc + "/minecraft_server." + mc + ".jar";

        downloadLibrary(url, getDestination());
        downloadLibrary(LAUNCHWRAPPER, new File(getDestination(),
                File.separatorChar + "libraries" + File.separatorChar + "net" + File.separatorChar + "minecraft" + File.separatorChar + "launchwrapper" + File.separatorChar + "1.12"));
    }

    private void downloadLibrary(String link, File destination) {
        SpongeStartExtension ext = getProject().getExtensions().getByType(SpongeStartExtension.class);

        File cached = new File(ext.getCacheFolder(), "downloads" + File.separatorChar + link.substring(link.lastIndexOf('/') + 1));

        DownloadUtils.downloadToFile(link, cached);

        destination.mkdirs();
        try {
            Files.copy(cached.toPath().toAbsolutePath(), Paths.get(destination.getAbsolutePath() + File.separatorChar + cached.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
