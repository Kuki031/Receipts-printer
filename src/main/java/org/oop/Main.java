package org.oop;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final Menu menu = Menu.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final int THREAD_DELAY = (int) (1000 + Math.floor(Math.random() * 1500));

    private static final Map<String, Double> orderList = new HashMap<String, Double>();
    private static double totalExpense = 0.0;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Would you like to place the order?(Y/N)");
        boolean decision;

        while (true) {
            String decisionOnOrder = scanner.nextLine();
            if (decisionOnOrder.equalsIgnoreCase("y")) {
                decision = true;
                break;
            } else if (decisionOnOrder.equalsIgnoreCase("n")) {
                System.out.println("Goodbye!");
                decision = false;
                break;
            } else {
                System.err.println("Wrong input. Please enter either Y or N.");
            }
        }

        if (decision) {
            boolean continueOrder = true;
            while (continueOrder) {

                String wantsToContinueTheOrder;
                boolean hasDecidedOnMenu = false;
                String menuDecision = "";
                Map<String, Double> selectedMenu = new HashMap<>();
                String chosenItem;
                String decidedItemFromMenu;
                double itemPrice = 0.0;
                int quantity = 1;

                System.out.println("Menu will load shortly... Please wait.");
                Thread.sleep(THREAD_DELAY);

                while (true) {
                    System.out.println("Which menu would you like to list? Please enter 'D' for drinks, 'B' for burgers or 'S' for side items!");
                    menuDecision = scanner.nextLine();
                    switch (menuDecision.toLowerCase()) {
                        case "d" -> {
                            menu.listMenu(menu.getDrinksMap(), "drinks");
                            selectedMenu = menu.getDrinksMap();
                            hasDecidedOnMenu = true;
                        }
                        case "b" -> {
                            menu.listMenu(menu.getBurgersMap(), "burgers");
                            selectedMenu = menu.getBurgersMap();
                            hasDecidedOnMenu = true;
                        }
                        case "s" -> {
                            menu.listMenu(menu.getSideMap(), "side items");
                            selectedMenu = menu.getSideMap();
                            hasDecidedOnMenu = true;
                        }
                        default -> {
                            hasDecidedOnMenu = false;
                        }
                    }
                    if (hasDecidedOnMenu) break;
                    else System.err.println("Wrong input. Enter either D, B or S.");
                }

                while (true) {
                    System.out.println("Please enter an item name from the selected menu list.");
                    decidedItemFromMenu = scanner.nextLine().toLowerCase();
                    chosenItem = menu.decideOnItem(decidedItemFromMenu, selectedMenu);
                    if (chosenItem.isBlank() || chosenItem.isEmpty()) {
                        System.err.println("Item in the menu does not exist. Please try again.");
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
                                System.err.println("Invalid input. Must be a positive integer value.");
                            }
                        }
                        orderList.put(chosenItem + " x " + quantity, itemPrice);
                        break;
                    }
                }

                generateInfoStatement(orderList, chosenItem, quantity, itemPrice, totalExpense);
                wantsToContinueTheOrder = scanner.nextLine();
                if (wantsToContinueTheOrder.equalsIgnoreCase("q")) {
                    while (true) {
                        System.out.println("Enter 'Y' to edit your order.\nEnter any key to continue to checkout.");
                        String wantsToEditOrder = scanner.nextLine();
                        if (wantsToEditOrder.equalsIgnoreCase("y")) {
                            System.out.println("Please enter the name of the item you would like to remove.");
                            Thread.sleep(500);

                            generateOrder(orderList);
                            String itemToRemove = scanner.nextLine().toLowerCase();
                            String removedItem = menu.modifyOrderList(orderList, itemToRemove);
                            if (removedItem != null) {
                                System.out.printf("Item %s removed from the order.\n", removedItem);
                                totalExpense -= menu.getPriceToDeduct();
                                if (totalExpense < 0) totalExpense = 0;
                            } else {
                                System.err.println("Item not found in the order.");
                            }
                            Thread.sleep(1000);

                            generateOrder(orderList);
                        } else {
                            continueOrder = false;
                            break;
                        }
                    }
                }
            }
            System.out.println("Creating repository on C partition.");
            createEmptyReceiptRepository();
            if (Repository.getInstance().isDoesExist()) {
                generateReceipt(Repository.getInstance());
            }
        }
    }



    private static void generateOrder(Map<String, Double> map) {
        for (Map.Entry<String, Double> order : map.entrySet()) {
            System.out.printf("%s %s €%.2f\n", order.getKey(), "-".repeat(4), order.getValue());
        }
    }

    private static void generateReceipt(Repository repository) throws IOException {
        repository.open("C:\\Receipts");
        Receipt receipt = new Receipt(orderList, totalExpense);
        receipt.writeReceipt();
        repository.open("C:\\Receipts\\receipt#"+receipt.getReceiptID()+".txt");
    }

    private static void generateInfoStatement(Map<String, Double> map, String newItem, int quantity, double itemPrice, double totalExpense) {
        System.out.printf("""
                You have ordered %s x %d that costs €%.2f.\
                                        
                Your current total is: €%.2f\
                \n
                %s                     
                """, newItem, quantity, itemPrice, totalExpense, "-".repeat(20));

        System.out.println("\nYour order currently consists of following items:");
        for (Map.Entry<String, Double> order : map.entrySet()) {
            System.out.printf("%s %s €%.2f\n", order.getKey(), "-".repeat(4), order.getValue());
        }
        System.out.println("-".repeat(20) +"\nEnter any key to order something else.");
        System.out.println("Enter 'q' to continue.");
    }

    private static void createEmptyReceiptRepository() {
        Repository.getInstance();
    }
}
