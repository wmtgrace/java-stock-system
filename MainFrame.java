import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.*;
import javax.swing.table.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTinge
 */
public final class MainFrame extends JFrame{
    private static final String EMPTY = "";
    private final Container contentPane;
    private final JPanel titlePanel;
    private final CustomerInputPanel cusPanel;
    private final JTabbedPane adminP;
    private final JLabel headln;
    private final TableRowSorter<TableModel> sorter,cusSorter;
    private static boolean bgMus=false;
    private final Font defaultfont=new Font("Lucida Console",Font.PLAIN,20);
    private final AllProdTable prodmodel;
    private final CustomerTable cusmodel;
    
    private JScrollPane ProdP;
  
    private final GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final TransactionTable trans;
    private prodInfoP editP;
    public static String cusName;
    private int actualIndex;
    private Font titlefont;
    public MainFrame(String inID) {
        this.setTitle("GJLR's Purchasing System");       
        trans = SystemMenu.getTransTM();
        titlePanel = new JPanel();
        titlefont = LoginFrame.newfont;

        titlefont= titlefont.deriveFont(Font.PLAIN,90);
        final String id = inID;
        
        this.setUndecorated(true);

        
        contentPane = getContentPane();
        
        //this.setSize(screenSize.width, screenSize.height);//set full screen
        
        this.setMaximizedBounds(e.getMaximumWindowBounds());
        setResizable(false);//restrict the size of screen
        this.setSize(e.getMaximumWindowBounds().width, e.getMaximumWindowBounds().height);
        titlePanel.setPreferredSize(new Dimension(e.getMaximumWindowBounds().width, 90));
        FlowLayout flow=new FlowLayout();
        titlePanel.setLayout(flow);
        titlePanel.setBackground(Color.white);
        contentPane.add(titlePanel,BorderLayout.NORTH);
        
        adminP = new JTabbedPane();
        prodmodel =SystemMenu.getprodTM();
        sorter = new TableRowSorter<TableModel>(prodmodel); 
        cusmodel=SystemMenu.getcusTM();
        cusSorter= new TableRowSorter<TableModel>(cusmodel); 
        cusPanel=new CustomerInputPanel(id,cusmodel,prodmodel,trans);
        
        if(id.equals("admin")){
            cusName="admin";
        }
        else{
            Customer tempcus = cusmodel.getCus(cusmodel.searchID(id));
            if(tempcus.getNickName().equals(EMPTY)){
                if(tempcus.getGender()=='M'){
                    cusName =" Mr. ";
                }
                else{
                    cusName = "Ms. ";
                }
                cusName+=tempcus.getFName();
            }
            else{
                cusName = tempcus.getNickName();
            }
        }
              
        //add different component depends on the id  
        if(id.equals("admin")){
            contentPane.add(adminP,BorderLayout.CENTER);
            addComponentToAdmin();
        }else{
            
            contentPane.add(cusPanel,BorderLayout.CENTER);
 
        }
        
        //component add to title panel which is a share panel
        headln = new JLabel("GJLR IT LIMITED",JLabel.CENTER);
        headln.setFont(titlefont);
        headln.setSize(530, 75);

        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("75logo.gif"));
        titlePanel.add(logo);
        titlePanel.add(headln);
        flow.setHgap(30);
        final JLabel time = new JLabel();
        time.setFont(new java.awt.Font("Tahoma", 0, 24)); 
        titlePanel.add(time);
        
       
        cusSetting();
        new Thread(){
            public void run(){
                while(true){
                    Calendar now = Calendar.getInstance();
                    String current = now.getTime().toString().substring(0,20);
                    time.setText("<html>Hi, "+cusName+"<br>"+current+"</html>");
                }
            }
        }.start();
        
    }
    private void cusSetting(){
        cusPanel.logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent back) {
                int confirm = JOptionPane.showConfirmDialog(null,"Are you Sure to Logout?","Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (confirm == JOptionPane.YES_OPTION) {
                        final CartTable cartmodel= cusPanel.getCartmodel();
                        for(int i=0;i<cartmodel.getRowCount();i++){
                            String tempPID = String.valueOf(cartmodel.getValueAt(i,1));
                            int prodRow  =prodmodel.searchItem(tempPID);
                            int tempQ = (int)cartmodel.getValueAt(i,5);
                            int currentStock = (int)prodmodel.getValueAt(prodRow, 4); 
                            prodmodel.setValueAt(currentStock+ tempQ, prodRow, 4);//restore the cart item to the product list
                        }
                        if(SystemMenu.savefile()){
                           JOptionPane.showMessageDialog(null,"File Saved");
                        }
                        setVisible(false);
                        
                        Main.test.setVisible(true);
                        LoginFrame.loginPanel.setVisible(true);
                    }
                
            }
            
        });
    }
    
    private void addComponentToAdmin(){
        //all prod panel of admin
        
        JTable allPT=new JTable(prodmodel);
        
        allPT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        allPT.getColumnModel().getColumn(1).setPreferredWidth(250);
        allPT.getColumnModel().getColumn(3).setPreferredWidth(600);
       
        allPT.setRowSorter(sorter);
        JScrollPane allProdP = new JScrollPane(allPT);
        allPT.setRowSelectionAllowed(false);
     
        JPanel tabbed1= new JPanel(new BorderLayout(10,10));
        JLabel titleT1 = new JLabel("Product List",JLabel.LEFT);
        JComboBox selectKind = new JComboBox(new String[]{"All","Mouse","Keyboard","Notebook","Out of Stock"});
        selectKind.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent prodev) {
            
              selectCatActionPerformed(prodev,sorter);
            } 
        });
        
        titleT1.setFont(new Font("Lucida Console",Font.BOLD,30));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT,250,0));
        header.add(titleT1);
        header.add(selectKind);
        tabbed1.add(header,BorderLayout.NORTH);
        tabbed1.add(allProdP,BorderLayout.CENTER);
        
        
        //edit panel
        JPanel tabbed2= new JPanel(new GridBagLayout());
        final GridBagConstraints editc = new GridBagConstraints();
        //first row
        JLabel titleT2 = new JLabel("Edit Product",JLabel.CENTER);
        titleT2.setFont(new Font("Lucida Console",Font.BOLD,30));
        editc.fill = GridBagConstraints.BOTH;
        editc.weightx = 1.0;
        editc.weighty = 0.0;
        editc.gridwidth = GridBagConstraints.REMAINDER;
        editc.gridx = 0;
        editc.gridy = 0;
        tabbed2.add(titleT2,editc);
        //second row
        JLabel titleL = new JLabel("Product Detail",JLabel.CENTER);
        editc.gridwidth = 1;
        editc.gridx = 0;
        editc.gridy = 1;
        tabbed2.add(titleL,editc);
        JPanel rightP = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
        JLabel titleR = new JLabel ("Product List",JLabel.LEFT);
         JComboBox selectProd = new JComboBox(new String[]{"All","Mouse","Keyboard","Notebook"});
        rightP.add(titleR);
        rightP.add(selectProd);
        editc.gridwidth = 1;
        editc.gridx = 1;
        editc.gridy = 1;
        tabbed2.add(rightP,editc);
        //third row
        JPanel insideP= new JPanel(new GridLayout(1,2));
        
        String[] button = new String[]{"Add","Update","Delete","Clear"};
        editP= new prodInfoP(e.getMaximumWindowBounds().width/2, button,true);
        editP.setPreferredSize(new Dimension(e.getMaximumWindowBounds().width/2-50,420));
        JScrollPane editSP = new JScrollPane(editP);
        insideP.add(editSP);
        final JTable prodT= new JTable(prodmodel);
        final TableRowSorter<TableModel> editsort = new TableRowSorter<TableModel>(prodmodel);
        ProdP = new JScrollPane(prodT);
        prodT.removeColumn(prodT.getColumnModel().getColumn(3));//remove description column
        prodT.setRowSorter(editsort);
        prodT.setToolTipText("Double Click to see detail for edit on the left");
        insideP.add(ProdP);
        //set itemlistener to change to row filter
        selectProd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent prodev) {
                selectCatActionPerformed(prodev,editsort);
            } 
        });
        
        //set actionListener in the editP
        //set actionListener to Add button
        editP.button[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
   
                if(checkProdAvalible(EMPTY)){
                    Product newprod = new Product(editP.textField[0].getText(),editP.textField[1].getText(),editP.categories.getSelectedItem().toString(),editP.des.getText(),Integer.parseInt(editP.textField[2].getText()),Float.parseFloat(editP.textField[3].getText()));
                    prodmodel.additem(newprod);
                    for(int i=0;i<4;i++){
                        ///setText blank to the message line
                        if(i<3){
                            editP.message[i].setText(EMPTY);
                        }
                        //setText blank to the textField when record added
                        editP.textField[i].setText(EMPTY);
                    }
                    editP.categories.setSelectedIndex(0);
                    editP.des.setText(EMPTY);
                }
            }
        });   
        
        //set actionListener to update button
        editP.button[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row =actualIndex;
                if(checkProdAvalible(String.valueOf(prodmodel.getValueAt(row, 0)))){ 
              
                    if(prodT.getSelectedRow()!=-1){
                    
                        int[] tempx ={0,1,4,5};
                        for(int i=0;i<4;i++){
                            prodmodel.setValueAt(editP.textField[i].getText(), row, tempx[i]);
                        }
                        prodmodel.setValueAt(editP.categories.getSelectedItem().toString(),row,2);
                        prodmodel.setValueAt(editP.des.getText(), row, 3);
                    
                    }
                }
                
               
            }
        });
        //set actionListener to delete button
        editP.button[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prodT.getSelectedRow()!=-1){
                    if((int)prodmodel.getValueAt(actualIndex, 4)>=0){
                        Product delprod = prodmodel.getProd(actualIndex);
                        prodmodel.delitem(delprod);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "This item has got order,cannot deleted", "Delete Warning", JOptionPane.WARNING_MESSAGE); 
                    }
                    
                }
            }
        });
        //Clear Button
        editP.button[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<4;i++){
                    //setText blank to the message line
                    if(i<3){
                        editP.message[i].setText(EMPTY);
                    }
                    //setText blank to the textField when record added
                    editP.textField[i].setText(EMPTY);
                 }
                editP.categories.setSelectedIndex(0);
                editP.des.setText(EMPTY);
            }
        });
        
        //add mouse Listener to product table
        prodT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(e.getClickCount() == 2 && prodT.getSelectedRow()!=-1){
                   /*int index =transTM.searchInvoice(transTM.getValueAt(transTB.convertRowIndexToModel(transTB.getSelectedRow()), 0).toString());//get correct row index when sorted
                   InvoiceTable readInv = transTM.getInvoice(index);*/
                   actualIndex = prodT.convertRowIndexToModel(prodT.getSelectedRow());
                   int[] tempx ={0,1,4,5};
                   for(int i=0;i<4;i++){
                        editP.textField[i].setText(String.valueOf(prodmodel.getValueAt(actualIndex, tempx[i])));
                   }
                   editP.categories.setSelectedItem(prodmodel.getValueAt(actualIndex, 2));
                   editP.des.setText(String.valueOf(prodmodel.getValueAt(actualIndex, 3)));
                   
               }
            }

        });
        editc.weighty = 1.0;
        editc.gridx = 0;
        editc.gridy = 2;
        editc.gridwidth = GridBagConstraints.REMAINDER;
        editc.gridheight = GridBagConstraints.REMAINDER;
        tabbed2.add(insideP,editc);
  

        
    
        //customer record panel
        JPanel tabbed3 = new JPanel();
        tabbed3.setLayout(new BoxLayout(tabbed3, BoxLayout.Y_AXIS));
        JLabel titleT3 = new JLabel("Customer Record",JLabel.CENTER);
        titleT3.setFont(new Font("Lucida Console",Font.BOLD,30));
        titleT3.setAlignmentX(Component.TOP_ALIGNMENT);
        JTable cusPT=new JTable(cusmodel);
        cusPT.setRowSorter(cusSorter);
        cusPT.removeColumn(cusPT.getColumnModel().getColumn(1));
        JScrollPane cusSP = new JScrollPane(cusPT);
        JPanel searchcusP = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        final JTextField cusKW=new  JTextField(50);
        cusKW.setFont(defaultfont);
        JButton searchcus=new JButton("search");
        searchcus.setFont(defaultfont);
        searchcus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cusKey=cusKW.getText();
                if(cusKey.trim().length()==0){
                    cusSorter.setRowFilter(null);
                }
                else{
                    cusSorter.setRowFilter(RowFilter.regexFilter(cusKey,0));
                     
                }
                      
            }
        });
        searchcusP.add(new JLabel("Search Customer:"));
        searchcusP.add(cusKW);
        searchcusP.add(searchcus);
        searchcusP.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabbed3.add(titleT3);
        tabbed3.add(searchcusP);
        tabbed3.add(cusSP);
        
        //Transaction panel in admin
        TransactionPanel adminTrans = new TransactionPanel(trans,true);
        
        
        adminP.add("<html><body topmargin=10 marginheight=10><font size=5>Show all Product</font></body></html>",tabbed1);
        adminP.add("<html><body  topmargin=10  marginheight=10><font size=5>Edit Product</font></body></html>",tabbed2);
        adminP.add("<html><body  topmargin=10  marginheight=10><font size=5>Customer Record</font></body></html>",tabbed3);
        adminP.add("<html><body  topmargin=10  marginheight=10><font size=5>Transaction Record</font></body></html>",adminTrans);
    }
    private boolean checkProdAvalible(String inOriginalID){
        final String PRODID = "[a-zA-Z][\\w]{1,30}";
        final String INVENTORY = "[\\d]+";
        final String PRICE = "[0-9]+\\.[0-9]{0,2}";
        boolean check = true;
        //chekc id
        if(prodmodel.searchItem(editP.textField[0].getText())!=-1 && !editP.textField[0].getText().equals(inOriginalID)){
            editP.message[0].setText("Product ID already exist");    
            check = false;
        }
        else if(!editP.textField[0].getText().matches(PRODID)){
            editP.message[0].setText("Product ID must be start with english characters");
            check = false;
        }
        else{
            editP.message[0].setText(EMPTY);
        }
        //check inventory
        
        if(!editP.textField[2].getText().matches(INVENTORY)){
                editP.message[2].setText("Input must be integer");
                check = false;
        }
        else{
            editP.message[2].setText(EMPTY);
        }
        
        //check price

        if(!editP.textField[3].getText().matches(PRICE)&&!editP.textField[3].getText().matches(INVENTORY)){
                editP.message[3].setText("Input must be up to 2 decimal places");
                check = false;
        }
        else{
            editP.message[3].setText(EMPTY);
        }
        
        return check;
    }
    
    
    private void selectCatActionPerformed(ItemEvent prodev,TableRowSorter<TableModel> insorter) {
        if (prodev.getStateChange() == ItemEvent.SELECTED) {
            if(prodev.getItem().equals("All")){
                        insorter.setRowFilter(null);
            }
            else if(prodev.getItem().equals("Out of Stock")){
                insorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, 1, 4));
            }
            else{
                insorter.setRowFilter(RowFilter.regexFilter(String.valueOf(prodev.getItem()),2));
            }       
         }
    } 
   // private void setOutFilter
}
