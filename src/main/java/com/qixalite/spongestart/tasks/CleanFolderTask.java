package com.qixalite.spongestart.tasks;

import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class CleanFolderTask extends SpongeStartTask {

    private File folder;

    public void setFolder(File folder) {
        this.folder = folder;
    }

    @TaskAction
    public void doStuff() {
        for (File f : this.folder.listFiles()) {
            f.delete();
        }
    }
}
