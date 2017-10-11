package com.qixalite.spongestart.tasks;

import com.qixalite.spongestart.SpongeStartExtension;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class DownloadTaskV2 extends SpongeStartTask {

    private File location;
    private SpongeStartExtension ext;

    public final void setLocation(File location) {
        this.location = location;
    }

    public final SpongeStartExtension getExtension() {
        return this.ext;
    }

    public final void setExtension(SpongeStartExtension ext) {
        this.ext = ext;
    }


    @TaskAction
    public void doStuff() {
        String url = getDownloadUrl();

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            File cached = new File(getExtension().getCacheFolder(), "downloads/" + url.substring(url.lastIndexOf("/") + 1));
            int size = Integer.valueOf(client.execute(new HttpGet(url)).getLastHeader("Content-Length").getValue());
            if (!(cached.exists() && cached.length() == size)) {
                FileUtils.copyURLToFile(new URL(url), cached);
            }

            FileUtils.copyFile(cached, location);
        } catch (IOException e) {
            throw new GradleException("Failed to download: " + url + " : " + e.getMessage());
        }
    }

    public abstract String getDownloadUrl();

}