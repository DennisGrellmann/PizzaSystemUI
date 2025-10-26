// Assignment 2 SENG110 
// Pizza Ordering System UI
// Dennis Grellmann c3468127 
// 12-2pm Thursday ES105

public class PizzaStore {
    private int orderCount;
    private PizzaOrder[] orders;

        public PizzaOrder[] getOrdersSnapshot() {
        PizzaOrder[] copy = new PizzaOrder[orderCount];
        for (int i = 0; i < orderCount; i++) copy[i] = orders[i];
        return copy;
    }
}
