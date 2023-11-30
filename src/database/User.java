package src.database;
import java.sql.*;

public class User {

    public enum Role{
        Customer,
        Staff,
        Manager
    };

    private int userID = 0;
    private String userEmail = null;
    private String hashedPassword = null;
    private String salt = null;
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
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

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
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
        // Using preparedStatement to prevent SQL injection
        String selectSQL = "SELECT * FROM User WHERE id = ?";
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);

        this.userID = id;
        db.resultSet.next();
        this.userEmail = db.resultSet.getString("email");
        this.hashedPassword = db.resultSet.getString("hashed_password");
        this.salt = db.resultSet.getString("salt");
        switch(db.resultSet.getString("role")){
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
        this.houseNumber = db.resultSet.getInt("houseNumber");
        this.firstName = db.resultSet.getString("firstName");
        this.lastName = db.resultSet.getString("lastName");
        this.roadName = db.resultSet.getString("roadName");
        this.city = db.resultSet.getString("city");
        this.postCode = db.resultSet.getString("postCode");
    }

    public User(String email) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        // Fixed query
        String selectSQL = "SELECT * FROM User WHERE email = ?";
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectSQL);
        preparedStatement.setString(1, email);
        db.resultSet.next();
        this.userID = db.resultSet.getInt("id");
        this.userEmail = email;
        this.hashedPassword = db.resultSet.getString("hashed_password");
        this.salt = db.resultSet.getString("salt");
        switch(db.resultSet.getString("role")){
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
        this.houseNumber = db.resultSet.getInt("houseNumber");
        this.firstName = db.resultSet.getString("firstName");
        this.lastName = db.resultSet.getString("lastName");
        this.roadName = db.resultSet.getString("roadName");
        this.city = db.resultSet.getString("city");
        this.postCode = db.resultSet.getString("postCode");
        db.close();
    }
}
