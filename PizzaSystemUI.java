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
    // Manage stores, create, switch, list, delete
    private void manageStoresMenu() {
        while (true) {
            System.out.println("\n=== Manage Stores ===");
            System.out.println("1. Create new Store");
            System.out.println("2. Switch Active Store");
            System.out.println("3. List all Stores");
            System.out.println("4. Delete all Stores");
            System.out.println("5. Return to Main Menu");
            int choice = readInt("Select an option (1-5): ", 1, 5);
            if (choice == 1) {
                if (storeCount >= MAX_STORES) {
                    System.out.println("Maximum number of stores reached.");
                    continue;
                }
                String name = "Store " + (storeCount + 1);
                System.out.print("Enter store ID (or enter for random): ");
                String line = scanner.nextLine().trim();
                int id;
                if (line.isEmpty()) {
                    id = generateUniqueStoreId(1, 99);
                } else {
                    try {
                        id = Integer.parseInt(line);
                        if (storeIdExists(id)) {
                            int newid = generateUniqueStoreId(1, 99);
                            System.out.println("Store ID already exists. using Generated unique ID ." + newid);
                            id = newid;
                        }
                    } catch (NumberFormatException e) {
                        id = generateUniqueStoreId(1, 99);
                        System.out.println("Invalid input. using Generated unique ID ." + id);
                    }
                }
                stores[storeCount++] = new PizzaStore(id, name);
                activeIndex = storeCount - 1;
                System.out.println("Created and switched to new store: " + id + " - " + name);
            } else if (choice == 2) {
                if (storeCount == 0) {
                    System.out.println("No stores available to switch.");
                    continue;
                }
                int id = readInt("Enter store ID to activate: ", 1, 999999);
                int found = -1;
                for (int i = 0; i < storeCount; i++) if (stores[i].getStoreId() == id) { found = i; break; }
                if (found == -1) System.out.println("Store ID not found.");
                else { activeIndex = found; System.out.println("Active store is now #" + id + "."); }
            } else if (c == 3) {
                if (storeCount == 0) System.out.println("No stores created.");
                else {
                    for (int i = 0; i < storeCount; i++) {
                        System.out.println((i==activeIndex ? "* " : "  ") + "#" + stores[i].getStoreId() + "  " + stores[i].getStoreName());
                    }
                }
            } else if (c == 4) {
                boolean ok = readYesNo("This will DELETE ALL stores. Are you sure");
                if (ok) {
                    stores = new PizzaStore[MAX_STORES];
                    storeCount = 0;
                    activeIndex = -1;
                    atLeastOneStore();
                    System.out.println("All stores deleted. A default store has been created and set active.");
                } else {
                    System.out.println("Delete all stores cancelled.");
                }
            } else {
                return;
            }
        }
    }
    private void viewPizzaMenu() {
        System.out.println("\n--- Pizza Menu ---");
        System.out.println("Margherita - S:$8.00  M:$10.00  L:$12.00");
        System.out.println("Neapolitan - S:$9.00  M:$11.00  L:$13.50");
        System.out.println("Marinara   - S:$9.50  M:$11.50  L:$14.00");
        System.out.println("Add-ons:");
        System.out.println("  Extra cheese (per pizza): $" + PizzaOrder.extraCheesePrice);
        System.out.println("  Extra olives (per pizza): $" + PizzaOrder.extraOlivesPrice);
        System.out.println("  Garlic Bread (each): $" + PizzaOrder.garlicBreadPrice);
        System.out.println("  Soft Drink (each): $" + PizzaOrder.softDrinkPrice);
        System.out.println("Tax rate: " + (int)(PizzaOrder.taxRate*100) + "%");
    }

    private double getPizzaBasePrice() {
        String type = pizzaType.toLowerCase();
        switch (type) {
            case "margherita":
                return (pizzaSize == 'S' ? 8.00 : pizzaSize == 'M' ? 10.00 : 12.00);
            case "neapolitan":
                return (pizzaSize == 'S' ? 9.00 : pizzaSize == 'M' ? 11.00 : 13.50);
            case "marinara":
                return (pizzaSize == 'S' ? 9.50 : pizzaSize == 'M' ? 11.50 : 14.00);
            default:
                return 0.0;
        }
    }

    private void placeNewOrder() {
        PizzaStore store = stores[activeIndex];
        if (store.isFull()) {
            System.out.println("Order list is full for this store (max " + PizzaStore.MAX_ORDERS + ").");
            return;
        }
        String type = readSelection("Pizza type (Margherita/Neapolitan/Marinara): ",
                                 new String[]{"Margherita","Neapolitan","Marinara"});
        char size = SizeSelectionCheck();
        int qty = readInt("Quantity of pizzas (1-10): ", 1, 10);

        boolean cheese = readYesNo("Add extra cheese ($1.50 per pizza)?");
        boolean olives = readYesNo("Add extra olives ($1.00 per pizza)?");
        int gb = readInt("Garlic breads (each $4): ", 0, 10);
        int sd = readInt("Soft drinks (each $3): ", 0, 10);

        // Order ID input with optional randomness
        System.out.print("Enter desired order ID (blank for auto): ");
        String raw = scanner.nextLine().trim();
        int orderId;
        if (raw.length() == 0) {
            orderId = generateUniqueOrderId(store);
        } else {
            try {
                orderId = Integer.parseInt(raw);
                if (orderIdExistsInStore(store, orderId)) {
                    int newid = generateUniqueOrderId(store);
                    System.out.println("Duplicate Order ID. Using generated ID: " + newid);
                    orderId = newid;
                }
            } catch (NumberFormatException e) {
                orderId = generateUniqueOrderId(store);
                System.out.println("Invalid ID. Using generated ID: " + orderId);
            }
        }

        PizzaOrder po = new PizzaOrder(orderId, type, size, qty, cheese, olives, gb, sd);
        if (store.addOrder(po)) {
            System.out.println("Order added: " + po);
        } else {
            System.out.println("Failed to add order.");
        }
    }


    private int generateUniqueOrderId(PizzaStore store) {
        Random r = new Random();
        while (true) {
            int orderid = 1 + r.nextInt(9999);
            if (!orderIdExistsInStore(store, orderid)) return orderid;
        }
    }

    private boolean orderIdExistsInStore(PizzaStore store, int orderId) {
        for (PizzaOrder o : store.getOrders()) {
            if (o.getOrderId() == orderId) return true;
        }
        return false;
    }
}