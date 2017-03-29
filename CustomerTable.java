import java.util.ArrayList;
import java.util.Collections;
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
//login ID,pw,Fname,Lname,NickName,gender,email,DOB,phone,district,address
public class CustomerTable extends AbstractTableModel{
    private final  java.util.List<Customer> cusList ;
    private final String [] cuscolumn=new String [] {"Login ID","Password","Full Name","NickName","Gender","Email","Date of Birth","Age","Discount","Phone Number","District","Address"};
    private static final Class[] columns = new Class[]{String.class, String.class, String.class,String.class,char.class,String.class,String.class,Integer.class,Float.class,String.class,String.class,String.class};
    public CustomerTable() {
        this.cusList = new ArrayList<Customer>();       
    }
    public int addCus(Customer newCus){
        cusList.add(newCus);
        this.fireTableDataChanged();
        return getRowCount();
    }

    @Override
    public int getRowCount() {
        return this.cusList.size();
    }

    @Override
    public int getColumnCount() {
        return cuscolumn.length;
    }    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer cus = cusList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return cus.getLoginID();
            case 1:
                return cus.getPassword();
            case 2:
                return cus.getFName()+","+cus.getLName();
            case 3:
                return cus.getNickName();
            case 4:
                return cus.getGender();
            case 5:
                return cus.getEmail();
            case 6:
                return cus.getDOB();
            case 7:
                return new Integer(cus.getAge());
            case 8:
                return new Float(cus.getDiscount());
            case 9:
                return cus.getPhoneNum();
            case 10:
                return cus.getDistrict();
            case 11:
                return cus.getAddress();
            default:
                return null;
           }
    }
    public Customer getCus(int rowIndex){
        Customer cusGet=cusList.get(rowIndex);
        return cusGet;
    }
    //the name of the column
    public String getColumnName(int col) {
        return cuscolumn[col] ;
    }
    
    public int searchID(String inID){
        //i = row index,j= column index
       
        for(int i=0;i<cusList.size();i++){
            
                if(getValueAt(i,0).equals(inID)){
                    return i;
                }
      
        }
        return -1;
    }
    @Override
   public void setValueAt(Object value,int rowIndex, int columnIndex) {
        Customer cusGet = cusList.get(rowIndex);
        System.out.println(rowIndex);
        switch(columnIndex){
            //cannot change the login id - no case 0
            case 1:
                cusGet.setPassword(String.valueOf(value));
                break;
            //cannot change full Name no case 2
            case 3:
                cusGet.setNickName(String.valueOf(value));
                break;
            //cannot change the gender - no case 4
            case 5:
                cusGet.setEmail(String.valueOf(value));
                break;
            case 9:
                cusGet.setPhoneNum(String.valueOf(value));
                break;
            case 10:
                cusGet.setDistrict(String.valueOf(value));
                break;
            case 11:
                cusGet.setAddress(String.valueOf(value));
                break;
            default:
                return;
           }
        this.fireTableDataChanged();
    } 
   @Override
    public Class getColumnClass(int c){
       return columns[c];
    }
}
    

