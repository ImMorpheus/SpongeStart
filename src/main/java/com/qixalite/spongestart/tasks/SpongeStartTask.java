package com.qixalite.spongestart.tasks;

import com.qixalite.spongestart.SpongeStart;
import org.gradle.api.DefaultTask;

public abstract class SpongeStartTask extends DefaultTask {

    public SpongeStartTask() {
        setGroup(SpongeStart.NAME);
    }

}
