package mmm.istic.fr.quickdish.activities.bo;

/**
 * Created by bouluad on 20/03/17.
 */
public class Order {

    private int id;
    private Dish dish;
    private int quantity;
    private String tableNumber;
    private boolean validation;


    public Order() {

    }

    public Order(Dish dish, int quantity, String tableNumber, boolean validation) {
        this.dish = dish;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.validation = validation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }
}
