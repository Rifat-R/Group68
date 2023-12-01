package src.database;
import java.sql.*;


public class Product {

    enum Gauge{
        OO,
        TT,
        N
    };
    
    enum DCCCode{
        Analogue,
        DCC_Ready,
        DCC_Fitted,
        DCC_Sound
    };

    private String productID = null;
    private String productName = null;
    private String productBrand = null;
    private double productPrice = 0;
    private Gauge productGauge = null;
    private String productEra = null;
    private DCCCode dCCCode = null;
    private int numberInStock = 0;

    public String getID() {
        return productID;
    }

    public String getName() {
        return productName;
    }

    public String getBrand() {
        return productBrand;
    }

    public double getPrice() {
        return productPrice;
    }

    public Gauge getGauge() {
        return productGauge;
    }

    public String getEra() {
        return productEra;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public DCCCode getDCCCode() {
        return dCCCode;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public void setBrand(String brand) {
        this.productBrand= brand;
    }

    public void setPrice(double price) {
        this.productPrice = price;
    }

    public void setGauge(Gauge gauge) {
        this.productGauge = gauge;
    }

    public void setEra(String era) {
        this.productEra = era;
    }

    public void setDCCCode(DCCCode code) {
        this.dCCCode = code;
    }

    public void setNumberInStock(int number) {
        this.numberInStock = number;
    }

    public Product(String id) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        try {
            ResultSet rs = db.getProduct(id); 
            this.productName = rs.getString("productID");
            this.productBrand = rs.getString("productName");
            this.productPrice = db.resultSet.getDouble("productPrice");
            switch(db.resultSet.getString("productGauge")){
                case "OO Gauge":
                    this.productGauge = Gauge.OO;
                    break;
                case "TT Gauge":
                    this.productGauge = Gauge.TT;
                    break;
                case "N Gauge":
                    this.productGauge = Gauge.N;
                    break;
                default:
                    this.productGauge = null;
                    break;
            }
            this.productEra = db.resultSet.getString("productEra");
            switch(db.resultSet.getString("dCCCode")){
                case "Analogue":
                    this.dCCCode = DCCCode.Analogue;
                    break;
                case "DCC-Ready":
                    this.dCCCode = DCCCode.DCC_Ready;
                    break;
                case "DCC-Fitted":
                    this.dCCCode = DCCCode.DCC_Fitted;
                    break;
                case "DCC-Sound":
                    this.dCCCode = DCCCode.DCC_Sound;
                    break;
                default:
                    this.dCCCode = null;
                    break;
            }
            this.numberInStock = db.resultSet.getInt("numberInStock");
        } catch (SQLException e) {
            return;
            //
        }
    }

    public void addProductToDB() throws SQLDataException{
        EasyDatabase db = new EasyDatabase();
        try {
            String sqlString = "INSERT INTO Product (productID, productName, ProductBrand, productPrice, productGauge, productEra, dCCCode, numberInStock) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = db.con.prepareStatement(sqlString);
            preparedStatement.setString(1, this.productID);
            preparedStatement.setString(2, this.productName);
            preparedStatement.setString(3, this.productBrand);
            preparedStatement.setDouble(4, this.productPrice);
            preparedStatement.setString(5, this.productGauge.toString());
            preparedStatement.setString(6, this.productEra);
            preparedStatement.setString(7, this.dCCCode.toString());
            preparedStatement.setInt(8, this.numberInStock);
            preparedStatement.execute();
            db.close();
        } catch (SQLException e) {
            throw new SQLDataException();
        }
    }

    public void updateProductInDB() throws SQLDataException{
        EasyDatabase db = new EasyDatabase();
        try {
            String sqlString = "UPDATE Product SET productName = ?, ProductBrand = ?, productPrice = ?, productGauge = ?, productEra = ?, dCCCode = ?, numberInStock = ? WHERE productID = ?";
            PreparedStatement preparedStatement = db.con.prepareStatement(sqlString);
            preparedStatement.setString(1, this.productName);
            preparedStatement.setString(2, this.productBrand);
            preparedStatement.setDouble(3, this.productPrice);
            preparedStatement.setString(4, this.productGauge.toString());
            preparedStatement.setString(5, this.productEra);
            preparedStatement.setString(6, this.dCCCode.toString());
            preparedStatement.setInt(7, this.numberInStock);
            preparedStatement.setString(8, this.productID);
            preparedStatement.execute();
            db.close();
        } catch (SQLException e) {
            throw new SQLDataException();
        }
    }

    public void deleteProductFromDB(Integer productID) throws SQLDataException{
        EasyDatabase db = new EasyDatabase();
        try {
            String sqlString = "DELETE FROM Product WHERE productID = ?";
            PreparedStatement preparedStatement = db.con.prepareStatement(sqlString);
            preparedStatement.setInt(1, productID);
            preparedStatement.execute();
            db.close();
        } catch (SQLException e) {
            throw new SQLDataException();
        }
    }

    public void updateProductQuantityInDB(Integer productID, Integer quantity) throws SQLDataException{
        EasyDatabase db = new EasyDatabase();
        try {
            String sqlString = "UPDATE Product SET numberInStock = ? WHERE productID = ?";
            PreparedStatement preparedStatement = db.con.prepareStatement(sqlString);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productID);
            preparedStatement.execute();
            db.close();
        } catch (SQLException e) {
            throw new SQLDataException();
        }
    }





    public Product(String id, String name, String brand, double price, Gauge gauge, String era, DCCCode dCCCode, int stock){
        this.productID = id;
        this.productName = name;
        this.productBrand = brand;
        this.productPrice = price;
        this.productGauge = gauge;
        this.productEra = era;
        this.dCCCode = dCCCode;
        this.numberInStock = stock;
    }
}
