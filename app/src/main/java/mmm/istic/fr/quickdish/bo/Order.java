package mmm.istic.fr.quickdish.bo;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bouluad on 20/03/17.
 */
public class Order implements Serializable {

    private int id;
    private List<Dish> dishs;
    private int quantity;
    private String tableNumber;
    private boolean validation;

    public Order(List<Dish> dishs, int quantity, String tableNumber, boolean validation) {
        this.dishs = dishs;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.validation = validation;
    }

    public Order() {

    }


    protected Order(Parcel in) {
        id = in.readInt();
        quantity = in.readInt();
        tableNumber = in.readString();
        validation = in.readByte() != 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getDishs() {
        return dishs;
    }

    public void setDishs(List<Dish> dishs) {
        this.dishs = dishs;
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
        for (int i = 0; i < dishs.size(); i++) {
            result += dishs.get(i).getType() + ": " + dishs.get(i).getTitle() + "\n" + dishs.get(i).getDescription() + "\n";
        }
        return result;
    }

}
