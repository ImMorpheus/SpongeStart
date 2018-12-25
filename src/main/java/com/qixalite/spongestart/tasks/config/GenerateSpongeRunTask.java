package com.qixalite.spongestart.tasks.config;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class GenerateSpongeRunTask extends GenerateRunTask {

    @Override
    public void setup() {
        StringBuilder s = new StringBuilder("-classpath \"" + getServerPath() + File.separatorChar + "server.jar\"" + File.pathSeparatorChar + '"');

        Configuration compile = getProject().getConfigurations().getByName("compile");
        ResolvedConfiguration resolvedconfig = compile.getResolvedConfiguration();

        for (ResolvedDependency res : resolvedconfig.getFirstLevelModuleDependencies()) {
            if (!res.getName().startsWith("org.spongepowered:spongeapi")) {
                for (ResolvedArtifact artifact : res.getAllModuleArtifacts()) {
                    s.append(artifact.getFile().getAbsolutePath())
                            .append('"')
                            .append(File.pathSeparatorChar)
                            .append('"');
                }
            }
        }

        File f = new File(getProject().getRootDir().getAbsolutePath() + File.separatorChar + ".idea" + File.separatorChar + "misc.xml");

        try {

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);

            Node run = null;
            NodeList comp = doc.getElementsByTagName("component");
            for (int i = 0; i < comp.getLength(); i++) {
                if (comp.item(i).getAttributes().getNamedItem("name").getNodeValue().equals("ProjectRootManager")) {
                    run = comp.item(i);
                    break;
                }
            }

            String dir = null;
            Element e = (Element) run.getChildNodes();
            NodeList conf = e.getElementsByTagName("output");
            for (int c = 0; c < conf.getLength(); c++) {
                Node n = conf.item(c);
                Node nm = n.getAttributes().getNamedItem("url");
                dir = nm.getNodeValue();
            }

            Pattern pattern = Pattern.compile("\\$PROJECT_DIR\\$");

            s.append(getProject().getRootDir()).append(pattern.split(dir)[1])
                    .append(File.separatorChar).append("production")
                    .append(File.separatorChar).append(getModule());
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        setVargs(s.toString());
    }

    public abstract String getServerPath();


}
