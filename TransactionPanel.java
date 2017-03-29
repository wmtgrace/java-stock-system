
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class TransactionPanel extends JPanel{
    private JTable transTB;
    private final TableRowSorter<TableModel> sorter;
    private TransactionTable transTM;
    private final List<RowFilter<TableModel,Object>> filters;
    private RowFilter<TableModel, Object> searchFilter;
    private RowFilter<TableModel, Object> timeFilter;
    private RowFilter<TableModel, Object> compoundRowFilter;
    private JButton searchB;
    private JTextField input;
    private final JComboBox filterTime;
    private String timeselected;
	private boolean inAdmin;
    public TransactionPanel(TransactionTable trans,boolean admin) {
        this.transTM = trans;
        this.sorter= new TableRowSorter<TableModel>(transTM);
        this.filters= new ArrayList<RowFilter<TableModel,Object>>();
        this.inAdmin=admin;
        this.timeselected="All Time";
        //filters.add(firstFilter);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        //first row
        JLabel transT = new JLabel("Transaction record",JLabel.CENTER);
        transT.setFont(new Font("Lucida Console",Font.BOLD,30));
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(transT,c);
        //second row
        JPanel filterP;
        if(inAdmin){
            filterP = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        }
        else
            filterP = new JPanel(new FlowLayout(FlowLayout.CENTER,300,10));
        JButton showall = new JButton("Show All");
        filterP.add(showall);
        if(inAdmin){
            JPanel searchCus = new JPanel(new FlowLayout());
            searchCus.add(new JLabel("Search Keywords:"));
            input = new JTextField(30);
            input.setDocument(new TFLengthRestricted(30));
            searchCus.add(input);
            searchB= new JButton("Search");
            searchCus.add(searchB);
            filterP.add(searchCus);
            searchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent back) {
                if(input.getText().trim().length()!=0){
                    if(filters.contains(searchFilter)){
                        filters.remove(searchFilter);//remove the old 
                    }
                    searchFilter = RowFilter.regexFilter(input.getText());
                    filters.add(searchFilter);
                    if(filterTime.getSelectedIndex()==0){
                        sorter.setRowFilter(searchFilter);
                    }
                    else{
                        compoundRowFilter =  RowFilter.andFilter(filters);
                        sorter.setRowFilter(compoundRowFilter);
                    }
                }

            }
           
        }); 
        }
        filterTime =new JComboBox(new String[]{"All Time","Within this Week","Within this Month","Within this Year"});
        filterP.add(filterTime);
        c.gridwidth = 1;
        c.gridy = 1;
        this.add(filterP,c);
        //third row
        transTB= new JTable(trans);
        transTB.setRowSorter(sorter);
        transTB.setToolTipText("Double click the row to see detail of invoice");
        JScrollPane invoiceSP = new JScrollPane(transTB);
        
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipady = 400;
        c.gridy = 2;
        this.add(invoiceSP,c);
        //fouth row
        JLabel numOfInv = new JLabel();
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.ipady = 0;
        c.gridy = 3;
        this.add(numOfInv,c);
        //prodmodel.getValueAt(allPT.getSelectedRow(), tempx[i])
        //this.sorter.setRowFilter(null);
        showall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent back) {
                if(inAdmin){
                    sorter.setRowFilter(null);
                    input.setText("");
                }
                filterTime.setSelectedIndex(0);
                
            }
            
        }); 
       
        filterTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if(filters.contains(timeFilter)){
                        filters.remove(timeFilter);//remove the old timeFilter
                    }
                    
                    timeselected =String.valueOf(e.getItem());
                    
                        Calendar calendar = Calendar.getInstance();
                    
                        switch (timeselected) {
                            case "Within this Week":
                                calendar.add(Calendar.DAY_OF_MONTH, -7);
                                break;
                            case "Within this Month":
                                calendar.add( Calendar.MONTH ,  -1 );
                                break;
                            case "Within this Year":
                                calendar.add( Calendar.YEAR ,  -1 );
                                break;
                        }
                        Date date= calendar.getTime();
                        if(timeselected.equals("All Time")){
                            timeFilter = RowFilter.dateFilter(ComparisonType.BEFORE, date,2);
                        }
                        else
                            timeFilter = RowFilter.dateFilter(ComparisonType.AFTER, date,2);
                        filters.add(timeFilter);
                    
                    
                    if(inAdmin){
                        if(input.getText().trim().length()==0){
                            sorter.setRowFilter(timeFilter);
                        }
                        else{
                            compoundRowFilter =  RowFilter.andFilter(filters);
                            sorter.setRowFilter(compoundRowFilter);
                        }  
                    }
                    else{
                        compoundRowFilter =  RowFilter.andFilter(filters);
                        sorter.setRowFilter(compoundRowFilter);
                    }
                    
                }
            }
        });
        //add action listenr to Table
        transTB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(e.getClickCount() == 2 && transTB.getSelectedRow()!=-1){
                   int index =transTM.searchInvoice(transTM.getValueAt(transTB.convertRowIndexToModel(transTB.getSelectedRow()), 0).toString());//get correct row index when sorted
                   InvoiceTable readInv = transTM.getInvoice(index);
                   openInvoice(readInv,readInv.getInvoiceNum().substring(0, 2).equals("SI"));
               }
            }
        });
    }

   
    public void setSorter(RowFilter inFilter){
        this.filters.add(inFilter);
        this.compoundRowFilter =  RowFilter.andFilter(filters);
        sorter.setRowFilter(compoundRowFilter);
    }
    private void openInvoice(InvoiceTable invoice,boolean inSI){
        JFrame invoiceF = new JFrame("Transaction detail");
        invoiceF.setSize(900,700);
        invoiceF.setLocationRelativeTo( null );
        //invoiceF.setUndecorated(true);
        invoiceF.setResizable(false);
        invoiceF.getContentPane().setLayout(new BorderLayout());
        JLabel companyT = new JLabel("GJLR IT LIMITED",JLabel.CENTER);//add icon
        Font comTfont = LoginFrame.newfont;
        comTfont=comTfont.deriveFont(Font.PLAIN,50);
        companyT.setFont(comTfont);
        invoiceF.getContentPane().add(companyT,BorderLayout.NORTH);
        invoiceF.getContentPane().add(new InvoicePanel(invoice,inSI),BorderLayout.CENTER);
        invoiceF.setVisible(true);
    }
    
}
