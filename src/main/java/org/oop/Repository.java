package org.oop;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository {
    private static Repository instance;
    private boolean doesExist;

    private Repository() {
        createEmptyRepository();
    }

    public static Repository getInstance() {

        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public boolean isDoesExist() {
        return doesExist;
    }

    protected void createEmptyRepository() {
        try {
            Path path = Paths.get("C:\\Receipts");
            if (path != null) doesExist = true;
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Failed to create directory." + e.getMessage());
        }
    }

    public void open(String targetFilePath) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(targetFilePath));
    }
}