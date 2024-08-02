package org.oop;

import java.util.Map;
import java.util.Scanner;

public class Order {

    public Order() {

    }

    public void generateOrder(Map<String, Double> map) {
        for (Map.Entry<String, Double> order : map.entrySet()) System.out.printf("%s %s €%.2f\n", order.getKey(), "-".repeat(4), order.getValue());
    }

    public void generateOrderStatement(Map<String, Double> map) {
        System.out.println("\nYour order currently consists of following items:");
        for (Map.Entry<String, Double> order : map.entrySet()) System.out.printf("%s %s €%.2f\n", order.getKey(), "-".repeat(4), order.getValue());
        System.out.println("-".repeat(20) +"\nEnter any key to order something else.");
        System.out.println("Enter 'q' to continue.");
    }

    public boolean initialDecision(Scanner scanner) {
        var decision = false;
            while (true) {
                var decisionOnOrder = scanner.nextLine();
                if (decisionOnOrder.equalsIgnoreCase("y")) {
                    decision = true;
                    return decision;
                } else if (decisionOnOrder.equalsIgnoreCase("n")) {
                    System.out.println("Goodbye!");
                    return decision;
                } else System.err.println("Wrong input. Please enter either Y or N.");

            }
        }
}



