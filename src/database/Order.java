package src.database;
import java.sql.*;

import src.database.User.Role;

public class Order {

    enum Status{
        Pending,
        Confirmed,
        Fulfilled
    };

    private int orderNumber = 0;
    private int userID = 0;
    private Status orderStatus = null;
    private Date orderDate = null;

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

    public Order(int orderNumber, int userId, Status status, Date date){
        this.orderNumber = orderNumber;
        this.userID = userId;
        this.orderStatus = status;
        this.orderDate = date;
    }
}
