package src.database;
import java.sql.*;

public class EasyDatabase {
    // Database credentials
    private static final String JDBC_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk/team068";
    private static final String USERNAME = "team068";
    private static final String PASSWORD = "av2Iedusi";


    // Database connection variables
    public Statement statement = null;
    public Connection con = null;
    public ResultSet resultSet = null;


    // Constructor to connect to the database and initialise con and statement
    public EasyDatabase() {
        try {
            con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Close the connection and statement
    public void close() {
        try {
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Execute a query (Later on realised that this can ONLY be used for queries with no WHERE clauses for SQL Injection reasons)
    // Used for update queries
    public void executeUpdate(String Query){
        try{statement.executeUpdate(Query);}
        catch(SQLException e){e.printStackTrace();}
    }


    // Execute a query (Later on realised that this can ONLY be used for queries with no WHERE clauses for SQL Injection reasons)
    // Used for select queries
    public void executeQuery(String Query){
        try{resultSet = statement.executeQuery(Query);}
        catch(SQLException e){e.printStackTrace();}
    }


    // Gets the result set for a product with a given productID
    public ResultSet getProduct(String productID) throws SQLException {
        String selectSQL = "SELECT * FROM Product WHERE productID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setString(1, productID);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }


    // Simple function to get the latest orderNumber for a given userID
    public int getOrderNumber(int userID) {
        try {
            String selectSQL = "SELECT orderNumber FROM `Order` WHERE userID = ? AND orderStatus = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setInt(1, userID);
            ps.setString(2, Order.Status.Pending.name());
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                {
                    // process resultset
                    return rs.getInt("orderNumber");
                }else
                {
                    return 0;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Check whether an order exists for a given userID
    public boolean checkIfOrderExsistsForCustomer(Integer userID) {
        try {
            String selectSQL = "SELECT * FROM `Order` WHERE userID = ? AND orderStatus = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setInt(1, userID);
            ps.setString(2, Order.Status.Pending.name());
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst() ) {    
                return false; 
            } else {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    // Update user details in the database
    public void updateUserDetails(Integer userID, String email, String name, String surname) throws SQLException {
        try {
            String selectSQL = "UPDATE User SET email = ?, firstName = ?, lastName = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setInt(4, userID);
            ps.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    // Update user address in the database (Realised we SHOULD have these functions in User.java)
    public void updateUserAddress(Integer userID, String houseNumber, String roadName, String city, String postcode) throws SQLException{
        try {
            String selectSQL = "UPDATE User SET houseNumber = ?, roadName = ?, city = ?, postCode = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setString(1, houseNumber);
            ps.setString(2, roadName);
            ps.setString(3, city);
            ps.setString(4, postcode);
            ps.setInt(5, userID);
            ps.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }


    // Get all products from the database in a resultset
    public ResultSet getProducts() throws SQLException {
        String selectSQL = "SELECT * FROM Product";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet getAllOrdersFromUser(int userID) throws SQLException {
        String selectSQL = "SELECT * FROM Order WHERE userID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet GetUserDetails(Integer userID) throws SQLException {
        String selectSQL = "SELECT * FROM User WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public boolean checkIfCardDetailsExsists(Integer userID) {
        try {
            String selectSQL = "SELECT cardNumber FROM User WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            if (rs.getString("cardNumber") == null) {
                return false;
            } else {
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper function to print the result set of a query (Not needed, pretty much for testing purposes)
    public void printQuery(){
        try{
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            
            // Print records
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } 
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get the connection
    public Connection getConnection() {
        return this.con;
    }

}
