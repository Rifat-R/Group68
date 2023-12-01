package src.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


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
                preparedStatement = db.getConnection().prepareStatement("SELECT * FROM OrderLines WHERE orderLineNumber= ?");
                preparedStatement.setInt(1, line.getOrderLineNumber());
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
            this.addOrderLine(new OrderLine(resultSet.getInt("orderLineNumber"), resultSet.getInt("quantity"), resultSet.getInt("orderNumber"), resultSet.getString("productionCode")));
        }
    }
}
