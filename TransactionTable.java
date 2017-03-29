
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public final class TransactionTable extends AbstractTableModel{
    private final List<InvoiceTable> invoiceList;
    private final String[] transactionColumn = new String[]{"Ref Number","Customer ID","Date","Total"};
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    protected static final Class[] columns = new Class[]{String.class, String.class,Date.class,Float.class};
    public TransactionTable(){
        this.invoiceList= new ArrayList<InvoiceTable>();
    }
    public int addInvoice(InvoiceTable newInvoice){
        invoiceList.add(newInvoice);
        this.fireTableDataChanged();
        return getRowCount();
    }
    public int searchInvoice(String inInvNum){
        int index=-1;
        for(int i=0;i<getRowCount();i++){
            if(getValueAt(i,0).equals(inInvNum)){
                    index=i;
                    break;
            }
        }
        return index;
    }
    public InvoiceTable getInvoice(int index){
        return invoiceList.get(index);
    }
    @Override
    public int getRowCount() {
        return this.invoiceList.size();
    }

    @Override
    public int getColumnCount() {
        return transactionColumn.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceTable invoice = invoiceList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return invoice.getInvoiceNum();
            case 1:
                return invoice.getCusID();
            case 2:
        
            try {
                return formatter.parse(invoice.getBuyDate());
            } catch (ParseException ex) {}
            
            case 3:
                return new Float(invoice.getTotal());
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int col) {
        return transactionColumn[col] ;
    }
    @Override
    public Class getColumnClass(int c){
       return columns[c];
    }
}
