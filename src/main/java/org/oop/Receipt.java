package org.oop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Receipt {
    private String companyName;
    private String receiptName;
    private Calendar date;
    private Map<String, Double> items = new HashMap<String, Double>();
    private double total;
    private int receiptID;

    public Receipt(Map<String, Double> items, double total) {
        this.companyName = "XYZ";
        this.receiptName = "Food receipt";
        this.date = Calendar.getInstance();
        this.items = items;
        this.total = total;
        this.receiptID = (int) Math.floor(Math.random() * 10000);
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void writeReceipt () {
        try {
            FileWriter writter = new FileWriter("C:\\Receipts\\receipt" + "#"+this.receiptID+".txt");
            writter.write("yay");
            writter.close();
            System.out.println("Receipt written successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing receipt.");
            e.printStackTrace();
        }
    }
}
