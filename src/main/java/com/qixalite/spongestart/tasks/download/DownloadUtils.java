package com.qixalite.spongestart.tasks.download;

import org.gradle.api.GradleException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class DownloadUtils {

    private DownloadUtils() {
    }

    public static void save(String url, Path to) {
        URLConnection from = DownloadUtils.open(url);
        Path parent = to.getParent();

        try {
            if (Files.exists(to) && Files.size(to) == from.getContentLength()) {
                return;
            }
            Files.createDirectories(parent);
        } catch (IOException e) {
            throw new GradleException("Error", e);
        }

        try (InputStream in = from.getInputStream();
             OutputStream out = Files.newOutputStream(to, StandardOpenOption.CREATE)) {

            byte[] buf = new byte[4096];
            int i;
            while (-1 != (i = in.read(buf))) {
                out.write(buf, 0, i);
            }
        } catch (FileNotFoundException e) {
            throw new GradleException("Unable to find " + from.getURL().getFile(), e);
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening a connection to " + from.getURL(), e);
        }
    }

    private static URLConnection open(String url) {
        try {
            return new URL(url).openConnection();
        } catch (MalformedURLException e) {
            throw new GradleException("Malformed URL: " + url, e);
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening a connection to " + url, e);
        }
    }
}
