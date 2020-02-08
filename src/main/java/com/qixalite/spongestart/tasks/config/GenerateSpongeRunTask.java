package com.qixalite.spongestart.tasks.config;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;
import java.nio.file.Path;

public abstract class GenerateSpongeRunTask extends GenerateRunTask {

    protected GenerateSpongeRunTask(String name, String main, String pargs, Property<Path> runDir) {
        super(name, main, pargs, runDir);
    }

    @Override
    public void setup() {
        Path server = this.runDir.get().resolve("server.jar");
        StringBuilder sb = new StringBuilder("-classpath \"" + server + '"' + File.pathSeparatorChar + '"');

        Configuration shadow = getProject().getConfigurations().findByName("shadow");

        if (shadow != null) {
            ResolvedConfiguration resolvedconfig = shadow.getResolvedConfiguration();

            for (ResolvedDependency res : resolvedconfig.getFirstLevelModuleDependencies()) {
                for (ResolvedArtifact artifact : res.getAllModuleArtifacts()) {
                    sb.append(artifact.getFile().getAbsolutePath())
                            .append('"')
                            .append(File.pathSeparatorChar)
                            .append('"');
                }
            }
        }

        SourceSet main = getProject().getExtensions().getByType(SourceSetContainer.class).getAt(SourceSet.MAIN_SOURCE_SET_NAME);
        sb.append(main.getOutput().getAsPath());
        setVargs(sb.toString());
    }


}
