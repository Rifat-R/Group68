package src.database;
import java.sql.*;
import java.text.SimpleDateFormat;

public class User {

    public enum Role{
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
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCardTypeNumber() {
        return cardTypeName;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public Integer getCardSecurityCode() {
        return cardSecurityCode;
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
        db.executeQuery("SELECT * FROM User WHERE id= " + id);
        this.userID = id;
        db.resultSet.next();
        this.userEmail = db.resultSet.getString(2);
        switch(db.resultSet.getString(5)){
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

        this.houseNumber = db.resultSet.getInt(6);
        this.firstName = db.resultSet.getString(7);
        this.lastName = db.resultSet.getString(8);
        this.roadName = db.resultSet.getString(9);
        this.city = db.resultSet.getString(10);
        this.postCode = db.resultSet.getString(11);
        this.cardTypeName = db.resultSet.getString(12);
        this.cardNumber = db.resultSet.getInt(13);
        this.cardExpiryDate = db.resultSet.getDate(14);
        this.cardSecurityCode = db.resultSet.getInt(15);
    }

    public User(String email) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM User WHERE email={email}");
        this.userID = db.resultSet.getInt(1);
        this.userEmail = email;
        switch(db.resultSet.getString(5)){
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
        this.houseNumber = db.resultSet.getInt(6);
        this.firstName = db.resultSet.getString(7);
        this.lastName = db.resultSet.getString(8);
        this.roadName = db.resultSet.getString(9);
        this.city = db.resultSet.getString(10);
        this.postCode = db.resultSet.getString(11);
    }
}
