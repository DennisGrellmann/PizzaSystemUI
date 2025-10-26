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

    // generate unique store id
    private int generateUniqueStoreId(int min, int max) {
        Random rand = new Random();
        while (true) {
            int id = min + rand.nextInt(max - min + 1);
            if (!storeIdExists(id)) return id;
        }
    }

    // check if store id exists
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
        System.out.println("\nPizza Ordering System");
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
            System.out.println("\nManage Stores");
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
            } else if (choice == 3) {
                if (storeCount == 0) System.out.println("No stores created.");
                else {
                    for (int i = 0; i < storeCount; i++) {
                        System.out.println((i==activeIndex ? "* " : "  ") + "#" + stores[i].getStoreId() + "  " + stores[i].getStoreName());
                    }
                }
            } else if (choice == 4) {
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
    
    // method to view pizza menu
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
    
    //pizza base price retrieval
    private double getPizzaBasePrice(String pizzaType, char pizzaSize) {
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
    
    //method for placing new order
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

        PizzaOrder pizza = new PizzaOrder(orderId, type, size, qty, cheese, olives, gb, sd);
        if (store.addOrder(pizza)) {
            System.out.println("Order added: " + pizza);
        } else {
            System.out.println("Failed to add order.");
        }
    }

    // generate unique order id within store
    private int generateUniqueOrderId(PizzaStore store) {
        Random r = new Random();
        while (true) {
            int orderid = 1 + r.nextInt(9999);
            if (!orderIdExistsInStore(store, orderid)) return orderid;
        }
    }

    // check if order id exists in store
    private boolean orderIdExistsInStore(PizzaStore store, int orderId) {
        for (PizzaOrder o : store.getOrders()) {
            if (o.getOrderId() == orderId) return true;
        }
        return false;
    }
   
    //method to compare two pizza orders
    private void compareTwoOrders() {
        System.out.println("\n-- Compare Two Pizza Orders (by total price) --");

        // first order
        String t1 = readSelection("First pizza type (Margherita/Neapolitan/Marinara): ",
                                new String[]{"Margherita","Neapolitan","Marinara"});
        char s1 = SizeSelectionCheck();
        int q1 = readInt("Enter quantity for first pizza: ", 1, 10);

        // second order
        String t2 = readSelection("Second pizza type (Margherita/Neapolitan/Marinara): ",
                                new String[]{"Margherita","Neapolitan","Marinara"});
        char s2 = SizeSelectionCheck();
        int q2 = readInt("Enter quantity for second pizza: ", 1, 10);

        // total before tax and add-ons
        double p1 = getPizzaBasePrice(t1, s1) * q1;
        double p2 = getPizzaBasePrice(t2, s2) * q2;

        // output
        System.out.print("Comparision before tax and add-ons:\n");
        System.out.printf("First:  %s (%c) x%d  $%.2f%n", t1, s1, q1, p1);
        System.out.printf("Second: %s (%c) x%d  $%.2f%n", t2, s2, q2, p2);

        // verdict
        if (p1 == p2) 
            System.out.println("Both orders cost the same.");
        else if (p1 < p2) 
            System.out.println("The first order is cheaper.");
        else 
            System.out.println("The first order is more expensive.");
    }

    //cancel previous order
    private void cancelLastOrder() {
        PizzaStore store = stores[activeIndex];
        if (store.cancelLastOrder()) System.out.println("Last order cancelled.");
        else System.out.println("No orders to cancel.");
    }

    //Simulate daily special (doesnt add it to order to not break encapsulation)
    private void simulateDailySpecial() {
    System.out.println("\n-- Simulating Daily Special --");

    // 1. Pick a random special offer
    int offer = (int)(Math.random() * 3);
    String offerMessage = "";
    if (offer == 0) {
        offerMessage = "Buy one, get the second pizza half price!";
    } else if (offer == 1) {
        offerMessage = "20% off your total order!";
    } else { // offer == 2
        offerMessage = "Free side with every LARGE pizza!";
    }

    System.out.println("Today's Special Offer: " + offerMessage);
    System.out.println();

    String pizzaType = readSelection(
        "Choose pizza (Margherita/Neapolitan/Marinara): ",
        new String[]{"Margherita","Neapolitan","Marinara"}
    );

    char pizzaSize = SizeSelectionCheck(); 

    int quantity = readInt("How many pizzas (1-10): ", 1, 10);

    boolean addCheese = readYesNo("Add extra cheese for $1.50 per pizza?");
    boolean addOlives = readYesNo("Add extra olives for $1.00 per pizza?");

    int garlicBreadCount = 0;
    int softDrinkCount = 0;

    if (offer == 2 && (pizzaSize == 'L')) {
        // Free side with large pizza
        System.out.println("Your LARGE pizza qualifies for a FREE side!");
        System.out.println("1) Garlic bread");
        System.out.println("2) Soft drink");
        int sideChoice = readInt("Choose your free side (1-2): ", 1, 2);
        if (sideChoice == 1) {
            garlicBreadCount = 1;
            System.out.println("Free garlic bread added.");
        } else {
            softDrinkCount = 1;
            System.out.println("Free soft drink added.");
        }

        // Optionally buy extra
        if (readYesNo("Would you like to add EXTRA garlic bread for $4.00 each?")) {
            garlicBreadCount += readInt("How many EXTRA garlic breads: ", 0, 10);
        }
        if (readYesNo("Would you like to add EXTRA soft drinks for $3.00 each?")) {
            softDrinkCount += readInt("How many EXTRA soft drinks: ", 0, 10);
        }

    } else {
        // Normal sell if no free side is triggered
        if (readYesNo("Add garlic bread for $4.00 each?")) {
            garlicBreadCount = readInt("How many garlic breads: ", 1, 10);
        }
        if (readYesNo("Add soft drinks for $3.00 each?")) {
            softDrinkCount = readInt("How many soft drinks: ", 1, 10);
        }
    }

    //Calculate base pizza cost
    double basePerPizza = getPizzaBasePrice(pizzaType, pizzaSize); 
    double pizzaCost = basePerPizza * quantity;

    //Calculate add-ons cost
    double addOnsCost = 0.0;
    if (addCheese) {
        addOnsCost += 1.50 * quantity;
    }
    if (addOlives) {
        addOnsCost += 1.00 * quantity;
    }
    addOnsCost += 4.00 * garlicBreadCount;
    addOnsCost += 3.00 * softDrinkCount;

    //Subtotal before discount/tax
    double preTaxSubtotal = pizzaCost + addOnsCost;

    // Work out discount from special
    double discount = 0.0;
    if (offer == 0) {
        // Buy one get second half price
        if (quantity >= 2) {
            discount = basePerPizza * 0.5;
        }
    } else if (offer == 1) {
        // 20% off total order (before tax)
        discount = preTaxSubtotal * 0.20;
    }

    // Apply discount
    double discountedSubtotal = preTaxSubtotal - discount;
    if (discountedSubtotal < 0) {
        discountedSubtotal = 0;
    }

    //Tax and total
    double tax = discountedSubtotal * 0.10; // 10% tax
    double finalTotal = discountedSubtotal + tax;

    // convert to string 
    String sAddOns           = String.format("%.2f", addOnsCost);
    String sPreTaxSubtotal   = String.format("%.2f", preTaxSubtotal);
    String sDiscount         = String.format("%.2f", discount);
    String sAfterDiscount    = String.format("%.2f", discountedSubtotal);
    String sTax              = String.format("%.2f", tax);
    String sFinalTotal       = String.format("%.2f", finalTotal);

    //Print receipt for simulation 
    System.out.println("\n---- Simulated Receipt (Not Saved) ----");
    System.out.println(" Pizza Type: " + pizzaType);
    System.out.println(" Pizza Size: " + pizzaSize);
    System.out.println(" Quantity:   " + quantity);
    System.out.println(" Add-ons Cost:            $" + sAddOns);
    System.out.println(" Subtotal (before disc.): $" + sPreTaxSubtotal);
    System.out.println(" Special Discount:       -$" + sDiscount);
    System.out.println(" Subtotal After Disc.:    $" + sAfterDiscount);
    System.out.println(" Tax (10%):               $" + sTax);
    System.out.println(" TOTAL DUE:               $" + sFinalTotal);
    System.out.println("---------------------------------------");
    System.out.println("(This was only a simulation. No order was added.)");
}

    // method to modify last order
    private void modifyLastOrder() {
    PizzaStore store = stores[activeIndex];
    PizzaOrder last = store.getLastOrder();

    if (last == null) {
        System.out.println("No orders to modify.");
        return;
    }

    System.out.println("\n-- Modify Last Order --");
    System.out.println("Current last order: " + last);
    System.out.println("You will now re-enter ALL values for this order.");

    // Re-collect full order details (same as placing new order)
    String newType = readSelection(
        "Pizza type (Margherita/Neapolitan/Marinara): ",
        new String[]{"Margherita","Neapolitan","Marinara"}
    );

    char newSize = SizeSelectionCheck();

    int newQty = readInt("Quantity of pizzas (1-10): ", 1, 10);

    boolean newCheese = readYesNo("Add extra cheese ($1.50 per pizza)?");
    boolean newOlives = readYesNo("Add extra olives ($1.00 per pizza)?");

    int newGb = readInt("Garlic breads (each $4): ", 0, 10);
    int newSd = readInt("Soft drinks (each $3): ", 0, 10);

    // keep the orderId 
    int sameId = last.getOrderId();

    PizzaOrder replacement = new PizzaOrder(
        sameId,
        newType,
        newSize,
        newQty,
        newCheese,
        newOlives,
        newGb,
        newSd
    );

    boolean ok = store.replaceLastOrder(replacement);

    if (ok) {
        System.out.println("Order updated: " + replacement);
    } else {
        System.out.println("Failed to modify order.");
    }
}

    //view sales statistics
    private void viewSalesStatistics() {
        stores[activeIndex].printSalesStatistics();
    }

    //view all stored orders
    private void viewAllStoredOrders() {
        stores[activeIndex].listAllOrders();
    }

    //save store data to file
    private void saveStoreData() {
        System.out.print("Enter filename to save (e.g., pizzasystem.txt): ");
        String name = scanner.nextLine().trim();
        if (name.length()==0) { System.out.println("Cancelled."); return; }
        try {
            stores[activeIndex].SaveToFile(new File(name));
            System.out.println("Saved to " + name);
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    //Prompt to overwrite current store OR create a new store (if space). If no space, must overwrite.
    private void loadStoreData() {
        System.out.print("Enter filename to load: ");
        String name = scanner.nextLine().trim();
        if (name.length()==0) { System.out.println("Cancelled."); return; }

        boolean haveSpace = storeCount < MAX_STORES;

        String mode;
        if (!haveSpace) {
            System.out.println("No free store slots available. The file will be loaded by OVERWRITING the current active store.");
            mode = "o";
        } else {
            mode = readSelection("Load mode: overwrite current (o) or create new store (n)? ", new String[]{"o","n"});
        }

        try {
            if (mode.equalsIgnoreCase("o")) {
                // Overwrite current store, preserving its ID/name
                PizzaStore loaded = PizzaStore.loadFromFile(
                    new File(name),
                    stores[activeIndex].getStoreId(),
                    stores[activeIndex].getStoreName()
                );
                stores[activeIndex] = loaded;
                System.out.println("Store data loaded into current active store (#" +
                    loaded.getStoreId() + ").");
            } else {
                // Create new store slot
                int id = generateUniqueStoreId(1000, 9999);
                PizzaStore loaded = PizzaStore.loadFromFile(new File(name), id, "Loaded Store");
                stores[storeCount++] = loaded;
                activeIndex = storeCount - 1;
                System.out.println("Loaded store as NEW store #" + loaded.getStoreId() + " (active).");
            }
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // main run loop
    public void run() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = readInt("Choose 1-12: ", 1, 12);
            switch (choice) {
                case 1: manageStoresMenu(); break;
                case 2: viewPizzaMenu(); break;
                case 3: placeNewOrder(); break;
                case 4: compareTwoOrders(); break;
                case 5: simulateDailySpecial(); break;
                case 6: modifyLastOrder(); break;
                case 7: cancelLastOrder(); break;
                case 8: viewSalesStatistics(); break;
                case 9: viewAllStoredOrders(); break;
                case 10: saveStoreData(); break;
                case 11: loadStoreData(); break;
                case 12: exit = true; System.out.println("Goodbye!"); break;
            }
        }
    }
    
    // main entry point
    public static void main(String[] args) {
        new PizzaSystemUI().run();
    }
}

