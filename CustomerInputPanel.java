
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class CustomerInputPanel extends JPanel{
    private final String EMPTY = "";
    public JTabbedPane myrecordP;
    private final JButton home,myacc,cartBut;
    public final JButton logout;
    private JPanel mainPanel;
    private final JPanel searchPanel,categoryPanel;
    private final AllProdTable prodmodel;
    private final TransactionTable transmodel;
    private final CustomerTable cusmodel;
    private final CartTable cartmodel;
     private final GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int xLeftPanel = 10;
    private final Font defaultfont=new Font("Lucida Console",Font.PLAIN,20);
    private int tempdiscount;
    private ShoppingCart addCartItem[];
    private int originalBuy;
    private int currentSelectCR ;
    private boolean checkShip;
    public CustomerInputPanel(String ID,CustomerTable inCus,AllProdTable inProd,TransactionTable inTrans) {
        this.setLayout(new BorderLayout(10,10));
        this.checkShip = true;
        this.tempdiscount = 0;
        cartmodel = SystemMenu.getcartTM();
        final CartTable checkcart = new CartTable(cartmodel.discount);
        this.prodmodel = inProd;
        this.transmodel = inTrans;
        this.cusmodel = inCus;
        final TableRowSorter<TableModel> cartSorter=new TableRowSorter<TableModel>(cartmodel);
        final TableRowSorter<TableModel> sorter=new TableRowSorter<TableModel>(prodmodel);
        TableRowSorter<TableModel> tranSort =new TableRowSorter<TableModel>(transmodel);
        final String cusID = ID;
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        mainPanel=new JPanel();
        //set cardLayout in mainPanel
        final CardLayout card=new CardLayout();
        mainPanel.setLayout(card);
        //mainPanel.setBorder(new LineBorder(Color.red, 1));
        //button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());     
        home = new JButton("Home");
        home.setFont(defaultfont);
        //add action listener to Home Buttom to show home panel
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show( mainPanel, "6");
            }
        });
        buttonPanel.add(home);
        cartBut = new JButton("Cart Content");
        cartBut.setFont(defaultfont);
        //cartBut listener is below
        buttonPanel.add(cartBut);
        
        myacc = new JButton("My Record");
        myacc.setFont(defaultfont);
        myacc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, "9");
            }
        });
        buttonPanel.add(myacc);
        JButton contact= new JButton("Contact us");
        contact.setFont(defaultfont);
        contact.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show( mainPanel, "10");
            }
        });
        buttonPanel.add(contact);
        logout = new JButton("Logout");
        logout.setFont(defaultfont);
        buttonPanel.add(logout);
        JButton quit = new JButton("Exit");
        quit.setFont(defaultfont);
        buttonPanel.add(quit);
       
        quit.addActionListener(new ActionListener() { 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                int confirm = JOptionPane.showConfirmDialog(null,"Are you Sure to quit the system?","Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    if(cartmodel.getRowCount()>0){
                        for(int i=0;i<cartmodel.getRowCount();i++){
                            String tempPID = String.valueOf(cartmodel.getValueAt(i,1));
                            int prodRow  =prodmodel.searchItem(tempPID);
                            int tempQ = (int)cartmodel.getValueAt(i,5);
                            int currentStock = (int)prodmodel.getValueAt(prodRow, 4); 
                            prodmodel.setValueAt(currentStock+ tempQ, prodRow, 4);//restore the cart item to the product list
                        }
                    }
                    //SystemMenu.savefile();
                    if(SystemMenu.savefile()){
                       JOptionPane.showMessageDialog(null,"File Saved");
                    }
                     System.exit(0);
                }
            } 
        });
        
        
        Font left=new Font("Lucida Console",Font.PLAIN,18);
        final TableRowSorter<TableModel> searchsort= new TableRowSorter<TableModel>(prodmodel);// new sort for the search
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(290,500));

        leftPanel.setLayout(null);
        searchPanel = new JPanel();
        searchPanel.setBounds(20,30,250,100);
        searchPanel.setLayout(null);
        searchPanel.setBorder(new TitledBorder("Search Item"));
        final JLabel searchHeadln = new JLabel("Keywords:");
        searchHeadln.setFont(left);
        searchHeadln.setBounds(xLeftPanel,30,140,20);
        final JTextField searchItem = new JTextField();
        searchItem.setFont(left);
        searchItem.setBounds(xLeftPanel,60,200,25);
        
        JButton searchB = new JButton(new ImageIcon("search.png"));
        searchB.setBounds(210, 60, 25, 25);
        searchB.setToolTipText("Search");
        
        searchB.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword=searchItem.getText();
                if(keyword.trim().length()!=0){
                    searchsort.setRowFilter(RowFilter.regexFilter(keyword));
                    //search result panel
                    JPanel searchResult= new JPanel(new BorderLayout(10,10));
                    JLabel titleS = new JLabel();
                    titleS.setText("Search Result of \"" + keyword+"\" ");
                    titleS.setFont(new Font("Lucida Console",Font.BOLD,30));
                    JTable searchT= new JTable(prodmodel);
                    searchT.setRowSorter(searchsort);
                    JScrollPane searchSP = new JScrollPane(searchT);
                   // searchSP.setBorder(new LineBorder(Color.red, 1));
                    searchResult.add(titleS,BorderLayout.NORTH);
                    searchResult.add(searchSP,BorderLayout.CENTER);
                    mainPanel.add(searchResult,"0");
                    card.show(mainPanel, "0");
                }
               
                
            }
        });
        searchPanel.add(searchHeadln);
        searchPanel.add(searchItem);
        searchPanel.add(searchB);
        categoryPanel =new JPanel();
        categoryPanel.setLayout(null);
        categoryPanel.setBorder(new TitledBorder("Categories"));
        categoryPanel.setBounds(20, 150, 250, 200);
        final String[] categoryString={"All Product List","Mouse","Keyboard","Notebook","Out of Stock Item"};
        JButton category[]=new JButton[5];
        int y=0;
        for(int i=0;i<5;i++){
            category[i]=new JButton(categoryString[i]);
            category[i].setFont(new Font("Lucida Console",Font.PLAIN,15));
            category[i].setBounds(15,y+=30,190,20);
            category[i].setHorizontalAlignment(JButton.LEFT);
            categoryPanel.add(category[i]);
        }
        
        
        leftPanel.add(searchPanel);
        leftPanel.add(categoryPanel);
        
        
        //panel of all product
        JPanel allProdPanel= new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        JLabel titleM1 = new JLabel("All product",JLabel.CENTER);
        titleM1.setFont(new Font("Lucida Console",Font.BOLD,30));
        c.weightx = 1.0;
	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        allProdPanel.add(titleM1,c);
        c.gridwidth = 1;
        c.gridheight =1;
        c.gridx = 0;
        c.gridy = 1;
        allProdPanel.add(new JLabel("Product Detail",JLabel.CENTER),c);
        c.gridx = 1;
        c.gridy = 1;
        final JLabel rightH = new JLabel();
        rightH.setHorizontalAlignment(JLabel.CENTER);
        allProdPanel.add(rightH,c);
        c.gridx = 1;
        c.gridy = 3;
        
        allProdPanel.add(new JLabel("Shopping Cart",JLabel.CENTER),c);
       
        final prodInfoP addCart = new prodInfoP((e.getMaximumWindowBounds().width-300)/2,new String[]{},false);
        addCart.setPreferredSize(new Dimension(500,460));

         final JSpinner spinner = new JSpinner();
        spinner.setFont(new Font("Lucida Console",Font.PLAIN,20));
        Rectangle tempLast = addCart.textField[3].getBounds();
        spinner.setBounds(tempLast.x,tempLast.y+=50,tempLast.width,tempLast.height);
        spinner.setValue(1);
        spinner.setEnabled(false);
        
        addCart.add(spinner);
        final JLabel numBuy= new JLabel("Quantity :");
        
        numBuy.setBounds(addCart.getLabelx(),tempLast.y,180,30);
        final JPanel addCartP = new JPanel (new FlowLayout(FlowLayout.CENTER,10,0));
        addCartP.setBounds(0,tempLast.y+50,(e.getMaximumWindowBounds().width-300)/2,40 );
        final JButton addToCart = new JButton("Add to Cart");
        addCartP.add(addToCart);
        final JButton editCart = new JButton("Edit Cart");
        addCartP.add(editCart);
        editCart.setEnabled(false);
        final JButton clear = new JButton("Clear");
        addCartP.add(clear);
        
        addCart.add(addCartP);
        addCart.add(numBuy);
        final JScrollPane editSP = new JScrollPane(addCart);
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        allProdPanel.add(editSP,c);
        
        final JTable allPT=new JTable(prodmodel);
        allPT.removeColumn(allPT.getColumnModel().getColumn(3));//remover column description
        allPT.setRowSorter(sorter);
        allPT.setToolTipText("Double click row to see detail which display on the left so as to add to cart");
        JScrollPane allProdSP = new JScrollPane(allPT);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 2;
        allProdPanel.add(allProdSP,c);
        final JTable cartPT = new JTable(cartmodel);
        cartPT.removeColumn(cartPT.getColumnModel().getColumn(2));//remove product Name
        cartPT.removeColumn(cartPT.getColumnModel().getColumn(2));//remove product description
        cartPT.removeColumn(cartPT.getColumnModel().getColumn(2));//remove categories
        cartPT.setRowSorter(cartSorter);
        cartPT.setToolTipText("Double click to see detail on the left for edit the number buy");
        JScrollPane cartSP = new JScrollPane(cartPT);
        c.gridx = 1;
        c.gridy = 4;
        allProdPanel.add(cartSP,c);
        for(int i=0;i<4;i++){
             addCart.textField[i].setEditable(false);
        }
        addCart.categories.setEnabled(false);
        
        addCart.des.setBackground(this.getBackground());
        addCart.des.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        addCart.des.setEditable(false);
        
        
        //add action listener in the all product panel
        //check out the from the table to the add cart side
        allPT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(e.getClickCount() == 2 && allPT.getSelectedRow()!=-1){
                   int[] tempx ={0,1,4,5};// 0:prod id,1:prod name,4:inventory,5:price
                   //addCart.categories.setEnabled(true);
                   for(int i=0;i<4;i++){
                       String info = String.valueOf(prodmodel.getValueAt(allPT.convertRowIndexToModel(allPT.getSelectedRow()),tempx[i]));
                       //String info = String.valueOf(prodmodel.getValueAt(allPT.getSelectedRow(),tempx[i]));
                      addCart.textField[i].setText(info);
                   }
                    addCart.categories.setSelectedItem(prodmodel.getValueAt(allPT.convertRowIndexToModel(allPT.getSelectedRow()), 2));
                    addCart.des.setText(String.valueOf(prodmodel.getValueAt(allPT.convertRowIndexToModel(allPT.getSelectedRow()), 3)));
                    spinner.setModel(new SpinnerNumberModel(1,1,999, 1));//value,min,max,step
                    spinner.setEnabled(true);
                    addToCart.setEnabled(true);
                    editCart.setEnabled(false);
                   
               }
            }
        });
        
        cartPT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(e.getClickCount() == 2 && cartPT.getSelectedRow()!=-1){
                   currentSelectCR =cartPT.convertRowIndexToModel(cartPT.getSelectedRow());
                   int[] tempx ={1,2,0,6};
                   //addCart.categories.setEnabled(true);
                   
                   for(int i=0;i<4;i++){
                       //inventory should get form product tabel
                       if(i!=2){
                            String info = String.valueOf(cartmodel.getValueAt(currentSelectCR,tempx[i]));
                            addCart.textField[i].setText(info);
                       }
                   }
                   String prodIDCart =String.valueOf(cartmodel.getValueAt(currentSelectCR,1));
                   String inventory = Integer.toString((int)prodmodel.getValueAt(prodmodel.searchItem(prodIDCart), 4));
                   addCart.textField[2].setText(inventory);
                   addCart.categories.setSelectedItem(cartmodel.getValueAt(currentSelectCR, 3));
                   addCart.des.setText(String.valueOf(cartmodel.getValueAt(currentSelectCR, 4)));
                   spinner.setModel(new SpinnerNumberModel(1,1,999, 1));//value,min,max,step
                   originalBuy = (int)cartmodel.getValueAt(currentSelectCR, 5);
                   spinner.setValue(originalBuy);
                   spinner.setEnabled(true);
                   editCart.setEnabled(true);
                   addToCart.setEnabled(false);
               }
            }
        });
       addToCart.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                //if cart do not have the new added item, new item row in the shopping cart create
                int rowNum=cartmodel.searchItem("Purchase",addCart.textField[0].getText());
                int rowNum2= cartmodel.searchItem("Order",addCart.textField[0].getText());
                int rowProdNum = prodmodel.searchItem(addCart.textField[0].getText());
                int inventory =Integer.parseInt(String.valueOf(prodmodel.getValueAt(rowProdNum, 4)));
                int buyNum = (int)spinner.getValue();
              
                    ShoppingCart newProdToCart;

                    //check if enough inventory
                    if( inventory >= buyNum){
                        if(rowNum!=-1){
                            int originNum = (int)cartmodel.getValueAt(rowNum, 5);
                            cartmodel.setValueAt(Integer.toString((int)spinner.getValue()+originNum) , rowNum, 5);
                        }
                        else{
                            newProdToCart = new ShoppingCart(true,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),buyNum,Float.parseFloat(addCart.textField[3].getText()));    
                            cartmodel.additem(newProdToCart);
                        }
                    }
                    else{
                        //already have order made,must be order
                        if(inventory<=0){
                            //check cus have already order this item
                            if(rowNum2!=-1){
                                int originNum = (int)cartmodel.getValueAt(rowNum2, 5);
                                cartmodel.setValueAt((int)spinner.getValue()+originNum , rowNum2, 5);
                            }
                            else{
                                int confirm = JOptionPane.showConfirmDialog(null,"<html>Not enough inventory!!<br>Do you want to have order?</html>","Order Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    newProdToCart = new ShoppingCart(false,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),buyNum,Float.parseFloat(addCart.textField[3].getText()));
                                    cartmodel.additem(newProdToCart);
                                }
                            }
                        }
  
                        //still have some
                        else{
                            int confirm =  JOptionPane.showOptionDialog(null, "<html>Not enough inventory!!<br>Purchase only or have both order and purchase?</html>","Option Dialog", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,new Object[]{"Purchase Only", "Both", "Cancel"}, "Both");
                            
                            if(confirm ==0 || confirm ==1){
                                if(confirm ==0){
                                    buyNum = 0;
                                }
                                if(rowNum!=-1){
                                    int originNum = (int)cartmodel.getValueAt(rowNum, 5);
                                    cartmodel.setValueAt((int)spinner.getValue()+originNum , rowNum, 5);
                                }
                                else{
                                    newProdToCart = new ShoppingCart(true,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),inventory,Float.parseFloat(addCart.textField[3].getText()));
                                    cartmodel.additem(newProdToCart);
                                }
                                
                            }
                            if(confirm ==1){
                                newProdToCart = new ShoppingCart(false,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),buyNum-inventory,Float.parseFloat(addCart.textField[3].getText()));
                                cartmodel.additem(newProdToCart);
                            }
                            
                            
                        }
                    } 
                    prodmodel.setValueAt(inventory-buyNum, rowProdNum, 4);//update inventory
                    addCart.textField[2].setText(String.valueOf(prodmodel.getValueAt(rowProdNum, 4)));
                    //addCart.categories.setEnabled(false);
               
            }
        });
       editCart.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                final ShoppingCart newedit;
                int inventory = Integer.parseInt(addCart.textField[2].getText());
                int tempcurrentQ = (int)cartmodel.getValueAt(currentSelectCR, 5);
                int editQ = (int)spinner.getValue();
                int rowInCart = currentSelectCR;
                int rowInProd = prodmodel.searchItem(String.valueOf(addCart.textField[0].getText()));
                int difference = editQ-tempcurrentQ;
                int searchOrder= cartmodel.searchItem("Order",addCart.textField[0].getText());
                boolean si = String.valueOf(cartmodel.getValueAt(rowInCart, 0)).equals("Purchase");
                int confirm =JOptionPane.YES_OPTION;
                //decrese the order /purchase
                if(editQ<=tempcurrentQ){
                    //the purchase decrease and it have order of same kind of product,order move to purchase
                    if(si&&searchOrder!=-1){
                        //if order is less than the nuber of delete 
                        if((int)cartmodel.getValueAt(searchOrder, 5)<=-difference){
                            cartmodel.delitem(cartmodel.getshoppingcart(searchOrder));
                            cartmodel.setValueAt(editQ+(int)cartmodel.getValueAt(searchOrder, 5), rowInCart, 5);
                            prodmodel.setValueAt(inventory+(int)cartmodel.getValueAt(searchOrder, 5), rowInProd, 4);
                            addCart.textField[2].setText(Integer.toString(inventory+(int)cartmodel.getValueAt(searchOrder, 5)));
                            confirm =JOptionPane.NO_OPTION;
                            
                        }
                        else{
                             cartmodel.setValueAt((int)cartmodel.getValueAt(searchOrder, 5)+difference, searchOrder, 5);
                        }
                    }
                    else
                    cartmodel.setValueAt(editQ, rowInCart, 5);
                }
                //else: editQ>tempcurrentQ which means buy more
                else{
                    if(inventory<=0){
                        if(si){
                             confirm= JOptionPane.showConfirmDialog(null,"<html>Not enough inventory!!<br>Do you want to have order?</html>","Order Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if (confirm == JOptionPane.YES_OPTION) {
                                if(searchOrder==-1){
                                    newedit = new ShoppingCart(false,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),difference,Float.parseFloat(addCart.textField[3].getText()));
                                    cartmodel.additem(newedit);
                                }
                                else{
                                    cartmodel.setValueAt((int)cartmodel.getValueAt(searchOrder, 5)+difference , searchOrder, 5);
                                }
                                
                            }
                        }
                        else{
                            cartmodel.setValueAt(editQ , rowInCart, 5);
                        }
                    }
                    else {
                        if(difference>inventory){
                            confirm = JOptionPane.showConfirmDialog(null,"<html>Not enough inventory!!<br>Do you want to have order?</html>","Order Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if (confirm == JOptionPane.YES_OPTION) {
                                newedit = new ShoppingCart(false,addCart.textField[0].getText(),addCart.textField[1].getText(),addCart.categories.getSelectedItem().toString(),addCart.des.getText(),difference-inventory,Float.parseFloat(addCart.textField[3].getText()));
                                cartmodel.additem(newedit);
                                editQ=tempcurrentQ +inventory;
                            }
                            else{
                                editQ=tempcurrentQ;
                            }
                        }
                        cartmodel.setValueAt(editQ , rowInCart, 5);
                    }
                }
                if(confirm !=JOptionPane.NO_OPTION){
                    prodmodel.setValueAt(inventory-difference, rowInProd, 4);
                    addCart.textField[2].setText(Integer.toString(inventory-difference));
                }
                
                
            }
        });
       clear.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<4;i++){
                    //setText blank to the textField when record added
                    addCart.textField[i].setText(EMPTY);
                 }
                addCart.categories.setSelectedIndex(0);
                addCart.des.setText(EMPTY);
                spinner.setValue(1);
                spinner.setEnabled(false);
                editCart.setEnabled(false);
                //addCart.categories.setEnabled(false);
            }
        });
        
        //homePanel
        JPanel homeP = new JPanel();
        homeP.add(new JLabel(new ImageIcon("300logo.gif")));
        
        //cart content Panel
        
        JPanel cartPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints cartC = new GridBagConstraints();
        JLabel titleC = new JLabel("Shopping Cart",JLabel.CENTER);
        titleC.setFont(new Font("Lucida Console",Font.BOLD,30));
        cartC.fill = GridBagConstraints.BOTH;
        cartC.weightx = 1.0;
        cartC.weighty = 0.0;
        cartC.gridwidth = GridBagConstraints.REMAINDER;
        cartC.gridx = 0;
        cartC.gridy = 0;
        cartPanel.add(titleC,cartC);
        JPanel cartHeadButton = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JButton selectAll = new JButton("SelectAll");
        cartHeadButton.add(selectAll);
        JButton clearSelect = new JButton("Clear Select");
        cartHeadButton.add(clearSelect);
        JButton delCart = new JButton("Delete");
        cartHeadButton.add(delCart);
        cartC.gridy = 1;
        cartPanel.add(cartHeadButton,cartC);
        
        
        final JTable allCart =new JTable(cartmodel);
        allCart.setRowSorter(cartSorter);
        
        
        //allCart.setPreferredScrollableViewportSize(allCart.getPreferredSize());
        //allCart.setFillsViewportHeight(true);
        JScrollPane allCartSP = new JScrollPane(allCart);
        cartC.weighty = 1.0;
        cartC.gridy = 2;
        cartPanel.add(allCartSP,cartC);

        
       // allCartSP.setBorder(new LineBorder(Color.red, 1));
        
        JPanel discountP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel discountLbl = new JLabel("Discount: ");
        final JLabel showdiscount = new JLabel("N/A");
        discountP.add(discountLbl);
        discountP.add(showdiscount);
        cartC.weighty = 0.0;
        cartC.gridy = 3;
        cartPanel.add(discountP,cartC);
        
        JPanel cartTotalP = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        JLabel cartTotal= new JLabel("Total(not include shipping cost): $");
        final JLabel showTotal = new JLabel();
        cartTotalP.add(cartTotal);
        cartTotalP.add(showTotal);
        cartC.gridy = 4;
        cartPanel.add(cartTotalP,cartC);
        
        JPanel cartButtonP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkoutB = new JButton("Checkout");
        cartButtonP.add(checkoutB);
        cartC.gridy = 5;
        cartPanel.add(cartButtonP,cartC);
	
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allCart.getSelectionModel().setSelectionInterval(0, allCart.getRowCount()-1);
                
            }
        });
        clearSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allCart.getSelectionModel().clearSelection();
                
            }
        });	
        delCart.addActionListener(new ActionListener() {
            //String info = prodmodel.getValueAt(allCart.convertRowIndexToView(allPT.getSelectedRow()),tempx[i]);
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(allCart.getSelectedRow()!=-1){
                    int selectedRow[]=allCart.getSelectedRows();
                    for(int i = 0;i<allCart.getSelectedRows().length;i++){
                        selectedRow[i] = allCart.convertRowIndexToModel(selectedRow[i]);
                    }
                    
                    ShoppingCart delCartItem[] = new ShoppingCart[selectedRow.length];
                    for(int i = 0;i<selectedRow.length;i++){
                        delCartItem[i] = cartmodel.getshoppingcart(selectedRow[i]);
                        int delitemrowProd = prodmodel.searchItem(String.valueOf(cartmodel.getValueAt(selectedRow[i], 1)));
                        prodmodel.setValueAt((int)prodmodel.getValueAt(delitemrowProd, 4)+delCartItem[i].getProductInv(), delitemrowProd, 4);
                    }
                    for(int i= 0;i<selectedRow.length;i++){
                        cartmodel.delitem(delCartItem[i]);
                    }
                    showTotal.setText(Float.toString(cartmodel.getTotal()));
                }
                
            }
        });
		
        mainPanel.add(cartPanel,"7");
        cartBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show( mainPanel, "7");
                //!=1 means have discount
                
                tempdiscount=Math.round((1-cartmodel.discount)*100);
                showdiscount.setText(tempdiscount+"% off");
                
                if(cartmodel.getRowCount()!=0){
                    showTotal.setText(Float.toString(cartmodel.getTotal()));
                }
                else{
                    showTotal.setText("0");
                }
                
            }
        });
        
        //checkout Panel
        //JTabbedPane checkoutPane = new JTabbedPane();
        //checkout the cart Panel
        JPanel checkoutCart = new JPanel(new GridBagLayout());
        GridBagConstraints ccGBC = new GridBagConstraints();//checkout Cart GridBagConstraints
        ccGBC.fill = GridBagConstraints.BOTH;
        JLabel checkoutT1 = new JLabel("Checkout the Cart",JLabel.CENTER);
        checkoutT1.setFont(new Font("Lucida Console",Font.BOLD,30));
        ccGBC.weightx = 1.0;
	ccGBC.weighty = 0.0;
        ccGBC.gridwidth = GridBagConstraints.REMAINDER;
        ccGBC.gridx = 0;
        ccGBC.gridy = 0;
        checkoutCart.add(checkoutT1,ccGBC);
        
        JLabel addressT = new JLabel("Address",JLabel.LEFT);
        ccGBC.gridwidth = 1;
        ccGBC.gridx = 0;
        ccGBC.gridy = 1;
        checkoutCart.add(addressT,ccGBC);
        
        String[] districtItem = new String[]{"District","Hong Kong","Kowloon","New Territories"};
        final JComboBox districtCombo = new JComboBox(districtItem);
        ccGBC.gridwidth = 1;
        ccGBC.gridx = 1;
        ccGBC.gridy = 1;
        checkoutCart.add(districtCombo,ccGBC);
        final JTextArea address = new JTextArea();
        address.setLineWrap(true);
        address.setDocument(new TFLengthRestricted(231));
        address.setFont(new Font("Lucida Console",Font.PLAIN,15));
        ccGBC.gridwidth = 2; 
        ccGBC.gridheight=2;
        ccGBC.weighty = 1.0;
        ccGBC.gridx = 0;
        ccGBC.gridy = 3;
        checkoutCart.add(address,ccGBC);
        address.setPreferredSize(new Dimension(400,50));
        
        JButton newaddress =new JButton("Set New Address");
        newaddress.setToolTipText("Clear the address on the left");
        ccGBC.fill = GridBagConstraints.NONE;
        ccGBC.insets = new Insets(0,30,0,0);
        //ccGBC.ipady = 5;      
        ccGBC.gridwidth = 1;
        ccGBC.gridheight=1;
        ccGBC.weighty = 1.0;
        ccGBC.anchor = GridBagConstraints.SOUTHWEST;
        ccGBC.gridx = 2;
        ccGBC.gridy = 3;
        checkoutCart.add(newaddress,ccGBC);
        
        JButton updateaddress =new JButton("Update & Save Address");
        updateaddress.setToolTipText("Save this address to the your file");
        //ccGBC.ipady = 5; 
        
        ccGBC.gridwidth = 1;
        ccGBC.gridheight=1;
        ccGBC.anchor = GridBagConstraints.SOUTHWEST;
        ccGBC.gridx = 2;
        ccGBC.gridy = 4;
        checkoutCart.add(updateaddress,ccGBC);
        
        JPanel shippingP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel shippingH = new JLabel("Shipping : $");
        final JLabel shipping = new JLabel();
        
        shippingP.add(shippingH);
        shippingP.add(shipping);
        ccGBC.ipady = 0;  //reset to default
        ccGBC.insets = new Insets(0,50,0,0);
        ccGBC.fill = GridBagConstraints.BOTH;
        ccGBC.gridwidth = 1;
        ccGBC.gridheight=1;
        ccGBC.weighty = 0.0;
        ccGBC.gridx = 2;
        ccGBC.gridy = 6;
        checkoutCart.add(shippingP,ccGBC);
        
        JPanel discountP2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel discountLbl2 = new JLabel("Discount: ");
        final JLabel showdiscount2 = new JLabel();
        
        discountP2.add(discountLbl2);
        discountP2.add(showdiscount2);
        ccGBC.gridx = 2;
        ccGBC.gridy = 7;
        checkoutCart.add(discountP2, ccGBC);
        
        JPanel cTotalP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel cTotal = new JLabel("Total(include shipping): $");
        final JLabel ctempTotal = new JLabel();
        cTotalP.add(cTotal);
        cTotalP.add(ctempTotal);
        ccGBC.gridx = 2;
        ccGBC.gridy = 8;
        checkoutCart.add(cTotalP, ccGBC);
        
        JButton payBut = new JButton("Pay the Cart");
        ccGBC.fill = GridBagConstraints.NONE;
        ccGBC.gridx = 2;
        ccGBC.gridy = 9;
        ccGBC.anchor = GridBagConstraints.EAST;
        checkoutCart.add(payBut, ccGBC);
        
        final JTable checkcartTB =new JTable(checkcart);
        JScrollPane checkCartSP = new JScrollPane(checkcartTB);
        ccGBC.insets = new Insets(0,0,0,0);
        ccGBC.fill = GridBagConstraints.BOTH;
        ccGBC.ipadx = 0;
        ccGBC.gridwidth = GridBagConstraints.REMAINDER;
        ccGBC.ipady = 240;
        ccGBC.gridheight = 1;
        ccGBC.weighty = 1.0;
        ccGBC.gridx = 0;
        ccGBC.gridy = 5;
        checkoutCart.add(checkCartSP,ccGBC);
        
       // checkoutPane.add("<html><body  topmargin=10  marginheight=10><font size=4>Checkout Cart</font></body></html>", checkoutCart);
        mainPanel.add(checkoutCart,"8");
        newaddress.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
               address.setText("");
            }
        });
        updateaddress.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
               if(address.getText().equals("")||districtCombo.getSelectedIndex()==0){
                    JOptionPane.showMessageDialog(null, "Empty address/district cannot be save.", null, JOptionPane.WARNING_MESSAGE);
                }
                else{
                   cusmodel.setValueAt(districtCombo.getSelectedItem(), cusmodel.searchID(cusID),10);
                   cusmodel.setValueAt(address.getText(), cusmodel.searchID(cusID), 11);
                }
            }
        });
        districtCombo.addItemListener(new ItemListener()  {
            @Override
            public void itemStateChanged(ItemEvent disev) {
                if (disev.getStateChange() == ItemEvent.SELECTED) {
                    if(disev.getItem().equals("District")){
                        shipping.setText("N/A");
                        
                    }
                    if(checkShip){
                        if(disev.getItem().equals("Kowloon")){
                            shipping.setText("10");
                        }
                        else if(disev.getItem().equals("Hong Kong")){
                            shipping.setText("15");
                        }
                        else if(disev.getItem().equals("New Territories")){
                            shipping.setText("20");
                        }
                        Float shippingCost;
                       if(shipping.getText().equals("District")){
                            shippingCost= Float.parseFloat(shipping.getText());
                            ctempTotal.setText(Float.toString(checkcart.total+ shippingCost));
                       }
                        
                    }
                    else if(!disev.getItem().equals("District")){
                        shipping.setText("0");
                    }
                }
            }
        });
        
        checkoutB.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                checkcart.delall();
                districtCombo.setSelectedItem(cusmodel.getValueAt(cusmodel.searchID(cusID),10));
                address.setText(String.valueOf(cusmodel.getValueAt(cusmodel.searchID(cusID),11)));
                if(allCart.getSelectedRow()!=-1){
                    card.show( mainPanel, "8");
                    int selectedRow[]=allCart.getSelectedRows();
                     addCartItem = new ShoppingCart[selectedRow.length];
                    for(int i = 0;i<allCart.getSelectedRows().length;i++){
                        selectedRow[i] = allCart.convertRowIndexToModel(selectedRow[i]);
                        addCartItem[i] = cartmodel.getshoppingcart(selectedRow[i]);
                        
                    }
                    for(int i= 0;i<selectedRow.length;i++){
                        checkcart.additem(addCartItem[i]);
                    }
                    if(checkcart.total>999){
                        shipping.setText("0");
                        checkShip =false;
                    }
                    else{
                        checkShip = true;
                    }
                    showdiscount2.setText(tempdiscount+"% off");
                    ctempTotal.setText(Float.toString(checkcart.total));
                }
                else{
                    if(cartmodel.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Nothing in the cart, cannot check out");
                    }
                    else
                    JOptionPane.showMessageDialog(null,"You should select things to checkout");
                }
            }
        });
        final JButton next = new JButton("Order");
        final JButton back = new JButton("Invoice");
        payBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shipping.getText().equals("N/A")||address.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"You should enter the district and address");
                }
                else{
                int confirm = JOptionPane.showConfirmDialog(null,"Are you Sure to Pay it?","Payment Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    ctempTotal.setText(Float.toString(cartmodel.total));
                    String date = formatter.format(new Date());//get the instant time for putting inside the invoicet
                    System.out.println(InvoiceTable.getTotalNoOfInvoice());
                    
                    InvoiceTable purchaseInvoice = new InvoiceTable(cusID,date,"SI",checkcart.getDiscount(),Integer.parseInt(shipping.getText()),address.getText());
                    System.out.println(InvoiceTable.getTotalNoOfInvoice());
                    InvoiceTable orderInvoice = new InvoiceTable(cusID,date,"IN",checkcart.getDiscount(),Integer.parseInt(shipping.getText()),address.getText());
                    for(int i=0;i<checkcart.getRowCount();i++){
                        if(checkcart.getValueAt(i, 0).equals("Purchase")){
                            ShoppingCart buyItem = checkcart.getshoppingcart(i);
                            purchaseInvoice.additem(buyItem);
                        }
                        else{
                            ShoppingCart buyItem = checkcart.getshoppingcart(i);
                            orderInvoice.additem(buyItem);
                        }
                    }
                    boolean si = purchaseInvoice.getRowCount()>0,od =orderInvoice.getRowCount()>0;
                    InvoicePanel newinvoice = new InvoicePanel(purchaseInvoice,true);
                    InvoicePanel orderP = new InvoicePanel(orderInvoice,false);
                    mainPanel.add(newinvoice,"a");
                    mainPanel.add(orderP,"b");
                    if(si){
                        transmodel.addInvoice(purchaseInvoice);
                        card.show( mainPanel, "a");
                    }
                    else{
                        card.show( mainPanel, "b");
                    }
                    if(od){
                        transmodel.addInvoice(orderInvoice);
                    }
                    if(si && od){
                        newinvoice.invoiceTP.add(next);
                        orderP.invoiceTP.add(back);
                    }
                    //delete cartItem in the outside cart list
                    for (ShoppingCart addCartItem1 : addCartItem) {
                        cartmodel.delitem(addCartItem1);
                    }
                }
                }
            }
        });
        next.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show( mainPanel, "b");
            }
        });
        back.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show( mainPanel, "a");
            }
        });
        
        //my record Panel
        myrecordP = new JTabbedPane();
        if(!cusID.equals("admin")){
            Customer currentcus = cusmodel.getCus(cusmodel.searchID(cusID));
            CustomerInformationPanel cusInfo = new CustomerInformationPanel(currentcus);
            myrecordP.add("<html><body  topmargin=10  marginheight=10><font size=4>Personal Information</font></body></html>", cusInfo);
        }
        
        
        TransactionPanel transaction = new TransactionPanel(transmodel,false);
        transaction.setSorter(RowFilter.regexFilter(cusID, 1));

      
       
        
        myrecordP.add("<html><body  topmargin=10  marginheight=10><font size=4>Transaction record</font></body></html>", transaction);

        
        //contact us Panel
        JPanel contactP = new JPanel();
        contactP.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1.0;
        c2.weighty = 0.0;
        c2.gridwidth = GridBagConstraints.REMAINDER;
        c2.gridx = 0;
        c2.gridy = 0;
        //contactP.add(new JLabel(new ImageIcon("300logo.jpg")));
        JLabel first = new JLabel("<html><br><u><b>Contact Us</b></u><br>GJLR IT LIMITED<br>3/F, 28 Wang Hoi Road, Kowloon Bay, Kowloon, H.K.<br><br><u><b>General Inquiries</b></u>" +
        "<br>Hotline: (852) 3762 2222 " +
        "<br>Fax: (852) 2274 3333 </html>");
        contactP.add(first,c2);
        //" +
        //"<br>E-mail: info@gjlr.com.hk" +
        //"<br>Office hours: (Monday to Friday): 9:00-13:00,14:00-18:00 " +
       // "<br>Close on Satuarday , Sunday & Public Holiday 
        
        JPanel mailP = new JPanel();
        mailP.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel second = new JLabel("E-mail: info@gjlr.com.hk");
        mailP.add(second);
        JButton a = new JButton("mail");
        mailP.add(a);
        //mailP.setBorder(new LineBorder(Color.red, 1));
        c2.gridy = 1;
       
        contactP.add(mailP,c2);
        c2.gridy=2;
        contactP.add(new JLabel("<html><br>Office hours: (Monday to Friday): 9:00-13:00,14:00-18:00<br>Close on Satuarday , Sunday & Public Holiday </html>"),c2);
        a.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop=Desktop.getDesktop();
                try{
                    URI mailto=new URI("mailto:info@gjlr.com.hk");
                    desktop.mail(mailto);
                }catch(URISyntaxException e1){} catch (IOException ex) { }
            }
        });
        //set action Listener to the category button
        category[0].addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
               card.show(mainPanel, "1");
               rightH.setText(categoryString[0]);
               sorter.setRowFilter(null);
            }
        });
        category[1].addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
               card.show(mainPanel, "1");
               rightH.setText(categoryString[1]);
               sorter.setRowFilter(RowFilter.regexFilter(categoryString[1], 2));
            }
        });
        category[2].addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
               card.show(mainPanel, "1");
               rightH.setText(categoryString[2]);
               sorter.setRowFilter(RowFilter.regexFilter(categoryString[2], 2));
            }
        });
        category[3].addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
               card.show(mainPanel, "1");
               rightH.setText(categoryString[3]);
               sorter.setRowFilter(RowFilter.regexFilter(categoryString[3], 2));
            }
        });
        category[4].addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
               card.show(mainPanel, "1");
               rightH.setText(categoryString[4]);
               sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, 1, 4));
            }
        });
        
        mainPanel.add(allProdPanel, "1");
        mainPanel.add(homeP,"6");
        mainPanel.add(myrecordP,"9");
        mainPanel.add(contactP,"10");
        card.show( mainPanel, "6");
        
        JLabel copy=new JLabel("Copyright © GJLR IT LIMITED",JLabel.CENTER);
        copy.setFont(new Font("Lucida Console",Font.PLAIN,12));
        this.add(buttonPanel,BorderLayout.PAGE_START);
        this.add(leftPanel,BorderLayout.LINE_START);
        this.add(mainPanel,BorderLayout.CENTER);
        this.add(copy,BorderLayout.PAGE_END);
    }

    public CartTable getCartmodel() {
        return cartmodel;
    }
    
}
