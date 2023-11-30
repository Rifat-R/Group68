package src.database;
import java.sql.*;
import java.util.ArrayList;

import src.database.User.Role;

public class Order {

    public enum Status{
        Pending,
        Confirmed,
        Fulfilled
    };

    private int orderNumber = 0;
    private int userID = 0;
    private Status orderStatus = null;
    private Date orderDate = null;
    private ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getUserID() {
        return userID;
    }

    public Status getStatus() {
        return orderStatus;
    }

    public Date getDate() {
        return orderDate;
    }

    public void addOrderLine(OrderLine line) {
        this.orderLines.add(line);
    }

    public void orderToDB() throws SQLException {
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM Order WHERE orderNumber= " + this.orderNumber);
        if (db.resultSet.next()){
            db.executeUpdate("UPDATE Order SET orderStatus = " + this.orderStatus);
        }else{
            db.executeUpdate("INSERT INTO Order (orderNumber, userID, orderStatus, orderDate) Values (" + this.orderNumber + ", " + this.userID + ", " + this.orderStatus + ", " + this.orderDate + ")");
        }
    }

    public Order(int orderNumber, int userId, Status status, Date date){
        this.orderNumber = orderNumber;
        this.userID = userId;
        this.orderStatus = status;
        this.orderDate = date;
    }

    public Order(int orderNumber) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM Order WHERE orderNumber= " + orderNumber);
        this.orderNumber = orderNumber;
        db.resultSet.next();
        this.userID = db.resultSet.getInt("userID");
        this.orderDate = db.resultSet.getDate("orderDate");
        switch(db.resultSet.getString("orderStatus")){
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
                this.orderStatus = Status.Fulfilled;
                break;
        }
        db.executeQuery("SELECT * FROM OrderLines WHERE orderNumber= " + orderNumber);
        while(db.resultSet.next()){
            this.addOrderLine(new OrderLine(db.resultSet.getInt("orderLineNumber"), db.resultSet.getInt("quantity"), db.resultSet.getInt("orderNumber"), db.resultSet.getString("productionCode")));
        }
    }
}
