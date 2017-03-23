package mmm.istic.fr.quickdish.bo;

/**
 * Created by bouluad on 20/03/17.
 */
public class Client {

    private int id;
    private String fullName;
    private String email;
    private String address;
    private int numberOfPoints;


    public Client() {

    }

    public Client(int numberOfPoints, String fullName, String email, String address) {
        this.numberOfPoints = numberOfPoints;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }
}
