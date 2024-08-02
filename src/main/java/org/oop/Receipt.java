package org.oop;

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
    private static final int underscores = 50;
    private final String totalString = "Total: " + "€";

    public Receipt(Map<String, Double> items, double total) {
        this.companyName = "Restaurant XYZ";
        this.receiptName = "Food receipt";
        this.operaterName = "John Doe";
        this.date = LocalDateTime.now();
        this.items = items;
        this.total = total;
        this.receiptID = (int) Math.floor(Math.random() * 10000);
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void writeReceipt () {
        var i = 0;
        try {
            FileWriter writter = new FileWriter("C:\\Receipts\\receipt" + "#" + this.receiptID+".txt");
            writter.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + " ".repeat(blankSpaces)+'\n');
            writter.write(" ".repeat(centerSpace) + this.receiptName + '\n');
            writter.write(" ".repeat(centerSpace) + this.companyName + '\n' + '\n');
            writter.write(" ".repeat(blankSpaces) + "Receipt ID: " + "#" + this.receiptID + '\n');
            writter.write(" ".repeat(blankSpaces) + "Date: " + formatDate(this.date) + '\n');
            writter.write(" ".repeat(blankSpaces) + "Operator: " + this.operaterName + '\n');

            for (Map.Entry<String, Double> menu : items.entrySet()) {
                i++;
                var parseKey = menu.getKey().substring(0, 1).toUpperCase() + menu.getKey().substring(1);

                writter.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + '\n');
                writter.write(" ".repeat(blankSpaces) + i + ". " + parseKey + " " + "|" + " " + menu.getValue() + "€" + '\n');
            }
            writter.write(" ".repeat(blankSpaces) + "-".repeat(underscores) + '\n');
            writter.write(" ".repeat((blankSpaces + underscores) - totalString.length()) + totalString + this.total);
            writter.close();
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
}
