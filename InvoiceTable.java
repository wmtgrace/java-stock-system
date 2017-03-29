
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class InvoiceTable extends CartTable{
    private static int totalNoOfInvoice;
    private static int totalNoOfOrder;
    private String  buyDate;
    private String invoiceNum;
    private String CusID;
    private int shipping;
    private String address;
    
    public InvoiceTable(){
        this.totalNoOfInvoice =0;
        this.totalNoOfOrder =0;
        this.invoiceNum ="";
        this.total= 0;
        this.discount=1;
    }
    //constructor for reading file
    public InvoiceTable(String inInvNum,String inCusID,String inDate,String inAddress){
        this();
        this.invoiceNum= inInvNum;
        this.CusID= inCusID;
        this.buyDate = inDate;
        this.address = inAddress;
    }
    //normal constuctor
     public InvoiceTable(String inCusID,String inDate,String prefix,float inDiscount,int inShip,String inAddress) {
	super(inDiscount);  	
        this.buyDate = inDate;
        this.CusID = inCusID; 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempDate= Calendar.getInstance();
        String temp;
        this.discount = inDiscount;
        this.total = 0;
        this.shipping = inShip;
        this.address = inAddress;
        try{
            tempDate.setTime(sdf.parse(inDate));
        }catch(ParseException e){}
        int temp2;
        temp=(Integer.toString(tempDate.get(Calendar.YEAR))).substring(2, 4);
        temp2=tempDate.get(Calendar.MONTH)+1;
         if(prefix.equals("IN")){
		 //set the format of invoice e.g SI1411000
            invoiceNum = String.format("%s%s%02d%03d",prefix,temp,temp2,totalNoOfOrder);
            
            totalNoOfOrder++;
        }else{
            //set the format of invoice e.g IN1411000
             invoiceNum = String.format("%s%s%02d%03d",prefix,temp,temp2,totalNoOfInvoice);
             totalNoOfInvoice++;
        }  
         
    }

    public String getAddress() {
        return address;
    }

    public int getShipping() {
        return shipping;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public static void setTotalNoOfInvoice(int totalNoOfInvoice) {
        InvoiceTable.totalNoOfInvoice = totalNoOfInvoice;
    }

    public static void setTotalNoOfOrder(int totalNoOfOrder) {
        InvoiceTable.totalNoOfOrder = totalNoOfOrder;
    }

    public static int getTotalNoOfInvoice() {
        return totalNoOfInvoice;
    }

    public static int getTotalNoOfOrder() {
        return totalNoOfOrder;
    }
    
    
    public String getBuyDate() {
        return buyDate;
    }
     public String getInvoiceNum() {
        return invoiceNum;
    }

    public String getCusID() {
        return CusID;
    }

   
}
