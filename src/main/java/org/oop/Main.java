package org.oop;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final Menu menu = Menu.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Double> orderList = new HashMap<String, Double>();
    private static double totalExpense;
    private static boolean continueOrder = true;

    private static final Repository repository= Repository.getInstance();
    private static Order order;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Would you like to place the order?(Y/N)");
        order = new Order();
        var decision = order.initialDecision(scanner);
        if (decision) orderProcedure();
    }


    public static void showAvailableItems() {
        while (true) {
            System.out.println("Please enter an item name from the selected menu list.");
            var decidedItemFromMenu = scanner.nextLine().toLowerCase();
            var chosenItem = menu.decideOnItem(decidedItemFromMenu, menu.getChosenMap());
            if (chosenItem.isBlank() || chosenItem.isEmpty()) System.err.println("Item in the menu does not exist. Please try again.");
             else {
                double itemPrice;
                int quantity;
                while (true) {
                    System.out.printf("Enter the quantity for item %s: ", chosenItem);
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity <= 0) throw new NumberFormatException();

                        itemPrice = menu.readItemPrice(chosenItem, menu.getChosenMap()) * quantity;
                        totalExpense += itemPrice;
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid input. Must be a positive integer value.");
                    }
                }
                orderList.put(chosenItem + " x " + quantity, itemPrice);
                return;
            }
        }
    }

    public static void editOrder() throws InterruptedException {
        while (true) {
            System.out.println("Enter 'Y' to edit your order.\nEnter any key to continue to checkout.");
            var wantsToEditOrder = scanner.nextLine();

            if (wantsToEditOrder.equalsIgnoreCase("y")) {
                System.out.println("Please enter the name of the item you would like to remove.");
                Thread.sleep(500);

                order.generateOrder(orderList);
                var itemToRemove = scanner.nextLine().toLowerCase();
                var removedItem = menu.modifyOrderList(orderList, itemToRemove);
                if (removedItem != null) {
                    System.out.printf("Item %s removed from the order.\n", removedItem);
                    totalExpense -= menu.getPriceToDeduct();
                    if (totalExpense < 0) totalExpense = 0;
                } else System.err.println("Item not found in the order.");

                order.generateOrder(orderList);

            } else {
                continueOrder = false;
                break;
            }
        }
    }

    private static void orderProcedure() throws InterruptedException, IOException {
            while (continueOrder) {

                var wantsToContinueTheOrder = "";
                System.out.println("Menu will load shortly... Please wait.");

                menu.showAvailableMenus(scanner);
                showAvailableItems();
                order.generateOrderStatement(orderList);
                wantsToContinueTheOrder = scanner.nextLine();

                if (wantsToContinueTheOrder.equalsIgnoreCase("q")) editOrder();
            }
            System.out.println("Creating repository on C partition.");
            var receipt = new Receipt(orderList, totalExpense);
            receipt.generateReceipt(repository, receipt);
            var printer = new Printer("C:\\Receipts\\receipt#" + receipt.getReceiptID() + ".txt");
            printer.startPrinting();
        }
    }

