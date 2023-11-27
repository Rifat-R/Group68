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

    public ResultSet getProducts() throws SQLException {
        String selectSQL = "SELECT * FROM ProductTable";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
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
