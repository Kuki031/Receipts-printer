package org.oop;

import com.github.javafaker.Faker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final Map<String, Double> foodMap = new HashMap<String, Double>();
    private final Map<String, Double> drinksMap = new HashMap<String, Double>();
    private final Map<String, Double> sideMap = new HashMap<String, Double>();
    private Map<String, Double> chosenMap = new HashMap<String, Double>();
    private final static String path = "./src/main/resources/";

    private double priceToDeduct;
    private final Faker faker = new Faker();

    private static Menu instance;
    private Menu() {
        populateNameFilesWithFaker("food");
        populatePriceFilesWithFaker("food");
        populateNameFilesWithFaker("beers");
        populatePriceFilesWithFaker("beers");
        populateNameFilesWithFaker("sides");
        populatePriceFilesWithFaker("sides");
        populateMenuFromFiles(path+"food.txt", path+"food_prices.txt", foodMap);
        populateMenuFromFiles(path+"beers.txt", path+"beers_prices.txt", drinksMap);
        populateMenuFromFiles(path+"sides.txt", path+"sides_prices.txt", sideMap);
    }

    public static Menu getInstance() {
        if (instance == null) instance = new Menu();
        return instance;
    }

    public double getPriceToDeduct() {
        return priceToDeduct;
    }

    public Map<String, Double> getFoodMap() {
        return foodMap;
    }

    public Map<String, Double> getDrinksMap() {
        return drinksMap;
    }

    public Map<String, Double> getSideMap() {
        return sideMap;
    }

    public Map<String, Double> getChosenMap() {
        return chosenMap;
    }

    public void listMenu(Map<String, Double> items, String item) {
        System.out.printf("Our %s menu consists of:\n", item);
        var i = 0;
        for (Map.Entry<String, Double> menu : items.entrySet()) {
            i++;
            System.out.printf("%d. %s %s €%.2f\n",i, menu.getKey(),"-".repeat(2), menu.getValue());
        }
    }

    private void populateNameFilesWithFaker(String itemName) {
        switch (itemName) {
            case "food" -> {
                try {
                    FileWriter foodNameWriter = new FileWriter(path + "food.txt");
                    for(int i = 0 ; i < 10 ; i++) foodNameWriter.write(faker.food().dish() + '\n');
                    foodNameWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            case "beers" -> {
                try {
                    FileWriter writer = new FileWriter(path + "beers.txt");
                    for(int i = 0 ; i < 10 ; i++) writer.write(faker.beer().malt() + '\n');
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            case "sides" -> {
                try {
                    FileWriter writer = new FileWriter(path + "sides.txt");
                    for(int i = 0 ; i < 10 ; i++) writer.write(faker.food().spice() + '\n');
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void populatePriceFilesWithFaker(String itemName) {
        switch (itemName) {
            case "food" -> {
                try {
                    FileWriter writer = new FileWriter(path + "food_prices.txt");
                    for(int i = 0 ; i < 10 ; i++) writer.write(String.valueOf(faker.number().randomDouble(1, 5, 10)) + '\n');
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            case "beers" -> {
                try {
                    FileWriter writer = new FileWriter(path + "beers_prices.txt");
                    for(int i = 0 ; i < 10 ; i++) writer.write(String.valueOf(faker.number().randomDouble(1, 3, 7)) + '\n');
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            case "sides" -> {
                try {
                    FileWriter writer = new FileWriter(path + "sides_prices.txt");
                    for(int i = 0 ; i < 10 ; i++) writer.write(String.valueOf(faker.number().randomDouble(1, 1, 3)) + '\n');
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void populateMenuFromFiles(String itemName, String itemPrice, Map<String, Double> map) {
        try {
            var name = new File(itemName);
            var price = new File(itemPrice);
            var nameReader = new Scanner(name);
            var priceReader = new Scanner(price);
            while (nameReader.hasNextLine() && priceReader.hasNextLine()) {
                var readName = nameReader.nextLine();
                var readPrice = priceReader.nextLine();
                map.put(readName.toLowerCase(), Double.valueOf(readPrice));
            }
            nameReader.close();
            priceReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File was not found.");
            e.printStackTrace();
        }
    }

    public Map<String, Double> showAvailableMenus(Scanner scanner) {
        while (true) {
            System.out.println("Which menu would you like to list? Please enter 'B' for beers, 'F' for food or 'S' for side items!");
            var menuDecision = scanner.nextLine();
            var hasDecidedOnMenu = false;
            switch (menuDecision.toLowerCase()) {
                case "b" -> {
                    listMenu(getDrinksMap(), "beers");
                    this.chosenMap = getDrinksMap();
                    hasDecidedOnMenu = true;
                }
                case "f" -> {
                    listMenu(getFoodMap(), "food");
                    this.chosenMap = getFoodMap();
                    hasDecidedOnMenu = true;
                }
                case "s" -> {
                    listMenu(getSideMap(), "side items");
                    this.chosenMap = getSideMap();
                    hasDecidedOnMenu = true;
                }
            }
            if (hasDecidedOnMenu) return this.chosenMap;
            else System.err.println("Wrong input. Enter either B, F or S.");
        }
    }

    public String decideOnItem(String itemName, Map<String, Double> items) {
        var exists = false;
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
        var price = 0.0;
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
