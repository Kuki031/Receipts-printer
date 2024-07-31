package org.oop;

public class Menu {


    private final String[] burgers = {"Big Mac", "Cowboy", "Cheeseburger", "Hamburger"};
    private final String[] drinks = {"Coca-Cola", "Tea", "Beer", "Water", "Milk"};
    private final String[] sideItems = {"Ketchup", "Mayo", "Tartar"};

    private static Menu instance;
    private Menu() {}

    public static Menu getInstance() {

        if (instance == null) {
            instance = new Menu();
        }

        return instance;
    }

    public String[] getBurgers() {
        return burgers;
    }

    public String[] getDrinks() {
        return drinks;
    }

    public String[] getSideItems() {
        return sideItems;
    }

    public void listMenu(String[] items, String item) {
        System.out.printf("Our %s menu consists of:\n", item);
        for(int i = 0 ; i < items.length ; i++) {
            System.out.printf("%d. %s\n", i + 1, items[i]);
        }
    }
    public String decideOnMenu(String menuDecision) {
        switch (menuDecision) {
            case "d", "D" -> menuDecision = "Drinks";
            case "b", "B" -> menuDecision = "Burgers";
            case "s", "S" -> menuDecision = "Side item";
            default -> menuDecision = "";
        }

        return menuDecision;
    }

    public void listDecidedMenu(String decidedMenu, Menu menu) {
        switch (decidedMenu) {
            case "Drinks":
                menu.listMenu(menu.getDrinks(), decidedMenu);
                break;
            case "Burgers":
                menu.listMenu(menu.getBurgers(), decidedMenu);
                break;
            case "Side item":
                menu.listMenu(menu.getSideItems(), decidedMenu);
                break;
        }
    }

    public double assignRandomPrice(String itemType) {
        double setPrice = 0.0;
        switch (itemType) {
            case "d", "D" -> setPrice = Math.random() * 3.0;
            case "b", "B" -> setPrice = Math.random() * 7.0;
            case "s", "S" -> setPrice = Math.random();
        }
        return setPrice;
    }

    public String decideOnItem(int itemNo, String[] items) {
        String itemToPlaceInOrder = "";
        for(int i = 0 ; i < items.length ; i++) {
            if (i == itemNo - 1) {
                itemToPlaceInOrder = items[i];
                break;
            }
        }
        return itemToPlaceInOrder;
    }
}
