
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class ShoppingCart extends Product{
    private float amount;
    private String type;

    public ShoppingCart(boolean inSI,String inID, String inName,String inCategories, String inDes, int inNum, float inPrice) {
        super(inID, inName,inCategories, inDes, inNum, inPrice);
        amount = SystemMenu.precision(inNum * inPrice);
        if(inSI){
            this.type = "Purchase";
        }
        else{
            this.type = "Order";
        }
        
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    @Override
    public void setProductInv(int newBN){
        this.productInv = newBN;
        amount= SystemMenu.precision(this.productInv*this.price);
    }
    

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    public float getAmount() {
        return amount;
    }
    
}
