package src.database;

import java.sql.SQLException;

public class OrderLine {
    private int orderLineNumber = 0;
    private int quantity = 0;
    private int orderNumber = 0;
    private String productCode = "";
    private Product product;

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
    
    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity= quantity;
    }



    // Constructor for creating a new orderLine + Checks whether the product exists
    public OrderLine(int orderLineNumber, int quantity, int orderNumber, String productCode){
        this.orderLineNumber = orderLineNumber;
        this.quantity = quantity;
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        try {
            product = new Product(productCode);
            System.out.println(product.getBrand());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
