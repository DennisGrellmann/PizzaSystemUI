public class PizzaOrder {
    // Order data
    private int orderId;
    private String pizzaType;
    private char pizzaSize;
    private int quantity;
    private boolean extraCheese;
    private boolean extraOlives;
    private int garlicBreadCount;
    private int softDrinkCount;

    // Pricing data
    private double addOnsCost;
    private double subtotal;
    private double tax;
    private double totalCost;

    // Extra prices + tax rate
    public static final double taxRate = 0.10; // 10% tax
    public static final double extraCheesePrice = 1.50; // Per Pizza
    public static final double extraOlivesPrice = 1.00; //Per Pizza
    public static final double garlicBreadPrice = 4.00; //Per Item
    public static final double softDrinkPrice = 3.00; //Per Item

    public PizzaOrder(int orderId, String pizzaType, char pizzaSize, int quantity,
                      boolean extraCheese, boolean extraOlives,
                      int garlicBreadCount, int softDrinkCount) {
        this.orderId = orderId;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.quantity = quantity;
        this.extraCheese = extraCheese;
        this.extraOlives = extraOlives;
        this.garlicBreadCount = garlicBreadCount;
        this.softDrinkCount = softDrinkCount;

        CalculateCosts();
    } 
    // Check and standardise the input for pizza type
    private String PizzaTypeCheck(String t) {
        if (t == null) return "";
        t = t.trim();
        if (t.length() == 0) return t;
        String lower = t.toLowerCase();
        if (lower.equals("margherita")) return "Margherita";
        if (lower.equals("neapolitan")) return "Neapolitan";
        if (lower.equals("marinara")) return "Marinara";
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    } 
    // Pizza price selection
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
    // Calculate costs
    public void CalculateCosts() {
        double basePrice = getPizzaBasePrice();
        addOnsCost = 0.0;

        if (extraCheese) {
            addOnsCost += extraCheesePrice * quantity;
        }
        if (extraOlives) {
            addOnsCost += extraOlivesPrice * quantity;
        }
        addOnsCost += (garlicBreadPrice * garlicBreadCount);
        addOnsCost += (softDrinkPrice * softDrinkCount);
        this.addOnsCost = round(this.addOnsCost);
        this.tax = round(subtotal * taxRate);
        this.subtotal = round((basePrice * quantity));
        this.totalCost = round(subtotal + tax);
    }
    // Round to 2 decimal place function
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
