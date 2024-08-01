package org.oop;

import java.util.*;

public class Main {
    private static Menu menu = Menu.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static final int THREAD_DELAY = (int) (1000 + Math.floor(Math.random() * 1500));

    private static Map<String, Double> orderList = new HashMap<String, Double>();
    private static double totalExpense = 0.0;
    private static double itemPrice;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Would you like to place the order?(Y/N)");
        boolean decision = false;


        while(true) {
            String decisionOnOrder = scanner.nextLine();
            if (decisionOnOrder.equalsIgnoreCase("y")) {
                decision = true;
                break;
            } else if (decisionOnOrder.equalsIgnoreCase("n")){
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Wrong input! Please enter either Y or N!");
            }
        }

        if (decision) {
            while (true) {

                String wantsToContinueTheOrder;
                String menuDecision = "";
                String[] selectedMenu = {};
                String chosenItem;
                int decidedItemFromMenu;
                int quantity = 1;


                System.out.println("Menu will load shortly... Please wait.");
                Thread.sleep(THREAD_DELAY);


                while (true) {
                    System.out.println("Which menu would you like to list? Please enter 'D' for drinks, 'B' for burgers or 'S' for side items!");
                    menuDecision = scanner.nextLine();
                    switch (menuDecision) {
                        case "d", "D" -> selectedMenu = menu.getDrinks();
                        case "b", "B" -> selectedMenu = menu.getBurgers();
                        case "s", "S" -> selectedMenu = menu.getSideItems();
                        default -> {
                        }
                    }
                    String chosenMenu = menu.decideOnMenu(menuDecision);
                    if (!chosenMenu.isEmpty() && !chosenMenu.isBlank()) {
                        menu.listDecidedMenu(chosenMenu, menu);
                        break;
                    }
                }

                while (true) {
                    System.out.println("Please enter an item number from the selected menu list.");
                    try {
                        decidedItemFromMenu = Integer.parseInt(scanner.nextLine());
                        chosenItem = menu.decideOnItem(decidedItemFromMenu, selectedMenu);

                        itemPrice = menu.assignRandomPrice(menuDecision);
                        orderList.put(chosenItem, itemPrice);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Must be an integer.");
                    }
                }

                while(true) {
                    System.out.printf("Please enter the quantity of item %s: ", chosenItem);
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        totalExpense += (itemPrice * quantity);
                        break;
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Invalid input. Must be an integer.");
                    }
                }

                generatePrintStatement(orderList, chosenItem, quantity);
                wantsToContinueTheOrder = scanner.nextLine();
                if (wantsToContinueTheOrder.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }

    private static void generatePrintStatement(Map<String, Double> map, String newItem, int quantity) {
        System.out.printf("""
                        You have ordered %s x %d that costs €%.2f.\
                        
                        Your current total is: €%.2f\

                        Would you like to order something else or checkout?

                        Enter 'q' to exit and proceed to checkout.\
                        
                        Enter any key to continue the order.""", newItem, quantity, itemPrice, totalExpense);

        System.out.println("\nYour order currently consists of following items:");
        for (Map.Entry<String, Double> order : map.entrySet()) {
            System.out.printf("%s x %d %s €%.2f\n", order.getKey(), quantity, "-".repeat(4), order.getValue() * quantity);
        }
    }
}