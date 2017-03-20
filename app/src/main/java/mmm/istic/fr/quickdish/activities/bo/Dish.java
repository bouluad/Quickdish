package mmm.istic.fr.quickdish.activities.bo;

/**
 * Created by bouluad on 20/03/17.
 */
public class Dish {

    private int id;
    private String idResto;
    private String title;
    private String description;
    private String price;
    private int numberOfPoint;


    public Dish() {
    }

    public Dish(String idResto, String title, String description, String price, int numberOfPoint) {
        this.idResto = idResto;
        this.title = title;
        this.description = description;
        this.price = price;
        this.numberOfPoint = numberOfPoint;
    }

    public Dish(int id, String idResto, String title, String description, String price, int numberOfPoint) {
        this.id = id;
        this.idResto = idResto;
        this.title = title;
        this.description = description;
        this.price = price;
        this.numberOfPoint = numberOfPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
