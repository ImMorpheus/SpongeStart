package com.qixalite.spongestart.tasks.config;

import com.qixalite.spongestart.tasks.SpongeStartTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class GenerateRunTask extends SpongeStartTask {

    private final String name;
    private final String main;
    private final String pargs;
    private String vargs = "";
    protected final String module;
    protected final Property<Path> runDir;

    protected GenerateRunTask(String name, String main, String pargs, Property<Path> runDir) {
        this.name = name;
        this.main = main;
        this.pargs = pargs;
        getProject().getPlugins().apply(IdeaPlugin.class);
        this.module = getProject().getExtensions().getByType(IdeaModel.class).getModule().getName() + '.' + SourceSet.MAIN_SOURCE_SET_NAME;
        this.runDir = runDir;
    }

    @TaskAction
    public void doStuff() {
        setup();

        Path path = getProject().getRootDir().toPath().resolve(".idea").resolve("workspace.xml");

        Document doc;
        try (InputStream is = Files.newInputStream(path)) {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new GradleException("Something went wrong with your workspace.xml", e);
        }

        Node run = null;
        NodeList comp = doc.getElementsByTagName("component");
        for (int i = 0; i < comp.getLength(); i++) {
            if ("RunManager".equals(comp.item(i).getAttributes().getNamedItem("name").getNodeValue())) {
                run = comp.item(i);
                break;
            }
        }

        if (run == null) {
            Element runManager = doc.createElement("component");
            runManager.setAttribute("name", "RunManager");
            doc.getDocumentElement().appendChild(runManager);
            run = runManager;
        }


        Element configuration = doc.createElement("configuration");
        configuration.setAttribute("name", this.name + System.currentTimeMillis());
        configuration.setAttribute("type", "Application");
        configuration.setAttribute("factoryName", "Application");

        Element mainName = doc.createElement("option");
        mainName.setAttribute("name", "MAIN_CLASS_NAME");
        mainName.setAttribute("value", this.main);

        Element moduleName = doc.createElement("module");
        moduleName.setAttribute("name", this.module);

        Element programParameters = doc.createElement("option");
        programParameters.setAttribute("name", "PROGRAM_PARAMETERS");
        programParameters.setAttribute("value", this.pargs);

        Element virtualParameters = doc.createElement("option");
        virtualParameters.setAttribute("name", "VM_PARAMETERS");
        virtualParameters.setAttribute("value", this.vargs);


        Element workingDir = doc.createElement("option");
        workingDir.setAttribute("name", "WORKING_DIRECTORY");
        workingDir.setAttribute("value", this.runDir.get().toString());


        configuration.appendChild(mainName);
        configuration.appendChild(moduleName);
        configuration.appendChild(programParameters);
        configuration.appendChild(virtualParameters);
        configuration.appendChild(workingDir);

        run.appendChild(configuration);

        try (OutputStream os = Files.newOutputStream(path)) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(os);
            Source input = new DOMSource(doc);

            transformer.transform(input, output);
        } catch (TransformerException | IOException e) {
            throw new GradleException("Error while saving workspace.xml", e);
        }
    }

    public abstract void setup();

    public void setVargs(String vargs) {
        this.vargs = vargs;
    }
}
