/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */

public class Product{
    //declare data member 
    protected String productName;
    protected String productID;
    protected float price;
    protected String description;
    protected int productInv;
    protected String categories;

    public Product(String inID, String inName,String inCategories, String inDes, int inInv, float inPrice) {
        // initialization
        this.productName = inName;
        this.productID = inID;
        this.price = SystemMenu.precision(inPrice);
        this.description = inDes;
        this.productInv = inInv;
        this.categories = inCategories;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCatogories(String catogories) {
        this.categories = catogories;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductID() {
        return productID;
    }

    public int getProductInv() {
        return productInv;
    }

    public String getCategories() {
        return categories;
    }
    public float getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setProductInv(int productInv) {
        this.productInv = productInv;
    }



}
