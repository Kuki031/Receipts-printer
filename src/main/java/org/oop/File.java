package org.oop;

/*import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class File {
    /*Opening file
        open("C:\\Users\\Kukac\\Desktop\\test");


    /*Creating empty directory*/
//    try {
//        Path path = Paths.get("C:\\Receipts");
//        Files.createDirectories(path);
//        System.out.println("Directory is created!");
//
//    } catch (
//    IOException e) {
//        System.err.println("Failed to create directory!" + e.getMessage());
//
//    }
//
//    /*Creating new file in a specific directory*/
//        try {
//        java.io.File file = new java.io.File("C:\\Receipts\\demo1.pdf");
//        file.createNewFile();
//        System.out.println("File: " + file);
//    } catch(Exception e) {
//        e.printStackTrace();
//    }
//
//    /*Write to file*/
//        try {
//        FileWriter myWriter = new FileWriter("C:\\Receipts\\demo1.txt");
//        myWriter.write("Files in Java might be tricky, but it is fun enough!");
//        myWriter.close();
//        System.out.println("Successfully wrote to the file.");
//    } catch (IOException e) {
//        System.out.println("An error occurred.");
//        e.printStackTrace();
//    }
//
