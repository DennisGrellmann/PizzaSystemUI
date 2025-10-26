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

    // Constructor
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
        this.subtotal = round((basePrice * quantity)+ addOnsCost);
        this.tax = round(subtotal * taxRate);
        this.totalCost = round(subtotal + tax);
    }
    
    // Round to 2 decimal place function
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    // Encapsulation from W3Schools https://www.w3schools.com/java/java_encapsulation.asp
    // Getters
    public int getOrderId() {return orderId;}
    public String getPizzaType() {return pizzaType;}
    public char getPizzaSize() {return pizzaSize;}
    public int getQuantity() {return quantity;}
    public boolean hasExtraCheese() {return extraCheese;}
    public boolean hasExtraOlives() {return extraOlives;}
    public int getGarlicBreadCount() {return garlicBreadCount;}
    public int getSoftDrinkCount() {return softDrinkCount;}
    public double getAddOnsCost() {return addOnsCost;}
    public double getSubtotal() {return subtotal;}
    public double getTax() {return tax;}
    public double getTotalCost() {return totalCost;}    
    //Setters
    public void setPizzaType(String pizzaType) {this.pizzaType = PizzaTypeCheck(pizzaType);}
    public void setPizzaSize(char pizzaSize) {this.pizzaSize = pizzaSize;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setExtraCheese(boolean extraCheese) {this.extraCheese = extraCheese;}
    public void setExtraOlives(boolean extraOlives) {this.extraOlives = extraOlives;}
    public void setGarlicBreadCount(int garlicBreadCount) {this.garlicBreadCount = garlicBreadCount;}
    public void setSoftDrinkCount(int softDrinkCount) {this.softDrinkCount = softDrinkCount;}

    // Save and Load helpers
    public String toSave() {
            return String.format(
        "%d , %s , %c , %d , %.2f , %.2f , %.2f , %.2f , %d , %d , %d , %d",
        orderId, pizzaType, pizzaSize, quantity,
        addOnsCost, subtotal, tax, totalCost,
        (extraCheese ? 1 : 0), (extraOlives ? 1 : 0),
        garlicBreadCount, softDrinkCount
    );
    }

    // Load from save line
    public static PizzaOrder fromSave(String line) throws Exception {
        String[] p = line.split("\\s*,\\s*");
        if (p.length < 8) {
            throw new Exception("Invalid save data");
        }
        int orderId = Integer.parseInt(p[0]);
        String pizzaType = p[1];
        char pizzaSize = p[2].trim().charAt(0);
        int quantity = Integer.parseInt(p[3]);
        //p4-7 are calculated values
        boolean extraCheese = p.length > 8 ? parseIntasBool(p[8]) : false;
        boolean extraOlives = p.length > 9 ? parseIntasBool(p[9]) : false;
        int garlicBreadCount = p.length > 10 ? Integer.parseInt(p[10]) : 0;
        int softDrinkCount = p.length > 11 ? Integer.parseInt(p[11]) : 0;
        return new PizzaOrder(orderId, pizzaType, pizzaSize, quantity, extraCheese, extraOlives, garlicBreadCount, softDrinkCount);
    }

    // Parse integer as boolean
    private static boolean parseIntasBool(String s) {
        try {return Integer.parseInt(s.trim()) != 0;} catch (Exception e) {return false;}
    }

    // String representation
    @Override
    public String toString() {
    return String.format(
        "#%d %s (%c) x%d  addons: $%.2f  subtotal: $%.2f  tax: $%.2f  total: $%.2f  [cheese:%s, olives:%s, GB:%d, SD:%d]",
        orderId, pizzaType, pizzaSize, quantity,
        addOnsCost, subtotal, tax, totalCost,
        extraCheese ? "Y" : "N", extraOlives ? "Y" : "N",
        garlicBreadCount, softDrinkCount
    );

    }
}