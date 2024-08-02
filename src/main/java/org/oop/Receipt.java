package org.oop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Receipt {
    private String companyName;
    private String receiptName;
    private String operaterName;
    private LocalDateTime date;
    private Map<String, Double> items = new HashMap<String, Double>();
    private double total;
    private int receiptID;
    private static final int blankSpaces = 15;
    private static final int centerSpace = 35;
    private static final int underscores = blankSpaces + centerSpace;
    private static final int randMaxValue = Integer.MAX_VALUE;
    private final String totalString = "Total: " + "€";

    public Receipt(Map<String, Double> items, double total) {
        this();
        this.items = items;
        this.total = total;
    }

    public Receipt() {
        this.companyName = "Restaurant XYZ";
        this.receiptName = "Food receipt";
        this.operaterName = "John Doe";
        this.receiptID = (int) Math.floor(Math.random() * randMaxValue);
        this.date = LocalDateTime.now();
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void writeReceipt () {
        try {
            FileWriter writer = new FileWriter("C:\\Receipts\\receipt" + "#" + this.receiptID+".txt");
            writeReceiptHead(writer);
            writeReceiptBody(writer);
            writeReceiptFooter(writer);
            writer.close();
            System.out.println("Receipt written successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing receipt.");
            e.printStackTrace();
        }
    }

    public void generateReceipt(Repository repository, Receipt receipt) throws IOException {
        repository.open("C:\\Receipts");
        receipt.writeReceipt();
        repository.open("C:\\Receipts\\receipt#"+receipt.getReceiptID()+".txt");
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatObj);
    }

    private void writeReceiptHead(FileWriter writer) throws IOException {
        writer.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + " ".repeat(blankSpaces)+'\n');
        writer.write(" ".repeat(centerSpace) + this.receiptName + '\n');
        writer.write(" ".repeat(centerSpace) + this.companyName + '\n' + '\n');
        writer.write(" ".repeat(blankSpaces) + "Receipt ID: " + "#" + this.receiptID + '\n');
        writer.write(" ".repeat(blankSpaces) + "Date: " + formatDate(this.date) + '\n');
        writer.write(" ".repeat(blankSpaces) + "Operator: " + this.operaterName + '\n');
    }

    private void writeReceiptBody(FileWriter writer) throws IOException {
        var i = 0;
        for (Map.Entry<String, Double> menu : items.entrySet()) {
            i++;
            var parseKey = menu.getKey().substring(0, 1).toUpperCase() + menu.getKey().substring(1);
            var itemName = parseKey.split("x")[0];
            var itemQuantity = Double.parseDouble(parseKey.split("x")[1]);
            var pricePerUnit =  menu.getValue() / itemQuantity;


            writer.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + '\n');
            writer.write(" ".repeat(blankSpaces) + i + ". " + itemName + '\n');
            writer.write(" ".repeat(blankSpaces) + "Unit price: " + "€" +pricePerUnit + '\n');
            writer.write(" ".repeat(blankSpaces) + "Quantity: " + (int) itemQuantity + '\n');
            writer.write(" ".repeat(blankSpaces) + "Total: " + "€" + itemQuantity * pricePerUnit + '\n');
        }
    }

    private void writeReceiptFooter(FileWriter writer) throws IOException {
        writer.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + '\n');
        writer.write(" ".repeat((blankSpaces + underscores) - totalString.length()) + totalString + this.total);
    }
}
