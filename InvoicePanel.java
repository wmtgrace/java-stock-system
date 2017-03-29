
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class InvoicePanel extends JPanel{
    public JPanel invoiceTP;
    public InvoicePanel(InvoiceTable invoice,boolean inSI) {
        String refNum = invoice.getInvoiceNum();
        String cusID = invoice.getCusID();
        String buyDate = invoice.getBuyDate();
        int shippingCost =invoice.getShipping();
        String address = invoice.getAddress();
        String discount = Float.toString(invoice.discount);
        String total = Float.toString(invoice.total);
        JLabel invoiceNo;
        JLabel invoiceT;
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
       /* //first row
        JLabel companyT = new JLabel("GJLR IT LIMITED",JLabel.CENTER);//add icon
        Font comTfont = LoginFrame.newfont;
        comTfont=comTfont.deriveFont(Font.PLAIN,50);
        companyT.setFont(comTfont);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(companyT,c);*/
        if(inSI){
            invoiceT = new JLabel("Invoice",JLabel.CENTER);
        }
        else{
            invoiceT = new JLabel("Order",JLabel.CENTER);
        }
        //second row
        invoiceT.setFont(new Font("Lucida Console",Font.BOLD,30));
        invoiceTP = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        invoiceTP.add(invoiceT);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(invoiceTP,c);
        //invoiceTP.setBorder(new LineBorder(Color.red, 1));
        //third row
        JPanel addressP = new JPanel(new BorderLayout());
        JLabel addressLB = new JLabel("Delivery address:");
      
        JTextArea addressArea = new JTextArea(address);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setEditable(false);
        addressArea.setBackground(this.getBackground());
        JScrollPane addressSP = new JScrollPane(addressArea);
        addressP.add(addressLB,BorderLayout.NORTH);
        addressP.add(addressSP,BorderLayout.CENTER);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 40;
        c.gridwidth=1;
        c.weightx = 5.0;
        c.weighty = 1.0;
        c.gridy = 1;
       // c.anchor= GridBagConstraints.WEST;
        this.add(addressP,c);
        JPanel infoInv = new JPanel(new GridBagLayout());//3 row ,1 column
        GridBagConstraints inc = new GridBagConstraints();
                inc.fill = GridBagConstraints.BOTH;
                inc.weightx = 1.0;
		inc.weighty = 1.0;
                inc.gridwidth = GridBagConstraints.REMAINDER;
                inc.gridx = 0;
                inc.gridy = 0;
        if(inSI){
            invoiceNo = new JLabel("Invoice Number : "+refNum);
        }
        else{
            invoiceNo = new JLabel("Order Number   : "+refNum);
        }
        
        infoInv.add(invoiceNo,inc);
        JLabel dateLB = new JLabel("Date           : "+buyDate);
        inc.gridy = 1;
        infoInv.add(dateLB,inc);
        JLabel idLB = new JLabel("Customer ID    : "+cusID);
        inc.gridy = 2;
        infoInv.add(idLB,inc);
        c.weightx = 1.0;
        c.gridx = 1;
        //infoInv.setBorder(new LineBorder(Color.red, 1));
        //c.anchor= GridBagConstraints.EAST;
        this.add(infoInv,c);
        
       ///forth row
        JTable invoiceTB= new JTable(invoice);
        invoiceTB.removeColumn(invoiceTB.getColumnModel().getColumn(0));
        JScrollPane invoiceSP = new JScrollPane(invoiceTB);
        //c.anchor= GridBagConstraints.NONE;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipady = 350;
        c.gridx = 0;
        c.gridy = 2;
        this.add(invoiceSP,c);
        
        //fifth row
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));
        JLabel shipping = new JLabel("Shipping Cost : "+shippingCost);
        //                new JLabel("Total         : "+total);
        shipping.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(shipping);
        JLabel discountLB = new JLabel("Discount      : "+discount);
        discountLB.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(discountLB);
        JLabel totalLB = new JLabel("Total         : "+total);
        totalLB.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(totalLB);
        c.ipady = 40;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.insets = new Insets(0,100,0,0);
        c.gridx = 1;
        c.gridy = 3;
        this.add(bottom,c);
        
        
    }
}
