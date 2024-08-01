package org.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    /*
    0.) Add quantity of the selected item ++++
    1.) Add more items in the list ++++
        -> Optionally, read menu from the .txt file and populate HashMap accordingly along with prices ++++
    2.) Convert arrays below into HashMaps (name, price) ++++
        -> You'll need to refactor the Main and Order classes as well ++++
    5.) Add receipt class for printing receipt
    6.) Write receipt in PDF file with following attributes:
        -> Date (parse it nicely)
        -> Business subject (name)
        -> Address
        -> Owner
        -> Ordered items with prices and total at the bottom
     7.) Add ability to remove item from the list (remove from HashMap + deduct from totalExpense) ++++
     8.) Store all receipts on C:\\ disk in a folder named "Receipts"
    */

    private Map<String, Double> burgersMap = new HashMap<String, Double>();
    private Map<String, Double> drinksMap = new HashMap<String, Double>();
    private Map<String, Double> sideMap = new HashMap<String, Double>();
    private double priceToDeduct;

    private static Menu instance;
    private Menu() {
        populateMenuFromFiles("burgers.txt", "burgerPrices.txt", burgersMap);
        populateMenuFromFiles("drinks.txt", "drinksPrices.txt", drinksMap);
        populateMenuFromFiles("side.txt", "sidePrices.txt", sideMap);
    }

    public static Menu getInstance() {

        if (instance == null) {
            instance = new Menu();
        }

        return instance;
    }

    public double getPriceToDeduct() {
        return priceToDeduct;
    }

    public Map<String, Double> getBurgersMap() {
        return burgersMap;
    }

    public Map<String, Double> getDrinksMap() {
        return drinksMap;
    }

    public Map<String, Double> getSideMap() {
        return sideMap;
    }

    public void listMenu(Map<String, Double> items, String item) {
        System.out.printf("Our %s menu consists of:\n", item);
        int i = 0;
        for (Map.Entry<String, Double> menu : items.entrySet()) {
            i++;
            System.out.printf("%d. %s %s €%.2f\n",i, menu.getKey(),"-".repeat(2), menu.getValue());
        }
    }

    private void populateMenuFromFiles(String itemName, String itemPrice, Map<String, Double> map) {
        try {
            File name = new File(itemName);
            File price = new File(itemPrice);
            Scanner nameReader = new Scanner(name);
            Scanner priceReader = new Scanner(price);
            while (nameReader.hasNextLine() && priceReader.hasNextLine()) {
                String readName = nameReader.nextLine();
                String readPrice = priceReader.nextLine();
                map.put(readName.toLowerCase(), Double.valueOf(readPrice));
            }
            nameReader.close();
            priceReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File was not found.");
            e.printStackTrace();
        }
    }

    public String decideOnItem(String itemName, Map<String, Double> items) {
        boolean exists = false;
        for (Map.Entry<String, Double> menu : items.entrySet()) {
            if (itemName.equalsIgnoreCase(menu.getKey())) {
                exists = true;
                if (exists) {
                    itemName = menu.getKey();
                    break;
                }
            }
        }
        if (!exists) return "";
        return itemName;
    }

    public double readItemPrice(String itemName, Map<String, Double> items) {
        double price = 0.0;
        for(Map.Entry<String, Double> menu : items.entrySet()) {
            if (itemName.equalsIgnoreCase(menu.getKey())) {
                price = menu.getValue();
                break;
            }
        }
        return price;
    }

    public String modifyOrderList(Map<String, Double> map, String itemName) {
        for(Map.Entry<String, Double> menu : map.entrySet()) {
            if (menu.getKey().startsWith(itemName)) {
                priceToDeduct = menu.getValue();
                map.remove(menu.getKey());
                break;
            }
        }
        return itemName;
    }
}
