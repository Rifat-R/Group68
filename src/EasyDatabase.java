package src;
import java.sql.*;

public class EasyDatabase {
    private static final String JDBC_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk/team068";
    private static final String USERNAME = "team068";
    private static final String PASSWORD = "av2Iedusi";

    Statement statement = null;
    Connection con = null;
    ResultSet resultSet = null;

    public EasyDatabase() {
        try {
            con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String Query){
        try{statement.executeUpdate(Query);}
        catch(SQLException e){e.printStackTrace();}
    }

    public void executeQuery(String Query){
        try{resultSet = statement.executeQuery(Query);}
        catch(SQLException e){e.printStackTrace();}
    }

    public ResultSet getProduct(String productID) throws SQLException {
        String selectSQL = "SELECT * FROM Product WHERE productID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setString(1, productID);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public void addOrderLine(String productID, String brand, String name, Integer Quantity, Double Price) throws SQLException {
        try {statement.executeQuery("SELECT * FROM Product");}
        catch (SQLException e) {e.printStackTrace();}
    }

    public void updateUserDetails(Integer userID, String email, String name, String surname) throws SQLException {
        try {
            String selectSQL = "UPDATE User SET userEmail = ?, firstName = ?, lastName = ? WHERE userID = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setInt(4, userID);
            ps.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public void updateUserAddress(Integer userID, String houseNumber, String roadName, String city, String postcode) throws SQLException{
        try {
            String selectSQL = "UPDATE User SET houseNumber = ?, roadName = ?, city = ?, postCode = ? WHERE userID = ?";
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

    public ResultSet getProducts() throws SQLException {
        String selectSQL = "SELECT * FROM Product";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet GetUserDetails(Integer userID) throws SQLException {
        String selectSQL = "SELECT * FROM User WHERE userID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

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
    public Connection getConnection() {
        return this.con;
    }

}
