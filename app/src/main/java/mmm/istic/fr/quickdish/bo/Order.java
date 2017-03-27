package mmm.istic.fr.quickdish.bo;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bouluad on 20/03/17.
 */
public class Order implements Serializable {

    private String id;
    private List<Dish> dishes;
    private int quantity;
    private String tableNumber;
    private boolean validation;

    public Order(List<Dish> dishes, int quantity, String tableNumber, boolean validation) {
        this.dishes = dishes;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.validation = validation;
    }

    public Order(String id, List<Dish> dishes, int quantity, boolean validation, String tableNumber) {
        this.id = id;
        this.dishes = dishes;
        this.quantity = quantity;
        this.validation = validation;
        this.tableNumber = tableNumber;
    }

    public Order() {

    }


    protected Order(Parcel in) {
        id = in.readString();
        quantity = in.readInt();
        tableNumber = in.readString();
        validation = in.readByte() != 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
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

    public String dishsToString() {
        String result = "";
        for (int i = 0; i < dishes.size(); i++) {
            result += dishes.get(i).getType() + ": " + dishes.get(i).getTitle() + "\n" + dishes.get(i).getDescription() + "\n\n";
        }
        return result;
    }

}
