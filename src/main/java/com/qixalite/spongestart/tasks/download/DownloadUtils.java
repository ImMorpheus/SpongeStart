package com.qixalite.spongestart.tasks.download;

import org.gradle.api.GradleException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class DownloadUtils {

    public static void downloadToFile(String url, File to) {
        URLConnection from = DownloadUtils.openURL(url);

        if (to.exists() && to.length() == from.getContentLength()) {
            return;
        }

        to.delete();
        try (InputStream in = from.getInputStream();
             FileOutputStream out = new FileOutputStream(to)) {

            byte[] buf = new byte[8192];
            int l;
            while (-1 != (l = in.read(buf))) {
                out.write(buf, 0, l);
            }
        } catch (FileNotFoundException e) {
            throw new GradleException("Unable to find " + from.getURL().getFile(), e);
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening a connection to " + from.getURL(), e);
        }
    }

    private static URLConnection openURL(String url) {
        try {
            return new URL(url).openConnection();
        } catch (MalformedURLException e) {
            throw new GradleException("Malformed URL: " + url, e);
        } catch (IOException e) {
            throw new GradleException("I/O exception while opening a connection to " + url, e);
        }
    }

    private DownloadUtils() {
    }
}
