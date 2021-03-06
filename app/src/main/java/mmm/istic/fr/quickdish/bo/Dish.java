package mmm.istic.fr.quickdish.bo;

import java.io.Serializable;

/**
 * Created by bouluad on 20/03/17.
 */
public class Dish implements Serializable {

    private String id;
    private String idResto;
    private String title;
    private String description;
    private String price;
    private int numberOfPoint;
    private String type;


    public Dish() {
    }

    public Dish(String idResto, String title, String description, String price, int numberOfPoint, String type) {
        this.idResto = idResto;
        this.title = title;
        this.description = description;
        this.price = price;
        this.numberOfPoint = numberOfPoint;
        this.type = type;
    }

    public Dish(String id, String idResto, String title, String description, String price, int numberOfPoint) {
        this.id = id;
        this.idResto = idResto;
        this.title = title;
        this.description = description;
        this.price = price;
        this.numberOfPoint = numberOfPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdResto() {
        return idResto;
    }

    public void setIdResto(String idResto) {
        this.idResto = idResto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumberOfPoint() {
        return numberOfPoint;
    }

    public void setNumberOfPoint(int numberOfPoint) {
        this.numberOfPoint = numberOfPoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
