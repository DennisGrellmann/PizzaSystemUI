// Assignment 2 SENG110 
// Pizza Ordering System UI
// Dennis Grellmann c3468127 
// 12-2pm Thursday ES105

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class PizzaSystemUI {
    private Scanner scanner;
    private PizzaStore[] stores;
    private int storeCount;
    private int activeIndex;

    public static final int MAX_STORES = 5;

    public PizzaSystemUI() {
        scanner = new Scanner(System.in);
        stores = new PizzaStore[MAX_STORES];
        storeCount = 0;
        activeIndex = -1;
        // create a store if no default is found
        atLeastOneStore();
    }

    // method to ensure at least one store exists and generate a default if not
    private void atLeastOneStore() {
        if (storeCount == 0) {
            int id = generateUniqueStoreId(1,99);
            stores[0] = new PizzaStore(id, "Default Store");
            storeCount = 1;
            activeIndex = 0;
            System.out.println("No store exists. Created a new one with "+ id + " ID.");
        }
    }

    private int generateUniqueStoreId(int min, int max) {
        Random rand = new Random();
        while (true) {
            int id = min + rand.nextInt(max - min + 1);
            if (!storeIdExists(id)) return id;
        }
    }

    private boolean storeIdExists(int id) {
        for (int i = 0; i < storeCount; i++) {
            if (stores[i].getStoreId() == id) return true;
        }
        return false;
    }
    // read integer from user with min and max validation
    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer input. Please try again.");
            }
        }
    }

    // read selection from user and do input validation
    private String readSelection(String prompt, String[] selection) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().toLowerCase();
            for (String s : selection) {
                if (line.equals(s.toLowerCase())) return s;
            }
            System.out.println("Invalid input. Valid options are: ");
            for (int i = 0; i < selection.length; i++) {
                System.out.print((i > 0 ? ", " : "") + selection[i]);
            }
            System.out.println();
            }
    }

    // Read a yes/no response from user
    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("yes") || line.equals("y")) {
                return true;
            } else if (line.equals("no") || line.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
    // Size Selection and using readSelection for validation
    private char SizeSelectionCheck() {
        while (true) {
            String s = readSelection("Size (S/M/L): ", new String[]{"s","m","l"});
            return Character.toUpperCase(s.charAt(0));
        }
    }

    // main menu 
    private void printMainMenu() {
        System.out.println("\n=== Pizza Ordering System ===");
        System.out.println("Active Store: " + (activeIndex >= 0 ? stores[activeIndex].getStoreName() : "None"));
        System.out.println("1. Manage Stores");
        System.out.println("2. View Pizza Menu");
        System.out.println("3. Place New Pizza Order");
        System.out.println("4. Compare two Pizza Orders");
        System.out.println("5. Simulate Daily Special");
        System.out.println("6. Modify Last Order");
        System.out.println("7. Cancel Last Order");
        System.out.println("8. View Sales Statistics");
        System.out.println("9. View All Stored Orders");
        System.out.println("10. Save Store Data");
        System.out.println("11. Load Store Data");
        System.out.println("12. Exit");
    }

    private void manageStoresMenu() {
        while (true)
    }
}