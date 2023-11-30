package src.database;
import java.sql.*;

import src.database.User.Role;

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

    /**
    public Product(String name) throws SQLException{
        EasyDatabase db = new EasyDatabase();
        db.executeQuery("SELECT * FROM ProductTable WHERE productID={id}");
        this.productID = db.resultSet.getInt(0);
        this.productName = name;
        this.productBrand = db.resultSet.getString(2);
        this.productPrice = db.resultSet.getDouble(3);
        switch(db.resultSet.getString(4)){
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
        this.productEra = db.resultSet.getString(5);
        switch(db.resultSet.getString(6)){
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
        this.numberInStock = db.resultSet.getInt(7);
    }**/

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
