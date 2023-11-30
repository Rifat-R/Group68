package src.database;

import java.sql.SQLException;

public class OrderLine {
    private int orderLineNumber = 0;
    private int quantity = 0;
    private int orderNumber = 0;
    private String productCode = "";

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setQuantity(int quantity) {
        this.quantity= quantity;
    }

    public OrderLine(int orderLineNumber, int quantity, int orderNumber, String productCode){
        this.orderLineNumber = orderLineNumber;
        this.quantity = quantity;
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public OrderLine(int int1, int int2, int int3, int int4) {
    }

    
}
