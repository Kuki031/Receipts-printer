package org.oop;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class Main {
    private static Menu menu = Menu.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static final int THREAD_DELAY = (int) (1000 + Math.floor(Math.random() * 1500));

    private static Map<String, Double> orderList = new HashMap<String, Double>();
    private static double totalExpense = 0.0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Would you like to place the order?(Y/N)");
        boolean decision = false;


        while (true) {
            String decisionOnOrder = scanner.nextLine();
            if (decisionOnOrder.equalsIgnoreCase("y")) {
                decision = true;
                break;
            } else if (decisionOnOrder.equalsIgnoreCase("n")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Wrong input! Please enter either Y or N!");
            }
        }

        if (decision) {
            while (true) {

                String wantsToContinueTheOrder;
                boolean hasDecidedOnMenu = false;
                String menuDecision = "";
                Map<String, Double> selectedMenu = new HashMap<String, Double>();
                String chosenItem;
                String decidedItemFromMenu;
                double itemPrice = 0.0;
                int quantity = 1;


                System.out.println("Menu will load shortly... Please wait.");
                Thread.sleep(THREAD_DELAY);


                while (true) {
                    System.out.println("Which menu would you like to list? Please enter 'D' for drinks, 'B' for burgers or 'S' for side items!");
                    menuDecision = scanner.nextLine();
                    switch (menuDecision) {
                        case "d", "D" -> {
                            menu.listMenu(menu.getDrinksMap(), "drinks");
                            selectedMenu = menu.getDrinksMap();
                            hasDecidedOnMenu = true;
                        }
                        case "b", "B" -> {
                            menu.listMenu(menu.getBurgersMap(), "burgers");
                            selectedMenu = menu.getBurgersMap();
                            hasDecidedOnMenu = true;
                        }
                        case "s", "S" -> {
                            menu.listMenu(menu.getSideMap(), "side items");
                            selectedMenu = menu.getSideMap();
                            hasDecidedOnMenu = true;
                        }
                        default -> {
                            hasDecidedOnMenu = false;
                        }
                    }
                    if (hasDecidedOnMenu) break;
                    else System.out.println("Wrong input. Enter either D, B or S.");
                }

                while (true) {
                    System.out.println("Please enter an item name from the selected menu list.");
                    decidedItemFromMenu = scanner.nextLine();
                    chosenItem = menu.decideOnItem(decidedItemFromMenu, selectedMenu);
                    if (chosenItem.isBlank() || chosenItem.isEmpty()) {
                        System.out.println("Item in the menu does not exist. Please try again.");
                    } else {
                        while (true) {
                            System.out.printf("Enter the quantity for item %s: ", chosenItem);
                            try {
                                quantity = Integer.parseInt(scanner.nextLine());
                                if (quantity <= 0) {
                                    throw new NumberFormatException();
                                }
                                itemPrice = menu.readItemPrice(chosenItem, selectedMenu) * quantity;
                                totalExpense += itemPrice;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Must be a positive integer value.");
                            }
                        }
                        orderList.put(chosenItem + " x " + quantity,itemPrice);
                        break;
                    }
                }

                generatePrintStatement(orderList, chosenItem, quantity, itemPrice, totalExpense);
                wantsToContinueTheOrder = scanner.nextLine();
                if (wantsToContinueTheOrder.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }


    private static void generatePrintStatement(Map<String, Double> map, String newItem, int quantity, double itemPrice, double totalExpense) {
        System.out.printf("""
                You have ordered %s x %d that costs €%.2f.\
                                        
                Your current total is: €%.2f\

                Would you like to order something else or checkout?

                Enter 'q' to exit and proceed to checkout.\
                                        
                Enter any key to continue the order.""", newItem, quantity, itemPrice, totalExpense);

        System.out.println("\nYour order currently consists of following items:");
        for (Map.Entry<String, Double> order : map.entrySet()) {
            System.out.printf("%s %s €%.2f\n", order.getKey(), "-".repeat(4), order.getValue());
        }
    }
}
