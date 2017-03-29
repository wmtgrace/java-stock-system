
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class AllProdTable extends AbstractTableModel{
    private final  java.util.List<Product> prodList ;
    private final String [] prodcolumn=new String [] {"Product ID", "Product Name","Categories", "Description","Inventory", "Price"};
    private static final Class[] columns = new Class[]{String.class, String.class, String.class,String.class,Integer.class,Float.class};
    public AllProdTable() {
        this.prodList = new ArrayList<Product>();       
    }

    public List<Product> getProdList() {
        return prodList;
    }
    public int additem(Product newItem){
        prodList.add(newItem);
        this.fireTableDataChanged();
        return getRowCount();
    }
    public int delitem(Product delItem){
         prodList.remove(delItem);
         this.fireTableDataChanged();
         return getRowCount();
    }
    @Override
    public int getRowCount() {
        return this.prodList.size();
    }

    @Override
    public int getColumnCount() {
        return prodcolumn.length;
    }    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product prod = prodList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return prod.getProductID();
            case 1:
                return prod.getProductName();
            case 2:
                return prod.getCategories();
            case 3:
                return prod.getDescription();
            case 4:
                return new Integer(prod.getProductInv());
            case 5:
                return new Float(prod.getPrice());
            default:
                return null;
           }
    }
    public Product getProd(int rowIndex){
        Product prodGet=prodList.get(rowIndex);
        return prodGet;
    }
    //the name of the column
    public String getColumnName(int col) {
        return prodcolumn[col] ;
    }
    
    public int searchItem(String inID){
        int index=-1;
        for(int i=0;i<prodList.size();i++){
            for(int j=0;j<prodcolumn.length;j++)
            {
                if(getValueAt(i,j).equals(inID)){
                    index=i;
                    break;
                }
            }
        }
        return index;
    }
    @Override
   public void setValueAt(Object value,int rowIndex, int columnIndex) {
        Product prod = prodList.get(rowIndex);
        switch(columnIndex){
            case 0:
                prod.setProductID(String.valueOf(value));
                break;
            case 1:
                prod.setProductName(String.valueOf(value));
                break;
            case 2:
                prod.setCatogories(String.valueOf(value));
                break;
            case 3:
                prod.setDescription(String.valueOf(value));
                break;
            case 4:
                prod.setProductInv(Integer.valueOf(String.valueOf(value)));
                break;
            case 5:
                prod.setPrice(Float.valueOf(String.valueOf(value)));
                break;
           }
         this.fireTableDataChanged();
    } 
@Override
    public Class getColumnClass(int c){
       return columns[c];
    }
  //
}
    

