package org.oop;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Writeable {
    private final String companyName;
    private final String receiptName;
    private final String operatorName;
    private final String address;
    private final LocalDateTime date;
    private Map<String, Double> items = new HashMap<String, Double>();
    private double total;
    private final int receiptID;
    private static final int underscores = 30;
    private static final int randMaxValue = Integer.MAX_VALUE;


    public Receipt(Map<String, Double> items, double total) {
        this();
        this.items = items;
        this.total = total;
    }

    public Receipt() {
        Faker faker = new Faker();
        this.companyName = "Restaurant " + faker.funnyName().name();
        this.operatorName = faker.name().fullName();
        this.address = faker.address().streetAddress() + ", " + faker.address().city();
        this.receiptName = "Food receipt";
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
        + "Company: " + this.companyName + '\n'
        + "Address: " + this.address+ "\n\n"
        + "Receipt ID: " + "#" + this.receiptID + '\n'
        + "Date: " + formatDate(this.date) + '\n'
        + "Operator: " + this.operatorName + '\n';
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
            var pricePerUnit =  Double.valueOf(String.format("%.2f", menu.getValue() / itemQuantity));
            var totalPrice = Double.valueOf(String.format("%.2f", itemQuantity * pricePerUnit));

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
                    .append(totalPrice).append('\n');
        }
        return body.toString();
    }

    @Override
    public String writeReceiptFooter() {
        return "-".repeat(underscores) + '\n'
        + "Total: $" + Double.valueOf(String.format("%.2f", this.total)) + '\n' + "***Thank you and come again!***";
    }
}
