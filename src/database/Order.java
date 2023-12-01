package src.database;
import java.sql.*;
import java.util.ArrayList;

import java.util.Date;

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

    public int getLinesCount() {
        return orderLines.size();
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

    public int getNextOrderLineNumber() {
        EasyDatabase db = new EasyDatabase();
        String selectSQL = "SELECT * FROM OrderLines WHERE orderNumber = ? ORDER BY orderLineNumber DESC LIMIT 1";
        
        try {
            PreparedStatement preparedStatement = db.con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, this.orderNumber);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.isBeforeFirst() ) {    
                return 1;
            } else {
                if(rs.next())
                {
                    // process resultset
                    return rs.getInt("orderLineNumber");
                }else
                {
                    return 0;
                }
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public void orderToDB() throws SQLException {
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM Order WHERE orderNumber= " + this.orderNumber);
        if (db.resultSet.next()){
            db.executeUpdate("UPDATE Order SET orderStatus = " + this.orderStatus + " WHERE orderNumber= " + this.orderNumber);
            for (int i = 0; i < this.orderLines.size(); i++) {
                OrderLine line = this.orderLines.get(i);
                db.executeQuery("SELECT * FROM OrderLines WHERE orderLineNumber= " + line.getOrderLineNumber());
                if (db.resultSet.next()){
                    db.executeUpdate("UPDATE OrderLines orderLineNumber= " + line.getOrderLineNumber() + ", quantity= " + line.getQuantity() + ", orderNumber= " + line.getOrderNumber() + ", productCode=  " + line.getProductCode() + " WHERE orderLineNumber= " + line.getOrderLineNumber());
                }
                
            }
            db.executeQuery("SELECT * FROM Order WHERE orderNumber= " + this.orderNumber);
        }else{
            db.executeUpdate("INSERT INTO Order (orderNumber, userID, orderStatus, orderDate) VALUES (" + this.orderNumber + ", " + this.userID + ", " + this.orderStatus + ", " + this.orderDate + ")");
            for (int i = 0; i < this.orderLines.size(); i++) {
                OrderLine line = this.orderLines.get(i);
                db.executeUpdate("INSERT INTO OrderLines (orderLineNumber, quantity, orderNumber, productCode) VALUES (" + line.getOrderLineNumber() + ", " + line.getQuantity() + ", " + line.getOrderNumber() + ", " + line.getProductCode() + ")");
            }
        }
    }

    public Order(int orderNumber, int userId, Status status, java.util.Date date){
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
