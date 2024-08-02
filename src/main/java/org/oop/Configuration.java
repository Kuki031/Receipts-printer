package org.oop;

import java.io.File;

public class Configuration {

    private static Configuration instance;
    private String FILE_PATH;
    private Configuration() {
        File[] roots = File.listRoots();
        this.FILE_PATH = roots[0].getAbsolutePath() + "Receipts\\";
    }

    public static Configuration getInstance() {

        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }
}
