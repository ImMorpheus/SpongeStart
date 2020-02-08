package com.qixalite.spongestart.tasks.download;

import java.nio.file.Path;

public interface DownloadTask {

    String getUrl();

    Path getDestination();

}
