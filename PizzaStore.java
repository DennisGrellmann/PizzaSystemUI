// Assignment 2 SENG110 
// Pizza Ordering System UI
// Dennis Grellmann c3468127 
// 12-2pm Thursday ES105
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PizzaStore {
    // Store attributes
    private int storeId;
    private String storeName;
    private int orderCount;
    private PizzaOrder[] orders;

    // Sales statistics
    private double dailyTotalSales;
    private int totalOrderCount;

    // Pizza counts
    private int margheritaSmall, margheritaMedium, margheritaLarge;
    private int neapolitanSmall, neapolitanMedium, neapolitanLarge;
    private int marinaraSmall, marinaraMedium, marinaraLarge;

    // Add-on counts
    private int extraCheeseCount;
    private int extraOlivesCount;
    private int garlicBreadTotal;
    private int softDrinkTotal;

    // Max Orders and other metrics
    public static final int MAX_ORDERS = 10;
    public PizzaStore(int storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.orders = new PizzaOrder[MAX_ORDERS];
        this.orderCount = 0;
        this.dailyTotalSales = 0.0;
        this.totalOrderCount = 0;
    }

    // Getters for store attributes
    public int getStoreId() { return storeId; }
    public String getStoreName() { return storeName; }
    public int getOrderCount() { return orderCount; }
    public double getDailyTotalSales() { return dailyTotalSales; }
    public int getTotalOrderCount() { return totalOrderCount; }

    // Check if store is full
    public boolean isFull() {
        return orderCount >= MAX_ORDERS;
    }

    // Add a new order to the store
    public boolean addOrder(PizzaOrder order) {
        if (isFull() || order == null) return false;
        orders[orderCount++] = order;
        applyOrderToSats(order, +1);
        return true;
    }

    // Cancel the last order
    public boolean cancelLastOrder() {
        if (orderCount == 0) return false;
        PizzaOrder last = orders[orderCount - 1];
        applyOrderToSats(last, -1);
        orders[orderCount - 1] = null;
        orderCount--;
        return true;
    }

    // Replace the last order with a new one
    public boolean replaceLastOrder(PizzaOrder replacement) {
        if (orderCount == 0 || replacement == null) return false;
        PizzaOrder lastOrder = orders[orderCount - 1];
        applyOrderToSats(lastOrder, -1);
        orders[orderCount - 1] = replacement;
        applyOrderToSats(replacement, 1);
        return true; 
    }

    // Get the last order
    public PizzaOrder getLastOrder() {
        if (orderCount == 0) return null;
        return orders[orderCount - 1];
    }

    // Get a copy of all orders
    public PizzaOrder[] getOrders() {
        PizzaOrder[] copy = new PizzaOrder[orderCount];
        for (int i = 0; i < orderCount; i++) copy[i] = orders[i];
        return copy;
    }

    // Apply order to sales statistics
    private void applyOrderToSats(PizzaOrder order, int factor) {
        if (order == null || factor == 0) return;
        String type = order.getPizzaType().toLowerCase();
        char size = order.getPizzaSize();
        int qty = order.getQuantity();

        if (type.equals("margherita")) {
            if (size == 'S') margheritaSmall += factor * qty;
            else if (size == 'M') margheritaMedium += factor * qty;
            else if (size == 'L') margheritaLarge += factor * qty;
        } else if (type.equals("neapolitan")) {
            if (size == 'S') neapolitanSmall += factor * qty;
            else if (size == 'M') neapolitanMedium += factor * qty;
            else if (size == 'L') neapolitanLarge += factor * qty;
        } else if (type.equals("marinara")) {
            if (size == 'S') marinaraSmall += factor * qty;
            else if (size == 'M') marinaraMedium += factor * qty;
            else if (size == 'L') marinaraLarge += factor * qty;
        }

        if (order.hasExtraCheese()) { extraCheeseCount += factor * qty; }
        if (order.hasExtraOlives()) { extraOlivesCount += factor * qty; }
        garlicBreadTotal += factor * order.getGarlicBreadCount();
        softDrinkTotal += factor * order.getSoftDrinkCount();
    }

    // Save file'
    public void SaveToFile(File f) throws IOException {
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            pw = new PrintWriter(bw);
            pw.println(format2(dailyTotalSales) + " ," +
                       totalOrderCount + " ," +
                       margheritaSmall + " ," + margheritaMedium + " ," + margheritaLarge + " ," +
                       neapolitanSmall + " ," + neapolitanMedium + " ," + neapolitanLarge + " ," +
                       marinaraSmall + " ," + marinaraMedium + " ," + marinaraLarge + " ," +
                       extraCheeseCount + " ," + extraOlivesCount + " ," +
                       garlicBreadTotal + " ," + softDrinkTotal);
            pw.println("# Orders :");
            for (int i = 0; i < orderCount; i++) {
                pw.println(orders[i].toSave());
            }
        } finally {
            if (pw != null) pw.close();
            else if (bw != null) bw.close();
        }
    }

    // Load from file
    public static PizzaStore loadFromFile(File f, int fallbackStoreId, String fallbackName) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String header = br.readLine();
            if (header == null) throw new IOException("Empty file.");
            PizzaStore store = new PizzaStore(fallbackStoreId, fallbackName);
            String[] h = header.split("\\s*,\\s*");
            int idx = 0;
            store.dailyTotalSales = parseDoubleSafe(h, idx++);
            store.totalOrderCount = (int) parseDoubleSafe(h, idx++);
            store.margheritaSmall  = (int) parseDoubleSafe(h, idx++);
            store.margheritaMedium = (int) parseDoubleSafe(h, idx++);
            store.margheritaLarge  = (int) parseDoubleSafe(h, idx++);
            store.neapolitanSmall  = (int) parseDoubleSafe(h, idx++);
            store.neapolitanMedium = (int) parseDoubleSafe(h, idx++);
            store.neapolitanLarge  = (int) parseDoubleSafe(h, idx++);
            store.marinaraSmall    = (int) parseDoubleSafe(h, idx++);
            store.marinaraMedium   = (int) parseDoubleSafe(h, idx++);
            store.marinaraLarge    = (int) parseDoubleSafe(h, idx++);
            store.extraCheeseCount = (int) parseDoubleSafe(h, idx++);
            store.extraOlivesCount = (int) parseDoubleSafe(h, idx++);
            store.garlicBreadTotal = (int) parseDoubleSafe(h, idx++);
            store.softDrinkTotal   = (int) parseDoubleSafe(h, idx++);

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) continue;
                if (line.startsWith("#")) continue;
                if (store.orderCount >= MAX_ORDERS) break;
                PizzaOrder po = PizzaOrder.fromSave(line);
                store.orders[store.orderCount++] = po;
            }
            return store;
        } finally {
            if (br != null) br.close();
        }
    }

        
        private static double parseDoubleSafe(String[] arr, int idx) {
        if (idx >= arr.length) return 0.0;
        try { return Double.parseDouble(arr[idx]); }
        catch (Exception e) { return 0.0; }
    }

    // Format double to 2 decimal places
    private static String format2(double v) {
        return String.format("%.2f", v);
    }
        // Print sales statistics
        public void printSalesStatistics() {
        System.out.println("Sales Statistics for Store #" + storeId + " - " + storeName);
        System.out.println("  Daily Total Sales: $" + format2(dailyTotalSales));
        System.out.println("  Total Orders: " + totalOrderCount);
        System.out.println("Pizza counts:");
        System.out.println("  Margherita  S:" + margheritaSmall + "  M:" + margheritaMedium + "  L:" + margheritaLarge);
        System.out.println("  Neapolitan  S:" + neapolitanSmall + "  M:" + neapolitanMedium + "  L:" + neapolitanLarge);
        System.out.println("  Marinara    S:" + marinaraSmall + "  M:" + marinaraMedium + "  L:" + marinaraLarge);
        System.out.println("Add-ons sold:");
        System.out.println("  Extra Cheese (per pizza): " + extraCheeseCount);
        System.out.println("  Extra Olives (per pizza): " + extraOlivesCount);
        System.out.println("  Garlic Bread: " + garlicBreadTotal);
        System.out.println("  Soft Drinks: " + softDrinkTotal);
    }

    // List all stored orders
    public void listAllOrders() {
        if (orderCount == 0) {
            System.out.println("No orders stored for this store.");
            return;
        }
        for (int i = 0; i < orderCount; i++) {
            System.out.println((i+1) + ". " + orders[i]);
        }
    }
}
