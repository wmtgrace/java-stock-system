
import javax.swing.table.AbstractTableModel;
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class CartTable extends AbstractTableModel{

    protected java.util.List<ShoppingCart> listOfCart;
    protected float total;
    protected float  discount;
    protected static final  String [] cartcolumn=new String [] {"Type","Product ID", "Product Name","Category","Description", "Quantity", "Price","Subtotal"};
    protected static final Class[] columns = new Class[]{String.class, String.class, String.class,String.class,String.class,Integer.class,Float.class,Float.class};
    //Constructor
    //initiation
    public CartTable() {
        this.listOfCart = new ArrayList<ShoppingCart>();
        total=0;
        discount=1;
    }
    public CartTable(float inDiscount) {
        this();
        discount=inDiscount;
    }

    public float getDiscount() {
        return discount;
    }
    
    
    public int additem(ShoppingCart newItem){
        listOfCart.add(newItem);
        total = SystemMenu.precision(total+newItem.getAmount()*discount);
        this.fireTableDataChanged();
        return getRowCount();
    }
    public int delitem(ShoppingCart delItem){
         listOfCart.remove(delItem);
         total=SystemMenu.precision(total-delItem.getAmount()*discount);
        this.fireTableDataChanged();
         return getRowCount();
    }
    public int delall(){
        listOfCart.clear();
	this.total=0;
        this.fireTableDataChanged();
        return getRowCount();
    }
    public int searchItem(String buy,String inID){
        int index=-1;
        for(int i=0;i<getRowCount();i++){
            //product ID and type is the primary key of the cart table
            if(getValueAt(i,1).equals(inID) && getValueAt(i,0).equals(buy)){
                    index=i;
                    break;
            }

        }
        return index;
    }
    public int searchItem(String inID){
        int index=-1;
        for(int i=0;i<getRowCount();i++){
            //product ID and type is the primary key of the cart table
            if(getValueAt(i,1).equals(inID) ){
                    index=i;
                    break;
            }

        }
        return index;
    }
    public ShoppingCart getshoppingcart(int inRowIndex){
        ShoppingCart getCart = listOfCart.get(inRowIndex);
        return getCart;
    }
    

    public void setValueAt(Object value,int rowIndex, int columnIndex) {
        ShoppingCart cart = listOfCart.get(rowIndex);
        switch(columnIndex){
            case 0:
                cart.setType(String.valueOf(value));
                break;
            case 1:
                cart.setProductID(String.valueOf(value));
                break;
            case 2:
                cart.setProductName(String.valueOf(value));
                break;
            case 3:
                cart.setCatogories(String.valueOf(value));
                break;
            case 4:
                cart.setDescription(String.valueOf(value));
                break;
            case 5:
                cart.setProductInv(Integer.parseInt(String.valueOf(value)));
                break;
            case 6:
                cart.setPrice(Float.parseFloat(String.valueOf(value)));
                break;
            case 7:
                cart.setAmount(Float.parseFloat(String.valueOf(value)));
		break;
           }
         this.fireTableDataChanged();
    }
    public float getTotal() {
        return this.total;
    }
    @Override
    public int getRowCount() {
        return this.listOfCart.size();
    }

    @Override
    public int getColumnCount() {
       return CartTable.cartcolumn.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShoppingCart cart = listOfCart.get(rowIndex);
        switch(columnIndex){
            
            case 0:
                return cart.getType();
            case 1:
                return cart.getProductID();
            case 2:
                return cart.getProductName();
            case 3:
                return cart.getCategories();
            case 4:
                return cart.getDescription();
            case 5:
                return new Integer(cart.getProductInv());
            case 6:
                return new Float(cart.getPrice());
            case 7:
                return new Float(cart.getAmount());
            default:
                return null;
           }
    }
	
    @Override
    public String getColumnName(int col) {
        return CartTable.cartcolumn[col] ;
    }
    @Override
    public Class getColumnClass(int c){
       return columns[c];
    }
    
}
