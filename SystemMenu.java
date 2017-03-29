
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;//for date format setting
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public final class SystemMenu {
    
    private static String EMAIL ="\\b[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}\\b";
    private static final TransactionTable invoiceList =new TransactionTable();//list for invoice
    //private static InvoiceTable invoice;
    private static String CustomerID;
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//set the format of the date which will be shown in the invoice
    public static Admin adminac;
    public static float discount;
    private static AllProdTable pt = new AllProdTable();//TableModel for product
    private static CustomerTable ct = new CustomerTable();//TableModel for customer
    private static CartTable cart;
    public static StringBuilder cusfile  = new StringBuilder();
    public static StringBuilder prodfile = new StringBuilder();
    public static StringBuilder tranfile = new StringBuilder();
    public static StringBuilder other = new StringBuilder();
    private static String cusfileN,prodfileN,adminfileN,tranfileN;
    public static boolean openfile(JPanel inP,String fileName){
        
        try{
            //read admin file
            BufferedReader readAdmin = new BufferedReader(new FileReader(fileName));
            Pattern adminp = Pattern.compile("(admin),([\\w.!#$%&'*+/=?^_`{|}~-]{3,35})");
            String adminLine = readAdmin.readLine();
            Matcher adminM = adminp.matcher(adminLine);
            if(adminM.matches()){
                adminac = new Admin(adminM.group(2));
                FirstPanel.loading.append("Admin File opened succesfully\n");
                return true;
            }
            else{
                FirstPanel.loading.append("Error Find: No admin find in the file\n");
                JOptionPane.showMessageDialog(inP,"No admin find in the file,please open the correct file.","Error Find: No admin find in the file",JOptionPane.ERROR_MESSAGE);
                JFileChooser chooser = new JFileChooser( );
                int status=chooser.showOpenDialog(inP);
                if(status== JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    FirstPanel.loading.append("File "+file.getName()+" opened\n");
                    return openfile(inP,file.getAbsolutePath());
                }
                else
                    return false;
            }
            
        }catch(IOException e){
            FirstPanel.loading.append("Error Find: Admin File Open Fail\n");
                JOptionPane.showMessageDialog(inP,"Admin File Open Fail,please open the correct file.","Error Find: Admin File Open Fail",JOptionPane.ERROR_MESSAGE);
                JFileChooser chooser = new JFileChooser( );
                int status=chooser.showOpenDialog(inP);
                if(status== JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    FirstPanel.loading.append("File "+file.getName()+" opened\n");
                    return openfile(inP,file.getAbsolutePath());
                }
                else
                    return false;

	}
	
    }
    public static void openfile(){    
        int cusErrorcount=0;
        try{
            //read customer file
            BufferedReader br = new BufferedReader(new FileReader("ctest.txt"));
            //login ID,pw,Fname,Lname,gender,email,DOB,phone
            Pattern loginp1 = Pattern.compile("([a-zA-Z][\\w]{1,35}),(\\w+),([\\s\\w]{2,35}),([\\s\\w]{2,35}),(M||F),("+EMAIL+"),([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{8})");
            //login ID,pw,Fname,Lname,NickName,gender,email,DOB,phone
            Pattern loginp2 = Pattern.compile("([a-zA-Z][\\w]{1,35}),(\\w+),([\\s\\w]{2,35}),([\\s\\w]{2,35}),([\\s\\w]{1,35}),(M||F),("+EMAIL+"),([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{8})");
            //login ID,pw,Fname,Lname,gender,email,DOB,phone,district,address
            Pattern loginp3 = Pattern.compile("([a-zA-Z][\\w]{1,35}),(\\w+),([\\s\\w]{2,35}),([\\s\\w]{2,35}),(M||F),("+EMAIL+"),([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{8}),(Hong Kong||Kowloon||New Terrtories),([\\s\\w!#$%&'()*+-./:,;<=>?]{1,231})");
            //login ID,pw,Fname,Lname,NickName,gender,email,DOB,phone,district,address
            Pattern loginp4 = Pattern.compile("([a-zA-Z][\\w]{1,35}),(\\w+),([\\s\\w]{2,35}),([\\s\\w]{2,35}),([\\s\\w]{1,35}),(M||F),("+EMAIL+"),([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{8}),(Hong Kong||Kowloon||New Terrtories),([\\s\\w!#$%&'()*+-./:,;<=>?]{1,231})");
            
            String line;
            while(null != (line = br.readLine())){
		Matcher m1 = loginp1.matcher(line);
                Matcher m2 = loginp2.matcher(line);
                Matcher m3 = loginp3.matcher(line);
                Matcher m4 = loginp4.matcher(line);
                Customer test;
                
		if(m1.matches())
		{
                    try{
                        //>0 mean there are already some customer in the record
                        if(ct.getRowCount()>0){
                            if(ct.searchID(m1.group(1))!=-1)//check the login id if already exist
                                throw new Exception("Login ID: "+m1.group(1)+" already exist");
                                
                        }
                        
                        Date indate = sdf.parse(m1.group(7));
                        test = new Customer(m1.group(1),m1.group(2),m1.group(3),m1.group(4),m1.group(5).charAt(0),m1.group(6),indate,m1.group(8));
                        ct.addCus(test);
                    }catch(ParseException e){
                        cusfile.append("Error: " + e.getMessage()+"\n");
                        cusErrorcount++;
                    }catch(Exception ex){
                        cusfile.append("Error: " + ex.getMessage()+"\n");
                        cusErrorcount++;
                    }
		}else if(m2.matches()){
                    
                    try{
                        
                        //>0 mean there are already some customer in the record
                        if(ct.getRowCount()>0){
                            if(ct.searchID(m2.group(1))!=-1)//check the login id if already exist
                                throw new Exception("Login ID: "+m1.group(1)+" already exist");
                        }
                        
                        Date indate = sdf.parse(m2.group(8));
                        //login ID,pw,Fname,Lname,NickName,gender,email,DOB,phone
                        test = new Customer(m2.group(1),m2.group(2),m2.group(3),m2.group(4),m2.group(5),m2.group(6).charAt(0),m2.group(7),indate,m2.group(9));
                        ct.addCus(test);
                    }catch(ParseException e){
                        cusfile.append("Error: " + e.getMessage()+"\n");
                        cusErrorcount++;
                    }catch(Exception ex){
                        cusfile.append("Error: " + ex.getMessage()+"\n");
                        cusErrorcount++;
                    }
                }
                else if(m3.matches()){
                    try{
                        //>0 mean there are already some customer in the record
                        if(ct.getRowCount()>0){
                            if(ct.searchID(m3.group(1))!=-1)//check the login id if already exist
                                throw new Exception("Login ID: "+m1.group(1)+" already exist");
                        }
                        
                        Date indate = sdf.parse(m3.group(7));
                         //login ID,pw,Fname,Lname,gender,email,DOB,phone,district,address
                        test = new Customer(m3.group(1),m3.group(2),m3.group(3),m3.group(4),m3.group(5).charAt(0),m3.group(6),indate,m3.group(8),m3.group(9),m3.group(10));
                        ct.addCus(test);
                    }catch(ParseException e){
                        cusfile.append("Error: " + e.getMessage()+"\n");
                        cusErrorcount++;
                    }catch(Exception ex){
                        cusfile.append("Error: " + ex.getMessage()+"\n");
                        cusErrorcount++;
                    }
                }else if(m4.matches()){
                    try{
                        //>0 mean there are already some customer in the record
                        if(ct.getRowCount()>0){
                            if(ct.searchID(m4.group(1))!=-1)//check the login id if already exist
                                throw new Exception("Login ID: "+m1.group(1)+" already exist");
                        }
                        
                        Date indate = sdf.parse(m4.group(8));
                         //login ID,pw,Fname,Lname,NickName,gender,email,DOB,phone,district,address
                        test = new Customer(m4.group(1),m4.group(2),m4.group(3),m4.group(4),m4.group(5),m4.group(6).charAt(0),m4.group(7),indate,m4.group(9),m4.group(10),m4.group(11));
                        ct.addCus(test);
                    }catch(ParseException e){
                        cusfile.append("Error: " + e.getMessage()+"\n");
                        cusErrorcount++;
                    }catch(Exception ex){
                        cusfile.append("Error: " + ex.getMessage()+"\n");
                        cusErrorcount++;
                    }
                }
                
		else{
                    cusfile.append("The wrong format of the line "+line+"\n");
                    cusErrorcount++;
		}
            }
            br.close();
        }catch(FileNotFoundException e){
            cusfile.append("Error: " + e.getMessage()+"\n");
            cusfile.append("Customer File open error,may registrate new ac\n");
            cusErrorcount++;
	}
	catch(IOException e){
            cusfile.append("IOException caught\n");
	}    
          if(cusErrorcount==0){
              cusfile.append("Cutomer File open successfully\n");
          }else{
              if(ct.getRowCount()>0)
                cusfile.append("\n"+cusErrorcount+" Error Find in reading Cutomer File, but you can still enter the system\n");
              else{
                  cusfile.append("\n"+cusErrorcount+" Error Find in reading Cutomer File and no valid customer record. You may registrate new ac\n");
              }
          }  
         System.out.println(cusfile);
        
        int prodErrorcount=0;
        try{    
            //read product file
            BufferedReader readprod = new BufferedReader(new FileReader("ptest.txt"));
            Pattern prodp = Pattern.compile("([a-zA-Z][\\w]{1,30}),([^,]{1,50}),(Mouse||Keyboard||Notebook),([^,]{2,150}),(-?[\\d]+),([0-9]+\\.[0-9]{0,2})");//id,name,catefories,description,inventory,price
            String prodline;
            while(null != (prodline = readprod.readLine())){
		Matcher prodm = prodp.matcher(prodline);
                
		if(prodm.matches())
		{
                    Product prod;
                    try{
                        int tempinv = Integer.parseInt(prodm.group(5));
                        float tempprice = Float.parseFloat(prodm.group(6));
                        if(pt.getRowCount()>0){
                           
                            if(pt.searchItem(prodm.group(1)) != -1)
                            throw new Exception("Product ID: " +prodm.group(1)+ " already exist");
              
                        }
                        prod =  new Product(prodm.group(1),prodm.group(2),prodm.group(3),prodm.group(4),tempinv,tempprice);
                        pt.additem(prod);
                    }catch(NumberFormatException e1){
                        prodfile.append("Error: " + e1.getMessage()+"\n");
                        prodfile.append("Wrong format of the inventory/price\n" );
                        prodErrorcount++;
                    }catch(Exception e){
                        prodfile.append("Error: " + e.getMessage()+"\n");
                        prodErrorcount++;
                    }
                    
		}
		else{
                    prodfile.append("The wrong format of the line "+prodline+"\n");
                    prodErrorcount++;
		}    
            }
            
            readprod.close();
        }catch(FileNotFoundException | IllegalStateException e){
            prodfile.append("Error: " + e.getMessage()+"\n");
	}
	catch(IOException e){
            prodfile.append("IOException caught \n");
	}
        
        if(prodErrorcount==0){
              prodfile.append("Product file open successfully\n");
          }else{
              if(pt.getRowCount()>0)
                prodfile.append("\n"+cusErrorcount+" Error Find in reading Product File,but you can still enter the system\n");
              else{
                  prodfile.append("\n"+cusErrorcount+" Error Find in reading Product File and no valid product record. You may add new product using admin\n");
              }
          }  
        System.out.println(prodfile);
        //read transation file
        int tranErrorcount =0;
        try{
            InvoiceTable readInvoice = new InvoiceTable();
            BufferedReader readtran = new BufferedReader(new FileReader("ttest.txt"));
            Pattern headln = Pattern.compile("((SI||IN)[\\d]{7}),([a-zA-Z][\\w]{1,35}),([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}),([\\s\\w!#$%&'()*+-./:,;<=>?]{1,231})");
            Pattern prodbln = Pattern.compile("([a-zA-Z][\\w]{1,30}),([^,]{1,50}),(Mouse||Keyboard||Notebook),([^,]{2,150}),([\\d]+),([0-9]+\\.[0-9]{0,2}),([0-9]+\\.[0-9]{0,2})");//prodid,name,catagories,description,num buy,price,total price
            Pattern end = Pattern.compile("([0-9]{1,2}),([01]\\.[0-9]+),([0-9]+\\.[0-9]{0,2})");
            String tranln;
            ShoppingCart prodB;
            boolean inSI = true;
            int lnCount = 0;
             while(null!=(tranln = readtran.readLine())){
                try{
                    Matcher head = headln.matcher(tranln);
                    Matcher itemln = prodbln.matcher(tranln);
                    Matcher totalln = end.matcher(tranln);
                    Calendar today = Calendar.getInstance();
                    Calendar invCal= Calendar.getInstance();
                    if(head.matches() && lnCount == 0){
                        lnCount++;
                        String temp =head.group(1);
                        String invYear,invMonth;
                        int tempTNum =Integer.parseInt(temp.substring(6,9));
                        invCal.setTime(formatter.parse(head.group(4)));//ParseException
                        invYear = Integer.toString(invCal.get(Calendar.YEAR));
                        invMonth = Integer.toString(invCal.get(Calendar.MONTH)+1);//as 0= January,must +1 to get the correct month
                        if(invoiceList.getRowCount()>0){
                            //check the invoice num already exist
                            if(invoiceList.searchInvoice(temp)!=-1){
                                lnCount=0;//set back the lnCount and wait for new invoice
                                throw new Exception("Ref Num: " +temp+ " already exist");
                            }
                        }
                        //check the format of the invoice-invoice num and the invoice date(not after today)  and the year & month that represent in the invoice num equals to date in the invoice
                        if((!temp.substring(0,2).equals("IN")&&!temp.substring(0,2).equals("SI")) || invCal.after(today) ||(!temp.substring(2,4).equals(invYear.substring(2,4)) || !temp.substring(4,6).equals(invMonth))){
                            throw new Exception("wrong format of Ref Number/ Ref Date " );
                        }
                        
                        readInvoice= new InvoiceTable(head.group(1),head.group(3),head.group(4),head.group(5));
                        
                        if(temp.substring(0,2).equals("SI")){
                            inSI=true;
                            if(temp.substring(2,4).equals(Integer.toString(today.get(Calendar.YEAR)).substring(2,4)) && temp.substring(4,6).equals(Integer.toString(today.get(Calendar.MONTH)+1)) && tempTNum > InvoiceTable.getTotalNoOfInvoice()){
                                InvoiceTable.setTotalNoOfInvoice(tempTNum+1);
                                
                            }
                            
                        }
                        else{
                            inSI=false;
                            if(temp.substring(2,4).equals(Integer.toString(today.get(Calendar.YEAR)).substring(2,4)) && temp.substring(4,6).equals(Integer.toString(today.get(Calendar.MONTH)+1)) && tempTNum > InvoiceTable.getTotalNoOfOrder()){
                                InvoiceTable.setTotalNoOfOrder(tempTNum+1); 
                                
                            }
                        }
                    }
                    else if(itemln.matches() && lnCount >= 1){//(\\w+),(\\w+),(Mouse||Keyboard||Notebook),(.+),(\\d+),(.+),(.+)");//prodid,name,catagories,description,num buy,price,total price
                        int tempnum = Integer.parseInt(itemln.group(5));
                        float tempprice = Float.parseFloat(itemln.group(6));//throw NumberFormatException
                        float tempamount = Float.parseFloat(itemln.group(7));    
                        if(tempamount!=SystemMenu.precision(tempnum*tempprice)){
                            lnCount=0; //error checked look for correct pattern of another invoice
                            throw new Exception("Subtotal(num*price) calculated not equal to amount in the file "+ tranln);
                        }
                            prodB=new ShoppingCart(inSI,itemln.group(1),itemln.group(2),itemln.group(3),itemln.group(4),tempnum,tempprice);
                            readInvoice.additem(prodB);
                            lnCount++;
                    }
                    else if(totalln.matches() && lnCount >=2){
                        lnCount=0; //reach end of invoice set to 0 to read new
                        int tempshipping = Integer.parseInt(totalln.group(1));//throw NumberFormatException
                        float tempdiscount =Float.parseFloat(totalln.group(2));
                        float temptotal =Float.parseFloat(totalln.group(3));
                       
                            //set the discount
                           
                            if(readInvoice.getTotal()*tempdiscount+tempshipping !=temptotal){//compare the value between caculated and that in the file 
                                throw new Exception("Total (shipping+discount*total amount of product) calculated not equal to amount in the file "+ tranln);
                            }
                            
                            readInvoice.setShipping(tempshipping);
                            readInvoice.setDiscount(tempdiscount);
                            readInvoice.setTotal(temptotal);
                            invoiceList.addInvoice(readInvoice);
                            
                    }
                    else{
                        tranErrorcount++;
                       tranfile.append("Wrong format of invoice of the line:" +tranln +"\n");
                       
                        lnCount=0;//look for new correct pattern
                    }
                    
                }catch(NumberFormatException e1){
                    tranfile.append("NumberFormatException Caught" + e1.getMessage()+"\n");
                    tranErrorcount++;
                }catch(ParseException e2){
                    tranfile.append("ParseException Caught\n");
                    tranErrorcount++;
                }catch(Exception e){
                    tranfile.append("Exception Caught: "+ e.getMessage()+"\n");
                    tranErrorcount++;
                }    
             }
            
            readtran.close();
        }catch(FileNotFoundException e){
            tranfile.append("Error: " + e.getMessage());
             tranErrorcount++;
	}
	catch(IOException e){
            tranfile.append("IOException caught");
             tranErrorcount++;
	}
        if(tranErrorcount==0){
              other.append("Transaction file open successfully\n");
          }else{
              if(invoiceList.getRowCount()>0)
                other.append("\n"+tranErrorcount+" Error Find in reading Transaction File,but you can still enter the system\n");
              else{
                  other.append("\n"+tranErrorcount+" Error Find in reading Transaction File and no valid Transaction record.\n");
              }
          }   
        
       
    }
    
    public static boolean savefile(){
        boolean check = true;
        check=savecusfile("ctest.txt");
        check=saveprodfile("ptest.txt");
        check=savetranfile("ttest.txt");
        return check;
        
        
        
    }
    
    public static boolean savecusfile(String cusFile){
        cusfileN = cusFile;
        try {
            int i,j;
            PrintWriter writeCus = new PrintWriter(cusfileN);
            for(i=0;i<ct.getRowCount();i++){
                StringBuilder cus = new StringBuilder();
                cus.append(ct.getValueAt(i, 0).toString());
                for(j=1;j<12;j++){
                    if(!ct.getValueAt(i, j).equals("") && j!=7&&j!=8)
                    cus.append(","+ct.getValueAt(i, j).toString());
                }
                writeCus.println(cus);//write one line to file when getting one customer record in the StringBulder
            }

            writeCus.close();
        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null,"Write to customer fail","Error",JOptionPane.ERROR_MESSAGE);
           return false;
        }
        return true;
    }
    public static boolean saveprodfile(String infilename){
        prodfileN = infilename;
        try {
            int i,j;
            PrintWriter writeProd = new PrintWriter(prodfileN);
            
            for(i=0;i<pt.getRowCount();i++){
                StringBuilder prod = new StringBuilder();
                for(j=0;j<pt.getColumnCount()-1;j++){
                    prod.append(pt.getValueAt(i, j)+",");
                }
                prod.append(pt.getValueAt(i, j));//last item have no comma after that
                writeProd.println(prod);//write one line to file when getting one product record in the StringBulder
            }

            writeProd.close();
        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null,"Write to Product fail","Error",JOptionPane.ERROR_MESSAGE);
           return false;
        }
        return true;
    }
    public static boolean savetranfile(String infilename){
        tranfileN = infilename;
        try {
		PrintWriter writeT = new PrintWriter(tranfileN);
                for (int i=0; i<invoiceList.getRowCount();i++)
                {
                    InvoiceTable b = invoiceList.getInvoice(i);
				
	//			System.out.println(b.getInvoiceNum()+","+b.getCusID()+b.getBuyDate());
				StringBuilder st = new StringBuilder();
				st.append(b.getInvoiceNum()+","+b.getCusID()+","+b.getBuyDate()+","+b.getAddress()+System.getProperty("line.separator"));
				for (int count=0;count<b.getRowCount();count++)
				{   
                                    int j;
                                    for(j=1;j<b.getColumnCount()-1;j++){
                                            st.append(b.getValueAt(count, j)+",");
                                    }
                                     st.append(b.getValueAt(count, j));//last item have no comma after that
                                    st.append(System.getProperty("line.separator"));
                                }
				st.append(b.getShipping()+","+b.getDiscount()+","+b.getTotal()+System.getProperty("line.separator"));
				writeT.print(st);            
        }
		writeT.close();
		}
		catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"Write to trans fail","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
        return true;
    }
    
    public static boolean checkLogin(String inID){
        return ct.searchID(inID)!=-1;//return ture when the ID is exist
    }
    public static MyResult isThisDateValid(String dateToValidate){
        Date date;
        try {
        if(dateToValidate == null){
			return new MyResult(false);
	}
	sdf.setLenient(false);
	
            SimpleDateFormat inputsdf = new SimpleDateFormat("yyyy-MMMM-dd");
		//if not valid, it will throw ParseException
            inputsdf.setLenient(false);
            date = inputsdf.parse(dateToValidate);
	} catch (ParseException e) {
            return new MyResult(false);
	}
        return new MyResult(true,date);
    }
    public final static class MyResult {
        private final boolean first;
        private Date second;
        
        public MyResult(boolean first, Date second) {
            this(first);
            this.second = second;
        }
        public MyResult(boolean first){
            this.first=first;
        }

    public boolean getFirst() {
        return first;
    }

    public Date getSecond(){
      
            return second;
        
    }
    }
        
    public static void createAC(Customer newcus){
            ct.addCus(newcus);//add to the customer list 
    }
   

    public static boolean login(String inID,String inPW){
        if(inID.equals("admin") && inPW.equals(adminac.getPW())){
                CustomerID = inID;
                JOptionPane.showMessageDialog(null,"Login Successfully");
                cart = new CartTable();//fake cart 
                return true;
         }

        int index=ct.searchID(inID);
        if(index==-1 && !inID.equals("admin")){
            JOptionPane.showMessageDialog(null,"No such Login ID");
        }
        else if(inID.equals("admin")){
            JOptionPane.showMessageDialog(null,"Wrong password","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            if(inPW.equals(ct.getValueAt(index, 1))){
                CustomerID = inID;
                
                JOptionPane.showMessageDialog(null,"Login Successfully");

                cart = new CartTable(checkDiscount(CustomerID));//new cart created when login

                return true;          
            }
            else 
                JOptionPane.showMessageDialog(null,"Wrong password","Error",JOptionPane.ERROR_MESSAGE);       
            }
        
        return false; 
    }
    public static float checkDiscount(String cusID){
        return ct.getCus(ct.searchID(cusID)).getDiscount();

    }
        

    
   
    //function to check the discount of the customer;
    public static float checkDiscount(int inAge){
        int age=inAge;
		//age below 25 10%off
        if(age<=25){
            discount=(float) 0.9;
        }
		//age above 60 20%off
        else if(age>=60){
            discount=(float)0.8;
        }
        else{
            discount=1;
        }
        return discount;
    }
   
 
    
    public static AllProdTable getprodTM(){
        return pt;
    }
    public static CustomerTable getcusTM(){
        return ct;
    }
    public static CartTable getcartTM(){
            return cart;
    }
    public static TransactionTable getTransTM(){
        return invoiceList;
    }
    
    
    
    
	//function of sleep
    /*public void delay(){
        try {
                 TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("Exception caught");
        }
    }
    public static void pressEnterToContinue() { 
        System.out.print("Press Enter to continue...");
		scan.nextLine();
        
    }*/
   /* public static boolean checkSpace(String abc, String string){
	return (abc.matches(VIN));
    }*/
    //method to round up the float
    public static float precision(float d) {

	BigDecimal bd = new BigDecimal(Float.toString(d));
	bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	return bd.floatValue();
    }
    
    
}