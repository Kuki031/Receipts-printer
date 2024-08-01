package org.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final Map<String, Double> burgersMap = new HashMap<String, Double>();
    private final Map<String, Double> drinksMap = new HashMap<String, Double>();
    private final Map<String, Double> sideMap = new HashMap<String, Double>();
    private final static String path = "./src/main/resources/";

    private double priceToDeduct;

    private static Menu instance;
    private Menu() {
        populateMenuFromFiles(path+"burgers.txt", path+"burgerPrices.txt", burgersMap);
        populateMenuFromFiles(path+"drinks.txt", path+"drinksPrices.txt", drinksMap);
        populateMenuFromFiles(path+"side.txt", path+"sidePrices.txt", sideMap);
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
