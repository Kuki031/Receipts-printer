package org.oop;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Writeable {
    private String companyName;
    private String receiptName;
    private String operaterName;
    private LocalDateTime date;
    private Map<String, Double> items = new HashMap<String, Double>();
    private double total;
    private int receiptID;
    private static final int underscores = 30;
    private static final int randMaxValue = Integer.MAX_VALUE;


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

    public void openDirectory(Repository repository, Receipt receipt) throws IOException {
        repository.open(Configuration.getInstance().getFILE_PATH());
        repository.open(Configuration.getInstance().getFILE_PATH() + "receipt#"+receipt.getReceiptID()+".pdf");
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatObj);
    }

    @Override
    public String writeReceiptHead() {
        return "-".repeat(underscores) + '\n'
        + this.receiptName + '\n'
        + this.companyName + '\n' + '\n'
        + "Receipt ID: " + "#" + this.receiptID + '\n'
        + "Date: " + formatDate(this.date) + '\n'
        + "Operator: " + this.operaterName + '\n';
    }

    @Override
    public String writeReceiptBody() {
        var i = 0;
        StringBuilder body = new StringBuilder();
        for (Map.Entry<String, Double> menu : items.entrySet()) {
            i++;
            var parseKey = menu.getKey().substring(0, 1).toUpperCase() + menu.getKey().substring(1);
            var itemName = parseKey.split("x")[0];
            var itemQuantity = Double.parseDouble(parseKey.split("x")[1]);
            var pricePerUnit =  menu.getValue() / itemQuantity;

            body.append("-".repeat(underscores))
                    .append('\n')
                    .append(i).append(". ")
                    .append(itemName)
                    .append('\n')
                    .append("Unit price: ").append("$")
                    .append(pricePerUnit).append('\n')
                    .append("Quantity: ")
                    .append((int) itemQuantity)
                    .append('\n')
                    .append("Total: ").append("$")
                    .append(itemQuantity * pricePerUnit).append('\n');
        }
        return body.toString();
    }

    @Override
    public String writeReceiptFooter() {
        return "-".repeat(underscores) + '\n'
        + "Total: $" + this.total;
    }
}
