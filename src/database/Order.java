package src.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.cj.xdevapi.SelectStatement;


public class Order {

    public enum Status{
        Pending,
        Confirmed,
        Fulfilled
    };

    private int orderNumber = 0;
    private int userID = 0;
    private Status orderStatus = null;
    private java.sql.Date orderDate = null;
    private ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();

    public int getOrderNumber() {
        return orderNumber;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
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

    public int getTotalOrderCost() {
        return 0;
    }

    public void changeStatus() {
        try {
            EasyDatabase db = new EasyDatabase();
            String selectSQL = "UPDATE `Order` SET orderStatus = ? WHERE orderNumber = ?";
            PreparedStatement selectStatement = db.con.prepareStatement(selectSQL);
            selectStatement.setString(1, Status.Confirmed.name());
            selectStatement.setInt(2, this.orderNumber);
            selectStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void addOrderLineToDB(OrderLine orderLine) {
        try {
            EasyDatabase db = new EasyDatabase();
            String selectSQL = "SELECT * FROM OrderLines WHERE orderNumber = ? AND productCode = ?";
            PreparedStatement selectStatement = db.con.prepareStatement(selectSQL);
            selectStatement.setInt(1, orderLine.getOrderNumber());
            selectStatement.setString(2, orderLine.getProductCode());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Order line already exists, update the quantity
                String updateSQL = "UPDATE OrderLines SET quantity = quantity + ? WHERE productCode = ? AND orderNumber = ?";
                PreparedStatement updateStatement = db.con.prepareStatement(updateSQL);
                updateStatement.setInt(1, orderLine.getQuantity());
                updateStatement.setString(2, orderLine.getProductCode());
                updateStatement.setInt(3, orderLine.getOrderNumber());
                updateStatement.executeUpdate();
            } else {
                // Order line does not exist, insert a new record
                String insertSQL = "INSERT INTO OrderLines (orderLineNumber, quantity, orderNumber, productCode) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = db.con.prepareStatement(insertSQL);
                insertStatement.setInt(1, orderLine.getOrderLineNumber());
                insertStatement.setInt(2, orderLine.getQuantity());
                insertStatement.setInt(3, orderLine.getOrderNumber());
                insertStatement.setString(4, orderLine.getProductCode());
                insertStatement.execute();
            }

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void orderToDB() throws SQLException {
        EasyDatabase db = new EasyDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        preparedStatement = db.getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderNumber = ?");
        preparedStatement.setInt(1, this.orderNumber);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement = db.getConnection().prepareStatement("UPDATE `Order` SET orderStatus = ? WHERE orderNumber= ?");
            preparedStatement.setString(1, this.orderStatus.toString());
            preparedStatement.setInt(2, this.orderNumber);
            preparedStatement.executeUpdate();

            for (int i = 0; i < this.orderLines.size(); i++) {
                OrderLine line = this.orderLines.get(i);
                preparedStatement = db.getConnection().prepareStatement("SELECT * FROM OrderLines WHERE orderLineNumber= ? AND orderNumber = ?");
                preparedStatement.setInt(1, line.getOrderLineNumber());
                preparedStatement.setInt(2, line.getOrderNumber());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    preparedStatement = db.getConnection().prepareStatement("UPDATE OrderLines SET orderLineNumber= ?, quantity= ?, orderNumber= ?, productCode= ? WHERE orderLineNumber= ?");
                    preparedStatement.setInt(1, line.getOrderLineNumber());
                    preparedStatement.setInt(2, line.getQuantity());
                    preparedStatement.setInt(3, line.getOrderNumber());
                    preparedStatement.setString(4, line.getProductCode());
                    preparedStatement.setInt(5, line.getOrderLineNumber());
                    preparedStatement.executeUpdate();
                }
            }
            preparedStatement = db.getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderNumber= ?");
            preparedStatement.setInt(1, this.orderNumber);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
        } else {
            preparedStatement = db.getConnection().prepareStatement("INSERT INTO `Order` (orderNumber, userID, orderStatus, orderDate) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, this.orderNumber);
            preparedStatement.setInt(2, this.userID);
            preparedStatement.setString(3, this.orderStatus.toString());
            preparedStatement.setDate(4, this.orderDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            for (int i = 0; i < this.orderLines.size(); i++) {
                OrderLine line = this.orderLines.get(i);
                preparedStatement = db.getConnection().prepareStatement("INSERT INTO OrderLines (orderLineNumber, quantity, orderNumber, productCode) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, line.getOrderLineNumber());
                preparedStatement.setInt(2, line.getQuantity());
                preparedStatement.setInt(3, line.getOrderNumber());
                preparedStatement.setString(4, line.getProductCode());
                preparedStatement.executeUpdate();
            }
        }
    }

    public Order(int orderNumber, int userId, Status status, java.util.Date date) {
        this.orderNumber = orderNumber;
        this.userID = userId;
        this.orderStatus = status;
        this.orderDate = new java.sql.Date(date.getTime());
    }

    public Order(int orderNumber) throws SQLException {
        EasyDatabase db = new EasyDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        preparedStatement = db.getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderNumber = ?");
        preparedStatement.setInt(1, orderNumber);
        resultSet = preparedStatement.executeQuery();
        this.orderNumber = orderNumber;
        resultSet.next();
        this.userID = resultSet.getInt("userID");
        this.orderDate = resultSet.getDate("orderDate");
        switch (resultSet.getString("orderStatus")) {
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
        preparedStatement = db.getConnection().prepareStatement("SELECT * FROM OrderLines WHERE orderNumber= ?");
        preparedStatement.setInt(1, orderNumber);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            this.addOrderLine(new OrderLine(resultSet.getInt("orderLineNumber"), resultSet.getInt("quantity"), resultSet.getInt("orderNumber"), resultSet.getString("productCode")));
        }
    }
}
