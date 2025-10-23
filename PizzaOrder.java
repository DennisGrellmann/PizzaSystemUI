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
}