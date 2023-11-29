package src;
import java.sql.*;

public class User {

    enum Role{
        Customer,
        Staff,
        Manager
    };

    private int userID = 0;
    private String userEmail = null;
    private String userPassword = null;
    private Role userRole = Role.Customer;
    private String firstName = null;
    private String lastName = null;
    private int houseNumber = 0;
    private String roadName = null;
    private String city = null;
    private String postCode = null;
    private String cardTypeName = null;
    private int cardNumber = 0;
    private Date cardExpiryDate = null;
    private int cardSecurityCode = 0;

    public int getID() {
        return userID;
    }

    public String getEmail() {
        return userEmail;
    }

    public Role getRole() {
        return userRole;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getFirstName() {
        return roadName;
    }
    public String getLastName() {
        return roadName;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }

    public void setRole(Role role) {
        this.userRole = role;
    }

    public void setHouseNumber(int number) {
        this.houseNumber = number;
    }

    public void setRoadName(String road) {
        this.roadName= road;
    }

    public void setPostCode(String code) {
        this.postCode = code;
    }

    public void setCardTypeName(String type) {
        this.cardTypeName = type;
    }

    public void setCardNumber(int number) {
        this.cardNumber = number;
    }

    public void setCardExpiryDate(Date date) {
        this.cardExpiryDate = date;
    }

    public void setCardSecurityCode(int code) {
        this.cardSecurityCode = code;
    }

    
    
    public User(int id, String email, Role role, String firstName, String lastName, int houseNumber, String roadName, String city, String postCode){
        this.userID = id;
        this.userEmail = email;
        this.userRole = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.houseNumber = houseNumber;
        this.roadName = roadName;
        this.city = city;
        this.postCode = postCode;
    }
    

    public User(int id) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM UserTable WHERE userID= " + id);
        this.userID = id;
        db.resultSet.next();
        this.userEmail = db.resultSet.getString(1);
        switch(db.resultSet.getString(4)){
            case "Customer":
                this.userRole = Role.Customer;
                break;
            case "Staff":
                this.userRole = Role.Staff;
                break;
            case "Manager":
                this.userRole = Role.Manager;
                break;
            default:
                this.userRole = Role.Customer;
                break;
        }
        this.houseNumber = db.resultSet.getInt(4);
        this.firstName = db.resultSet.getString(5);
        this.lastName = db.resultSet.getString(6);
        this.roadName = db.resultSet.getString(7);
        this.city = db.resultSet.getString(8);
        this.postCode = db.resultSet.getString(9);
    }

    public User(String email) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM UserTable WHERE userEmail={email}");
        this.userID = db.resultSet.getInt(0);
        this.userEmail = email;
        switch(db.resultSet.getString(3)){
            case "Customer":
                this.userRole = Role.Customer;
                break;
            case "Staff":
                this.userRole = Role.Staff;
                break;
            case "Manager":
                this.userRole = Role.Manager;
                break;
            default:
                this.userRole = Role.Customer;
                break;
        }
        this.houseNumber = db.resultSet.getInt(4);
        this.firstName = db.resultSet.getString(5);
        this.lastName = db.resultSet.getString(6);
        this.roadName = db.resultSet.getString(7);
        this.city = db.resultSet.getString(8);
        this.postCode = db.resultSet.getString(9);
    }
}
