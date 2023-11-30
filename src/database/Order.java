package src.database;
import java.sql.*;

import src.database.User.Role;

public class Order {

    enum Status{
        Pending,
        Confirmed,
        Fulfilled
    };

    private int orderID = 0;
    private int userID = 0;
    private int productID = 0;
    private Status orderStatus = null;
    private Date orderDate = null;
    private int orderQuantity = 0;

    public int getID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public int getProductID() {
        return productID;
    }

    public Status getStatus() {
        return orderStatus;
    }

    public Date getDate() {
        return orderDate;
    }

    public int getQuantity() {
        return orderQuantity;
    }

    public void setStatus(Status status) {
        this.orderStatus = status;
    }

    public void setQuantity(int quantity) {
        this.orderQuantity= quantity;
    }

    public void setPrice(Date date) {
        this.orderDate = date;
    }

    public Order(int id) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM Order WHERE orderID={id}");
        this.orderID = id;
        this.userID = db.resultSet.getInt(1);
        this.productID = db.resultSet.getInt(2);
        switch(db.resultSet.getString(3)){
            case "Pending":
                this.orderStatus = Status.Pending;
                break;
            case "Confirmed":
                this.orderStatus = Status.Confirmed;
                break;
            case "Fulfilled":
                this.orderStatus = Status.Fulfilled;
                break;
            default:
                this.orderStatus = null;
                break;
        }
        this.orderQuantity = db.resultSet.getInt(4);
        this.orderDate = db.resultSet.getDate(5);
    }

    public Order(int orderId, int userId, int productId, Status status, int quantity, Date date){
        this.orderID = orderId;
        this.productID = productId;
        this.userID = userId;
        this.orderStatus = status;
        this.orderQuantity = quantity;
        this.orderDate = date;
    }
}
